package tech.skala.action.integration.traycorp;

import tech.skala.core.beans.interfaces.EntityBean;

public class TrayPagamentoBoleto implements EntityBean {

	private String							urlBoleto;
	private String							codigoDeBarras;

	public String getUrlBoleto() {
		return urlBoleto;
	}

	public void setUrlBoleto(String urlBoleto) {
		this.urlBoleto = urlBoleto;
	}

	public String getCodigoDeBarras() {
		return codigoDeBarras;
	}

	public void setCodigoDeBarras(String codigoDeBarras) {
		this.codigoDeBarras = codigoDeBarras;
	}

	@Override
	public String toString() {
		return String.format("TrayPagamentoBoleto [urlBoleto=%s, codigoDeBarras=%s]", urlBoleto, codigoDeBarras);
	}

}
