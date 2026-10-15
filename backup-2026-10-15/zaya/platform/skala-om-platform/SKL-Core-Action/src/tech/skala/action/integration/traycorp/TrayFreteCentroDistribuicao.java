package tech.skala.action.integration.traycorp;

import tech.skala.core.beans.interfaces.EntityBean;

public class TrayFreteCentroDistribuicao implements EntityBean {
	private long							freteContratoId;
	private String							freteContrato;
	private double							valorFreteEmpresa;
	private double							valorFreteCliente;
	private double							peso;
	private double							pesoCobrado;
	private double							volume;
	private double							volumeCobrado;
	private long							prazoEnvio;
	private String							prazoEnvioTexto;
	private long							centroDistribuicaoId;

	public long getFreteContratoId() {
		return freteContratoId;
	}

	public void setFreteContratoId(long freteContratoId) {
		this.freteContratoId = freteContratoId;
	}

	public String getFreteContrato() {
		return freteContrato;
	}

	public void setFreteContrato(String freteContrato) {
		this.freteContrato = freteContrato;
	}

	public double getValorFreteEmpresa() {
		return valorFreteEmpresa;
	}

	public void setValorFreteEmpresa(double valorFreteEmpresa) {
		this.valorFreteEmpresa = valorFreteEmpresa;
	}

	public double getValorFreteCliente() {
		return valorFreteCliente;
	}

	public void setValorFreteCliente(double valorFreteCliente) {
		this.valorFreteCliente = valorFreteCliente;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public double getPesoCobrado() {
		return pesoCobrado;
	}

	public void setPesoCobrado(double pesoCobrado) {
		this.pesoCobrado = pesoCobrado;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getVolumeCobrado() {
		return volumeCobrado;
	}

	public void setVolumeCobrado(double volumeCobrado) {
		this.volumeCobrado = volumeCobrado;
	}

	public long getPrazoEnvio() {
		return prazoEnvio;
	}

	public void setPrazoEnvio(long prazoEnvio) {
		this.prazoEnvio = prazoEnvio;
	}

	public String getPrazoEnvioTexto() {
		return prazoEnvioTexto;
	}

	public void setPrazoEnvioTexto(String prazoEnvioTexto) {
		this.prazoEnvioTexto = prazoEnvioTexto;
	}

	public long getCentroDistribuicaoId() {
		return centroDistribuicaoId;
	}

	public void setCentroDistribuicaoId(long centroDistribuicaoId) {
		this.centroDistribuicaoId = centroDistribuicaoId;
	}

	@Override
	public String toString() {
		return String.format(
				"TrayFreteCentroDistribuicao [freteContratoId=%s, freteContrato=%s, valorFreteEmpresa=%s, valorFreteCliente=%s, peso=%s, pesoCobrado=%s, volume=%s, volumeCobrado=%s, prazoEnvio=%s, prazoEnvioTexto=%s, centroDistribuicaoId=%s]",
				freteContratoId, freteContrato, valorFreteEmpresa, valorFreteCliente, peso, pesoCobrado, volume,
				volumeCobrado, prazoEnvio, prazoEnvioTexto, centroDistribuicaoId);
	}

}
