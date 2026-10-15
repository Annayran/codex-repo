package br.com.crmbonus.bonus.service.impl;

import br.com.crmbonus.bonus.service.BonusService;
import br.com.crmbonus.bonus.dto.request.BonusAvailableRequestDTO;
import br.com.crmbonus.bonus.dto.request.BonusCampaignsRequestDTO;
import br.com.crmbonus.bonus.dto.request.BonusSubmitRequestDTO;
import br.com.crmbonus.bonus.dto.response.BonusAvailableResponseDTO;
import br.com.crmbonus.bonus.dto.response.BonusCampaignsResponseDTO;
import br.com.crmbonus.bonus.dto.response.BonusSubmitResponseDTO;
import br.com.crmbonus.clientes.config.CRMConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

public class BonusServiceImpl implements BonusService {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public BonusAvailableResponseDTO checkAvailable(BonusAvailableRequestDTO request, String requestHash) throws Exception {
        return makeRequest("bonus", "bonus-available", request, requestHash, BonusAvailableResponseDTO.class);
    }

    @Override
    public BonusCampaignsResponseDTO getCampaigns(BonusCampaignsRequestDTO request, String requestHash) throws Exception {
        return makeRequest("bonus", "bonus-campaigns", request, requestHash, BonusCampaignsResponseDTO.class);
    }

    @Override
    public BonusSubmitResponseDTO submitCampaign(BonusSubmitRequestDTO request, String requestHash) throws Exception {
        return makeRequest("bonus", "bonus-submit", request, requestHash, BonusSubmitResponseDTO.class);
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
            conn.setRequestProperty("Request-Hash", requestHash);

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

