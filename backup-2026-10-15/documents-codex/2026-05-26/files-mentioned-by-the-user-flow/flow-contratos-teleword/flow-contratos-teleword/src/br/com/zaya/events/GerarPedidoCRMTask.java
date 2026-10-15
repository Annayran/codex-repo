package br.com.zaya.events;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;

import javax.ejb.FinderException;

import org.jdom.Content;
import org.jdom.Element;

import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.TimeUtils;
import com.sankhya.util.XMLUtils;

import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.extensions.flow.ContextoTarefa;
import br.com.sankhya.extensions.flow.TarefaJava;
import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.bmp.PersistentLocalEntity;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.util.FinderWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.jape.vo.PrePersistEntityState;
import br.com.sankhya.modelcore.MGEModelException;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.comercial.CentralItemNota;
import br.com.sankhya.modelcore.comercial.ComercialUtils;
import br.com.sankhya.modelcore.comercial.centrais.CACHelper;
import br.com.sankhya.modelcore.comercial.impostos.ImpostosHelpper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.sankhya.ws.ServiceContext;

public class GerarPedidoCRMTask implements TarefaJava {

    ServiceContext ctx = ServiceContext.getCurrent();

    CACHelper cacHelper = new CACHelper();

    @Override
    public void executar(ContextoTarefa contexto) throws Exception {
        EntityFacade dwfFacade = EntityFacadeFactory.getDWFFacade();
        CACHelper.setupContext(this.ctx);
        for (int i = 0; i < (contexto.getLinhasFormulario("VGF_PROCOPO")).length; i++) {
            Registro r = contexto.getLinhasFormulario("VGF_PROCOPO")[i];
            BigDecimal nrOportunidade = (BigDecimal) r.getCampo("NUMOP");
            DynamicVO opoVO = null;
            PersistentLocalEntity opoEntity = null;
            try {
                opoEntity = dwfFacade.findEntityByPrimaryKey("Oportunidade", new Object[] { nrOportunidade });
                opoVO = (DynamicVO) opoEntity.getValueObject();
            } catch (FinderException finderException) {
            }
            if (opoVO == null)
                throw new Exception("NFoi possrecuperar a Oportunidade");
            if (!opoVO.asBigDecimalOrZero("AD_NUNOTA").equals(BigDecimal.ZERO))
                throw new Exception("Proposta jgerou Pedido");
            boolean podeGerar = (opoVO.asString("STATUS").equalsIgnoreCase("G")
                    || opoVO.asString("AD_LIBERAPROC").equalsIgnoreCase("S"));
            if (!podeGerar)
                throw new Exception("Spossgerar pedido para Oportunidade Ganha ou com liberade processamento");
            DynamicVO negociacaoVO = (DynamicVO) dwfFacade.findEntityByPrimaryKeyAsVO("TipoNegociacaoPreVenda", opoVO
                    .asBigDecimalOrZero("CODTPN"));
            BigDecimal modeloNota = negociacaoVO.asBigDecimalOrZero("NUMODELO");
            if (modeloNota.equals(BigDecimal.ZERO))
                throw new Exception("Modelo Negociasem Modelo de Pedido");
            DynamicVO pedidoVO = inserirCabecalho(this.cacHelper, modeloNota, opoVO);
            FinderWrapper finder = new FinderWrapper("ProdutoNegociado", "this.NUMOP = ?",
                    new Object[] { nrOportunidade });
            Collection<DynamicVO> itensNegociacao = dwfFacade.findByDynamicFinderAsVO(finder);
            Boolean temContrato = Boolean.valueOf(false);
            for (DynamicVO itemVO : itensNegociacao) {
                if (!itemVO.asBoolean("Produto.AD_USOCONTRATO")) {
                    incluiItem(pedidoVO, itemVO);
                    continue;
                }
                temContrato = Boolean.valueOf(true);
            }
            createComissao(pedidoVO.asBigDecimalOrZero("NUNOTA"), opoVO.asBigDecimalOrZero("CODVEND"), "V");
            DynamicVO vendedorVO = (DynamicVO) dwfFacade.findEntityByPrimaryKeyAsVO("Vendedor", new Object[] { opoVO
                    .asBigDecimalOrZero("CODVEND") });
            if (vendedorVO != null && !vendedorVO.asBigDecimalOrZero("CODGER").equals(BigDecimal.ZERO))
                createComissao(pedidoVO.asBigDecimalOrZero("NUNOTA"), vendedorVO.asBigDecimalOrZero("CODGER"), "G");
            if (!opoVO.asBigDecimalOrZero("CODPROSPECT").equals(BigDecimal.ZERO))
                createComissao(pedidoVO.asBigDecimalOrZero("NUNOTA"), opoVO.asBigDecimalOrZero("CODPROSPECT"), "P");
            if (!opoVO.asBigDecimalOrZero("CODINDICADOR").equals(BigDecimal.ZERO))
                createComissao(pedidoVO.asBigDecimalOrZero("NUNOTA"), opoVO.asBigDecimalOrZero("CODINDICADOR"), "I");
            opoVO.setProperty("AD_NUNOTA", pedidoVO.asBigDecimalOrZero("NUNOTA"));
            opoEntity.setValueObject((EntityVO) opoVO);
        }
    }

