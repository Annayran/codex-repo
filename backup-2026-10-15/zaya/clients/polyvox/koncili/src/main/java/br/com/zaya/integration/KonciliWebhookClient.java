package br.com.zaya.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.zaya.models.ConciliacaoResponse;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class KonciliWebhookClient {
    private static final String WEBHOOK_URL_PADRAO = "https://hub.zaya.it/webhook/koncili_polyvox";
    private static final String WEBHOOK_RESOLVIDO_URL_PADRAO = "https://hub.zaya.it/webhook/koncili_polyvox_resolvido_erp";
    private static final String DEFAULT_WEBHOOK_TOKEN_GET = "05a5fe7e4de129be1f387637b704ea7bed76e0acbac5b0a4c85db57e81ce249f";
    private static final String DEFAULT_WEBHOOK_TOKEN_POST = "4f8a2b6c1d9e3f0a7b8c5d2e9f1a4c6b3d2e1f0ab9c7d8e6f5a4b3c2d1e0f9a0";
    private static final String USER_AGENT_PADRAO = "okhttp/4.11.0";

    public List<ConciliacaoResponse> buscarConciliacoes(String cnpj, String dataIni, String dataFim) throws Exception {
        return buscarConciliacoes(obterWebhookUrl(), cnpj, dataIni, dataFim);
    }

    public List<ConciliacaoResponse> buscarConciliacoes(String webhookBaseUrl, String cnpj, String dataIni, String dataFim) throws Exception {
        String query = "cnpj_empresa=" + urlEncode(cnpj)
                + "&data_conciliacao_inicial=" + urlEncode(dataIni)
                + "&data_conciliacao_final=" + urlEncode(dataFim);

        String urlFinal = webhookBaseUrl + (webhookBaseUrl.contains("?") ? "&" : "?") + query;
        String authBasic = "Basic " + Base64.getEncoder().encodeToString(("token:" + obterTokenGet()).getBytes(StandardCharsets.UTF_8));

        HttpsURLConnection conn = null;
        try {
            conn = (HttpsURLConnection) new URL(urlFinal).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(60000);
            conn.setUseCaches(false);
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("User-Agent", obterUserAgent());
            conn.setRequestProperty("Authorization", authBasic);

            int status = conn.getResponseCode();
            String body = readAll(status >= 200 && status < 300 ? conn.getInputStream() : conn.getErrorStream());

            if (status < 200 || status >= 300) {
                throw new IOException("Erro ao chamar webhook: HTTP " + status + " body=" + truncar(body, 500));
            }

            return parseResposta(body);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public void notificarConciliacaoResolvida(String cnpjEmpresa, Integer idTransacao) throws Exception {
        HttpsURLConnection conn = null;
        try {
            conn = (HttpsURLConnection) new URL(obterWebhookResolvidoUrl()).openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(60000);
            conn.setDoOutput(true);

            String auth = "token:" + obterTokenPost();
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
            conn.setRequestProperty("Authorization", "Basic " + encodedAuth);

            String jsonBody = String.format("{\"cnpj_empresa\":\"%s\",\"id_transacao\":%d}", cnpjEmpresa, idTransacao);
            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonBody.getBytes(StandardCharsets.UTF_8));
            }

            int status = conn.getResponseCode();
            if (status < 200 || status >= 300) {
                String body = readAll(conn.getErrorStream());
                throw new IOException("Erro ao notificar webhook resolvido: HTTP " + status + " body=" + truncar(body, 500));
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private static List<ConciliacaoResponse> parseResposta(String body) throws Exception {
        if (body == null || body.trim().isEmpty()) {
            return new ArrayList<ConciliacaoResponse>();
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(body);

        if (root.isArray()) {
            ConciliacaoResponse[] itens = mapper.treeToValue(root, ConciliacaoResponse[].class);
            return arrayToList(itens);
        }

        if (root.isObject() && root.has("data") && root.get("data").isArray()) {
            ConciliacaoResponse[] itens = mapper.treeToValue(root.get("data"), ConciliacaoResponse[].class);
            return arrayToList(itens);
        }

        return new ArrayList<ConciliacaoResponse>();
    }

    private static List<ConciliacaoResponse> arrayToList(ConciliacaoResponse[] itens) {
        List<ConciliacaoResponse> lista = new ArrayList<ConciliacaoResponse>();
        if (itens != null) {
            for (ConciliacaoResponse item : itens) {
                lista.add(item);
            }
        }
        return lista;
    }

    private static String readAll(InputStream input) throws IOException {
        if (input == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    private static String truncar(String texto, int limite) {
        if (texto == null) {
            return "";
        }
        return texto.length() <= limite ? texto : texto.substring(0, limite) + "...";
    }

    private static String urlEncode(String valor) throws Exception {
        return URLEncoder.encode(valor, "UTF-8");
    }

    private static String obterWebhookUrl() {
        String prop = System.getProperty("koncili.webhook.url");
        if (prop != null && !prop.trim().isEmpty()) {
            return prop.trim();
        }
        return WEBHOOK_URL_PADRAO;
    }

    private static String obterWebhookResolvidoUrl() {
        String prop = System.getProperty("koncili.webhook.resolvido.url");
        if (prop != null && !prop.trim().isEmpty()) {
            return prop.trim();
        }
        return WEBHOOK_RESOLVIDO_URL_PADRAO;
    }

    private static String obterTokenGet() {
        String env = System.getenv("KONCILI_WEBHOOK_TOKEN_GET");
        if (env != null && !env.trim().isEmpty()) {
            return env.trim();
        }
        String prop = System.getProperty("koncili.webhook.token.get");
        if (prop != null && !prop.trim().isEmpty()) {
            return prop.trim();
        }
        return DEFAULT_WEBHOOK_TOKEN_GET;
    }

    private static String obterTokenPost() {
        String env = System.getenv("KONCILI_WEBHOOK_TOKEN_POST");
        if (env != null && !env.trim().isEmpty()) {
            return env.trim();
        }
        String prop = System.getProperty("koncili.webhook.token.post");
        if (prop != null && !prop.trim().isEmpty()) {
            return prop.trim();
        }
        return DEFAULT_WEBHOOK_TOKEN_POST;
    }

    private static String obterUserAgent() {
        String prop = System.getProperty("koncili.webhook.userAgent");
        if (prop != null && !prop.trim().isEmpty()) {
            return prop.trim();
        }
        return USER_AGENT_PADRAO;
    }
}

