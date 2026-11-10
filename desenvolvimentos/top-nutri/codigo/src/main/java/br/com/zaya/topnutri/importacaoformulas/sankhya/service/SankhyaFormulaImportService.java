package br.com.zaya.topnutri.importacaoformulas.sankhya.service;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidCreateVO;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.zaya.topnutri.importacaoformulas.model.entity.CompositionPlan;
import br.com.zaya.topnutri.importacaoformulas.model.entity.FormulaComponent;
import br.com.zaya.topnutri.importacaoformulas.model.entity.IntermediateProductAllocation;
import br.com.zaya.topnutri.importacaoformulas.model.entity.ProductCategory;
import br.com.zaya.topnutri.importacaoformulas.model.entity.ProductionFormula;
import br.com.zaya.topnutri.importacaoformulas.model.service.FormulaCompositionPlanner;
import br.com.zaya.topnutri.importacaoformulas.model.service.PrefixedMicroIntermediateProductResolver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/** Integra a importacao ao versionamento nativo da Manufatura Sankhya. */
public final class SankhyaFormulaImportService {
    private static final String PROCESS_ENTITY = "ProcessoProdutivo";
    private static final String FINISHED_PRODUCT_ENTITY = "ProdutoAcabado";
    private static final String ACTIVITY_ENTITY = "Atividade";
    private static final String MATERIAL_ENTITY = "ListaMateriaisAtividade";
    private static final String PRODUCT_ENTITY = "Produto";
    private static final BigDecimal PA_PROCESS_CODE = BigDecimal.ONE;
    private static final BigDecimal MICRO_PROCESS_CODE = new BigDecimal("6");
    private static final String MICRO_MATERIAL_TYPE = "Micro";
    private static final String PACKAGING_MATERIAL_TYPE = "EmbRotulo";
    private static final String UNIT = "UN";
    private final MaterialStockControlPolicy stockControlPolicy =
            new MaterialStockControlPolicy();

    public ImportResult importFormulas(List<ProductionFormula> formulas) throws Exception {
        if (formulas == null || formulas.isEmpty()) {
            throw new IllegalArgumentException("Nenhuma formula valida foi recebida para importacao.");
        }

        JapeSession.SessionHandle session = null;
        JdbcWrapper jdbc = null;
        try {
            session = JapeSession.open();
            session.setCanTimeout(false);
            session.setFindersMaxRows(-1);

            EntityFacade facade = EntityFacadeFactory.getDWFFacade();
            jdbc = facade.getJdbcWrapper();
            jdbc.openSession();

            JapeWrapper processDao = JapeFactory.dao(PROCESS_ENTITY);
            JapeWrapper finishedProductDao = JapeFactory.dao(FINISHED_PRODUCT_ENTITY);
            JapeWrapper activityDao = JapeFactory.dao(ACTIVITY_ENTITY);
            JapeWrapper materialDao = JapeFactory.dao(MATERIAL_ENTITY);
            JapeWrapper productDao = JapeFactory.dao(PRODUCT_ENTITY);

            Map<BigDecimal, ProductSettings> products =
                    loadAndValidateProducts(formulas, productDao);
            List<CompositionPlan> plans = planFormulas(formulas, products);
            List<BigDecimal> finishedProductCodes = new ArrayList<BigDecimal>();
            List<BigDecimal> microProductCodes = new ArrayList<BigDecimal>();
            for (CompositionPlan plan : plans) {
                ProductionFormula formula = plan.getSourceFormula();
                finishedProductCodes.add(numericCode(
                        formula.getFinishedProductCode(), "produto acabado",
                        formula.getLineNumber()));
                if (plan.getIntermediateProduct().isPresent()) {
                    IntermediateProductAllocation allocation =
                            plan.getIntermediateProduct().get();
                    BigDecimal microCode = numericCode(
                            allocation.getProductCode(), "PI de micronutrientes",
                            formula.getLineNumber());
                    loadProductSettings(
                            microCode, allocation.getProductCode(), "PI de micronutrientes",
                            formula.getLineNumber(), products, productDao);
                    microProductCodes.add(microCode);
                }
            }

            VersionedProcess paProcess = versionExpectedProcess(
                    PA_PROCESS_CODE, "PA", finishedProductCodes,
                    processDao, finishedProductDao, activityDao, materialDao);
            VersionedProcess microProcess = null;
            if (!microProductCodes.isEmpty()) {
                microProcess = versionExpectedProcess(
                        MICRO_PROCESS_CODE, "PI de micronutrientes", microProductCodes,
                        processDao, finishedProductDao, activityDao, materialDao);
            }

            int importedComponents = 0;
            for (CompositionPlan plan : plans) {
                importedComponents += updateFinishedProductComposition(
                        plan, paProcess, products, productDao,
                        finishedProductDao, materialDao);
                if (plan.getIntermediateProduct().isPresent()) {
                    importedComponents += updateMicroComposition(
                            plan, microProcess, products,
                            finishedProductDao, materialDao);
                }
            }

            validateProcess(paProcess.getNewProcessId(), jdbc, facade);
            if (microProcess != null) {
                validateProcess(microProcess.getNewProcessId(), jdbc, facade);
                deployProcess(microProcess.getNewProcessId(), jdbc);
                updateProductionFormulas(
                        microProcess.getNewProcessId(), MICRO_PROCESS_CODE, jdbc);
            }
            deployProcess(paProcess.getNewProcessId(), jdbc);
            updateProductionFormulas(paProcess.getNewProcessId(), PA_PROCESS_CODE, jdbc);

            BigDecimal paVersion = findVersion(paProcess.getNewProcessId(), processDao);
            BigDecimal microVersion = microProcess == null
                    ? null : findVersion(microProcess.getNewProcessId(), processDao);
            return new ImportResult(
                    paProcess.getNewProcessId(), paVersion,
                    microProcess == null ? null : microProcess.getNewProcessId(), microVersion,
                    formulas.size(), importedComponents);
        } finally {
            if (jdbc != null) {
                JdbcWrapper.closeSession(jdbc);
            }
            if (session != null) {
                JapeSession.close(session);
            }
        }
    }

