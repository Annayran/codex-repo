package br.com.zaya.like.Actions.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.zaya.like.Actions.dto.DocumentoGeradoDTO;
import br.com.zaya.like.Actions.dto.ItemMomentoNegociacaoDTO;
import br.com.zaya.like.Actions.dto.ItemSuprimentoDTO;
import br.com.zaya.like.Actions.dto.MomentoNegociacaoDTO;
import br.com.zaya.like.Actions.dto.NotaInternaDTO;

public class MomentoNegociacaoDAO {

    public static MomentoNegociacaoDTO buscarMomento(BigDecimal nunegociacao) throws Exception {
        NativeSql sql = null;
        ResultSet rs = null;

        try {
            sql = criarNativeSql();
            sql.appendSql("SELECT ");
            sql.appendSql("    CAB.NUNEGOCIACAO, ");
            sql.appendSql("    DASH.CLIENTE, ");
            sql.appendSql("    DASH.VENDEDOR, ");
            sql.appendSql("    CAB.STATUSPROPOSTA AS STATUS_NEGOCIACAO, ");
            sql.appendSql("    DASH.STATUS_SUPRIMENTO, ");
            sql.appendSql("    DASH.STATUS_LAYOUT, ");
            sql.appendSql("    NVL(DASH.STATUS_PRODUCAO_FINAL, 'Sem Producao') AS STATUS_PRODUCAO_FINAL, ");
            sql.appendSql("    DASH.STATUS_FATURAMENTO, ");
            sql.appendSql("    DASH.STATUS_ENTREGA ");
            sql.appendSql("FROM AD_TGLCAB CAB ");
            sql.appendSql("LEFT JOIN AD_TCRNEGCAB DASH ON DASH.NUNEGOCIACAO = CAB.NUNEGOCIACAO ");
            sql.appendSql("WHERE CAB.NUNEGOCIACAO = :NUNEGOCIACAO");
            sql.setNamedParameter("NUNEGOCIACAO", nunegociacao);

            rs = sql.executeQuery();

            if (!rs.next()) {
                throw new Exception("Negociacao [" + nunegociacao + "] nao encontrada em AD_TGLCAB.");
            }

            MomentoNegociacaoDTO dto = new MomentoNegociacaoDTO();
            dto.setNuNegociacao(rs.getBigDecimal("NUNEGOCIACAO"));
            dto.setNumeroNegociacao(rs.getBigDecimal("NUNEGOCIACAO"));
            dto.setCliente(rs.getString("CLIENTE"));
            dto.setVendedor(rs.getString("VENDEDOR"));
            dto.setStatusNegociacao(rs.getString("STATUS_NEGOCIACAO"));
            dto.setStatusSuprimento(rs.getString("STATUS_SUPRIMENTO"));
            dto.setStatusLayout(rs.getString("STATUS_LAYOUT"));
            dto.setStatusProducaoFinal(rs.getString("STATUS_PRODUCAO_FINAL"));
            dto.setStatusFaturamento(rs.getString("STATUS_FATURAMENTO"));
            dto.setStatusEntrega(rs.getString("STATUS_ENTREGA"));

            return dto;
        } finally {
            fechar(rs, sql);
        }
    }

