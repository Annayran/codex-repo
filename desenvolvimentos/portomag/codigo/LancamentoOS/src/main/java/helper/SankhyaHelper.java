package helper;
import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.bmp.PersistentLocalEntity;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.util.JapeSessionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.jape.vo.PrePersistEntityState;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.comercial.*;
import br.com.sankhya.modelcore.comercial.centrais.CACHelper;
import br.com.sankhya.modelcore.comercial.impostos.ImpostosHelpper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.ResultSet;

public class SankhyaHelper {
    private static final BigDecimal PRODUTO_SERVICO_INSTALACAO = new BigDecimal("202160");
    private BarramentoRegra.DadosBarramento dadosBarramento;
    private final EntityFacade dwFacade = EntityFacadeFactory.getDWFFacade();
    private final JdbcWrapper jdbc = dwFacade.getJdbcWrapper();

    public static void inicializarValoresCabecalho(DynamicVO cabecalho, DynamicVO apontamento) {
        // Modelo fornece configuracoes, nao os valores de outro documento.
        String[] campos = {"VLRNOTA", "VLRDESCTOT", "VLRDESCTOTITEM", "VLRDESCSERV", "PERCDESC", "VLRFRETE"};
        for (String campo : campos) cabecalho.setProperty(campo, BigDecimal.ZERO);
        cabecalho.setProperty("VLRNOTA", ValorAtividade.total(apontamento.asBigDecimal("VLRSERVICO"),
                apontamento.asBigDecimal("QTDNEG")));
    }

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

    public DynamicVO validarItem(DynamicVO item) throws Exception {
        BigDecimal produto = item.asBigDecimal("CODPROD");
        BigDecimal quantidade = item.asBigDecimal("QTDNEG");
        if (produto == null || produto.signum() <= 0 || quantidade == null || quantidade.signum() <= 0) {
            throw new IllegalArgumentException("Preencha CODPROD e QTDNEG positiva no apontamento.");
        }
        ValorAtividade.total(item.asBigDecimal("VLRSERVICO"), quantidade);
        DynamicVO produtoOriginal = JapeFactory.dao(DynamicEntityNames.PRODUTO).findByPK(produto);
        if (produtoOriginal == null) {
            throw new IllegalArgumentException("Produto original do apontamento nao encontrado.");
        }
        DynamicVO produtoServico = JapeFactory.dao(DynamicEntityNames.PRODUTO).findByPK(PRODUTO_SERVICO_INSTALACAO);
        if (produtoServico == null) {
            throw new IllegalArgumentException("Produto de servico 202160 nao encontrado.");
        }
        String referenciaEngenharia = item.asString("REFERENCIA");
        if (referenciaEngenharia == null || referenciaEngenharia.trim().isEmpty()) {
            referenciaEngenharia = produtoOriginal.asString("REFERENCIA");
        }
        if (referenciaEngenharia == null || referenciaEngenharia.trim().isEmpty()) {
            throw new IllegalArgumentException("Referencia de engenharia nao informada no apontamento/produto original.");
        }
        String descricaoOriginal = produtoOriginal.asString("DESCRPROD");
        if (descricaoOriginal == null || descricaoOriginal.trim().isEmpty()) {
            throw new IllegalArgumentException("Descricao do produto original nao informada.");
        }
        String site = item.asString("SITEDEF");
        if (site == null || !site.trim().matches("[0-9]+")) {
            throw new IllegalArgumentException("SITEDEF deve conter a SEQUENCIA numerica do cadastro de site.");
        }
        DynamicVO siteVO = JapeFactory.dao("AD_SITE").findByPK(new BigDecimal(site.trim()));
        if (siteVO == null) throw new IllegalArgumentException("Site informado no apontamento nao encontrado.");
        return siteVO;
    }

    public void inseriItem(DynamicVO cabecalhoVO,DynamicVO item) throws Exception {
        inseriItem(cabecalhoVO, item, validarItem(item));
    }

