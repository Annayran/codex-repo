package br.com.zaya.mahospitalar.genesis.client;

import br.com.zaya.mahospitalar.genesis.config.GenesisSecretProvider;
import br.com.zaya.mahospitalar.genesis.config.ParametrosGenesis;
import br.com.zaya.mahospitalar.genesis.domain.TipoEntidade;
import br.com.zaya.mahospitalar.genesis.util.GenesisDiagnosticLog;
import br.com.zaya.mahospitalar.genesis.util.GenesisSanitizer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public final class GenesisHttpClient implements GenesisApiClient {
    private final ParametrosGenesis parametros;
    private final GenesisSecretProvider secretProvider;
    private final ObjectMapper mapper;
    private final GenesisDiagnosticLog log;

    public GenesisHttpClient(ParametrosGenesis parametros, GenesisSecretProvider secretProvider) {
        this(parametros, secretProvider, new ObjectMapper(), GenesisDiagnosticLog.NONE);
    }

    GenesisHttpClient(ParametrosGenesis parametros, GenesisSecretProvider secretProvider, ObjectMapper mapper) {
        this(parametros, secretProvider, mapper, GenesisDiagnosticLog.NONE);
    }

    public GenesisHttpClient(ParametrosGenesis parametros, GenesisSecretProvider secretProvider,
                             GenesisDiagnosticLog log) {
        this(parametros, secretProvider, new ObjectMapper(), log);
    }

    GenesisHttpClient(ParametrosGenesis parametros, GenesisSecretProvider secretProvider, ObjectMapper mapper,
                      GenesisDiagnosticLog log) {
        this.parametros = parametros;
        this.secretProvider = secretProvider;
        this.mapper = mapper;
        this.log = log == null ? GenesisDiagnosticLog.NONE : log;
    }

    @Override
    public GenesisApiResult buscarPorCodigoExterno(TipoEntidade tipo, String codigoExterno) throws Exception {
        String query = "?codigoExterno=" + URLEncoder.encode(codigoExterno, "UTF-8");
        return executar("GET", tipo.getEndpoint() + query, null);
    }

    @Override
    public GenesisApiResult buscarPorId(TipoEntidade tipo, String idGenesis) throws Exception {
        return executar("GET", tipo.getEndpoint() + URLEncoder.encode(idGenesis, "UTF-8"), null);
    }

    @Override
    public GenesisApiResult buscarPorDocumento(TipoEntidade tipo, String documento) throws Exception {
        if (tipo != TipoEntidade.CLIENTE && tipo != TipoEntidade.FABRICANTE) {
            throw new IllegalArgumentException("Consulta por documento permitida somente para cliente/fabricante.");
        }
        String digitos = documento == null ? "" : documento.replaceAll("\\D", "");
        String campo = digitos.length() <= 11 && tipo == TipoEntidade.CLIENTE ? "cpf" : "cnpj";
        String query = "?" + campo + "=" + URLEncoder.encode(digitos, "UTF-8");
        return executar("GET", tipo.getEndpoint() + query, null);
    }

    @Override
    public GenesisApiResult criar(TipoEntidade tipo, Object payload) throws Exception {
        return executar("POST", tipo.getEndpoint(), mapper.writeValueAsBytes(payload));
    }

    @Override
    public GenesisApiResult atualizar(TipoEntidade tipo, String idGenesis, Object payload) throws Exception {
        return executar("PUT", tipo.getEndpoint() + URLEncoder.encode(idGenesis, "UTF-8"), mapper.writeValueAsBytes(payload));
    }

    public GenesisApiResult inativarItem(String idGenesis) throws Exception {
        if (idGenesis == null || !idGenesis.matches("[1-9][0-9]*")) {
            throw new IllegalArgumentException("ID Genesis do item deve ser numerico e positivo.");
        }
        return executar("DELETE", TipoEntidade.ITEM_MATERIAL.getEndpoint() + idGenesis, null);
    }

    @Override
    public GenesisApiResult inativarEstoque(String idGenesis) throws Exception {
        if (idGenesis == null || !idGenesis.matches("[1-9][0-9]*")) {
            throw new IllegalArgumentException("ID Genesis de estoque deve ser numerico e positivo.");
        }
        return executar("DELETE", TipoEntidade.ESTOQUE.getEndpoint() + idGenesis, null);
    }

    private GenesisApiResult executar(String method, String path, byte[] payload) throws Exception {
        HttpURLConnection connection = null;
        long inicio = System.currentTimeMillis();
        br.com.zaya.mahospitalar.genesis.repository.GenesisRequestRepository historico =
                new br.com.zaya.mahospitalar.genesis.repository.GenesisRequestRepository();
        String rastreio = log instanceof br.com.zaya.mahospitalar.genesis.util.GenesisExecutionLog
                ? historico.iniciar((br.com.zaya.mahospitalar.genesis.util.GenesisExecutionLog)log,
                    method,path,payload==null?null:new String(payload,StandardCharsets.UTF_8)) : null;
        try {
            log.info("HTTP request: metodo=" + method + ", endpoint=" + GenesisSanitizer.sanitizeForLog(path) +
                    ", contentType=application/json; charset=UTF-8, autenticacao=token[MASCARADO], bytes=" +
                    (payload == null ? 0 : payload.length) + ".");
            logCorpo("HTTP request body", payload == null ? null : new String(payload, StandardCharsets.UTF_8));
            connection = (HttpURLConnection) new URL(normalizarBaseUrl() + removerBarraInicial(path)).openConnection();
            connection.setRequestMethod(method);
            connection.setConnectTimeout(parametros.getConnectTimeoutMs());
            connection.setReadTimeout(parametros.getReadTimeoutMs());
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            String token = secretProvider.obterToken();
            connection.setRequestProperty("token", token);
            if (payload != null) {
                connection.setDoOutput(true);
                try (OutputStream output = connection.getOutputStream()) { output.write(payload); }
            }
            int status = connection.getResponseCode();
            String body = readBody(status >= 400 ? connection.getErrorStream() : connection.getInputStream());
            GenesisApiResult.Classificacao classificacao = classificar(status);
            if ("GET".equals(method) && classificacao == GenesisApiResult.Classificacao.SUCESSO) {
                classificacao = classificarConteudoGet(body);
            }
            String idGenesis = extrairId(body);
            log.info("HTTP response: metodo=" + method + ", endpoint=" + GenesisSanitizer.sanitizeForLog(path) +
                    ", status=" + status + ", classificacao=" + classificacao +
                    ", idGenesis=" + valor(idGenesis) + ", duracaoMs=" +
                    (System.currentTimeMillis() - inicio) + ".");
            logCorpo("HTTP response body", body);
            if (status == 429) {
                body = "COOLDOWN_MS=" + GenesisCooldown.prazoMs(connection.getHeaderField("Retry-After"),
                        body, System.currentTimeMillis()) + "; " + body;
            }
            return historico.finalizar(rastreio,new GenesisApiResult(status, classificacao, idGenesis, body,
                    GenesisSanitizer.sanitize(body)),inicio,body);
        } catch (java.net.SocketTimeoutException e) {
            log.info("HTTP timeout: metodo=" + method + ", endpoint=" + GenesisSanitizer.sanitizeForLog(path) +
                    ", duracaoMs=" + (System.currentTimeMillis() - inicio) + ".");
            return historico.finalizar(rastreio,new GenesisApiResult(408, GenesisApiResult.Classificacao.TRANSITORIO, null, "Timeout na API Genesis."),inicio,"Timeout na API Genesis.");
        } catch (java.io.IOException e) {
            log.info("HTTP falha de transporte: metodo=" + method + ", endpoint=" +
                    GenesisSanitizer.sanitizeForLog(path) + ", duracaoMs=" +
                    (System.currentTimeMillis() - inicio) + ", motivo=" +
                    GenesisSanitizer.sanitizeForLog(e.getMessage()) + ".");
            return historico.finalizar(rastreio,new GenesisApiResult(503, GenesisApiResult.Classificacao.TRANSITORIO, null, GenesisSanitizer.sanitize(e.getMessage())),inicio,GenesisSanitizer.sanitize(e.getMessage()));
        } finally {
            if (connection != null) connection.disconnect();
        }
    }

    private String normalizarBaseUrl() {
        String url = parametros.getBaseUrl().trim();
        return url.endsWith("/") ? url : url + "/";
    }

    private String removerBarraInicial(String path) { return path.startsWith("/") ? path.substring(1) : path; }

    private void logCorpo(String titulo, String body) {
        String seguro = GenesisSanitizer.sanitizeForLog(body);
        if (seguro == null || seguro.isEmpty()) {
            log.info(titulo + ": <vazio>.");
            return;
        }
        int tamanhoParte = 3000;
        int total = (seguro.length() + tamanhoParte - 1) / tamanhoParte;
        for (int inicio = 0, parte = 1; inicio < seguro.length(); inicio += tamanhoParte, parte++) {
            int fim = Math.min(seguro.length(), inicio + tamanhoParte);
            log.info(titulo + " [parte " + parte + "/" + total + "]: " + seguro.substring(inicio, fim));
        }
    }

    private static String valor(String value) {
        return value == null || value.trim().isEmpty() ? "<ausente>" : value;
    }

    private String extrairId(String body) {
        if (body == null || body.trim().isEmpty()) return null;
        try {
            JsonNode root = mapper.readTree(body);
            JsonNode value = root.get("id");
            if (value == null && root.has("data")) {
                JsonNode data = root.get("data");
                value = data.isArray() && data.size() == 1 ? data.get(0).get("id") : data.get("id");
            }
            if (value == null && root.has("content") && root.get("content").isArray()
                    && root.get("content").size() == 1) value = root.get("content").get(0).get("id");
            if (value == null && root.isArray() && root.size() == 1) value = root.get(0).get("id");
            return value == null || value.isNull() ? null : value.asText();
        } catch (Exception ignored) { return null; }
    }

    private GenesisApiResult.Classificacao classificarConteudoGet(String body) {
        try {
            JsonNode root = mapper.readTree(body);
            JsonNode data = root;
            if (root != null && root.has("data")) data = root.get("data");
            else if (root != null && root.has("content")) data = root.get("content");
            if (data != null && data.isArray()) {
                if (data.size() == 0) return GenesisApiResult.Classificacao.NAO_ENCONTRADO;
                if (data.size() > 1) return GenesisApiResult.Classificacao.CONFLITO;
            }
            return GenesisApiResult.Classificacao.SUCESSO;
        } catch (Exception e) {
            return GenesisApiResult.Classificacao.ERRO_FUNCIONAL;
        }
    }

    static GenesisApiResult.Classificacao classificar(int status) {
        if (status >= 200 && status < 300) return GenesisApiResult.Classificacao.SUCESSO;
        if (status == 404) return GenesisApiResult.Classificacao.NAO_ENCONTRADO;
        if (status == 400 || status == 422) return GenesisApiResult.Classificacao.ERRO_FUNCIONAL;
        if (status == 401 || status == 403) return GenesisApiResult.Classificacao.AUTENTICACAO;
        if (status == 409) return GenesisApiResult.Classificacao.CONFLITO;
        if (status == 408 || status == 429 || status >= 500) return GenesisApiResult.Classificacao.TRANSITORIO;
        return GenesisApiResult.Classificacao.ERRO_FUNCIONAL;
    }

    private static String readBody(InputStream input) throws Exception {
        if (input == null) return "";
        try (InputStream stream = input; ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[2048];
            int read;
            while ((read = stream.read(buffer)) >= 0 && output.size() < 65536) output.write(buffer, 0, read);
            return new String(output.toByteArray(), StandardCharsets.UTF_8);
        }
    }
}
