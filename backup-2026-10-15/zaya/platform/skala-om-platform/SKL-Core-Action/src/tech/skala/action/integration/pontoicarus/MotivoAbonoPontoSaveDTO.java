package tech.skala.action.integration.pontoicarus;

import tech.skala.core.commons.dd.DTO;

public class MotivoAbonoPontoSaveDTO implements DTO {
	private String								descricao;
	private String								tipoAbono;
	private String								obrigatorioAnexo = "NAO";
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getTipoAbono() {
		return tipoAbono;
	}
	public void setTipoAbono(String tipoAbono) {
		this.tipoAbono = tipoAbono;
	}	
	public String getObrigatorioAnexo() {
		return obrigatorioAnexo;
	}
	public void setObrigatorioAnexo(String obrigatorioAnexo) {
		this.obrigatorioAnexo = obrigatorioAnexo;
	}
	
	@Override
	public String toString() {
		return String.format("MotivoAbonoPontoSaveDTO [descricao=%s, tipoAbono=%s, obrigatorioAnexo=%s]", descricao,
				tipoAbono, obrigatorioAnexo);
	}

	
}
