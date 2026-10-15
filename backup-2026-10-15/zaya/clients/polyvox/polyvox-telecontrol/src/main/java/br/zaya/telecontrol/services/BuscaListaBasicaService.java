package br.zaya.telecontrol.services;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidCreateVO;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.zaya.telecontrol.shared.config.ConfigTelecontrol;
import br.zaya.telecontrol.shared.infrastructure.http.Api;
import br.zaya.telecontrol.shared.utils.LogUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sankhya.util.TimeUtils;
import java.math.BigDecimal;

public class BuscaListaBasicaService {
    public static Resultado buscarPorProduto(BigDecimal codprod) throws Exception {
        if (codprod == null) {
            throw new Exception("CODPROD nao informado para busca de lista basica.");
        }

        DynamicVO produtoVO = JapeFactory.dao("AD_TCLPRO").findByPK(codprod);
        if (produtoVO == null) {
            throw new Exception("Produto nao encontrado em AD_TCLPRO | CODPROD=" + codprod);
        }

        String referencia = produtoVO.asString("REFERENCIA");
        if (referencia == null || referencia.trim().isEmpty()) {
            throw new Exception("Produto sem REFERENCIA em AD_TCLPRO | CODPROD=" + codprod);
        }

        return buscarPorReferencia(referencia);
    }

    public static Resultado buscarPorReferencia(String referenciaProduto) throws Exception {
        if (referenciaProduto == null || referenciaProduto.trim().isEmpty()) {
            throw new Exception("REFERENCIA do produto nao informada para busca de lista basica.");
        }

        LogUtil.SYNC = "BuscaListaBasica";
        LogUtil.REGISTRO = referenciaProduto;

        DynamicVO produtoVO = buscarProdutoPorReferencia(referenciaProduto);
        if (produtoVO == null) {
            throw new Exception("Referencia do produto nao encontrada em AD_TCLPRO | REFERENCIA=" + referenciaProduto);
        }

        BigDecimal codprod = produtoVO.asBigDecimal("CODPROD");
        ConfigTelecontrol config = new ConfigTelecontrol();
        String url = String.valueOf(config.url) + config.epListaBasica + "/referenciaProduto/" + referenciaProduto;
        LogUtil.URL = url;

        String response = Api.get(url);
        LogUtil.RESPONSE = response;
        return persistirResposta(codprod, response);
    }

    public static void deletarNaApiPorProduto(BigDecimal codprod) throws Exception {
        if (codprod == null) {
            throw new Exception("CODPROD nao informado para exclusao de lista basica.");
        }

        DynamicVO produtoVO = JapeFactory.dao("AD_TCLPRO").findByPK(codprod);
        if (produtoVO == null) {
            throw new Exception("Produto nao encontrado em AD_TCLPRO | CODPROD=" + codprod);
        }

        deletarNaApiPorReferencia(produtoVO.asString("REFERENCIA"));
    }

    public static void deletarNaApiPorReferencia(String referenciaProduto) throws Exception {
        if (referenciaProduto == null || referenciaProduto.trim().isEmpty()) {
            throw new Exception("REFERENCIA do produto nao informada para exclusao de lista basica.");
        }

        LogUtil.SYNC = "DeleteListaBasicaProduto";
        LogUtil.REGISTRO = referenciaProduto;

        ConfigTelecontrol config = new ConfigTelecontrol();
        String url = String.valueOf(config.url) + config.epListaBasica + "/referenciaProduto/" + referenciaProduto;
        LogUtil.URL = url;
        Api.delete(url);
    }

