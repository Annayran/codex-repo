package br.com.zaya.skala.particionamento.sankhya;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DesfazerParticionamentoRepository {
    private static final String STATUS_GERADO = "GERADO";
    private static final String STATUS_DESFEITO = "DESFEITO";

    public Integer localizarParticionamentoAtivo(int nuNotaSelecionada) throws Exception {
        Integer porOrigem = buscarIdPartPorOrigem(nuNotaSelecionada);
        if (porOrigem != null) {
            return porOrigem;
        }
        return buscarIdPartPorOE(nuNotaSelecionada);
    }

    public boolean ehOEParticionadaAtiva(int nuNotaOE) throws Exception {
        return buscarIdPartPorOE(nuNotaOE) != null;
    }

    public void bloquearParticionamento(int idPart) throws Exception {
        JdbcWrapper jdbc = null;
        NativeSql sql = null;
        ResultSet rs = null;

        try {
            jdbc = abrirJdbc();
            sql = new NativeSql(jdbc);
            sql.appendSql("SELECT IDPART FROM AD_PARTCAB WHERE IDPART = :IDPART FOR UPDATE ");
            sql.setNamedParameter("IDPART", inteiro(idPart));
            rs = sql.executeQuery();
            if (!rs.next()) {
                throw new IllegalStateException("Particionamento nao encontrado: " + idPart);
            }
        } finally {
            fechar(rs);
            NativeSql.releaseResources(sql);
            fechar(jdbc);
        }
    }

    public List<Integer> excluirOEsGeradas(int idPart, int nuNotaSelecionada) throws Exception {
        List<Integer> notas = listarOEs(idPart, nuNotaSelecionada);
        for (Integer nuNotaOE : notas) {
            excluirOE(nuNotaOE);
        }
        return notas;
    }

    public void marcarRastroComoDesfeito(int idPart, int codUsu) throws Exception {
        executarUpdate(
                "UPDATE AD_PARTOE SET STATUS = :STATUS WHERE IDPART = :IDPART",
                idPart,
                codUsu,
                false
        );
        executarUpdate(
                "UPDATE AD_PARTCAB SET STATUS = :STATUS, DHDESFEITO = SYSDATE, CODUSUDESFEITO = :CODUSU WHERE IDPART = :IDPART",
                idPart,
                codUsu,
                true
        );
    }

    public void desmarcarPedidoOrigem(int idPart) throws Exception {
        JdbcWrapper jdbc = null;
        NativeSql sql = null;

        try {
            jdbc = abrirJdbc();
            sql = new NativeSql(jdbc);
            sql.appendSql("UPDATE TGFCAB ");
            sql.appendSql("SET AD_PARTICIONADO = 'N' ");
            sql.appendSql("WHERE NUNOTA = (SELECT NUNOTA_ORIGEM FROM AD_PARTCAB WHERE IDPART = :IDPART) ");
            sql.setNamedParameter("IDPART", inteiro(idPart));
            sql.executeUpdate();
        } finally {
            NativeSql.releaseResources(sql);
            fechar(jdbc);
        }
    }

    public void marcarItensOrigemComoPendentes(int idPart) throws Exception {
        JdbcWrapper jdbc = null;
        NativeSql sql = null;

        try {
            jdbc = abrirJdbc();
            sql = new NativeSql(jdbc);
            sql.appendSql("UPDATE TGFITE ");
            sql.appendSql("SET PENDENTE = 'S' ");
            sql.appendSql("WHERE NUNOTA = (SELECT NUNOTA_ORIGEM FROM AD_PARTCAB WHERE IDPART = :IDPART) ");
            sql.setNamedParameter("IDPART", inteiro(idPart));
            sql.executeUpdate();
        } finally {
            NativeSql.releaseResources(sql);
            fechar(jdbc);
        }
    }

    private Integer buscarIdPartPorOrigem(int nuNotaOrigem) throws Exception {
        return buscarIdPart(
                "SELECT MAX(IDPART) AS IDPART FROM AD_PARTCAB WHERE NUNOTA_ORIGEM = :NUNOTA AND NVL(STATUS, :STATUS_GERADO) = :STATUS_GERADO",
                nuNotaOrigem
        );
    }

    private Integer buscarIdPartPorOE(int nuNotaOE) throws Exception {
        return buscarIdPart(
                "SELECT MAX(IDPART) AS IDPART FROM AD_PARTOE WHERE NUNOTA_OE = :NUNOTA AND NVL(STATUS, :STATUS_GERADO) = :STATUS_GERADO",
                nuNotaOE
        );
    }

    private Integer buscarIdPart(String sqlText, int nuNota) throws Exception {
        JdbcWrapper jdbc = null;
        NativeSql sql = null;
        ResultSet rs = null;

        try {
            jdbc = abrirJdbc();
            sql = new NativeSql(jdbc);
            sql.appendSql(sqlText);
            sql.setNamedParameter("NUNOTA", inteiro(nuNota));
            sql.setNamedParameter("STATUS_GERADO", STATUS_GERADO);
            rs = sql.executeQuery();
            if (rs.next() && rs.getBigDecimal("IDPART") != null) {
                return rs.getBigDecimal("IDPART").intValue();
            }
            return null;
        } finally {
            fechar(rs);
            NativeSql.releaseResources(sql);
            fechar(jdbc);
        }
    }

    private List<Integer> listarOEs(int idPart, int nuNotaSelecionada) throws Exception {
        JdbcWrapper jdbc = null;
        NativeSql sql = null;
        ResultSet rs = null;
        List<Integer> notas = new ArrayList<Integer>();

        try {
            jdbc = abrirJdbc();
            sql = new NativeSql(jdbc);
            sql.appendSql("SELECT oe.NUNOTA_OE ");
            sql.appendSql("FROM AD_PARTOE oe ");
            sql.appendSql("JOIN AD_PARTCAB part ON part.IDPART = oe.IDPART ");
            sql.appendSql("WHERE oe.IDPART = :IDPART ");
            sql.appendSql("  AND NVL(oe.STATUS, :STATUS_GERADO) = :STATUS_GERADO ");
            sql.appendSql("  AND oe.NUNOTA_OE <> part.NUNOTA_ORIGEM ");
            sql.appendSql("  AND oe.NUNOTA_OE <> :NUNOTA_SELECIONADA ");
            sql.appendSql("ORDER BY oe.IDPARTOE ");
            sql.setNamedParameter("IDPART", inteiro(idPart));
            sql.setNamedParameter("STATUS_GERADO", STATUS_GERADO);
            sql.setNamedParameter("NUNOTA_SELECIONADA", inteiro(nuNotaSelecionada));
            rs = sql.executeQuery();
            while (rs.next()) {
                notas.add(rs.getBigDecimal("NUNOTA_OE").intValue());
            }
            return notas;
        } finally {
            fechar(rs);
            NativeSql.releaseResources(sql);
            fechar(jdbc);
        }
    }

    private void excluirOE(int nuNotaOE) throws Exception {
        JdbcWrapper jdbc = null;

        try {
            jdbc = abrirJdbc();
            definirAdiarAtualizacaoOrigem(jdbc, "S");
            excluirVariacoesOE(jdbc, nuNotaOE);
            excluirItensOE(jdbc, nuNotaOE);
            excluirCabecalhoOE(jdbc, nuNotaOE);
        } finally {
            if (jdbc != null) {
                try {
                    definirAdiarAtualizacaoOrigem(jdbc, "N");
                } catch (Exception ignored) {
                    // Reset defensivo da variavel de sessao.
                }
            }
            fechar(jdbc);
        }
    }

    private void excluirVariacoesOE(JdbcWrapper jdbc, int nuNotaOE) throws Exception {
        NativeSql sql = null;
        try {
            sql = new NativeSql(jdbc);
            sql.appendSql("DELETE FROM TGFVAR WHERE NUNOTA = :NUNOTA ");
            sql.setNamedParameter("NUNOTA", inteiro(nuNotaOE));
            sql.executeUpdate();
        } finally {
            NativeSql.releaseResources(sql);
        }
    }

    private void excluirItensOE(JdbcWrapper jdbc, int nuNotaOE) throws Exception {
        NativeSql sql = null;
        try {
            sql = new NativeSql(jdbc);
            sql.appendSql("DELETE FROM TGFITE WHERE NUNOTA = :NUNOTA ");
            sql.setNamedParameter("NUNOTA", inteiro(nuNotaOE));
            sql.executeUpdate();
        } finally {
            NativeSql.releaseResources(sql);
        }
    }

    private void excluirCabecalhoOE(JdbcWrapper jdbc, int nuNotaOE) throws Exception {
        NativeSql sql = null;
        try {
            sql = new NativeSql(jdbc);
            sql.appendSql("DELETE FROM TGFCAB WHERE NUNOTA = :NUNOTA ");
            sql.setNamedParameter("NUNOTA", inteiro(nuNotaOE));
            sql.executeUpdate();
        } finally {
            NativeSql.releaseResources(sql);
        }
    }

    private void executarUpdate(String sqlText, int idPart, int codUsu, boolean incluiUsuario) throws Exception {
        JdbcWrapper jdbc = null;
        NativeSql sql = null;

        try {
            jdbc = abrirJdbc();
            sql = new NativeSql(jdbc);
            sql.appendSql(sqlText);
            sql.setNamedParameter("STATUS", STATUS_DESFEITO);
            sql.setNamedParameter("IDPART", inteiro(idPart));
            if (incluiUsuario) {
                sql.setNamedParameter("CODUSU", inteiro(codUsu));
            }
            sql.executeUpdate();
        } finally {
            NativeSql.releaseResources(sql);
            fechar(jdbc);
        }
    }

    private BigDecimal inteiro(int value) {
        return BigDecimal.valueOf(value);
    }

    private void definirAdiarAtualizacaoOrigem(JdbcWrapper jdbc, String valor) throws Exception {
        NativeSql sql = null;
        try {
            sql = new NativeSql(jdbc);
            sql.appendSql("BEGIN VARIAVEIS_PKG.V_ADIARATUALIZACAOORIGEM := :VALOR; END; ");
            sql.setNamedParameter("VALOR", valor);
            sql.executeUpdate();
        } finally {
            NativeSql.releaseResources(sql);
        }
    }

    private JdbcWrapper abrirJdbc() throws Exception {
        EntityFacade dwfFacade = EntityFacadeFactory.getDWFFacade();
        JdbcWrapper jdbc = dwfFacade.getJdbcWrapper();
        jdbc.openSession();
        return jdbc;
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