    private Map<BigDecimal, ProductSettings> loadAndValidateProducts(
            List<ProductionFormula> formulas,
            JapeWrapper productDao) throws Exception {
        Map<BigDecimal, ProductSettings> products =
                new HashMap<BigDecimal, ProductSettings>();

        for (ProductionFormula formula : formulas) {
            BigDecimal finishedProductCode = numericCode(
                    formula.getFinishedProductCode(), "produto acabado", formula.getLineNumber());
            loadProductSettings(
                    finishedProductCode, formula.getFinishedProductCode(),
                    "Produto acabado", formula.getLineNumber(), products, productDao);
            for (FormulaComponent component : formula.getComponents()) {
                BigDecimal componentCode = numericCode(
                        component.getComponentCode(), "componente", component.getLineNumber());
                loadProductSettings(
                        componentCode, component.getComponentCode(),
                        "Componente", component.getLineNumber(), products, productDao);
            }
        }
        return products;
    }

    private List<CompositionPlan> planFormulas(
            List<ProductionFormula> formulas,
            final Map<BigDecimal, ProductSettings> products) throws Exception {
        FormulaCompositionPlanner planner = new FormulaCompositionPlanner(
                componentCode -> {
                    BigDecimal code = numericCode(componentCode, "componente", 0);
                    ProductSettings settings = products.get(code);
                    return settings == null
                            ? Optional.<ProductCategory>empty()
                            : Optional.ofNullable(settings.getCategory());
                },
                new PrefixedMicroIntermediateProductResolver());
        List<CompositionPlan> plans = new ArrayList<CompositionPlan>();
        for (ProductionFormula formula : formulas) {
            plans.add(planner.plan(formula));
        }
        return plans;
    }

    private void loadProductSettings(
            BigDecimal code,
            String displayCode,
            String label,
            int line,
            Map<BigDecimal, ProductSettings> products,
            JapeWrapper productDao) throws Exception {
        if (products.containsKey(code)) {
            return;
        }
        DynamicVO product = productDao.findByPK(code);
        if (product == null) {
            throw new IllegalStateException(
                    label + " nao cadastrado no Sankhya: " + displayCode
                            + " (linha " + line + ").");
        }
        String categoryCode = product.asString("AD_CATEGORIA");
        ProductCategory category = null;
        if (categoryCode != null && !categoryCode.trim().isEmpty()) {
            category = ProductCategory.fromCode(categoryCode.trim())
                    .orElse(null);
        }
        products.put(code, new ProductSettings(
                product.asString("TIPCONTEST"),
                product.asString("CODVOL"),
                category));
    }

    private DynamicVO resolveSourceProcess(
            Collection<BigDecimal> productCodes,
            BigDecimal expectedProcessCode,
            String processLabel,
            JapeWrapper finishedProductDao,
            JapeWrapper processDao) throws Exception {
        Map<BigDecimal, DynamicVO> candidates = new LinkedHashMap<BigDecimal, DynamicVO>();
        for (BigDecimal productCode : productCodes) {
            Collection<DynamicVO> links = finishedProductDao.find(
                    "this.CODPRODPA = ?", productCode);
            for (DynamicVO link : links) {
                BigDecimal processId = link.asBigDecimal("IDPROC");
                if (processId == null || candidates.containsKey(processId)) {
                    continue;
                }
                DynamicVO process = processDao.findByPK(processId);
                if (process != null
                        && expectedProcessCode.compareTo(requiredNumber(process, "CODPRC")) == 0) {
                    candidates.put(processId, process);
                }
            }
        }
        if (candidates.isEmpty()) {
            for (DynamicVO process : processDao.find(
                    "this.CODPRC = ?", expectedProcessCode)) {
                BigDecimal processId = process.asBigDecimal("IDPROC");
                if (processId != null) {
                    candidates.put(processId, process);
                }
            }
        }
        if (candidates.isEmpty()) {
            throw new IllegalStateException(
                    "Processo produtivo " + expectedProcessCode + " (" + processLabel
                            + ") nao localizado para versionamento.");
        }

        List<DynamicVO> preferred = new ArrayList<DynamicVO>();
        for (DynamicVO process : candidates.values()) {
            if ("S".equalsIgnoreCase(process.asString("PADRAO"))) {
                preferred.add(process);
            }
        }
        if (preferred.isEmpty()) {
            preferred.addAll(candidates.values());
        }
        Collections.sort(preferred, new Comparator<DynamicVO>() {
            @Override
            public int compare(DynamicVO left, DynamicVO right) {
                BigDecimal leftVersion = valueOrZero(left.asBigDecimal("VERSAO"));
                BigDecimal rightVersion = valueOrZero(right.asBigDecimal("VERSAO"));
                return rightVersion.compareTo(leftVersion);
            }
        });

        return preferred.get(0);
    }