    private BigDecimal inserirContrato(DynamicVO pedCRMVO, DynamicVO opoVO) throws Exception {
        DynamicVO contratoVO = null;
        try {
            EntityFacade dwfEntityFacade = EntityFacadeFactory.getDWFFacade();
            contratoVO = (DynamicVO) dwfEntityFacade.getDefaultValueObjectInstance("Contrato");
            contratoVO.setProperty("CODEMP", pedCRMVO.getProperty("CODEMP"));
            contratoVO.setProperty("CODPARC", pedCRMVO.getProperty("CODPARC"));
            if (!BigDecimalUtil.getValueOrZero(pedCRMVO.asBigDecimal("CODCONTATO")).equals(BigDecimal.ZERO))
                contratoVO.setProperty("CODCONTATO", pedCRMVO.getProperty("CODCONTATO"));
            contratoVO.setProperty("DTCONTRATO", opoVO.getProperty("DTPREVISTA"));
            contratoVO.setProperty("DTBASEREAJ", new Timestamp(
                    TimeUtils.add(TimeUtils.getNow().getTime(), BigDecimalUtil.valueOf(12L).intValue(), 2)));
            contratoVO.setProperty("QTDPREV", BigDecimalUtil.valueOf(12L));
            contratoVO.setProperty("QTDPROVISAO", BigDecimalUtil.valueOf(12L));
            contratoVO.setProperty("RECDESP", BigDecimalUtil.valueOf(1L));
            contratoVO.setProperty("CODMOEDA", pedCRMVO.getProperty("CODMOEDA"));
            contratoVO.setProperty("TIPPAG", "C");
            contratoVO.setProperty("CODTIPVENDA", pedCRMVO.getProperty("CODTIPVENDA"));
            contratoVO.setProperty("CODNAT", pedCRMVO.getProperty("CODNAT"));
            contratoVO.setProperty("CODCENCUS", pedCRMVO.getProperty("CODCENCUS"));
            contratoVO.setProperty("DIAPAG", BigDecimalUtil.valueOf(5L));
            contratoVO.setProperty("FREQREAJ", BigDecimalUtil.valueOf(12L));
            contratoVO.setProperty("CODUSU", AuthenticationInfo.getCurrent().getUserID());
            contratoVO.setProperty("AD_CODVEND", opoVO.getProperty("CODVEND"));
            contratoVO.setProperty("AD_CODPROSPECT", opoVO.getProperty("CODPROSPECT"));
            contratoVO.setProperty("AD_CODINDICA", opoVO.getProperty("CODINDICADOR"));
            Timestamp DataInicio = new Timestamp(TimeUtils.getMonthStart(opoVO.asTimestamp("DTPREVISTA").getTime()));
            contratoVO.setProperty("DTREFPROXFAT", DataInicio);
            contratoVO = (DynamicVO) dwfEntityFacade.createEntity("Contrato", (EntityVO) contratoVO).getValueObject();
        } catch (Exception e) {
            e.printStackTrace();
            MGEModelException.throwMe(e);
        }
        return BigDecimalUtil.getValueOrZero(contratoVO.asBigDecimal("NUMCONTRATO"));
    }

    private void createComissao(BigDecimal nuNota, BigDecimal codvend, String tipo) throws Exception {
        EntityFacade dwfFacade = EntityFacadeFactory.getDWFFacade();
        DynamicVO comissaoVO = (DynamicVO) dwfFacade.getDefaultValueObjectInstance("ComissaoMultipla");
        comissaoVO.setProperty("NUNOTA", nuNota);
        comissaoVO.setProperty("CODVEND", codvend);
        comissaoVO.setProperty("AD_TIPVEND", tipo);
        dwfFacade.createEntity("ComissaoMultipla", (EntityVO) comissaoVO);
    }

