package br.zaya.telecontrol.services;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.bmp.PersistentLocalEntity;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.util.FinderWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.zaya.telecontrol.models.PostoGet;
import br.zaya.telecontrol.parsers.ParsePosto;
import br.zaya.telecontrol.shared.config.ConfigTelecontrol;
import br.zaya.telecontrol.shared.utils.LogUtil;
import com.google.gson.Gson;
import com.sankhya.util.TimeUtils;
import java.math.BigDecimal;
import java.util.Collection;

public class EnvioPosto {
    public static void postPosto() throws Exception {
        postPosto("(this.DTALTER > this.DHINTEG OR this.DHINTEG IS NULL)");
    }

    public static void postPosto(String where) throws Exception {
        LogUtil.SYNC = "Posto";
        JdbcWrapper jdbc = null;
        try {
            EntityFacade dwfEntityFacade = EntityFacadeFactory.getDWFFacade();
            jdbc = dwfEntityFacade.getJdbcWrapper();
            jdbc.openSession();
            FinderWrapper finder = new FinderWrapper("AD_TCLPST", where, new Object[0]);
            finder.setMaxResults(Api.maxResult);
            finder.setOrderBy("this.DTALTER");
            Collection<PersistentLocalEntity> registros = dwfEntityFacade.findByDynamicFinder(finder);
            ConfigTelecontrol serv = new ConfigTelecontrol();

            for (PersistentLocalEntity itemPLE : registros) {
                try {
                    DynamicVO postoVO = (DynamicVO) itemPLE.getValueObject();
                    DynamicVO parceiroVO = postoVO.asDymamicVO("Parceiro");
                    LogUtil.REGISTRO = postoVO.asBigDecimal("CODPARC").toString();
                    String url = String.valueOf(serv.url) + serv.epPostos;
                    LogUtil.URL = url;
                    String result;

                    if (postoVO.asBigDecimal("POSTOID") == null) {
                        String content = ParsePosto.toJson(postoVO);
                        LogUtil.REQUEST = content;
                        result = Api.post(url, content);
                    } else {
                        url = String.valueOf(url) + "/cnpj/" + parceiroVO.asString("CGC_CPF");
                        String content = ParsePosto.toJsonPut(postoVO);
                        LogUtil.REQUEST = content;
                        result = Api.put(url, content);
                    }
                    PostoGet postoResp = new Gson().fromJson(result, PostoGet.class);
                    postoVO.setProperty("POSTOID", toBigDecimal(postoResp == null ? null : postoResp.posto));
                    postoVO.setProperty("DHINTEG", TimeUtils.getNow());
                    itemPLE.setValueObject((EntityVO) postoVO);
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

