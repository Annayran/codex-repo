package br.com.zaya.mahospitalar.genesis.repository;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.zaya.mahospitalar.genesis.client.GenesisApiResult;
import br.com.zaya.mahospitalar.genesis.domain.CandidatoGenesis;
import br.com.zaya.mahospitalar.genesis.util.GenesisSanitizer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public final class MovimentoGenesisRepository {
    private final String tabela;
    public MovimentoGenesisRepository() { this(false); }
    public MovimentoGenesisRepository(boolean tecnico) {
        tabela = tecnico ? "AD_GENAPICTRL" : "AD_GENMOVEST";
    }
    public void etapa(JdbcWrapper jdbc,CandidatoGenesis c,String etapa,String entidade) throws Exception {
        try(PreparedStatement ps=jdbc.getConnection().prepareStatement(
                "UPDATE AD_GENMOVEST SET ETAPA_ATUAL=?,ENTIDADE_BLOQ=? WHERE CHAVE_MOVIMENTO=?")) {
            ps.setString(1,etapa);ps.setString(2,entidade);ps.setString(3,chave(c));
            if(ps.executeUpdate()!=1)throw new IllegalStateException("Movimento ausente na atualizacao de etapa.");
        }
    }
    public String statusAtual(JdbcWrapper jdbc, CandidatoGenesis c) throws Exception {
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                "SELECT STATUS_INTEGRACAO FROM " + tabela + " WHERE CHAVE_MOVIMENTO=?")) {
            ps.setString(1, chave(c));
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? rs.getString(1) : null; }
        }
    }

    public void registrarRetornoCancelado(JdbcWrapper jdbc, CandidatoGenesis c, GenesisApiResult r,
                                           String hash) throws Exception {
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                "UPDATE AD_GENMOVEST SET PROTOCOLO_GENESIS=?,PAYLOAD_HASH=?,HTTP_STATUS=?," +
                        "STATUS_INTEGRACAO='CANCELADO',NUNOTA=NULL,DH_ATUALIZACAO=SYSDATE " +
                        "WHERE CHAVE_MOVIMENTO=? AND STATUS_INTEGRACAO IN ('PENDENTE','ENVIO_INCERTO','CANCELADO')")) {
            ps.setString(1, r.getIdGenesis()); ps.setString(2, hash); ps.setInt(3, r.getHttpStatus());
            ps.setString(4, chave(c));
            if (ps.executeUpdate() != 1) throw new IllegalStateException("Controle de cancelamento ambiguo.");
        }
    }
    public void normalizarReprocessamento(JdbcWrapper jdbc, int maxTentativas) throws Exception {
        // Legacy entry point: failures remain eligible without an attempt cutoff.
    }
    public void registrarPendente(JdbcWrapper jdbc, CandidatoGenesis candidato) throws Exception {
        if (atualizarPendente(jdbc, candidato) > 0) return;
        bloquearGeracaoId(jdbc);
        if (atualizarPendente(jdbc, candidato) > 0) return;
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                "INSERT INTO " + tabela + " (IDINTGENESIS,CHAVE_MOVIMENTO,TIPO_MOVIMENTO," +
                        "STATUS_INTEGRACAO,TENTATIVAS,DH_INCLUSAO,DH_ATUALIZACAO,NUNOTA) " +
                        "VALUES (?,?,?,'PENDENTE',0,SYSDATE,SYSDATE,?)")) {
            ps.setLong(1, proximoId(jdbc)); ps.setString(2, chave(candidato));
            ps.setString(3, limitar(candidato.tipoMovimento, 100));
            ps.setBigDecimal(4, candidato.nuNota); ps.executeUpdate();
        }
    }

    public void marcarEnviado(JdbcWrapper jdbc, CandidatoGenesis candidato, GenesisApiResult result,
                              String payloadHash) throws Exception {
        atualizarResultado(jdbc, candidato, "ENVIADO", result.getHttpStatus(), null,
                result.getIdGenesis(), payloadHash, false, Integer.MAX_VALUE);
    }

    public void registrarRetornoPendente(JdbcWrapper jdbc, CandidatoGenesis candidato,
                                         GenesisApiResult result, String hash) throws Exception {
        atualizarResultado(jdbc, candidato, "ENVIO_INCERTO", result.getHttpStatus(),
                "API confirmou; persistencia da base instalada ainda em andamento.",
                result.getIdGenesis(), hash, false, Integer.MAX_VALUE);
    }

    public void marcarSimulado(JdbcWrapper jdbc, CandidatoGenesis candidato, String payloadHash) throws Exception {
        atualizarResultado(jdbc, candidato, "SIMULADO", null, null, null, payloadHash, false, Integer.MAX_VALUE);
    }

    public void registrarOrigemItem(JdbcWrapper jdbc, CandidatoGenesis candidato, String operacao) throws Exception {
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                "UPDATE " + tabela + " SET ORIGEM_ITEM=COALESCE(ORIGEM_ITEM,?) WHERE CHAVE_MOVIMENTO=?")) {
            ps.setString(1, operacao); ps.setString(2, chave(candidato)); ps.executeUpdate();
        }
    }

    public void marcarErro(JdbcWrapper jdbc, CandidatoGenesis candidato, String status,
                           Integer httpStatus, String mensagem) throws Exception {
        marcarErro(jdbc,candidato,status,httpStatus,mensagem,Integer.MAX_VALUE);
    }

    public void marcarErro(JdbcWrapper jdbc, CandidatoGenesis candidato, String status,
                           Integer httpStatus, String mensagem, int maxTentativas) throws Exception {
        atualizarResultado(jdbc,candidato,status,httpStatus,mensagem,null,null,true,maxTentativas);
    }

    public int reprocessarSelecionados(JdbcWrapper jdbc, long[] ids) throws Exception {
        int total = 0;
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                "UPDATE AD_GENMOVEST SET STATUS_INTEGRACAO='REPROCESSAR',MENSAGEM_ERRO=NULL," +
                        "HTTP_STATUS=NULL,TENTATIVAS=0,DH_REPROCESSAMENTO=SYSDATE,DH_ATUALIZACAO=SYSDATE " +
                        "WHERE IDINTGENESIS=? AND STATUS_INTEGRACAO IN ('ERRO','REPROCESSAR') " +
                        "AND (HTTP_STATUS IS NULL OR HTTP_STATUS<>429 OR DH_ATUALIZACAO+" +
                        "NVL(TO_NUMBER(REGEXP_SUBSTR(DBMS_LOB.SUBSTR(MENSAGEM_ERRO,1000,1)," +
                        "'COOLDOWN_MS=([0-9]{1,15});',1,1,NULL,1)),60000)/86400000<=SYSDATE) " +
                        "AND NOT EXISTS (SELECT 1 FROM AD_GENEXCFILA F WHERE F.IDINTGENESIS=AD_GENMOVEST.IDINTGENESIS) " +
                        "AND NOT EXISTS (SELECT 1 FROM TGFCAN C WHERE C.NUNOTA=AD_GENMOVEST.NUNOTA)")) {
            for (long id : ids) { ps.setLong(1, id); total += ps.executeUpdate(); }
        }
        return total;
    }

    private int atualizarPendente(JdbcWrapper jdbc, CandidatoGenesis candidato) throws Exception {
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                "UPDATE " + tabela + " SET TIPO_MOVIMENTO=?," +
                        "STATUS_INTEGRACAO=CASE WHEN STATUS_INTEGRACAO IN ('ENVIADO','CANCELADO') " +
                        "THEN STATUS_INTEGRACAO ELSE 'PENDENTE' END," +
                        "NUNOTA=?,DH_ATUALIZACAO=SYSDATE WHERE CHAVE_MOVIMENTO=?")) {
            ps.setString(1, limitar(candidato.tipoMovimento, 100)); ps.setBigDecimal(2, candidato.nuNota);
            ps.setString(3, chave(candidato)); return ps.executeUpdate();
        }
    }

    private void atualizarResultado(JdbcWrapper jdbc, CandidatoGenesis candidato, String status,
                                     Integer http, String erro, String protocolo, String hash,
                                     boolean incrementarTentativa, int maxTentativas) throws Exception {
        atualizarResultado(jdbc.getConnection(),candidato,status,http,erro,protocolo,hash,
                incrementarTentativa,maxTentativas);
    }

    void atualizarResultado(java.sql.Connection connection, CandidatoGenesis candidato, String status,
                            Integer http, String erro, String protocolo, String hash,
                            boolean incrementarTentativa, int maxTentativas) throws Exception {
        String sql = "UPDATE " + tabela + " SET STATUS_INTEGRACAO=?,HTTP_STATUS=?,MENSAGEM_ERRO=?," +
                "PROTOCOLO_GENESIS=COALESCE(?,PROTOCOLO_GENESIS),PAYLOAD_HASH=COALESCE(?,PAYLOAD_HASH)," +
                "TENTATIVAS=NVL(TENTATIVAS,0)+?,DH_ATUALIZACAO=SYSDATE," +
                "DH_ENVIO=CASE WHEN ? IN ('ENVIADO','SIMULADO') THEN SYSDATE ELSE DH_ENVIO END " +
                ("AD_GENMOVEST".equals(tabela) ? ",PROX_TENTATIVA=" + (incrementarTentativa
                        ? "SYSDATE+LEAST(60,POWER(2,LEAST(NVL(TENTATIVAS,0)+1,6)))/1440 " : "NULL ") : "") +
                "WHERE CHAVE_MOVIMENTO=? AND STATUS_INTEGRACAO NOT IN ('ENVIADO','CANCELADO')";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            if (http == null) ps.setNull(2, java.sql.Types.NUMERIC); else ps.setInt(2, http);
            ps.setString(3, limitar(GenesisSanitizer.sanitize(erro), 4000));
            ps.setString(4, limitar(protocolo, 100)); ps.setString(5, hash);
            ps.setInt(6, incrementarTentativa ? 1 : 0); ps.setString(7, status);
            ps.setString(8, chave(candidato)); ps.executeUpdate();
        }
    }

    private void bloquearGeracaoId(JdbcWrapper jdbc) throws Exception {
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                "SELECT NUPAR FROM AD_CENTPARAM WHERE NUPAR=1 FOR UPDATE");
             ResultSet rs = ps.executeQuery()) {
            if (!rs.next()) throw new IllegalStateException("Central NUPAR=1 inexistente.");
        }
    }

    private long proximoId(JdbcWrapper jdbc) throws Exception {
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                "SELECT NVL(MAX(IDINTGENESIS),0)+1 FROM (SELECT IDINTGENESIS FROM " + tabela + " " +
                "UNION ALL SELECT IDINTGENESIS FROM AD_GENEXCFILA)");
             ResultSet rs = ps.executeQuery()) {
            rs.next(); return rs.getLong(1);
        }
    }

    private static String chave(CandidatoGenesis candidato) {
        if (candidato == null || candidato.chaveMovimento == null || candidato.chaveMovimento.trim().isEmpty()) {
            throw new IllegalStateException("CHAVE_MOVIMENTO ausente na view.");
        }
        return candidato.chaveMovimento.trim();
    }

    private static String limitar(String value, int max) {
        if (value == null) return null;
        return value.length() <= max ? value : value.substring(0, max);
    }
}
