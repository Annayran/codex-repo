/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  br.com.sankhya.extensions.actionbutton.ContextoAcao
 *  br.com.sankhya.jape.EntityFacade
 *  br.com.sankhya.jape.dao.JdbcWrapper
 *  br.com.sankhya.jape.sql.NativeSql
 *  br.com.sankhya.jape.vo.DynamicVO
 *  br.com.sankhya.jape.vo.EntityVO
 *  br.com.sankhya.jape.wrapper.JapeFactory
 *  br.com.sankhya.jape.wrapper.JapeWrapper
 *  br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO
 *  br.com.sankhya.modelcore.util.EntityFacadeFactory
 *  com.sankhya.util.BigDecimalUtil
 */
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
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import com.sankhya.util.BigDecimalUtil;
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
import org.ImportadorPedido.helper.DecimalImportHelper;
import org.ImportadorPedido.helper.ImportadorLogger;
import org.ImportadorPedido.helper.RegistrarLog;
import org.ImportadorPedido.helper.SankhyaHelper;
import org.ImportadorPedido.models.ItemImportado;
import org.ImportadorPedido.models.ResultadoReconciliacaoProduto;
import org.ImportadorPedido.repository.ProdutoRepository;

public class NotaGeracaoService {
    private final EntityFacade dwFacade = EntityFacadeFactory.getDWFFacade();
    private final JdbcWrapper jdbc = this.dwFacade.getJdbcWrapper();
    private final SankhyaHelper helper = new SankhyaHelper();
    private final ProdutoRepository produtoRepository = new ProdutoRepository(this.jdbc);
    private static final Logger LOGGER = Logger.getLogger(NotaGeracaoService.class.getName());
    private static final boolean LOG_DETALHE_ITEM = Boolean.parseBoolean(System.getProperty("importador.log.item.detalhe", "false"));
    private final Map<String, List<ProdutoRepository.ProdutoInfo>> cacheProdutoPorReferencia = new HashMap<String, List<ProdutoRepository.ProdutoInfo>>();
    private final Map<BigDecimal, ProdutoRepository.ProdutoInfo> cacheProdutoPorCod = new HashMap<BigDecimal, ProdutoRepository.ProdutoInfo>();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void gerar(ContextoAcao ctx) throws Exception {
        LinkedHashMap<String, Integer> motivosReconciliacao;
        int totalItensIgnoradosPorDivergencia;
        int totalItensCodCorrigido;
        int totalItensProcessados;
        int totalNotasGeradas;
        long inicio;
        StringBuilder retornoFinal;
        block19: {
            RegistrarLog registrarLog = new RegistrarLog();
            retornoFinal = new StringBuilder();
            ResultSet regPedSQL = null;
            inicio = System.currentTimeMillis();
            totalNotasGeradas = 0;
            totalItensProcessados = 0;
            totalItensCodCorrigido = 0;
            totalItensIgnoradosPorDivergencia = 0;
            motivosReconciliacao = new LinkedHashMap<String, Integer>();
            ImportadorLogger.info(LOGGER, "GERACAO_START", "Inicio da geracao de notas de importacao", ImportadorLogger.ctx(new Object[0]));
            try {
                NativeSql sql = new NativeSql(this.jdbc);
                sql.setReuseStatements(false);
                regPedSQL = sql.executeQuery("SELECT DISTINCT PVX, EMPRESA, CODPARC FROM AD_IMPREGPED WHERE NUNOTA IS NULL ORDER BY PVX, EMPRESA, CODPARC");
                while (regPedSQL.next()) {
                    BigDecimal codEmp = regPedSQL.getBigDecimal("EMPRESA");
                    String pvx = regPedSQL.getString("PVX");
                    BigDecimal codParc = regPedSQL.getBigDecimal("CODPARC");
                    if (codEmp == null) {
                        ImportadorLogger.warn(LOGGER, "GERACAO_CHAVE_INVALIDA", "Registro ignorado por empresa nula", ImportadorLogger.ctx("pvx", pvx, "codparc", codParc));
                        continue;
                    }
                    List<ItemImportado> itensPVX = this.carregarItensPendentes(pvx, codEmp, codParc);
                    ImportadorLogger.info(LOGGER, "GERACAO_ITENS_DISPONIVEIS", "Itens pendentes encontrados para chave de PVX", ImportadorLogger.ctx("pvx", pvx, "empresa", codEmp, "codparc", codParc, "qtd_itens", itensPVX.size()));
                    if (itensPVX.isEmpty()) {
                        ImportadorLogger.warn(LOGGER, "GERACAO_SEM_ITENS", "Nenhum item pendente para a chave informada", ImportadorLogger.ctx("pvx", pvx, "empresa", codEmp, "codparc", codParc));
                        continue;
                    }
                    this.logSinaisDivergenciaNaOrigem(pvx, codEmp, codParc, itensPVX);
                    for (ItemImportado candidato : itensPVX) {
                        ResultadoReconciliacaoProduto resultado = this.reconciliarProduto(candidato);
                        if (resultado.deveIgnorar()) {
                            throw new Exception("PVX " + pvx + ": produto nao resolvido (COD " + candidato.getCod()
                                    + ", referencia " + candidato.getReferencia() + "): " + resultado.getMotivo());
                        }
                    }
                    ItemImportado regPed = itensPVX.iterator().next();
                    DynamicVO empVO = JapeFactory.dao((String)"Empresa").findOne("this.CODEMP = ?", new Object[]{BigDecimalUtil.getValueOrZero((BigDecimal)codEmp)});
                    if (empVO == null || empVO.asBigDecimal("AD_MODPEDCPIMP") == null) {
                        ImportadorLogger.error(LOGGER, "GERACAO_MODELO_NULO", "Modelo de cabecalho nao configurado para empresa", null, ImportadorLogger.ctx("pvx", pvx, "empresa", codEmp, "codparc", codParc));
                        retornoFinal.append("Erro: modelo nao configurado para empresa ").append(codEmp).append(" (PVX ").append(pvx).append(")\n");
                        continue;
                    }
                    BigDecimal empModelCab = empVO.asBigDecimal("AD_MODPEDCPIMP");
                    int qtdItensSucesso = 0;
                    int qtdItensErro = 0;
                    String obs = regPed.getPa();
                    try {
                        DynamicVO cabVO = JapeFactory.dao((String)"CabecalhoNota").findByPK(new Object[]{empModelCab});
                        DynamicVO novaCabVO = cabVO.buildClone();
                        novaCabVO.setPrimaryKey(null);
                        novaCabVO.clearReferences();
                        novaCabVO.setProperty("DTNEG", (Object)new Timestamp(System.currentTimeMillis()));
                        novaCabVO.setProperty("OBSERVACAO", (Object)obs);
                        novaCabVO.setProperty("NUNOTA", null);
                        novaCabVO.setProperty("AD_INVOICE", (Object)pvx);
                        novaCabVO.setProperty("CODPARC", (Object)codParc);
                        this.dwFacade.createEntity("CabecalhoNota", (EntityVO)novaCabVO);
                        BigDecimal nuNota = novaCabVO.asBigDecimal("NUNOTA");
                        ImportadorLogger.info(LOGGER, "GERACAO_CABECALHO_OK", "Cabecalho da nota criado", ImportadorLogger.ctx("pvx", pvx, "empresa", codEmp, "codparc", codParc, "nunota", nuNota));
                        for (ItemImportado item : itensPVX) {
                            BigDecimal totalInformado;
                            BigDecimal totalCalculado;
                            int itemIndex = qtdItensSucesso + qtdItensErro + 1;
                            if (LOG_DETALHE_ITEM) {
                                ImportadorLogger.info(LOGGER, "GERACAO_ITEM_ORIGEM", "Item de origem pronto para insercao", this.construirContextoItem(pvx, codEmp, codParc, nuNota, item, itemIndex, itensPVX.size()));
                            }
                            BigDecimal qty = item.getQty();
                            BigDecimal unitPrice = item.getUnitPrice();
                            BigDecimal totalPrice = item.getTotalPrice();
                            if (qty != null && unitPrice != null && totalPrice != null && (totalCalculado = DecimalImportHelper.calcularTotalComparavel(qty, unitPrice)).compareTo(totalInformado = totalPrice.setScale(2, java.math.RoundingMode.HALF_UP)) != 0) {
                                Map<String, Object> ctxDivergencia = this.construirContextoItem(pvx, codEmp, codParc, nuNota, item, itemIndex, itensPVX.size());
                                ctxDivergencia.put("total_calculado_comparavel", totalCalculado);
                                ctxDivergencia.put("total_informado_comparavel", totalInformado);
                                ImportadorLogger.warn(LOGGER, "GERACAO_ITEM_DIVERGENCIA_TOTAL", "Total do item diverge de QTY x UNITPRICE na origem", ctxDivergencia);
                                retornoFinal.append("Aviso: COD ").append(item.getCod()).append(" total informado ")
                                    .append(totalPrice).append(" difere de quantidade x unitario ").append(qty.multiply(unitPrice)).append(".\n");
                            }
                            long inicioItem = System.currentTimeMillis();
                            try {
                                ResultadoReconciliacaoProduto reconciliacao = this.reconciliarProduto(item);
                                if (reconciliacao.deveIgnorar()) {
                                    ++qtdItensErro;
                                    ++totalItensIgnoradosPorDivergencia;
                                    this.incrementarMotivo(motivosReconciliacao, reconciliacao.getMotivo());
                                    Map<String, Object> ctxIgnorado = this.construirContextoItem(pvx, codEmp, codParc, nuNota, item, itemIndex, itensPVX.size(), System.currentTimeMillis() - inicioItem);
                                    ctxIgnorado.put("motivo_reconciliacao", reconciliacao.getMotivo());
                                    ctxIgnorado.put("causa_provavel", reconciliacao.getCausaProvavel());
                                    ctxIgnorado.put("cod_origem", reconciliacao.getCodOriginal());
                                    ctxIgnorado.put("referencia_origem", reconciliacao.getReferenciaInformada());
                                    ImportadorLogger.warn(LOGGER, "GERACAO_ITEM_IGNORADO_RECONCILIACAO", "Item ignorado por inconsistencia entre COD e REFERENCIA na origem", ctxIgnorado);
                                    registrarLog.registrarLog(item.getPvx(), item.getEmpresa(), item.getCod(), item.getQty(), item.getUnit(), item.getUnitPrice(), item.getTotalPrice(), nuNota, "Erro", "Item ignorado na reconciliacao de produto | motivo=" + reconciliacao.getMotivo() + " | causa=" + reconciliacao.getCausaProvavel() + " | cod_origem=" + reconciliacao.getCodOriginal() + " | referencia=" + reconciliacao.getReferenciaInformada());
                                    continue;
                                }
                                BigDecimal codParaInserir = reconciliacao.getCodResolvido();
                                if (reconciliacao.foiCorrigido()) {
                                    ++totalItensCodCorrigido;
                                    this.incrementarMotivo(motivosReconciliacao, reconciliacao.getMotivo());
                                    ImportadorLogger.warn(LOGGER, "GERACAO_ITEM_COD_CORRIGIDO", "COD do item ajustado por reconciliacao com REFERENCIA", ImportadorLogger.ctx("pvx", pvx, "empresa", codEmp, "codparc", codParc, "nunota", nuNota, "item_index", itemIndex, "cod_origem", reconciliacao.getCodOriginal(), "cod_resolvido", reconciliacao.getCodResolvido(), "referencia", reconciliacao.getReferenciaInformada(), "motivo_reconciliacao", reconciliacao.getMotivo(), "causa_provavel", reconciliacao.getCausaProvavel()));
                                }
                                if (LOG_DETALHE_ITEM) {
                                    ImportadorLogger.info(LOGGER, "GERACAO_ITEM_INSERT_START", "Iniciando insercao do item na nota", this.construirContextoItem(pvx, codEmp, codParc, nuNota, item, itemIndex, itensPVX.size()));
                                }
                                this.helper.inseriItem(novaCabVO, codParaInserir, item.getQty(), item.getUnitPrice(), item.getTotalPrice());
                                ++qtdItensSucesso;
                                ++totalItensProcessados;
                                registrarLog.registrarLog(item.getPvx(), item.getEmpresa(), item.getCod(), item.getQty(), item.getUnit(), item.getUnitPrice(), item.getTotalPrice(), nuNota, "Processado", "Item inserido com sucesso");
                                if (!LOG_DETALHE_ITEM) continue;
                                ImportadorLogger.info(LOGGER, "GERACAO_ITEM_INSERT_OK", "Item inserido com sucesso na nota", this.construirContextoItem(pvx, codEmp, codParc, nuNota, item, itemIndex, itensPVX.size(), System.currentTimeMillis() - inicioItem));
                            }
                            catch (Exception eItem) {
                                ++qtdItensErro;
                                String msgErro = eItem.getMessage() != null ? eItem.getMessage() : eItem.toString();
                                String origemErro = this.classificarOrigemErro(eItem);
                                Map<String, Object> ctxErro = this.construirContextoItem(pvx, codEmp, codParc, nuNota, item, itemIndex, itensPVX.size(), System.currentTimeMillis() - inicioItem);
                                ctxErro.put("origem_erro", origemErro);
                                ctxErro.put("mensagem_erro", msgErro);
                                ImportadorLogger.error(LOGGER, "GERACAO_ITEM_ERRO", "Falha ao inserir item da nota", eItem, ctxErro);
                                registrarLog.registrarLog(item.getPvx(), item.getEmpresa(), item.getCod(), item.getQty(), item.getUnit(), item.getUnitPrice(), item.getTotalPrice(), nuNota, "Erro", "Origem: " + origemErro + " | " + msgErro);
                                throw eItem;
                            }
                        }
                        ImportadorLogger.info(LOGGER, "GERACAO_RESUMO_ITENS", "Resumo da insercao de itens da nota", ImportadorLogger.ctx("pvx", pvx, "empresa", codEmp, "codparc", codParc, "nunota", nuNota, "itens_total", itensPVX.size(), "itens_sucesso", qtdItensSucesso, "itens_erro", qtdItensErro, "itens_cod_corrigido", totalItensCodCorrigido, "itens_ignorados_reconciliacao", totalItensIgnoradosPorDivergencia, "motivos_reconciliacao", motivosReconciliacao));
                        if (qtdItensSucesso == 0) {
                            JapeFactory.dao((String)"CabecalhoNota").delete(new Object[]{nuNota});
                            retornoFinal.append("Erro: todos os itens falharam no PVX ").append(pvx).append(". Nota descartada.\n");
                            ImportadorLogger.warn(LOGGER, "GERACAO_SEM_ITENS_SUCESSO", "Cabecalho excluido porque nenhum item foi inserido", ImportadorLogger.ctx("pvx", pvx, "empresa", codEmp, "codparc", codParc, "nunota", nuNota));
                            continue;
                        }
                        this.helper.recalcularImpost(nuNota);
                        Collection<DynamicVO> itensGravados = JapeFactory.dao("ItemNota").find("NUNOTA = ?", nuNota);
                        if (itensGravados.size() != itensPVX.size()) {
                            throw new Exception("Quantidade de itens gravados diverge da importacao para nota " + nuNota);
                        }
                        for (DynamicVO persistido : itensGravados) {
                            BigDecimal quantidade = persistido.asBigDecimalOrZero("QTDNEG");
                            BigDecimal unitarioMoeda = persistido.asBigDecimalOrZero("VLRUNITMOE");
                            if (quantidade.multiply(unitarioMoeda).setScale(2, java.math.RoundingMode.HALF_UP)
                                    .compareTo(persistido.asBigDecimalOrZero("VLRTOTMOE").setScale(2, java.math.RoundingMode.HALF_UP)) != 0
                                    || (unitarioMoeda.signum() > 0 && persistido.asBigDecimalOrZero("VLRTOT").signum() <= 0)) {
                                throw new Exception("Totais inconsistentes apos totalizacao da nota " + nuNota);
                            }
                        }
                        DynamicVO cabGravado = JapeFactory.dao("CabecalhoNota").findByPK(nuNota);
                        if (cabGravado.asBigDecimalOrZero("VLRNOTA").signum() <= 0) {
                            throw new Exception("Total da nota nao calculado; conferir TOP/moeda/cotacao: " + nuNota);
                        }
                        int qtdRegsAtualizar = this.atualizarNunotaPorChave(pvx, codEmp, codParc, nuNota);
                        ImportadorLogger.info(LOGGER, "GERACAO_ATUALIZACAO_NUNOTA", "Registros de importacao atualizados com NUNOTA gerada", ImportadorLogger.ctx("pvx", pvx, "empresa", codEmp, "codparc", codParc, "nunota", nuNota, "qtd_registros_atualizado", qtdRegsAtualizar, "qtd_itens_processados", qtdItensSucesso));
                        int qtdJsonMarcado = this.marcarJsonComoProcessadoAposGerarNota(pvx);
                        ImportadorLogger.info(LOGGER, "GERACAO_JSON_PROCESSADO", "Tentativa de marcar JSON como processado apos gerar nota", ImportadorLogger.ctx("pvx", pvx, "nunota", nuNota, "qtd_json_marcado", qtdJsonMarcado, "status_destino", "S"));
                        ++totalNotasGeradas;
                        retornoFinal.append("Nota gerada com sucesso para PVX: ").append(pvx).append(" (NUNOTA ").append(nuNota).append(")\n");
                        ImportadorLogger.info(LOGGER, "GERACAO_NOTA_OK", "Nota gerada com sucesso", ImportadorLogger.ctx("pvx", pvx, "empresa", codEmp, "codparc", codParc, "nunota", nuNota, "itens_sucesso", qtdItensSucesso, "itens_erro", qtdItensErro));
                    }
                    catch (Exception eCab) {
                        String msgErro = eCab.getMessage() != null ? eCab.getMessage() : eCab.toString();
                        for (ItemImportado item : itensPVX) {
                            registrarLog.registrarLog(item.getPvx(), item.getEmpresa(), item.getCod(), item.getQty(), item.getUnit(), item.getUnitPrice(), item.getTotalPrice(), null, "Com erro", "Erro cabecalho: " + msgErro);
                        }
                        retornoFinal.append("Erro no cabecalho do PVX ").append(pvx).append(". Processo abortado.\n");
                        ImportadorLogger.error(LOGGER, "GERACAO_CABECALHO_ERRO", "Falha ao criar cabecalho da nota", eCab, ImportadorLogger.ctx("pvx", pvx, "empresa", codEmp, "codparc", codParc));
                        throw eCab;
                    }
                    ctx.setMensagemRetorno(retornoFinal.toString());
                }
                if (regPedSQL == null) break block19;
            }
            catch (Throwable throwable) {
                if (regPedSQL != null) {
                    regPedSQL.close();
                }
                this.jdbc.closeSession();
                ImportadorLogger.info(LOGGER, "GERACAO_FIM", "Processamento de geracao de notas finalizado", ImportadorLogger.ctx("notas_geradas", totalNotasGeradas, "itens_processados", totalItensProcessados, "itens_cod_corrigido", totalItensCodCorrigido, "itens_ignorados_reconciliacao", totalItensIgnoradosPorDivergencia, "motivos_reconciliacao", motivosReconciliacao, "duracao_ms", System.currentTimeMillis() - inicio));
                throw throwable;
            }
            regPedSQL.close();
        }
        this.jdbc.closeSession();
        ImportadorLogger.info(LOGGER, "GERACAO_FIM", "Processamento de geracao de notas finalizado", ImportadorLogger.ctx("notas_geradas", totalNotasGeradas, "itens_processados", totalItensProcessados, "itens_cod_corrigido", totalItensCodCorrigido, "itens_ignorados_reconciliacao", totalItensIgnoradosPorDivergencia, "motivos_reconciliacao", motivosReconciliacao, "duracao_ms", System.currentTimeMillis() - inicio));
        ctx.setMensagemRetorno(retornoFinal.toString());
    }