    public static List<ItemMomentoNegociacaoDTO> buscarItensPrincipais(BigDecimal nunegociacao) throws Exception {
        NativeSql sql = null;
        ResultSet rs = null;
        List<ItemMomentoNegociacaoDTO> itens = new ArrayList<ItemMomentoNegociacaoDTO>();

        try {
            sql = criarNativeSql();
            sql.appendSql("WITH ");
            sql.appendSql("    ART AS ( ");
            sql.appendSql("        SELECT ");
            sql.appendSql("            NUNEGOCIACAO, ");
            sql.appendSql("            SEQUENCIA, ");
            sql.appendSql("            IDARTE, ");
            sql.appendSql("            OPTION_LABEL('AD_TGLITEART','STATUS', STATUS) AS STATUS, ");
            sql.appendSql("            OBS, ");
            sql.appendSql("            OBSARTE, ");
            sql.appendSql("            TITULO ");
            sql.appendSql("        FROM AD_TGLITEART ");
            sql.appendSql("    ), ");
            sql.appendSql("    PRO AS ( ");
            sql.appendSql("        SELECT ");
            sql.appendSql("            AD_IDPRODPAI, ");
            sql.appendSql("            CODPROD, ");
            sql.appendSql("            REFERENCIA, ");
            sql.appendSql("            DESCRPROD, ");
            sql.appendSql("            OPTION_LABEL('TGFPRO','AD_TIPO', AD_TIPO) AS AD_TIPO ");
            sql.appendSql("        FROM TGFPRO ");
            sql.appendSql("    ), ");
            sql.appendSql("    INEG AS ( ");
            sql.appendSql("        SELECT ");
            sql.appendSql("            NUNEGOCIACAO, ");
            sql.appendSql("            SEQUENCIA, ");
            sql.appendSql("            IDARTE, ");
            sql.appendSql("            CODPROD, ");
            sql.appendSql("            STATUS_SUPRIMENTO_N, ");
            sql.appendSql("            STATUS_SUPRIMENTO, ");
            sql.appendSql("            STATUS_PROD, ");
            sql.appendSql("            QTDNEG, ");
            sql.appendSql("            CASE WHEN AD_TIPO <> 'C' THEN QTD_LIB_CQ END AS QTD_LIB_CQ, ");
            sql.appendSql("            CASE WHEN AD_TIPO <> 'C' THEN QTD_LIB_ESTOQUE END AS QTD_LIB_ESTOQUE, ");
            sql.appendSql("            CASE WHEN AD_TIPO <> 'C' THEN QTD_DISP_ESTOQUE END AS QTD_DISP_ESTOQUE, ");
            sql.appendSql("            CASE WHEN AD_TIPO <> 'C' THEN QTD_PEN_COMPRA END AS QTD_PEN_COMPRA, ");
            sql.appendSql("            NVL(QTD_DISP_FATURAMENTO, 0) AS QTD_DISP_FATURAMENTO, ");
            sql.appendSql("            QTDFATURADA ");
            sql.appendSql("        FROM AD_TCRNEGITE INEG ");
            sql.appendSql("        WHERE INEG.NUNEGOCIACAO = :NUNEGOCIACAO ");
            sql.appendSql("        AND CLASSIFICACAO_PRODUTO = 'Principal' ");
            sql.appendSql("    ) ");
            sql.appendSql("SELECT ");
            sql.appendSql("    INEG.NUNEGOCIACAO, ");
            sql.appendSql("    INEG.SEQUENCIA, ");
            sql.appendSql("    INEG.IDARTE, ");
            sql.appendSql("    PRO.REFERENCIA, ");
            sql.appendSql("    PRO.CODPROD, ");
            sql.appendSql("    PRO.DESCRPROD, ");
            sql.appendSql("    PRO.AD_TIPO, ");
            sql.appendSql("    ART.STATUS AS STATUS_ARTE, ");
            sql.appendSql("    ART.OBS, ");
            sql.appendSql("    ART.OBSARTE, ");
            sql.appendSql("    ART.TITULO, ");
            sql.appendSql("    INEG.STATUS_SUPRIMENTO, ");
            sql.appendSql("    INEG.STATUS_PROD, ");
            sql.appendSql("    INEG.QTDNEG, ");
            sql.appendSql("    INEG.QTD_LIB_CQ, ");
            sql.appendSql("    INEG.QTD_LIB_ESTOQUE, ");
            sql.appendSql("    INEG.QTD_DISP_ESTOQUE, ");
            sql.appendSql("    INEG.QTD_PEN_COMPRA, ");
            sql.appendSql("    INEG.QTD_DISP_FATURAMENTO, ");
            sql.appendSql("    INEG.QTDFATURADA ");
            sql.appendSql("FROM INEG ");
            sql.appendSql("JOIN PRO ON PRO.CODPROD = INEG.CODPROD ");
            sql.appendSql("LEFT JOIN ART ON ART.NUNEGOCIACAO = INEG.NUNEGOCIACAO ");
            sql.appendSql("    AND ART.SEQUENCIA = INEG.SEQUENCIA ");
            sql.appendSql("    AND ART.IDARTE = INEG.IDARTE ");
            sql.appendSql("ORDER BY INEG.STATUS_SUPRIMENTO_N, INEG.SEQUENCIA");
            sql.setNamedParameter("NUNEGOCIACAO", nunegociacao);

            rs = sql.executeQuery();

            while (rs.next()) {
                ItemMomentoNegociacaoDTO dto = new ItemMomentoNegociacaoDTO();
                dto.setNuNegociacao(rs.getBigDecimal("NUNEGOCIACAO"));
                dto.setSequencia(rs.getBigDecimal("SEQUENCIA"));
                dto.setIdArte(rs.getBigDecimal("IDARTE"));
                dto.setReferencia(rs.getString("REFERENCIA"));
                dto.setCodProduto(rs.getBigDecimal("CODPROD"));
                dto.setDescricaoProduto(rs.getString("DESCRPROD"));
                dto.setTipoProduto(rs.getString("AD_TIPO"));
                dto.setStatusArte(rs.getString("STATUS_ARTE"));
                dto.setObservacao(rs.getString("OBS"));
                dto.setObservacaoArte(rs.getString("OBSARTE"));
                dto.setTituloArte(rs.getString("TITULO"));
                dto.setStatusSuprimento(rs.getString("STATUS_SUPRIMENTO"));
                dto.setStatusProducao(rs.getString("STATUS_PROD"));
                dto.setQtdNegociada(rs.getBigDecimal("QTDNEG"));
                dto.setQtdLiberadaCQ(rs.getBigDecimal("QTD_LIB_CQ"));
                dto.setQtdLiberadaEstoque(rs.getBigDecimal("QTD_LIB_ESTOQUE"));
                dto.setQtdDisponivelEstoque(rs.getBigDecimal("QTD_DISP_ESTOQUE"));
                dto.setQtdPendenteCompra(rs.getBigDecimal("QTD_PEN_COMPRA"));
                dto.setQtdDisponivelFaturamento(rs.getBigDecimal("QTD_DISP_FATURAMENTO"));
                dto.setQtdFaturada(rs.getBigDecimal("QTDFATURADA"));
                itens.add(dto);
            }

            return itens;
        } finally {
            fechar(rs, sql);
        }
    }

