package br.zaya.telecontrol.services;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.zaya.telecontrol.utils.ConfigTelecontrol;
import br.zaya.telecontrol.utils.LogUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sankhya.util.TimeUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BuscaCadastrosTelecontrolService {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String LOG_PREFIX = "[GET-CADASTROS] ";

    public static Resultado atualizarProdutos() throws Exception {
        LogUtil.SYNC = "GetProdutos";
        ConfigTelecontrol config = new ConfigTelecontrol();
        String url = montarUrl(config.url, config.epProdutos);
        LogUtil.URL = url;

        List<JsonNode> produtos = buscarItens(url, "produtos", "produto");
        Resultado resultado = new Resultado("produtos", produtos.size());
        JapeWrapper produtoDAO = JapeFactory.dao("AD_TCLPRO");

        for (JsonNode produto : produtos) {
            BigDecimal codTelecontrol = asBigDecimal(produto.get("produto"));
            String referencia = asText(produto.get("referencia"));
            LogUtil.REGISTRO = firstNonBlank(referencia, String.valueOf(codTelecontrol));

            try {
                DynamicVO produtoVO = localizarRegistro(produtoDAO, codTelecontrol, referencia);
                if (produtoVO == null) {
                    resultado.naoEncontrados++;
                    System.out.println(LOG_PREFIX + "Produto nao encontrado na AD_TCLPRO, ignorando para evitar duplicidade | COD_TELECONTROL="
                            + codTelecontrol + " | REFERENCIA=" + referencia);
                    continue;
                }

                FluidUpdateVO updVO = produtoDAO.prepareToUpdate(produtoVO);
                setNumero(updVO, "COD_TELECONTROL", codTelecontrol);
                setTexto(updVO, "REFERENCIA", referencia);
                setTexto(updVO, "DESCRICAO", asText(produto.get("descricao")));
                setTexto(updVO, "GARANTIA", asText(produto.get("garantia")));
                setTexto(updVO, "VOLTAGEM", asText(produto.get("voltagem")));
                setTexto(updVO, "ATIVO", toSimNao(produto.get("ativo")));
                setTexto(updVO, "CODFAMILIA", asText(produto.path("familia").get("codigo_familia")));
                setTexto(updVO, "CODLINHA", asText(produto.path("linha").get("codigo_linha")));
                setTexto(updVO, "MAODEOBRA", asText(produto.get("mao_de_obra")));
                setTexto(updVO, "MAODEOBRAADMIN", asText(produto.get("mao_de_obra_admin")));
                setTexto(updVO, "NUMEROSERIEOBRIGATORIO", toSimNao(produto.get("numero_serie_obrigatorio")));
                updVO.set("DHINTEG", TimeUtils.getNow());
                updVO.update();

                resultado.atualizados++;
                System.out.println(LOG_PREFIX + "Produto atualizado | CODPROD=" + produtoVO.asBigDecimal("CODPROD")
                        + " | COD_TELECONTROL=" + codTelecontrol + " | REFERENCIA=" + referencia);
            } catch (Exception e) {
                resultado.comErro++;
                System.out.println(LOG_PREFIX + "Erro ao atualizar produto | COD_TELECONTROL=" + codTelecontrol
                        + " | REFERENCIA=" + referencia + " | erro=" + e.getMessage());
                LogUtil.saveLog(e);
            }
        }

        System.out.println(LOG_PREFIX + "Fim atualizarProdutos | " + resultado);
        return resultado;
    }

    public static Resultado atualizarPecas() throws Exception {
        LogUtil.SYNC = "GetPecas";
        ConfigTelecontrol config = new ConfigTelecontrol();
        String url = montarUrl(config.url, config.epPecas);
        LogUtil.URL = url;

        List<JsonNode> pecas = buscarItens(url, "pecas", "peca");
        Resultado resultado = new Resultado("pecas", pecas.size());
        JapeWrapper pecaDAO = JapeFactory.dao("AD_TCLPCA");

        for (JsonNode peca : pecas) {
            BigDecimal codTelecontrol = asBigDecimal(peca.get("peca"));
            String referencia = asText(peca.get("referencia"));
            LogUtil.REGISTRO = firstNonBlank(referencia, String.valueOf(codTelecontrol));

            try {
                DynamicVO pecaVO = localizarRegistro(pecaDAO, codTelecontrol, referencia);
                if (pecaVO == null) {
                    resultado.naoEncontrados++;
                    System.out.println(LOG_PREFIX + "Peca nao encontrada na AD_TCLPCA, ignorando para evitar duplicidade | COD_TELECONTROL="
                            + codTelecontrol + " | REFERENCIA=" + referencia);
                    continue;
                }

                FluidUpdateVO updVO = pecaDAO.prepareToUpdate(pecaVO);
                setNumero(updVO, "COD_TELECONTROL", codTelecontrol);
                setTexto(updVO, "REFERENCIA", referencia);
                setTexto(updVO, "DESCRICAO", asText(peca.get("descricao")));
                setNumero(updVO, "MULTIPLO", asBigDecimal(peca.get("multiplo")));
                setTexto(updVO, "DEVOLUCAOOBRIGATORIA", toSimNao(peca.get("devolucao_obrigatoria")));
                setTexto(updVO, "GARANTIADIFERENCIADA", asText(peca.get("garantia_diferenciada")));
                setTexto(updVO, "NUMEROSERIEPECA", toSimNao(peca.get("numero_serie_peca")));
                setTexto(updVO, "ACESSORIO", toSimNao(peca.get("acessorio")));
                setTexto(updVO, "ITEMAPARENCIA", toSimNao(peca.get("item_aparencia")));
                setTexto(updVO, "ATIVO", toSimNao(peca.get("ativo")));
                setTexto(updVO, "BLOQUEADAVENDA", toSimNao(peca.get("bloqueada_venda")));
                setTexto(updVO, "BLOQUEADAGARANTIA", toSimNao(peca.get("bloqueada_garantia")));
                setNumero(updVO, "ESTOQUE", asBigDecimal(peca.get("estoque")));
                updVO.set("DHINTEG", TimeUtils.getNow());
                updVO.update();

                resultado.atualizados++;
                System.out.println(LOG_PREFIX + "Peca atualizada | CODPROD=" + pecaVO.asBigDecimal("CODPROD")
                        + " | COD_TELECONTROL=" + codTelecontrol + " | REFERENCIA=" + referencia);
            } catch (Exception e) {
                resultado.comErro++;
                System.out.println(LOG_PREFIX + "Erro ao atualizar peca | COD_TELECONTROL=" + codTelecontrol
                        + " | REFERENCIA=" + referencia + " | erro=" + e.getMessage());
                LogUtil.saveLog(e);
            }
        }

        System.out.println(LOG_PREFIX + "Fim atualizarPecas | " + resultado);
        return resultado;
    }

    private static DynamicVO localizarRegistro(JapeWrapper dao, BigDecimal codTelecontrol, String referencia) throws Exception {
        DynamicVO vo = null;

        if (codTelecontrol != null && codTelecontrol.compareTo(BigDecimal.ZERO) > 0) {
            Collection<DynamicVO> encontrados = dao.find("this.COD_TELECONTROL = ?", codTelecontrol);
            vo = unico(encontrados);
            if (vo != null) {
                return vo;
            }
        }

        if (!isBlank(referencia)) {
            Collection<DynamicVO> encontrados = dao.find("this.REFERENCIA = ?", referencia.trim());
            return unico(encontrados);
        }

        return null;
    }

    private static DynamicVO unico(Collection<DynamicVO> registros) {
        if (registros == null || registros.isEmpty()) {
            return null;
        }
        if (registros.size() > 1) {
            System.out.println(LOG_PREFIX + "Mais de um registro encontrado para a mesma chave, ignorando para evitar duplicidade.");
            return null;
        }
        return registros.iterator().next();
    }

    private static List<JsonNode> buscarItens(String url, String arrayName, String singleObjectName) throws Exception {
        List<JsonNode> todos = new ArrayList<JsonNode>();
        int paginaEsperada = 0;
        String proximaUrl = url;

        while (true) {
            LogUtil.URL = proximaUrl;
            String resposta = Api.get(proximaUrl);
            JsonNode root = MAPPER.readTree(resposta);
            List<JsonNode> itensPagina = extrairItens(root, arrayName, singleObjectName);
            todos.addAll(itensPagina);

            if (!root.isObject() || !root.has("pagina") || itensPagina.isEmpty()) {
                break;
            }

            int paginaRetornada = root.path("pagina").asInt(paginaEsperada);
            int quantidadePagina = root.path("quantidade").asInt(itensPagina.size());
            if (paginaRetornada != paginaEsperada || itensPagina.size() < quantidadePagina) {
                break;
            }

            paginaEsperada++;
            proximaUrl = adicionarParametroPagina(url, paginaEsperada);
        }

        return todos;
    }

    private static List<JsonNode> extrairItens(JsonNode root, String arrayName, String singleObjectName) throws Exception {
        List<JsonNode> itens = new ArrayList<JsonNode>();

        if (root == null || root.isNull()) {
            return itens;
        }

        if (root.isArray()) {
            for (JsonNode item : root) {
                itens.add(item);
            }
            return itens;
        }

        JsonNode arrayNode = root.get(arrayName);
        if (arrayNode == null || !arrayNode.isArray()) {
            arrayNode = root.get("data");
        }
        if (arrayNode == null || !arrayNode.isArray()) {
            arrayNode = root.get("response");
        }

        if (arrayNode != null && arrayNode.isArray()) {
            for (JsonNode item : arrayNode) {
                itens.add(item);
            }
            return itens;
        }

        if (root.has(singleObjectName)) {
            itens.add(root);
        }

        return itens;
    }

    private static String adicionarParametroPagina(String url, int pagina) {
        String separador = url.contains("?") ? "&" : "?";
        return url + separador + "pagina=" + pagina;
    }

    private static String montarUrl(String baseUrl, String endpoint) {
        if (baseUrl == null) {
            baseUrl = "";
        }
        if (endpoint == null) {
            endpoint = "";
        }
        if (baseUrl.endsWith("/") || endpoint.startsWith("/")) {
            return baseUrl + endpoint;
        }
        return baseUrl + "/" + endpoint;
    }

    private static void setTexto(FluidUpdateVO updVO, String field, String value) throws Exception {
        if (!isBlank(value)) {
            updVO.set(field, value.trim());
        }
    }

    private static void setNumero(FluidUpdateVO updVO, String field, BigDecimal value) throws Exception {
        if (value != null) {
            updVO.set(field, value);
        }
    }

    private static BigDecimal asBigDecimal(JsonNode node) {
        if (node == null || node.isNull()) {
            return null;
        }
        try {
            if (node.isNumber()) {
                return node.decimalValue();
            }
            String text = node.asText();
            if (isBlank(text)) {
                return null;
            }
            return new BigDecimal(text.trim().replace(',', '.'));
        } catch (Exception e) {
            return null;
        }
    }

    private static String asText(JsonNode node) {
        if (node == null || node.isNull()) {
            return null;
        }
        String value = node.asText();
        return isBlank(value) ? null : value;
    }

    private static String toSimNao(JsonNode node) {
        if (node == null || node.isNull()) {
            return null;
        }
        if (node.isBoolean()) {
            return node.asBoolean() ? "S" : "N";
        }
        if (node.isNumber()) {
            return node.asInt() == 1 ? "S" : "N";
        }
        String value = node.asText();
        if (isBlank(value)) {
            return null;
        }
        value = value.trim();
        return ("true".equalsIgnoreCase(value) || "1".equals(value) || "S".equalsIgnoreCase(value)) ? "S" : "N";
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private static String firstNonBlank(String... values) {
        if (values == null) {
            return "";
        }
        for (String value : values) {
            if (!isBlank(value)) {
                return value;
            }
        }
        return "";
    }

    public static class Resultado {
        public String entidade;
        public int recebidos;
        public int atualizados;
        public int naoEncontrados;
        public int comErro;

        public Resultado(String entidade, int recebidos) {
            this.entidade = entidade;
            this.recebidos = recebidos;
        }

        @Override
        public String toString() {
            return "Resultado{" +
                    "entidade='" + entidade + '\'' +
                    ", recebidos=" + recebidos +
                    ", atualizados=" + atualizados +
                    ", naoEncontrados=" + naoEncontrados +
                    ", comErro=" + comErro +
                    '}';
        }
    }
}
