package tech.skala.action.integration.pontoicarus;

import java.sql.Timestamp;
import java.util.List;

import tech.skala.core.commons.dd.DTO;

public class AjustePontoSaveDTO implements DTO {
	private List<AjustePontoHorarioDTO> 				ajustePontoHorario;
	private Timestamp									dataSolicitacao;
	private String										justificativa;
	private MutuarioIdDTO								mutuario;
	
	public List<AjustePontoHorarioDTO> getAjustePontoHorario() {
		return ajustePontoHorario;
	}
	public void setAjustePontoHorario(List<AjustePontoHorarioDTO> ajustePontoHorario) {
		this.ajustePontoHorario = ajustePontoHorario;
	}
	public Timestamp getDataSolicitacao() {
		return dataSolicitacao;
	}
	public void setDataSolicitacao(Timestamp dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}
	public String getJustificativa() {
		return justificativa;
	}
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}
	public MutuarioIdDTO getMutuario() {
		return mutuario;
	}
	public void setMutuario(MutuarioIdDTO mutuario) {
		this.mutuario = mutuario;
	}
	
	@Override
	public String toString() {
		return String.format(
				"AjustePontoSaveDTO [ajustePontoHorario=%s, dataSolicitacao=%s, justificativa=%s, mutuario=%s]",
				ajustePontoHorario, dataSolicitacao, justificativa, mutuario);
	}
}