    private int marcarJsonComoProcessadoAposGerarNota(String pvx) throws Exception {
        JapeWrapper jsonDAO = JapeFactory.dao((String)"AD_PRCSJSON");
        Collection<DynamicVO> jsonVOs = jsonDAO.find("this.PROCESSADO = ? AND this.JSON LIKE ?", new Object[]{"I", "%\"PVX\":\"" + pvx + "\"%"});
        int qtdAtualizada = 0;
        for (DynamicVO jsonVo : jsonVOs) {
            FluidUpdateVO fluidUpd = jsonDAO.prepareToUpdate(jsonVo);
            fluidUpd.set("PROCESSADO", (Object)"S");
            fluidUpd.update();
            ++qtdAtualizada;
        }
        if (qtdAtualizada == 0) {
            ImportadorLogger.warn(LOGGER, "GERACAO_JSON_NAO_ENCONTRADO", "Nenhum JSON em status I foi encontrado para PVX apos gerar a nota", ImportadorLogger.ctx("pvx", pvx));
        } else if (qtdAtualizada > 1) {
            ImportadorLogger.warn(LOGGER, "GERACAO_JSON_MULTIPLO", "Mais de um JSON foi marcado como S para o mesmo PVX", ImportadorLogger.ctx("pvx", pvx, "qtd_json_marcado", qtdAtualizada));
        }
        return qtdAtualizada;
    }

