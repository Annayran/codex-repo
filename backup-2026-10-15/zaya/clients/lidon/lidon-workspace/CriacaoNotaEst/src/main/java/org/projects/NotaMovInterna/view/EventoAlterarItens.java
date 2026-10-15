//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.projects.NotaMovInterna.view;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import org.projects.NotaMovInterna.utils.SankhyaUtils;

public class EventoAlterarItens implements EventoProgramavelJava {
    private static final String LOG_PREFIX = "[NotaMovInterna][EventoAlterarItens] ";

    public EventoAlterarItens() {
    }

    public void beforeInsert(PersistenceEvent event) throws Exception {
        DynamicVO vo = (DynamicVO)event.getVo();
        BigDecimal numOS = vo.asBigDecimal("NUMOS");
        BigDecimal sequencia = vo.asBigDecimal("SEQUENCIA");
        this.log("beforeInsert - NUMOS=" + numOS + ", SEQUENCIA=" + sequencia);
        Map<String, Object> result = this.buscarNunotaIntECodemp(numOS);
        if (result == null) {
            this.log("beforeInsert - NUNOTAINT nao encontrada para NUMOS=" + numOS + ", item nao sera replicado");
        } else {
            BigDecimal nunota = (BigDecimal)result.get("NUNOTAINT");
            BigDecimal qtd = vo.asBigDecimal("QUANTIDADE");
            BigDecimal vlrUnit = vo.asBigDecimal("VLRUNIT");
            String codVol = vo.asString("CODVOL");
            BigDecimal codProd = vo.asBigDecimal("CODPROD");
            BigDecimal codLocal = vo.asBigDecimal("CODLOCAL");
            BigDecimal codEmp = (BigDecimal)result.get("CODEMP");
            if (nunota == null || codProd == null || qtd == null || vlrUnit == null || codVol == null || codVol.trim().isEmpty() || codLocal == null || codEmp == null) {
                throw new IllegalStateException("Dados obrigatorios ausentes para inserir item na nota interna. NUMOS=" + numOS + ", NUNOTAINT=" + nunota + ", SEQUENCIA=" + sequencia + ", CODPROD=" + codProd + ", QTD=" + qtd + ", VLRUNIT=" + vlrUnit + ", CODVOL=" + codVol + ", CODLOCAL=" + codLocal + ", CODEMP=" + codEmp);
            }

            EntityFacade dwfFacade = EntityFacadeFactory.getDWFFacade();
            DynamicVO item = this.criarItemPadraoSeguro(dwfFacade);
            item.setProperty("NUNOTA", nunota);
            item.setProperty("SEQUENCIA", sequencia);
            item.setProperty("CODPROD", codProd);
            item.setProperty("QTDNEG", qtd);
            item.setProperty("VLRUNIT", vlrUnit);
            item.setProperty("CODVOL", codVol);
            item.setProperty("CODEMP", codEmp);
            item.setProperty("CODLOCALORIG", codLocal);
            item.setProperty("ATUALESTOQUE", BigDecimal.ONE);
            item.setProperty("RESERVA", "S");
            this.log("beforeInsert - incluindo item na nota via CACHelper NUNOTA=" + nunota + ", SEQUENCIA=" + sequencia + ", QTD=" + qtd + ", VLRUNIT=" + vlrUnit + ", CODLOCAL=" + codLocal + ", CODEMP=" + codEmp);
            SankhyaUtils.insereItem(dwfFacade, item, AuthenticationInfo.getCurrent());
        }
    }

    public void beforeUpdate(PersistenceEvent event) throws Exception {
        DynamicVO vo = (DynamicVO)event.getVo();
        BigDecimal numOS = vo.asBigDecimal("NUMOS");
        BigDecimal sequencia = vo.asBigDecimal("SEQUENCIA");
        this.log("beforeUpdate - NUMOS=" + numOS + ", SEQUENCIA=" + sequencia);
        if (numOS != null && sequencia != null) {
            BigDecimal qtd = vo.asBigDecimal("QUANTIDADE");
            BigDecimal vlrUnit = vo.asBigDecimal("VLRUNIT");
            BigDecimal codLocal = vo.asBigDecimal("CODLOCAL");
            if (qtd == null && vlrUnit == null && codLocal == null) {
                this.log("beforeUpdate - nenhum campo de item alterado (QTD/VLRUNIT/CODLOCAL). Update de nota interna ignorado para NUMOS=" + numOS + ", SEQUENCIA=" + sequencia);
            } else {
                Map<String, Object> result = this.buscarNunotaIntECodemp(numOS);
                if (result == null) {
                    throw new IllegalStateException("Nao foi possivel localizar NUNOTAINT para NUMOS=" + numOS + ".");
                } else {
                    BigDecimal nunota = (BigDecimal)result.get("NUNOTAINT");
                    BigDecimal codEmp = (BigDecimal)result.get("CODEMP");
                    this.log("beforeUpdate - alterando item via JapeWrapper NUNOTA=" + nunota + ", SEQUENCIA=" + sequencia + ", QTD=" + qtd + ", VLRUNIT=" + vlrUnit + ", CODLOCAL=" + codLocal + ", CODEMP=" + codEmp);
                    SankhyaUtils.alteraItemNotaViaJapeWrapper(nunota, sequencia, qtd, vlrUnit, codLocal, codEmp);
                }
            }
        } else {
            throw new IllegalArgumentException("NUMOS e SEQUENCIA sao obrigatorios para atualizar item da OS.");
        }
    }

