package tech.skala.action.integration.traycorp;

import tech.skala.core.beans.interfaces.EntityBean;

public class TrayObservacao implements EntityBean {
	private String							observacao;
	private String							usuario;
	private String							data;
	private boolean							publica;
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public boolean isPublica() {
		return publica;
	}
	public void setPublica(boolean publica) {
		this.publica = publica;
	}
	@Override
	public String toString() {
		return String.format("TrayObservacao [observacao=%s, usuario=%s, data=%s, publica=%s]", observacao, usuario,
				data, publica);
	}

}
