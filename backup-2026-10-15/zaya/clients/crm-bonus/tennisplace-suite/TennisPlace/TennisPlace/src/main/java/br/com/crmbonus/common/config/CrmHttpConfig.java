package br.com.crmbonus.common.config;

import br.com.crmbonus.clientes.config.CRMConfig;
import br.com.crmbonus.common.integration.EmpresaCredentialsProvider;
import br.com.crmbonus.common.integration.EmpresaCredentialsValidationService;

import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

public class CrmHttpConfig {

    private final EmpresaCredentialsProvider credentialsProvider = new EmpresaCredentialsProvider();
    private final EmpresaCredentialsValidationService validationService = new EmpresaCredentialsValidationService();

    public void validateCompanyCredentials(BigDecimal codEmp, String requestId) {
        Optional<String> validationError = validationService.validate(codEmp);
        if (validationError.isPresent()) {
            String msg = validationError.get();
            System.out.println("[CRM-BONUS][" + requestId + "] " + msg);
            throw new IllegalStateException(msg);
        }
    }

    /**
     * Aplica headers no HttpURLConnection.
     * - Authorization: sempre via CRMConfig
     * - username/password: enviados quando configurados na empresa
     */
    public void applyAuthHeaders(HttpURLConnection conn, BigDecimal codEmp, String requestId) {

        try {

            Optional<EmpresaCredentialsProvider.Credentials> opt = credentialsProvider.getCredentials(codEmp);
            if (!opt.isPresent()) {
                String msg = "Credenciais CRM não configuradas para a empresa " + codEmp + ": preencha AD_CRM_USERNAME e AD_CRM_PASSWORD na TSIEMP.";
                System.out.println("[CRM-BONUS][" + requestId + "] " + msg);
                throw new IllegalStateException(msg);
            }

            EmpresaCredentialsProvider.Credentials c = opt.get();
            conn.setRequestProperty("username", c.username);
            conn.setRequestProperty("password", c.password);
            System.out.println("[CRM-BONUS][" + requestId + "] Credenciais da empresa aplicadas via TSIEMP: " + codEmp);

            String credentials = CRMConfig.USERNAME + ":" + CRMConfig.TOKEN;
            String encoded = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
            conn.setRequestProperty("Authorization", "Basic " + encoded);
            System.out.println("[CRM-BONUS][" + requestId + "] Authorization aplicado via CRMConfig.");
        } catch (Exception e) {
            if (e instanceof IllegalStateException) {
                throw (IllegalStateException) e;
            }
            String msg = "Erro ao validar credenciais CRM da empresa " + codEmp + ": " + e.getMessage();
            System.out.println("[CRM-BONUS][" + requestId + "] " + msg);
            throw new IllegalStateException(msg, e);
        }
    }
}
