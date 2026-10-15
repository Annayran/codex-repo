package org.ImportadorPedido.service;

import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import com.sankhya.util.BigDecimalUtil;
import org.ImportadorPedido.helper.DecimalImportHelper;
import org.ImportadorPedido.helper.ImportadorLogger;
import org.ImportadorPedido.helper.RegistrarLog;
import org.ImportadorPedido.helper.SankhyaHelper;
import org.ImportadorPedido.models.ItemImportado;
import org.ImportadorPedido.models.ResultadoReconciliacaoProduto;
import org.ImportadorPedido.repository.ProdutoRepository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class NotaGeracaoService {
    private final EntityFacade dwFacade = EntityFacadeFactory.getDWFFacade();
    private final JdbcWrapper jdbc = dwFacade.getJdbcWrapper();
    private final SankhyaHelper helper = new SankhyaHelper();
    private final ProdutoRepository produtoRepository = new ProdutoRepository(jdbc);
    private static final Logger LOGGER = Logger.getLogger(NotaGeracaoService.class.getName());
    private static final boolean LOG_DETALHE_ITEM = Boolean.parseBoolean(System.getProperty("importador.log.item.detalhe", "false"));
    private final Map<String, List<ProdutoRepository.ProdutoInfo>> cacheProdutoPorReferencia = new HashMap<>();
    private final Map<BigDecimal, ProdutoRepository.ProdutoInfo> cacheProdutoPorCod = new HashMap<>();

    public void gerar(ContextoAcao ctx) throws Exception {
        RegistrarLog registrarLog = new RegistrarLog();
        StringBuilder retornoFinal = new StringBuilder();
        ResultSet regPedSQL = null;
        long inicio = System.currentTimeMillis();
        int totalNotasGeradas = 0;
        int totalItensProcessados = 0;
        int totalItensCodCorrigido = 0;
        int totalItensIgnoradosPorDivergencia = 0;
        Map<String, Integer> motivosReconciliacao = new LinkedHashMap<>();

        ImportadorLogger.info(LOGGER, "GERACAO_START", "Inicio da geracao de notas de importacao", ImportadorLogger.ctx());

        try {
            NativeSql sql = new NativeSql(jdbc);
            sql.setReuseStatements(false);
            regPedSQL = sql.executeQuery("SELECT DISTINCT PVX, EMPRESA, CODPARC FROM AD_IMPREGPED WHERE NUNOTA IS NULL ORDER BY PVX, EMPRESA, CODPARC");

            while (regPedSQL.next()) {
                BigDecimal codEmp = regPedSQL.getBigDecimal("EMPRESA");
                String pvx = regPedSQL.getString("PVX");
                BigDecimal codParc = regPedSQL.getBigDecimal("CODPARC");

                if (codEmp == null) {
                    ImportadorLogger.warn(
                            LOGGER,
                            "GERACAO_CHAVE_INVALIDA",
                            "Registro ignorado por empresa nula",
                            ImportadorLogger.ctx("pvx", pvx, "codparc", codParc)
                    );
                    continue;
                }

                List<ItemImportado> itensPVX = carregarItensPendentes(pvx, codEmp, codParc);

                ImportadorLogger.info(
                        LOGGER,
                        "GERACAO_ITENS_DISPONIVEIS",
                        "Itens pendentes encontrados para chave de PVX",
                        ImportadorLogger.ctx("pvx", pvx, "empresa", codEmp, "codparc", codParc, "qtd_itens", itensPVX.size())
                );

                if (itensPVX.isEmpty()) {
                    ImportadorLogger.warn(
                            LOGGER,
                            "GERACAO_SEM_ITENS",
                            "Nenhum item pendente para a chave informada",
                            ImportadorLogger.ctx("pvx", pvx, "empresa", codEmp, "codparc", codParc)
                    );
                    continue;
                }

                logSinaisDivergenciaNaOrigem(pvx, codEmp, codParc, itensPVX);

                ItemImportado regPed = itensPVX.iterator().next();
                DynamicVO empVO = JapeFactory.dao(DynamicEntityNames.EMPRESA)
                        .findOne("this.CODEMP = ?", BigDecimalUtil.getValueOrZero(codEmp));

                if (empVO == null || empVO.asBigDecimal("AD_MODPEDCPIMP") == null) {
                    ImportadorLogger.error(
                            LOGGER,
                            "GERACAO_MODELO_NULO",
                            "Modelo de cabecalho nao configurado para empresa",
                            null,
                            ImportadorLogger.ctx("pvx", pvx, "empresa", codEmp, "codparc", codParc)
                    );
                    retornoFinal.append("Erro: modelo nao configurado para empresa ").append(codEmp).append(" (PVX ").append(pvx).append(")\n");
                    continue;
                }

                BigDecimal empModelCab = empVO.asBigDecimal("AD_MODPEDCPIMP");
                BigDecimal nuNota;
                int qtdItensSucesso = 0;
                int qtdItensErro = 0;
                String obs = regPed.getPa();

                try {
                    DynamicVO cabVO = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA).findByPK(empModelCab);
                    DynamicVO novaCabVO = cabVO.buildClone();
                    novaCabVO.setPrimaryKey(null);
                    novaCabVO.clearReferences();

                    novaCabVO.setProperty("DTNEG", new Timestamp(System.currentTimeMillis()));
                    novaCabVO.setProperty("OBSERVACAO", obs);
                    novaCabVO.setProperty("NUNOTA", null);
                    novaCabVO.setProperty("AD_INVOICE", pvx);
                    novaCabVO.setProperty("CODPARC", codParc);

                    dwFacade.createEntity(DynamicEntityNames.CABECALHO_NOTA, (EntityVO) novaCabVO);
                    nuNota = novaCabVO.asBigDecimal("NUNOTA");

                    ImportadorLogger.info(
                            LOGGER,
                            "GERACAO_CABECALHO_OK",
                            "Cabecalho da nota criado",
                            ImportadorLogger.ctx("pvx", pvx, "empresa", codEmp, "codparc", codParc, "nunota", nuNota)
                    );

                    for (ItemImportado item : itensPVX) {
                        int itemIndex = qtdItensSucesso + qtdItensErro + 1;
                        if (LOG_DETALHE_ITEM) {
                            ImportadorLogger.info(
                                    LOGGER,
                                    "GERACAO_ITEM_ORIGEM",
                                    "Item de origem pronto para insercao",
                                    construirContextoItem(pvx, codEmp, codParc, nuNota, item, itemIndex, itensPVX.size())
                            );
                        }

                        BigDecimal qty = item.getQty();
                        BigDecimal unitPrice = item.getUnitPrice();
                        BigDecimal totalPrice = item.getTotalPrice();
                        if (qty != null && unitPrice != null && totalPrice != null) {
                            BigDecimal totalCalculado = DecimalImportHelper.calcularTotalComparavel(qty, unitPrice);
                            BigDecimal totalInformado = DecimalImportHelper.normalizarTotalPrice(totalPrice);
                            if (totalCalculado.compareTo(totalInformado) != 0) {
                                Map<String, Object> ctxDivergencia = construirContextoItem(pvx, codEmp, codParc, nuNota, item, itemIndex, itensPVX.size());
                                ctxDivergencia.put("total_calculado_comparavel", totalCalculado);
                                ctxDivergencia.put("total_informado_comparavel", totalInformado);
                                ImportadorLogger.warn(
                                        LOGGER,
                                        "GERACAO_ITEM_DIVERGENCIA_TOTAL",
                                        "Total do item diverge de QTY x UNITPRICE na origem",
                                        ctxDivergencia
                                );
                            }
                        }

                        long inicioItem = System.currentTimeMillis();
                        try {
                            ResultadoReconciliacaoProduto reconciliacao = reconciliarProduto(item);

                            if (reconciliacao.deveIgnorar()) {
                                qtdItensErro++;
                                totalItensIgnoradosPorDivergencia++;
                                incrementarMotivo(motivosReconciliacao, reconciliacao.getMotivo());

                                Map<String, Object> ctxIgnorado = construirContextoItem(pvx, codEmp, codParc, nuNota, item, itemIndex, itensPVX.size(), System.currentTimeMillis() - inicioItem);
                                ctxIgnorado.put("motivo_reconciliacao", reconciliacao.getMotivo());
                                ctxIgnorado.put("causa_provavel", reconciliacao.getCausaProvavel());
                                ctxIgnorado.put("cod_origem", reconciliacao.getCodOriginal());
                                ctxIgnorado.put("referencia_origem", reconciliacao.getReferenciaInformada());

                                ImportadorLogger.warn(
                                        LOGGER,
                                        "GERACAO_ITEM_IGNORADO_RECONCILIACAO",
                                        "Item ignorado por inconsistencia entre COD e REFERENCIA na origem",
                                        ctxIgnorado
                                );

                                registrarLog.registrarLog(
                                        item.getPvx(),
                                        item.getEmpresa(),
                                        item.getCod(),
                                        item.getQty(),
                                        item.getUnit(),
                                        item.getUnitPrice(),
                                        item.getTotalPrice(),
                                        nuNota,
                                        "Erro",
                                        "Item ignorado na reconciliacao de produto | motivo=" + reconciliacao.getMotivo() +
                                                " | causa=" + reconciliacao.getCausaProvavel() +
                                                " | cod_origem=" + reconciliacao.getCodOriginal() +
                                                " | referencia=" + reconciliacao.getReferenciaInformada()
                                );
                                continue;
                            }

                            BigDecimal codParaInserir = reconciliacao.getCodResolvido();
                            if (reconciliacao.foiCorrigido()) {
                                totalItensCodCorrigido++;
                                incrementarMotivo(motivosReconciliacao, reconciliacao.getMotivo());

                                ImportadorLogger.warn(
                                        LOGGER,
                                        "GERACAO_ITEM_COD_CORRIGIDO",
                                        "COD do item ajustado por reconciliacao com REFERENCIA",
                                        ImportadorLogger.ctx(
                                                "pvx", pvx,
                                                "empresa", codEmp,
                                                "codparc", codParc,
                                                "nunota", nuNota,
                                                "item_index", itemIndex,
                                                "cod_origem", reconciliacao.getCodOriginal(),
                                                "cod_resolvido", reconciliacao.getCodResolvido(),
                                                "referencia", reconciliacao.getReferenciaInformada(),
                                                "motivo_reconciliacao", reconciliacao.getMotivo(),
                                                "causa_provavel", reconciliacao.getCausaProvavel()
                                        )
                                );
                            }

                            if (LOG_DETALHE_ITEM) {
                                ImportadorLogger.info(
                                        LOGGER,
                                        "GERACAO_ITEM_INSERT_START",
                                        "Iniciando insercao do item na nota",
                                        construirContextoItem(pvx, codEmp, codParc, nuNota, item, itemIndex, itensPVX.size())
                                );
                            }

                            helper.inseriItem(novaCabVO, codParaInserir, item.getQty(), item.getUnitPrice(), item.getTotalPrice());
                            qtdItensSucesso++;
                            totalItensProcessados++;
                            registrarLog.registrarLog(
                                    item.getPvx(),
                                    item.getEmpresa(),
                                    item.getCod(),
                                    item.getQty(),
                                    item.getUnit(),
                                    item.getUnitPrice(),
                                    item.getTotalPrice(),
                                    nuNota,
                                    "Processado",
                                    "Item inserido com sucesso"
                            );

                            if (LOG_DETALHE_ITEM) {
                                ImportadorLogger.info(
                                        LOGGER,
                                        "GERACAO_ITEM_INSERT_OK",
                                        "Item inserido com sucesso na nota",
                                        construirContextoItem(pvx, codEmp, codParc, nuNota, item, itemIndex, itensPVX.size(), System.currentTimeMillis() - inicioItem)
                                );
                            }
                        } catch (Exception eItem) {
                            qtdItensErro++;
                            String msgErro = eItem.getMessage() != null ? eItem.getMessage() : eItem.toString();
                            String origemErro = classificarOrigemErro(eItem);
                            Map<String, Object> ctxErro = construirContextoItem(
                                    pvx, codEmp, codParc, nuNota, item, itemIndex, itensPVX.size(), System.currentTimeMillis() - inicioItem
                            );
                            ctxErro.put("origem_erro", origemErro);
                            ctxErro.put("mensagem_erro", msgErro);

                            ImportadorLogger.error(
                                    LOGGER,
                                    "GERACAO_ITEM_ERRO",
                                    "Falha ao inserir item da nota",
                                    eItem,
                                    ctxErro
                            );
                            registrarLog.registrarLog(
                                    item.getPvx(),
                                    item.getEmpresa(),
                                    item.getCod(),
                                    item.getQty(),
                                    item.getUnit(),
                                    item.getUnitPrice(),
                                    item.getTotalPrice(),
                                    nuNota,
                                    "Erro",
                                    "Origem: " + origemErro + " | " + msgErro
                            );
                        }
                    }

                    ImportadorLogger.info(
                            LOGGER,
                            "GERACAO_RESUMO_ITENS",
                            "Resumo da insercao de itens da nota",
                            ImportadorLogger.ctx(
                                    "pvx", pvx,
                                    "empresa", codEmp,
                                    "codparc", codParc,
                                    "nunota", nuNota,
                                    "itens_total", itensPVX.size(),
                                    "itens_sucesso", qtdItensSucesso,
                                    "itens_erro", qtdItensErro,
                                    "itens_cod_corrigido", totalItensCodCorrigido,
                                    "itens_ignorados_reconciliacao", totalItensIgnoradosPorDivergencia,
                                    "motivos_reconciliacao", motivosReconciliacao
                            )
                    );

                    if (qtdItensSucesso == 0) {
                        JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA).delete(nuNota);
                        retornoFinal.append("Erro: todos os itens falharam no PVX ").append(pvx).append(". Nota descartada.\n");

                        ImportadorLogger.warn(
                                LOGGER,
                                "GERACAO_SEM_ITENS_SUCESSO",
                                "Cabecalho excluido porque nenhum item foi inserido",
                                ImportadorLogger.ctx("pvx", pvx, "empresa", codEmp, "codparc", codParc, "nunota", nuNota)
                        );
                        continue;
                    }

                    int qtdRegsAtualizar = atualizarNunotaPorChave(pvx, codEmp, codParc, nuNota);

                    ImportadorLogger.info(
                            LOGGER,
                            "GERACAO_ATUALIZACAO_NUNOTA",
                            "Registros de importacao atualizados com NUNOTA gerada",
                            ImportadorLogger.ctx(
                                    "pvx", pvx,
                                    "empresa", codEmp,
                                    "codparc", codParc,
                                    "nunota", nuNota,
                                    "qtd_registros_atualizado", qtdRegsAtualizar,
                                    "qtd_itens_processados", qtdItensSucesso
                            )
                    );

                    int qtdJsonMarcado = marcarJsonComoProcessadoAposGerarNota(pvx);
                    ImportadorLogger.info(
                            LOGGER,
                            "GERACAO_JSON_PROCESSADO",
                            "Tentativa de marcar JSON como processado apos gerar nota",
                            ImportadorLogger.ctx(
                                    "pvx", pvx,
                                    "nunota", nuNota,
                                    "qtd_json_marcado", qtdJsonMarcado,
                                    "status_destino", "S"
                            )
                    );

                    totalNotasGeradas++;
                    retornoFinal.append("Nota gerada com sucesso para PVX: ").append(pvx).append(" (NUNOTA ").append(nuNota).append(")\n");
                    ImportadorLogger.info(
                            LOGGER,
                            "GERACAO_NOTA_OK",
                            "Nota gerada com sucesso",
                            ImportadorLogger.ctx(
                                    "pvx", pvx,
                                    "empresa", codEmp,
                                    "codparc", codParc,
                                    "nunota", nuNota,
                                    "itens_sucesso", qtdItensSucesso,
                                    "itens_erro", qtdItensErro
                            )
                    );
                } catch (Exception eCab) {
                    String msgErro = eCab.getMessage() != null ? eCab.getMessage() : eCab.toString();
                    for (ItemImportado item : itensPVX) {
                        registrarLog.registrarLog(
                                item.getPvx(),
                                item.getEmpresa(),
                                item.getCod(),
                                item.getQty(),
                                item.getUnit(),
                                item.getUnitPrice(),
                                item.getTotalPrice(),
                                null,
                                "Com erro",
                                "Erro cabecalho: " + msgErro
                        );
                    }

                    retornoFinal.append("Erro no cabecalho do PVX ").append(pvx).append(". Processo abortado.\n");
                    ImportadorLogger.error(
                            LOGGER,
                            "GERACAO_CABECALHO_ERRO",
                            "Falha ao criar cabecalho da nota",
                            eCab,
                            ImportadorLogger.ctx("pvx", pvx, "empresa", codEmp, "codparc", codParc)
                    );
                }

                ctx.setMensagemRetorno(retornoFinal.toString());
            }
        } finally {
            if (regPedSQL != null) {
                regPedSQL.close();
            }
            jdbc.closeSession();

            ImportadorLogger.info(
                    LOGGER,
                    "GERACAO_FIM",
                    "Processamento de geracao de notas finalizado",
                    ImportadorLogger.ctx(
                            "notas_geradas", totalNotasGeradas,
                            "itens_processados", totalItensProcessados,
                            "itens_cod_corrigido", totalItensCodCorrigido,
                            "itens_ignorados_reconciliacao", totalItensIgnoradosPorDivergencia,
                            "motivos_reconciliacao", motivosReconciliacao,
                            "duracao_ms", System.currentTimeMillis() - inicio
                    )
            );
        }

        ctx.setMensagemRetorno(retornoFinal.toString());
    }

    private int marcarJsonComoProcessadoAposGerarNota(String pvx) throws Exception {
        JapeWrapper jsonDAO = JapeFactory.dao("AD_PRCSJSON");
        Collection<DynamicVO> jsonVOs = jsonDAO.find(
                "this.PROCESSADO = ? AND this.JSON LIKE ?",
                "I",
                "%\"PVX\":\"" + pvx + "\"%"
        );

        int qtdAtualizada = 0;
        for (DynamicVO jsonVo : jsonVOs) {
            FluidUpdateVO fluidUpd = jsonDAO.prepareToUpdate(jsonVo);
            fluidUpd.set("PROCESSADO", "S");
            fluidUpd.update();
            qtdAtualizada++;
        }

        if (qtdAtualizada == 0) {
            ImportadorLogger.warn(
                    LOGGER,
                    "GERACAO_JSON_NAO_ENCONTRADO",
                    "Nenhum JSON em status I foi encontrado para PVX apos gerar a nota",
                    ImportadorLogger.ctx("pvx", pvx)
            );
        } else if (qtdAtualizada > 1) {
            ImportadorLogger.warn(
                    LOGGER,
                    "GERACAO_JSON_MULTIPLO",
                    "Mais de um JSON foi marcado como S para o mesmo PVX",
                    ImportadorLogger.ctx("pvx", pvx, "qtd_json_marcado", qtdAtualizada)
            );
        }

        return qtdAtualizada;
    }

    private String classificarOrigemErro(Throwable throwable) {
        Throwable atual = throwable;
        while (atual != null) {
            String classe = atual.getClass().getName();
            if (classe.startsWith("br.com.sankhya")) {
                return "SANKHYA";
            }
            if (classe.startsWith("org.ImportadorPedido")) {
                return "IMPORTADOR";
            }
            atual = atual.getCause();
        }
        return "INDEFINIDA";
    }

    private void logSinaisDivergenciaNaOrigem(String pvx, BigDecimal codEmp, BigDecimal codParc, List<ItemImportado> itensPVX) {
        Map<BigDecimal, Set<String>> refsPorProduto = new HashMap<>();
        for (ItemImportado item : itensPVX) {
            BigDecimal cod = item.getCod();
            String ref = item.getReferencia();
            refsPorProduto.computeIfAbsent(cod, key -> new HashSet<>()).add(ref == null ? "" : ref);
        }

        for (Map.Entry<BigDecimal, Set<String>> entry : refsPorProduto.entrySet()) {
            if (entry.getValue().size() > 1) {
                ImportadorLogger.warn(
                        LOGGER,
                        "GERACAO_MULTI_REF_POR_COD",
                        "Mesmo COD com referencias diferentes na origem do PVX",
                        ImportadorLogger.ctx(
                                "pvx", pvx,
                                "empresa", codEmp,
                                "codparc", codParc,
                                "cod", entry.getKey(),
                                "referencias", entry.getValue(),
                                "qtd_itens", itensPVX.size()
                        )
                );
            }
        }
    }

    private Map<String, Object> construirContextoItem(
            String pvx,
            BigDecimal codEmp,
            BigDecimal codParc,
            BigDecimal nuNota,
            ItemImportado item,
            int index,
            int total
    ) {
        return construirContextoItem(pvx, codEmp, codParc, nuNota, item, index, total, null);
    }

    private Map<String, Object> construirContextoItem(
            String pvx,
            BigDecimal codEmp,
            BigDecimal codParc,
            BigDecimal nuNota,
            ItemImportado item,
            int index,
            int total,
            Long duracaoMs
    ) {
        Map<String, Object> ctxItem = ImportadorLogger.ctx(
                "pvx", pvx,
                "empresa", codEmp,
                "codparc", codParc,
                "nunota", nuNota,
                "item_index", index,
                "item_total", total,
                "cod", item.getCod(),
                "referencia", item.getReferencia(),
                "qty", item.getQty(),
                "unit", item.getUnit(),
                "unitprice", item.getUnitPrice(),
                "totalprice", item.getTotalPrice()
        );

        if (duracaoMs != null) {
            ctxItem.put("duracao_ms", duracaoMs);
        }

        return ctxItem;
    }

    private List<ItemImportado> carregarItensPendentes(String pvx, BigDecimal codEmp, BigDecimal codParc) throws Exception {
        NativeSql itensSql = new NativeSql(jdbc);
        itensSql.setReuseStatements(false);
        ResultSet rsItens = null;
        List<ItemImportado> itens = new ArrayList<>();

        try {
            rsItens = itensSql.executeQuery(
                    "SELECT PVX, EMPRESA, CODPARC, PA, REFERENCIA, COD, UNIT, UNITPRICE, QTY, TOTALPRICE " +
                            "FROM AD_IMPREGPED " +
                            "WHERE PVX = '" + escapeSqlLiteral(pvx) + "' " +
                            "AND EMPRESA = " + codEmp + " " +
                            "AND CODPARC = " + codParc + " " +
                            "AND NUNOTA IS NULL " +
                            "ORDER BY COD, REFERENCIA, QTY, UNITPRICE"
            );

            while (rsItens.next()) {
                itens.add(new ItemImportado(
                        rsItens.getString("PVX"),
                        rsItens.getBigDecimal("EMPRESA"),
                        rsItens.getString("PA"),
                        rsItens.getString("REFERENCIA"),
                        rsItens.getBigDecimal("COD"),
                        rsItens.getString("UNIT"),
                        rsItens.getBigDecimal("UNITPRICE"),
                        rsItens.getBigDecimal("QTY"),
                        rsItens.getBigDecimal("TOTALPRICE")
                ));
            }
        } finally {
            if (rsItens != null) {
                rsItens.close();
            }
        }

        return itens;
    }

    private int atualizarNunotaPorChave(String pvx, BigDecimal codEmp, BigDecimal codParc, BigDecimal nuNota) throws Exception {
        NativeSql updateSql = new NativeSql(jdbc);
        updateSql.setReuseStatements(false);

        boolean atualizado = updateSql.executeUpdate(
                "UPDATE AD_IMPREGPED SET NUNOTA = " + nuNota + " " +
                        "WHERE PVX = '" + escapeSqlLiteral(pvx) + "' " +
                        "AND EMPRESA = " + codEmp + " " +
                        "AND CODPARC = " + codParc + " " +
                        "AND NUNOTA IS NULL"
        );

        if (!atualizado) {
            return 0;
        }

        NativeSql countSql = new NativeSql(jdbc);
        countSql.setReuseStatements(false);
        ResultSet rsCount = null;
        try {
            rsCount = countSql.executeQuery(
                    "SELECT COUNT(1) AS QTD " +
                            "FROM AD_IMPREGPED " +
                            "WHERE PVX = '" + escapeSqlLiteral(pvx) + "' " +
                            "AND EMPRESA = " + codEmp + " " +
                            "AND CODPARC = " + codParc + " " +
                            "AND NUNOTA = " + nuNota
            );
            if (rsCount.next()) {
                return rsCount.getInt("QTD");
            }
            return 0;
        } finally {
            if (rsCount != null) {
                rsCount.close();
            }
        }
    }

    private ResultadoReconciliacaoProduto reconciliarProduto(ItemImportado item) throws Exception {
        String referencia = normalizarTexto(item.getReferencia());
        BigDecimal codOrigem = item.getCod();

        ProdutoRepository.ProdutoInfo produtoPorCod = buscarProdutoPorCodComCache(codOrigem);
        List<ProdutoRepository.ProdutoInfo> produtosPorReferencia = buscarProdutoPorReferenciaComCache(referencia);

        if (codOrigem == null) {
            if (produtosPorReferencia.size() == 1) {
                return ResultadoReconciliacaoProduto.corrigido(
                        null,
                        produtosPorReferencia.get(0).getCodProd(),
                        referencia,
                        "COD_AUSENTE_CORRIGIDO_POR_REFERENCIA",
                        "ERRO_USUARIO_PLANILHA"
                );
            }
            if (produtosPorReferencia.isEmpty()) {
                return ResultadoReconciliacaoProduto.ignorar(
                        null,
                        referencia,
                        "SEM_PRODUTO_PELA_REFERENCIA",
                        "CADASTRO_PRODUTO_OU_PLANILHA"
                );
            }
            return ResultadoReconciliacaoProduto.ignorar(
                    null,
                    referencia,
                    "REFERENCIA_AMBIGUA",
                    "CADASTRO_PRODUTO"
            );
        }

        if (produtoPorCod == null) {
            if (produtosPorReferencia.size() == 1) {
                return ResultadoReconciliacaoProduto.corrigido(
                        codOrigem,
                        produtosPorReferencia.get(0).getCodProd(),
                        referencia,
                        "COD_INEXISTENTE_CORRIGIDO_POR_REFERENCIA",
                        "ERRO_USUARIO_PLANILHA"
                );
            }
            if (produtosPorReferencia.isEmpty()) {
                return ResultadoReconciliacaoProduto.ignorar(
                        codOrigem,
                        referencia,
                        "COD_INEXISTENTE_E_SEM_REFERENCIA_VALIDA",
                        "CADASTRO_PRODUTO_OU_PLANILHA"
                );
            }
            return ResultadoReconciliacaoProduto.ignorar(
                    codOrigem,
                    referencia,
                    "COD_INEXISTENTE_E_REFERENCIA_AMBIGUA",
                    "CADASTRO_PRODUTO"
            );
        }

        if (referencia.isEmpty()) {
            return ResultadoReconciliacaoProduto.ok(codOrigem, referencia);
        }

        String referenciaDoCod = normalizarTexto(produtoPorCod.getReferencia());
        if (referencia.equals(referenciaDoCod)) {
            return ResultadoReconciliacaoProduto.ok(codOrigem, referencia);
        }

        // Regra principal: COD existente na base tem prioridade sobre referencia divergente/ambigua.
        if (produtosPorReferencia.isEmpty()) {
            return ResultadoReconciliacaoProduto.ok(
                    codOrigem,
                    referencia,
                    "COD_PRIORIZADO_REFERENCIA_SEM_MATCH",
                    "REFERENCIA_DIVERGENTE_NA_ORIGEM"
            );
        }

        if (produtosPorReferencia.size() == 1) {
            return ResultadoReconciliacaoProduto.ok(
                    codOrigem,
                    referencia,
                    "COD_PRIORIZADO_SOBRE_REFERENCIA_DIVERGENTE",
                    "REFERENCIA_APONTA_PARA_OUTRO_PRODUTO"
            );
        }

        return ResultadoReconciliacaoProduto.ok(
                codOrigem,
                referencia,
                "COD_PRIORIZADO_COM_REFERENCIA_AMBIGUA",
                "CADASTRO_COM_REFERENCIA_DUPLICADA"
        );
    }

    private ProdutoRepository.ProdutoInfo buscarProdutoPorCodComCache(BigDecimal cod) throws Exception {
        if (cod == null) {
            return null;
        }
        if (cacheProdutoPorCod.containsKey(cod)) {
            return cacheProdutoPorCod.get(cod);
        }
        ProdutoRepository.ProdutoInfo encontrado = produtoRepository.buscarPorCod(cod);
        cacheProdutoPorCod.put(cod, encontrado);
        return encontrado;
    }

    private List<ProdutoRepository.ProdutoInfo> buscarProdutoPorReferenciaComCache(String referencia) throws Exception {
        String chave = referencia == null ? "" : referencia;
        if (cacheProdutoPorReferencia.containsKey(chave)) {
            return cacheProdutoPorReferencia.get(chave);
        }
        List<ProdutoRepository.ProdutoInfo> encontrados = produtoRepository.buscarPorReferenciaNormalizada(chave);
        cacheProdutoPorReferencia.put(chave, encontrados);
        return encontrados;
    }

    private void incrementarMotivo(Map<String, Integer> agregador, String motivo) {
        String chave = motivo == null || motivo.trim().isEmpty() ? "NAO_INFORMADO" : motivo;
        agregador.put(chave, agregador.getOrDefault(chave, 0) + 1);
    }

    private String normalizarTexto(String valor) {
        return valor == null ? "" : valor.trim().toUpperCase();
    }

    private String escapeSqlLiteral(String value) {
        return value == null ? "" : value.replace("'", "''");
    }
}

