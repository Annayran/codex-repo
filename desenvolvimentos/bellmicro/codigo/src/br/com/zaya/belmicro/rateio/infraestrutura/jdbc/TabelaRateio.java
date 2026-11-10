package br.com.zaya.belmicro.rateio.infraestrutura.jdbc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Physical keys from Criacao-Manual-Telas.html, not the earlier ZYARAT layout. */
public enum TabelaRateio {
    CRITERIO("AD_RATCRI", "CODCRITERIO"),
    VERSAO("AD_RATVER", "CODCRITERIO", "SEQUENCIA"),
    DESTINO("AD_RATDES", "CODCRITERIO", "SEQUENCIA", "SEQDESTINO"),
    SIMULACAO("AD_RATSIM", "NUSIMULACAO"),
    PARCELA("AD_RATPAR", "NUSIMULACAO", "SEQUENCIA"),
    GRUPO("AD_RATGRP", "NUSIMULACAO", "SEQUENCIA"),
    INTERNO("AD_RATINT", "NUSIMULACAO", "SEQUENCIA", "SEQINT"),
    EXECUCAO("AD_RATEXE", "NUSIMULACAO", "SEQUENCIA"),
    VINCULO("AD_RATVIN", "NUSIMULACAO", "SEQUENCIA", "SEQVIN"),
    EVENTO("AD_RATEVT", "NUSIMULACAO", "SEQUENCIA"),
    FINANCEIRO("AD_RATFIN", "NUFIN");

    private final String nome;
    private final List<String> chave;
    TabelaRateio(String nome, String... chave) {
        this.nome = nome;
        this.chave = Collections.unmodifiableList(Arrays.asList(chave.clone()));
    }
    public String getNome() { return nome; }
    public List<String> getChave() { return chave; }
}
