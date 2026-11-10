package br.com.zaya.teleworld.fornecedorxml.model.dto;

import java.math.BigDecimal;

public class FornecedorXmlData {
    private BigDecimal nuArquivo;
    private BigDecimal codEmp;
    private BigDecimal codParc;
    private String statusXml;
    private String origem;
    private String cnpjCpf;
    private String chaveAcesso;
    private String nomeArquivo;
    private String tipoCadastro;
    private String razaoSocial;
    private String nomeFantasia;
    private String email;
    private String telefone;
    private String endereco;

    public BigDecimal getNuArquivo() { return nuArquivo; }
    public void setNuArquivo(BigDecimal value) { nuArquivo = value; }
    public BigDecimal getCodEmp() { return codEmp; }
    public void setCodEmp(BigDecimal value) { codEmp = value; }
    public BigDecimal getCodParc() { return codParc; }
    public void setCodParc(BigDecimal value) { codParc = value; }
    public String getStatusXml() { return statusXml; }
    public void setStatusXml(String value) { statusXml = value; }
    public String getOrigem() { return origem; }
    public void setOrigem(String value) { origem = value; }
    public String getCnpjCpf() { return cnpjCpf; }
    public void setCnpjCpf(String value) { cnpjCpf = value; }
    public String getChaveAcesso() { return chaveAcesso; }
    public void setChaveAcesso(String value) { chaveAcesso = value; }
    public String getNomeArquivo() { return nomeArquivo; }
    public void setNomeArquivo(String value) { nomeArquivo = value; }
    public String getTipoCadastro() { return tipoCadastro; }
    public void setTipoCadastro(String value) { tipoCadastro = value; }
    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String value) { razaoSocial = value; }
    public String getNomeFantasia() { return nomeFantasia; }
    public void setNomeFantasia(String value) { nomeFantasia = value; }
    public String getEmail() { return email; }
    public void setEmail(String value) { email = value; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String value) { telefone = value; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String value) { endereco = value; }
}