    private String classificarOrigemErro(Throwable throwable) {
        for (Throwable atual = throwable; atual != null; atual = atual.getCause()) {
            String classe = atual.getClass().getName();
            if (classe.startsWith("br.com.sankhya")) {
                return "SANKHYA";
            }
            if (!classe.startsWith("org.ImportadorPedido")) continue;
            return "IMPORTADOR";
        }
        return "INDEFINIDA";
    }

    private void logSinaisDivergenciaNaOrigem(String pvx, BigDecimal codEmp, BigDecimal codParc, List<ItemImportado> itensPVX) {
        HashMap<BigDecimal, Set> refsPorProduto = new HashMap<BigDecimal, Set>();
        for (ItemImportado itemImportado : itensPVX) {
            String ref;
            BigDecimal cod = itemImportado.getCod();
            refsPorProduto.computeIfAbsent(cod, key -> new HashSet()).add((ref = itemImportado.getReferencia()) == null ? "" : ref);
        }
        for (Map.Entry entry : refsPorProduto.entrySet()) {
            if (((Set)entry.getValue()).size() <= 1) continue;
            ImportadorLogger.warn(LOGGER, "GERACAO_MULTI_REF_POR_COD", "Mesmo COD com referencias diferentes na origem do PVX", ImportadorLogger.ctx("pvx", pvx, "empresa", codEmp, "codparc", codParc, "cod", entry.getKey(), "referencias", entry.getValue(), "qtd_itens", itensPVX.size()));
        }
    }

