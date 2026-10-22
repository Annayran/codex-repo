package br.com.zaya.mahospitalar.genesis.importacao;

import java.util.Locale;

public enum AbaBaseGenesis {
    PROPRIEDADE("prorpiedade_equipamento", "GENDOMINIO", "PROPRIEDADE"),
    SITUACAO("situacao_equipamento", "GENDOMINIO", "SITUACAO"),
    CLIENTE("Cliente", "GENCLIENTE", null),
    SETOR("Setor", "GENSECTOR", null),
    MATERIAL("Material", "GENMATERIAL", null),
    EMPRESA("Empresa", "GENEMPRESA", null),
    MODELO("Modelo", "GENMODELO", null),
    ITEM_MATERIAL("ItemMaterial", "GENITEMMAT", null);

    private final String nomePlanilha;
    private final String entidade;
    private final String tipoDominio;

    AbaBaseGenesis(String nomePlanilha, String entidade, String tipoDominio) {
        this.nomePlanilha = nomePlanilha;
        this.entidade = entidade;
        this.tipoDominio = tipoDominio;
    }

    public String getNomePlanilha() {
        return nomePlanilha;
    }

    public String getEntidade() {
        return entidade;
    }

    public String getTipoDominio() {
        return tipoDominio;
    }

    public static AbaBaseGenesis porNome(String nome) {
        if (nome == null) {
            throw new IllegalArgumentException("Aba da planilha nao informada.");
        }
        String comparacao = nome.trim().toLowerCase(Locale.ROOT);
        if ("clientes".equals(comparacao)) {
            return CLIENTE;
        }
        for (AbaBaseGenesis aba : values()) {
            if (aba.nomePlanilha.toLowerCase(Locale.ROOT).equals(comparacao)) {
                return aba;
            }
        }
        throw new IllegalArgumentException("Aba nao permitida para importacao: " + nome);
    }
}
