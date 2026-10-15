package org.projects.NotaMovInterna.utils;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.bmp.PersistentLocalEntity;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.jape.vo.PrePersistEntityState;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.comercial.centrais.CACHelper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SankhyaUtils {
    public static ResultSet consultaSQLReturnResultSet(String query) throws Exception {
        JdbcWrapper jdbc = null;
        EntityFacade dwfEntityFacade = EntityFacadeFactory.getDWFFacade();
        jdbc = dwfEntityFacade.getJdbcWrapper();
        jdbc.openSession();
        NativeSql sql = null;
        try {
            sql = new NativeSql(jdbc);
            sql.appendSql(query);
            ResultSet result = sql.executeQuery();
            return result;
        } catch (Throwable throwable) {
            NativeSql.releaseResources(sql);
            throw throwable;
        }
    }

    public static void execQuery(String query) throws Exception {
        JdbcWrapper jdbc = null;
        EntityFacade dwfEntityFacade = EntityFacadeFactory.getDWFFacade();
        jdbc = dwfEntityFacade.getJdbcWrapper();
        jdbc.openSession();
        NativeSql sql = null;
        try {
            sql = new NativeSql(jdbc);
            sql.appendSql(query);
            sql.executeUpdate();
        } catch (Throwable throwable) {
            NativeSql.releaseResources(sql);
            throw throwable;
        }
        NativeSql.releaseResources(sql);
    }

    public static String createInClause(String fieldName, List lista) {
        String ret = "";
        int inMaxElements = 1000;
        Double aux = Math.ceil(lista.size() * 1.0 / inMaxElements);
        Integer lastIn = aux.intValue();
        for (int inNumber = 1; inNumber <= lastIn; inNumber++) {
            ret += fieldName + " IN (";
            int countInLine = 0;
            for (int i = (inNumber - 1) * inMaxElements; i < (inMaxElements * inNumber) && i < lista.size(); i++) {
                countInLine++;
                ret += lista.get(i).toString();
                int nextPosition = i + 1;
                if (nextPosition < (inMaxElements * inNumber) && nextPosition < lista.size()) {
                    ret += ",";
                }
                if (countInLine == 50) {
                    ret += "\n";
                    countInLine = 0;
                }
            }
            ret += ")\n";
        }
        return ret;
    }

    public static BigDecimal convertAtualEstoqueTop2Ite(String atualEst) {
        BigDecimal convertedValue;
        switch(atualEst) {
            case "B":
                convertedValue = new BigDecimal(-1);
                break;
            case "E":
                convertedValue = new BigDecimal(1);
                break;
            case "N":
                convertedValue = new BigDecimal(0);
                break;
            case "R":
                convertedValue = new BigDecimal(0);
                break;
            default:
                convertedValue = null;
        }

        return convertedValue;
    }

    public static DynamicVO insereItem(EntityFacade dwfFacade, DynamicVO item, AuthenticationInfo authInfo) throws Exception {
        Collection<PrePersistEntityState> itens = new ArrayList<PrePersistEntityState>();

        PrePersistEntityState itePreState = PrePersistEntityState.build(dwfFacade,
                DynamicEntityNames.ITEM_NOTA, item);

        itens.add(itePreState);

        CACHelper cacHelper = new CACHelper();
        cacHelper.incluirAlterarItem(item.asBigDecimal("NUNOTA"), authInfo, itens, true);

        DynamicVO insertedIte = JapeFactory.dao(DynamicEntityNames.ITEM_NOTA).findByPK(
                new Object[] { item.asBigDecimal("NUNOTA"), item.asBigDecimal("SEQUENCIA") }
        );

        return insertedIte;
    }

    public static DynamicVO insereLigacaoEntreNotas(EntityFacade dwfFacade, BigDecimal nunotaOrig, BigDecimal sequenciaOrig,
                                                    BigDecimal nunotaDest, BigDecimal sequenciaDest, BigDecimal qtdAtendida) throws Exception {
        DynamicVO varVO = (DynamicVO) dwfFacade.getDefaultValueObjectInstance(DynamicEntityNames.COMPRA_VENDA_VARIOS_PEDIDO);
        varVO.setProperty("NUNOTA", nunotaDest);
        varVO.setProperty("SEQUENCIA", sequenciaDest);
        varVO.setProperty("NUNOTAORIG", nunotaOrig);
        varVO.setProperty("SEQUENCIAORIG", sequenciaOrig);
        varVO.setProperty("QTDATENDIDA", qtdAtendida);
        PersistentLocalEntity createEntity = dwfFacade.createEntity(DynamicEntityNames.COMPRA_VENDA_VARIOS_PEDIDO, (EntityVO) varVO);
        return (DynamicVO) createEntity.getValueObject();
    }
}