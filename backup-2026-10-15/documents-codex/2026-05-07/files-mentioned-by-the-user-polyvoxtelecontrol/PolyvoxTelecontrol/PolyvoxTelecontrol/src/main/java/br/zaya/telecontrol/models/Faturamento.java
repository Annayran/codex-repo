package br.zaya.telecontrol.models;

import java.util.ArrayList;

public class Faturamento {
	public String posto;

	public String notaFiscal;

	public String serie;

	public String emissao;

	public String totalNota;

	public String totalIpi;

	public String totalIcms;

	public String cfop;

	public String valor_desconto_fabricante;

	public String transportadora;

	public String valorFrete;

	public String envio;

	public String codigoRastreio;

	public ArrayList<FaturamentoItem> itens = new ArrayList<>();
}
