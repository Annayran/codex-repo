package br.zaya.telecontrol.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrdemServico {
    @JsonProperty("id_posto")
    private int idPosto;
    @JsonProperty("nome_fabrica")
    private String nomeFabrica;
    @JsonProperty("id_fabrica")
    private int idFabrica;
    private String os;
    @JsonProperty("codigo_posto")
    private String codigoPosto;
    private String nome;
    @JsonProperty("sua_os")
    private String suaOs;
    @JsonProperty("descricao_tipo_atendimento")
    private String descricaoTipoAtendimento;
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
    @JsonProperty("os_reincidente")
    private String osReincidente;
    private String referencia;
    private String descricao;
    @JsonProperty("defeito_reclamado")
    private String defeitoReclamado;
    @JsonProperty("defeito_constatado")
    private String defeitoConstatado;
    private String solucao;
    @JsonProperty("consumidor_nome")
    private String consumidorNome;
    @JsonProperty("consumidor_fone")
    private String consumidorFone;
    @JsonProperty("consumidor_cpf")
    private String consumidorCpf;
    private String revenda;
    @JsonProperty("nota_fiscal")
    private String notaFiscal;
    @JsonProperty("data_nf")
    private String dataNf;
    private String cnpj;
    @JsonProperty("defeito_reclamado_descricao")
    private String defeitoReclamadoDescricao;
    @JsonProperty("consumidor_fone_comercial")
    private String consumidorFoneComercial;
    @JsonProperty("consumidor_email")
    private String consumidorEmail;
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
    @JsonProperty("consumidor_estado")
    private String consumidorEstado;
    private String serie;
    @JsonProperty("data_fabricacao")
    private String dataFabricacao;
    @JsonProperty("local_reparo")
    private String localReparo;
    @JsonProperty("dias_aberto")
    private int diasAberto;
    @JsonProperty("hd_chamado")
    private String hdChamado;
    @JsonProperty("data_atendimento_callcenter_resolvido")
    private String dataAtendimentoCallcenterResolvido;
    @JsonProperty("data_abertura_atendimento")
    private String dataAberturaAtendimento;

    // Getters and Setters
    public String getOs() { return os; }
    public void setOs(String os) { this.os = os; }
    public String getCodigoPosto() { return codigoPosto; }
    public void setCodigoPosto(String codigoPosto) { this.codigoPosto = codigoPosto; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getSuaOs() { return suaOs; }
    public void setSuaOs(String suaOs) { this.suaOs = suaOs; }
    public String getDescricaoTipoAtendimento() { return descricaoTipoAtendimento; }
    public void setDescricaoTipoAtendimento(String descricaoTipoAtendimento) { this.descricaoTipoAtendimento = descricaoTipoAtendimento; }
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
    public String getOsReincidente() { return osReincidente; }
    public void setOsReincidente(String osReincidente) { this.osReincidente = osReincidente; }
    public String getReferencia() { return referencia; }
    public void setReferencia(String referencia) { this.referencia = referencia; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getDefeitoReclamado() { return defeitoReclamado; }
    public void setDefeitoReclamado(String defeitoReclamado) { this.defeitoReclamado = defeitoReclamado; }
    public String getDefeitoConstatado() { return defeitoConstatado; }
    public void setDefeitoConstatado(String defeitoConstatado) { this.defeitoConstatado = defeitoConstatado; }
    public String getSolucao() { return solucao; }
    public void setSolucao(String solucao) { this.solucao = solucao; }
    public String getConsumidorNome() { return consumidorNome; }
    public void setConsumidorNome(String consumidorNome) { this.consumidorNome = consumidorNome; }
    public String getConsumidorFone() { return consumidorFone; }
    public void setConsumidorFone(String consumidorFone) { this.consumidorFone = consumidorFone; }
    public String getConsumidorCpf() { return consumidorCpf; }
    public void setConsumidorCpf(String consumidorCpf) { this.consumidorCpf = consumidorCpf; }
    public String getRevenda() { return revenda; }
    public void setRevenda(String revenda) { this.revenda = revenda; }
    public String getNotaFiscal() { return notaFiscal; }
    public void setNotaFiscal(String notaFiscal) { this.notaFiscal = notaFiscal; }
    public String getDataNf() { return dataNf; }
    public void setDataNf(String dataNf) { this.dataNf = dataNf; }
    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    public String getDefeitoReclamadoDescricao() { return defeitoReclamadoDescricao; }
    public void setDefeitoReclamadoDescricao(String defeitoReclamadoDescricao) { this.defeitoReclamadoDescricao = defeitoReclamadoDescricao; }
    public String getConsumidorFoneComercial() { return consumidorFoneComercial; }
    public void setConsumidorFoneComercial(String consumidorFoneComercial) { this.consumidorFoneComercial = consumidorFoneComercial; }
    public String getConsumidorEmail() { return consumidorEmail; }
    public void setConsumidorEmail(String consumidorEmail) { this.consumidorEmail = consumidorEmail; }
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
    public String getConsumidorEstado() { return consumidorEstado; }
    public void setConsumidorEstado(String consumidorEstado) { this.consumidorEstado = consumidorEstado; }
    public String getSerie() { return serie; }
    public void setSerie(String serie) { this.serie = serie; }
    public String getDataFabricacao() { return dataFabricacao; }
    public void setDataFabricacao(String dataFabricacao) { this.dataFabricacao = dataFabricacao; }
    public String getLocalReparo() { return localReparo; }
    public void setLocalReparo(String localReparo) { this.localReparo = localReparo; }
    public int getDiasAberto() { return diasAberto; }
    public void setDiasAberto(int diasAberto) { this.diasAberto = diasAberto; }
    public String getHdChamado() { return hdChamado; }
    public void setHdChamado(String hdChamado) { this.hdChamado = hdChamado; }
    public String getDataAtendimentoCallcenterResolvido() { return dataAtendimentoCallcenterResolvido; }
    public void setDataAtendimentoCallcenterResolvido(String dataAtendimentoCallcenterResolvido) { this.dataAtendimentoCallcenterResolvido = dataAtendimentoCallcenterResolvido; }
    public String getDataAberturaAtendimento() { return dataAberturaAtendimento; }
    public void setDataAberturaAtendimento(String dataAberturaAtendimento) { this.dataAberturaAtendimento = dataAberturaAtendimento; }

    public int getIdPosto() { return idPosto; }
    public void setIdPosto(int idPosto) { this.idPosto = idPosto; }
    public String getNomeFabrica() { return nomeFabrica; }
    public void setNomeFabrica(String nomeFabrica) { this.nomeFabrica = nomeFabrica; }
    public int getIdFabrica() { return idFabrica; }
    public void setIdFabrica(int idFabrica) { this.idFabrica = idFabrica; }
}
