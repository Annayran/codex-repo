package br.com.crmbonus.produtos.integration.client;

import br.com.crmbonus.produtos.config.CRMConfig;
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
import java.util.UUID;

public class CRMClient {

    private static final ObjectMapper mapper = new ObjectMapper();
    private final CrmHttpConfig crmHttpConfig = new CrmHttpConfig();

    private Exception buildHubError(int status, String responseBody, BigDecimal codEmp) {
        if (status == 500 && responseBody != null && responseBody.contains("Error in workflow")) {
            return new Exception("Credenciais CRM não preenchidas para a empresa " + codEmp + ". Preencha AD_CRM_USERNAME e AD_CRM_PASSWORD na TSIEMP.");
        }
        return new Exception("Erro CRM (" + status + "): " + responseBody);
    }

    public String enviar(String entidade,
                         String subEntidade,
                         Object body, BigDecimal codEmp) throws Exception {

        String requestId = UUID.randomUUID().toString();
        long startTime = System.currentTimeMillis();

        String fullUrl = CRMConfig.BASE_URL;

        crmHttpConfig.validateCompanyCredentials(codEmp, requestId);

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

            // Auth centralizada via credenciais da empresa (TSIEMP)
            crmHttpConfig.applyAuthHeaders(conn, codEmp, requestId);
            System.out.println("[CRM-BONUS][" + requestId + "] Auth da empresa processado para codEmp=" + codEmp);
            System.out.println("[CRM-BONUS][" + requestId + "] Headers enviados: entidade=" + entidade + ", sub-entidade=" + subEntidade + ", credenciais=TSIEMP");

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
            conn.getHeaderFields().forEach((key, value) -> System.out.println("   " + key + " = " + value));

            InputStream is = status >= 400
                    ? conn.getErrorStream()
                    : conn.getInputStream();

            String responseBody = "";

            if (is != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
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

            if (status < 200 || status >= 300) {
                throw buildHubError(status, responseBody, codEmp);
            }

            return responseBody;

        } catch (Exception e) {

            long duration = System.currentTimeMillis() - startTime;

            System.out.println("[CRM-BONUS][" + requestId + "] ERRO NA CHAMADA:");
            System.out.println("[CRM-BONUS][" + requestId + "] TEMPO EXECUÇÃO (ms): " + duration);
            System.out.println("[CRM-BONUS][" + requestId + "] ERRO: " + e.getClass().getSimpleName() + " - " + e.getMessage());

            throw e;

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}