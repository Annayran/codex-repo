package br.com.zaya.like.Actions.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.zaya.like.Actions.dto.ItemMomentoNegociacaoDTO;
import br.com.zaya.like.Actions.dto.MomentoNegociacaoDTO;

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
