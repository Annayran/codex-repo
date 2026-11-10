package br.com.zaya.belmicro.rateio.aplicacao.porta;

import br.com.zaya.belmicro.rateio.aplicacao.dto.OrigemCanonica;
import br.com.zaya.belmicro.rateio.aplicacao.dto.SimulacaoRateio;

public interface SimulacaoRepository {
    interface Trabalho<T> { T executar(); }
    /**
     * One transaction/lock for canonical origin and request key, shared with future effectuation.
     * Roll back ALL changes on failure. No in-JVM lock is sufficient for a production adapter.
     */
    <T> T emTransacao(OrigemCanonica origem, String solicitacaoId, Trabalho<T> trabalho);
    SimulacaoRateio correnteDe(OrigemCanonica origem);
    /** Find current snapshot using its stable simulation ID, without returning it to the caller yet. */
    SimulacaoRateio correntePorId(long simulacaoId);
    long idPara(OrigemCanonica origem);
    /** Native header creation, when needed, is part of the same locked transaction. */
    default long idPara(br.com.zaya.belmicro.rateio.aplicacao.dto.DadosOrigem origem,
                        br.com.zaya.belmicro.rateio.aplicacao.dto.EntradaSimulacao entrada,
                        long usuario, java.time.Instant data) {
        return idPara(origem.getOrigem());
    }
    /**
     * Enforce a single current revision and increment under lock. Mark previous as SUBSTITUIDA
     * without overwriting its input/calculation. Must be atomic with request registration.
     */
    void salvarNovaRevisao(SimulacaoRateio simulacao);
    /** Global key lookup: never reuse across origin, user, operation or payload. */
    Solicitacao porSolicitacao(String solicitacaoId);
    void registrarSolicitacao(String solicitacaoId, String identidade, SimulacaoRateio resultado);

    final class Solicitacao {
        private final String identidade;
        private final SimulacaoRateio resultado;
        public Solicitacao(String identidade, SimulacaoRateio resultado) {
            this.identidade = identidade;
            this.resultado = resultado;
        }
        public String getIdentidade() { return identidade; }
        public SimulacaoRateio getResultado() { return resultado; }
    }
}
