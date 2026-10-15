package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import tech.skala.action.beans.registroocorrencia.RegistroOcorrencia;

public class FuncionarioAjuste {
	private FuncionarioIcarus							funcionario;
	private Timestamp									dataOcorrencia;
	private List<RegistroOcorrencia>					ocorrencias;
	private PontoHorarioBatidasDTO						batidas;
	
	public FuncionarioIcarus getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(FuncionarioIcarus funcionario) {
		this.funcionario = funcionario;
	}
	public Timestamp getDataOcorrencia() {
		return dataOcorrencia;
	}
	public void setDataOcorrencia(Timestamp dataOcorrencia) {
		this.dataOcorrencia = dataOcorrencia;
	}
	public List<RegistroOcorrencia> getOcorrencias() {
		return ocorrencias;
	}
	public void setOcorrencias(List<RegistroOcorrencia> ocorrencias) {
		this.ocorrencias = ocorrencias;
	}
	public BigDecimal getIdMutuario() {
		return this.funcionario.getIdMutuario();
	}
	public PontoHorarioBatidasDTO getBatidas() {
		return batidas;
	}
	public void setBatidas(PontoHorarioBatidasDTO batidas) {
		this.batidas = batidas;
	}
	
	@Override
	public String toString() {
		return String.format("FuncionarioAjuste [funcionario=%s, dataOcorrencia=%s, ocorrencias=%s, batidas=%s]",
				funcionario, dataOcorrencia, ocorrencias, batidas);
	}

}
