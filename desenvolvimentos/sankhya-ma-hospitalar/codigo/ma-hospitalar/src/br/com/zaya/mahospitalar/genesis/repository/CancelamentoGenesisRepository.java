package br.com.zaya.mahospitalar.genesis.repository;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.zaya.mahospitalar.genesis.client.GenesisApiResult;
import br.com.zaya.mahospitalar.genesis.util.GenesisSanitizer;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public final class CancelamentoGenesisRepository {
    public boolean notaCancelada(JdbcWrapper jdbc, BigDecimal nota) throws Exception {
        if (nota == null) return false;
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                "SELECT COUNT(*) FROM TGFCAN WHERE NUNOTA=?")) {
            ps.setBigDecimal(1, nota);
            try (ResultSet rs = ps.executeQuery()) { rs.next(); return rs.getInt(1) > 0; }
        }
    }

    public void enfileirarRetornoTardio(JdbcWrapper jdbc, BigDecimal nota, String chave) throws Exception {
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                "UPDATE AD_GENEXCFILA F SET IDGENESIS=(SELECT M.PROTOCOLO_GENESIS FROM AD_GENMOVEST M " +
                        "WHERE M.IDINTGENESIS=F.IDINTGENESIS),STATUS=CASE WHEN EXISTS " +
                        "(SELECT 1 FROM AD_GENMOVEST M WHERE M.IDINTGENESIS=F.IDINTGENESIS AND M.ORIGEM_ITEM='CRIADO') " +
                        "THEN 'PENDENTE' ELSE 'REVISAO' END,DH_ATUALIZACAO=SYSDATE " +
                        "WHERE F.CHAVE_MOVIMENTO=? AND F.STATUS<>'CONCLUIDO'")) {
            ps.setString(1, chave); ps.executeUpdate();
        }
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                "INSERT INTO AD_GENEXCFILA (IDINTGENESIS,NUNOTA_ORIGEM,CHAVE_MOVIMENTO,IDGENESIS," +
                        "STATUS,TENTATIVAS,DH_INCLUSAO,DH_ATUALIZACAO,MENSAGEM) " +
                        "SELECT M.IDINTGENESIS,?,M.CHAVE_MOVIMENTO,M.PROTOCOLO_GENESIS," +
                        "CASE WHEN M.ORIGEM_ITEM='CRIADO' THEN 'PENDENTE' ELSE 'REVISAO' END,0,SYSDATE,SYSDATE," +
                        "'Retorno remoto recebido apos cancelamento.' FROM AD_GENMOVEST M " +
                        "WHERE M.CHAVE_MOVIMENTO=? AND NOT EXISTS " +
                        "(SELECT 1 FROM AD_GENEXCFILA F WHERE F.IDINTGENESIS=M.IDINTGENESIS)")) {
            ps.setBigDecimal(1, nota); ps.setString(2, chave); ps.executeUpdate();
        }
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                "UPDATE AD_GENMOVEST SET NUNOTA=NULL,STATUS_INTEGRACAO='CANCELADO'," +
                        "DH_ATUALIZACAO=SYSDATE WHERE CHAVE_MOVIMENTO=?")) {
            ps.setString(1, chave); ps.executeUpdate();
        }
    }
    // Same transactional lock used by sending, cancellation and queue processing.
    public void bloquearIntegracao(JdbcWrapper jdbc) throws Exception {
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                "SELECT NUPAR FROM AD_CENTPARAM WHERE NUPAR=1 FOR UPDATE"); ResultSet rs = ps.executeQuery()) {
            if (!rs.next()) throw new IllegalStateException("Central NUPAR=1 inexistente.");
        }
    }

    public void preparar(JdbcWrapper jdbc, BigDecimal nota) throws Exception {
        if (nota == null) throw new IllegalArgumentException("NUNOTA ausente no cancelamento.");
        bloquearIntegracao(jdbc);
        recuperarControles(jdbc,nota);
        // Preserve unresolved/legacy sends for review; never guess a remote item identity.
        executar(jdbc, "INSERT INTO AD_GENEXCFILA (IDINTGENESIS,NUNOTA_ORIGEM,CHAVE_MOVIMENTO," +
                "IDGENESIS,STATUS,TENTATIVAS,DH_INCLUSAO,DH_ATUALIZACAO,MENSAGEM) " +
                "SELECT M.IDINTGENESIS,M.NUNOTA,M.CHAVE_MOVIMENTO,M.PROTOCOLO_GENESIS," +
                "CASE WHEN M.ORIGEM_ITEM='CRIADO' AND REGEXP_LIKE(M.PROTOCOLO_GENESIS,'^[1-9][0-9]*$') " +
                "AND M.STATUS_INTEGRACAO='ENVIADO' THEN 'PENDENTE' ELSE 'REVISAO' END," +
                "0,SYSDATE,SYSDATE,CASE WHEN M.ORIGEM_ITEM='CRIADO' AND M.STATUS_INTEGRACAO='ENVIADO' " +
                "THEN NULL ELSE 'Origem ou resultado do envio exige revisao manual.' END " +
                "FROM AD_GENMOVEST M WHERE M.NUNOTA=? " +
                "AND (M.DH_ENVIO IS NOT NULL AND M.STATUS_INTEGRACAO<>'SIMULADO' " +
                "OR M.PROTOCOLO_GENESIS IS NOT NULL OR M.STATUS_INTEGRACAO IN ('ENVIADO','ERRO','REPROCESSAR','ENVIO_INCERTO')) " +
                "AND NOT EXISTS (SELECT 1 FROM AD_GENEXCFILA F WHERE F.IDINTGENESIS=M.IDINTGENESIS)", nota);
        executar(jdbc, "DELETE FROM AD_GENMOVEST M WHERE M.NUNOTA=? AND NOT EXISTS " +
                "(SELECT 1 FROM AD_GENEXCFILA F WHERE F.IDINTGENESIS=M.IDINTGENESIS)", nota);
        executar(jdbc, "UPDATE AD_GENMOVEST SET NUNOTA=NULL,STATUS_INTEGRACAO='CANCELADO'," +
                "DH_ATUALIZACAO=SYSDATE WHERE NUNOTA=?", nota);
        executar(jdbc, "UPDATE AD_GENITEMMAT SET NUNOTA=NULL WHERE NUNOTA=?", nota);
    }

    private void recuperarControles(JdbcWrapper jdbc, BigDecimal nota) throws Exception {
        String sql = "SELECT I.CHAVEMOVIMENTO,MIN(I.IDGENESIS) IDGENESIS,COUNT(*) QTD " +
                "FROM AD_GENITEMMAT I WHERE (I.NUNOTA=? OR " +
                "REGEXP_SUBSTR(I.CHAVEMOVIMENTO,'[^|]+',1,2)=?) AND I.CHAVEMOVIMENTO IS NOT NULL " +
                "AND NOT EXISTS (SELECT 1 FROM AD_GENMOVEST M WHERE M.CHAVE_MOVIMENTO=I.CHAVEMOVIMENTO) " +
                "GROUP BY I.CHAVEMOVIMENTO";
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(sql)) {
            ps.setBigDecimal(1,nota); ps.setString(2,nota.toPlainString());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    try (PreparedStatement insert = jdbc.getConnection().prepareStatement(
                            "INSERT INTO AD_GENMOVEST (IDINTGENESIS,NUNOTA,CHAVE_MOVIMENTO,TIPO_MOVIMENTO," +
                            "STATUS_INTEGRACAO,TENTATIVAS,PROTOCOLO_GENESIS,DH_INCLUSAO,DH_ATUALIZACAO) " +
                            "SELECT NVL(MAX(IDINTGENESIS),0)+1,?,?,'RECUPERADO_CANCELAMENTO','ERRO',0,?,SYSDATE,SYSDATE " +
                            "FROM (SELECT IDINTGENESIS FROM AD_GENMOVEST UNION ALL SELECT IDINTGENESIS FROM AD_GENEXCFILA)")) {
                        insert.setBigDecimal(1,nota); insert.setString(2,rs.getString("CHAVEMOVIMENTO"));
                        insert.setString(3,rs.getInt("QTD")==1 ? rs.getString("IDGENESIS") : null);
                        insert.executeUpdate();
                    }
                }
            }
        }
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                "UPDATE AD_GENMOVEST SET NUNOTA=? WHERE NUNOTA IS NULL AND " +
                "REGEXP_SUBSTR(CHAVE_MOVIMENTO,'[^|]+',1,2)=? AND STATUS_INTEGRACAO<>'CANCELADO'")) {
            ps.setBigDecimal(1,nota); ps.setString(2,nota.toPlainString()); ps.executeUpdate();
        }
        executar(jdbc,"UPDATE AD_GENMOVEST M SET PROTOCOLO_GENESIS=" +
                "(SELECT TO_CHAR(MIN(I.IDGENESIS)) FROM AD_GENITEMMAT I WHERE I.CHAVEMOVIMENTO=M.CHAVE_MOVIMENTO) " +
                "WHERE M.NUNOTA=? AND M.PROTOCOLO_GENESIS IS NULL AND " +
                "(SELECT COUNT(*) FROM AD_GENITEMMAT I WHERE I.CHAVEMOVIMENTO=M.CHAVE_MOVIMENTO " +
                "AND I.IDGENESIS IS NOT NULL)=1",nota);
    }

    public void concluir(JdbcWrapper jdbc, long id, GenesisApiResult resultado, int maxTentativas) throws Exception {
        String status = statusResultado(resultado);
        boolean sucesso = "CONCLUIDO".equals(status);
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                "UPDATE AD_GENEXCFILA SET STATUS=?,TENTATIVAS=NVL(TENTATIVAS,0)+1,HTTP_STATUS=?,MENSAGEM=?," +
                "DH_ATUALIZACAO=SYSDATE WHERE IDINTGENESIS=? AND STATUS IN ('PENDENTE','ERRO','REVISAO')")) {
            ps.setString(1,status);
            ps.setInt(2,resultado.getHttpStatus());
            String mensagem = sucesso ? null : GenesisSanitizer.sanitize(resultado.getCorpoSanitizado());
            ps.setString(3,mensagem == null ? null : mensagem.substring(0,Math.min(4000,mensagem.length())));
            ps.setLong(4,id); ps.executeUpdate();
        }
        if (sucesso) {
            try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                    "UPDATE AD_GENITEMMAT I SET STATUSGENESIS='Inativo' WHERE EXISTS " +
                    "(SELECT 1 FROM AD_GENEXCFILA F WHERE F.IDINTGENESIS=? AND F.STATUS='CONCLUIDO' " +
                    "AND TO_CHAR(I.IDGENESIS)=F.IDGENESIS AND I.CHAVEMOVIMENTO=F.CHAVE_MOVIMENTO)")) {
                ps.setLong(1,id); ps.executeUpdate();
            }
        }
    }

    static String statusResultado(GenesisApiResult resultado) {
        if (resultado.getHttpStatus() == 200 || resultado.getHttpStatus() == 404) return "CONCLUIDO";
        return resultado.getClassificacao() == GenesisApiResult.Classificacao.TRANSITORIO ? "PENDENTE" : "REVISAO";
    }

    private void executar(JdbcWrapper jdbc, String sql, BigDecimal nota) throws Exception {
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(sql)) {
            ps.setBigDecimal(1,nota); ps.executeUpdate();
        }
    }
}
