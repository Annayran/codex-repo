package br.zaya.telecontrol.services;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.bmp.PersistentLocalEntity;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.util.FinderWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.zaya.telecontrol.models.ListaBasicaGet;
import br.zaya.telecontrol.parsers.ParseListaBasica;
import br.zaya.telecontrol.shared.config.ConfigTelecontrol;
import br.zaya.telecontrol.shared.utils.LogUtil;
import com.google.gson.Gson;
import com.sankhya.util.TimeUtils;
import java.math.BigDecimal;
import java.util.Collection;

public class EnvioListaBasica {
    public static void postListaBasica() throws Exception {
        postListaBasica("(this.DHALTER > this.DHINTEG OR this.DHINTEG IS NULL)");
    }

    public static void postListaBasica(String where) throws Exception {
        LogUtil.SYNC = "ListaBasica";
        JdbcWrapper jdbc = null;
        try {
            EntityFacade dwfEntityFacade = EntityFacadeFactory.getDWFFacade();
            jdbc = dwfEntityFacade.getJdbcWrapper();
            jdbc.openSession();
            FinderWrapper finder = new FinderWrapper("AD_TCLLTB", where, new Object[0]);
            finder.setMaxResults(Api.maxResult);
            finder.setOrderBy("this.DHALTER");
            Collection<PersistentLocalEntity> registros = dwfEntityFacade.findByDynamicFinder(finder);
            ConfigTelecontrol serv = new ConfigTelecontrol();

            for (PersistentLocalEntity itemPLE : registros) {
                try {
                    DynamicVO listaVO = (DynamicVO) itemPLE.getValueObject();
                    LogUtil.REGISTRO = listaVO.asBigDecimal("CODPROD").toString();
                    String url = String.valueOf(serv.url) + serv.epListaBasica;
                    LogUtil.URL = url;
                    String result;

                    if (listaVO.asBigDecimal("LISTABASICA") == null) {
                        String content = ParseListaBasica.toJson(listaVO);
                        LogUtil.REQUEST = content;
                        result = Api.post(url, content);
                    } else {
                        url = String.valueOf(url) + "/referenciaProduto/" + listaVO.asString("REFPRODUTO")
                                + "/referenciaPeca/" + listaVO.asString("REFPECA");
                        String content = ParseListaBasica.toJsonPut(listaVO);
                        LogUtil.REQUEST = content;
                        result = Api.put(url, content);
                    }
                    ListaBasicaGet listaResp = new Gson().fromJson(result, ListaBasicaGet.class);
                    listaVO.setProperty("LISTABASICA", toBigDecimal(listaResp == null ? null : listaResp.lista_basica));
                    listaVO.setProperty("DHINTEG", TimeUtils.getNow());
                    itemPLE.setValueObject((EntityVO) listaVO);
                } catch (Exception e) {
                    LogUtil.saveLog(e);
                    e.printStackTrace();
                }
            }
        } finally {
            if (jdbc != null) {
                jdbc.closeSession();
            }
        }
    }

    public static void deleteListaBasica(String where) throws Exception {
        LogUtil.SYNC = "DeleteListaBasica";
        JdbcWrapper jdbc = null;
        try {
            EntityFacade dwfEntityFacade = EntityFacadeFactory.getDWFFacade();
            jdbc = dwfEntityFacade.getJdbcWrapper();
            jdbc.openSession();
            FinderWrapper finder = new FinderWrapper("AD_TCLLTB", where, new Object[0]);
            finder.setMaxResults(Api.maxResult);
            finder.setOrderBy("this.DHALTER");
            Collection<PersistentLocalEntity> registros = dwfEntityFacade.findByDynamicFinder(finder);
            ConfigTelecontrol serv = new ConfigTelecontrol();

            for (PersistentLocalEntity itemPLE : registros) {
                try {
                    DynamicVO listaVO = (DynamicVO) itemPLE.getValueObject();
                    LogUtil.REGISTRO = listaVO.asBigDecimal("CODPROD").toString();
                    String url = String.valueOf(serv.url) + serv.epListaBasica
                            + "/referenciaProduto/" + listaVO.asString("REFPRODUTO")
                            + "/referenciaPeca/" + listaVO.asString("REFPECA");
                    Api.delete(url);
                } catch (Exception e) {
                    LogUtil.saveLog(e);
                    e.printStackTrace();
                }
            }
        } finally {
            if (jdbc != null) {
                jdbc.closeSession();
            }
        }
    }

    private static BigDecimal toBigDecimal(String value) {
        try {
            return value == null || value.trim().isEmpty() ? null : new BigDecimal(value.trim());
        } catch (Exception e) {
            return null;
        }
    }
}

