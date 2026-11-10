package br.com.zaya.belmicro.rateio.aplicacao.porta;

import br.com.zaya.belmicro.rateio.aplicacao.dto.OrigemCanonica;

public interface AutorizacaoRateio {
    enum Operacao { CONSULTAR, SIMULAR }
    /** User is obtained from the authenticated server session, never from request JSON. */
    void exigir(long usuario, OrigemCanonica origem, Operacao operacao);
    boolean permite(long usuario, OrigemCanonica origem, Operacao operacao);
}