    private VersionedProcess versionExpectedProcess(
            BigDecimal expectedProcessCode,
            String processLabel,
            Collection<BigDecimal> importedProducts,
            JapeWrapper processDao,
            JapeWrapper finishedProductDao,
            JapeWrapper activityDao,
            JapeWrapper materialDao) throws Exception {
        DynamicVO sourceProcess = resolveSourceProcess(
                importedProducts, expectedProcessCode, processLabel,
                finishedProductDao, processDao);
        BigDecimal sourceProcessId = requiredNumber(sourceProcess, "IDPROC");
        BigDecimal processCode = requiredNumber(sourceProcess, "CODPRC");
        if (processCode.compareTo(expectedProcessCode) != 0) {
            throw new IllegalStateException(
                    "O processo localizado para " + processLabel + " possui codigo "
                            + processCode + ", mas era esperado " + expectedProcessCode + ".");
        }
        ProcessStructure sourceStructure = loadProcessStructure(
                sourceProcessId, activityDao, materialDao);
        Collection<BigDecimal> processProducts = loadProcessProducts(
                sourceProcessId, finishedProductDao);
        BigDecimal newProcessId = versionProcess(
                sourceProcessId, processCode, sourceProcess.asString("DESCRABREV"),
                processProducts);
        ProcessStructure newStructure = loadProcessStructure(
                newProcessId, activityDao, materialDao);
        return new VersionedProcess(
                newProcessId, sourceStructure, newStructure);
    }

    private BigDecimal findVersion(
            BigDecimal processId,
            JapeWrapper processDao) throws Exception {
        DynamicVO process = processDao.findByPK(processId);
        return process == null ? null : process.asBigDecimal("VERSAO");
    }

    private Collection<BigDecimal> loadProcessProducts(
            BigDecimal processId,
            JapeWrapper finishedProductDao) throws Exception {
        Set<BigDecimal> products = new LinkedHashSet<BigDecimal>();
        for (DynamicVO link : finishedProductDao.find("this.IDPROC = ?", processId)) {
            BigDecimal productCode = link.asBigDecimal("CODPRODPA");
            if (productCode != null) {
                products.add(productCode);
            }
        }
        if (products.isEmpty()) {
            throw new IllegalStateException(
                    "O processo produtivo de referencia nao possui produtos acabados.");
        }
        return products;
    }

    private BigDecimal versionProcess(
            BigDecimal sourceProcessId,
            BigDecimal processCode,
            String shortDescription,
            Collection<BigDecimal> products) throws Exception {
        Class<?> helperClass = loadRuntimeClass(
                "br.com.sankhya.mgeprod.model.helper.DuplicarProcessoProdutivoHelper");
        Object helper = invoke(helperClass.getMethod("getInstance"), null);
        Method versionMethod;
        try {
            versionMethod = helperClass.getMethod(
                    "versionar", BigDecimal.class, BigDecimal.class, String.class,
                    Collection.class, List.class);
        } catch (NoSuchMethodException exception) {
            throw incompatibleRuntime("versionar processo produtivo", exception);
        }
        Object result = invoke(versionMethod, helper,
                sourceProcessId, processCode, shortDescription, products, null);
        if (!(result instanceof BigDecimal)) {
            throw new IllegalStateException(
                    "O versionamento nativo nao retornou o identificador da nova versao.");
        }
        return (BigDecimal) result;
    }

