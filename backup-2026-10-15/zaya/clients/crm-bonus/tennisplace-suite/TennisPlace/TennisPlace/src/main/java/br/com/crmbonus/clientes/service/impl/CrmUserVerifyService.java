package br.com.crmbonus.clientes.service.impl;

import br.com.crmbonus.clientes.dto.response.UserVerifyResponse;
import br.com.crmbonus.clientes.integration.client.CrmHttpClient;

import java.math.BigDecimal;

public class CrmUserVerifyService {
    private final CrmHttpClient crmClient = new CrmHttpClient();

    public void validarUsuarioCRM(String cpf) throws Exception {

        UserVerifyResponse response = crmClient.userVerify(cpf);

        if (response.isResource_id()) {

            String html =
                    "<html>" +
                            "<div style='font-family:Arial; padding:10px;'>" +
                            "<h2 style='color:#E74C3C;'>Parceiro não cadastrado no CRM Bonus</h2>" +
                            "<hr/>" +
                            "<p>CPF: <b>" + cpf + "</b></p>" +
                            "<p>É necessário realizar o cadastro no CRM Bonus antes de continuar.</p>" +
                            "</div>" +
                            "</html>";

            throw new Exception(html);
        }

        System.out.println("[CRM] Usuário já cadastrado no CRM.");
    }

    // overload that accepts company
    public void validarUsuarioCRM(String cpf, BigDecimal codEmp) throws Exception {
        UserVerifyResponse response = crmClient.userVerify(cpf, codEmp);

        if (response.isResource_id()) {
            String html =
                    "<html>" +
                            "<div style='font-family:Arial; padding:10px;'>" +
                            "<h2 style='color:#E74C3C;'>Parceiro não cadastrado no CRM Bonus</h2>" +
                            "<hr/>" +
                            "<p>CPF: <b>" + cpf + "</b></p>" +
                            "<p>É necessário realizar o cadastro no CRM Bonus antes de continuar.</p>" +
                            "</div>" +
                            "</html>";

            throw new Exception(html);
        }

        System.out.println("[CRM] Usuário já cadastrado no CRM.");
    }
}