    public static DocumentoGeradoDTO buscarPedidoVendaRelacionado(BigDecimal nunegociacao) throws Exception {
        List<DocumentoGeradoDTO> pedidos = buscarPedidosVendaRelacionados(nunegociacao);
        return pedidos.isEmpty() ? null : pedidos.get(0);
    }

    public static List<DocumentoGeradoDTO> buscarPedidosVendaRelacionados(BigDecimal nunegociacao) throws Exception {
        NativeSql sql = null;
        ResultSet rs = null;
        List<DocumentoGeradoDTO> pedidos = new ArrayList<DocumentoGeradoDTO>();

        try {
            sql = criarNativeSql();
            sql.appendSql("SELECT ");
            sql.appendSql("    DOC.NUNEGOCIACAO, ");
            sql.appendSql("    CAB.NUNOTA, ");
            sql.appendSql("    CAB.NUMNOTA, ");
            sql.appendSql("    CAB.CODTIPOPER, ");
            sql.appendSql("    CAB.TIPMOV, ");
            sql.appendSql("    TPO.DESCROPER ");
            sql.appendSql("FROM AD_VGLDOC DOC ");
            sql.appendSql("JOIN TGFCAB CAB ON CAB.NUNOTA = DOC.NUNOTA ");
            sql.appendSql("LEFT JOIN TGFTOP TPO ON TPO.CODTIPOPER = CAB.CODTIPOPER ");
            sql.appendSql("    AND TPO.DHALTER = CAB.DHTIPOPER ");
            sql.appendSql("WHERE DOC.NUNEGOCIACAO = :NUNEGOCIACAO ");
            sql.appendSql("AND CAB.TIPMOV = 'P' ");
            sql.appendSql("ORDER BY CAB.NUNOTA DESC");
            sql.setNamedParameter("NUNEGOCIACAO", nunegociacao);

            rs = sql.executeQuery();

            while (rs.next()) {
                DocumentoGeradoDTO dto = new DocumentoGeradoDTO();
                dto.setNuNegociacao(rs.getBigDecimal("NUNEGOCIACAO"));
                dto.setNuNota(rs.getBigDecimal("NUNOTA"));
                dto.setNumeroNota(rs.getBigDecimal("NUMNOTA"));
                dto.setCodTipOper(rs.getBigDecimal("CODTIPOPER"));
                dto.setTipMov(rs.getString("TIPMOV"));
                dto.setDescricaoTipOper(rs.getString("DESCROPER"));
                pedidos.add(dto);
            }

            return pedidos;
        } finally {
            fechar(rs, sql);
        }
    }

