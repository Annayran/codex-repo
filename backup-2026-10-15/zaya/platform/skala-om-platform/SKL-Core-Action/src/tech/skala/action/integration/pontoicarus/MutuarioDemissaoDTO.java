package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;
import java.sql.Timestamp;

import tech.skala.core.commons.dd.DTO;

public class MutuarioDemissaoDTO implements DTO {
	private Timestamp							dtDesligamento;
	private BigDecimal							idMutuario;
	private String								motivoDesligamento;
	private String								suspenderAcessos;
	private String								tipoTurnover;
	
	public Timestamp getDtDesligamento() {
		return dtDesligamento;
	}
	public void setDtDesligamento(Timestamp dtDesligamento) {
		this.dtDesligamento = dtDesligamento;
	}
	public BigDecimal getIdMutuario() {
		return idMutuario;
	}
	public void setIdMutuario(BigDecimal idMutuario) {
		this.idMutuario = idMutuario;
	}
	public String getMotivoDesligamento() {
		return motivoDesligamento;
	}
	public void setMotivoDesligamento(String motivoDesligamento) {
		this.motivoDesligamento = motivoDesligamento;
	}
	public String getSuspenderAcessos() {
		return suspenderAcessos;
	}
	public void setSuspenderAcessos(String suspenderAcessos) {
		this.suspenderAcessos = suspenderAcessos;
	}
	public String getTipoTurnover() {
		return tipoTurnover;
	}
	public void setTipoTurnover(String tipoTurnover) {
		this.tipoTurnover = tipoTurnover;
	}
	
	@Override
	public String toString() {
		return String.format(
				"MutuarioDemissaoDTO [dtDesligamento=%s, idMutuario=%s, motivoDesligamento=%s, suspenderAcessos=%s, tipoTurnover=%s]",
				dtDesligamento, idMutuario, motivoDesligamento, suspenderAcessos, tipoTurnover);
	}
}