    private DynamicVO inserirCabecalho(CACHelper cacHelper, BigDecimal modeloNota, DynamicVO opoVO) throws Exception {
        DynamicVO pedidoPrincipalVO = null;
        try {
            EntityFacade dwfFacade = EntityFacadeFactory.getDWFFacade();
            pedidoPrincipalVO = (DynamicVO) dwfFacade.findEntityByPrimaryKeyAsVO("CabecalhoNota",
                    new Object[] { modeloNota });
            pedidoPrincipalVO.setPrimaryKey(null);
            pedidoPrincipalVO.clearReferences();
            String[] camposParaAnular = {
                    "DHTIPOPER", "STATUSNOTA", "DTNEG", "HRMOV", "DTMOV", "VOLUME", "QTDVOL", "CHAVENFE", "CODMAQ",
                    "DANFE",
                    "DTENTSAI", "DTFATUR", "DHPROTOC", "DHREGDPEC", "NULOTENFE", "NULOTENFSE", "NUMALEATORIO",
                    "NUMCOTACAO", "NUMNFSE", "NUMPROTOC",
                    "NUMREGDPEC", "NUNOTA", "NUNOTAPEDFRET", "TPEMISNFE", "TPEMISNFSE", "STATUSNFE", "STATUSNFSE",
                    "COMGER", "NUREM", "OBSERVACAO",
                    "NUMOS", "DTVAL", "NUMCONTRATO", "TIPMOV", "DHTIPVENDA" };
            for (String nomeCampo : camposParaAnular)
                pedidoPrincipalVO.setProperty(nomeCampo, null);
            DynamicVO topVO = ComercialUtils.getTipoOperacao(pedidoPrincipalVO.asBigDecimalOrZero("CODTIPOPER"));
            pedidoPrincipalVO.setProperty("TIPMOV", topVO.getProperty("TIPMOV"));
            pedidoPrincipalVO.setProperty("DTNEG", TimeUtils.clearTime(TimeUtils.getNow()));
            pedidoPrincipalVO.setProperty("DTFATUR", TimeUtils.getNow());
            if (opoVO.getProperty("DTPREVFAT") != null)
                pedidoPrincipalVO.setProperty("DTENTSAI", opoVO.getProperty("DTPREVFAT"));
            if (opoVO.getProperty("DTFECHAMENTO") != null)
                pedidoPrincipalVO.setProperty("DTNEG", TimeUtils.clearTime(opoVO.asTimestamp("DTFECHAMENTO")));
            if (opoVO.getProperty("CODTIPVENDA") != null)
                pedidoPrincipalVO.setProperty("CODTIPVENDA", opoVO.getProperty("CODTIPVENDA"));
            if (opoVO.getProperty("CODPARCTRANSP") != null)
                pedidoPrincipalVO.setProperty("CODPARCTRANSP", opoVO.getProperty("CODPARCTRANSP"));
            if (opoVO.getProperty("FRETE") != null)
                pedidoPrincipalVO.setProperty("CIF_FOB", opoVO.getProperty("FRETE"));
            pedidoPrincipalVO.setProperty("CODVEND", opoVO.asBigDecimalOrZero("CODVEND"));
            pedidoPrincipalVO.setProperty("STATUSNOTA", "P");
            pedidoPrincipalVO.setProperty("CODUSU", AuthenticationInfo.getCurrent().getUserID());
            pedidoPrincipalVO.setProperty("OBSERVACAO", opoVO.getProperty("OBSFAT"));
            DynamicVO prospectVO = (DynamicVO) dwfFacade.findEntityByPrimaryKeyAsVO("ParceiroProspect", opoVO
                    .asBigDecimalOrZero("CODPAP"));
            if (prospectVO == null)
                throw new Exception("NFoi PossRecuperar o Prospect");
            DynamicVO parceiroVO = (DynamicVO) dwfFacade.findEntityByPrimaryKeyAsVO("Parceiro", prospectVO
                    .asBigDecimalOrZero("CODPARC"));
            if (parceiroVO == null || parceiroVO.asBigDecimalOrZero("CODPARC").equals(BigDecimal.ZERO))
                throw new Exception("Prospect nfoi Convertido em Parceiro.");
            pedidoPrincipalVO.setProperty("CODPARC", parceiroVO.getProperty("CODPARC"));
            pedidoPrincipalVO.setProperty("ISSRETIDO",
                    (ImpostosHelpper.ehIssRetido(parceiroVO, pedidoPrincipalVO.asBigDecimal("CODEMP")) == true) ? "S"
                            : "N");
            cacHelper.incluirAlterarCabecalho(this.ctx,
                    PrePersistEntityState.build(dwfFacade, "CabecalhoNota", pedidoPrincipalVO, null, null));
        } catch (Exception e) {
            MGEModelException.throwMe(e);
        }
        return pedidoPrincipalVO;
    }

