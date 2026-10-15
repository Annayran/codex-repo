package br.com.crmbonus.clientes.query;

import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

import java.math.BigDecimal;
import java.sql.ResultSet;

public class PinSendQueryHelper {

    /**
     * Busca dados do cabecalho da nota para envio ao CRM
     */
    public static DynamicVO findNotaByNunota(BigDecimal nunota) throws Exception {
        JapeWrapper dao = JapeFactory.dao("CabecalhoNota");
        return dao.findByPK(nunota);
    }

    /**
     * Busca dados do parceiro (cliente) baseado no código
     */
    public static DynamicVO findParceiroByCode(BigDecimal codParc) throws Exception {
        JapeWrapper dao = JapeFactory.dao("Parceiro");
        return dao.findByPK(codParc);
    }

    /**
     * Extrai CPF limpo (apenas números) do parceiro
     */
    public static String extractCleanCPF(String cpfWithFormatting) {
        if (cpfWithFormatting == null || cpfWithFormatting.isEmpty()) {
            return null;
        }
        return cpfWithFormatting.replaceAll("[^0-9]", "");
    }

    /**
     * Retorna a DynamicVO do cabeçalho da nota mais recente para um parceiro (por NUNOTA desc).
     * Retorna null se não encontrar.
     */
    public static DynamicVO findLatestNotaByCodParc(BigDecimal codParc) throws Exception {
        EntityFacade facade = null;
        JdbcWrapper jdbc = null;
        NativeSql sql = null;

        try {
            facade = EntityFacadeFactory.getDWFFacade();
            jdbc = facade.getJdbcWrapper();
            jdbc.openSession();

            sql = new NativeSql(jdbc);
            sql.appendSql("SELECT NUNOTA FROM TGFCAB WHERE CODPARC = :CODPARC ORDER BY NUNOTA DESC");
            sql.setNamedParameter("CODPARC", codParc);

            try (ResultSet rs = sql.executeQuery()) {
                if (rs.next()) {
                    BigDecimal nunota = rs.getBigDecimal("NUNOTA");
                    if (nunota != null) {
                        return findNotaByNunota(nunota);
                    }
                }
            }
        } finally {
            if (sql != null) NativeSql.releaseResources(sql);
            if (jdbc != null) JdbcWrapper.closeSession(jdbc);
        }

        return null;
    }

    /**
     * Retorna o valor do AD_REQHASH de uma nota (ou null)
     */
    public static String getRequestHashFromNota(BigDecimal nunota) throws Exception {
        if (nunota == null) return null;
        DynamicVO vo = findNotaByNunota(nunota);
        if (vo == null) return null;
        return vo.asString("AD_REQHASH");
    }

}
