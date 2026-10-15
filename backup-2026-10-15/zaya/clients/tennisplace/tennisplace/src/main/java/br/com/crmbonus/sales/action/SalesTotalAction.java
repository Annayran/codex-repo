package br.com.crmbonus.sales.action;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;

public class SalesTotalAction implements AcaoRotinaJava {

    @Override
    public void doAction(ContextoAcao contexto) throws Exception {
        // TODO: Implementar lógica de envio de dados finais da venda
        // Passo 8 do fluxo CRM Bonus
        // Chamada: SalesService.submitSalesTotal()
        // Deve ser acionado após emissão da NF
        // Salva o sale_id no campo AD_SALESIDCRM
    }
}