    private ProcessStructure loadProcessStructure(
            BigDecimal processId,
            JapeWrapper activityDao,
            JapeWrapper materialDao) throws Exception {
        Map<BigDecimal, Integer> usageByActivity = new LinkedHashMap<BigDecimal, Integer>();
        Map<BigDecimal, Set<BigDecimal>> activitiesByProduct =
                new HashMap<BigDecimal, Set<BigDecimal>>();
        Map<String, List<DynamicVO>> materialsByProductActivity =
                new HashMap<String, List<DynamicVO>>();

        for (DynamicVO activity : activityDao.find("this.IDPROC = ?", processId)) {
            BigDecimal activityId = activity.asBigDecimal("IDEFX");
            if (activityId == null) {
                continue;
            }
            Collection<DynamicVO> materials = materialDao.find("this.IDEFX = ?", activityId);
            usageByActivity.put(activityId, materials.size());
            for (DynamicVO material : materials) {
                BigDecimal finishedProductCode = material.asBigDecimal("CODPRODPA");
                if (finishedProductCode == null) {
                    continue;
                }
                Set<BigDecimal> activities = activitiesByProduct.get(finishedProductCode);
                if (activities == null) {
                    activities = new LinkedHashSet<BigDecimal>();
                    activitiesByProduct.put(finishedProductCode, activities);
                }
                activities.add(activityId);
                String key = ProcessStructure.key(finishedProductCode, activityId);
                List<DynamicVO> productMaterials = materialsByProductActivity.get(key);
                if (productMaterials == null) {
                    productMaterials = new ArrayList<DynamicVO>();
                    materialsByProductActivity.put(key, productMaterials);
                }
                productMaterials.add(material);
            }
        }
        if (usageByActivity.isEmpty()) {
            throw new IllegalStateException(
                    "A nova versao nao possui atividade onde a composicao possa ser vinculada.");
        }
        return new ProcessStructure(
                usageByActivity, activitiesByProduct, materialsByProductActivity);
    }

    private int updateFinishedProductComposition(
            CompositionPlan plan,
            VersionedProcess process,
            Map<BigDecimal, ProductSettings> products,
            JapeWrapper productDao,
            JapeWrapper finishedProductDao,
            JapeWrapper materialDao) throws Exception {
        ProductionFormula formula = plan.getSourceFormula();
        BigDecimal finishedProductCode = numericCode(
                formula.getFinishedProductCode(), "produto acabado",
                formula.getLineNumber());
        ProductSettings finishedProduct = products.get(finishedProductCode);
        ensureFinishedProductLink(
                process.getNewProcessId(), finishedProductCode, finishedProductDao);

        BigDecimal activityId = process.getNewStructure().resolveActivity(finishedProductCode);
        Map<BigDecimal, DynamicVO> existingMaterials =
                process.getNewStructure().materialsFor(finishedProductCode, activityId);
        Map<BigDecimal, DynamicVO> sourceMaterials =
                process.getSourceStructure().materialsForExistingProduct(finishedProductCode);
        List<DynamicVO> packagingTemplates = findPackagingMaterials(
                sourceMaterials, products, productDao, formula.getLineNumber());
        if (packagingTemplates.size() < 2) {
            throw new IllegalStateException(
                    "O produto acabado " + finishedProductCode
                            + " nao possui etiqueta e sacaria na versao anterior do processo 1. "
                            + "Cadastre as duas embalagens antes de importar.");
        }

        Set<BigDecimal> retainedProductCodes = new HashSet<BigDecimal>();
        int importedComponents = 0;
        for (FormulaComponent component : plan.getDirectComponents()) {
            BigDecimal componentCode = numericCode(
                    component.getComponentCode(), "macronutriente",
                    component.getLineNumber());
            importedComponents += upsertMaterial(
                    finishedProductCode, finishedProduct, componentCode,
                    products.get(componentCode), component.getQuantity(),
                    ProductCategory.MACRONUTRIENT.getMaterialType(), activityId,
                    existingMaterials, process.getNewStructure(), materialDao);
            retainedProductCodes.add(componentCode);
        }

        if (plan.getIntermediateProduct().isPresent()) {
            IntermediateProductAllocation allocation = plan.getIntermediateProduct().get();
            BigDecimal microProductCode = numericCode(
                    allocation.getProductCode(), "PI de micronutrientes",
                    formula.getLineNumber());
            importedComponents += upsertMaterial(
                    finishedProductCode, finishedProduct, microProductCode,
                    products.get(microProductCode), allocation.getQuantity(),
                    MICRO_MATERIAL_TYPE, activityId, existingMaterials,
                    process.getNewStructure(), materialDao);
            retainedProductCodes.add(microProductCode);
        }

        for (DynamicVO packaging : packagingTemplates) {
            BigDecimal packagingCode = requiredNumber(packaging, "CODPRODMP");
            retainedProductCodes.add(packagingCode);
            if (existingMaterials.containsKey(packagingCode)) {
                materialDao.prepareToUpdate(existingMaterials.get(packagingCode))
                        .set("QTDMISTURA", BigDecimal.ONE)
                        .set("CODVOL", UNIT)
                        .set("AD_TIPOMP", PACKAGING_MATERIAL_TYPE)
                        .update();
                importedComponents++;
                continue;
            }
            loadProductSettings(
                    packagingCode, packagingCode.toPlainString(), "Embalagem do PA",
                    formula.getLineNumber(), products, productDao);
            insertPackagingFromPreviousVersion(
                    packaging, finishedProductCode, finishedProduct,
                    products.get(packagingCode), activityId,
                    process.getNewStructure(), materialDao);
            importedComponents++;
        }

        removeOmittedComponents(
                finishedProductCode, activityId, existingMaterials,
                retainedProductCodes, materialDao);
        return importedComponents;
    }

