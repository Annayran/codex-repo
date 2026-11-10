package br.com.zaya.topnutri.importacaoformulas.sankhya.service;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.zaya.topnutri.importacaoformulas.model.dto.FormulaImportSimulation;
import br.com.zaya.topnutri.importacaoformulas.model.dto.ProductImportSimulation;
import br.com.zaya.topnutri.importacaoformulas.model.dto.SimulatedMaterial;
import br.com.zaya.topnutri.importacaoformulas.model.dto.ValidationIssue;
import br.com.zaya.topnutri.importacaoformulas.model.entity.CompositionPlan;
import br.com.zaya.topnutri.importacaoformulas.model.entity.FormulaComponent;
import br.com.zaya.topnutri.importacaoformulas.model.entity.IntermediateProductAllocation;
import br.com.zaya.topnutri.importacaoformulas.model.entity.ProductCategory;
import br.com.zaya.topnutri.importacaoformulas.model.entity.ProductionFormula;
import br.com.zaya.topnutri.importacaoformulas.model.service.FormulaCompositionPlanner;
import br.com.zaya.topnutri.importacaoformulas.model.service.FormulaPlanningException;
import br.com.zaya.topnutri.importacaoformulas.model.service.PrefixedMicroIntermediateProductResolver;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Consulta o Sankhya e monta a composicao futura sem realizar persistencia. */
public final class SankhyaFormulaPreviewService {
    private static final BigDecimal PA_PROCESS_CODE = BigDecimal.ONE;
    private static final BigDecimal MICRO_PROCESS_CODE = new BigDecimal("6");
    private static final String PACKAGING_TYPE = "EmbRotulo";

    public FormulaImportSimulation simulate(List<ProductionFormula> formulas) throws Exception {
        JapeSession.SessionHandle session = null;
        try {
            session = JapeSession.open();
            session.setCanTimeout(false);
            session.setFindersMaxRows(-1);

            JapeWrapper productDao = JapeFactory.dao("Produto");
            JapeWrapper processDao = JapeFactory.dao("ProcessoProdutivo");
            JapeWrapper finishedProductDao = JapeFactory.dao("ProdutoAcabado");
            JapeWrapper activityDao = JapeFactory.dao("Atividade");
            JapeWrapper materialDao = JapeFactory.dao("ListaMateriaisAtividade");

            Map<BigDecimal, ProductSnapshot> products =
                    new HashMap<BigDecimal, ProductSnapshot>();
            List<ProductImportSimulation> simulations =
                    new ArrayList<ProductImportSimulation>();
            List<ValidationIssue> issues = new ArrayList<ValidationIssue>();
            boolean microProcessExists = !processDao.find(
                    "this.CODPRC = ?", MICRO_PROCESS_CODE).isEmpty();

            for (ProductionFormula formula : formulas) {
                simulations.add(simulateFormula(
                        formula, microProcessExists, products, issues,
                        productDao, processDao, finishedProductDao,
                        activityDao, materialDao));
            }
            return new FormulaImportSimulation(simulations, issues);
        } finally {
            if (session != null) {
                JapeSession.close(session);
            }
        }
    }

