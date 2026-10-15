package br.com.zaya.mahospitalar.genesis.client;

import br.com.zaya.mahospitalar.genesis.config.ParametrosGenesis;
import br.com.zaya.mahospitalar.genesis.dto.EstoqueGenesisDTO;
import br.com.zaya.mahospitalar.genesis.support.JsonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GenesisHttpClient {
    private static final String ESTOQUES_PATH = "/api/v1/estoques/";
    private static final String ESTOQUES_PAGE_PATH = "/api/v1/estoques?page=0";
    private final ParametrosGenesis parametros;

    public GenesisHttpClient(ParametrosGenesis parametros) {
        this.parametros = parametros;
    }

    public GenesisResponse validarAutenticacao() {
        HttpURLConnection connection = null;
        try {
            connection = abrirConexao("GET", ESTOQUES_PAGE_PATH);
            int http = connection.getResponseCode();
            if (http == 200) {
                return GenesisResponse.sucesso(http, lerCorpo(connection.getInputStream()));
            }
            String erro = lerCorpo(connection.getErrorStream());
            if (http == 408 || http >= 500) {
                return GenesisResponse.transitorio("Falha transitoria Genesis HTTP " + http + ": " + erro);
            }
            return GenesisResponse.erroFuncional(http, "Falha Genesis HTTP " + http + ": " + erro);
        } catch (SocketTimeoutException e) {
            return GenesisResponse.transitorio("TIMEOUT");
        } catch (IOException e) {
            return GenesisResponse.transitorio(e.getMessage());
        } finally {
            if (connection != null) connection.disconnect();
        }
    }

    public GenesisResponse enviarEstoque(EstoqueGenesisDTO dto) {
        HttpURLConnection connection = null;
        try {
            connection = abrirConexao("POST", ESTOQUES_PATH);
            escreverCorpo(connection, JsonUtil.toJson(dto));
            int http = connection.getResponseCode();
            if (http == 201) {
                return GenesisResponse.sucesso(http, lerCorpo(connection.getInputStream()));
            }
            String erro = lerCorpo(connection.getErrorStream());
            if (http == 408 || http >= 500) {
                return GenesisResponse.transitorio("Falha transitoria Genesis HTTP " + http + ": " + erro);
            }
            return GenesisResponse.erroFuncional(http, "Falha Genesis HTTP " + http + ": " + erro);
        } catch (SocketTimeoutException e) {
            return GenesisResponse.transitorio("TIMEOUT");
        } catch (IOException e) {
            return GenesisResponse.transitorio(e.getMessage());
        } finally {
            if (connection != null) connection.disconnect();
        }
    }

    private HttpURLConnection abrirConexao(String method, String path) throws IOException {
        URL url = new URL(normalizarBaseUrl(parametros.getBaseUrl()) + path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("token", parametros.getToken());
        connection.setConnectTimeout(parametros.getConnectTimeoutMillis());
        connection.setReadTimeout(parametros.getReadTimeoutMillis());
        connection.setDoOutput("POST".equals(method) || "PUT".equals(method));
        return connection;
    }

    private void escreverCorpo(HttpURLConnection connection, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        connection.setFixedLengthStreamingMode(bytes.length);
        OutputStream out = connection.getOutputStream();
        try {
            out.write(bytes);
        } finally {
            out.close();
        }
    }

    private String lerCorpo(InputStream stream) throws IOException {
        if (stream == null) return "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) content.append(line);
        return content.toString();
    }

    private String normalizarBaseUrl(String baseUrl) {
        String normalized = trimRight(baseUrl, '/');
        if (normalized.endsWith("/swagger-ui.html")) {
            normalized = normalized.substring(0, normalized.length() - "/swagger-ui.html".length());
        }
        return trimRight(normalized, '/');
    }

    private String trimRight(String value, char remove) {
        if (value == null) return "";
        int end = value.length();
        while (end > 0 && value.charAt(end - 1) == remove) end--;
        return value.substring(0, end);
    }
}