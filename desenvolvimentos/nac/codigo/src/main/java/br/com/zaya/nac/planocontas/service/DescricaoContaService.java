package br.com.zaya.nac.planocontas.service;

import br.com.zaya.nac.planocontas.model.Parceiro;

public final class DescricaoContaService {

    public static final int LIMITE_DESCRICAO = 40;

    public String gerar(Parceiro parceiro) {
        String nome = parceiro.getNome() == null ? "" : parceiro.getNome().trim();
        String descricao = nome.isEmpty()
                ? "PARCEIRO " + parceiro.getCodigo()
                : nome;
        return descricao.length() <= LIMITE_DESCRICAO
                ? descricao
                : descricao.substring(0, LIMITE_DESCRICAO);
    }
}
