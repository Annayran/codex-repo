package br.com.zaya.nac.planocontas.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ResultadoProcessamentoLote {

    private int parceirosProcessados;
    private int parceirosIgnorados;
    private int contasCriadas;
    private final List<FalhaProcessamento> falhas = new ArrayList<FalhaProcessamento>();

    public void registrarSucesso(int quantidadeContas) {
        parceirosProcessados++;
        contasCriadas += quantidadeContas;
    }

    public void registrarIgnorado() {
        parceirosIgnorados++;
    }

    public void registrarFalha(FalhaProcessamento falha) {
        falhas.add(falha);
    }

    public int getParceirosProcessados() {
        return parceirosProcessados;
    }

    public int getParceirosIgnorados() {
        return parceirosIgnorados;
    }

    public int getContasCriadas() {
        return contasCriadas;
    }

    public int getQuantidadeFalhas() {
        return falhas.size();
    }

    public int getTotal() {
        return parceirosProcessados + parceirosIgnorados + falhas.size();
    }

    public boolean possuiFalhas() {
        return !falhas.isEmpty();
    }

    public List<FalhaProcessamento> getFalhas() {
        return Collections.unmodifiableList(falhas);
    }
}