    public static DocumentoGeradoDTO buscarPedidoVendaRelacionado(BigDecimal nunegociacao, BigDecimal nunota)
            throws Exception {
        NativeSql sql = null;
        ResultSet rs = null;

        try {
            sql = criarNativeSql();
            sql.appendSql("SELECT ");
            sql.appendSql("    DOC.NUNEGOCIACAO, ");
            sql.appendSql("    CAB.NUNOTA, ");
            sql.appendSql("    CAB.NUMNOTA, ");
            sql.appendSql("    CAB.CODTIPOPER, ");
            sql.appendSql("    CAB.TIPMOV, ");
            sql.appendSql("    TPO.DESCROPER ");
            sql.appendSql("FROM AD_VGLDOC DOC ");
            sql.appendSql("JOIN TGFCAB CAB ON CAB.NUNOTA = DOC.NUNOTA ");
            sql.appendSql("LEFT JOIN TGFTOP TPO ON TPO.CODTIPOPER = CAB.CODTIPOPER ");
            sql.appendSql("    AND TPO.DHALTER = CAB.DHTIPOPER ");
            sql.appendSql("WHERE DOC.NUNEGOCIACAO = :NUNEGOCIACAO ");
            sql.appendSql("AND CAB.NUNOTA = :NUNOTA ");
            sql.appendSql("AND CAB.TIPMOV = 'P' ");
            sql.setNamedParameter("NUNEGOCIACAO", nunegociacao);
            sql.setNamedParameter("NUNOTA", nunota);

            rs = sql.executeQuery();

            if (!rs.next()) {
                return null;
            }

            DocumentoGeradoDTO dto = new DocumentoGeradoDTO();
            dto.setNuNegociacao(rs.getBigDecimal("NUNEGOCIACAO"));
            dto.setNuNota(rs.getBigDecimal("NUNOTA"));
            dto.setNumeroNota(rs.getBigDecimal("NUMNOTA"));
            dto.setCodTipOper(rs.getBigDecimal("CODTIPOPER"));
            dto.setTipMov(rs.getString("TIPMOV"));
            dto.setDescricaoTipOper(rs.getString("DESCROPER"));
            return dto;
        } finally {
            fechar(rs, sql);
        }
    }

    public static NotaInternaDTO buscarNotaInterna(BigDecimal nunegociacao) throws Exception {
        NativeSql sql = null;
        ResultSet rs = null;

        try {
            sql = criarNativeSql();
            sql.appendSql("SELECT ");
            sql.appendSql("    CAB.AD_NUNEGOCIACAO AS NUNEGOCIACAO, ");
            sql.appendSql("    CAB.NUNOTA, ");
            sql.appendSql("    CAB.NUMNOTA, ");
            sql.appendSql("    CAB.CODTIPOPER, ");
            sql.appendSql("    CAB.TIPMOV ");
            sql.appendSql("FROM TGFCAB CAB ");
            sql.appendSql("WHERE CAB.AD_NUNEGOCIACAO = :NUNEGOCIACAO ");
            sql.appendSql("AND NOT EXISTS ( ");
            sql.appendSql("    SELECT 1 ");
            sql.appendSql("    FROM AD_VGLDOC DOC ");
            sql.appendSql("    JOIN TGFCAB CABDOC ON CABDOC.NUNOTA = DOC.NUNOTA ");
            sql.appendSql("    WHERE DOC.NUNEGOCIACAO = :NUNEGOCIACAO ");
            sql.appendSql("    AND DOC.NUNOTA = CAB.NUNOTA ");
            sql.appendSql("    AND CABDOC.TIPMOV = 'P' ");
            sql.appendSql(") ");
            sql.appendSql("ORDER BY CAB.NUNOTA DESC");
            sql.setNamedParameter("NUNEGOCIACAO", nunegociacao);

            rs = sql.executeQuery();

            if (!rs.next()) {
                return null;
            }

            NotaInternaDTO dto = new NotaInternaDTO();
            dto.setNuNegociacao(rs.getBigDecimal("NUNEGOCIACAO"));
            dto.setNuNota(rs.getBigDecimal("NUNOTA"));
            dto.setNumeroNota(rs.getBigDecimal("NUMNOTA"));
            dto.setCodTipOper(rs.getBigDecimal("CODTIPOPER"));
            dto.setTipMov(rs.getString("TIPMOV"));
            return dto;
        } finally {
            fechar(rs, sql);
        }
    }

