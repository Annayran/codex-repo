package br.com.zaya.skala.particionamento.sankhya;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.zaya.skala.particionamento.config.ParticionamentoConfig;
import br.com.zaya.skala.particionamento.model.ItemPedido;
import br.com.zaya.skala.particionamento.model.PedidoInfo;
import br.com.zaya.skala.particionamento.util.NumericUtils;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PedidoRepository {
    public PedidoInfo validarPedido(int nuNota) throws Exception {
        JdbcWrapper jdbc = null;
        NativeSql sql = null;
        ResultSet rs = null;

        try {
            jdbc = abrirJdbc();
            sql = new NativeSql(jdbc);
            sql.appendSql("SELECT cab.NUNOTA, cab.CODTIPOPER, cab.CODPARC, cab.STATUSNOTA, top.AD_PERMITEPART, cab.AD_PARTICIONADO ");
            sql.appendSql("FROM TGFCAB cab ");
            sql.appendSql("JOIN TGFTOP top ON top.CODTIPOPER = cab.CODTIPOPER ");
            sql.appendSql("               AND top.DHALTER = ( ");
            sql.appendSql("                   SELECT MAX(top2.DHALTER) ");
            sql.appendSql("                   FROM TGFTOP top2 ");
            sql.appendSql("                   WHERE top2.CODTIPOPER = cab.CODTIPOPER ");
            sql.appendSql("               ) ");
            sql.appendSql("WHERE cab.NUNOTA = :NUNOTA ");
            sql.setNamedParameter("NUNOTA", BigDecimal.valueOf(nuNota));

            rs = sql.executeQuery();
            if (!rs.next()) {
                throw new IllegalStateException("Pedido nao encontrado: " + nuNota);
            }

            return new PedidoInfo(
                    rs.getBigDecimal("NUNOTA").intValue(),
                    rs.getBigDecimal("CODTIPOPER").intValue(),
                    rs.getBigDecimal("CODPARC").intValue(),
                    rs.getString(ParticionamentoConfig.CAMPO_PERMITE_PART),
                    rs.getString(ParticionamentoConfig.CAMPO_PARTICIONADO),
                    rs.getString("STATUSNOTA")
            );
        } finally {
            fechar(rs);
            NativeSql.releaseResources(sql);
            fechar(jdbc);
        }
    }

    public List<ItemPedido> lerItensPedido(int nuNota) throws Exception {
        JdbcWrapper jdbc = null;
        NativeSql sql = null;
        ResultSet rs = null;
        List<ItemPedido> itens = new ArrayList<ItemPedido>();

        try {
            jdbc = abrirJdbc();
            sql = new NativeSql(jdbc);
            validarCamposPaletizacao(jdbc);

            String exprLastro = "NVL(voa.LASTRO, 0)";
            String exprCamada = "NVL(voa.CAMADAS, 0)";
            String exprQtdConversao = "NVL(NULLIF(voa.QUANTIDADE, 0), 1)";
            String exprVolumePorPalete =
                    "CASE WHEN " + exprLastro + " > 0 AND " + exprCamada + " > 0 THEN " + exprLastro + " * " + exprCamada + " ELSE 0 END";
            String exprQtdVolume =
                    "CASE "
                            + "WHEN UPPER(NVL(voa.DIVIDEMULTIPLICA, 'M')) = 'M' THEN ite.QTDNEG / (" + exprQtdConversao + ") "
                            + "WHEN UPPER(NVL(voa.DIVIDEMULTIPLICA, 'M')) = 'D' THEN ite.QTDNEG * (" + exprQtdConversao + ") "
                            + "ELSE ite.QTDNEG END";
            String exprUnidPorPalete =
                    "CASE "
                            + "WHEN UPPER(NVL(voa.DIVIDEMULTIPLICA, 'M')) = 'M' THEN (" + exprVolumePorPalete + ") * (" + exprQtdConversao + ") "
                            + "WHEN UPPER(NVL(voa.DIVIDEMULTIPLICA, 'M')) = 'D' AND (" + exprQtdConversao + ") > 0 THEN (" + exprVolumePorPalete + ") / (" + exprQtdConversao + ") "
                            + "ELSE (" + exprVolumePorPalete + ") END";

            sql.appendSql("SELECT ite.CODPROD, ite.SEQUENCIA, ite.CODLOCALORIG, ite.CONTROLE, ite.STATUSLOTE, ite.CODVOL, ite.USOPROD, ite.VLRUNIT, ");
            sql.appendSql("       pro.DESCRPROD, ite.QTDNEG, ");
            sql.appendSql("       CASE WHEN pem.TIPCONTEST IS NOT NULL THEN pem.TIPCONTEST ELSE pro.TIPCONTEST END AS TIPCONTEST, ");
            sql.appendSql("       " + exprLastro + " AS LASTRO_CALC, ");
            sql.appendSql("       " + exprCamada + " AS CAMADA_CALC, ");
            sql.appendSql("       " + exprUnidPorPalete + " AS UND_PALETE_CAD, ");
            sql.appendSql("       " + exprVolumePorPalete + " AS CX_PALETE_CAD, ");
            sql.appendSql("       CASE WHEN (" + exprVolumePorPalete + ") > 0 THEN (" + exprQtdVolume + ") / (" + exprVolumePorPalete + ") ELSE 0 END AS QTD_PALETE_DEMANDA ");
            sql.appendSql("FROM TGFITE ite ");
            sql.appendSql("JOIN TGFPRO pro ON pro.CODPROD = ite.CODPROD ");
            sql.appendSql("JOIN TGFVOA voa ON voa.CODPROD = ite.CODPROD AND voa.CODVOL = ite.CODVOL ");
            sql.appendSql("LEFT JOIN TGFPEM pem ON pem.CODPROD = ite.CODPROD AND pem.CODEMP = ite.CODEMP ");
            sql.appendSql("WHERE ite.NUNOTA = :NUNOTA ");
            sql.appendSql("ORDER BY ite.SEQUENCIA ");
            sql.setNamedParameter("NUNOTA", BigDecimal.valueOf(nuNota));

            rs = sql.executeQuery();
            while (rs.next()) {
                String codProd = rs.getString("CODPROD");
                String descProd = rs.getString("DESCRPROD");
                double qtdPalete = NumericUtils.toDouble(rs.getBigDecimal("QTD_PALETE_DEMANDA"));
                double undPorPalete = NumericUtils.toDouble(rs.getBigDecimal("UND_PALETE_CAD"));
                double cxPorPalete = NumericUtils.toDouble(rs.getBigDecimal("CX_PALETE_CAD"));

                if (codProd == null || codProd.trim().isEmpty()) {
                    continue;
                }

                if (undPorPalete <= 0 || qtdPalete <= 0) {
                    throw new IllegalStateException(
                            "Produto " + codProd + " sem paletizacao valida para o volume " + rs.getString("CODVOL")
                                    + ". Preencha LASTRO, CAMADAS e conversao na TGFVOA."
                    );
                }

                ItemPedido item = new ItemPedido(codProd, descProd, qtdPalete, undPorPalete, cxPorPalete);
                item.setNuNotaOrigem(nuNota);
                item.setQtdOrigem(NumericUtils.toDouble(rs.getBigDecimal("QTDNEG")));
                item.setSequenciaOrigem(rs.getInt("SEQUENCIA"));
                item.setCodLocalOrig(NumericUtils.toDouble(rs.getBigDecimal("CODLOCALORIG")));
                item.setControle(rs.getString("CONTROLE"));
                item.setStatusLote(rs.getString("STATUSLOTE"));
                item.setTipContest(rs.getString("TIPCONTEST"));
                item.setCodVol(rs.getString("CODVOL"));
                item.setUsoProd(rs.getString("USOPROD"));
                item.setVlrUnit(NumericUtils.toDouble(rs.getBigDecimal("VLRUNIT")));

                itens.add(item);
            }

            return itens;
        } finally {
            fechar(rs);
            NativeSql.releaseResources(sql);
            fechar(jdbc);
        }
    }

    public void bloquearPedidoOrigem(int nuNota) throws Exception {
        JdbcWrapper jdbc = null;
        NativeSql sql = null;
        ResultSet rs = null;

        try {
            jdbc = abrirJdbc();
            sql = new NativeSql(jdbc);
            sql.appendSql("SELECT NUNOTA ");
            sql.appendSql("FROM TGFCAB ");
            sql.appendSql("WHERE NUNOTA = :NUNOTA ");
            sql.appendSql("FOR UPDATE ");
            sql.setNamedParameter("NUNOTA", BigDecimal.valueOf(nuNota));

            rs = sql.executeQuery();
            if (!rs.next()) {
                throw new IllegalStateException("Pedido nao encontrado para bloqueio: " + nuNota);
            }
        } finally {
            fechar(rs);
            NativeSql.releaseResources(sql);
            fechar(jdbc);
        }
    }

    public void marcarPedidoComoParticionado(int nuNota) throws Exception {
        JdbcWrapper jdbc = null;
        NativeSql sql = null;

        try {
            jdbc = abrirJdbc();
            sql = new NativeSql(jdbc);
            sql.appendSql("UPDATE TGFCAB ");
            sql.appendSql("SET AD_PARTICIONADO = 'S' ");
            sql.appendSql("WHERE NUNOTA = :NUNOTA ");
            sql.appendSql("  AND NVL(AD_PARTICIONADO, 'N') <> 'S' ");
            sql.setNamedParameter("NUNOTA", BigDecimal.valueOf(nuNota));
            sql.executeUpdate();
        } finally {
            NativeSql.releaseResources(sql);
            fechar(jdbc);
        }
    }

    private JdbcWrapper abrirJdbc() throws Exception {
        EntityFacade dwfFacade = EntityFacadeFactory.getDWFFacade();
        JdbcWrapper jdbc = dwfFacade.getJdbcWrapper();
        jdbc.openSession();
        return jdbc;
    }

    private String primeiroCampoExistente(JdbcWrapper jdbc, String tabela, String... campos) throws Exception {
        for (String campo : campos) {
            if (campoExiste(jdbc, tabela, campo)) {
                return campo;
            }
        }
        return null;
    }

    private void validarCamposPaletizacao(JdbcWrapper jdbc) throws Exception {
        if (!campoExiste(jdbc, "TGFVOA", "LASTRO")
                || !campoExiste(jdbc, "TGFVOA", "CAMADAS")
                || !campoExiste(jdbc, "TGFVOA", "DIVIDEMULTIPLICA")
                || !campoExiste(jdbc, "TGFVOA", "QUANTIDADE")) {
            throw new IllegalStateException("Campos obrigatorios TGFVOA.LASTRO, TGFVOA.CAMADAS, TGFVOA.DIVIDEMULTIPLICA e TGFVOA.QUANTIDADE nao encontrados.");
        }
    }

    private boolean campoExiste(JdbcWrapper jdbc, String tabela, String campo) throws Exception {
        NativeSql sql = null;
        ResultSet rs = null;
        try {
            sql = new NativeSql(jdbc);
            sql.appendSql("SELECT 1 ");
            sql.appendSql("FROM ALL_TAB_COLUMNS ");
            sql.appendSql("WHERE TABLE_NAME = :TABELA ");
            sql.appendSql("  AND COLUMN_NAME = :CAMPO ");
            sql.appendSql("  AND ROWNUM = 1 ");
            sql.setNamedParameter("TABELA", tabela.toUpperCase());
            sql.setNamedParameter("CAMPO", campo.toUpperCase());
            rs = sql.executeQuery();
            return rs.next();
        } finally {
            fechar(rs);
            NativeSql.releaseResources(sql);
        }
    }

    private void fechar(JdbcWrapper jdbc) {
        if (jdbc != null) {
            JdbcWrapper.closeSession(jdbc);
        }
    }

    private void fechar(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception ignored) {
            // Fechamento defensivo.
        }
    }
}
