package br.com.crmbonus.bonus.action;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;

public class BonusCampaignsAction implements AcaoRotinaJava {

    @Override
    public void doAction(ContextoAcao contexto) throws Exception {
        // TODO: Implementar lógica de listagem de campanhas
        // Passo 6 do fluxo CRM Bonus
        // Chamada: BonusService.getCampaigns()
        // Deve retornar campanhas elegíveis para modal de seleção no PDV
    }
}

