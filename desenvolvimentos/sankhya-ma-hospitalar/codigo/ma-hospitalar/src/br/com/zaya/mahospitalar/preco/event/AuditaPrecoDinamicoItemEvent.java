package br.com.zaya.mahospitalar.preco.event;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.comercial.LiberacaoAlcadaHelper;

import java.math.BigDecimal;
import java.sql.ResultSet;

public class AuditaPrecoDinamicoItemEvent implements EventoProgramavelJava {

    @Override
    public void beforeInsert(PersistenceEvent event) {
    }

    @Override
    public void beforeUpdate(PersistenceEvent event) {
    }

    @Override
    public void beforeDelete(PersistenceEvent event) {
    }

    @Override
    public void afterInsert(PersistenceEvent event) throws Exception {
        auditar(event);
    }

    @Override
    public void afterUpdate(PersistenceEvent event) throws Exception {
        if (event.getModifingFields() != null &&
                (event.getModifingFields().isModifing("AD_MAH_CALCHASH") ||
                 event.getModifingFields().isModifing("VLRUNIT") ||
                 event.getModifingFields().isModifing("VLRDESC") ||
                 event.getModifingFields().isModifing("QTDNEG") ||
                 event.getModifingFields().isModifing("CODPROD"))) {
            invalidarLiberacao(event);
            auditar(event);
        }
    }

    private void invalidarLiberacao(PersistenceEvent event) throws Exception {
        DynamicVO item = (DynamicVO) event.getVo();
        BigDecimal nuNota = item.asBigDecimal("NUNOTA");
        NativeSql sql = new NativeSql(event.getJdbcWrapper());
        ResultSet rs = null;
        try {
            sql.setNamedParameter("NUNOTA", nuNota);
            rs = sql.executeQuery(
                    "SELECT CFG.EVENTOLIB FROM TGFCAB C " +
                    "JOIN AD_MAH_PD_CONFIG CFG ON CFG.CODEMP=C.CODEMP AND CFG.ATIVO='S' " +
                    "WHERE C.NUNOTA=:NUNOTA"
            );
            if (rs.next()) {
                int evento = rs.getInt("EVENTOLIB");
                rs.close();
                rs = null;
                LiberacaoAlcadaHelper.zerarValorLiberado(
                        nuNota, "TGFCAB", BigDecimal.ZERO, evento,
                        "Preco dinamico alterado; nova validacao obrigatoria."
                );
                sql.resetSqlBuf();
                sql.cleanParameters();
                sql.setNamedParameter("NUNOTA", nuNota);
                sql.executeUpdate("UPDATE TGFCAB SET AD_MAH_LIBHASH=NULL WHERE NUNOTA=:NUNOTA");
            }
        } finally {
            if (rs != null) rs.close();
            NativeSql.releaseResources(sql);
        }
    }

    private void auditar(PersistenceEvent event) throws Exception {
        DynamicVO item = (DynamicVO) event.getVo();
        if (!item.containsProperty("AD_MAH_STATUS") || !"CALCULADO".equals(item.asString("AD_MAH_STATUS"))) {
            return;
        }

        NativeSql sql = new NativeSql(event.getJdbcWrapper());
        try {
            sql.setNamedParameter("NUNOTA", item.asBigDecimal("NUNOTA"));
            sql.setNamedParameter("SEQUENCIA", item.asBigDecimal("SEQUENCIA"));
            sql.setNamedParameter("CODUSU", usuarioAtual());
            sql.executeUpdate(
                    "INSERT INTO AD_MAH_PD_AUDITORIA (" +
                    " ORIGEMCALCULO,NUNOTA,SEQUENCIA,CODUSU,CODEMP,CODPARC,CODPROD,CODTIPVENDA," +
                    " CENARIO,PRECOBASE,PRECOAPLICADO,RECEITABRUTA,RECEITALIQUIDA,RESULTADO," +
                    " MARGEMCONTRIB,COEFA,CREDITOB,COEFO,DESPFIXAD,CUSTOGER,CALCHASH,FORMULAVERSAO,STATUSCALC" +
                    ") " +
                    "SELECT 'ITEM', I.NUNOTA, I.SEQUENCIA, :CODUSU, C.CODEMP, C.CODPARC, I.CODPROD, C.CODTIPVENDA," +
                    " I.AD_MAH_CENARIO, I.AD_MAH_PRECOBASE, I.VLRUNIT," +
                    " (I.QTDNEG * I.VLRUNIT * NVL(I.AD_MAH_COTACAO,1)), I.AD_MAH_RECEITALIQ," +
                    " I.AD_MAH_RESULTADO, I.AD_MAH_MC, I.AD_MAH_COEFA, I.AD_MAH_CREDITOB," +
                    " I.AD_MAH_COEFO, I.AD_MAH_DESPFIXAD, I.AD_MAH_CUSTOGER, I.AD_MAH_CALCHASH," +
                    " I.AD_MAH_FORMULAVERSAO, I.AD_MAH_STATUS " +
                    "FROM TGFITE I JOIN TGFCAB C ON C.NUNOTA=I.NUNOTA " +
                    "WHERE I.NUNOTA=:NUNOTA AND I.SEQUENCIA=:SEQUENCIA"
            );
        } finally {
            NativeSql.releaseResources(sql);
        }
    }

    private BigDecimal usuarioAtual() {
        AuthenticationInfo auth = AuthenticationInfo.getCurrentOrNull();
        return auth == null || auth.getUserID() == null ? BigDecimal.ZERO : auth.getUserID();
    }

    @Override
    public void afterDelete(PersistenceEvent event) {
    }

    @Override
    public void beforeCommit(TransactionContext tranCtx) {
    }
}