    private static Resultado persistirResposta(BigDecimal codprod, String response) throws Exception {
        Resultado resultado = new Resultado();
        JsonArray itens = extrairItens(response);
        JapeWrapper pecaDAO = JapeFactory.dao("AD_TCLPCA");
        JapeWrapper listaDAO = JapeFactory.dao("AD_TCLLTB");

        for (int i = 0; i < itens.size(); i++) {
            JsonObject item = asObject(itens.get(i));
            if (item == null) {
                resultado.ignoradas++;
                continue;
            }

            JsonObject peca = primeiroObjeto(item, "peca");
            String referenciaPeca = primeiroTexto(peca, "referencia");
            if (referenciaPeca == null) {
                referenciaPeca = primeiroTexto(item, "referenciaPeca", "referencia_peca", "pecaReferencia", "referencia");
            }
            if (referenciaPeca == null || referenciaPeca.trim().isEmpty()) {
                resultado.pecaNaoEncontrada++;
                continue;
            }

            DynamicVO pecaVO = buscarPecaPorReferencia(pecaDAO, referenciaPeca);
            if (pecaVO == null) {
                resultado.pecaNaoEncontrada++;
                continue;
            }

            BigDecimal codpeca = pecaVO.asBigDecimal("CODPROD");
            BigDecimal quantidade = primeiroBigDecimal(item, "quantidade", "qtd", "QTD");
            String posicao = primeiroTexto(item, "posicao", "POSICAO");
            BigDecimal listaBasica = primeiroBigDecimal(item, "listaBasica", "lista_basica", "LISTABASICA");

            DynamicVO existenteVO = listaDAO.findByPK(codprod, codpeca);
            if (existenteVO == null) {
                FluidCreateVO createVO = listaDAO.create();
                createVO.set("CODPROD", codprod);
                createVO.set("CODPECA", codpeca);
                setIfNotNull(createVO, "QTD", quantidade);
                setIfNotNull(createVO, "POSICAO", posicao);
                createVO.set("DHALTER", TimeUtils.getNow());
                createVO.set("DHINTEG", TimeUtils.getNow());
                setIfNotNull(createVO, "LISTABASICA", listaBasica);
                createVO.save();
                resultado.inseridas++;
            } else {
                FluidUpdateVO updateVO = listaDAO.prepareToUpdateByPK(codprod, codpeca);
                setIfNotNull(updateVO, "QTD", quantidade);
                setIfNotNull(updateVO, "POSICAO", posicao);
                updateVO.set("DHALTER", TimeUtils.getNow());
                updateVO.set("DHINTEG", TimeUtils.getNow());
                setIfNotNull(updateVO, "LISTABASICA", listaBasica);
                updateVO.update();
                resultado.atualizadas++;
            }
        }

        return resultado;
    }

    private static DynamicVO buscarProdutoPorReferencia(String referencia) throws Exception {
        return buscarPorReferencia(JapeFactory.dao("AD_TCLPRO"), referencia);
    }

    private static DynamicVO buscarPecaPorReferencia(JapeWrapper pecaDAO, String referencia) throws Exception {
        return buscarPorReferencia(pecaDAO, referencia);
    }

    private static DynamicVO buscarPorReferencia(JapeWrapper dao, String referencia) throws Exception {
        if (referencia == null) {
            return null;
        }
        return dao.findOne("this.REFERENCIA = ?", new Object[]{referencia});
    }

    private static JsonArray extrairItens(String response) throws Exception {
        JsonElement root = new JsonParser().parse(response == null ? "[]" : response);
        if (root == null || root.isJsonNull()) {
            return new JsonArray();
        }
        if (root.isJsonArray()) {
            return root.getAsJsonArray();
        }
        if (!root.isJsonObject()) {
            return new JsonArray();
        }

        JsonObject obj = root.getAsJsonObject();
        JsonElement data = primeiroElemento(obj, "data", "itens", "lista", "listaBasica", "listasBasicas");
        if (data != null && data.isJsonArray()) {
            return data.getAsJsonArray();
        }

        JsonArray array = new JsonArray();
        array.add(obj);
        return array;
    }

    private static JsonObject asObject(JsonElement element) {
        return element != null && element.isJsonObject() ? element.getAsJsonObject() : null;
    }

    private static JsonElement primeiroElemento(JsonObject obj, String... campos) {
        if (obj == null) {
            return null;
        }
        for (String campo : campos) {
            if (obj.has(campo) && !obj.get(campo).isJsonNull()) {
                return obj.get(campo);
            }
        }
        return null;
    }

    private static String primeiroTexto(JsonObject obj, String... campos) {
        JsonElement element = primeiroElemento(obj, campos);
        return element == null ? null : element.getAsString();
    }

    private static JsonObject primeiroObjeto(JsonObject obj, String... campos) {
        JsonElement element = primeiroElemento(obj, campos);
        return element != null && element.isJsonObject() ? element.getAsJsonObject() : null;
    }

    private static BigDecimal primeiroBigDecimal(JsonObject obj, String... campos) {
        JsonElement element = primeiroElemento(obj, campos);
        if (element == null) {
            return null;
        }
        try {
            return element.getAsBigDecimal();
        } catch (Exception e) {
            try {
                return new BigDecimal(element.getAsString());
            } catch (Exception ignored) {
                return null;
            }
        }
    }

    private static void setIfNotNull(FluidCreateVO vo, String campo, Object valor) throws Exception {
        if (valor != null) {
            vo.set(campo, valor);
        }
    }

    private static void setIfNotNull(FluidUpdateVO vo, String campo, Object valor) throws Exception {
        if (valor != null) {
            vo.set(campo, valor);
        }
    }

    public static class Resultado {
        public int inseridas;
        public int atualizadas;
        public int ignoradas;
        public int pecaNaoEncontrada;

        @Override
        public String toString() {
            return "Resultado{" +
                    "inseridas=" + inseridas +
                    ", atualizadas=" + atualizadas +
                    ", ignoradas=" + ignoradas +
                    ", pecaNaoEncontrada=" + pecaNaoEncontrada +
                    '}';
        }
    }
}
