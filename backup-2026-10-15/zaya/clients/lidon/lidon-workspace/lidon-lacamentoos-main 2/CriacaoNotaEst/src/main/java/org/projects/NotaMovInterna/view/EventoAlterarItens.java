//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.projects.NotaMovInterna.view;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class EventoAlterarItens implements EventoProgramavelJava {
    public void beforeInsert(PersistenceEvent event) throws Exception {
        System.out.println("===== beforeInsert INICIO =====");
        DynamicVO vo = (DynamicVO) event.getVo();
        System.out.println("VO recebido: " + vo);
        BigDecimal numOS = vo.asBigDecimal("NUMOS");
        BigDecimal sequencia = vo.asBigDecimal("SEQUENCIA");
        System.out.println("NUMOS: " + numOS);
        System.out.println("SEQUENCIA: " + sequencia);
        Map<String, Object> result = this.buscarNunotaIntECodemp(numOS);
        System.out.println("Resultado buscarNunotaIntECodemp: " + result);
        if (result == null) {
            System.out.println("Result veio NULL — saindo do método");
        } else {
            BigDecimal qtd = vo.asBigDecimal("QUANTIDADE");
            BigDecimal vlrUnit = vo.asBigDecimal("VLRUNIT");
            String codVol = vo.asString("CODVOL");
            BigDecimal codProd = vo.asBigDecimal("CODPROD");
            BigDecimal codLocal = vo.asBigDecimal("CODLOCAL");
            System.out.println("QTD: " + qtd);
            System.out.println("VLRUNIT: " + vlrUnit);
            System.out.println("CODVOL: " + codVol);
            System.out.println("CODPROD: " + codProd);
            System.out.println("CODLOCAL: " + codLocal);
            System.out.println("NUNOTAINT do result: " + result.get("NUNOTAINT"));
            System.out.println("CODEMP do result: " + result.get("CODEMP"));

            JapeSession.SessionHandle hnd = null;

            try {
                System.out.println("Abrindo sessão JDBC...");
                hnd = JapeSession.open();

                JapeWrapper iteDAO = JapeFactory.dao("TGFITE");

                DynamicVO item = iteDAO.create()
                        .set("NUNOTA", (BigDecimal) result.get("NUNOTAINT"))
                        .set("SEQUENCIA", sequencia)
                        .set("CODPROD", codProd)
                        .set("QTDNEG", qtd)
                        .set("VLRUNIT", vlrUnit)
                        .set("CODVOL", codVol)
                        .set("CODEMP", (BigDecimal) result.get("CODEMP"))
                        .set("CODLOCALORIG", codLocal)
                        .set("ATUALESTOQUE", BigDecimal.ONE)
                        .set("RESERVA", "S")
                        .save();


            } catch (Exception e) {
                System.out.println("ERRO no beforeInsert:");
                e.printStackTrace();
                throw e;
            } finally {
                System.out.println("Fechando sessão JDBC...");
                JapeSession.close(hnd);

            }

            System.out.println("===== beforeInsert FIM =====");
        }

    }

    public void beforeUpdate(PersistenceEvent event) throws Exception {
        DynamicVO vo = (DynamicVO) event.getVo();
        BigDecimal numOS = vo.asBigDecimal("NUMOS");
        BigDecimal sequencia = vo.asBigDecimal("SEQUENCIA");
        Map<String, Object> result = this.buscarNunotaIntECodemp(numOS);
        if (result != null) {
            BigDecimal qtd = vo.asBigDecimal("QUANTIDADE");
            BigDecimal vlrUnit = vo.asBigDecimal("VLRUNIT");
            BigDecimal codProd = vo.asBigDecimal("CODPROD");
            BigDecimal codLocal = vo.asBigDecimal("CODLOCAL");
            JdbcWrapper jdbc = null;
            NativeSql sql = null;

            try {
                Map<String, Object> pk = new HashMap<>();
                pk.put("NUNOTA", result.get("NUNOTAINT"));
                pk.put("SEQUENCIA", sequencia);

                JapeFactory.dao(DynamicEntityNames.ITEM_NOTA)
                        .prepareToUpdateByPK(pk)
                        .set("QTDNEG", qtd)
                        .set("VLRUNIT", vlrUnit)
                        .set("CODLOCALORIG", codLocal)
                        .update();
            } catch (Exception e) {
                System.out.println("ERRO no beforeUpdate:");
            }

        }

    }

    public void beforeDelete(PersistenceEvent event) throws Exception {
        DynamicVO vo = (DynamicVO) event.getVo();
        BigDecimal numOS = vo.asBigDecimal("NUMOS");
        BigDecimal sequencia = vo.asBigDecimal("SEQUENCIA");
        BigDecimal codProd = vo.asBigDecimal("CODPROD");
        Map<String, Object> result = this.buscarNunotaIntECodemp(numOS);
        if (result != null) {

            try {
                JapeFactory.dao(DynamicEntityNames.ITEM_NOTA)
                        .deleteByCriteria(
                                "NUNOTA = ? AND SEQUENCIA = ? AND CODPROD = ?",
                                result.get("NUNOTAINT"), sequencia, codProd
                        );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            }
        }


    public void afterInsert(PersistenceEvent event) throws Exception {
    }

    public void afterUpdate(PersistenceEvent event) throws Exception {
    }

    public void afterDelete(PersistenceEvent event) throws Exception {
    }

    public void beforeCommit(TransactionContext tranCtx) throws Exception {
    }

    private Map<String, Object> buscarNunotaIntECodemp(BigDecimal numOS) throws Exception {
        JdbcWrapper jdbc = null;
        NativeSql sql = null;
        ResultSet rs = null;

        Map var7;
        try {
            jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
            jdbc.openSession();
            sql = new NativeSql(jdbc);
            sql.appendSql("SELECT NUNOTAINT, CODEMP FROM AD_TCSOSE WHERE NUMOS = :NUMOS");
            sql.setNamedParameter("NUMOS", numOS);
            rs = sql.executeQuery();
            if (rs.next()) {
                Map<String, Object> result = new HashMap();
                result.put("NUNOTAINT", rs.getBigDecimal("NUNOTAINT"));
                result.put("CODEMP", rs.getBigDecimal("CODEMP"));
                Object var6 = result;
                return (Map) var6;
            }

            Object var11 = null;
            var7 = (Map) var11;
        } finally {
            if (rs != null) {
                rs.close();
            }

            JdbcWrapper.closeSession(jdbc);
        }

        return var7;
    }
}
