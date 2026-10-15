package br.zaya.telecontrol.models;

public class PedidoExportado {
	public String pedido;
	public Exportado exportado;

	public static class Exportado {
		public String date;
		public int timezone_type;
		public String timezone;
	}
}
