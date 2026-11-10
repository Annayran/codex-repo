package br.com.zaya.belmicro.rateio.aplicacao.porta;

import br.com.zaya.belmicro.rateio.aplicacao.dto.DadosOrigem;
import br.com.zaya.belmicro.rateio.aplicacao.dto.OrigemCanonica;

public interface OrigemRepository {
    /** Resolve financial installments to their NUNOTA, and detect the auxiliary marker. Read only. */
    DadosOrigem resolver(OrigemCanonica referencia);
    /** Validate destination registrations and all material creation mappings on the server. */
    void validarDestinos(DadosOrigem origem, java.util.List<br.com.zaya.belmicro.rateio.dominio.DestinoPeso> destinos);
}
