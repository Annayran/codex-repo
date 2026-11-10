package br.com.zaya.belmicro.rateio.aplicacao.porta;

import br.com.zaya.belmicro.rateio.aplicacao.dto.OrigemCanonica;

/** Read-only port: financial creation is intentionally absent from this service. */
public interface ExecucaoRepository {
    boolean concluidaDe(OrigemCanonica origem);
}