    public void beforeDelete(PersistenceEvent event) throws Exception {
        DynamicVO vo = (DynamicVO)event.getVo();
        BigDecimal numOS = vo.asBigDecimal("NUMOS");
        BigDecimal sequencia = vo.asBigDecimal("SEQUENCIA");
        BigDecimal codProd = vo.asBigDecimal("CODPROD");
        this.log("beforeDelete - NUMOS=" + numOS + ", SEQUENCIA=" + sequencia + ", CODPROD=" + codProd);
        Map<String, Object> result = this.buscarNunotaIntECodemp(numOS);
        if (result != null && sequencia != null) {
            try {
                BigDecimal nunota = (BigDecimal)result.get("NUNOTAINT");
                if (nunota == null) {
                    this.log("beforeDelete - NUNOTAINT nula para NUMOS=" + numOS + ", exclusao ignorada");
                    return;
                }

                this.log("beforeDelete - removendo item da nota NUNOTA=" + nunota + ", SEQUENCIA=" + sequencia + ", CODPROD=" + codProd);
                if (codProd != null) {
                    JapeFactory.dao("ItemNota").deleteByCriteria("NUNOTA = ? AND SEQUENCIA = ? AND CODPROD = ?", new Object[]{nunota, sequencia, codProd});
                } else {
                    JapeFactory.dao("ItemNota").deleteByCriteria("NUNOTA = ? AND SEQUENCIA = ?", new Object[]{nunota, sequencia});
                }
            } catch (Exception e) {
                this.log("Erro no beforeDelete: " + e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            this.log("beforeDelete - NUNOTAINT ou SEQUENCIA nao disponivel, exclusao ignorada para NUMOS=" + numOS);
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
        if (numOS == null) {
            this.log("buscarNunotaIntECodemp - NUMOS nula");
            return null;
        } else {
            JdbcWrapper jdbc = null;
            NativeSql sql = null;
            ResultSet rs = null;

            Map<String, Object> result;
            try {
                jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
                jdbc.openSession();
                sql = new NativeSql(jdbc);
                sql.appendSql("SELECT NUNOTAINT, CODEMP FROM AD_TCSOSE WHERE NUMOS = :NUMOS");
                sql.setNamedParameter("NUMOS", numOS);
                rs = sql.executeQuery();
                if (!rs.next()) {
                    this.log("buscarNunotaIntECodemp - OS nao encontrada para NUMOS=" + numOS);
                    return null;
                }

                BigDecimal nunotaInt = rs.getBigDecimal("NUNOTAINT");
                BigDecimal codEmp = rs.getBigDecimal("CODEMP");
                if (nunotaInt != null) {
                    result = new HashMap<String, Object>();
                    result.put("NUNOTAINT", nunotaInt);
                    result.put("CODEMP", codEmp);
                    return result;
                }

                this.log("buscarNunotaIntECodemp - NUNOTAINT ainda nula para NUMOS=" + numOS);
                result = null;
            } finally {
                if (rs != null) {
                    rs.close();
                }

                JdbcWrapper.closeSession(jdbc);
            }

            return result;
        }
    }

    private DynamicVO criarItemPadraoSeguro(EntityFacade dwfFacade) throws Exception {
        try {
            return (DynamicVO)dwfFacade.getDefaultValueObjectInstance("ItemNota");
        } catch (Exception erro) {
            throw new IllegalStateException("Nao foi possivel criar ValueObject padrao de ItemNota pelo EntityFacade. Erro=" + erro.getMessage(), erro);
        }
    }

    private void log(String message) {
        System.out.println(LOG_PREFIX + message);
    }
}
