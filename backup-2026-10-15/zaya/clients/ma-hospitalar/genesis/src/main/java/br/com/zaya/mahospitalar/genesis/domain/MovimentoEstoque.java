package br.com.zaya.mahospitalar.genesis.domain;

import java.math.BigDecimal;
import java.util.Date;

public class MovimentoEstoque {
    private String chaveMovimento;
    private String tipoMovimento;
    private Integer codTipoOperacao;
    private Integer codEmpresa;
    private String nomeFantasia;
    private String razaoEmpresa;
    private String cnpjEmpresa;
    private Long numUnico;
    private Long numNota;
    private Date dataNegociacao;
    private Date dataInstalacao;
    private Integer codParceiro;
    private String nomeParceiro;
    private String razaoParceiro;
    private String cnpjCpfParceiro;
    private String parcAtivo;
    private String codProduto;
    private String produto;
    private String grupoProduto;
    private String marca;
    private String rgAnvisa;
    private String serie;
    private String prodAtivo;
    private String poder;
    private Integer garantiaMeses;
    private String statusEstoque;
    private BigDecimal quantidade;
    private BigDecimal valorUnitario;
    private String localizacao;
    private Long idCategoria;
    private Long idFabricante;
    private Long idFornecedor;
    private Long idSubCategoria;

    public String getChaveMovimento() { return chaveMovimento; }
    public void setChaveMovimento(String chaveMovimento) { this.chaveMovimento = chaveMovimento; }
    public String getTipoMovimento() { return tipoMovimento; }
    public void setTipoMovimento(String tipoMovimento) { this.tipoMovimento = tipoMovimento; }
    public Integer getCodTipoOperacao() { return codTipoOperacao; }
    public void setCodTipoOperacao(Integer codTipoOperacao) { this.codTipoOperacao = codTipoOperacao; }
    public Integer getCodEmpresa() { return codEmpresa; }
    public void setCodEmpresa(Integer codEmpresa) { this.codEmpresa = codEmpresa; }
    public String getNomeFantasia() { return nomeFantasia; }
    public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }
    public String getRazaoEmpresa() { return razaoEmpresa; }
    public void setRazaoEmpresa(String razaoEmpresa) { this.razaoEmpresa = razaoEmpresa; }
    public String getCnpjEmpresa() { return cnpjEmpresa; }
    public void setCnpjEmpresa(String cnpjEmpresa) { this.cnpjEmpresa = cnpjEmpresa; }
    public Long getNumUnico() { return numUnico; }
    public void setNumUnico(Long numUnico) { this.numUnico = numUnico; }
    public Long getNumNota() { return numNota; }
    public void setNumNota(Long numNota) { this.numNota = numNota; }
    public Date getDataNegociacao() { return dataNegociacao; }
    public void setDataNegociacao(Date dataNegociacao) { this.dataNegociacao = dataNegociacao; }
    public Date getDataInstalacao() { return dataInstalacao; }
    public void setDataInstalacao(Date dataInstalacao) { this.dataInstalacao = dataInstalacao; }
    public Integer getCodParceiro() { return codParceiro; }
    public void setCodParceiro(Integer codParceiro) { this.codParceiro = codParceiro; }
    public String getNomeParceiro() { return nomeParceiro; }
    public void setNomeParceiro(String nomeParceiro) { this.nomeParceiro = nomeParceiro; }
    public String getRazaoParceiro() { return razaoParceiro; }
    public void setRazaoParceiro(String razaoParceiro) { this.razaoParceiro = razaoParceiro; }
    public String getCnpjCpfParceiro() { return cnpjCpfParceiro; }
    public void setCnpjCpfParceiro(String cnpjCpfParceiro) { this.cnpjCpfParceiro = cnpjCpfParceiro; }
    public String getParcAtivo() { return parcAtivo; }
    public void setParcAtivo(String parcAtivo) { this.parcAtivo = parcAtivo; }
    public String getCodProduto() { return codProduto; }
    public void setCodProduto(String codProduto) { this.codProduto = codProduto; }
    public String getProduto() { return produto; }
    public void setProduto(String produto) { this.produto = produto; }
    public String getGrupoProduto() { return grupoProduto; }
    public void setGrupoProduto(String grupoProduto) { this.grupoProduto = grupoProduto; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getRgAnvisa() { return rgAnvisa; }
    public void setRgAnvisa(String rgAnvisa) { this.rgAnvisa = rgAnvisa; }
    public String getSerie() { return serie; }
    public void setSerie(String serie) { this.serie = serie; }
    public String getProdAtivo() { return prodAtivo; }
    public void setProdAtivo(String prodAtivo) { this.prodAtivo = prodAtivo; }
    public String getPoder() { return poder; }
    public void setPoder(String poder) { this.poder = poder; }
    public Integer getGarantiaMeses() { return garantiaMeses; }
    public void setGarantiaMeses(Integer garantiaMeses) { this.garantiaMeses = garantiaMeses; }
    public String getStatusEstoque() { return statusEstoque; }
    public void setStatusEstoque(String statusEstoque) { this.statusEstoque = statusEstoque; }
    public BigDecimal getQuantidade() { return quantidade; }
    public void setQuantidade(BigDecimal quantidade) { this.quantidade = quantidade; }
    public BigDecimal getValorUnitario() { return valorUnitario; }
    public void setValorUnitario(BigDecimal valorUnitario) { this.valorUnitario = valorUnitario; }
    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }
    public Long getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Long idCategoria) { this.idCategoria = idCategoria; }
    public Long getIdFabricante() { return idFabricante; }
    public void setIdFabricante(Long idFabricante) { this.idFabricante = idFabricante; }
    public Long getIdFornecedor() { return idFornecedor; }
    public void setIdFornecedor(Long idFornecedor) { this.idFornecedor = idFornecedor; }
    public Long getIdSubCategoria() { return idSubCategoria; }
    public void setIdSubCategoria(Long idSubCategoria) { this.idSubCategoria = idSubCategoria; }
}