    private int updateMicroComposition(
            CompositionPlan plan,
            VersionedProcess process,
            Map<BigDecimal, ProductSettings> products,
            JapeWrapper finishedProductDao,
            JapeWrapper materialDao) throws Exception {
        if (process == null || !plan.getIntermediateProduct().isPresent()) {
            return 0;
        }
        ProductionFormula formula = plan.getSourceFormula();
        IntermediateProductAllocation allocation = plan.getIntermediateProduct().get();
        BigDecimal microProductCode = numericCode(
                allocation.getProductCode(), "PI de micronutrientes",
                formula.getLineNumber());
        ProductSettings microProduct = products.get(microProductCode);
        ensureFinishedProductLink(
                process.getNewProcessId(), microProductCode, finishedProductDao);

        BigDecimal activityId = process.getNewStructure().resolveActivity(microProductCode);
        Map<BigDecimal, DynamicVO> existingMaterials =
                process.getNewStructure().materialsFor(microProductCode, activityId);
        Set<BigDecimal> retainedProductCodes = new HashSet<BigDecimal>();
        int importedComponents = 0;
        for (FormulaComponent component : plan.getNormalizedMicronutrients()) {
            BigDecimal componentCode = numericCode(
                    component.getComponentCode(), "micronutriente",
                    component.getLineNumber());
            importedComponents += upsertMaterial(
                    microProductCode, microProduct, componentCode,
                    products.get(componentCode), component.getQuantity(),
                    MICRO_MATERIAL_TYPE, activityId, existingMaterials,
                    process.getNewStructure(), materialDao);
            retainedProductCodes.add(componentCode);
        }
        removeOmittedComponents(
                microProductCode, activityId, existingMaterials,
                retainedProductCodes, materialDao);
        return importedComponents;
    }

    private int upsertMaterial(
            BigDecimal finishedProductCode,
            ProductSettings finishedProduct,
            BigDecimal componentCode,
            ProductSettings material,
            BigDecimal quantity,
            String materialType,
            BigDecimal activityId,
            Map<BigDecimal, DynamicVO> existingMaterials,
            ProcessStructure structure,
            JapeWrapper materialDao) throws Exception {
        if (material == null) {
            throw new IllegalStateException(
                    "Configuracao cadastral nao carregada para o produto "
                            + componentCode + ".");
        }
        DynamicVO existingMaterial = existingMaterials.get(componentCode);
        MaterialStockControlPolicy.Selection stockControl = stockControlPolicy.resolve(
                finishedProduct.getStockControlType(), material.getStockControlType(),
                existingMaterial == null
                        ? null : existingMaterial.asString("TIPOCONTROLEMP"),
                existingMaterial == null
                        ? null : existingMaterial.asString("CONTROLEMP"));
        if (existingMaterial != null) {
            materialDao.prepareToUpdate(existingMaterial)
                    .set("QTDMISTURA", quantity)
                    .set("AD_TIPOMP", materialType)
                    .set("TIPOCONTROLEMP", stockControl.getType())
                    .set("CONTROLEMP", stockControl.getControl())
                    .update();
            return 1;
        }

        FluidCreateVO create = materialDao.create()
                .set("IDEFX", activityId)
                .set("CODPRODPA", finishedProductCode)
                .set("CODPRODMP", componentCode)
                .set("QTDMISTURA", quantity)
                .set("CODVOL", material.getVolume())
                .set("TIPOCONTROLEMP", stockControl.getType())
                .set("CONTROLEMP", stockControl.getControl())
                .set("AD_TIPOMP", materialType)
                .set("TIPOQTD", "V")
                .set("TIPOUSOMP", "N")
                .set("FIXAQTDAPO", "N")
                .set("CODLOCALORIG", BigDecimal.ZERO)
                .set("CODLOCALBAIXA", BigDecimal.ZERO);
        String finishedProductControl = structure.controlFor(
                finishedProductCode, activityId);
        if (finishedProductControl != null) {
            create.set("CONTROLEPA", finishedProductControl);
        }
        create.save();
        return 1;
    }

    private List<DynamicVO> findPackagingMaterials(
            Map<BigDecimal, DynamicVO> sourceMaterials,
            Map<BigDecimal, ProductSettings> products,
            JapeWrapper productDao,
            int lineNumber) throws Exception {
        List<DynamicVO> packaging = new ArrayList<DynamicVO>();
        for (Map.Entry<BigDecimal, DynamicVO> entry : sourceMaterials.entrySet()) {
            BigDecimal code = entry.getKey();
            loadProductSettings(
                    code, code.toPlainString(), "Componente da versao anterior",
                    lineNumber, products, productDao);
            ProductSettings settings = products.get(code);
            String existingType = entry.getValue().asString("AD_TIPOMP");
            if (settings.getCategory() == ProductCategory.PACKAGING
                    || PACKAGING_MATERIAL_TYPE.equalsIgnoreCase(trim(existingType))) {
                packaging.add(entry.getValue());
            }
        }
        return packaging;
    }

