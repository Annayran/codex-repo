/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  br.com.sankhya.jape.EntityFacade
 *  br.com.sankhya.jape.bmp.PersistentLocalEntity
 *  br.com.sankhya.jape.core.JapeSession
 *  br.com.sankhya.jape.dao.JdbcWrapper
 *  br.com.sankhya.jape.util.JapeSessionContext
 *  br.com.sankhya.jape.vo.DynamicVO
 *  br.com.sankhya.jape.vo.EntityVO
 *  br.com.sankhya.jape.vo.PrePersistEntityState
 *  br.com.sankhya.modelcore.auth.AuthenticationInfo
 *  br.com.sankhya.modelcore.comercial.BarramentoRegra
 *  br.com.sankhya.modelcore.comercial.BarramentoRegra$DadosBarramento
 *  br.com.sankhya.modelcore.comercial.CentralFaturamento
 *  br.com.sankhya.modelcore.comercial.CentralFinanceiro
 *  br.com.sankhya.modelcore.comercial.CentralItemNota
 *  br.com.sankhya.modelcore.comercial.CentralItemNota$ParamsInicializacaoProduto
 *  br.com.sankhya.modelcore.comercial.ComercialUtils
 *  br.com.sankhya.modelcore.comercial.ComercialUtils$PrecoUnitarioInfo
 *  br.com.sankhya.modelcore.comercial.ConfirmacaoNotaHelper
 *  br.com.sankhya.modelcore.comercial.centrais.CACHelper
 *  br.com.sankhya.modelcore.comercial.impostos.ImpostosHelpper
 *  br.com.sankhya.modelcore.util.EntityFacadeFactory
 */
package org.ImportadorPedido.helper;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.bmp.PersistentLocalEntity;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.util.JapeSessionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.jape.vo.PrePersistEntityState;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.comercial.BarramentoRegra;
import br.com.sankhya.modelcore.comercial.CentralFaturamento;
import br.com.sankhya.modelcore.comercial.CentralFinanceiro;
import br.com.sankhya.modelcore.comercial.CentralItemNota;
import br.com.sankhya.modelcore.comercial.ComercialUtils;
import br.com.sankhya.modelcore.comercial.ConfirmacaoNotaHelper;
import br.com.sankhya.modelcore.comercial.centrais.CACHelper;
import br.com.sankhya.modelcore.comercial.impostos.ImpostosHelpper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import java.math.BigDecimal;
import java.util.logging.Logger;
import org.ImportadorPedido.helper.DecimalImportHelper;
import org.ImportadorPedido.helper.ImportadorLogger;

public class SankhyaHelper {
    private BarramentoRegra.DadosBarramento dadosBarramento;
    private final EntityFacade dwFacade = EntityFacadeFactory.getDWFFacade();
    private final JdbcWrapper jdbc = this.dwFacade.getJdbcWrapper();
    private static final Logger LOGGER = Logger.getLogger(SankhyaHelper.class.getName());

    public void refazerFinanceiroForce(BigDecimal nunota) throws Exception {
        try {
            JapeSessionContext.putProperty((String)"br.com.sankhya.com.CentralCompraVenda", (Object)Boolean.TRUE);
            JapeSessionContext.putProperty((String)"ItemNota.incluindo.alterando.pela.central", (Object)Boolean.TRUE);
            JapeSessionContext.putProperty((String)"calcular.outros.impostos", (Object)"true");
            CentralFinanceiro financeiro = new CentralFinanceiro();
            financeiro.inicializaNota(nunota);
            financeiro.refazerFinanceiro();
        }
        finally {
            JapeSessionContext.putProperty((String)"br.com.sankhya.com.CentralCompraVenda", (Object)Boolean.FALSE);
            JapeSessionContext.putProperty((String)"ItemNota.incluindo.alterando.pela.central", (Object)Boolean.FALSE);
            JapeSessionContext.putProperty((String)"calcular.outros.impostos", (Object)"false");
        }
    }

    public void recalcularImpost(BigDecimal nuNota) throws Exception {
        EntityFacade dwfFacade = EntityFacadeFactory.getDWFFacade();
        DynamicVO cabVO = (DynamicVO)dwfFacade.findEntityByPrimaryKeyAsVO("CabecalhoNota", new Object[]{nuNota});
        ImpostosHelpper impostosHelper = new ImpostosHelpper();
        impostosHelper.setForcarRecalculo(true);
        impostosHelper.carregarNota(cabVO);
        impostosHelper.totalizarNota(nuNota);
        impostosHelper.setCalcularTudo(true);
        impostosHelper.calculaICMS(true);
    }

    public void inseriItem(DynamicVO cabecalhoVO, DynamicVO itemRS) throws Exception {
        this.inseriItem(cabecalhoVO, itemRS.asBigDecimal("COD"), itemRS.asBigDecimal("QTY"), itemRS.asBigDecimal("UNITPRICE"), itemRS.asBigDecimal("TOTALPRICE"));
    }

