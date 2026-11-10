package br.com.zaya.belmicro.rateio.aplicacao.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Proposed modal response. VALIDA never grants permission to create financial records. */
public final class ContextoModal {
    private final DadosOrigem origem;
    private final List<CriterioDisponivel> criterios;
    private final SimulacaoRateio simulacao;
    private final boolean efetivada;
    private final boolean podeSimular;
    private final List<String> pendencias;
    public ContextoModal(DadosOrigem origem, List<CriterioDisponivel> criterios,
                         SimulacaoRateio simulacao, boolean efetivada, boolean podeSimular) {
        this(origem,criterios,simulacao,efetivada,podeSimular,Collections.<String>emptyList());
    }
    public ContextoModal(DadosOrigem origem,List<CriterioDisponivel> criterios,
                         SimulacaoRateio simulacao,boolean efetivada,boolean podeSimular,List<String> pendencias){
        this.origem = origem;
        this.criterios = Collections.unmodifiableList(new ArrayList<CriterioDisponivel>(criterios));
        this.simulacao = simulacao;
        this.efetivada = efetivada;
        this.podeSimular = podeSimular;
        this.pendencias=Collections.unmodifiableList(new ArrayList<String>(pendencias));
    }
    public DadosOrigem getOrigem() { return origem; }
    public List<CriterioDisponivel> getCriterios() { return criterios; }
    public SimulacaoRateio getSimulacao() { return simulacao; }
    public boolean isEfetivada() { return efetivada; }
    public boolean isPodeSimular() { return podeSimular; }
    public List<String> getPendencias(){return pendencias;}
}