    private void insertPackagingFromPreviousVersion(
            DynamicVO source,
            BigDecimal finishedProductCode,
            ProductSettings finishedProduct,
            ProductSettings packaging,
            BigDecimal activityId,
            ProcessStructure structure,
            JapeWrapper materialDao) throws Exception {
        BigDecimal packagingCode = requiredNumber(source, "CODPRODMP");
        MaterialStockControlPolicy.Selection stockControl = stockControlPolicy.resolve(
                finishedProduct.getStockControlType(), packaging.getStockControlType(),
                source.asString("TIPOCONTROLEMP"), source.asString("CONTROLEMP"));
        FluidCreateVO create = materialDao.create()
                .set("IDEFX", activityId)
                .set("CODPRODPA", finishedProductCode)
                .set("CODPRODMP", packagingCode)
                .set("QTDMISTURA", BigDecimal.ONE)
                .set("CODVOL", UNIT)
                .set("TIPOCONTROLEMP", stockControl.getType())
                .set("CONTROLEMP", stockControl.getControl())
                .set("AD_TIPOMP", PACKAGING_MATERIAL_TYPE)
                .set("TIPOQTD", valueOrDefault(source.asString("TIPOQTD"), "V"))
                .set("TIPOUSOMP", valueOrDefault(source.asString("TIPOUSOMP"), "N"))
                .set("FIXAQTDAPO", valueOrDefault(source.asString("FIXAQTDAPO"), "N"))
                .set("CODLOCALORIG", valueOrZero(source.asBigDecimal("CODLOCALORIG")))
                .set("CODLOCALBAIXA", valueOrZero(source.asBigDecimal("CODLOCALBAIXA")));
        String finishedProductControl = trim(source.asString("CONTROLEPA"));
        if (finishedProductControl == null) {
            finishedProductControl = structure.controlFor(finishedProductCode, activityId);
        }
        if (finishedProductControl != null) {
            create.set("CONTROLEPA", finishedProductControl);
        }
        create.save();
    }

    private void ensureFinishedProductLink(
            BigDecimal processId,
            BigDecimal productCode,
            JapeWrapper finishedProductDao) throws Exception {
        DynamicVO existing = finishedProductDao.findOne(
                "this.IDPROC = ? AND this.CODPRODPA = ?", processId, productCode);
        if (existing == null) {
            finishedProductDao.create()
                    .set("IDPROC", processId)
                    .set("CODPRODPA", productCode)
                    .save();
        }
    }

    private void removeOmittedComponents(
            BigDecimal productCode,
            BigDecimal activityId,
            Map<BigDecimal, DynamicVO> existingMaterials,
            Set<BigDecimal> importedProductCodes,
            JapeWrapper materialDao) throws Exception {
        for (BigDecimal existingProductCode : existingMaterials.keySet()) {
            if (!importedProductCodes.contains(existingProductCode)) {
                materialDao.deleteByCriteria(
                        "this.IDEFX = ? AND this.CODPRODPA = ? AND this.CODPRODMP = ?",
                        activityId, productCode, existingProductCode);
            }
        }
    }

    private void validateProcess(
            BigDecimal processId,
            JdbcWrapper jdbc,
            EntityFacade facade) throws Exception {
        Class<?> validatorClass = loadRuntimeClass(
                "br.com.sankhya.mgeprod.model.helper.ValidadorProcessoProdutivo");
        Constructor<?> constructor;
        try {
            constructor = validatorClass.getConstructor(
                    BigDecimal.class, JdbcWrapper.class, EntityFacade.class);
        } catch (NoSuchMethodException exception) {
            throw incompatibleRuntime("validar processo produtivo", exception);
        }
        Object validator = constructor.newInstance(processId, jdbc, facade);
        Object validation = invoke(
                validatorClass.getMethod("validaComposicaoProduto"), validator);
        Object errorsObject = invoke(
                validation.getClass().getMethod("getErros"), validation);
        if (!(errorsObject instanceof List) || ((List<?>) errorsObject).isEmpty()) {
            return;
        }
        List<?> errors = (List<?>) errorsObject;
        StringBuilder message = new StringBuilder(
                "A versao foi revertida porque a validacao nativa encontrou: ");
        int limit = Math.min(errors.size(), 5);
        for (int index = 0; index < limit; index++) {
            if (index > 0) {
                message.append(" | ");
            }
            Object error = errors.get(index);
            try {
                message.append(String.valueOf(invoke(
                        error.getClass().getMethod("getDescricao"), error)));
            } catch (NoSuchMethodException exception) {
                message.append(String.valueOf(error));
            }
        }
        if (errors.size() > limit) {
            message.append(" | e mais ").append(errors.size() - limit).append(" ocorrencia(s)");
        }
        throw new IllegalStateException(message.toString());
    }

    private void deployProcess(BigDecimal processId, JdbcWrapper jdbc) throws Exception {
        Class<?> helperClass = loadRuntimeClass(
                "br.com.sankhya.mgeprod.model.helper.ControleProcessoHelper");
        Constructor<?> constructor;
        try {
            constructor = helperClass.getConstructor(JdbcWrapper.class);
        } catch (NoSuchMethodException exception) {
            throw incompatibleRuntime("publicar processo produtivo", exception);
        }
        Object helper = constructor.newInstance(jdbc);
        invoke(helperClass.getMethod("deployProcesso", BigDecimal.class), helper, processId);
    }

