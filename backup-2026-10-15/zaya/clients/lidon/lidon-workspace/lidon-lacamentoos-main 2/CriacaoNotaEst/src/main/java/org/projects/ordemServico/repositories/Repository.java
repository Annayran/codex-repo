package org.projects.ordemServico.repositories;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import com.sankhya.util.BigDecimalUtil;
import java.math.BigDecimal;

public class Repository {
    public static BigDecimal buscarCodCenCusPadUsuario(BigDecimal codUsu) {
        try {
            return NativeSql.getBigDecimal("CODCENCUSPAD", "TSIUSU", "CODUSU = ?", new Object[]{codUsu});
        } catch (Exception e) {
            e.getMessage();
            return BigDecimal.ZERO;
        }
    }

    public static BigDecimal getTipoNegociacaoGarantia(BigDecimal codemp) throws Exception {
        return BigDecimalUtil.getValueOrZero(NativeSql.getBigDecimal("AD_CODTIPVENDA", "TGFEMP", "CODEMP = ? ", new Object[]{codemp}));
    }

    public static String getTipoOs(BigDecimal numOs) throws Exception {
        return NativeSql.getString("TIP.TIPOOS", "AD_TCSOSE OSE INNER JOIN AD_TIPOORDEMSERVICO TIP ON TIP.CODTIPODEOS = OSE.CODTIPODEOS ", "OSE.NUMOS  = ? ", new Object[]{numOs});
    }


    public void atualizarMovInternaComoPendente(BigDecimal nunotaInt) throws Exception {
        JdbcWrapper jdbc = null;
        NativeSql query = null;

        try {
            jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
            jdbc.openSession();

            query = new NativeSql(jdbc);
            query.appendSql("UPDATE TGFCAB SET PENDENTE = 'N' WHERE NUNOTA = :NUNOTAINT");
            query.setNamedParameter("NUNOTA", nunotaInt);

            query.executeUpdate();

        } catch (Exception e) {
            throw new Exception("Erro ao atualizar PENDENTE da mov. interna: " + e.getMessage(), e);
        } finally {
            JdbcWrapper.closeSession(jdbc);
        }
    }


}