    public void inseriItem(DynamicVO cabecalhoVO, BigDecimal codProd, BigDecimal qty, BigDecimal unitPrice, BigDecimal totalPrice) throws Exception {
        BigDecimal nuNota = cabecalhoVO.asBigDecimal("NUNOTA");
        BigDecimal unitPriceNormalizado = DecimalImportHelper.normalizarUnitPrice(unitPrice);
        BigDecimal totalPriceNormalizado = DecimalImportHelper.normalizarTotalPrice(totalPrice);
        if (codProd == null) {
            throw new Exception("COD do produto nao informado para NUNOTA " + nuNota);
        }
        if (qty == null) {
            throw new Exception("QTY nao informada para NUNOTA " + nuNota + " e COD " + codProd);
        }
        ImportadorLogger.info(LOGGER, "ITEM_INSERCAO_INICIO", "Iniciando insercao de item na nota", ImportadorLogger.ctx("nunota", nuNota, "cod", codProd));
        try {
            CentralItemNota centralItemNota = new CentralItemNota();
            CentralItemNota.ParamsInicializacaoProduto params = new CentralItemNota.ParamsInicializacaoProduto();
            BigDecimal codtop = cabecalhoVO.asBigDecimalOrZero("CODTIPOPER");
            DynamicVO topVO = ComercialUtils.getTipoOperacao((BigDecimal)codtop);
            String topAtualEst = topVO.asString("ATUALEST");
            BigDecimal topAtualEstMP = topVO.asBigDecimal("ATUALESTMP");
            String estNaConfirmacao = topVO.asString("ADIARATUALEST");
            params.nuNota = nuNota;
            params.chamadoPelaTela = true;
            params.codProd = codProd;
            ComercialUtils.PrecoUnitarioInfo pui = centralItemNota.inicializaProduto(params);
            DynamicVO itemVO = (DynamicVO)this.dwFacade.getDefaultValueObjectInstance("ItemNota");
            itemVO.setProperty("NUNOTA", (Object)nuNota);
            itemVO.setProperty("CODPROD", (Object)codProd);
            itemVO.setProperty("QTDNEG", (Object)qty);
            itemVO.setProperty("CODVOL", (Object)pui.getCodVol());
            itemVO.setProperty("VLRUNIT", (Object)unitPriceNormalizado);
            itemVO.setProperty("VLRUNITMOE", (Object)unitPriceNormalizado);
            itemVO.setProperty("CUSTO", (Object)pui.getCusto());
            itemVO.setProperty("NUTAB", (Object)pui.getNuTab());
            itemVO.setProperty("PRECOBASE", (Object)pui.getPrecoBase());
            itemVO.setProperty("VLRCUS", (Object)pui.getVlrCus());
            itemVO.setProperty("USOPROD", (Object)pui.getUsoProd());
            itemVO.setProperty("VLRTOTMOE", (Object)totalPriceNormalizado);
            itemVO.setProperty("CODLOCALORIG", (Object)new BigDecimal(90100000));
            itemVO.setProperty("CODVEND", (Object)cabecalhoVO.asBigDecimal("CODVEND"));
            if ("M".equals(pui.getUsoProd())) {
                itemVO.setProperty("ATUALESTOQUE", (Object)topAtualEstMP);
            } else {
                if ("B".equals(topAtualEst)) {
                    itemVO.setProperty("ATUALESTOQUE", (Object)BigDecimal.valueOf(-1L));
                } else {
                    itemVO.setProperty("ATUALESTOQUE", (Object)("R-E".contains(topAtualEst) ? BigDecimal.ONE : BigDecimal.ZERO));
                }
                if ("S".equals(estNaConfirmacao)) {
                    itemVO.setProperty("ATUALESTOQUE", (Object)BigDecimal.ZERO);
                }
            }
            itemVO.setProperty("RESERVA", (Object)("R".equals(topAtualEst) ? "S" : "N"));
            centralItemNota.recalcularValores("VLRUNITMOE", null, itemVO, nuNota);
            if (itemVO.asBigDecimalOrZero("VLRTOT").signum() <= 0 && unitPriceNormalizado.signum() > 0) {
                throw new Exception("Calculo Sankhya retornou total local zero; conferir moeda/cotacao da nota " + nuNota);
            }
            PersistentLocalEntity entidade = this.dwFacade.createEntity("ItemNota", (EntityVO)itemVO);
            DynamicVO gravado = (DynamicVO) entidade.getValueObject();
            if (qty.compareTo(gravado.asBigDecimalOrZero("QTDNEG")) != 0
                    || unitPriceNormalizado.compareTo(gravado.asBigDecimalOrZero("VLRUNITMOE")) != 0) {
                throw new Exception("Precisao alterada pelo ERP no produto " + codProd + "; conferir escala dos campos e configuracao de decimais");
            }
            if (qty.multiply(unitPriceNormalizado).setScale(2, java.math.RoundingMode.HALF_UP)
                    .compareTo(gravado.asBigDecimalOrZero("VLRTOTMOE").setScale(2, java.math.RoundingMode.HALF_UP)) != 0) {
                throw new Exception("Total em moeda gravado diverge de quantidade x unitario no produto " + codProd);
            }
            ImportadorLogger.info(LOGGER, "ITEM_INSERCAO_SUCESSO", "Item inserido com sucesso na nota", ImportadorLogger.ctx("nunota", nuNota, "cod", codProd, "qtd", qty));
        }
        catch (Exception e) {
            ImportadorLogger.error(LOGGER, "ITEM_INSERCAO_ERRO", "Falha ao inserir item na nota", e, ImportadorLogger.ctx("nunota", nuNota, "cod", codProd));
            throw e;
        }
    }

