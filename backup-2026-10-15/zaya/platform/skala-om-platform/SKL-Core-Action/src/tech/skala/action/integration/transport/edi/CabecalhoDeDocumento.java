package tech.skala.action.integration.transport.edi;

import java.math.BigDecimal;

import tech.skala.core.beans.interfaces.EntityBean;

public class CabecalhoDeDocumento implements EntityBean {

	private BigDecimal								identificador;
	private String									documento;

	public BigDecimal getIdentificador() {
		return identificador;
	}

	public void setIdentificador(BigDecimal identificador) {
		this.identificador = identificador;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public void loadByEDI (String linha) {
		identificador = new BigDecimal(linha.substring(0, 3));
		documento = linha.substring(3, 17);
	}
	
	@Override
	public String toString() {
		return String.format("CabecalhoDeDocumento [identificador=%s, documento=%s]", identificador, documento);
	}

}
