//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package br.com.zaya.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConciliacaoResponse {
    @JsonProperty("id")
    public Long id;

    @JsonProperty("nome_empresa")
    public String nome_empresa;

    @JsonProperty("cnpj_empresa")
    public String cnpj_empresa;

    @JsonProperty("created_at")
    public String created_at;

    @JsonProperty("updated_at")
    public String updated_at;

    @JsonProperty("id_transacao")
    public String id_transacao;

    @JsonProperty("id_extrato")
    public String id_extrato;

    @JsonProperty("id_koncili")
    public String id_koncili;

    @JsonProperty("cod_venda")
    public String cod_venda;

    @JsonProperty("data_venda")
    public String data_venda;

    @JsonProperty("data_pagamento")
    public String data_pagamento;

    @JsonProperty("numero_nota_fiscal")
    public Integer numero_nota_fiscal;

    @JsonProperty("data_nota_fiscal")
    public String data_nota_fiscal;

    @JsonProperty("forma_pagamento")
    public String forma_pagamento;

    @JsonProperty("valor_esperado")
    public Double valor_esperado;

    @JsonProperty("percentual_comissao_esperado")
    public Double percentual_comissao_esperado;

    @JsonProperty("data_esperada")
    public String data_esperada;

    @JsonProperty("data_liberacao")
    public String data_liberacao;

    @JsonProperty("conciliado")
    public Boolean conciliado;

    @JsonProperty("resolvido_erp")
    public Boolean resolvido_erp;

    @JsonProperty("id_conciliacao")
    public Integer id_conciliacao;

    @JsonProperty("observacao")
    public String observacao;

    @JsonProperty("canal")
    public String canal;

    @JsonProperty("nome_conta")
    public String nome_conta;

    @JsonProperty("nome_cliente")
    public String nome_cliente;

    @JsonProperty("documento_cliente")
    public String documento_cliente;

    @JsonProperty("situacao")
    public String situacao;

    @JsonProperty("valor_venda")
    public Double valor_venda;

    @JsonProperty("valor_comissao")
    public Double valor_comissao;

    @JsonProperty("valor_taxas")
    public Double valor_taxas;

    @JsonProperty("valor_frete")
    public Double valor_frete;

    @JsonProperty("data_conciliacao_inicial")
    public String data_conciliacao_inicial;

    @JsonProperty("data_conciliacao_final")
    public String data_conciliacao_final;

    @JsonProperty("data_conciliacao_fechamento")
    public String data_conciliacao_fechamento;

    @JsonProperty("conciliado_parcialmente")
    public Boolean conciliado_parcialmente;

    @JsonProperty("resolvido_koncili")
    public Boolean resolvido_koncili;

    @JsonProperty("serie_nota_fiscal")
    public String serie_nota_fiscal;

    @JsonProperty("id_venda")
    public String id_venda;

    @JsonProperty("tipo_extrato")
    public String tipo_extrato;

    @JsonProperty("id_registro")
    public String id_registro;

    @JsonProperty("valor_liberado")
    public Double valor_liberado;
}
