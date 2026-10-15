package tech.skala.action.integration.traycorp;

import tech.skala.core.commons.exceptions.SkalaModelException;

public class TrayCorpException {
	private static final String ECOMM_NAO_FOI_POSSIVEL_LOCALIZAR_CLIENTE = "Não foi possível localizar o Cliente de e-commerce.";
	private static final String ECOMM_NAO_FOI_POSSIVEL_INCLUIR_CLIENTE = "Não foi possível incluir o Cliente de e-commerce.";
	private static final String ECOMM_NAO_FOI_POSSIVEL_DEFINIR_ENDERECO = "Não foi possível definir corretamente a estrutura do endereço.";
	private static final String ECOMM_NAO_FOI_POSSIVEL_INCLUIR_PARCEIRO = "Não foi possível incluir o Parceiro de e-commerce.";
	private static final String ECOMM_NAO_FOI_POSSIVEL_VINCULAR_CLIENTE_PARCEIRO = "Não foi possível incluir o relacionamento entre Cliente e Parceiro de e-commerce.";
	private static final String ECOMM_NAO_FOI_POSSIVEL_INCLUIR_PEDIDO = "Não foi possível incluir o Pedido de e-commerce.";
	private static final String ECOMM_NAO_FOI_POSSIVEL_INCLUIR_CABECALHO_PEDIDO = "Não foi possível incluir o Cabeçalho do Pedido de Venda.";
	private static final String ECOMM_NAO_FOI_POSSIVEL_INCLUIR_ITEM_PEDIDO = "Não foi possível incluir itens do Pedido de Venda.";
	
	private TrayCorpException() {
		throw new IllegalStateException("Utility class");
	}

	public static class ParceiroEcommNaoLocalizadoException extends SkalaModelException {

		private static final long serialVersionUID = 5101282858183204212L;

		public ParceiroEcommNaoLocalizadoException() {
			super(ECOMM_NAO_FOI_POSSIVEL_LOCALIZAR_CLIENTE);
		}

		public ParceiroEcommNaoLocalizadoException(String message) {
			super(message);
		}

		public ParceiroEcommNaoLocalizadoException(Throwable cause) {
			super(ECOMM_NAO_FOI_POSSIVEL_LOCALIZAR_CLIENTE, cause);
		}

	}

	public static class ClienteEcommNaoSalvoException extends SkalaModelException {

		private static final long serialVersionUID = -478423366164247925L;

		public ClienteEcommNaoSalvoException() {
			super(ECOMM_NAO_FOI_POSSIVEL_INCLUIR_CLIENTE);
		}

		public ClienteEcommNaoSalvoException(String message) {
			super(message);
		}

		public ClienteEcommNaoSalvoException(Throwable cause) {
			super(ECOMM_NAO_FOI_POSSIVEL_INCLUIR_CLIENTE, cause);
		}

	}

	public static class EnderecoNaoDefinidoException extends SkalaModelException {

		private static final long serialVersionUID = 8818726543675151627L;

		public EnderecoNaoDefinidoException() {
			super(ECOMM_NAO_FOI_POSSIVEL_DEFINIR_ENDERECO);
		}

		public EnderecoNaoDefinidoException(String message) {
			super(message);
		}

		public EnderecoNaoDefinidoException(Throwable cause) {
			super(ECOMM_NAO_FOI_POSSIVEL_DEFINIR_ENDERECO, cause);
		}

	}

	public static class ParceiroNaoSalvoException extends SkalaModelException {

		private static final long serialVersionUID = 2242401756943942966L;

		public ParceiroNaoSalvoException() {
			super(ECOMM_NAO_FOI_POSSIVEL_INCLUIR_PARCEIRO);
		}

		public ParceiroNaoSalvoException(String message) {
			super(message);
		}

		public ParceiroNaoSalvoException(Throwable cause) {
			super(ECOMM_NAO_FOI_POSSIVEL_INCLUIR_PARCEIRO, cause);
		}

	}

	public static class ParceiroClienteEcommNaoSalvoException extends SkalaModelException {

		private static final long serialVersionUID = 8700488129189677385L;

		public ParceiroClienteEcommNaoSalvoException() {
			super(ECOMM_NAO_FOI_POSSIVEL_VINCULAR_CLIENTE_PARCEIRO);
		}

		public ParceiroClienteEcommNaoSalvoException(String message) {
			super(message);
		}

		public ParceiroClienteEcommNaoSalvoException(Throwable cause) {
			super(ECOMM_NAO_FOI_POSSIVEL_VINCULAR_CLIENTE_PARCEIRO, cause);
		}

	}

	public static class PedidoEcommNaoSalvoException extends SkalaModelException {

		private static final long serialVersionUID = -1104025513745226917L;

		public PedidoEcommNaoSalvoException() {
			super(ECOMM_NAO_FOI_POSSIVEL_INCLUIR_PEDIDO);
		}

		public PedidoEcommNaoSalvoException(String message) {
			super(message);
		}

		public PedidoEcommNaoSalvoException(Throwable cause) {
			super(ECOMM_NAO_FOI_POSSIVEL_INCLUIR_PEDIDO, cause);
		}

	}

	public static class PedidoCabecalhoNaoSalvoException extends SkalaModelException {

		private static final long serialVersionUID = 338188012829259815L;

		public PedidoCabecalhoNaoSalvoException() {
			super(ECOMM_NAO_FOI_POSSIVEL_INCLUIR_CABECALHO_PEDIDO);
		}

		public PedidoCabecalhoNaoSalvoException(String message) {
			super(message);
		}

		public PedidoCabecalhoNaoSalvoException(Throwable cause) {
			super(ECOMM_NAO_FOI_POSSIVEL_INCLUIR_CABECALHO_PEDIDO, cause);
		}

	}

	public static class PedidoItemNaoSalvoException extends SkalaModelException {

		private static final long serialVersionUID = 918711383190715390L;

		public PedidoItemNaoSalvoException() {
			super(ECOMM_NAO_FOI_POSSIVEL_INCLUIR_ITEM_PEDIDO);
		}

		public PedidoItemNaoSalvoException(String message) {
			super(message);
		}

		public PedidoItemNaoSalvoException(Throwable cause) {
			super(ECOMM_NAO_FOI_POSSIVEL_INCLUIR_ITEM_PEDIDO, cause);
		}

	}
}
