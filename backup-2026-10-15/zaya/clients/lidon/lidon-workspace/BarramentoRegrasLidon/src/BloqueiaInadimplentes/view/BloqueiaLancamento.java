package BloqueiaInadimplentes.view;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

import java.math.BigDecimal;
import java.sql.ResultSet;

public class BloqueiaLancamento implements EventoProgramavelJava {

    @Override
    public void beforeInsert(PersistenceEvent event) throws Exception {
        DynamicVO vo = (DynamicVO) event.getVo();
        BigDecimal codparc = (BigDecimal) vo.getProperty("CODPARC");
        System.out.println(">> beforeInsert iniciado. CODPARC recebido: " + codparc);

        EntityFacade dwFacade = EntityFacadeFactory.getDWFFacade();
        JdbcWrapper jdbc = dwFacade.getJdbcWrapper();
        JapeSession.SessionHandle hnd = null;
        ResultSet rs = null;

        NativeSql sql = new NativeSql(jdbc);

        try {
            hnd = JapeSession.open();
            System.out.println(">> Sessão Jape aberta.");

            // Monta a SQL
            String query =
                    "SELECT 1\n" +
                            "FROM TGFFIN F\n" +
                            "WHERE F.CODPARC = :CODPARC \n" +
                            "  AND F.RECDESP = 1\n" +
                            "  AND F.DHBAIXA IS NULL\n" +
                            "  AND (F.VLRBAIXA IS NULL OR F.VLRBAIXA = 0 \n" +
                            "       OR F.VLRBAIXA < F.VLRDESDOB)\n" +
                            "  AND F.DTVENC <= TRUNC(SYSDATE) - 4" +
                            "  AND F.PROVISAO = 'N'";
            // sem pagamento, pagamento zerado ou parcial

            sql.appendSql(query);
            sql.setNamedParameter("CODPARC", codparc);

            System.out.println(">> SQL montada: " + query.replace(":CODPARC", codparc.toString()));

// Executa a consulta
            rs = sql.executeQuery();
            System.out.println(">> SQL executada.");

            if (rs.next()) {
                // Encontrou título em aberto ou parcial
                System.out.println(">> Cliente " + codparc + " possui título(s) em aberto ou pagamento parcial!");
                throw new RuntimeException(
                        "__PRETTY_MSG__<p>Cliente possui título(s) em aberto ou pagamento parcial!<br>Não é possível lançar OS para este cliente.</p>"
                );
            } else {
                System.out.println(">> Cliente " + codparc + " está LIBERADO para lançar OS.");
            }


        } catch (Exception e) {
            System.out.println(">> Erro encontrado: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            NativeSql.releaseResources(sql);
            JdbcWrapper.closeSession(jdbc);
            JapeSession.close(hnd);
            System.out.println(">> Recursos liberados e sessão encerrada.");
        }
    }


    @Override
    public void beforeUpdate(PersistenceEvent event) throws Exception {

    }

    @Override
    public void beforeDelete(PersistenceEvent event) throws Exception {

    }

    @Override
    public void afterInsert(PersistenceEvent event) throws Exception {

    }

    @Override
    public void afterUpdate(PersistenceEvent event) throws Exception {

    }

    @Override
    public void afterDelete(PersistenceEvent event) throws Exception {

    }

    @Override
    public void beforeCommit(TransactionContext tranCtx) throws Exception {

    }
}
