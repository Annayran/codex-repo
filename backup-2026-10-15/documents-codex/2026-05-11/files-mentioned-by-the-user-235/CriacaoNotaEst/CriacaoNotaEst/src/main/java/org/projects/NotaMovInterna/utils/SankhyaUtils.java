//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.projects.NotaMovInterna.utils;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.bmp.PersistentLocalEntity;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.util.JapeSessionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.jape.vo.PrePersistEntityState;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.comercial.centrais.CACHelper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SankhyaUtils {
    private static final String LOG_PREFIX = "[NotaMovInterna][SankhyaUtils] ";

    public SankhyaUtils() {
    }

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

    private static void log(String message) {
        System.out.println(LOG_PREFIX + message);
    }

    public static String createInClause(String fieldName, List lista) {
        String ret = "";
        int inMaxElements = 1000;
        Double aux = Math.ceil((double)lista.size() * (double)1.0F / (double)inMaxElements);
        Integer lastIn = aux.intValue();

        for(int inNumber = 1; inNumber <= lastIn; ++inNumber) {
            ret = ret + fieldName + " IN (";
            int countInLine = 0;

            for(int i = (inNumber - 1) * inMaxElements; i < inMaxElements * inNumber && i < lista.size(); ++i) {
                ++countInLine;
                ret = ret + lista.get(i).toString();
                int nextPosition = i + 1;
                if (nextPosition < inMaxElements * inNumber && nextPosition < lista.size()) {
                    ret = ret + ",";
                }

                if (countInLine == 50) {
                    ret = ret + "\n";
                    countInLine = 0;
                }
            }

            ret = ret + ")\n";
        }

        return ret;
    }

    public static BigDecimal convertAtualEstoqueTop2Ite(String atualEst) {
        BigDecimal convertedValue;
        switch (atualEst) {
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
        return salvarItemComValidacao(dwfFacade, item, authInfo, true);
    }

    public static DynamicVO salvarItemComValidacao(EntityFacade dwfFacade, DynamicVO item, AuthenticationInfo authInfo, boolean inicializaProdutos) throws Exception {
        if (item == null) {
            throw new IllegalArgumentException("Item da nota nao pode ser nulo para validacao CAC.");
        } else {
            BigDecimal nunota = item.asBigDecimal("NUNOTA");
            BigDecimal sequencia = item.asBigDecimal("SEQUENCIA");
            BigDecimal codProd = item.asBigDecimal("CODPROD");
            BigDecimal codEmp = item.asBigDecimal("CODEMP");
            if (nunota == null) {
                throw new IllegalStateException("NUNOTA nula no item antes da validacao CAC.");
            } else if (authInfo == null) {
                throw new IllegalStateException("AuthenticationInfo nula no contexto para validar item da nota. NUNOTA=" + nunota);
            } else if (sequencia != null && codProd != null && codEmp != null) {
                if (inicializaProdutos) {
                    BigDecimal codLocal = item.asBigDecimal("CODLOCALORIG");
                    String codVol = item.asString("CODVOL");
                    if (codLocal == null || codVol == null || codVol.trim().isEmpty()) {
                        throw new IllegalStateException("Payload incompleto para inicializacao de produto no CAC. NUNOTA=" + nunota + ", SEQUENCIA=" + sequencia + ", CODLOCALORIG=" + codLocal + ", CODVOL=" + codVol);
                    }
                }

                Collection<PrePersistEntityState> itens = new ArrayList<PrePersistEntityState>();
                PrePersistEntityState itePreState = PrePersistEntityState.build(dwfFacade, "ItemNota", item);
                itens.add(itePreState);
                log("Executando CACHelper.incluirAlterarItem - NUNOTA=" + nunota + ", SEQUENCIA=" + sequencia + ", CODPROD=" + codProd + ", CODEMP=" + codEmp + ", QTDNEG=" + item.asBigDecimal("QTDNEG") + ", VLRUNIT=" + item.asBigDecimal("VLRUNIT") + ", VLRTOT=" + item.asBigDecimal("VLRTOT") + ", CODLOCALORIG=" + item.asBigDecimal("CODLOCALORIG") + ", CODVOL=" + item.asString("CODVOL") + ", CONTROLE=" + item.asString("CONTROLE") + ", INICIALIZA_PROD=" + inicializaProdutos);
                Object previousCentralCtx = JapeSessionContext.getProperty("br.com.sankhya.com.CentralCompraVenda");

                try {
                    JapeSessionContext.putProperty("br.com.sankhya.com.CentralCompraVenda", Boolean.TRUE);
                    CACHelper cacHelper = new CACHelper();
                    cacHelper.incluirAlterarItem(nunota, authInfo, itens, inicializaProdutos);
                } catch (NullPointerException npe) {
                    throw new IllegalStateException("Falha interna no CACHelper ao incluir/alterar item. Verifique payload do item: NUNOTA=" + nunota + ", SEQUENCIA=" + sequencia + ", CODPROD=" + codProd + ", CODEMP=" + codEmp + ", QTDNEG=" + item.asBigDecimal("QTDNEG") + ", VLRUNIT=" + item.asBigDecimal("VLRUNIT") + ", VLRTOT=" + item.asBigDecimal("VLRTOT") + ", CODLOCALORIG=" + item.asBigDecimal("CODLOCALORIG") + ", CODVOL=" + item.asString("CODVOL") + ", CONTROLE=" + item.asString("CONTROLE") + ", INICIALIZA_PROD=" + inicializaProdutos, npe);
                } finally {
                    JapeSessionContext.putProperty("br.com.sankhya.com.CentralCompraVenda", previousCentralCtx);
                }

                return JapeFactory.dao("ItemNota").findByPK(new Object[]{nunota, sequencia});
            } else {
                throw new IllegalStateException("Campos obrigatorios ausentes para validar item no CAC. NUNOTA=" + nunota + ", SEQUENCIA=" + sequencia + ", CODPROD=" + codProd + ", CODEMP=" + codEmp);
            }
        }
    }

    public static DynamicVO alteraItemNotaComValidacao(EntityFacade dwfFacade, BigDecimal nunota, BigDecimal sequencia, BigDecimal qtd, BigDecimal vlrUnit, BigDecimal codLocal, BigDecimal codEmp, AuthenticationInfo authInfo) throws Exception {
        if (nunota != null && sequencia != null) {
            DynamicVO itemAtual = JapeFactory.dao("ItemNota").findByPK(new Object[]{nunota, sequencia});
            if (itemAtual == null) {
                throw new IllegalStateException("Item da nota nao encontrado para alteracao. NUNOTA=" + nunota + ", SEQUENCIA=" + sequencia);
            } else {
                BigDecimal qtdAtual = itemAtual.asBigDecimal("QTDNEG");
                BigDecimal vlrUnitAtual = itemAtual.asBigDecimal("VLRUNIT");
                BigDecimal codLocalAtual = itemAtual.asBigDecimal("CODLOCALORIG");
                BigDecimal codEmpAtual = itemAtual.asBigDecimal("CODEMP");
                BigDecimal atualEstoqueAtual = itemAtual.asBigDecimal("ATUALESTOQUE");
                String reservaAtual = itemAtual.asString("RESERVA");
                BigDecimal percDescAtual = itemAtual.asBigDecimal("PERCDESC");
                BigDecimal vlrDescAtual = itemAtual.asBigDecimal("VLRDESC");
                BigDecimal qtdFinal = qtd != null ? qtd : qtdAtual;
                BigDecimal vlrUnitFinal = vlrUnit != null ? vlrUnit : vlrUnitAtual;
                BigDecimal codLocalFinal = codLocal != null ? codLocal : codLocalAtual;
                BigDecimal codEmpFinal = codEmp != null ? codEmp : codEmpAtual;
                BigDecimal atualEstoqueFinal = atualEstoqueAtual != null ? atualEstoqueAtual : BigDecimal.ONE;
                String reservaFinal = reservaAtual != null && !reservaAtual.trim().isEmpty() ? reservaAtual : "S";
                BigDecimal percDescFinal = percDescAtual != null ? percDescAtual : BigDecimal.ZERO;
                BigDecimal vlrDescFinal = vlrDescAtual != null ? vlrDescAtual : BigDecimal.ZERO;
                BigDecimal vlrTotFinal = qtdFinal != null && vlrUnitFinal != null ? qtdFinal.multiply(vlrUnitFinal) : itemAtual.asBigDecimal("VLRTOT");
                itemAtual.setProperty("QTDNEG", qtdFinal);
                itemAtual.setProperty("VLRUNIT", vlrUnitFinal);
                itemAtual.setProperty("CODLOCALORIG", codLocalFinal);
                itemAtual.setProperty("ATUALESTOQUE", atualEstoqueFinal);
                itemAtual.setProperty("RESERVA", reservaFinal);
                itemAtual.setProperty("PERCDESC", percDescFinal);
                itemAtual.setProperty("VLRDESC", vlrDescFinal);
                itemAtual.setProperty("VLRTOT", vlrTotFinal);
                itemAtual.setProperty("CODEMP", codEmpFinal);
                log("Alterando item com validacao CAC - NUNOTA=" + nunota + ", SEQUENCIA=" + sequencia + ", CODEMP=" + codEmpFinal + ", QTD(atual->novo)=" + qtdAtual + "->" + qtdFinal + ", VLRUNIT(atual->novo)=" + vlrUnitAtual + "->" + vlrUnitFinal + ", CODLOCALORIG(atual->novo)=" + codLocalAtual + "->" + codLocalFinal + ", VLRTOT=" + vlrTotFinal + ", PERCDESC=" + percDescFinal + ", VLRDESC=" + vlrDescFinal + ", RESERVA=" + reservaFinal + ", ATUALESTOQUE=" + atualEstoqueFinal);
                return salvarItemComValidacao(dwfFacade, itemAtual, authInfo, false);
            }
        } else {
            throw new IllegalArgumentException("NUNOTA e SEQUENCIA sao obrigatorias para alterar item com validacao CAC.");
        }
    }

    public static DynamicVO insereLigacaoEntreNotas(EntityFacade dwfFacade, BigDecimal nunotaOrig, BigDecimal sequenciaOrig, BigDecimal nunotaDest, BigDecimal sequenciaDest, BigDecimal qtdAtendida) throws Exception {
        DynamicVO varVO = (DynamicVO)dwfFacade.getDefaultValueObjectInstance("CompraVendavariosPedido");
        varVO.setProperty("NUNOTA", nunotaDest);
        varVO.setProperty("SEQUENCIA", sequenciaDest);
        varVO.setProperty("NUNOTAORIG", nunotaOrig);
        varVO.setProperty("SEQUENCIAORIG", sequenciaOrig);
        varVO.setProperty("QTDATENDIDA", qtdAtendida);
        PersistentLocalEntity createEntity = dwfFacade.createEntity("CompraVendavariosPedido", (EntityVO)varVO);
        return (DynamicVO)createEntity.getValueObject();
    }

    public static void alteraItemNotaViaJapeWrapper(BigDecimal nunota, BigDecimal sequencia, BigDecimal qtd, BigDecimal vlrUnit, BigDecimal codLocal, BigDecimal codEmp) throws Exception {
        if (nunota != null && sequencia != null) {
            DynamicVO itemAtual = JapeFactory.dao("ItemNota").findByPK(new Object[]{nunota, sequencia});
            if (itemAtual == null) {
                throw new IllegalStateException("Item nao encontrado para update via JapeWrapper. NUNOTA=" + nunota + ", SEQUENCIA=" + sequencia);
            } else {
                BigDecimal qtdFinal = qtd != null ? qtd : itemAtual.asBigDecimal("QTDNEG");
                BigDecimal vlrUnitFinal = vlrUnit != null ? vlrUnit : itemAtual.asBigDecimal("VLRUNIT");
                BigDecimal codLocalFinal = codLocal != null ? codLocal : itemAtual.asBigDecimal("CODLOCALORIG");
                BigDecimal codEmpFinal = codEmp != null ? codEmp : itemAtual.asBigDecimal("CODEMP");
                BigDecimal vlrTotFinal = qtdFinal != null && vlrUnitFinal != null ? qtdFinal.multiply(vlrUnitFinal) : itemAtual.asBigDecimal("VLRTOT");
                log("Atualizando item via JapeWrapper - NUNOTA=" + nunota + ", SEQUENCIA=" + sequencia + ", QTDNEG=" + qtdFinal + ", VLRUNIT=" + vlrUnitFinal + ", VLRTOT=" + vlrTotFinal + ", CODLOCALORIG=" + codLocalFinal + ", CODEMP=" + codEmpFinal);
                ((FluidUpdateVO)((FluidUpdateVO)((FluidUpdateVO)((FluidUpdateVO)((FluidUpdateVO)JapeFactory.dao("ItemNota").prepareToUpdateByPK(new Object[]{nunota, sequencia}).set("QTDNEG", qtdFinal)).set("VLRUNIT", vlrUnitFinal)).set("VLRTOT", vlrTotFinal)).set("CODLOCALORIG", codLocalFinal)).set("CODEMP", codEmpFinal)).update();
            }
        } else {
            throw new IllegalArgumentException("NUNOTA e SEQUENCIA sao obrigatorias para atualizar item via JapeWrapper.");
        }
    }
}
