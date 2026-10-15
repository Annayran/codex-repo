package br.com.crmbonus.clientes.integration.client;

import br.com.crmbonus.clientes.dto.request.PinSendRequestDTO;
import br.com.crmbonus.clientes.dto.request.PinValidateRequestDTO;
import br.com.crmbonus.clientes.dto.request.PinResendRequestDTO;
import br.com.crmbonus.clientes.dto.response.UserVerifyResponse;
import br.com.crmbonus.clientes.dto.response.PinSendResponseDTO;

import java.math.BigDecimal;

public interface CrmBonusClient {

    /**
     * Passo 1: user-verify
     * Verifica se o cliente existe e se exige PIN
     */
    UserVerifyResponse userVerify(String cpf) throws Exception;

    // overload accepting company
    UserVerifyResponse userVerify(String cpf, BigDecimal codEmp) throws Exception;

    /**
     * Passo 2: pin-send
     * Cadastra o cliente e envia PIN
     */
    PinSendResponseDTO pinSend(PinSendRequestDTO request, String requestHash) throws Exception;

    PinSendResponseDTO pinSend(PinSendRequestDTO request, String requestHash, BigDecimal codEmp) throws Exception;

    /**
     * Passo 3: pin-validate
     * Valida o PIN enviado pelo cliente
     */
    String pinValidate(PinValidateRequestDTO request, String requestHash) throws Exception;

    String pinValidate(PinValidateRequestDTO request, String requestHash, BigDecimal codEmp) throws Exception;

    /**
     * Passo 4: pin-resend
     * Reenvia o PIN para o cliente
     */
    PinSendResponseDTO pinResend(PinResendRequestDTO request, String requestHash) throws Exception;

    PinSendResponseDTO pinResend(PinResendRequestDTO request, String requestHash, BigDecimal codEmp) throws Exception;
}