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
import br.com.sankhya.modelcore.comercial.*;
import br.com.sankhya.modelcore.comercial.centrais.CACHelper;
import br.com.sankhya.modelcore.comercial.impostos.ImpostosHelpper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

import java.math.BigDecimal;
import java.util.logging.Logger;

public class SankhyaHelper {
    private BarramentoRegra.DadosBarramento dadosBarramento;
    private final EntityFacade dwFacade = EntityFacadeFactory.getDWFFacade();
    private final JdbcWrapper jdbc = dwFacade.getJdbcWrapper();
    private static final Logger LOGGER = Logger.getLogger(SankhyaHelper.class.getName());

    public void refazerFinanceiroForce(BigDecimal nunota) throws Exception {
        try {

            JapeSessionContext.putProperty("br.com.sankhya.com.CentralCompraVenda", Boolean.TRUE);
            JapeSessionContext.putProperty("ItemNota.incluindo.alterando.pela.central", Boolean.TRUE);
            JapeSessionContext.putProperty("calcular.outros.impostos", "true");

            CentralFinanceiro financeiro = new CentralFinanceiro();
            financeiro.inicializaNota(nunota);
            financeiro.refazerFinanceiro();
        } finally {
            JapeSessionContext.putProperty("br.com.sankhya.com.CentralCompraVenda", Boolean.FALSE);
            JapeSessionContext.putProperty("ItemNota.incluindo.alterando.pela.central", Boolean.FALSE);
            JapeSessionContext.putProperty("calcular.outros.impostos", "false");
        }
    }

    public void recalcularImpost(BigDecimal nuNota) throws Exception {

        EntityFacade dwfFacade = EntityFacadeFactory.getDWFFacade();

        DynamicVO cabVO = (DynamicVO) dwfFacade.findEntityByPrimaryKeyAsVO("CabecalhoNota", new Object[]{nuNota});
        ImpostosHelpper impostosHelper = new ImpostosHelpper();
        impostosHelper.setForcarRecalculo(true);
        impostosHelper.carregarNota(cabVO);
        impostosHelper.totalizarNota(nuNota);
        impostosHelper.setCalcularTudo(true);
        impostosHelper.calculaICMS(true);

    }

    public void inseriItem(DynamicVO cabecalhoVO,DynamicVO itemRS) throws Exception {
        inseriItem(
                cabecalhoVO,
                itemRS.asBigDecimal("COD"),
                itemRS.asBigDecimal("QTY"),
                itemRS.asBigDecimal("UNITPRICE"),
                itemRS.asBigDecimal("TOTALPRICE")
        );
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

        ImportadorLogger.info(
                LOGGER,
                "ITEM_INSERCAO_INICIO",
                "Iniciando insercao de item na nota",
                ImportadorLogger.ctx("nunota", nuNota, "cod", codProd)
        );

        try {
            CentralItemNota centralItemNota = new CentralItemNota();
            CentralItemNota.ParamsInicializacaoProduto params = new CentralItemNota.ParamsInicializacaoProduto();

            BigDecimal codtop = cabecalhoVO.asBigDecimalOrZero("CODTIPOPER");
            DynamicVO topVO = ComercialUtils.getTipoOperacao(codtop);

            String topAtualEst = topVO.asString("ATUALEST");
            BigDecimal topAtualEstMP = topVO.asBigDecimal("ATUALESTMP");
            String estNaConfirmacao = topVO.asString("ADIARATUALEST");

            params.nuNota = nuNota;
            params.chamadoPelaTela = true;
            params.codProd = codProd;

            ComercialUtils.PrecoUnitarioInfo pui = centralItemNota.inicializaProduto(params);
            DynamicVO itemVO = (DynamicVO) dwFacade.getDefaultValueObjectInstance(DynamicEntityNames.ITEM_NOTA);

            itemVO.setProperty("NUNOTA", nuNota);
            itemVO.setProperty("CODPROD", codProd);
            itemVO.setProperty("QTDNEG", qty);
            itemVO.setProperty("CODVOL", pui.getCodVol());
            itemVO.setProperty("VLRUNIT", unitPriceNormalizado);
            itemVO.setProperty("VLRUNITMOE", unitPriceNormalizado);
            itemVO.setProperty("CUSTO", pui.getCusto());
            itemVO.setProperty("NUTAB", pui.getNuTab());
            itemVO.setProperty("PRECOBASE", pui.getPrecoBase());
            itemVO.setProperty("VLRCUS", pui.getVlrCus());
            itemVO.setProperty("USOPROD", pui.getUsoProd());
            itemVO.setProperty("VLRTOTMOE", totalPriceNormalizado);
            itemVO.setProperty("CODLOCALORIG", new BigDecimal(90100000));
            itemVO.setProperty("CODVEND", cabecalhoVO.asBigDecimal("CODVEND"));

            if ("M".equals(pui.getUsoProd())) {
                itemVO.setProperty("ATUALESTOQUE", topAtualEstMP);
            } else {
                if ("B".equals(topAtualEst)) {
                    itemVO.setProperty("ATUALESTOQUE", BigDecimal.valueOf(-1));
                } else {
                    itemVO.setProperty("ATUALESTOQUE", ("R-E".contains(topAtualEst)) ? BigDecimal.ONE : BigDecimal.ZERO);
                }

                if ("S".equals(estNaConfirmacao)) {
                    itemVO.setProperty("ATUALESTOQUE", BigDecimal.ZERO);
                }
            }

            itemVO.setProperty("RESERVA", "R".equals(topAtualEst) ? "S" : "N");
            dwFacade.createEntity(DynamicEntityNames.ITEM_NOTA, (EntityVO) itemVO);

            ImportadorLogger.info(
                    LOGGER,
                    "ITEM_INSERCAO_SUCESSO",
                    "Item inserido com sucesso na nota",
                    ImportadorLogger.ctx("nunota", nuNota, "cod", codProd, "qtd", qty)
            );
        } catch (Exception e) {
            ImportadorLogger.error(
                    LOGGER,
                    "ITEM_INSERCAO_ERRO",
                    "Falha ao inserir item na nota",
                    e,
                    ImportadorLogger.ctx("nunota", nuNota, "cod", codProd)
            );
            throw e;
        }

    }

