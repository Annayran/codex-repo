package tech.skala.action.integration.transport.edi;

import java.util.ArrayList;
import java.util.List;

public class OcorrenciaEDI {
	
	private CabecalhoDeIntercambio 				cabecalhoDeIntercambio;
	private CabecalhoDeDocumento 				cabecalhoDeDocumento;
	private DadosDaTransportadora				dadosDaTransportadora;
	private List<OcorrenciaNaEntrega>			ocorrencias = new ArrayList<>();
	private List<OcorrenciaCT>					ocorrenciasCT =  new ArrayList<>();

	public CabecalhoDeIntercambio getCabecalhoDeIntercambio() {
		return cabecalhoDeIntercambio;
	}

	public void setCabecalhoDeIntercambio(CabecalhoDeIntercambio cabecalhoDeIntercambio) {
		this.cabecalhoDeIntercambio = cabecalhoDeIntercambio;
	}

	public CabecalhoDeDocumento getCabecalhoDeDocumento() {
		return cabecalhoDeDocumento;
	}

	public void setCabecalhoDeDocumento(CabecalhoDeDocumento cabecalhoDeDocumento) {
		this.cabecalhoDeDocumento = cabecalhoDeDocumento;
	}

	public DadosDaTransportadora getDadosDaTransportadora() {
		return dadosDaTransportadora;
	}

	public void setDadosDaTransportadora(DadosDaTransportadora dadosDaTransportadora) {
		this.dadosDaTransportadora = dadosDaTransportadora;
	}

	public List<OcorrenciaNaEntrega> getOcorrencias() {
		return ocorrencias;
	}

	public void setOcorrencias(List<OcorrenciaNaEntrega> ocorrencias) {
		this.ocorrencias = ocorrencias;
	}

	public List<OcorrenciaCT> getOcorrenciasCT() {
		return ocorrenciasCT;
	}

	public void setOcorrenciasCT(List<OcorrenciaCT> ocorrenciasCT) {
		this.ocorrenciasCT = ocorrenciasCT;
	}

	public void loadByEDI(String linha) {
		String identificadorRegistro = linha.substring(0, 3);
		
		if(identificadorRegistro.equals("000")) {
			cabecalhoDeIntercambio = new CabecalhoDeIntercambio();
			cabecalhoDeIntercambio.loadByEDI(linha);
		}
		
		if(identificadorRegistro.equals("340")) {
			cabecalhoDeDocumento = new CabecalhoDeDocumento();
			cabecalhoDeDocumento.loadByEDI(linha);
		}
		
		if(identificadorRegistro.equals("341")) {
			dadosDaTransportadora = new DadosDaTransportadora();
			dadosDaTransportadora.loadByEDI(linha);
		}
		
		if(identificadorRegistro.equals("342")) {
			OcorrenciaNaEntrega ocorrenciaNaEntrega = new OcorrenciaNaEntrega();
			ocorrenciaNaEntrega.loadByEDI(linha);
			
			ocorrencias.add(ocorrenciaNaEntrega);
		}
		
		if(identificadorRegistro.equals("343")) {
			OcorrenciaCT ocorrenciaCT = new OcorrenciaCT();
			ocorrenciaCT.loadByEDI(linha);
			
			ocorrenciasCT.add(ocorrenciaCT);
		}
	}
	
	@Override
	public String toString() {
		return String.format(
				"OcorrenciaEDI [cabecalhoDeIntercambio=%s, cabecalhoDeDocumento=%s, dadosDaTransportadora=%s, ocorrencias=%s, ocorrenciasCT=%s]",
				cabecalhoDeIntercambio, cabecalhoDeDocumento, dadosDaTransportadora, ocorrencias, ocorrenciasCT);
	}
	
	
}
