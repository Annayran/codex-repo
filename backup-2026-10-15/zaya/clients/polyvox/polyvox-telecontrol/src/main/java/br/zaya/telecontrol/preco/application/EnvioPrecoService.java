package br.zaya.telecontrol.preco.application;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.bmp.PersistentLocalEntity;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.util.FinderWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.zaya.telecontrol.shared.config.ConfigTelecontrol;
import br.zaya.telecontrol.shared.infrastructure.http.Api;
import br.zaya.telecontrol.shared.utils.LogUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sankhya.util.JdbcUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.Collection;

public class EnvioPrecoService {
    public static void postPrecoProduto(BigDecimal codprod) throws Exception {
        postPreco("AD_TCLPRO", "this.CODPROD = " + codprod);
    }

    public static void postPrecoPeca(BigDecimal codprod) throws Exception {
        postPreco("AD_TCLPCA", "this.CODPROD = " + codprod);
    }

    public static void postPreco(String entityName, String where) throws Exception {
        ConfigTelecontrol config = new ConfigTelecontrol();
        LogUtil.SYNC = "Preco";
        JdbcWrapper jdbc = null;
        try {
            EntityFacade facade = EntityFacadeFactory.getDWFFacade();
            jdbc = facade.getJdbcWrapper();
            jdbc.openSession();

            FinderWrapper finder = new FinderWrapper(entityName, where, new Object[0]);
            finder.setMaxResults(Api.maxResult);
            finder.setOrderBy("this.CODPROD");
            Collection<PersistentLocalEntity> registros = facade.findByDynamicFinder(finder);

            for (PersistentLocalEntity itemPLE : registros) {
                try {
                    DynamicVO itemVO = (DynamicVO) itemPLE.getValueObject();
                    BigDecimal codprod = itemVO.asBigDecimal("CODPROD");
                    LogUtil.REGISTRO = codprod == null ? null : codprod.toString();

                    Double precoVenda = getPrecoTabela(codprod, config.codigoTabelaPreco);
                    if (enviarPreco(config, itemVO, TipoPreco.VEN, precoVenda)) {
                        itemVO.setProperty(TipoPreco.VEN.field, toBigDecimal(precoVenda));
                        itemPLE.setValueObject((EntityVO) itemVO);
                    }

                    Double precoGarantia = getPrecoTabela(codprod, config.codigoTabelaPrecoGarantia);
                    if (enviarPreco(config, itemVO, TipoPreco.GAR, precoGarantia)) {
                        itemVO.setProperty(TipoPreco.GAR.field, toBigDecimal(precoGarantia));
                        itemPLE.setValueObject((EntityVO) itemVO);
                    }
                } catch (Exception e) {
                    LogUtil.saveLog(e);
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            LogUtil.saveLog(e);
            throw e;
        } finally {
            if (jdbc != null) {
                jdbc.closeSession();
            }
        }
    }

    private static boolean enviarPreco(ConfigTelecontrol config, DynamicVO itemVO, TipoPreco tipo, Double preco)
            throws Exception {
        if (preco == null || itemVO.asString("REFERENCIA") == null) {
            return false;
        }

        String url = String.valueOf(config.url) + config.epPrecos;
        LogUtil.URL = url;
        JsonObject json = new JsonObject();
        json.addProperty("preco", preco);

        if (itemVO.asBigDecimal(tipo.field) == null) {
            json.addProperty("siglaTabela", tipo.table);
            json.addProperty("pecaReferencia", itemVO.asString("REFERENCIA"));
            String content = new Gson().toJson(json);
            LogUtil.REQUEST = content;
            Api.post(url, content);
            return true;
        }

        url = url + String.format("/siglaTabela/%s/pecaReferencia/%s", tipo.table, itemVO.asString("REFERENCIA"));
        LogUtil.URL = url;
        String content = new Gson().toJson(json);
        LogUtil.REQUEST = content;
        Api.put(url, content);
        return true;
    }

    private static Double getPrecoTabela(BigDecimal codprod, BigDecimal codtab) throws Exception {
        if (codprod == null || codtab == null) {
            return null;
        }
        NativeSql sql = null;
        ResultSet rs = null;
        JdbcWrapper jdbc = null;
        try {
            jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
            sql = new NativeSql(jdbc);
            sql.appendSql(" SELECT NVL(SNK_PRECO(:CODTAB, :CODPROD), 0) VLRVENDA ");
            sql.appendSql(" FROM DUAL ");
            sql.setNamedParameter("CODTAB", codtab);
            sql.setNamedParameter("CODPROD", codprod);
            rs = sql.executeQuery();
            if (rs.next() && rs.getBigDecimal(1) != null) {
                return rs.getBigDecimal(1).setScale(2, RoundingMode.HALF_UP).doubleValue();
            }
            return null;
        } catch (Exception e) {
            LogUtil.saveLog(e);
            throw e;
        } finally {
            JdbcUtils.closeResultSet(rs);
            NativeSql.releaseResources(sql);
            if (jdbc != null) {
                JdbcWrapper.closeSession(jdbc);
            }
        }
    }

    private static BigDecimal toBigDecimal(Double value) {
        return value == null ? null : BigDecimal.valueOf(value);
    }

    private enum TipoPreco {
        VEN("VEN", "PRECOVEN"),
        GAR("GAR", "PRECOGAR");

        private final String table;
        private final String field;

        TipoPreco(String table, String field) {
            this.table = table;
            this.field = field;
        }
    }
}
