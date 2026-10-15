package tech.skala.action.integration.traycorp;

import tech.skala.core.beans.interfaces.EntityBean;

public class TrayCorpInformacoesAdicionais implements EntityBean {
	private String							chave;
	private String							valor;
	public String getChave() {
		return chave;
	}
	public void setChave(String chave) {
		this.chave = chave;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	@Override
	public String toString() {
		return String.format("TrayCorpInformacoesAdicionais [chave=%s, valor=%s]", chave, valor);
	}
	
	
	
}
