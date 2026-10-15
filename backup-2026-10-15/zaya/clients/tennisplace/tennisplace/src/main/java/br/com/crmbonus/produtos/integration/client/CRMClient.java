package br.com.crmbonus.produtos.integration.client;

import br.com.crmbonus.produtos.config.CRMConfig;
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

public class CRMClient {

    private static final ObjectMapper mapper = new ObjectMapper();

    public String enviar(String entidade,
                         String subEntidade,
                         Object body) throws Exception {

        String requestId = UUID.randomUUID().toString();
        long startTime = System.currentTimeMillis();

        String fullUrl = CRMConfig.BASE_URL;

        System.out.println("\n[CRM-BONUS][" + requestId + "] =====================================");
        System.out.println("[CRM-BONUS][" + requestId + "] URL: " + fullUrl);
        System.out.println("[CRM-BONUS][" + requestId + "] ENTIDADE: " + entidade);
        System.out.println("[CRM-BONUS][" + requestId + "] SUBENTIDADE: " + subEntidade);

        HttpURLConnection conn = null;

        try {

            URL url = new URL(fullUrl);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");


            // 🔥 HEADERS IMPORTANTES
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("User-Agent", "Sankhya-CRM-Integration");

            conn.setRequestProperty("entidade", entidade);
            conn.setRequestProperty("sub-entidade", subEntidade);

            // 🔐 BASIC AUTH CORRETO
            String credentials = CRMConfig.USERNAME + ":" + CRMConfig.TOKEN;
            String encoded = Base64.getEncoder()
                    .encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

            conn.setRequestProperty("Authorization", "Basic " + encoded);

            // 🔎 LOG AUTH MASCARADO
            System.out.println("[CRM-BONUS][" + requestId + "] Authorization: Basic ********");

            conn.setDoOutput(true);

            // 🔥 BODY
            String json = mapper.writeValueAsString(body);

            System.out.println("[CRM-BONUS][" + requestId + "] JSON ENVIADO:");
            System.out.println(json);

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();

            int status = conn.getResponseCode();
            String message = conn.getResponseMessage();

            long duration = System.currentTimeMillis() - startTime;

            System.out.println("[CRM-BONUS][" + requestId + "] STATUS HTTP: " + status);
            System.out.println("[CRM-BONUS][" + requestId + "] RESPONSE MESSAGE: " + message);
            System.out.println("[CRM-BONUS][" + requestId + "] TEMPO EXECUÇÃO (ms): " + duration);

            // 🔎 HEADERS DE RESPOSTA
            System.out.println("[CRM-BONUS][" + requestId + "] HEADERS RESPOSTA:");
            conn.getHeaderFields().forEach((key, value) -> {
                System.out.println("   " + key + " = " + value);
            });

            InputStream is = status >= 400
                    ? conn.getErrorStream()
                    : conn.getInputStream();

            String responseBody = "";

            if (is != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                br.close();
                responseBody = response.toString();
            }

            System.out.println("[CRM-BONUS][" + requestId + "] BODY RESPOSTA:");
            System.out.println(responseBody);
            System.out.println("[CRM-BONUS][" + requestId + "] =====================================\n");

            return responseBody;

        } catch (Exception e) {

            long duration = System.currentTimeMillis() - startTime;

            System.out.println("[CRM-BONUS][" + requestId + "] ERRO NA CHAMADA:");
            System.out.println("[CRM-BONUS][" + requestId + "] TEMPO EXECUÇÃO (ms): " + duration);
            e.printStackTrace();

            throw e;

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}