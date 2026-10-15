package br.com.crmbonus.metas.action;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AcaoCriarReferenciaMeta implements AcaoRotinaJava {

    @Override
    public void doAction(ContextoAcao ctx) throws Exception {
        BigDecimal codEmpDestino = firstNonNullBigDecimal(
                toBigDecimal(ctx.getParam("CODEMP_DEST")),
                toBigDecimal(ctx.getParam("CODEMP_NOVO")),
                toBigDecimal(fieldFromFirstLine(ctx, "CODEMP"))
        );

        Date dtRefDestino = firstNonNullDate(
                toSqlDate(ctx.getParam("DTREF_DEST")),
                toSqlDate(ctx.getParam("DTREF_NOVA")),
                toSqlDate(fieldFromFirstLine(ctx, "DTREF"))
        );

        BigDecimal codEmpOrigem = firstNonNullBigDecimal(
                toBigDecimal(ctx.getParam("CODEMP")),
                toBigDecimal(ctx.getParam("CODEMP_ORIG"))
        );

        Date dtRefOrigem = firstNonNullDate(
                toSqlDate(ctx.getParam("DTREF")),
                toSqlDate(ctx.getParam("DTREF_ORIG"))
        );

        if (codEmpDestino == null || dtRefDestino == null) {
            throw new IllegalArgumentException("Informe CODEMP_DEST/DTREF_DEST (ou selecione uma linha de AD_METREF com CODEMP e DTREF).");
        }

        boolean copiarLinhas = codEmpOrigem != null || dtRefOrigem != null || isTrue(ctx.getParam("COPIAR"));
        if (copiarLinhas && (codEmpOrigem == null || dtRefOrigem == null)) {
            throw new IllegalArgumentException("Para copiar AD_METVEN, informe CODEMP e DTREF da origem.");
        }

        EntityFacade facade = EntityFacadeFactory.getDWFFacade();
        JdbcWrapper jdbc = facade.getJdbcWrapper();
        jdbc.openSession();

        try {
            validarMetaRefDestinoNaoExiste(jdbc, codEmpDestino, dtRefDestino);
            inserirMetaRefDestino(jdbc, codEmpDestino, dtRefDestino);

            int totalCopiado = 0;
            if (copiarLinhas) {
                totalCopiado = copiarMetasVendedor(jdbc, codEmpOrigem, dtRefOrigem, codEmpDestino, dtRefDestino);
            }

            StringBuilder html = new StringBuilder();
            html.append("<html><div style='font-family:Arial;'>");
            html.append("<h3 style='color:#2E86C1;'>Referência criada com sucesso</h3>");
            html.append("<hr/>");
            html.append("<b>Destino:</b> CODEMP ").append(codEmpDestino).append(" | DTREF ").append(dtRefDestino).append("<br/>");

            if (copiarLinhas) {
                html.append("<b>Origem:</b> CODEMP ").append(codEmpOrigem).append(" | DTREF ").append(dtRefOrigem).append("<br/>");
                html.append("<b>Linhas copiadas para AD_METVEN:</b> ").append(totalCopiado).append("<br/>");
            }

            html.append("</div></html>");
            ctx.setMensagemRetorno(html.toString());

        } catch (Exception e) {
            ctx.setMensagemRetorno("<b>Erro ao criar/copiar metas:</b> " + e.getMessage());
            throw e;
        } finally {
            jdbc.closeSession();
        }
    }

    private void validarMetaRefDestinoNaoExiste(JdbcWrapper jdbc, BigDecimal codEmpDestino, Date dtRefDestino) throws Exception {
        JapeWrapper metRefDAO = JapeFactory.dao("AD_METREF");
        DynamicVO existente = metRefDAO.findByPK(codEmpDestino, dtRefDestino);

        if (existente != null) {
            throw new IllegalArgumentException("Já existe referência em AD_METREF para CODEMP/DTREF de destino.");
        }
    }

    private void inserirMetaRefDestino(JdbcWrapper jdbc, BigDecimal codEmpDestino, Date dtRefDestino) throws Exception {
        JapeWrapper metRefDAO = JapeFactory.dao("AD_METREF");
        metRefDAO.create()
                .set("CODEMP", codEmpDestino)
                .set("DTREF", dtRefDestino)
                .save();
    }

    private int copiarMetasVendedor(
            JdbcWrapper jdbc,
            BigDecimal codEmpOrigem,
            Date dtRefOrigem,
            BigDecimal codEmpDestino,
            Date dtRefDestino
    ) throws Exception {
        int totalAntes = contarMetasVendedorDestino(jdbc, codEmpDestino, dtRefDestino);

        NativeSql sqlInsert = new NativeSql(jdbc);
        sqlInsert.appendSql(
                "INSERT INTO AD_METVEN (" +
                        "CODEMP, CODVEND, DTREG, NUMCOT, NUMPED, VLRTOTAL, VLRDID, VLRVLUKAN" +
                        ") " +
                        "SELECT " +
                        ":CODEMP_DEST, ORIG.CODVEND, :DTREF_DEST, ORIG.NUMCOT, ORIG.NUMPED, ORIG.VLRTOTAL, ORIG.VLRDID, ORIG.VLRVLUKAN " +
                        "FROM AD_METVEN ORIG " +
                        "WHERE ORIG.CODEMP = :CODEMP_ORIG " +
                        "AND ORIG.DTREG = :DTREF_ORIG " +
                        "AND NOT EXISTS (" +
                        "    SELECT 1 " +
                        "    FROM AD_METVEN DEST " +
                        "    WHERE DEST.CODEMP = :CODEMP_DEST " +
                        "    AND DEST.DTREG = :DTREF_DEST " +
                        "    AND DEST.CODVEND = ORIG.CODVEND " +
                        "    AND NVL(DEST.NUMCOT, 0) = NVL(ORIG.NUMCOT, 0) " +
                        "    AND NVL(DEST.NUMPED, 0) = NVL(ORIG.NUMPED, 0)" +
                        ")"
        );

        sqlInsert.setNamedParameter("CODEMP_DEST", codEmpDestino);
        sqlInsert.setNamedParameter("DTREF_DEST", dtRefDestino);
        sqlInsert.setNamedParameter("CODEMP_ORIG", codEmpOrigem);
        sqlInsert.setNamedParameter("DTREF_ORIG", dtRefOrigem);

        try {
            sqlInsert.executeUpdate();
        } finally {
            NativeSql.releaseResources(sqlInsert);
        }

        int totalDepois = contarMetasVendedorDestino(jdbc, codEmpDestino, dtRefDestino);
        return Math.max(0, totalDepois - totalAntes);
    }

    private int contarMetasVendedorDestino(JdbcWrapper jdbc, BigDecimal codEmpDestino, Date dtRefDestino) throws Exception {
        NativeSql sql = new NativeSql(jdbc);
        sql.appendSql("SELECT COUNT(1) AS TOTAL FROM AD_METVEN WHERE CODEMP = :CODEMP AND DTREG = :DTREF");
        sql.setNamedParameter("CODEMP", codEmpDestino);
        sql.setNamedParameter("DTREF", dtRefDestino);

        try (ResultSet rs = sql.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("TOTAL");
            }
            return 0;
        } finally {
            NativeSql.releaseResources(sql);
        }
    }

    private Object fieldFromFirstLine(ContextoAcao ctx, String field) {
        Registro[] linhas = ctx.getLinhas();
        if (linhas == null || linhas.length == 0) {
            return null;
        }
        return linhas[0].getCampo(field);
    }

    private boolean isTrue(Object value) {
        if (value == null) {
            return false;
        }
        String text = value.toString().trim();
        return "S".equalsIgnoreCase(text) || "SIM".equalsIgnoreCase(text) || "TRUE".equalsIgnoreCase(text) || "1".equals(text);
    }

    private BigDecimal toBigDecimal(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }
        return new BigDecimal(value.toString().trim());
    }

    private Date toSqlDate(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Date) {
            return (Date) value;
        }

        if (value instanceof Timestamp) {
            return new Date(((Timestamp) value).getTime());
        }

        if (value instanceof java.util.Date) {
            return new Date(((java.util.Date) value).getTime());
        }

        String texto = value.toString().trim();
        if (texto.isEmpty()) {
            return null;
        }

        DateTimeFormatter[] formatos = new DateTimeFormatter[]{
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                DateTimeFormatter.BASIC_ISO_DATE
        };

        for (DateTimeFormatter formato : formatos) {
            try {
                LocalDate localDate = LocalDate.parse(texto, formato);
                return Date.valueOf(localDate);
            } catch (DateTimeParseException ignored) {
            }
        }

        throw new IllegalArgumentException("Data inválida para DTREF: " + texto + ". Use yyyy-MM-dd ou dd/MM/yyyy.");
    }

    private BigDecimal firstNonNullBigDecimal(BigDecimal... values) {
        for (BigDecimal value : values) {
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    private Date firstNonNullDate(Date... values) {
        for (Date value : values) {
            if (value != null) {
                return value;
            }
        }
        return null;
    }
}

