package br.com.zaya.belmicro.rateio.acao;

import br.com.sankhya.extensions.actionbutton.*;

/** Retained for an explicit migration message on existing button registrations. Do not register anew. */
@Deprecated
public final class AbrirModalRateioAction implements AcaoRotinaJava {
    public void doAction(ContextoAcao ctx)throws Exception{
        ctx.mostraErro("Acao antiga de abertura descontinuada. Cadastre SimularRateioAction, RecalcularRateioAction e EfetivarRateioAction na instancia Financeiro. O modal e aberto pelo JavaScript da pagina.");
    }
}