    public void inseriItem(DynamicVO cabecalhoVO, DynamicVO item, DynamicVO siteVO) throws Exception {
        System.out.println(">>> [inseriItem] Iniciando inserção de item...");

        CentralItemNota centralItemNota = new CentralItemNota();
        CentralItemNota.ParamsInicializacaoProduto params = new CentralItemNota.ParamsInicializacaoProduto();

        BigDecimal codtop = cabecalhoVO.asBigDecimal("CODTIPOPER");
        Timestamp dhTipoOper = cabecalhoVO.asTimestamp("DHTIPOPER");
        if (codtop == null || dhTipoOper == null) {
            throw new IllegalArgumentException("Cabecalho sem CODTIPOPER/DHTIPOPER para inserir o item.");
        }
        // getTipoOperacao(codigo, data) procura DHALTER < data, nao a PK exata.
        DynamicVO topVO = JapeFactory.dao("TipoOperacao").findByPK(codtop, dhTipoOper);
        if (topVO == null) {
            throw new IllegalStateException("Versao exata da TOP do cabecalho nao encontrada.");
        }
        System.out.println("[PORTOMAG-OS] TOP_ITEM_CHAVE_EXATA nunota=" + cabecalhoVO.asBigDecimal("NUNOTA")
                + " codTop=" + codtop + " dhTop=" + dhTipoOper);

        String topAtualEst = topVO.asString("ATUALEST");
        BigDecimal topAtualEstMP = topVO.asBigDecimal("ATUALESTMP");
        String estNaConfirmacao = topVO.asString("ADIARATUALEST");

        params.nuNota = cabecalhoVO.asBigDecimal("NUNOTA");
        params.chamadoPelaTela = true;
        params.codProd = PRODUTO_SERVICO_INSTALACAO;

        System.out.println(">>> [inseriItem] NUNOTA: " + params.nuNota);
        System.out.println(">>> [inseriItem] Produto (COD): " + params.codProd);

        ComercialUtils.PrecoUnitarioInfo pui = centralItemNota.inicializaProduto(params);

        DynamicVO itemVO = (DynamicVO) dwFacade.getDefaultValueObjectInstance(DynamicEntityNames.ITEM_NOTA);


        itemVO.setProperty("NUNOTA", cabecalhoVO.asBigDecimal("NUNOTA"));
        BigDecimal produtoOriginal = item.asBigDecimal("CODPROD");
        DynamicVO produtoOriginalVO = JapeFactory.dao(DynamicEntityNames.PRODUTO).findByPK(produtoOriginal);
        if (produtoOriginalVO == null) {
            throw new IllegalStateException("Produto original deixou de estar disponivel durante a geracao.");
        }
        String referenciaEngenharia = item.asString("REFERENCIA");
        if (referenciaEngenharia == null || referenciaEngenharia.trim().isEmpty()) {
            referenciaEngenharia = produtoOriginalVO.asString("REFERENCIA");
        }
        itemVO.setProperty("CODPROD", PRODUTO_SERVICO_INSTALACAO);
        itemVO.setProperty("PRODUTONFE", referenciaEngenharia.trim());
        itemVO.setProperty("AD_PRODINST", produtoOriginalVO.asString("DESCRPROD").trim());
        BigDecimal quantidade = item.asBigDecimal("QTDNEG");
        BigDecimal valorAtividade = item.asBigDecimal("VLRSERVICO");
        BigDecimal totalServico = ValorAtividade.total(valorAtividade, quantidade);
        // A soma padrao usa VLRTOT + VLRIPI - VLRDESC - VLRREPRED.
        // Sem valores iniciais, aritmetica SQL com NULL pode anular o item.
        itemVO.setProperty("VLRDESC", BigDecimal.ZERO);
        itemVO.setProperty("PERCDESC", BigDecimal.ZERO);
        itemVO.setProperty("VLRIPI", BigDecimal.ZERO);
        itemVO.setProperty("VLRREPRED", BigDecimal.ZERO);
        itemVO.setProperty("QTDNEG", quantidade);
        itemVO.setProperty("VLRUNIT", ValorAtividade.unitario(valorAtividade, quantidade));
        itemVO.setProperty("VLRTOT", totalServico);
        itemVO.setProperty("AD_SITEDEF", item.asString("SITEDEF"));
        itemVO.setProperty("AD_NOMECID", siteVO.asString("CIDADE"));
        itemVO.setProperty("AD_UF", item.asString("UF")); //criar campo
        itemVO.setProperty("AD_CEP", siteVO.asBigDecimal("COSPOSTAL"));
        itemVO.setProperty("CODVOL", pui.getCodVol());
        itemVO.setProperty("CUSTO", pui.getCusto());
        itemVO.setProperty("NUTAB", pui.getNuTab());
        itemVO.setProperty("PRECOBASE", pui.getPrecoBase());
        itemVO.setProperty("VLRCUS", pui.getVlrCus());
        itemVO.setProperty("USOPROD", pui.getUsoProd());
        itemVO.setProperty("CODVEND", cabecalhoVO.asBigDecimal("CODVEND"));
        // itemVO.setProperty("OBSERVACAO", obs);
        //itemVO.setProperty("CODLOCALORIG", new BigDecimal(90100000));


        if ( "M".equals(pui.getUsoProd()) ) {
            itemVO.setProperty("ATUALESTOQUE", topAtualEstMP);
            System.out.println(">>> [inseriItem] Estoque atualizado para MP: " + topAtualEstMP);

        } else {

            if ( "B".equals(topAtualEst) ) {
                itemVO.setProperty("ATUALESTOQUE", BigDecimal.valueOf(-1));
                System.out.println(">>> [inseriItem] Estoque atualizado para -1 (B)");

            } else {
                itemVO.setProperty("ATUALESTOQUE", ("R".equals(topAtualEst) || "E".equals(topAtualEst)) ? BigDecimal.ONE : BigDecimal.ZERO);

            }

            if ( "S".equals(estNaConfirmacao) ) {
                itemVO.setProperty("ATUALESTOQUE", BigDecimal.ZERO);
            }
        }

        if ( "R".equals(topAtualEst) ) {
            itemVO.setProperty("RESERVA", "S");
        } else {
            itemVO.setProperty("RESERVA", "N");
        }

        PersistentLocalEntity itemPersistido = dwFacade.createEntity(DynamicEntityNames.ITEM_NOTA, (EntityVO) itemVO);
        DynamicVO gravado = (DynamicVO) itemPersistido.getValueObject();
        if (gravado.asBigDecimal("VLRTOT") == null
                || gravado.asBigDecimal("VLRTOT").compareTo(totalServico) != 0
                || gravado.asBigDecimal("QTDNEG") == null
                || gravado.asBigDecimal("QTDNEG").compareTo(quantidade) != 0) {
            throw new IllegalStateException("Valor/quantidade do item persistido diverge da atividade. OS nao vinculada.");
        }
        System.out.println(">>> [inseriItem] Item persistido com sucesso para NUNOTA " + cabecalhoVO.asBigDecimal("NUNOTA"));

        //centralItemNota.recalcularValores("QTDNEG", null, itemVO, cabecalhoVO.asBigDecimal("NUNOTA"));
//        System.out.println(">>> [inseriItem] Recalculando impostos e financeiro...");

//        recalcularImpost(cabecalhoVO.asBigDecimal("NUNOTA"));
//        refazerFinanceiroForce(cabecalhoVO.asBigDecimal("NUNOTA"));
        System.out.println(">>> [inseriItem] Finalizado inserção do item para NUNOTA " + cabecalhoVO.asBigDecimal("NUNOTA"));
        System.out.println("--------------------------------------------------------");

    }

