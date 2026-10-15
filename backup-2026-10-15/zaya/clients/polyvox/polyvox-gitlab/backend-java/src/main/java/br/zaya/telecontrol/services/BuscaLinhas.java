package br.zaya.telecontrol.services;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidCreateVO;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.zaya.telecontrol.models.LinhaGet;
import br.zaya.telecontrol.parsers.ParseLinha;
import br.zaya.telecontrol.shared.config.ConfigTelecontrol;
import br.zaya.telecontrol.shared.utils.LogUtil;
import com.sankhya.util.TimeUtils;
import java.math.BigDecimal;
import java.util.List;

public class BuscaLinhas {
    public static void getLinhas() throws Exception {
        LogUtil.SYNC = "Linha";
        ConfigTelecontrol serv = new ConfigTelecontrol();
        String url = String.valueOf(serv.url) + serv.epLinha;
        LogUtil.URL = url;
        LogUtil.METHOD = "GET";

        String result = Api.get(url);
        List<LinhaGet> linhas = ParseLinha.getLinha(result);
        JapeWrapper linhaDAO = JapeFactory.dao("AD_TCLLIN");

        for (LinhaGet linha : linhas) {
            try {
                String codigo = firstNonBlank(linha.codigo_linha, linha.linha);
                if (codigo == null) {
                    continue;
                }
                LogUtil.REGISTRO = codigo;
                DynamicVO linhaVO = linhaDAO.findByPK(codigo);
                if (linhaVO == null) {
                    FluidCreateVO creVO = linhaDAO.create();
                    preencherLinha(creVO, codigo, linha);
                    creVO.save();
                } else {
                    FluidUpdateVO updVO = linhaDAO.prepareToUpdate(linhaVO);
                    preencherLinha(updVO, codigo, linha);
                    updVO.update();
                }
            } catch (Exception e) {
                LogUtil.saveLog(e);
                e.printStackTrace();
            }
        }
    }

    private static void preencherLinha(Object vo, String codigo, LinhaGet linha) throws Exception {
        setField(vo, "CODLINHA", codigo);
        setField(vo, "NOME", linha.nome);
        setField(vo, "ATIVO", linha.ativo ? "S" : "N");
        setField(vo, "LINHA", toBigDecimal(linha.linha));
        setField(vo, "FABRICA", linha.fabrica == null ? null : toBigDecimal(linha.fabrica.fabrica));
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

