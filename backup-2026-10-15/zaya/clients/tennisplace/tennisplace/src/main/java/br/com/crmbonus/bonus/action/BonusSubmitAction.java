package br.com.crmbonus.bonus.action;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;

public class BonusSubmitAction implements AcaoRotinaJava {

    @Override
    public void doAction(ContextoAcao contexto) throws Exception {
        // TODO: Implementar lógica de submissão da campanha selecionada
        // Passo 7 do fluxo CRM Bonus
        // Chamada: BonusService.submitCampaign()
        // Vincular campaign_id selecionada à venda
    }
}

