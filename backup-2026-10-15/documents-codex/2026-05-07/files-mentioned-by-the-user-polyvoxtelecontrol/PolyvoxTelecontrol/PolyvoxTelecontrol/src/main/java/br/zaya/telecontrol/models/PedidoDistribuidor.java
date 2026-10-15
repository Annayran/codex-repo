package br.zaya.telecontrol.models;

import java.util.ArrayList;

public class PedidoDistribuidor {
	public int pedido;
	public Fabrica fabrica;
	public Posto posto;
	public TipoPedido tipo_pedido;
	public Condicao condicao;
	public Object entrega;
	public Object exportado;
	public int status_pedido;
	public Object tipo_frete;
	public Object valores_adicionais;
	public Tabela tabela;
	public Distribuidor distribuidor;
	public String cnpj;
	public String data;
	public ArrayList<Item> itens;

	public static class Condicao {
		public int condicao;
		public String descricao;
	}

	public static class Distribuidor {
		public int posto;
		public String cnpj;
	}

	public static class Fabrica {
		public int fabrica;
		public String nome;
	}

	public static class Item {
		public int pedido_item;
		public String referencia;
		public String qtde;
		public String preco;
		public Object ipi;
		public String os;
		public String qtde_cancelada;
		public String qtde_faturada;
		public PedidoDistribuidor.Produtos produtos;
	}

	public static class Posto {
		public int posto;
		public String cnpj;
	}

	public static class Produtos {
		public String referencia;
		public String descricao;
	}

	public static class Tabela {
		public int tabela;
		public String sigla_tabela;
		public boolean ativa;
	}

	public static class TipoPedido {
		public int tipo_pedido;
		public String descricao;
		public String codigo;
	}
}