    private void incluiItem(DynamicVO pedidoConfirmedVO, DynamicVO itemVO) throws Exception {
        try {
            DynamicVO produtoVO = null;
            try {
                EntityFacade dwfFacade = EntityFacadeFactory.getDWFFacade();
                produtoVO = (DynamicVO) dwfFacade.findEntityByPrimaryKeyAsVO("Produto", new Object[] { itemVO
                        .asBigDecimalOrZero("CODPROD") });
            } catch (FinderException finderException) {
            }
            DynamicVO topVO = pedidoConfirmedVO.asDymamicVO("TipoOperacao");
            String topAtualEst = topVO.asString("ATUALEST");
            Element itensElem = new Element("itens");
            Element itemElem = new Element("item");
            itemElem.addContent((Content) (new Element("NUNOTA")).setText(null));
            CentralItemNota centralItemNota = new CentralItemNota();
            CentralItemNota.ParamsInicializacaoProduto params = new CentralItemNota.ParamsInicializacaoProduto();
            params.nuNota = pedidoConfirmedVO.asBigDecimal("NUNOTA");
            params.chamadoPelaTela = true;
            params.codProd = itemVO.asBigDecimalOrZero("CODPROD");
            ComercialUtils.PrecoUnitarioInfo pui = centralItemNota.inicializaProduto(params);
            BigDecimal vlrUnit = itemVO.asBigDecimalOrZero("VLRNEG");
            BigDecimal quantidade = itemVO.containsProperty("QTDNEG") ? itemVO.asBigDecimalOrZero("QTDNEG")
                    : itemVO.asBigDecimalOrZero("QTD");
            XMLUtils.addContentElement(itemElem, "CODPROD", itemVO.asBigDecimalOrZero("CODPROD"));
            XMLUtils.addContentElement(itemElem, "QTDNEG",
                    BigDecimalUtil.getRounded(quantidade, BigDecimalUtil.getValueOrZero(pui.getDecQtd()).intValue()));
            XMLUtils.addContentElement(itemElem, "CODVOL", pui.getCodVol());
            XMLUtils.addContentElement(itemElem, "VLRUNIT", vlrUnit);
            XMLUtils.addContentElement(itemElem, "CUSTO", pui.getCusto());
            XMLUtils.addContentElement(itemElem, "NUTAB", pui.getNuTab());
            XMLUtils.addContentElement(itemElem, "PRECOBASE", pui.getPrecoBase());
            XMLUtils.addContentElement(itemElem, "VLRCUS", pui.getVlrCus());
            XMLUtils.addContentElement(itemElem, "VLRDESC", BigDecimal.ZERO);
            XMLUtils.addContentElement(itemElem, "PERCDESC", BigDecimal.ZERO);
            XMLUtils.addContentElement(itemElem, "USOPROD", pui.getUsoProd());
            XMLUtils.addContentElement(itemElem, "VLRTOT", BigDecimalUtil.getRounded(vlrUnit.multiply(quantidade), 2));
            if (produtoVO != null &&
                    produtoVO.asString("USALOCAL").equalsIgnoreCase("S"))
                XMLUtils.addContentElement(itemElem, "CODLOCALORIG", BigDecimalUtil.valueOf(101L));
            if ("R".equals(topAtualEst)) {
                XMLUtils.addContentElement(itemElem, "RESERVA", "S");
            } else {
                XMLUtils.addContentElement(itemElem, "RESERVA", "N");
            }
            itensElem.addContent((Content) itemElem);
            this.ctx.addClientEvent("br.com.sankhya.mgecomercial.event.estoque.insuficiente.produto", null);
            String podeEfetivar = "central.notas.pode.efetivar_" + itemVO.asBigDecimalOrZero("CODPROD").toString();
            JapeSession.getCurrentSession().addTxProperty(podeEfetivar, Boolean.valueOf(true));
            this.cacHelper.incluirAlterarItem(pedidoConfirmedVO.asBigDecimal("NUNOTA"), this.ctx, itensElem, false);
        } catch (Exception e) {
            throw e;
        }
    }
}
