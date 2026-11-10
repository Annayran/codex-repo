package br.com.zaya.mahospitalar.genesis.repository;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.zaya.mahospitalar.genesis.domain.TipoEntidade;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public final class BaseGenesisRefreshRepository {
    public static final class Alvo {
        public final TipoEntidade tipo;
        public final long id;
        public Alvo(TipoEntidade tipo, long id) { this.tipo = tipo; this.id = id; }
    }

    public Alvo proximo(JdbcWrapper jdbc) throws Exception {
        StringBuilder union = new StringBuilder();
        String[][] fontes = {{"CLIENTE","AD_GENCLIENTE"},{"SETOR","AD_GENSECTOR"},
                {"MATERIAL","AD_GENMATERIAL"},{"FABRICANTE","AD_GENEMPRESA"},
                {"MODELO","AD_GENMODELO"},{"ITEM_MATERIAL","AD_GENITEMMAT"}};
        for (String[] fonte : fontes) {
            if (union.length() > 0) union.append(" UNION ALL ");
            union.append("SELECT '").append(fonte[0]).append("' TIPO,IDGENESIS,DHCONCILIACAO FROM ")
                    .append(fonte[1]).append(" WHERE IDBASE=1 AND IDGENESIS>0");
        }
        String sql = "SELECT * FROM (SELECT R.TIPO,R.IDGENESIS FROM (" + union + ") R " +
                "LEFT JOIN AD_GENAPICTRL M ON M.CHAVE_MOVIMENTO='API|REFRESH|'||R.TIPO||'|'||TO_CHAR(R.IDGENESIS) " +
                "WHERE (R.DHCONCILIACAO IS NULL OR R.DHCONCILIACAO<=SYSDATE-1) " +
                "AND (M.DH_ATUALIZACAO IS NULL OR M.DH_ATUALIZACAO<=SYSDATE-" +
                "LEAST(60,POWER(2,LEAST(NVL(M.TENTATIVAS,0),6)))/1440) " +
                "ORDER BY (SELECT MAX(Z.DH_ATUALIZACAO) FROM AD_GENAPICTRL Z WHERE Z.TIPO_MOVIMENTO='RECONSULTA_'||R.TIPO) ASC NULLS FIRST," +
                "COALESCE(M.DH_ATUALIZACAO,R.DHCONCILIACAO) ASC NULLS FIRST,R.TIPO,R.IDGENESIS) WHERE ROWNUM<=1";
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            return rs.next() ? new Alvo(TipoEntidade.valueOf(rs.getString(1)), rs.getLong(2)) : null;
        }
    }
}