    private ProductImportSimulation simulateFormula(
            ProductionFormula formula,
            boolean microProcessExists,
            final Map<BigDecimal, ProductSnapshot> products,
            List<ValidationIssue> issues,
            JapeWrapper productDao,
            JapeWrapper processDao,
            JapeWrapper finishedProductDao,
            JapeWrapper activityDao,
            JapeWrapper materialDao) throws Exception {
        BigDecimal finishedProductCode = numericCode(
                formula.getFinishedProductCode(), "produto acabado",
                formula.getLineNumber());
        ProductSnapshot finishedProduct = loadProduct(
                finishedProductCode, products, productDao);
        if (finishedProduct == null) {
            issue(issues, formula.getLineNumber(), "PRODUCT_NOT_FOUND",
                    "Produto acabado " + formula.getFinishedProductCode()
                            + " não cadastrado no Sankhya.");
        }

        int initialIssueCount = issues.size();
        for (FormulaComponent component : formula.getComponents()) {
            BigDecimal code = numericCode(
                    component.getComponentCode(), "componente",
                    component.getLineNumber());
            ProductSnapshot material = loadProduct(code, products, productDao);
            if (material == null) {
                issue(issues, component.getLineNumber(), "PRODUCT_NOT_FOUND",
                        "Componente " + component.getComponentCode()
                                + " não cadastrado no Sankhya.");
            } else if (material.getCategory() != ProductCategory.MACRONUTRIENT
                    && material.getCategory() != ProductCategory.MICRONUTRIENT) {
                issue(issues, component.getLineNumber(), "INVALID_CATEGORY",
                        "Componente " + component.getComponentCode()
                                + " deve estar classificado como Macro ou Micro em TGFPRO.AD_CATEGORIA.");
            }
        }

        CompositionPlan plan = null;
        if (issues.size() == initialIssueCount) {
            FormulaCompositionPlanner planner = new FormulaCompositionPlanner(
                    code -> {
                        ProductSnapshot product = products.get(new BigDecimal(code));
                        return product == null
                                ? Optional.<ProductCategory>empty()
                                : Optional.ofNullable(product.getCategory());
                    },
                    new PrefixedMicroIntermediateProductResolver());
            try {
                plan = planner.plan(formula);
            } catch (FormulaPlanningException exception) {
                issue(issues, formula.getLineNumber(), "SIMULATION_RULE", exception.getMessage());
            }
        }

        List<SimulatedMaterial> finishedMaterials =
                new ArrayList<SimulatedMaterial>();
        List<SimulatedMaterial> microMaterials =
                new ArrayList<SimulatedMaterial>();
        String microCodeText = null;
        String microDescription = null;
        BigDecimal microQuantity = null;

        if (plan != null) {
            for (FormulaComponent component : plan.getDirectComponents()) {
                ProductSnapshot material = products.get(new BigDecimal(component.getComponentCode()));
                finishedMaterials.add(material(
                        "MACRO", component.getComponentCode(), material,
                        component.getQuantity(), component.getQuantity(), "TXT Optimal"));
            }
            if (plan.getIntermediateProduct().isPresent()) {
                IntermediateProductAllocation allocation = plan.getIntermediateProduct().get();
                microCodeText = allocation.getProductCode();
                microQuantity = allocation.getQuantity();
                BigDecimal microCode = new BigDecimal(microCodeText);
                ProductSnapshot microProduct = loadProduct(microCode, products, productDao);
                if (microProduct == null) {
                    issue(issues, formula.getLineNumber(), "MICRO_PRODUCT_NOT_FOUND",
                            "PI/Batida " + microCodeText + " não cadastrado no Sankhya.");
                } else {
                    microDescription = microProduct.getDescription();
                    finishedMaterials.add(material(
                            "PI_MICRO", microCodeText, microProduct,
                            allocation.getQuantity(), null, "Calculado dos micros"));
                }
                if (!microProcessExists) {
                    issue(issues, formula.getLineNumber(), "MICRO_PROCESS_NOT_FOUND",
                            "Processo produtivo 6 (Produção - Micro) não localizado.");
                }
                for (int index = 0; index < plan.getNormalizedMicronutrients().size(); index++) {
                    FormulaComponent normalized = plan.getNormalizedMicronutrients().get(index);
                    FormulaComponent source = plan.getMicronutrients().get(index);
                    ProductSnapshot material = products.get(
                            new BigDecimal(normalized.getComponentCode()));
                    microMaterials.add(material(
                            "MICRO", normalized.getComponentCode(), material,
                            normalized.getQuantity(), source.getQuantity(), "TXT normalizado"));
                }
            }
        }

        DynamicVO previousProcess = findProductProcess(
                finishedProductCode, PA_PROCESS_CODE,
                finishedProductDao, processDao);
        if (previousProcess == null) {
            issue(issues, formula.getLineNumber(), "PA_PROCESS_NOT_FOUND",
                    "PA " + formula.getFinishedProductCode()
                            + " não possui versão anterior no processo produtivo 1.");
        } else {
            List<SimulatedMaterial> packaging = findPackaging(
                    finishedProductCode, previousProcess.asBigDecimal("IDPROC"),
                    formula.getLineNumber(), products, issues,
                    productDao, activityDao, materialDao);
            finishedMaterials.addAll(packaging);
            if (packaging.size() < 2) {
                issue(issues, formula.getLineNumber(), "PACKAGING_NOT_FOUND",
                        "A versão anterior do PA deve possuir etiqueta e sacaria.");
            }
        }

        return new ProductImportSimulation(
                formula.getLineNumber(), formula.getFinishedProductCode(),
                finishedProduct == null ? formula.getDescription() : finishedProduct.getDescription(),
                formula.getOutputQuantity(), microCodeText, microDescription,
                microQuantity, finishedMaterials, microMaterials);
    }

