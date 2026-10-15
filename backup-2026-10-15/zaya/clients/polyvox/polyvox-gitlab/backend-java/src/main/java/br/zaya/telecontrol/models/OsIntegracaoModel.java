package br.zaya.telecontrol.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OsIntegracaoModel {

    @JsonProperty("numero_os")
    private String numeroOs;

    @JsonProperty("os")
    private String os;

    @JsonProperty("codigo_posto")
    private String codigoPosto;

    @JsonProperty("cnpj_posto")
    @JsonAlias({"cnpj"})
    private String cnpj;

    @JsonAlias({"nome", "razao_social"})
    private String nome;

    @JsonProperty("sua_os")
    private String suaOs;

    @JsonProperty("status_os")
    private String statusOs;

    @JsonProperty("data_abertura")
    private String dataAbertura;

    @JsonProperty("data_digitacao")
    private String dataDigitacao;

    @JsonProperty("data_conserto")
    private String dataConserto;

    @JsonProperty("data_fechamento")
    private String dataFechamento;

    @JsonProperty("tipo_atendimento")
    private String tipoAtendimento;

    @JsonProperty("os_reincidente")
    private String osReincidente;

    @JsonProperty("referencia_produto")
    private String referenciaProduto;

    @JsonProperty("descricao_produto")
    private String descricaoProduto;

    @JsonProperty("defeito_reclamado")
    private String defeitoReclamado;

    @JsonProperty("defeito_constatado")
    private String defeitoConstatado;

    @JsonProperty("solucao")
    private String solucao;

    @JsonProperty("consumidor")
    private String consumidor;

    @JsonProperty("consumidor_celular")
    private String consumidorCelular;

    @JsonProperty("consumidor_telefone")
    private String consumidorTelefone;

    @JsonProperty("consumidor_cpf")
    private String consumidorCpf;

    @JsonProperty("consumidor_endereco")
    private String consumidorEndereco;

    @JsonProperty("consumidor_numero")
    private String consumidorNumero;

    @JsonProperty("consumidor_bairro")
    private String consumidorBairro;

    @JsonProperty("consumidor_complemento")
    private String consumidorComplemento;

    @JsonProperty("consumidor_cep")
    private String consumidorCep;

    @JsonProperty("consumidor_cidade")
    private String consumidorCidade;

    @JsonProperty("consumidor_uf")
    private String consumidorUf;

    @JsonProperty("consumidor_email")
    private String consumidorEmail;

    @JsonProperty("revenda_razao_social")
    private String revendaRazaoSocial;

    @JsonProperty("revenda_cnpj")
    private String revendaCnpj;

    @JsonProperty("nf_compra")
    private String nfCompra;

    @JsonProperty("data_nf")
    private String dataNf;

    @JsonProperty("m_o")
    private String mao;

    @JsonProperty("serie")
    private String serie;

    @JsonProperty("data_fabricacao")
    private String dataFabricacao;

    @JsonProperty("local_reparo")
    private String localReparo;

    @JsonProperty("hd_chamado")
    private String hdChamado;

    @JsonProperty("dt_atendimento_callcenter_resolvido")
    private String dtAtendimentoCallcenterResolvido;

    @JsonProperty("data_abertura_atendimento")
    private String dataAberturaAtendimento;

    @JsonProperty("dias_aberto")
    private String diasAberto;

    @JsonProperty("os_interna")
    private String osInterna;

    @JsonProperty("protocolo")
    private String protocolo;

    @JsonProperty("defeito_reclamado_descricao")
    private String defeitoReclamadoDescricao;

    @JsonProperty("pecas")
    private List<PecaModel> pecas;

    public String getNumeroOs() { return numeroOs; }
    public void setNumeroOs(String numeroOs) { this.numeroOs = numeroOs; }

    public String getOs() { return os; }
    public void setOs(String os) { this.os = os; }

    public String getCodigoPosto() { return codigoPosto; }
    public void setCodigoPosto(String codigoPosto) { this.codigoPosto = codigoPosto; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSuaOs() { return suaOs; }
    public void setSuaOs(String suaOs) { this.suaOs = suaOs; }

    public String getStatusOs() { return statusOs; }
    public void setStatusOs(String statusOs) { this.statusOs = statusOs; }

    public String getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(String dataAbertura) { this.dataAbertura = dataAbertura; }

    public String getDataDigitacao() { return dataDigitacao; }
    public void setDataDigitacao(String dataDigitacao) { this.dataDigitacao = dataDigitacao; }

    public String getDataConserto() { return dataConserto; }
    public void setDataConserto(String dataConserto) { this.dataConserto = dataConserto; }

    public String getDataFechamento() { return dataFechamento; }
    public void setDataFechamento(String dataFechamento) { this.dataFechamento = dataFechamento; }

    public String getTipoAtendimento() { return tipoAtendimento; }
    public void setTipoAtendimento(String tipoAtendimento) { this.tipoAtendimento = tipoAtendimento; }

    public String getOsReincidente() { return osReincidente; }
    public void setOsReincidente(String osReincidente) { this.osReincidente = osReincidente; }

    public String getReferenciaProduto() { return referenciaProduto; }
    public void setReferenciaProduto(String referenciaProduto) { this.referenciaProduto = referenciaProduto; }

    public String getDescricaoProduto() { return descricaoProduto; }
    public void setDescricaoProduto(String descricaoProduto) { this.descricaoProduto = descricaoProduto; }

    public String getDefeitoReclamado() { return defeitoReclamado; }
    public void setDefeitoReclamado(String defeitoReclamado) { this.defeitoReclamado = defeitoReclamado; }

    public String getDefeitoConstatado() { return defeitoConstatado; }
    public void setDefeitoConstatado(String defeitoConstatado) { this.defeitoConstatado = defeitoConstatado; }

    public String getSolucao() { return solucao; }
    public void setSolucao(String solucao) { this.solucao = solucao; }

    public String getConsumidor() { return consumidor; }
    public void setConsumidor(String consumidor) { this.consumidor = consumidor; }

    public String getConsumidorCelular() { return consumidorCelular; }
    public void setConsumidorCelular(String consumidorCelular) { this.consumidorCelular = consumidorCelular; }

    public String getConsumidorTelefone() { return consumidorTelefone; }
    public void setConsumidorTelefone(String consumidorTelefone) { this.consumidorTelefone = consumidorTelefone; }

    public String getConsumidorCpf() { return consumidorCpf; }
    public void setConsumidorCpf(String consumidorCpf) { this.consumidorCpf = consumidorCpf; }

    public String getConsumidorEndereco() { return consumidorEndereco; }
    public void setConsumidorEndereco(String consumidorEndereco) { this.consumidorEndereco = consumidorEndereco; }

    public String getConsumidorNumero() { return consumidorNumero; }
    public void setConsumidorNumero(String consumidorNumero) { this.consumidorNumero = consumidorNumero; }

    public String getConsumidorBairro() { return consumidorBairro; }
    public void setConsumidorBairro(String consumidorBairro) { this.consumidorBairro = consumidorBairro; }

    public String getConsumidorComplemento() { return consumidorComplemento; }
    public void setConsumidorComplemento(String consumidorComplemento) { this.consumidorComplemento = consumidorComplemento; }

    public String getConsumidorCep() { return consumidorCep; }
    public void setConsumidorCep(String consumidorCep) { this.consumidorCep = consumidorCep; }

    public String getConsumidorCidade() { return consumidorCidade; }
    public void setConsumidorCidade(String consumidorCidade) { this.consumidorCidade = consumidorCidade; }

    public String getConsumidorUf() { return consumidorUf; }
    public void setConsumidorUf(String consumidorUf) { this.consumidorUf = consumidorUf; }

    public String getConsumidorEmail() { return consumidorEmail; }
    public void setConsumidorEmail(String consumidorEmail) { this.consumidorEmail = consumidorEmail; }

    public String getRevendaRazaoSocial() { return revendaRazaoSocial; }
    public void setRevendaRazaoSocial(String revendaRazaoSocial) { this.revendaRazaoSocial = revendaRazaoSocial; }

    public String getRevendaCnpj() { return revendaCnpj; }
    public void setRevendaCnpj(String revendaCnpj) { this.revendaCnpj = revendaCnpj; }

    public String getNfCompra() { return nfCompra; }
    public void setNfCompra(String nfCompra) { this.nfCompra = nfCompra; }

    public String getDataNf() { return dataNf; }
    public void setDataNf(String dataNf) { this.dataNf = dataNf; }

    public String getMao() { return mao; }
    public void setMao(String mao) { this.mao = mao; }

    public String getSerie() { return serie; }
    public void setSerie(String serie) { this.serie = serie; }

    public String getDataFabricacao() { return dataFabricacao; }
    public void setDataFabricacao(String dataFabricacao) { this.dataFabricacao = dataFabricacao; }

    public String getLocalReparo() { return localReparo; }
    public void setLocalReparo(String localReparo) { this.localReparo = localReparo; }

    public String getHdChamado() { return hdChamado; }
    public void setHdChamado(String hdChamado) { this.hdChamado = hdChamado; }

    public String getDtAtendimentoCallcenterResolvido() { return dtAtendimentoCallcenterResolvido; }
    public void setDtAtendimentoCallcenterResolvido(String dtAtendimentoCallcenterResolvido) { this.dtAtendimentoCallcenterResolvido = dtAtendimentoCallcenterResolvido; }

    public String getDataAberturaAtendimento() { return dataAberturaAtendimento; }
    public void setDataAberturaAtendimento(String dataAberturaAtendimento) { this.dataAberturaAtendimento = dataAberturaAtendimento; }

    public String getDiasAberto() { return diasAberto; }
    public void setDiasAberto(String diasAberto) { this.diasAberto = diasAberto; }

    public String getOsInterna() { return osInterna; }
    public void setOsInterna(String osInterna) { this.osInterna = osInterna; }

    public String getProtocolo() { return protocolo; }
    public void setProtocolo(String protocolo) { this.protocolo = protocolo; }

    public String getDefeitoReclamadoDescricao() { return defeitoReclamadoDescricao; }
    public void setDefeitoReclamadoDescricao(String defeitoReclamadoDescricao) { this.defeitoReclamadoDescricao = defeitoReclamadoDescricao; }

    public List<PecaModel> getPecas() { return pecas; }
    public void setPecas(List<PecaModel> pecas) { this.pecas = pecas; }
}

