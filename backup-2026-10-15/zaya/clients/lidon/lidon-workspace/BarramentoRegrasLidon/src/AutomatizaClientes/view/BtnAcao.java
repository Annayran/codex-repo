package AutomatizaClientes.view;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

import java.sql.ResultSet;

public class BtnAcao implements AcaoRotinaJava {
    @Override
    public void doAction(ContextoAcao contexto) throws Exception {
        EntityFacade dwFacade = EntityFacadeFactory.getDWFFacade();
        JdbcWrapper jdbc = dwFacade.getJdbcWrapper();
        JapeSession.SessionHandle hnd = null;

        NativeSql sql = new NativeSql(jdbc);

        try {
            hnd = JapeSession.open();
            System.out.println(">> Iniciando execução do onTime...");

            String updateSql =
                    "UPDATE TGFPAR " +
                            "SET BLOQUEAR = 'N' " +
                            "WHERE CODPARC IN ( " +
                            "    SELECT P.CODPARC " +
                            "    FROM TGFPAR P " +
                            "    WHERE P.ATIVO = 'S' " +
                            "      AND P.BLOQUEAR = 'S' " +
                            "      AND NOT EXISTS ( " +
                            "          SELECT 1 " +
                            "          FROM TGFFIN F " +
                            "          WHERE F.CODPARC = P.CODPARC " +
                            "            AND F.DHBAIXA IS NULL " +          // título ainda em aberto
                            "            AND TRUNC(F.DTVENC) BETWEEN TRUNC(ADD_MONTHS(SYSDATE, 0), 'MM') " + // início do mês atual
                            "            AND TRUNC(SYSDATE) " +            // até hoje
                            "      ) " +
                            ")";

            sql.appendSql(updateSql);
            System.out.println(">> SQL UPDATE montada: " + updateSql);

            sql.executeUpdate();
            System.out.println(">> Total de parceiros atualizados: " + sql.getLastUpdateCount());

        } catch (Exception e) {
            System.out.println(">> Erro no onTime: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            NativeSql.releaseResources(sql);
            JdbcWrapper.closeSession(jdbc);
            JapeSession.close(hnd);
            System.out.println(">> Recursos liberados e sessão encerrada.");
        }
    }
}