    public static List<ItemSuprimentoDTO> buscarItensSuprimento(BigDecimal nunota) throws Exception {
        NativeSql sql = null;
        ResultSet rs = null;
        List<ItemSuprimentoDTO> itens = new ArrayList<ItemSuprimentoDTO>();

        try {
            sql = criarNativeSql();
            sql.appendSql("SELECT ");
            sql.appendSql("    ITE.NUNOTA, ");
            sql.appendSql("    ITE.SEQUENCIA, ");
            sql.appendSql("    ITE.CODPROD, ");
            sql.appendSql("    PRO.REFERENCIA, ");
            sql.appendSql("    PRO.DESCRPROD, ");
            sql.appendSql("    ITE.QTDNEG, ");
            sql.appendSql("    NVL(ITE.QTDENTREGUE, 0) AS QTDENTREGUE, ");
            sql.appendSql("    ITE.PENDENTE, ");
            sql.appendSql("    NVL(ACT.AD_ITEMCANCELADO, 'N') AS AD_ITEMCANCELADO, ");
            sql.appendSql("    ACT.OCORRENCIAS AS OCORRENCIA_CANCELAMENTO ");
            sql.appendSql("FROM TGFITE ITE ");
            sql.appendSql("LEFT JOIN TGFPRO PRO ON PRO.CODPROD = ITE.CODPROD ");
            sql.appendSql("LEFT JOIN ( ");
            sql.appendSql("    SELECT NUNOTA, SEQUENCIA, AD_ITEMCANCELADO, OCORRENCIAS ");
            sql.appendSql("    FROM ( ");
            sql.appendSql("        SELECT NUNOTA, SEQUENCIA, AD_ITEMCANCELADO, OCORRENCIAS, ");
            sql.appendSql("               ROW_NUMBER() OVER (PARTITION BY NUNOTA, SEQUENCIA ORDER BY DHOCOR DESC, HRACT DESC) AS RN ");
            sql.appendSql("        FROM TGFACT ");
            sql.appendSql("        WHERE AD_ITEMCANCELADO = 'S' ");
            sql.appendSql("    ) ");
            sql.appendSql("    WHERE RN = 1 ");
            sql.appendSql(") ACT ON ACT.NUNOTA = ITE.NUNOTA AND ACT.SEQUENCIA = ITE.SEQUENCIA ");
            sql.appendSql("WHERE ITE.NUNOTA = :NUNOTA ");
            sql.appendSql("ORDER BY ITE.SEQUENCIA");
            sql.setNamedParameter("NUNOTA", nunota);

            rs = sql.executeQuery();

            while (rs.next()) {
                ItemSuprimentoDTO dto = new ItemSuprimentoDTO();
                dto.setNuNota(rs.getBigDecimal("NUNOTA"));
                dto.setSequencia(rs.getBigDecimal("SEQUENCIA"));
                dto.setCodProduto(rs.getBigDecimal("CODPROD"));
                dto.setReferencia(rs.getString("REFERENCIA"));
                dto.setDescricaoProduto(rs.getString("DESCRPROD"));
                dto.setQtdNeg(rs.getBigDecimal("QTDNEG"));
                dto.setQtdEntregue(rs.getBigDecimal("QTDENTREGUE"));
                dto.setPendente(rs.getString("PENDENTE"));
                dto.setItemCancelado(rs.getString("AD_ITEMCANCELADO"));
                dto.setOcorrenciaCancelamento(rs.getString("OCORRENCIA_CANCELAMENTO"));
                itens.add(dto);
            }

            return itens;
        } finally {
            fechar(rs, sql);
        }
    }

