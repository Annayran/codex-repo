package br.com.crmbonus.bonus.service;

import br.com.crmbonus.bonus.dto.request.BonusAvailableRequestDTO;
import br.com.crmbonus.bonus.dto.request.BonusCampaignsRequestDTO;
import br.com.crmbonus.bonus.dto.request.BonusSubmitRequestDTO;
import br.com.crmbonus.bonus.dto.response.BonusAvailableResponseDTO;
import br.com.crmbonus.bonus.dto.response.BonusCampaignsResponseDTO;
import br.com.crmbonus.bonus.dto.response.BonusSubmitResponseDTO;

public interface BonusService {

    /**
     * Passo 5: bonus-available
     * Consultar saldo disponível antes do financeiro
     */
    BonusAvailableResponseDTO checkAvailable(BonusAvailableRequestDTO request, String requestHash) throws Exception;

    /**
     * Passo 6: bonus-campaigns
     * Retornar campanhas elegíveis para seleção em modal no PDV
     */
    BonusCampaignsResponseDTO getCampaigns(BonusCampaignsRequestDTO request, String requestHash) throws Exception;

    /**
     * Passo 7: bonus-submit
     * Vincular a campaign_id selecionada à venda
     */
    BonusSubmitResponseDTO submitCampaign(BonusSubmitRequestDTO request, String requestHash) throws Exception;
}