    private void updateProductionFormulas(
            BigDecimal processId,
            BigDecimal processCode,
            JdbcWrapper jdbc) throws Exception {
        Class<?> helperClass = loadRuntimeClass(
                "br.com.sankhya.mgeprod.model.helper.ProcessoProdutivoHelper");
        Object helper = invoke(helperClass.getMethod("getInstance"), null);

        try {
            Method method = helperClass.getMethod(
                    "atualizarFormulasProducao",
                    JdbcWrapper.class, BigDecimal.class, BigDecimal.class);
            invoke(method, helper, jdbc, processId, processCode);
            return;
        } catch (NoSuchMethodException ignored) {
            // Versoes mais novas podem expor somente o overload com a flag.
        }

        try {
            Method method = helperClass.getMethod(
                    "atualizarFormulasProducao",
                    JdbcWrapper.class, BigDecimal.class, BigDecimal.class, Boolean.class);
            invoke(method, helper, jdbc, processId, processCode, Boolean.FALSE);
            return;
        } catch (NoSuchMethodException ignored) {
            // Compatibilidade defensiva para runtime que use boolean primitivo.
        }

        try {
            Method method = helperClass.getMethod(
                    "atualizarFormulasProducao",
                    JdbcWrapper.class, BigDecimal.class, BigDecimal.class, Boolean.TYPE);
            invoke(method, helper, jdbc, processId, processCode, Boolean.FALSE);
        } catch (NoSuchMethodException exception) {
            throw incompatibleRuntime(
                    "atualizar formulas de producao. Assinaturas encontradas: "
                            + publicMethodSignatures(helperClass, "atualizarFormulasProducao"),
                    exception);
        }
    }

    private String publicMethodSignatures(Class<?> type, String methodName) {
        StringBuilder signatures = new StringBuilder();
        for (Method method : type.getMethods()) {
            if (!methodName.equals(method.getName())) {
                continue;
            }
            if (signatures.length() > 0) {
                signatures.append(" | ");
            }
            signatures.append(method.toGenericString());
        }
        return signatures.length() == 0 ? "nenhuma publica" : signatures.toString();
    }

