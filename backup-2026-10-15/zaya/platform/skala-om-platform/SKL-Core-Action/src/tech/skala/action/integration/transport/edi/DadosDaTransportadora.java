package tech.skala.action.integration.transport.edi;

import java.math.BigDecimal;

import tech.skala.core.beans.interfaces.EntityBean;

public class DadosDaTransportadora implements EntityBean {

	private BigDecimal								identificador;
	private String									cgc;
	private String									razaoSocial;
	
	public BigDecimal getIdentificador() {
		return identificador;
	}

	public void setIdentificador(BigDecimal identificador) {
		this.identificador = identificador;
	}

	public String getCgc() {
		return cgc;
	}

	public void setCgc(String cgc) {
		this.cgc = cgc;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	
	public void loadByEDI (String linha) {
		identificador = new BigDecimal(linha.substring(0, 3));
		cgc = linha.substring(3, 17);
		razaoSocial = linha.substring(17, 57);
	}
	
	@Override
	public String toString() {
		return String.format("DadosDaTransportadora [identificador=%s, cgc=%s, razaoSocial=%s]", identificador, cgc,
				razaoSocial);
	}

}
