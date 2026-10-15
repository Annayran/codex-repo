package br.zaya.telecontrol.parsers;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ParsePosto {
    public static String toJson(DynamicVO postoVO) throws Exception {
        DynamicVO parceiroVO = postoVO.asDymamicVO("Parceiro");
        JsonObject json = new JsonObject();
        json.addProperty("razao", getString(parceiroVO, "RAZAOSOCIAL"));
        json.addProperty("nomeFantasia", getString(parceiroVO, "NOMEPARC"));
        json.addProperty("cnpj", getString(parceiroVO, "CGC_CPF"));
        json.addProperty("ie", getString(parceiroVO, "IDENTINSCESTAD"));
        json.addProperty("codigoPosto", getBigDecimalString(parceiroVO, "CODPARC"));
        json.addProperty("tipoPosto", getString(parceiroVO, "AD_TIPOPOSTO"));
        preencherEndereco(json, parceiroVO);
        json.addProperty("email", getString(parceiroVO, "EMAIL"));
        json.addProperty("fone", getString(parceiroVO, "TELEFONE"));
        json.addProperty("pedidoFaturado", postoVO.asBoolean("PEDFATURADO"));
        json.addProperty("credenciamento", postoVO.asString("CREDENCIAMENTO"));
        preencherCamposPut(json, postoVO);
        return new Gson().toJson(json);
    }

    public static String toJsonPut(DynamicVO postoVO) throws Exception {
        JsonObject json = new JsonObject();
        preencherCamposPut(json, postoVO);
        return new Gson().toJson(json);
    }

    private static void preencherCamposPut(JsonObject json, DynamicVO postoVO) throws Exception {
        json.addProperty("pedidoGarantia", postoVO.asBoolean("PEDGARANTIA"));
        json.addProperty("valorKm", postoVO.asDouble("VALORKM"));
        json.addProperty("desconto", postoVO.asDouble("DESCONTO"));
    }

    private static void preencherEndereco(JsonObject json, DynamicVO parceiroVO) throws Exception {
        DynamicVO enderecoVO = findByPk("Endereco", parceiroVO, "CODEND");
        DynamicVO bairroVO = findByPk("Bairro", parceiroVO, "CODBAI");
        DynamicVO cidadeVO = findByPk("Cidade", parceiroVO, "CODCID");
        DynamicVO ufVO = cidadeVO == null ? null : JapeFactory.dao("UnidadeFederativa").findByPK(cidadeVO.asBigDecimal("UF"));
        json.addProperty("endereco", getString(enderecoVO, "NOMEEND"));
        json.addProperty("numero", toInt(getString(parceiroVO, "NUMEND")));
        json.addProperty("bairro", getString(bairroVO, "NOMEBAI"));
        json.addProperty("cep", getString(parceiroVO, "CEP"));
        json.addProperty("cidade", getString(cidadeVO, "NOMECID"));
        json.addProperty("estado", getString(ufVO, "UF"));
    }

    private static DynamicVO findByPk(String entidade, DynamicVO vo, String campo) throws Exception {
        if (vo == null || vo.asBigDecimal(campo) == null) {
            return null;
        }
        JapeWrapper dao = JapeFactory.dao(entidade);
        return dao.findByPK(vo.asBigDecimal(campo));
    }

    private static String getString(DynamicVO vo, String campo) {
        return vo == null ? null : vo.asString(campo);
    }

    private static String getBigDecimalString(DynamicVO vo, String campo) {
        return vo == null || vo.asBigDecimal(campo) == null ? null : vo.asBigDecimal(campo).toString();
    }

    private static int toInt(String valor) {
        try {
            return valor == null || valor.trim().isEmpty() ? 0 : Integer.parseInt(valor.trim());
        } catch (Exception e) {
            return 0;
        }
    }
}
