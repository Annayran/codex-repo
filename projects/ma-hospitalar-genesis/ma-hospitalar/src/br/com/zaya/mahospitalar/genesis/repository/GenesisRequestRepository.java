package br.com.zaya.mahospitalar.genesis.repository;

import br.com.zaya.mahospitalar.genesis.client.GenesisApiResult;
import br.com.zaya.mahospitalar.genesis.util.*;
import java.sql.*;

/** Request evidence survives a later rollback of business data. */
public final class GenesisRequestRepository {
    public String iniciar(GenesisExecutionLog log, String metodo, String caminho, String corpo) throws Exception {
        final String id = java.util.UUID.randomUUID().toString();
        GenesisTransaction.execute(jdbc -> {
            try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                    "INSERT INTO AD_GENREQLOG (IDREQ,EXECUCAO,FLUXO,CONTEXTO,METODO,ENDPOINT,DH_INICIO,REQUISICAO,IDENTIDADE,PERSISTENCIA,ENTIDADE,IDCONSULTADO,CHAVE_CONTROLE,CHAVE_MOVIMENTO) " +
                    "VALUES (?,?,?,?,?,?,SYSDATE,?,'PENDENTE','PENDENTE',?,?,?,?)")) {
                ps.setString(1,id); ps.setString(2,log.getExecucao()); ps.setString(3,log.getFluxo());
                ps.setString(4,limit(GenesisSanitizer.sanitizeForLog(log.getRegistro()),1000));
                ps.setString(5,metodo); ps.setString(6,limit(GenesisSanitizer.sanitizeForLog(caminho),1000));
                clob(ps,7,GenesisSanitizer.sanitizeForHistory(corpo));
                String[] partes=caminho.replaceFirst("^/","").split("/",2);
                ps.setString(8,limit(partes[0],50));
                ps.setString(9,partes.length==2 && partes[1].matches("[1-9][0-9]*") ? partes[1] : null);
                ps.setString(10,limit(log.getControle(),200));
                int pos=log.getRegistro().indexOf("movimento=");
                ps.setString(11,pos>=0 ? limit(log.getRegistro().substring(pos+10),200) : null);
                ps.executeUpdate();
            }
            if(log.getRegistro().startsWith("movimento=")) {
                try(PreparedStatement ps=jdbc.getConnection().prepareStatement("UPDATE AD_GENMOVEST SET ULT_OPERACAO=? WHERE CHAVE_MOVIMENTO=?")) {
                    ps.setString(1,metodo);ps.setString(2,log.getRegistro().substring("movimento=".length()));ps.executeUpdate();
                }
            }
            return null;
        });
        return id;
    }

    public GenesisApiResult finalizar(String id, GenesisApiResult r, long inicio, String corpo) throws Exception {
        if (id == null) return r;
        GenesisTransaction.execute(jdbc -> {
            try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                    "UPDATE AD_GENREQLOG SET DH_FIM=SYSDATE,DURACAO_MS=?,HTTP_STATUS=?,CLASSIFICACAO=?,IDRETORNO=?,RESPOSTA=? WHERE IDREQ=?")) {
                ps.setLong(1,System.currentTimeMillis()-inicio); ps.setInt(2,r.getHttpStatus());
                ps.setString(3,r.getClassificacao().name()); ps.setString(4,r.getIdGenesis());
                clob(ps,5,GenesisSanitizer.sanitizeForHistory(corpo)); ps.setString(6,id); exigirUm(ps.executeUpdate());
            }
            return null;
        });
        return r.comRastreio(id);
    }

    public static void identidade(GenesisApiResult r) throws Exception {
        if (r == null || r.getIdRastreio() == null) return;
        GenesisTransaction.execute(jdbc -> {
            try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                    "UPDATE AD_GENREQLOG SET IDENTIDADE='VALIDADA' WHERE IDREQ=?")) {
                ps.setString(1,r.getIdRastreio()); exigirUm(ps.executeUpdate());
            }
            return null;
        });
    }

    public static void persistido(Connection connection, GenesisApiResult r, String detalhe) throws Exception {
        if (r == null || r.getIdRastreio() == null) return;
        // Must participate in the same transaction as the applied business update.
        try (PreparedStatement ps = connection.prepareStatement(
                "UPDATE AD_GENREQLOG SET PERSISTENCIA='CONFIRMADA',DH_PERSISTENCIA=SYSDATE,DETALHE=? WHERE IDREQ=?")) {
            clob(ps,1,GenesisSanitizer.sanitizeForLog(detalhe)); ps.setString(2,r.getIdRastreio()); exigirUm(ps.executeUpdate());
        }
    }
    public static void falhaPersistencia(GenesisApiResult r, Exception erro) throws Exception {
        if (r == null || r.getIdRastreio() == null) return;
        GenesisTransaction.execute(jdbc -> {
            falhaPersistencia(jdbc.getConnection(),r,erro);
            return null;
        });
    }
    static void falhaPersistencia(Connection connection, GenesisApiResult r, Exception erro) throws Exception {
        try (PreparedStatement ps = connection.prepareStatement(
                    "UPDATE AD_GENREQLOG SET DETALHE=? WHERE IDREQ=? AND PERSISTENCIA<>'CONFIRMADA'")) {
                clob(ps,1,GenesisSanitizer.sanitizeForLog("FALHA_PERSISTENCIA_LOCAL: " + erro.getClass().getSimpleName() + ": " + erro.getMessage()));
                ps.setString(2,r.getIdRastreio()); ps.executeUpdate();
        }
    }
    private static void exigirUm(int n) { if(n!=1) throw new IllegalStateException("Historico de requisicao ausente ou ambiguo."); }
    private static String limit(String s,int n) { return s==null?null:s.substring(0,Math.min(s.length(),n)); }
    private static void clob(PreparedStatement ps,int i,String s) throws SQLException {
        if(s==null) ps.setNull(i,Types.CLOB); else ps.setCharacterStream(i,new java.io.StringReader(s),s.length());
    }
}