    private List<SimulatedMaterial> findPackaging(
            BigDecimal finishedProductCode,
            BigDecimal processId,
            int lineNumber,
            Map<BigDecimal, ProductSnapshot> products,
            List<ValidationIssue> issues,
            JapeWrapper productDao,
            JapeWrapper activityDao,
            JapeWrapper materialDao) throws Exception {
        Map<BigDecimal, SimulatedMaterial> packaging =
                new LinkedHashMap<BigDecimal, SimulatedMaterial>();
        int materialActivities = 0;
        for (DynamicVO activity : activityDao.find("this.IDPROC = ?", processId)) {
            BigDecimal activityId = activity.asBigDecimal("IDEFX");
            if (activityId == null) {
                continue;
            }
            List<DynamicVO> rows = new ArrayList<DynamicVO>(materialDao.find(
                    "this.IDEFX = ? AND this.CODPRODPA = ?",
                    activityId, finishedProductCode));
            if (!rows.isEmpty()) {
                materialActivities++;
            }
            for (DynamicVO row : rows) {
                BigDecimal materialCode = row.asBigDecimal("CODPRODMP");
                if (materialCode == null) {
                    continue;
                }
                ProductSnapshot product = loadProduct(materialCode, products, productDao);
                String materialType = trim(row.asString("AD_TIPOMP"));
                boolean isPackaging = product != null
                        && product.getCategory() == ProductCategory.PACKAGING;
                if (!isPackaging && !PACKAGING_TYPE.equalsIgnoreCase(materialType)) {
                    continue;
                }
                if (packaging.containsKey(materialCode)) {
                    issue(issues, lineNumber, "DUPLICATE_PACKAGING",
                            "Embalagem " + materialCode
                                    + " aparece mais de uma vez na versão anterior do PA.");
                    continue;
                }
                packaging.put(materialCode, material(
                        "EMBALAGEM", materialCode.toPlainString(), product,
                        BigDecimal.ONE, null, "Versão anterior"));
            }
        }
        if (materialActivities > 1) {
            issue(issues, lineNumber, "MULTIPLE_MATERIAL_ACTIVITIES",
                    "O PA possui materiais em mais de uma atividade na versão anterior.");
        }
        return new ArrayList<SimulatedMaterial>(packaging.values());
    }

    private DynamicVO findProductProcess(
            BigDecimal productCode,
            BigDecimal expectedProcessCode,
            JapeWrapper finishedProductDao,
            JapeWrapper processDao) throws Exception {
        List<DynamicVO> candidates = new ArrayList<DynamicVO>();
        for (DynamicVO link : finishedProductDao.find(
                "this.CODPRODPA = ?", productCode)) {
            BigDecimal processId = link.asBigDecimal("IDPROC");
            if (processId == null) {
                continue;
            }
            DynamicVO process = processDao.findByPK(processId);
            if (process != null && expectedProcessCode.compareTo(
                    process.asBigDecimal("CODPRC")) == 0) {
                candidates.add(process);
            }
        }
        if (candidates.isEmpty()) {
            return null;
        }
        List<DynamicVO> preferred = new ArrayList<DynamicVO>();
        for (DynamicVO process : candidates) {
            if ("S".equalsIgnoreCase(process.asString("PADRAO"))) {
                preferred.add(process);
            }
        }
        if (preferred.isEmpty()) {
            preferred.addAll(candidates);
        }
        Collections.sort(preferred, new Comparator<DynamicVO>() {
            @Override
            public int compare(DynamicVO left, DynamicVO right) {
                return valueOrZero(right.asBigDecimal("VERSAO")).compareTo(
                        valueOrZero(left.asBigDecimal("VERSAO")));
            }
        });
        return preferred.get(0);
    }

    private ProductSnapshot loadProduct(
            BigDecimal code,
            Map<BigDecimal, ProductSnapshot> products,
            JapeWrapper productDao) throws Exception {
        if (products.containsKey(code)) {
            return products.get(code);
        }
        DynamicVO product = productDao.findByPK(code);
        if (product == null) {
            return null;
        }
        ProductCategory category = null;
        String categoryCode = trim(product.asString("AD_CATEGORIA"));
        if (categoryCode != null) {
            category = ProductCategory.fromCode(categoryCode).orElse(null);
        }
        ProductSnapshot snapshot = new ProductSnapshot(
                valueOrDefault(product.asString("DESCRPROD"), code.toPlainString()),
                valueOrDefault(product.asString("CODVOL"), "KG"), category);
        products.put(code, snapshot);
        return snapshot;
    }

    private SimulatedMaterial material(
            String role,
            String code,
            ProductSnapshot product,
            BigDecimal quantity,
            BigDecimal sourceQuantity,
            String source) {
        return new SimulatedMaterial(
                role, code,
                product == null ? "Cadastro não localizado" : product.getDescription(),
                quantity, sourceQuantity,
                product == null ? "" : product.getVolume(), source);
    }

    private BigDecimal numericCode(String code, String label, int line) {
        try {
            return new BigDecimal(code);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(
                    "Código de " + label + " inválido na linha " + line + ": " + code + ".",
                    exception);
        }
    }

    private void issue(
            List<ValidationIssue> issues,
            int line,
            String code,
            String message) {
        issues.add(new ValidationIssue(line, code, message));
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

    private static final class ProductSnapshot {
        private final String description;
        private final String volume;
        private final ProductCategory category;

        private ProductSnapshot(
                String description,
                String volume,
                ProductCategory category) {
            this.description = description;
            this.volume = volume;
            this.category = category;
        }

        private String getDescription() { return description; }
        private String getVolume() { return volume; }
        private ProductCategory getCategory() { return category; }
    }
}
