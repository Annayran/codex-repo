package tech.skala.action.integration.transport.edi;

import java.math.BigDecimal;

import tech.skala.core.beans.interfaces.EntityBean;

public class OcorrenciaCT implements EntityBean {
	
	private BigDecimal								identificador;
	private String									cgcEmpresaContratante;
	private String									filialEmissoraCT;
	private String									serieCT;
	private String									numeroCT;
	
	public BigDecimal getIdentificador() {
		return identificador;
	}

	public void setIdentificador(BigDecimal identificador) {
		this.identificador = identificador;
	}

	public String getCgcEmpresaContratante() {
		return cgcEmpresaContratante;
	}

	public void setCgcEmpresaContratante(String cgcEmpresaContratante) {
		this.cgcEmpresaContratante = cgcEmpresaContratante;
	}

	public String getFilialEmissoraCT() {
		return filialEmissoraCT;
	}

	public void setFilialEmissoraCT(String filialEmissoraCT) {
		this.filialEmissoraCT = filialEmissoraCT;
	}

	public String getSerieCT() {
		return serieCT;
	}

	public void setSerieCT(String serieCT) {
		this.serieCT = serieCT;
	}

	public String getNumeroCT() {
		return numeroCT;
	}

	public void setNumeroCT(String numeroCT) {
		this.numeroCT = numeroCT;
	}
	
	public void loadByEDI (String linha) {
		identificador = new BigDecimal(linha.substring(0, 3));
		cgcEmpresaContratante = linha.substring(3, 17);
		filialEmissoraCT = linha.substring(17, 27);
		serieCT = linha.substring(27, 32);
		numeroCT =linha.substring(32, 44);
		
	}

	@Override
	public String toString() {
		return String.format(
				"OcorrenciaCT [identificador=%s, cgcEmpresaContratante=%s, filialEmissoraCT=%s, serieCT=%s, numeroCT=%s]",
				identificador, cgcEmpresaContratante, filialEmissoraCT, serieCT, numeroCT);
	}
	
	
}
