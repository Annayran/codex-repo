package br.com.zaya.belmicro.rateio.aplicacao.porta;

import br.com.zaya.belmicro.rateio.aplicacao.dto.OrigemCanonica;
import java.util.Map;

/**
 * Integration contract, not an implementation of financial creation.
 * Implementations must authorize EFETIVAR, bind the selected origin to the persisted snapshot,
 * enforce request idempotency/current revision/hash, recalculate under shared locks, and create
 * titles/native rateios/links in one transaction. A failure must propagate for integral rollback.
 */
public interface EfetivacaoRateio {
    Map<String,Object> efetivar(OrigemCanonica referencia,long simulacaoId,int revisaoEsperada,
                               String hashEsperado,String solicitacaoId,long usuario);
}
