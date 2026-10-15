package br.zaya.telecontrol.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PecaModel {

    @JsonProperty("referencia_peca")
    private String referenciaPeca;

    @JsonProperty("descricao_peca")
    private String descricaoPeca;

    @JsonProperty("po_peca")
    private String poPeca;

    @JsonProperty("data_digitacao_peca")
    private String dataDigitacaoPeca;

    @JsonProperty("qtde_solicitada")
    private String qtdeSolicitada;

    @JsonProperty("qtde_faturada")
    private String qtdeFaturada;

    @JsonProperty("qtde_pendente")
    private String qtdePendente;

    @JsonProperty("pedido")
    private String pedido;

    @JsonProperty("valor_item")
    private String valorItem;

    @JsonProperty("data_pedido")
    private String dataPedido;

    @JsonProperty("nf_faturamento")
    private String nfFaturamento;

    @JsonProperty("data_emissao_nota")
    private String dataEmissaoNota;

    @JsonProperty("data_postagem")
    private String dataPostagem;

    @JsonProperty("defeito_peca")
    private String defeitoPeca;

    @JsonProperty("rastreamento")
    private String rastreamento;

    @JsonProperty("data_entrega")
    private String dataEntrega;

    public String getReferenciaPeca() { return referenciaPeca; }
    public void setReferenciaPeca(String referenciaPeca) { this.referenciaPeca = referenciaPeca; }

    public String getDescricaoPeca() { return descricaoPeca; }
    public void setDescricaoPeca(String descricaoPeca) { this.descricaoPeca = descricaoPeca; }

    public String getPoPeca() { return poPeca; }
    public void setPoPeca(String poPeca) { this.poPeca = poPeca; }

    public String getDataDigitacaoPeca() { return dataDigitacaoPeca; }
    public void setDataDigitacaoPeca(String dataDigitacaoPeca) { this.dataDigitacaoPeca = dataDigitacaoPeca; }

    public String getQtdeSolicitada() { return qtdeSolicitada; }
    public void setQtdeSolicitada(String qtdeSolicitada) { this.qtdeSolicitada = qtdeSolicitada; }

    public String getQtdeFaturada() { return qtdeFaturada; }
    public void setQtdeFaturada(String qtdeFaturada) { this.qtdeFaturada = qtdeFaturada; }

    public String getQtdePendente() { return qtdePendente; }
    public void setQtdePendente(String qtdePendente) { this.qtdePendente = qtdePendente; }

    public String getPedido() { return pedido; }
    public void setPedido(String pedido) { this.pedido = pedido; }

    public String getValorItem() { return valorItem; }
    public void setValorItem(String valorItem) { this.valorItem = valorItem; }

    public String getDataPedido() { return dataPedido; }
    public void setDataPedido(String dataPedido) { this.dataPedido = dataPedido; }

    public String getNfFaturamento() { return nfFaturamento; }
    public void setNfFaturamento(String nfFaturamento) { this.nfFaturamento = nfFaturamento; }

    public String getDataEmissaoNota() { return dataEmissaoNota; }
    public void setDataEmissaoNota(String dataEmissaoNota) { this.dataEmissaoNota = dataEmissaoNota; }

    public String getDataPostagem() { return dataPostagem; }
    public void setDataPostagem(String dataPostagem) { this.dataPostagem = dataPostagem; }

    public String getDefeitoPeca() { return defeitoPeca; }
    public void setDefeitoPeca(String defeitoPeca) { this.defeitoPeca = defeitoPeca; }

    public String getRastreamento() { return rastreamento; }
    public void setRastreamento(String rastreamento) { this.rastreamento = rastreamento; }

    public String getDataEntrega() { return dataEntrega; }
    public void setDataEntrega(String dataEntrega) { this.dataEntrega = dataEntrega; }
}