    public BigDecimal totalizarEValidar(BigDecimal nuNota, DynamicVO apontamento) throws Exception {
        BigDecimal esperado = ValorAtividade.total(apontamento.asBigDecimal("VLRSERVICO"),
                apontamento.asBigDecimal("QTDNEG"));
        ImpostosHelpper totalizador = new ImpostosHelpper();
        totalizador.carregarNota(nuNota);
        totalizador.totalizarNota(nuNota);
        // Ler na mesma conexao/transacao, sem depender do VO clonado/cacheado.
        NativeSql consulta = new NativeSql(jdbc);
        BigDecimal totalNota;
        try {
            consulta.appendSql("SELECT C.VLRNOTA, (SELECT SUM(I.VLRTOT) FROM TGFITE I "
                    + "WHERE I.NUNOTA = C.NUNOTA AND I.SEQUENCIA > 0) AS TOTAL_ITENS "
                    + "FROM TGFCAB C WHERE C.NUNOTA = :NUNOTA");
            consulta.setNamedParameter("NUNOTA", nuNota);
            try (ResultSet rs = consulta.executeQuery()) {
                if (!rs.next()) throw new IllegalStateException("Cabecalho nao encontrado apos totalizacao.");
                totalNota = rs.getBigDecimal("VLRNOTA");
                BigDecimal totalItens = rs.getBigDecimal("TOTAL_ITENS");
                System.out.println("[PORTOMAG-OS] TOTAL_BANCO nunota=" + nuNota
                        + " esperado=" + esperado + " itens=" + totalItens + " cabecalho=" + totalNota);
                if (totalItens == null || totalItens.compareTo(esperado) != 0) {
                    throw new IllegalStateException("Soma dos itens no banco diverge de VLRSERVICO. OS nao vinculada.");
                }
            }
        } finally {
            NativeSql.releaseResources(consulta);
        }
        if (totalNota == null || totalNota.compareTo(esperado) != 0) {
            throw new IllegalStateException("Total da OS diverge de VLRSERVICO: esperado="
                    + esperado + ", obtido=" + totalNota
                    + ". Verifique descontos, frete e regras da TOP/modelo. OS nao vinculada.");
        }
        return totalNota;
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
            e.printStackTrace();
            throw new Exception(e.getMessage());

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
