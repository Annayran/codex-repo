package br.com.crmbonus.clientes.integration.client;

import br.com.crmbonus.clientes.config.CRMConfig;
import br.com.crmbonus.clientes.dto.request.PinResendRequestDTO;
import br.com.crmbonus.clientes.dto.request.PinSendRequestDTO;
import br.com.crmbonus.clientes.dto.request.PinValidateRequestDTO;
import br.com.crmbonus.clientes.dto.response.PinSendResponseDTO;
import br.com.crmbonus.clientes.dto.response.UserVerifyResponse;
import br.com.crmbonus.common.config.CrmHttpConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.UUID;

public class CrmHttpClient {

    private static final ObjectMapper mapper = new ObjectMapper();
    private final CrmHttpConfig crmHttpConfig = new CrmHttpConfig();

    private Exception buildHubError(int status, String responseBody, BigDecimal codEmp) {
        if (status == 500 && responseBody != null && responseBody.contains("Error in workflow")) {
            return new Exception("Credenciais CRM não preenchidas para a empresa " + codEmp + ". Preencha AD_CRM_USERNAME e AD_CRM_PASSWORD na TSIEMP.");
        }
        return new Exception("Erro CRM (" + status + "): " + responseBody);
    }

    /**
     * Passo 1: user-verify
     */
    public UserVerifyResponse userVerify(String cpf) throws Exception {
        return userVerify(cpf, null);
    }

    public UserVerifyResponse userVerify(String cpf, BigDecimal codEmp) throws Exception {

        String requestId = UUID.randomUUID().toString();
        long startTime = System.currentTimeMillis();

        String entidade = "inicializacao";
        String subEntidade = "user-verify";

        crmHttpConfig.validateCompanyCredentials(codEmp, requestId);

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

            // APPLY AUTH HEADERS
            crmHttpConfig.applyAuthHeaders(conn, codEmp, requestId);
            // keep sub-entidade as the original value
            conn.setRequestProperty("sub-entidade", subEntidade);

            conn.setDoOutput(true);

            // BODY JSON
            String json = mapper.writeValueAsString(
                    Collections.singletonMap("cpf", cpf)
            );

            // Não imprimir Basic Auth em logs (evita exfiltração de token)
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
                throw buildHubError(status, responseBody, codEmp);
            }

            return mapper.readValue(responseBody, UserVerifyResponse.class);

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
        return pinSend(request, requestHash, null);
    }

    public PinSendResponseDTO pinSend(PinSendRequestDTO request, String requestHash, BigDecimal codEmp) throws Exception {
        return makeRequest("inicializacao", "pin-send", request, requestHash, PinSendResponseDTO.class, codEmp);
    }

    /**
     * Passo 3: pin-validate
     */
    public String pinValidate(PinValidateRequestDTO request, String requestHash) throws Exception {
        return pinValidate(request, requestHash, null);
    }

    public String pinValidate(PinValidateRequestDTO request, String requestHash, BigDecimal codEmp) throws Exception {
        // Use a raw request to return the response body as string because the API may return an object
        return makeRequestRaw("inicializacao", "pin-validate", request, requestHash, codEmp);
    }

    /**
     * Passo 4: pin-resend
     */
    public PinSendResponseDTO pinResend(PinResendRequestDTO request, String requestHash) throws Exception {
        return pinResend(request, requestHash, null);
    }

    public PinSendResponseDTO pinResend(PinResendRequestDTO request, String requestHash, BigDecimal codEmp) throws Exception {
        return makeRequest("inicializacao", "pin-resend", request, requestHash, PinSendResponseDTO.class, codEmp);
    }

    /**
     * Passo: bonus-available (consulta de bônus)
     */
    public String bonusAvailable(Object request, String requestHash) throws Exception {
        return bonusAvailable(request, requestHash, null);
    }

    public String bonusAvailable(Object request, String requestHash, BigDecimal codEmp) throws Exception {
        return makeRequestRaw("bonus", "bonus-available", request, requestHash, codEmp);
    }

    /**
     * Passo: bonus-submit (aplica/vincula campanha)
     */
    public String bonusSubmit(Object request, String requestHash) throws Exception {
        return bonusSubmit(request, requestHash, null);
    }

    public String bonusSubmit(Object request, String requestHash, BigDecimal codEmp) throws Exception {
        return makeRequestRaw("bonus", "bonus-submit", request, requestHash, codEmp);
    }

    /**
     * Método que retorna o corpo da resposta bruto (String)
     */
    private String makeRequestRaw(String entidade, String subEntidade, Object requestBody, String requestHash) throws Exception {
        return makeRequestRaw(entidade, subEntidade, requestBody, requestHash, null);
    }

    private String makeRequestRaw(String entidade, String subEntidade, Object requestBody, String requestHash, BigDecimal codEmp) throws Exception {
        String requestId = UUID.randomUUID().toString();
        long startTime = System.currentTimeMillis();

        crmHttpConfig.validateCompanyCredentials(codEmp, requestId);

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
            if (requestHash != null && !requestHash.trim().isEmpty()) {
                conn.setRequestProperty("Request-Hash", requestHash);
                System.out.println("[CRM-BONUS][" + requestId + "] Request-Hash header set: " + requestHash);
            }

            // Authorization via CRMConfig + AD_CRM_* headers da empresa
            crmHttpConfig.applyAuthHeaders(conn, codEmp, requestId);

            conn.setDoOutput(true);

            String json = mapper.writeValueAsString(requestBody);
            System.out.println("[CRM-BONUS][" + requestId + "] JSON ENVIADO:");
            System.out.println(json);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            int status = conn.getResponseCode();
            String message = conn.getResponseMessage();

            long duration = System.currentTimeMillis() - startTime;

            System.out.println("[CRM-BONUS][" + requestId + "] STATUS HTTP: " + status);
            System.out.println("[CRM-BONUS][" + requestId + "] RESPONSE MESSAGE: " + message);
            System.out.println("[CRM-BONUS][" + requestId + "] TEMPO (ms): " + duration);

            // HEADERS RESPONSE
            System.out.println("[CRM-BONUS][" + requestId + "] HEADERS RESPOSTA:");
            conn.getHeaderFields().forEach((key, value) -> {
                System.out.println("   " + key + " = " + value);
            });

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
                throw buildHubError(status, responseBody, codEmp);
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
        return makeRequest(entidade, subEntidade, requestBody, requestHash, responseType, null);
    }

    private <T> T makeRequest(String entidade, String subEntidade, Object requestBody, String requestHash, Class<T> responseType, BigDecimal codEmp) throws Exception {

        String requestId = UUID.randomUUID().toString();
        long startTime = System.currentTimeMillis();

        crmHttpConfig.validateCompanyCredentials(codEmp, requestId);

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
            // only set Request-Hash header when provided
            if (requestHash != null && !requestHash.trim().isEmpty()) {
                conn.setRequestProperty("Request-Hash", requestHash);
                System.out.println("[CRM-BONUS][" + requestId + "] Request-Hash header set: " + requestHash);
            }

            // Authorization via CRMConfig + AD_CRM_* headers da empresa
            crmHttpConfig.applyAuthHeaders(conn, codEmp, requestId);

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
                throw buildHubError(status, responseBody, codEmp);
            }

            return mapper.readValue(responseBody, responseType);

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