    public static ItemSuprimentoDTO buscarItemSuprimento(BigDecimal nunota, BigDecimal sequencia) throws Exception {
        NativeSql sql = null;
        ResultSet rs = null;

        try {
            sql = criarNativeSql();
            sql.appendSql("SELECT ");
            sql.appendSql("    ITE.NUNOTA, ");
            sql.appendSql("    ITE.SEQUENCIA, ");
            sql.appendSql("    ITE.CODPROD, ");
            sql.appendSql("    PRO.REFERENCIA, ");
            sql.appendSql("    PRO.DESCRPROD, ");
            sql.appendSql("    ITE.QTDNEG, ");
            sql.appendSql("    NVL(ITE.QTDENTREGUE, 0) AS QTDENTREGUE, ");
            sql.appendSql("    ITE.PENDENTE, ");
            sql.appendSql("    NVL(ACT.AD_ITEMCANCELADO, 'N') AS AD_ITEMCANCELADO, ");
            sql.appendSql("    ACT.OCORRENCIAS AS OCORRENCIA_CANCELAMENTO ");
            sql.appendSql("FROM TGFITE ITE ");
            sql.appendSql("LEFT JOIN TGFPRO PRO ON PRO.CODPROD = ITE.CODPROD ");
            sql.appendSql("LEFT JOIN ( ");
            sql.appendSql("    SELECT NUNOTA, SEQUENCIA, AD_ITEMCANCELADO, OCORRENCIAS ");
            sql.appendSql("    FROM ( ");
            sql.appendSql("        SELECT NUNOTA, SEQUENCIA, AD_ITEMCANCELADO, OCORRENCIAS, ");
            sql.appendSql("               ROW_NUMBER() OVER (PARTITION BY NUNOTA, SEQUENCIA ORDER BY DHOCOR DESC, HRACT DESC) AS RN ");
            sql.appendSql("        FROM TGFACT ");
            sql.appendSql("        WHERE AD_ITEMCANCELADO = 'S' ");
            sql.appendSql("    ) ");
            sql.appendSql("    WHERE RN = 1 ");
            sql.appendSql(") ACT ON ACT.NUNOTA = ITE.NUNOTA AND ACT.SEQUENCIA = ITE.SEQUENCIA ");
            sql.appendSql("WHERE ITE.NUNOTA = :NUNOTA ");
            sql.appendSql("AND ITE.SEQUENCIA = :SEQUENCIA");
            sql.setNamedParameter("NUNOTA", nunota);
            sql.setNamedParameter("SEQUENCIA", sequencia);

            rs = sql.executeQuery();

            if (!rs.next()) {
                return null;
            }

            ItemSuprimentoDTO dto = new ItemSuprimentoDTO();
            dto.setNuNota(rs.getBigDecimal("NUNOTA"));
            dto.setSequencia(rs.getBigDecimal("SEQUENCIA"));
            dto.setCodProduto(rs.getBigDecimal("CODPROD"));
            dto.setReferencia(rs.getString("REFERENCIA"));
            dto.setDescricaoProduto(rs.getString("DESCRPROD"));
            dto.setQtdNeg(rs.getBigDecimal("QTDNEG"));
            dto.setQtdEntregue(rs.getBigDecimal("QTDENTREGUE"));
            dto.setPendente(rs.getString("PENDENTE"));
            dto.setItemCancelado(rs.getString("AD_ITEMCANCELADO"));
            dto.setOcorrenciaCancelamento(rs.getString("OCORRENCIA_CANCELAMENTO"));
            return dto;
        } finally {
            fechar(rs, sql);
        }
    }

    public static void marcarItemComoPendenteCancelado(BigDecimal nunota, BigDecimal sequencia, String observacao) throws Exception {
        JapeFactory.dao("ItemNota")
                .prepareToUpdateByPK(new Object[] { nunota, sequencia })
                .set("PENDENTE", "S")
                .set("OBSERVACAO", observacao)
                .update();
    }

