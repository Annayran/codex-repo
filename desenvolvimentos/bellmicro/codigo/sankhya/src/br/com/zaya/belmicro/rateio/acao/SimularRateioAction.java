package br.com.zaya.belmicro.rateio.acao;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;

/** Register as Rotina Java on Financeiro. Input: RATEIO_JSON; output: JSON in mensagemRetorno. */
public final class SimularRateioAction extends AcaoFinanceiroRateio implements AcaoRotinaJava {
    public SimularRateioAction(){super(Tipo.SIMULAR);}
    SimularRateioAction(RuntimeAcoesRateio runtime){super(Tipo.SIMULAR,runtime);}
}
