package br.com.zaya.mahospitalar.genesis.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class FabricanteGenesisDto {
    public String codigoExterno;
    public String nomeFantasia;
    public String razaoSocial;
    public String cnpj;
    public String status;
    public Boolean fabricante;
    public Boolean fornecedor;
}