    public DynamicVO buscaModelo(BigDecimal nunota) throws Exception {
        DynamicVO modeloVO = (DynamicVO)this.dwFacade.findEntityByPrimaryKeyAsVO("CabecalhoNota", (Object)nunota);
        return modeloVO;
    }

    public DynamicVO inserirCabecalho(DynamicVO origemVO, String observacao, BigDecimal codParc, BigDecimal codCencus, BigDecimal codNat) throws Exception {
        String[] camposParaAnular;
        DynamicVO novoPedidoVO = origemVO.buildClone();
        novoPedidoVO.setPrimaryKey(null);
        novoPedidoVO.clearReferences();
        for (String nomeCampo : camposParaAnular = new String[]{"DHTIPOPER", "STATUSNOTA", "DTNEG", "HRMOV", "DTMOV", "VOLUME", "QTDVOL", "CHAVENFE", "CODMAQ", "DANFE", "DTENTSAI", "DTFATUR", "DHPROTOC", "DHREGDPEC", "NULOTENFE", "NULOTENFSE", "NUMALEATORIO", "NUMNFSE", "NUMPROTOC", "NUMREGDPEC", "NUNOTA", "NUNOTAPEDFRET", "TPEMISNFE", "INDPRESNFCE", "TPEMISNFSE", "STATUSNFE", "STATUSNFSE", "COMGER", "NUREM"}) {
            novoPedidoVO.setProperty(nomeCampo, null);
        }
        novoPedidoVO.setProperty("CODPARC", (Object)codParc);
        novoPedidoVO.setProperty("CODCENCUS", (Object)codCencus);
        novoPedidoVO.setProperty("CODNAT", (Object)codNat);
        PersistentLocalEntity cabEntity = null;
        try {
            cabEntity = this.dwFacade.createEntity("CabecalhoNota", (EntityVO)novoPedidoVO);
        }
        catch (Exception e) {
            ImportadorLogger.error(LOGGER, "CABECALHO_ERRO", "Falha ao criar cabecalho da nota", e, ImportadorLogger.ctx("codparc", codParc, "codcencus", codCencus, "codnat", codNat));
            throw new Exception(e.getMessage(), e);
        }
        return (DynamicVO)cabEntity.getValueObject();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void confirm(BigDecimal nuNotaPedidoConfirmedOrder, Boolean ignorarLiberacao) throws Exception {
        BarramentoRegra bRegras = BarramentoRegra.build(CACHelper.class, (String)"regrasConfirmacaoCAC.xml", (AuthenticationInfo)AuthenticationInfo.getCurrent());
        JapeSessionContext.putProperty((String)"barramento.regras.confirmacao", (Object)bRegras);
        try {
            if (ignorarLiberacao.booleanValue()) {
                JapeSession.putProperty((String)"ignorar.liberacao.alcada", (Object)Boolean.TRUE);
            }
            ConfirmacaoNotaHelper.confirmarNota((BigDecimal)nuNotaPedidoConfirmedOrder, (BarramentoRegra)bRegras, (boolean)ignorarLiberacao);
            bRegras.getErros();
            bRegras.getMensagens();
        }
        finally {
            if (ignorarLiberacao.booleanValue()) {
                JapeSession.putProperty((String)"ignorar.liberacao.alcada", (Object)Boolean.FALSE);
            }
            JapeSessionContext.removeProperty((String)"barramento.regras.confirmacao");
        }
    }

    public static PrePersistEntityState confirmaNotaComAvisos(BigDecimal nuNota) throws Exception {
        BarramentoRegra barramento = BarramentoRegra.build(CentralFaturamento.class, (String)"regrasConfirmacaoSilenciosa.xml", (AuthenticationInfo)AuthenticationInfo.getCurrent());
        barramento.setValidarSilencioso(true);
        PrePersistEntityState pre = ConfirmacaoNotaHelper.confirmarNota((BigDecimal)nuNota, (BarramentoRegra)barramento, (boolean)true);
        return pre;
    }
}

