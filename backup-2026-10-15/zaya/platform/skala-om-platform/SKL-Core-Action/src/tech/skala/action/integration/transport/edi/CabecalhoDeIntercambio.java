package tech.skala.action.integration.transport.edi;

import java.math.BigDecimal;

import tech.skala.core.beans.interfaces.EntityBean;

public class CabecalhoDeIntercambio implements EntityBean {
	
	private BigDecimal								identificador;
	private String									rementente;
	private String									destinatario;
	private String									data;
	private String									hora;
	private String									intercambio;

	public BigDecimal getIdentificador() {
		return identificador;
	}

	public void setIdentificador(BigDecimal identificador) {
		this.identificador = identificador;
	}

	public String getRementente() {
		return rementente;
	}

	public void setRementente(String rementente) {
		this.rementente = rementente;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getIntercambio() {
		return intercambio;
	}

	public void setIntercambio(String intercambio) {
		this.intercambio = intercambio;
	}

	public void loadByEDI (String linha) {
		identificador = new BigDecimal(linha.substring(0, 3));
		rementente = linha.substring(3, 38);
		destinatario = linha.substring(38, 73);
		data = linha.substring(73, 79);
		hora = linha.substring(79, 83);
		intercambio = linha.substring(83, 95);
	}
	
	@Override
	public String toString() {
		return String.format(
				"CabecalhoDeIntercambio [identificador=%s, rementente=%s, destinatario=%s, data=%s, hora=%s, intercambio=%s]",
				identificador, rementente, destinatario, data, hora, intercambio);
	}
	
	
	
}
