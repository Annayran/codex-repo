package br.com.crmbonus.clientes.service.impl;

import br.com.crmbonus.clientes.dto.request.PinResendRequestDTO;
import br.com.crmbonus.clientes.dto.request.PinSendRequestDTO;
import br.com.crmbonus.clientes.dto.request.PinValidateRequestDTO;
import br.com.crmbonus.clientes.dto.response.PinSendResponseDTO;
import br.com.crmbonus.clientes.dto.response.UserVerifyResponse;
import br.com.crmbonus.clientes.integration.client.CrmBonusClient;
import br.com.crmbonus.clientes.integration.client.CrmHttpClient;

public class CrmBonusClientImpl implements CrmBonusClient {

    private final CrmHttpClient httpClient = new CrmHttpClient();

    @Override
    public UserVerifyResponse userVerify(String cpf) throws Exception {
        return httpClient.userVerify(cpf);
    }

    @Override
    public PinSendResponseDTO pinSend(PinSendRequestDTO request, String requestHash) throws Exception {
        return httpClient.pinSend(request, requestHash);
    }

    @Override
    public String pinValidate(PinValidateRequestDTO request, String requestHash) throws Exception {
        return httpClient.pinValidate(request, requestHash);
    }

    @Override
    public PinSendResponseDTO pinResend(PinResendRequestDTO request, String requestHash) throws Exception {
        return httpClient.pinResend(request, requestHash);
    }
}