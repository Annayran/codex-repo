package br.zaya.telecontrol.services;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.bmp.PersistentLocalEntity;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.util.FinderWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.zaya.telecontrol.models.PecaGet;
import br.zaya.telecontrol.parsers.ParsePeca;
import br.zaya.telecontrol.utils.ConfigTelecontrol;
import br.zaya.telecontrol.utils.LogUtil;
import com.google.gson.Gson;
import com.sankhya.util.TimeUtils;
import java.math.BigDecimal;
import java.util.Collection;

public class EnvioPeca {
    public static void postPeca() throws Exception {
        postPeca("(this.DTALTER > this.DHINTEG OR this.DHINTEG IS NULL)");
    }

    public static void postPeca(String where) throws Exception {
        LogUtil.SYNC = "Peca";
        JdbcWrapper jdbc = null;
        try {
            EntityFacade dwfEntityFacade = EntityFacadeFactory.getDWFFacade();
            jdbc = dwfEntityFacade.getJdbcWrapper();
            jdbc.openSession();
            FinderWrapper finder = new FinderWrapper("AD_TCLPCA", where, new Object[0]);
            finder.setMaxResults(Api.maxResult);
            finder.setOrderBy("this.CODPROD");
            Collection<PersistentLocalEntity> registros = dwfEntityFacade.findByDynamicFinder(finder);
            ConfigTelecontrol serv = new ConfigTelecontrol();

            for (PersistentLocalEntity itemPLE : registros) {
                try {
                    DynamicVO pecaVO = (DynamicVO) itemPLE.getValueObject();
                    LogUtil.REGISTRO = pecaVO.asBigDecimal("CODPROD").toString();
                    String url = String.valueOf(serv.url) + serv.epPecas;
                    LogUtil.URL = url;
                    String result;

                    if (pecaVO.asBigDecimal("COD_TELECONTROL") == null) {
                        String content = ParsePeca.toJson(pecaVO);
                        LogUtil.REQUEST = content;
                        result = Api.post(url, content);
                    } else {
                        url = String.valueOf(url) + "/referencia/" + pecaVO.asString("REFERENCIA");
                        String content = ParsePeca.toJsonPut(pecaVO);
                        LogUtil.REQUEST = content;
                        result = Api.put(url, content);
                    }
                    PecaGet pecaResp = new Gson().fromJson(result, PecaGet.class);
                    pecaVO.setProperty("COD_TELECONTROL", toBigDecimal(pecaResp == null ? null : pecaResp.peca));
                    pecaVO.setProperty("DHINTEG", TimeUtils.getNow());
                    itemPLE.setValueObject((EntityVO) pecaVO);
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
