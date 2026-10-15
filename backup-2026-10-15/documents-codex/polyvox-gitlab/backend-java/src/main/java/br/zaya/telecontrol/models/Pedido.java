package br.zaya.telecontrol.models;

import java.util.ArrayList;

public class Pedido {
	public int pedido;
	public String data;
	public String cnpj;
	public String codigo;
	public String codigo_condicao;
	public String entrega;
	public String exportado;
	public double valor_adicional_fabricante;
	public double valor_desconto_fabricante;
	public ArrayList<PedidoItem> itens;
}
