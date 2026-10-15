package tech.skala.action.integration.pontoicarus;

import java.util.LinkedList;
import java.util.List;

import tech.skala.core.commons.dd.DTO;

public class PontoPesquisaDTO implements DTO {
	private List<MutuarioIdDTO>					mutuarios;
	private List<CentroCustoIdDTO>				centrosCusto;
	private List<LotacaoIdDTO>					lotacoes;
	private List<RegimeContratacaoIdDTO>		regimesContratacao;
	private String								dataInicio;
	private String								dataFim;
	
	public PontoPesquisaDTO() {
		this.mutuarios = new LinkedList<>();
		this.centrosCusto = new LinkedList<>();
		this.lotacoes = new LinkedList<>();
		this.regimesContratacao = new LinkedList<>();
	}
	
	public List<MutuarioIdDTO> getMutuarios() {
		return mutuarios;
	}
	public void setMutuarios(List<MutuarioIdDTO> mutuarios) {
		this.mutuarios = mutuarios;
	}
	public List<CentroCustoIdDTO> getCentrosCusto() {
		return centrosCusto;
	}
	public void setCentrosCusto(List<CentroCustoIdDTO> centrosCusto) {
		this.centrosCusto = centrosCusto;
	}
	public List<LotacaoIdDTO> getLotacoes() {
		return lotacoes;
	}
	public void setLotacoes(List<LotacaoIdDTO> lotacoes) {
		this.lotacoes = lotacoes;
	}
	public List<RegimeContratacaoIdDTO> getRegimesContratacao() {
		return regimesContratacao;
	}
	public void setRegimesContratacao(List<RegimeContratacaoIdDTO> regimesContratacao) {
		this.regimesContratacao = regimesContratacao;
	}
	public String getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}
	public String getDataFim() {
		return dataFim;
	}
	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}
	
	@Override
	public String toString() {
		return String.format(
				"PontoPesquisaDTO [mutuarios=%s, centrosCusto=%s, lotacoes=%s, regimesContratacao=%s, dataInicio=%s, dataFim=%s]",
				mutuarios, centrosCusto, lotacoes, regimesContratacao, dataInicio, dataFim);
	}

}
