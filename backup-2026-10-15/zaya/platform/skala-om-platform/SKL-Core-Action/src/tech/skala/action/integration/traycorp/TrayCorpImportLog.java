package tech.skala.action.integration.traycorp;

public class TrayCorpImportLog {
	private long							totalPedidos;
	private long							qtdSucesso;
	private long							qtdErro;

	public TrayCorpImportLog() {
		this.totalPedidos = 0L;
		this.qtdSucesso = 0L;
		this.qtdErro = 0L;
	}

	public long getTotalPedidos() {
		return totalPedidos;
	}

	public void setTotalPedidos(long totalPedidos) {
		this.totalPedidos = totalPedidos;
	}

	public long getQtdSucesso() {
		return qtdSucesso;
	}

	public void setQtdSucesso(long qtdSucesso) {
		this.qtdSucesso = qtdSucesso;
	}

	public long getQtdErro() {
		return qtdErro;
	}

	public void setQtdErro(long qtdErro) {
		this.qtdErro = qtdErro;
	}

	public void add() {
		this.qtdSucesso++;
	}

	public void addError() {
		this.qtdErro++;
	}

	@Override
	public String toString() {
		String msg = null;
		StringBuilder sb = new StringBuilder();

		msg = String.format("Total de Pedidos Processados   : %4d%n", totalPedidos);
		sb.append(msg);

		msg = String.format("Pedidos processados com sucesso: %4d%n", qtdSucesso);
		sb.append(msg);

		msg = String.format("Pedidos processados com erro   : %4d%n", qtdErro);
		sb.append(msg);

		return sb.toString();
	}

}
