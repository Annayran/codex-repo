package br.com.zaya.mahospitalar.genesis.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ClienteGenesisDto {
    public String codigoExterno;
    public String cnpj;
    public String cpf;
    public String nomeFantasia;
    public String razaoSocial;
    public String status;
    public String email;
    public String endereco;
    public String enderecoBairro;
    public String enderecoCidade;
    public String enderecoUF;
    public String cep;
    public String telefone;
}