    public DynamicVO buscaModelo(BigDecimal nunota) throws Exception {

        DynamicVO modeloVO = (DynamicVO) dwFacade.findEntityByPrimaryKeyAsVO(DynamicEntityNames.CABECALHO_NOTA, nunota);

        return modeloVO;

    }

    public DynamicVO inserirCabecalho(DynamicVO origemVO, String observacao, BigDecimal codParc, BigDecimal codCencus, BigDecimal codNat) throws Exception {

        DynamicVO novoPedidoVO = origemVO.buildClone();
        novoPedidoVO.setPrimaryKey(null);
        novoPedidoVO.clearReferences();

        String[] camposParaAnular = {"DHTIPOPER", "STATUSNOTA", "DTNEG", "HRMOV", "DTMOV", "VOLUME", "QTDVOL", "CHAVENFE",
                "CODMAQ", "DANFE", "DTENTSAI", "DTFATUR", "DHPROTOC", "DHREGDPEC", "NULOTENFE", "NULOTENFSE", "NUMALEATORIO",
                "NUMNFSE", "NUMPROTOC", "NUMREGDPEC", "NUNOTA", "NUNOTAPEDFRET", "TPEMISNFE", "INDPRESNFCE", "TPEMISNFSE", "STATUSNFE", "STATUSNFSE",
                "COMGER", "NUREM"};

        for (String nomeCampo : camposParaAnular) {
            novoPedidoVO.setProperty(nomeCampo, null);
        }

        novoPedidoVO.setProperty("CODPARC", codParc);
        novoPedidoVO.setProperty("CODCENCUS", codCencus);
        novoPedidoVO.setProperty("CODNAT", codNat);

        PersistentLocalEntity cabEntity = null;
        try {
            cabEntity = dwFacade.createEntity(DynamicEntityNames.CABECALHO_NOTA, (EntityVO) novoPedidoVO);
        } catch (Exception e) {
            ImportadorLogger.error(
                    LOGGER,
                    "CABECALHO_ERRO",
                    "Falha ao criar cabecalho da nota",
                    e,
                    ImportadorLogger.ctx("codparc", codParc, "codcencus", codCencus, "codnat", codNat)
            );
            throw new Exception(e.getMessage(), e);

        }

        return (DynamicVO) cabEntity.getValueObject();

    }

    public void confirm(BigDecimal nuNotaPedidoConfirmedOrder, Boolean ignorarLiberacao) throws Exception {
        BarramentoRegra bRegras = BarramentoRegra.build(CACHelper.class, "regrasConfirmacaoCAC.xml", AuthenticationInfo.getCurrent());
        JapeSessionContext.putProperty("barramento.regras.confirmacao", bRegras);

        try {
            if (ignorarLiberacao) JapeSession.putProperty("ignorar.liberacao.alcada", Boolean.TRUE);

            ConfirmacaoNotaHelper.confirmarNota(nuNotaPedidoConfirmedOrder, bRegras, ignorarLiberacao);
            bRegras.getErros();
            bRegras.getMensagens();

        } finally {
            if (ignorarLiberacao) {
                JapeSession.putProperty("ignorar.liberacao.alcada", Boolean.FALSE);
            }

            JapeSessionContext.removeProperty("barramento.regras.confirmacao");
        }
    }

    public static PrePersistEntityState confirmaNotaComAvisos(BigDecimal nuNota) throws Exception {
        BarramentoRegra barramento = BarramentoRegra.build(CentralFaturamento.class, "regrasConfirmacaoSilenciosa.xml", AuthenticationInfo.getCurrent());
        barramento.setValidarSilencioso(true);
        PrePersistEntityState pre = ConfirmacaoNotaHelper.confirmarNota(nuNota, barramento, true);
        return pre;
    }
}
