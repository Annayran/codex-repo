package br.com.zaya.mahospitalar.genesis.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class MaterialGenesisDto {
    public String codigoExterno;
    public String nome;
    public String status;
    public String grauRisco;
}
