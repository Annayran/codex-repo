package br.com.zaya.belmicro.rateio.acao;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;

/** Entry point only: creation remains blocked without the homologated financial implementation. */
public final class EfetivarRateioAction extends AcaoFinanceiroRateio implements AcaoRotinaJava {
    public EfetivarRateioAction(){super(Tipo.EFETIVAR);}
    EfetivarRateioAction(RuntimeAcoesRateio runtime){super(Tipo.EFETIVAR,runtime);}
}
