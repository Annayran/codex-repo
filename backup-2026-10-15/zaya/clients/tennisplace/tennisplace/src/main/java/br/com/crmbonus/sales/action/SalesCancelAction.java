package br.com.crmbonus.sales.action;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;

public class SalesCancelAction implements AcaoRotinaJava {

    @Override
    public void doAction(ContextoAcao contexto) throws Exception {
        // TODO: Implementar lógica de cancelamento da venda no CRM
        // Passo 9 do fluxo CRM Bonus
        // Chamada: SalesService.cancelSales()
        // Deve ser acionado quando a NF é cancelada no Sankhya
        // Cancela a operação na CRM Bonus
    }
}

