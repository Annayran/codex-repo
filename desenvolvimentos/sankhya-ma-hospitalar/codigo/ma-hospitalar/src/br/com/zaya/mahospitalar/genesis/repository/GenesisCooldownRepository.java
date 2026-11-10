package br.com.zaya.mahospitalar.genesis.repository;

import br.com.sankhya.jape.dao.JdbcWrapper;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public final class GenesisCooldownRepository {
    // Persisted 429 evidence shares the existing transaction and survives job restarts.
    public boolean emEspera(JdbcWrapper jdbc) throws Exception {
        String estoque = new EstoqueGenesisRepository().instalado(jdbc)
                ? " UNION ALL SELECT DH_ATUALIZACAO,MENSAGEM MSG FROM AD_GENESTCTRL WHERE HTTP_STATUS=429" : "";
        String sql = "SELECT COUNT(*) FROM (" +
                "SELECT DH_ATUALIZACAO,DBMS_LOB.SUBSTR(MENSAGEM_ERRO,1000,1) MSG FROM AD_GENMOVEST WHERE HTTP_STATUS=429 " +
                "UNION ALL SELECT DH_ATUALIZACAO,DBMS_LOB.SUBSTR(MENSAGEM_ERRO,1000,1) MSG FROM AD_GENAPICTRL WHERE HTTP_STATUS=429 " +
                "UNION ALL SELECT DH_FIM,DBMS_LOB.SUBSTR(RESPOSTA,1000,1) MSG FROM AD_GENREQLOG WHERE HTTP_STATUS=429 " +
                "UNION ALL SELECT DH_ATUALIZACAO,MENSAGEM MSG FROM AD_GENEXCFILA WHERE HTTP_STATUS=429" + estoque + ") " +
                "WHERE DH_ATUALIZACAO+NVL(TO_NUMBER(REGEXP_SUBSTR(MSG,'COOLDOWN_MS=([0-9]{1,15});',1,1,NULL,1)),60000)/86400000>SYSDATE";
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() && rs.getLong(1) > 0;
        }
    }
}