    private Class<?> loadRuntimeClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException exception) {
            throw incompatibleRuntime("carregar " + className, exception);
        }
    }

    private Object invoke(Method method, Object target, Object... arguments) throws Exception {
        try {
            return method.invoke(target, arguments);
        } catch (InvocationTargetException exception) {
            Throwable cause = exception.getCause();
            if (cause instanceof Exception) {
                throw (Exception) cause;
            }
            throw exception;
        }
    }

    private IllegalStateException incompatibleRuntime(String operation, Exception cause) {
        return new IllegalStateException(
                "A API nativa do Sankhya Om 4.36b109 nao e compativel com a operacao de "
                        + operation + ". Atualize as dependencias de homologacao.", cause);
    }

    private BigDecimal numericCode(String code, String field, int line) {
        try {
            return new BigDecimal(code);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(
                    "Codigo de " + field + " deve ser numerico na linha " + line + ": " + code + ".",
                    exception);
        }
    }

    private BigDecimal requiredNumber(DynamicVO value, String property) {
        BigDecimal number = value.asBigDecimal(property);
        if (number == null) {
            throw new IllegalStateException(
                    "Campo obrigatorio nao informado no processo produtivo: " + property + ".");
        }
        return number;
    }

    private static BigDecimal valueOrZero(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    private static String valueOrDefault(String value, String fallback) {
        String normalized = trim(value);
        return normalized == null ? fallback : normalized;
    }

    private static String trim(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value.trim();
    }

    public static final class ImportResult {
        private final BigDecimal processId;
        private final BigDecimal version;
        private final BigDecimal microProcessId;
        private final BigDecimal microVersion;
        private final int formulaCount;
        private final int componentCount;

        private ImportResult(
                BigDecimal processId,
                BigDecimal version,
                BigDecimal microProcessId,
                BigDecimal microVersion,
                int formulaCount,
                int componentCount) {
            this.processId = processId;
            this.version = version;
            this.microProcessId = microProcessId;
            this.microVersion = microVersion;
            this.formulaCount = formulaCount;
            this.componentCount = componentCount;
        }

        public String toUserMessage() {
            StringBuilder message = new StringBuilder(
                    "Importacao concluida. Processo PA ").append(processId);
            if (version != null) {
                message.append(", versao ").append(version);
            }
            message.append(": ").append(formulaCount)
                    .append(" produto(s) e ").append(componentCount)
                    .append(" componente(s).");
            if (microProcessId != null) {
                message.append(" Processo Micro ").append(microProcessId);
                if (microVersion != null) {
                    message.append(", versao ").append(microVersion);
                }
                message.append('.');
            }
            return message.toString();
        }
    }

    private static final class ProductSettings {
        private final String stockControlType;
        private final String volume;
        private final ProductCategory category;

        private ProductSettings(
                String stockControlType,
                String volume,
                ProductCategory category) {
            this.stockControlType = stockControlType;
            this.volume = volume;
            this.category = category;
        }

        private String getStockControlType() {
            return stockControlType;
        }

        private String getVolume() {
            if (volume == null || volume.trim().isEmpty()) {
                throw new IllegalStateException(
                        "Unidade padrao nao informada no cadastro da materia-prima.");
            }
            return volume;
        }

        private ProductCategory getCategory() {
            return category;
        }
    }

    private static final class VersionedProcess {
        private final BigDecimal newProcessId;
        private final ProcessStructure sourceStructure;
        private final ProcessStructure newStructure;

        private VersionedProcess(
                BigDecimal newProcessId,
                ProcessStructure sourceStructure,
                ProcessStructure newStructure) {
            this.newProcessId = newProcessId;
            this.sourceStructure = sourceStructure;
            this.newStructure = newStructure;
        }

        private BigDecimal getNewProcessId() {
            return newProcessId;
        }

        private ProcessStructure getSourceStructure() {
            return sourceStructure;
        }

        private ProcessStructure getNewStructure() {
            return newStructure;
        }
    }

    private static final class ProcessStructure {
        private final Map<BigDecimal, Integer> usageByActivity;
        private final Map<BigDecimal, Set<BigDecimal>> activitiesByProduct;
        private final Map<String, List<DynamicVO>> materialsByProductActivity;

        private ProcessStructure(
                Map<BigDecimal, Integer> usageByActivity,
                Map<BigDecimal, Set<BigDecimal>> activitiesByProduct,
                Map<String, List<DynamicVO>> materialsByProductActivity) {
            this.usageByActivity = usageByActivity;
            this.activitiesByProduct = activitiesByProduct;
            this.materialsByProductActivity = materialsByProductActivity;
        }

        private Map<BigDecimal, DynamicVO> materialsFor(
                BigDecimal productCode,
                BigDecimal activityId) {
            Map<BigDecimal, DynamicVO> result =
                    new LinkedHashMap<BigDecimal, DynamicVO>();
            List<DynamicVO> materials = materialsByProductActivity.get(
                    key(productCode, activityId));
            if (materials == null) {
                return result;
            }
            for (DynamicVO material : materials) {
                BigDecimal materialCode = material.asBigDecimal("CODPRODMP");
                if (materialCode == null) {
                    continue;
                }
                if (result.containsKey(materialCode)) {
                    throw new IllegalStateException(
                            "O produto " + productCode + " possui o componente "
                                    + materialCode + " mais de uma vez com controles distintos. "
                                    + "O TXT nao identifica qual controle deve ser atualizado.");
                }
                result.put(materialCode, material);
            }
            return result;
        }

        private Map<BigDecimal, DynamicVO> materialsForExistingProduct(
                BigDecimal productCode) {
            Set<BigDecimal> activities = activitiesByProduct.get(productCode);
            if (activities == null || activities.isEmpty()) {
                return Collections.emptyMap();
            }
            if (activities.size() > 1) {
                throw new IllegalStateException(
                        "O produto " + productCode
                                + " possui materiais em mais de uma atividade na versao anterior.");
            }
            return materialsFor(productCode, activities.iterator().next());
        }

        private String controlFor(BigDecimal productCode, BigDecimal activityId) {
            List<DynamicVO> materials = materialsByProductActivity.get(
                    key(productCode, activityId));
            if (materials == null || materials.isEmpty()) {
                return null;
            }
            String selected = null;
            for (DynamicVO material : materials) {
                String current = material.asString("CONTROLEPA");
                if (current == null || current.trim().isEmpty()) {
                    continue;
                }
                current = current.trim();
                if (selected != null && !selected.equals(current)) {
                    throw new IllegalStateException(
                            "O produto " + productCode
                                    + " possui mais de um Controle (PA) na mesma atividade.");
                }
                selected = current;
            }
            return selected;
        }

        private static String key(BigDecimal productCode, BigDecimal activityId) {
            return productCode.stripTrailingZeros().toPlainString() + "|"
                    + activityId.stripTrailingZeros().toPlainString();
        }

        private BigDecimal resolveActivity(BigDecimal productCode) {
            Set<BigDecimal> current = activitiesByProduct.get(productCode);
            if (current != null && current.size() == 1) {
                return current.iterator().next();
            }
            if (current != null && current.size() > 1) {
                throw new IllegalStateException(
                        "O produto " + productCode
                                + " possui materiais em mais de uma atividade; ajuste manualmente o processo.");
            }

            BigDecimal selected = null;
            int greatestUsage = -1;
            boolean tied = false;
            for (Map.Entry<BigDecimal, Integer> entry : usageByActivity.entrySet()) {
                int usage = entry.getValue();
                if (usage > greatestUsage) {
                    selected = entry.getKey();
                    greatestUsage = usage;
                    tied = false;
                } else if (usage == greatestUsage) {
                    tied = true;
                }
            }
            if (selected == null || greatestUsage <= 0 || tied) {
                throw new IllegalStateException(
                        "Nao foi possivel identificar uma unica atividade de materiais para o novo produto "
                                + productCode + ".");
            }
            return selected;
        }
    }
}
