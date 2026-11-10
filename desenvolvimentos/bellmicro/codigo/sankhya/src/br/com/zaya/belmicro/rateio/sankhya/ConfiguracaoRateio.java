package br.com.zaya.belmicro.rateio.sankhya;

import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.zaya.belmicro.rateio.aplicacao.dto.OrigemCanonica;

/** Legacy compatibility only. New Financeiro actions use ConfiguracaoServicosRateio. */
@Deprecated
public interface ConfiguracaoRateio extends ConfiguracaoServicosRateio {
    /** Use the customer's supported modal launcher and component identifier, without copying IDs. */
    void abrirModal(ContextoAcao contexto,OrigemCanonica origem) throws Exception;
}
