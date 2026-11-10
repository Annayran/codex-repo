package br.com.zaya.belmicro.rateio.acao;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;

/** Register as Rotina Java on Financeiro. Bound to selected NUFIN and persisted revision. */
public final class RecalcularRateioAction extends AcaoFinanceiroRateio implements AcaoRotinaJava {
    public RecalcularRateioAction(){super(Tipo.RECALCULAR);}
    RecalcularRateioAction(RuntimeAcoesRateio runtime){super(Tipo.RECALCULAR,runtime);}
}
