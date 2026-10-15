package br.zaya.telecontrol.services;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidCreateVO;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.zaya.telecontrol.shared.config.ConfigTelecontrol;
import br.zaya.telecontrol.shared.infrastructure.http.Api;
import br.zaya.telecontrol.shared.utils.LogUtil;
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
            String referencia = asText(produto.get("referencia"));
            String codigoTelecontrol = asText(produto.get("produto"));
            LogUtil.REGISTRO = firstNonBlank(referencia, codigoTelecontrol);

            try {
                if (isBlank(referencia)) {
                    resultado.naoEncontrados++;
                    System.out.println(LOG_PREFIX + "Produto sem referencia no retorno Telecontrol, ignorando | produto=" + codigoTelecontrol);
                    continue;
                }

                DynamicVO produtoVO = localizarPorReferencia(produtoDAO, referencia);
                if (produtoVO == null) {
                    DynamicVO produtoSistemaVO = localizarProdutoSistema(referencia);
                    if (produtoSistemaVO == null) {
                        resultado.naoEncontrados++;
                        System.out.println(LOG_PREFIX + "Produto nao encontrado na AD_TCLPRO nem na TGFPRO | REFERENCIA="
                                + referencia + " | produtoTelecontrol=" + codigoTelecontrol);
                        continue;
                    }

                    FluidCreateVO creVO = produtoDAO.create();
                    creVO.set("CODPROD", produtoSistemaVO.asBigDecimal("CODPROD"));
                    preencherProduto(creVO, produto);
                    creVO.save();
                    resultado.inseridos++;
                    System.out.println(LOG_PREFIX + "Produto inserido na AD_TCLPRO a partir da TGFPRO | CODPROD="
                            + produtoSistemaVO.asBigDecimal("CODPROD") + " | REFERENCIA=" + referencia);
                } else {
                    FluidUpdateVO updVO = produtoDAO.prepareToUpdate(produtoVO);
                    preencherProduto(updVO, produto);
                    updVO.update();
                    resultado.atualizados++;
                    System.out.println(LOG_PREFIX + "Produto atualizado | CODPROD=" + produtoVO.asBigDecimal("CODPROD")
                            + " | REFERENCIA=" + referencia);
                }
            } catch (Exception e) {
                resultado.comErro++;
                System.out.println(LOG_PREFIX + "Erro ao sincronizar produto | REFERENCIA=" + referencia
                        + " | produtoTelecontrol=" + codigoTelecontrol + " | erro=" + e.getMessage());
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
            String referencia = asText(peca.get("referencia"));
            String codigoTelecontrol = asText(peca.get("peca"));
            LogUtil.REGISTRO = firstNonBlank(referencia, codigoTelecontrol);

            try {
                if (isBlank(referencia)) {
                    resultado.naoEncontrados++;
                    System.out.println(LOG_PREFIX + "Peca sem referencia no retorno Telecontrol, ignorando | peca=" + codigoTelecontrol);
                    continue;
                }

                DynamicVO pecaVO = localizarPorReferencia(pecaDAO, referencia);
                if (pecaVO == null) {
                    DynamicVO produtoSistemaVO = localizarProdutoSistema(referencia);
                    if (produtoSistemaVO == null) {
                        resultado.naoEncontrados++;
                        System.out.println(LOG_PREFIX + "Peca nao encontrada na AD_TCLPCA nem na TGFPRO | REFERENCIA="
                                + referencia + " | pecaTelecontrol=" + codigoTelecontrol);
                        continue;
                    }

                    FluidCreateVO creVO = pecaDAO.create();
                    creVO.set("CODPROD", produtoSistemaVO.asBigDecimal("CODPROD"));
                    preencherPeca(creVO, peca);
                    creVO.save();
                    resultado.inseridos++;
                    System.out.println(LOG_PREFIX + "Peca inserida na AD_TCLPCA a partir da TGFPRO | CODPROD="
                            + produtoSistemaVO.asBigDecimal("CODPROD") + " | REFERENCIA=" + referencia);
                } else {
                    FluidUpdateVO updVO = pecaDAO.prepareToUpdate(pecaVO);
                    preencherPeca(updVO, peca);
                    updVO.update();
                    resultado.atualizados++;
                    System.out.println(LOG_PREFIX + "Peca atualizada | CODPROD=" + pecaVO.asBigDecimal("CODPROD")
                            + " | REFERENCIA=" + referencia);
                }
            } catch (Exception e) {
                resultado.comErro++;
                System.out.println(LOG_PREFIX + "Erro ao sincronizar peca | REFERENCIA=" + referencia
                        + " | pecaTelecontrol=" + codigoTelecontrol + " | erro=" + e.getMessage());
                LogUtil.saveLog(e);
            }
        }

        System.out.println(LOG_PREFIX + "Fim atualizarPecas | " + resultado);
        return resultado;
    }

    private static void preencherProduto(Object vo, JsonNode produto) throws Exception {
        setTexto(vo, "REFERENCIA", asText(produto.get("referencia")));
        setTexto(vo, "DESCRICAO", asText(produto.get("descricao")));
        setNumero(vo, "COD_TELECONTROL", asBigDecimal(produto.get("produto")));
        setNumero(vo, "GARANTIA", asBigDecimal(produto.get("garantia")));
        setTexto(vo, "VOLTAGEM", asText(produto.get("voltagem")));
        setTexto(vo, "ATIVO", toSimNao(produto.get("ativo")));
        setTexto(vo, "CODFAMILIA", asText(produto.path("familia").get("codigo_familia")));
        setTexto(vo, "CODLINHA", asText(produto.path("linha").get("codigo_linha")));
        setNumero(vo, "MAODEOBRA", asBigDecimal(produto.get("mao_de_obra")));
        setNumero(vo, "MAODEOBRAADMIN", asBigDecimal(produto.get("mao_de_obra_admin")));
        setTexto(vo, "NUMEROSERIEOBRIGATORIO", toSimNao(produto.get("numero_serie_obrigatorio")));
        setDataIntegracao(vo);
    }

    private static void preencherPeca(Object vo, JsonNode peca) throws Exception {
        setTexto(vo, "REFERENCIA", asText(peca.get("referencia")));
        setTexto(vo, "DESCRICAO", asText(peca.get("descricao")));
        setNumero(vo, "COD_TELECONTROL", asBigDecimal(peca.get("peca")));
        setNumero(vo, "MULTIPLO", asBigDecimal(peca.get("multiplo")));
        setTexto(vo, "DEVOLUCAOOBRIGATORIA", toSimNao(peca.get("devolucao_obrigatoria")));
        setNumero(vo, "GARANTIADIFERENCIADA", asBigDecimal(peca.get("garantia_diferenciada")));
        setTexto(vo, "NUMEROSERIEPECA", toSimNao(peca.get("numero_serie_peca")));
        setTexto(vo, "ACESSORIO", toSimNao(peca.get("acessorio")));
        setTexto(vo, "ITEMAPARENCIA", toSimNao(peca.get("item_aparencia")));
        setTexto(vo, "ATIVO", toSimNao(peca.get("ativo")));
        setTexto(vo, "BLOQUEADAVENDA", toSimNao(peca.get("bloqueada_venda")));
        setTexto(vo, "BLOQUEADAGARANTIA", toSimNao(peca.get("bloqueada_garantia")));
        setNumero(vo, "ESTOQUE", asBigDecimal(peca.get("estoque")));
        setDataIntegracao(vo);
    }

    private static DynamicVO localizarPorReferencia(JapeWrapper dao, String referencia) throws Exception {
        if (isBlank(referencia)) {
            return null;
        }
        Collection<DynamicVO> encontrados = dao.find("this.REFERENCIA = ?", referencia.trim());
        return unico(encontrados, "REFERENCIA=" + referencia);
    }

    private static DynamicVO localizarProdutoSistema(String referencia) throws Exception {
        if (isBlank(referencia)) {
            return null;
        }
        JapeWrapper produtoDAO = JapeFactory.dao("Produto");
        Collection<DynamicVO> encontrados = produtoDAO.find("this.REFERENCIA = ?", referencia.trim());
        return unico(encontrados, "TGFPRO.REFERENCIA=" + referencia);
    }

    private static DynamicVO unico(Collection<DynamicVO> registros, String chave) {
        if (registros == null || registros.isEmpty()) {
            return null;
        }
        if (registros.size() > 1) {
            System.out.println(LOG_PREFIX + "Mais de um registro encontrado para a mesma chave, ignorando | " + chave);
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
            System.out.println(LOG_PREFIX + "Consultando API | url=" + proximaUrl);
            String resposta = Api.get(proximaUrl);
            JsonNode root = MAPPER.readTree(resposta);
            List<JsonNode> itensPagina = extrairItens(root, arrayName, singleObjectName);
            todos.addAll(itensPagina);
            System.out.println(LOG_PREFIX + "Pagina processada | itensPagina=" + itensPagina.size()
                    + " | totalAcumulado=" + todos.size());

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

    private static List<JsonNode> extrairItens(JsonNode root, String arrayName, String singleObjectName) {
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

    private static void setTexto(Object vo, String field, String value) throws Exception {
        if (!isBlank(value)) {
            setField(vo, field, value.trim());
        }
    }

    private static void setNumero(Object vo, String field, BigDecimal value) throws Exception {
        if (value != null) {
            setField(vo, field, value);
        }
    }

    private static void setDataIntegracao(Object vo) throws Exception {
        setField(vo, "DHINTEG", TimeUtils.getNow());
    }

    private static void setField(Object vo, String field, Object value) throws Exception {
        if (vo instanceof FluidCreateVO) {
            ((FluidCreateVO) vo).set(field, value);
        } else if (vo instanceof FluidUpdateVO) {
            ((FluidUpdateVO) vo).set(field, value);
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
        public int inseridos;
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
                    ", inseridos=" + inseridos +
                    ", naoEncontrados=" + naoEncontrados +
                    ", comErro=" + comErro +
                    '}';
        }
    }
}