    private Map<String, Object> construirContextoItem(String pvx, BigDecimal codEmp, BigDecimal codParc, BigDecimal nuNota, ItemImportado item, int index, int total) {
        return this.construirContextoItem(pvx, codEmp, codParc, nuNota, item, index, total, null);
    }

    private Map<String, Object> construirContextoItem(String pvx, BigDecimal codEmp, BigDecimal codParc, BigDecimal nuNota, ItemImportado item, int index, int total, Long duracaoMs) {
        Map<String, Object> ctxItem = ImportadorLogger.ctx("pvx", pvx, "empresa", codEmp, "codparc", codParc, "nunota", nuNota, "item_index", index, "item_total", total, "cod", item.getCod(), "referencia", item.getReferencia(), "qty", item.getQty(), "unit", item.getUnit(), "unitprice", item.getUnitPrice(), "totalprice", item.getTotalPrice());
        if (duracaoMs != null) {
            ctxItem.put("duracao_ms", duracaoMs);
        }
        return ctxItem;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<ItemImportado> carregarItensPendentes(String pvx, BigDecimal codEmp, BigDecimal codParc) throws Exception {
        NativeSql itensSql = new NativeSql(this.jdbc);
        itensSql.setReuseStatements(false);
        ArrayList<ItemImportado> itens = new ArrayList<ItemImportado>();
        try (ResultSet rsItens = itensSql.executeQuery("SELECT PVX, EMPRESA, CODPARC, PA, REFERENCIA, COD, UNIT, UNITPRICE, QTY, TOTALPRICE FROM AD_IMPREGPED WHERE PVX = '" + this.escapeSqlLiteral(pvx) + "' AND EMPRESA = " + codEmp + " AND CODPARC = " + codParc + " AND NUNOTA IS NULL ORDER BY COD, REFERENCIA, QTY, UNITPRICE")) {
            while (rsItens.next()) {
                itens.add(new ItemImportado(rsItens.getString("PVX"), rsItens.getBigDecimal("EMPRESA"), rsItens.getString("PA"), rsItens.getString("REFERENCIA"), rsItens.getBigDecimal("COD"), rsItens.getString("UNIT"), rsItens.getBigDecimal("UNITPRICE"), rsItens.getBigDecimal("QTY"), rsItens.getBigDecimal("TOTALPRICE")));
            }
        }
        return itens;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int atualizarNunotaPorChave(String pvx, BigDecimal codEmp, BigDecimal codParc, BigDecimal nuNota) throws Exception {
        NativeSql updateSql = new NativeSql(this.jdbc);
        updateSql.setReuseStatements(false);
        boolean atualizado = updateSql.executeUpdate("UPDATE AD_IMPREGPED SET NUNOTA = " + nuNota + " WHERE PVX = '" + this.escapeSqlLiteral(pvx) + "' AND EMPRESA = " + codEmp + " AND CODPARC = " + codParc + " AND NUNOTA IS NULL");
        if (!atualizado) {
            return 0;
        }
        NativeSql countSql = new NativeSql(this.jdbc);
        countSql.setReuseStatements(false);
        try (ResultSet rsCount = countSql.executeQuery("SELECT COUNT(1) AS QTD FROM AD_IMPREGPED WHERE PVX = '" + this.escapeSqlLiteral(pvx) + "' AND EMPRESA = " + codEmp + " AND CODPARC = " + codParc + " AND NUNOTA = " + nuNota)) {
            if (rsCount.next()) {
                int n = rsCount.getInt("QTD");
                return n;
            }
            int n = 0;
            return n;
        }
    }

    private ResultadoReconciliacaoProduto reconciliarProduto(ItemImportado item) throws Exception {
        String referencia = this.normalizarTexto(item.getReferencia());
        BigDecimal codOrigem = item.getCod();
        ProdutoRepository.ProdutoInfo produtoPorCod = this.buscarProdutoPorCodComCache(codOrigem);
        List<ProdutoRepository.ProdutoInfo> produtosPorReferencia = this.buscarProdutoPorReferenciaComCache(referencia);
        if (codOrigem == null) {
            if (produtosPorReferencia.size() == 1) {
                return ResultadoReconciliacaoProduto.corrigido(null, produtosPorReferencia.get(0).getCodProd(), referencia, "COD_AUSENTE_CORRIGIDO_POR_REFERENCIA", "ERRO_USUARIO_PLANILHA");
            }
            if (produtosPorReferencia.isEmpty()) {
                return ResultadoReconciliacaoProduto.ignorar(null, referencia, "SEM_PRODUTO_PELA_REFERENCIA", "CADASTRO_PRODUTO_OU_PLANILHA");
            }
            return ResultadoReconciliacaoProduto.ignorar(null, referencia, "REFERENCIA_AMBIGUA", "CADASTRO_PRODUTO");
        }
        if (produtoPorCod == null) {
            if (produtosPorReferencia.size() == 1) {
                return ResultadoReconciliacaoProduto.corrigido(codOrigem, produtosPorReferencia.get(0).getCodProd(), referencia, "COD_INEXISTENTE_CORRIGIDO_POR_REFERENCIA", "ERRO_USUARIO_PLANILHA");
            }
            if (produtosPorReferencia.isEmpty()) {
                return ResultadoReconciliacaoProduto.ignorar(codOrigem, referencia, "COD_INEXISTENTE_E_SEM_REFERENCIA_VALIDA", "CADASTRO_PRODUTO_OU_PLANILHA");
            }
            return ResultadoReconciliacaoProduto.ignorar(codOrigem, referencia, "COD_INEXISTENTE_E_REFERENCIA_AMBIGUA", "CADASTRO_PRODUTO");
        }
        if (referencia.isEmpty()) {
            return ResultadoReconciliacaoProduto.ok(codOrigem, referencia);
        }
        String referenciaDoCod = this.normalizarTexto(produtoPorCod.getReferencia());
        if (referencia.equals(referenciaDoCod)) {
            return ResultadoReconciliacaoProduto.ok(codOrigem, referencia);
        }
        if (produtosPorReferencia.isEmpty()) {
            return ResultadoReconciliacaoProduto.ok(codOrigem, referencia, "COD_PRIORIZADO_REFERENCIA_SEM_MATCH", "REFERENCIA_DIVERGENTE_NA_ORIGEM");
        }
        if (produtosPorReferencia.size() == 1) {
            return ResultadoReconciliacaoProduto.ok(codOrigem, referencia, "COD_PRIORIZADO_SOBRE_REFERENCIA_DIVERGENTE", "REFERENCIA_APONTA_PARA_OUTRO_PRODUTO");
        }
        return ResultadoReconciliacaoProduto.ok(codOrigem, referencia, "COD_PRIORIZADO_COM_REFERENCIA_AMBIGUA", "CADASTRO_COM_REFERENCIA_DUPLICADA");
    }

    private ProdutoRepository.ProdutoInfo buscarProdutoPorCodComCache(BigDecimal cod) throws Exception {
        if (cod == null) {
            return null;
        }
        if (this.cacheProdutoPorCod.containsKey(cod)) {
            return this.cacheProdutoPorCod.get(cod);
        }
        ProdutoRepository.ProdutoInfo encontrado = this.produtoRepository.buscarPorCod(cod);
        this.cacheProdutoPorCod.put(cod, encontrado);
        return encontrado;
    }

    private List<ProdutoRepository.ProdutoInfo> buscarProdutoPorReferenciaComCache(String referencia) throws Exception {
        String chave;
        String string = chave = referencia == null ? "" : referencia;
        if (this.cacheProdutoPorReferencia.containsKey(chave)) {
            return this.cacheProdutoPorReferencia.get(chave);
        }
        List<ProdutoRepository.ProdutoInfo> encontrados = this.produtoRepository.buscarPorReferenciaNormalizada(chave);
        this.cacheProdutoPorReferencia.put(chave, encontrados);
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

