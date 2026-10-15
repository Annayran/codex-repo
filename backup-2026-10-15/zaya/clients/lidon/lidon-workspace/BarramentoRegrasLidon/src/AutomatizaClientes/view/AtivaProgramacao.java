package AutomatizaClientes.view;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;

import java.math.BigDecimal;
import java.sql.ResultSet;

public class AtivaProgramacao implements ScheduledAction {
    @Override
    public void onTime(ScheduledActionContext scheduledActionContext) {
        EntityFacade dwFacade = EntityFacadeFactory.getDWFFacade();
        JdbcWrapper jdbc = dwFacade.getJdbcWrapper();
        JapeSession.SessionHandle hnd = null;

        NativeSql sql = new NativeSql(jdbc);

        try {
            hnd = JapeSession.open();
            System.out.println(">> Iniciando execução do onTime...");

            String updateSql =
                    "UPDATE TGFPAR P \n" +
                            "SET P.BLOQUEAR = 'N' \n" +
                            "WHERE P.ATIVO = 'S' \n" +
                            "  AND P.BLOQUEAR = 'S' \n" +
                            "  AND NOT EXISTS ( \n" +
                            "        SELECT 1 \n" +
                            "        FROM TGFFIN F \n" +
                            "        WHERE F.CODPARC = P.CODPARC \n" +
                            "          AND F.RECDESP = 1 \n" + // Apenas receitas
                            "          AND F.DHBAIXA IS NULL \n" +
                            "          AND (F.VLRBAIXA IS NULL OR F.VLRBAIXA = 0 OR F.VLRBAIXA < F.VLRDESDOB) \n" +
                            "          AND F.DTVENC <= TRUNC(SYSDATE) - 2 \n" + // Vencido há mais de 2 dias
                            "    )";

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


//        EntityFacade dwFacade = EntityFacadeFactory.getDWFFacade();
//        JdbcWrapper jdbc = dwFacade.getJdbcWrapper();
//        JapeSession.SessionHandle hnd = null;
//        ResultSet rs = null;
//
//        NativeSql sql = new NativeSql(jdbc);
//
//        try {
//            hnd = JapeSession.open();
//            System.out.println(">> Iniciando execução do onTime...");
//
//            String query = "SELECT P.CODPARC AS CODPARC " +
//                    "FROM TGFFIN F " +
//                    "JOIN TGFPAR P ON P.CODPARC = F.CODPARC " +
//                    "WHERE F.VLRBAIXA IS NOT NULL " +
//                    "AND F.DHBAIXA IS NOT NULL " +
//                    "AND P.BLOQUEAR = 'S' " +
//                    "AND P.ATIVO = 'S'";
//
//            sql.appendSql(query);
//            System.out.println(">> SQL montada: " + query);
//
//            rs = sql.executeQuery();
//            System.out.println(">> Consulta executada. Processando resultados...");
//
//            int count = 0;
//            while (rs.next()) {
//                int codparc = rs.getInt("CODPARC");
//                System.out.println(">> Atualizando parceiro CODPARC = " + codparc);
//
//                JapeFactory.dao(DynamicEntityNames.PARCEIRO)
//                        .prepareToUpdateByPK(codparc)
//                        .set("BLOQUEAR", "N")
//                        .update();
//
//                System.out.println(">> Parceiro " + codparc + " atualizado com sucesso (BLOQUEAR = 'N').");
//                count++;
//            }
//
//            System.out.println(">> Total de parceiros atualizados: " + count);
//
//        } catch (Exception e) {
//            System.out.println(">> Erro no onTime: " + e.getMessage());
//            throw new RuntimeException(e);
//        } finally {
//            NativeSql.releaseResources(sql);
//            JdbcWrapper.closeSession(jdbc);
//            JapeSession.close(hnd);
//            System.out.println(">> Recursos liberados e sessão encerrada.");
//        }
//    }

}
