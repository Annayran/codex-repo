package br.com.zaya.proauto.compensacao;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;



/** Oracle: leitura parametrizada e locks. Escritas financeiras pertencem ao helper nativo. */
public final class CompensacaoRepository {
    private final JdbcWrapper jdbc;
    private final ConfiguracaoCompensacao config;
    private final BigDecimal nota;

    public CompensacaoRepository(JdbcWrapper jdbc, ConfiguracaoCompensacao config, BigDecimal nota) {
        this.jdbc = jdbc;
        this.config = config;
        this.nota = nota;
    }

    public Timestamp hoje() throws Exception {
        NativeSql sql = sql("SELECT TRUNC(SYSDATE) HOJE FROM DUAL");
        try (ResultSet rs = sql.executeQuery()) {
            if (!rs.next()) { throw new IllegalStateException("Data do banco indisponivel."); }
            return rs.getTimestamp("HOJE");
        } finally { NativeSql.releaseResources(sql); }
    }

    public static boolean parceiroHabilitado(JdbcWrapper jdbc, BigDecimal parceiro) throws Exception {
        NativeSql sql = new NativeSql(jdbc).appendSql("SELECT " + ConfiguracaoCompensacao.FLAG_PARCEIRO
                + " FLAG FROM TGFPAR WHERE CODPARC=:PARCEIRO FOR UPDATE NOWAIT");
        sql.setNamedParameter("PARCEIRO", parceiro);
        try (ResultSet rs = sql.executeQuery()) {
            return rs.next() && "S".equals(rs.getString("FLAG"));
        } finally { NativeSql.releaseResources(sql); }
    }

    public Titulo primeiro(BigDecimal parceiro, BigDecimal empresa, boolean credito, Timestamp hoje)
            throws Exception {
        NativeSql sql = sql("SELECT * FROM (SELECT F.NUFIN, F.VLRDESDOB FROM TGFFIN F "
                + "WHERE F.CODPARC=:PARCEIRO AND F.CODEMP=:EMPRESA AND " + elegivel("F", credito)
                + " ORDER BY F.DTVENC, F.NUFIN) WHERE ROWNUM=1");
        sql.setNamedParameter("PARCEIRO", parceiro);
        sql.setNamedParameter("EMPRESA", empresa);
        if (credito) { parametros(sql, hoje); }
        else { sql.setNamedParameter("NOTA", nota); }
        try (ResultSet rs = sql.executeQuery()) {
            return rs.next() ? new Titulo(rs.getBigDecimal("NUFIN"), rs.getBigDecimal("VLRDESDOB")) : null;
        } finally { NativeSql.releaseResources(sql); }
    }

    public void bloquear(BigDecimal id) throws Exception {
        NativeSql sql = sql("SELECT NUFIN FROM TGFFIN WHERE NUFIN=:ID FOR UPDATE NOWAIT");
        sql.setNamedParameter("ID", id);
        try (ResultSet rs = sql.executeQuery()) {
            if (!rs.next()) { throw new IllegalStateException("Titulo deixou de existir; repetir na proxima rodada."); }
        } finally { NativeSql.releaseResources(sql); }
    }

    public Titulo revalidar(Titulo original, BigDecimal parceiro, BigDecimal empresa,
                            boolean credito, Timestamp hoje) throws Exception {
        NativeSql sql = sql("SELECT F.NUFIN,F.VLRDESDOB FROM TGFFIN F WHERE F.NUFIN=:ID "
                + "AND F.CODPARC=:PARCEIRO AND F.CODEMP=:EMPRESA AND " + elegivel("F", credito));
        sql.setNamedParameter("ID", original.id);
        sql.setNamedParameter("PARCEIRO", parceiro);
        sql.setNamedParameter("EMPRESA", empresa);
        if (credito) { parametros(sql, hoje); }
        else { sql.setNamedParameter("NOTA", nota); }
        try (ResultSet rs = sql.executeQuery()) {
            if (!rs.next()) { throw new IllegalStateException("Titulo nao e mais elegivel; nenhuma compensacao efetuada."); }
            return new Titulo(rs.getBigDecimal("NUFIN"), rs.getBigDecimal("VLRDESDOB"));
        } finally { NativeSql.releaseResources(sql); }
    }

    private String elegivel(String alias, boolean credito) {
        // Homologacao inicial: principal sem encargos/retencoes/moeda/boleto registrado.
        // Nao aproximar o liquido pelo principal quando ha outros componentes financeiros.
        String filtro = alias + ".RECDESP=" + (credito ? "-1" : "1")
                + " AND " + alias + ".PROVISAO='N' AND " + alias + ".DHBAIXA IS NULL"
                + " AND " + alias + ".VLRDESDOB>0 AND NVL(" + alias + ".VLRBAIXA,0)=0"
                + " AND NVL(" + alias + ".CODMOEDA,0)=0"
                + " AND " + alias + ".NOSSONUM IS NULL";
        for (String campo : new String[] {"VLRJURO", "VLRMULTA", "VLRDESC", "VLRINSS", "VLRIRF", "VLRISS",
                "VLRVENDOR", "VLRJURONEGOC", "VLRMULTANEGOC", "VLRJUROEMBUT", "VLRMULTAEMBUT", "VLRDESCEMBUT"}) {
            filtro += " AND NVL(" + alias + "." + campo + ",0)=0";
        }
        if (credito) {
            // DHMOV e inicializado na inclusao pelo FinanceiroListener dos JARs locais.
            // Validar preservacao em migracoes e recomposicoes na versao 4.36b139.
            filtro += " AND " + alias + ".DHMOV<:LIMITEINCLUSAO"
                    + " AND " + alias + ".CODTIPTIT IN (";
            int indice = 0;
            for (Integer tipo : config.tipos) {
                if (indice > 0) { filtro += ","; }
                filtro += ":TIPO" + indice++;
            }
            filtro += ")";
        } else {
            filtro += " AND " + alias + ".NUNOTA=:NOTA";
        }
        return filtro;
    }

    private void parametros(NativeSql sql, Timestamp hoje) {
        sql.setNamedParameter("LIMITEINCLUSAO", RegraCompensacao.limiteInclusao(hoje, config.dias));
        int indice = 0;
        for (Integer tipo : config.tipos) { sql.setNamedParameter("TIPO" + indice++, tipo); }
    }

    private NativeSql sql(String consulta) { return new NativeSql(jdbc).appendSql(consulta); }

    public static final class Titulo {
        final BigDecimal id;
        final BigDecimal valor;
        Titulo(BigDecimal id, BigDecimal valor) { this.id = id; this.valor = valor; }
    }
}
