package br.zaya.telecontrol.services;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidCreateVO;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.zaya.telecontrol.models.FamiliaGet;
import br.zaya.telecontrol.parsers.ParseFamilia;
import br.zaya.telecontrol.utils.ConfigTelecontrol;
import br.zaya.telecontrol.utils.LogUtil;
import com.sankhya.util.TimeUtils;
import java.math.BigDecimal;
import java.util.List;

public class BuscaFamilias {
    public static void getFamilias() throws Exception {
        LogUtil.SYNC = "Familia";
        ConfigTelecontrol serv = new ConfigTelecontrol();
        String url = String.valueOf(serv.url) + serv.epFamilia;
        LogUtil.URL = url;
        LogUtil.METHOD = "GET";

        String result = Api.get(url);
        List<FamiliaGet> familias = ParseFamilia.getFamilia(result);
        JapeWrapper familiaDAO = JapeFactory.dao("AD_TCLFAM");

        for (FamiliaGet familia : familias) {
            try {
                String codigo = firstNonBlank(familia.codigo_familia, familia.familia);
                if (codigo == null) {
                    continue;
                }
                LogUtil.REGISTRO = codigo;
                DynamicVO familiaVO = familiaDAO.findByPK(codigo);
                if (familiaVO == null) {
                    FluidCreateVO creVO = familiaDAO.create();
                    preencherFamilia(creVO, codigo, familia);
                    creVO.save();
                } else {
                    FluidUpdateVO updVO = familiaDAO.prepareToUpdate(familiaVO);
                    preencherFamilia(updVO, codigo, familia);
                    updVO.update();
                }
            } catch (Exception e) {
                LogUtil.saveLog(e);
                e.printStackTrace();
            }
        }
    }

    private static void preencherFamilia(Object vo, String codigo, FamiliaGet familia) throws Exception {
        setField(vo, "CODFAMILIA", codigo);
        setField(vo, "DESCRICAO", familia.descricao);
        setField(vo, "FAMILIA", toBigDecimal(familia.familia));
        setField(vo, "DHINTEG", TimeUtils.getNow());
    }

    private static void setField(Object vo, String field, Object value) throws Exception {
        if (vo instanceof FluidCreateVO) {
            ((FluidCreateVO) vo).set(field, value);
        } else if (vo instanceof FluidUpdateVO) {
            ((FluidUpdateVO) vo).set(field, value);
        }
    }

    private static BigDecimal toBigDecimal(String value) {
        try {
            return value == null || value.trim().isEmpty() ? null : new BigDecimal(value.trim());
        } catch (Exception e) {
            return null;
        }
    }

    private static String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.trim().isEmpty()) {
                return value.trim();
            }
        }
        return null;
    }
}