    public static boolean existeAcompanhamentoCancelamento(BigDecimal nunota, BigDecimal sequencia) throws Exception {
        NativeSql sql = null;
        ResultSet rs = null;

        try {
            sql = criarNativeSql();
            sql.appendSql("SELECT 1 ");
            sql.appendSql("FROM TGFACT ACT ");
            sql.appendSql("WHERE ACT.NUNOTA = :NUNOTA ");
            sql.appendSql("AND ACT.SEQUENCIA = :SEQUENCIA ");
            sql.appendSql("AND ACT.AD_ITEMCANCELADO = 'S' ");
            sql.appendSql("AND ROWNUM = 1");
            sql.setNamedParameter("NUNOTA", nunota);
            sql.setNamedParameter("SEQUENCIA", sequencia);

            rs = sql.executeQuery();
            return rs.next();
        } finally {
            fechar(rs, sql);
        }
    }

    public static void incluirAcompanhamentoCancelamento(BigDecimal nunota, BigDecimal sequencia, String ocorrencia,
            BigDecimal codUsuario) throws Exception {
        if (existeAcompanhamentoCancelamento(nunota, sequencia)) {
            return;
        }

        long now = System.currentTimeMillis();
        Timestamp dataHora = new Timestamp(now);

        DynamicVO acompanhamento = (DynamicVO) EntityFacadeFactory.getDWFFacade()
                .getDefaultValueObjectInstance("AcompanhamentoNota");

        acompanhamento.setProperty("NUNOTA", nunota);
        acompanhamento.setProperty("SEQUENCIA", sequencia);
        acompanhamento.setProperty("DHOCOR", dataHora);
        acompanhamento.setProperty("HRACT", TimeUtils.timestamp2BigDecimal(dataHora));
        acompanhamento.setProperty("CODUSU", codUsuario);
        acompanhamento.setProperty("REFERENCIA", BigDecimal.ZERO.compareTo(sequencia) == 0 ? "N" : "R");
        acompanhamento.setProperty("OCORRENCIAS", ocorrencia);
        acompanhamento.setProperty("DIGITADO", "S");
        acompanhamento.setProperty("AD_ITEMCANCELADO", "S");

        EntityFacadeFactory.getDWFFacade().createEntity("AcompanhamentoNota", (EntityVO) acompanhamento);
    }

    public static int suspenderProcessosProducaoSeAcompanhamentoPendente(BigDecimal nunegociacao) throws Exception {
        if (!possuiProducaoPendenteNoAcompanhamento(nunegociacao)) {
            return 0;
        }

        int processosParaSuspender = contarProcessosProducaoNaoSuspensos(nunegociacao);

        if (processosParaSuspender == 0) {
            return 0;
        }

        NativeSql sql = null;

        try {
            sql = criarNativeSql();
            sql.appendSql("UPDATE TPRIPROC ");
            sql.appendSql("SET STATUSPROC = 'S' ");
            sql.appendSql("WHERE AD_NUNEGOCIACAO = :NUNEGOCIACAO ");
            sql.appendSql("AND NVL(STATUSPROC, ' ') <> 'S'");
            sql.setNamedParameter("NUNEGOCIACAO", nunegociacao);
            sql.executeUpdate();
            return processosParaSuspender;
        } catch (Exception e) {
            if (isTabelaOuViewInexistente(e)) {
                return 0;
            }

            throw e;
        } finally {
            fechar(null, sql);
        }
    }

    public static int suspenderProcessosProducao(BigDecimal nunegociacao) throws Exception {
        int processosParaSuspender = contarProcessosProducaoNaoSuspensos(nunegociacao);

        if (processosParaSuspender == 0) {
            return 0;
        }

        NativeSql sql = null;

        try {
            sql = criarNativeSql();
            sql.appendSql("UPDATE TPRIPROC ");
            sql.appendSql("SET STATUSPROC = 'S' ");
            sql.appendSql("WHERE AD_NUNEGOCIACAO = :NUNEGOCIACAO ");
            sql.appendSql("AND NVL(STATUSPROC, ' ') <> 'S'");
            sql.setNamedParameter("NUNEGOCIACAO", nunegociacao);
            sql.executeUpdate();
            return processosParaSuspender;
        } catch (Exception e) {
            if (isTabelaOuViewInexistente(e)) {
                return 0;
            }

            throw e;
        } finally {
            fechar(null, sql);
        }
    }

