package br.com.zaya.belmicro.rateio.aplicacao.dto;

import br.com.zaya.belmicro.rateio.dominio.ResultadoCalculo;
import java.time.Instant;
import java.util.Objects;

/** Immutable persisted snapshot. ID is stable per origin; revisions increase per origin. */
public final class SimulacaoRateio {
    public enum Situacao { VALIDA, COM_PENDENCIA, SUBSTITUIDA }
    private final long id;
    private final int revisao;
    private final OrigemCanonica origem;
    private final DadosOrigem dadosOrigem;
    private final EntradaSimulacao entrada;
    private final ResultadoCalculo resultado;
    private final String hash;
    private final Situacao situacao;
    private final String pendencia;
    private final long usuario;
    private final Instant data;
    private final String motivo;

    public SimulacaoRateio(long id, int revisao, DadosOrigem dadosOrigem, EntradaSimulacao entrada,
                           ResultadoCalculo resultado, String hash, Situacao situacao,
                           String pendencia, long usuario, Instant data, String motivo) {
        if (id <= 0 || revisao <= 0 || usuario <= 0 || situacao == null) throw new IllegalArgumentException("Simulacao invalida.");
        if (situacao == Situacao.VALIDA && (resultado == null || hash == null || pendencia != null)) {
            throw new IllegalArgumentException("Simulacao valida exige calculo e hash sem pendencia.");
        }
        if (situacao == Situacao.COM_PENDENCIA && (pendencia == null || resultado != null || hash != null)) {
            throw new IllegalArgumentException("Pendencia nao pode conter calculo valido.");
        }
        this.id = id;
        this.revisao = revisao;
        this.dadosOrigem = Objects.requireNonNull(dadosOrigem, "dadosOrigem");
        this.origem = dadosOrigem.getOrigem();
        this.entrada = Objects.requireNonNull(entrada, "entrada");
        this.resultado = resultado;
        this.hash = hash;
        this.situacao = situacao;
        this.pendencia = pendencia;
        this.usuario = usuario;
        this.data = Objects.requireNonNull(data, "data");
        this.motivo = motivo;
    }
    public long getId() { return id; }
    public int getRevisao() { return revisao; }
    public OrigemCanonica getOrigem() { return origem; }
    public DadosOrigem getDadosOrigem() { return dadosOrigem; }
    public EntradaSimulacao getEntrada() { return entrada; }
    public ResultadoCalculo getResultado() { return resultado; }
    public String getHash() { return hash; }
    public Situacao getSituacao() { return situacao; }
    public String getPendencia() { return pendencia; }
    public long getUsuario() { return usuario; }
    public Instant getData() { return data; }
    public String getMotivo() { return motivo; }
    public SimulacaoRateio substituida() {
        return new SimulacaoRateio(id,revisao,dadosOrigem,entrada,resultado,hash,Situacao.SUBSTITUIDA,pendencia,usuario,data,motivo);
    }
}
