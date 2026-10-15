package tech.skala.action.integration.traycorp;

import java.util.ArrayList;
import java.util.Collection;

import tech.skala.core.beans.interfaces.EntityBean;

public class TrayFrete implements EntityBean {
	private long							freteContratoId;
	private String							freteContrato;
	private String							referenciaConector;
	private double							valorFreteEmpresa;
	private double							valorFreteCliente;
	private double							peso;
	private double							pesoCobrado;
	private double							volume;
	private double							volumeCobrado;
	private long							prazoEnvio;
	private String							prazoEnvioTexto;
	private long							retiradaLojaId;
	private Collection<TrayFreteCentroDistribuicao>	centrosDistribuicao = new ArrayList<>();
	private String							servico;
	private String							retiradaAgendada;
	private String							agendamento;
	private Collection<TrayCorpInformacoesAdicionais> informacoesAdicionais;

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

	public String getReferenciaConector() {
		return referenciaConector;
	}

	public void setReferenciaConector(String referenciaConector) {
		this.referenciaConector = referenciaConector;
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

	public long getRetiradaLojaId() {
		return retiradaLojaId;
	}

	public void setRetiradaLojaId(long retiradaLojaId) {
		this.retiradaLojaId = retiradaLojaId;
	}

	public Collection<TrayFreteCentroDistribuicao> getCentrosDistribuicao() {
		return centrosDistribuicao;
	}

	public void setCentrosDistribuicao(Collection<TrayFreteCentroDistribuicao> centrosDistribuicao) {
		this.centrosDistribuicao = centrosDistribuicao;
	}

	public String getServico() {
		return servico;
	}

	public void setServico(String servico) {
		this.servico = servico;
	}

	public String getRetiradaAgendada() {
		return retiradaAgendada;
	}

	public void setRetiradaAgendada(String retiradaAgendada) {
		this.retiradaAgendada = retiradaAgendada;
	}

	public String getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(String agendamento) {
		this.agendamento = agendamento;
	}

	public Collection<TrayCorpInformacoesAdicionais> getInformacoesAdicionais() {
		return informacoesAdicionais;
	}

	public void setInformacoesAdicionais(Collection<TrayCorpInformacoesAdicionais> informacoesAdicionais) {
		this.informacoesAdicionais = informacoesAdicionais;
	}

	@Override
	public String toString() {
		return String.format(
				"TrayFrete [freteContratoId=%s, freteContrato=%s, referenciaConector=%s, valorFreteEmpresa=%s, valorFreteCliente=%s, peso=%s, pesoCobrado=%s, volume=%s, volumeCobrado=%s, prazoEnvio=%s, prazoEnvioTexto=%s, retiradaLojaId=%s, centrosDistribuicao=%s, servico=%s, retiradaAgendada=%s, agendamento=%s, informacoesAdicionais=%s]",
				freteContratoId, freteContrato, referenciaConector, valorFreteEmpresa, valorFreteCliente, peso,
				pesoCobrado, volume, volumeCobrado, prazoEnvio, prazoEnvioTexto, retiradaLojaId, centrosDistribuicao,
				servico, retiradaAgendada, agendamento, informacoesAdicionais);
	}

}