    public static int contarProcessosProducao(BigDecimal nunegociacao) throws Exception {
        NativeSql sql = null;
        ResultSet rs = null;

        try {
            sql = criarNativeSql();
            sql.appendSql("SELECT COUNT(1) AS TOTAL ");
            sql.appendSql("FROM TPRIPROC PROC ");
            sql.appendSql("WHERE PROC.AD_NUNEGOCIACAO = :NUNEGOCIACAO");
            sql.setNamedParameter("NUNEGOCIACAO", nunegociacao);

            rs = sql.executeQuery();
            return rs.next() ? rs.getInt("TOTAL") : 0;
        } catch (Exception e) {
            if (isTabelaOuViewInexistente(e)) {
                return 0;
            }

            throw e;
        } finally {
            fechar(rs, sql);
        }
    }

    private static int contarProcessosProducaoNaoSuspensos(BigDecimal nunegociacao) throws Exception {
        NativeSql sql = null;
        ResultSet rs = null;

        try {
            sql = criarNativeSql();
            sql.appendSql("SELECT COUNT(1) AS TOTAL ");
            sql.appendSql("FROM TPRIPROC PROC ");
            sql.appendSql("WHERE PROC.AD_NUNEGOCIACAO = :NUNEGOCIACAO ");
            sql.appendSql("AND NVL(PROC.STATUSPROC, ' ') <> 'S'");
            sql.setNamedParameter("NUNEGOCIACAO", nunegociacao);

            rs = sql.executeQuery();
            return rs.next() ? rs.getInt("TOTAL") : 0;
        } catch (Exception e) {
            if (isTabelaOuViewInexistente(e)) {
                return 0;
            }

            throw e;
        } finally {
            fechar(rs, sql);
        }
    }

    private static boolean isTabelaOuViewInexistente(Exception e) {
        String mensagem = e == null ? "" : String.valueOf(e.getMessage()).toUpperCase();
        return mensagem.contains("ORA-00942");
    }

    private static boolean possuiProducaoPendenteNoAcompanhamento(BigDecimal nunegociacao) throws Exception {
        NativeSql sql = null;
        ResultSet rs = null;

        try {
            sql = criarNativeSql();
            sql.appendSql("SELECT 1 ");
            sql.appendSql("FROM AD_TCRNEGCAB DASH ");
            sql.appendSql("WHERE DASH.NUNEGOCIACAO = :NUNEGOCIACAO ");
            sql.appendSql("AND ( ");
            sql.appendSql("    UPPER(NVL(DASH.STATUS_PRODUCAO_FINAL, '')) LIKE '%PEND%' ");
            sql.appendSql("    OR UPPER(NVL(DASH.STATUS_PRODUCAO_FINAL, '')) LIKE '%AGUARD%' ");
            sql.appendSql("    OR UPPER(NVL(DASH.STATUS_PRODUCAO_FINAL, '')) LIKE '%ANDAMENTO%' ");
            sql.appendSql("    OR UPPER(NVL(DASH.STATUS_PRODUCAO_FINAL, '')) LIKE '%PARCIAL%' ");
            sql.appendSql(") ");
            sql.appendSql("AND ROWNUM = 1");
            sql.setNamedParameter("NUNEGOCIACAO", nunegociacao);

            rs = sql.executeQuery();
            return rs.next();
        } finally {
            fechar(rs, sql);
        }
    }

    private static NativeSql criarNativeSql() throws Exception {
        EntityFacade entityFacade = EntityFacadeFactory.getDWFFacade();
        JdbcWrapper jdbc = entityFacade.getJdbcWrapper();
        jdbc.openSession();
        return new NativeSql(jdbc);
    }

    private static void fechar(ResultSet rs, NativeSql sql) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception ignored) {
        }

        try {
            if (sql != null) {
                NativeSql.releaseResources(sql);
            }
        } catch (Exception ignored) {
        }
    }
}
