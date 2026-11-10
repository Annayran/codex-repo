package br.com.zaya.belmicro.rateio.aplicacao.porta;

import br.com.zaya.belmicro.rateio.aplicacao.dto.CriterioDisponivel;
import br.com.zaya.belmicro.rateio.aplicacao.dto.DadosOrigem;
import br.com.zaya.belmicro.rateio.dominio.DestinoPeso;
import java.util.List;

public interface CriterioRepository {
    /** Only active, approved, applicable and nonoverlapping versions; never choose implicitly. */
    List<CriterioDisponivel> aplicaveis(DadosOrigem origem);
    /**
     * Revalidate selected ID/version, category sources, eligibility and evidence. No automatic fallback.
     * For matrix criteria, enforce positive percentages (up to six decimals) totaling exactly 100
     * with ComposicaoManual.validar; analytical sources use their approved weights instead.
     */
    List<DestinoPeso> carregarPesos(String criterioId, int versao, DadosOrigem origem);
}
