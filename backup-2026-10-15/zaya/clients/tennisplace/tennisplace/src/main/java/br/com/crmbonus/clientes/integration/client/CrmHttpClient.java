package br.com.crmbonus.clientes.integration.client;

import br.com.crmbonus.clientes.config.CRMConfig;
import br.com.crmbonus.clientes.dto.request.PinSendRequestDTO;
import br.com.crmbonus.clientes.dto.request.PinValidateRequestDTO;
import br.com.crmbonus.clientes.dto.request.PinResendRequestDTO;
import br.com.crmbonus.clientes.dto.response.UserVerifyResponse;
import br.com.crmbonus.clientes.dto.response.PinSendResponseDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.UUID;

public class CrmHttpClient {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Passo 1: user-verify
     */
    public UserVerifyResponse userVerify(String cpf) throws Exception {

        String requestId = UUID.randomUUID().toString();
        long startTime = System.currentTimeMillis();

        String entidade = "inicializacao";
        String subEntidade = "user-verify";

        System.out.println("\n[CRM-BONUS][" + requestId + "] =====================================");
        System.out.println("[CRM-BONUS][" + requestId + "] CPF: " + cpf);

        HttpURLConnection conn = null;

        try {

            URL url = new URL(CRMConfig.BASE_URL);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);

            // HEADERS
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("User-Agent", "Sankhya-CRM-Integration");
            conn.setRequestProperty("entidade", entidade);
            conn.setRequestProperty("sub-entidade", subEntidade);

            // 🔐 BASIC AUTH
            String username = "token";
            String password = CRMConfig.TOKEN.trim();

            String authString = username + ":" + password;

            String basicAuth = Base64.getEncoder()
                    .encodeToString(authString.getBytes(StandardCharsets.UTF_8));

            conn.setRequestProperty("Authorization", "Basic " + basicAuth);

            conn.setDoOutput(true);

            // BODY JSON
            String json = mapper.writeValueAsString(
                    Collections.singletonMap("cpf", cpf)
            );

            // Não imprimir Basic Auth em logs (evita exfiltraç��o de token)
            System.out.println("[CRM-BONUS][" + requestId + "] JSON ENVIADO:");
            System.out.println(json);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            int status = conn.getResponseCode();
            long duration = System.currentTimeMillis() - startTime;

            System.out.println("[CRM-BONUS][" + requestId + "] STATUS HTTP: " + status);
            System.out.println("[CRM-BONUS][" + requestId + "] TEMPO (ms): " + duration);

            InputStream is = status >= 400
                    ? conn.getErrorStream()
                    : conn.getInputStream();

            StringBuilder response = new StringBuilder();

            if (is != null) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                }
            }

            String responseBody = response.toString();

            System.out.println("[CRM-BONUS][" + requestId + "] RESPONSE:");
            System.out.println(responseBody);
            System.out.println("[CRM-BONUS][" + requestId + "] =====================================\n");

            if (status < 200 || status >= 300) {
                throw new Exception("Erro CRM (" + status + "): " + responseBody);
            }

            // Robust parsing: API may return
            // - a single object { status,message,data:{...} }
            // - an array with one element [ { status,message,data:{...} } ]
            // - or top-level fields (user_id/request_pin)
            JsonNode root = mapper.readTree(responseBody);
            JsonNode node = root;
            if (root.isArray() && root.size() > 0) {
                node = root.get(0);
            }

            // Extract candidate payload (prefer `data` object if present)
            JsonNode dataNode = node.has("data") ? node.get("data") : node;

            String userId = null;
            boolean requestPin = false;
            boolean resourceId = false;

            if (dataNode != null && !dataNode.isMissingNode()) {
                if (dataNode.has("user_id")) userId = dataNode.get("user_id").asText(null);
                if (dataNode.has("request_pin")) requestPin = dataNode.get("request_pin").asBoolean(false);
                if (dataNode.has("resource_id")) resourceId = dataNode.get("resource_id").asBoolean(false);
            }

            // fallback to top-level fields if not present inside data
            if (userId == null || userId.isEmpty()) {
                if (node.has("user_id")) userId = node.get("user_id").asText(null);
            }
            if (!requestPin && node.has("request_pin")) {
                requestPin = node.get("request_pin").asBoolean(false);
            }
            if (!resourceId && node.has("resource_id")) {
                resourceId = node.get("resource_id").asBoolean(false);
            }

            UserVerifyResponse resp = new UserVerifyResponse();
            resp.setResource_id(resourceId);
            resp.setUser_id(userId);
            resp.setRequest_pin(requestPin);

            return resp;

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * Passo 2: pin-send
     */
    public PinSendResponseDTO pinSend(PinSendRequestDTO request, String requestHash) throws Exception {
        return makeRequest("inicializacao", "pin-send", request, requestHash, PinSendResponseDTO.class);
    }

    /**
     * Passo 3: pin-validate
     */
    public String pinValidate(PinValidateRequestDTO request, String requestHash) throws Exception {
        // Use a raw request to return the response body as string because the API may return an object
        return makeRequestRaw("inicializacao", "pin-validate", request, requestHash);
    }

    /**
     * Passo 4: pin-resend
     */
    public PinSendResponseDTO pinResend(PinResendRequestDTO request, String requestHash) throws Exception {
        return makeRequest("inicializacao", "pin-resend", request, requestHash, PinSendResponseDTO.class);
    }

    /**
     * Passo: bonus-available (consulta de bônus)
     */
    public String bonusAvailable(Object request, String requestHash) throws Exception {
        return makeRequestRaw("bonus", "bonus-available", request, requestHash);
    }

    /**
     * Passo: bonus-submit (aplica/vincula campanha)
     */
    public String bonusSubmit(Object request, String requestHash) throws Exception {
        return makeRequestRaw("bonus", "bonus-submit", request, requestHash);
    }

    /**
     * Método que retorna o corpo da resposta bruto (String)
     */
    private String makeRequestRaw(String entidade, String subEntidade, Object requestBody, String requestHash) throws Exception {
        String requestId = UUID.randomUUID().toString();
        long startTime = System.currentTimeMillis();

        System.out.println("\n[CRM-BONUS][" + requestId + "] =====================================");
        System.out.println("[CRM-BONUS][" + requestId + "] Entidade: " + entidade + " | SubEntidade: " + subEntidade);

        HttpURLConnection conn = null;

        try {
            URL url = new URL(CRMConfig.BASE_URL);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);

            // HEADERS
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("User-Agent", "Sankhya-CRM-Integration");
            conn.setRequestProperty("entidade", entidade);
            conn.setRequestProperty("sub-entidade", subEntidade);
            if (requestHash != null && !requestHash.trim().isEmpty()) {
                // keep legacy Request-Hash header and also send AD_REQHASH as requested
                conn.setRequestProperty("Request-Hash", requestHash);
                conn.setRequestProperty("AD_REQHASH", requestHash);
                System.out.println("[CRM-BONUS][" + requestId + "] Request-Hash header set: " + requestHash);
                System.out.println("[CRM-BONUS][" + requestId + "] AD_REQHASH header set: " + requestHash);
            }

            String username = "token";
            String password = CRMConfig.TOKEN.trim();
            String authString = username + ":" + password;
            String basicAuth = Base64.getEncoder().encodeToString(authString.getBytes(StandardCharsets.UTF_8));
            conn.setRequestProperty("Authorization", "Basic " + basicAuth);

            conn.setDoOutput(true);

            String json = mapper.writeValueAsString(requestBody);
            System.out.println("[CRM-BONUS][" + requestId + "] JSON ENVIADO:");
            System.out.println(json);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            int status = conn.getResponseCode();
            long duration = System.currentTimeMillis() - startTime;

            System.out.println("[CRM-BONUS][" + requestId + "] STATUS HTTP: " + status);
            System.out.println("[CRM-BONUS][" + requestId + "] TEMPO (ms): " + duration);

            InputStream is = status >= 400 ? conn.getErrorStream() : conn.getInputStream();

            StringBuilder response = new StringBuilder();
            if (is != null) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                }
            }

            String responseBody = response.toString();
            System.out.println("[CRM-BONUS][" + requestId + "] RESPONSE:");
            System.out.println(responseBody);
            System.out.println("[CRM-BONUS][" + requestId + "] =====================================\n");

            if (status < 200 || status >= 300) {
                throw new Exception("Erro CRM (" + status + "): " + responseBody);
            }

            return responseBody;

        } finally {
            if (conn != null) conn.disconnect();
        }
    }

    /**
     * Método genérico para fazer requisições ao CRM
     */
    private <T> T makeRequest(String entidade, String subEntidade, Object requestBody, String requestHash, Class<T> responseType) throws Exception {

        String requestId = UUID.randomUUID().toString();
        long startTime = System.currentTimeMillis();

        System.out.println("\n[CRM-BONUS][" + requestId + "] =====================================");
        System.out.println("[CRM-BONUS][" + requestId + "] Entidade: " + entidade + " | SubEntidade: " + subEntidade);

        HttpURLConnection conn = null;

        try {

            URL url = new URL(CRMConfig.BASE_URL);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);

            // HEADERS
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("User-Agent", "Sankhya-CRM-Integration");
            conn.setRequestProperty("entidade", entidade);
            conn.setRequestProperty("sub-entidade", subEntidade);
            // only set Request-Hash/AD_REQHASH header when provided
            if (requestHash != null && !requestHash.trim().isEmpty()) {
                conn.setRequestProperty("Request-Hash", requestHash);
                conn.setRequestProperty("AD_REQHASH", requestHash);
                System.out.println("[CRM-BONUS][" + requestId + "] Request-Hash header set: " + requestHash);
                System.out.println("[CRM-BONUS][" + requestId + "] AD_REQHASH header set: " + requestHash);
            }

            // BASIC AUTH
            String username = "token";
            String password = CRMConfig.TOKEN.trim();

            String authString = username + ":" + password;

            String basicAuth = Base64.getEncoder()
                    .encodeToString(authString.getBytes(StandardCharsets.UTF_8));

            conn.setRequestProperty("Authorization", "Basic " + basicAuth);

            conn.setDoOutput(true);

            // BODY JSON
            String json = mapper.writeValueAsString(requestBody);

            System.out.println("[CRM-BONUS][" + requestId + "] JSON ENVIADO:");
            System.out.println(json);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            int status = conn.getResponseCode();
            long duration = System.currentTimeMillis() - startTime;

            System.out.println("[CRM-BONUS][" + requestId + "] STATUS HTTP: " + status);
            System.out.println("[CRM-BONUS][" + requestId + "] TEMPO (ms): " + duration);

            InputStream is = status >= 400
                    ? conn.getErrorStream()
                    : conn.getInputStream();

            StringBuilder response = new StringBuilder();

            if (is != null) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                }
            }

            String responseBody = response.toString();

            System.out.println("[CRM-BONUS][" + requestId + "] RESPONSE:");
            System.out.println(responseBody);
            System.out.println("[CRM-BONUS][" + requestId + "] =====================================\n");

            if (status < 200 || status >= 300) {
                throw new Exception("Erro CRM (" + status + "): " + responseBody);
            }

            return mapper.readValue(responseBody, responseType);

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}

