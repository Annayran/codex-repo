package tech.skala.modules.logistica.ocorrencias;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import com.sankhya.util.JdbcUtils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.modules.logistica.callcenter.CallCenter;
import tech.skala.modules.logistica.callcenter.CallCenterProcessor;
import tech.skala.modules.logistica.callcenter.OcorrenciaCallCenter;
import tech.skala.modules.logistica.commons.OcorrenciaTransportadoraFinder;
import tech.skala.modules.logistica.hub.HubTransport;
import tech.skala.modules.logistica.hub.HubTransportOccurrence;
import tech.skala.modules.vendas.CabecalhoNotaDAO;

public class LinhaPlanilhaOcorrencia {

	private String 							chaveCte;
	private String 							chaveNFe;
	private BigDecimal 						codOco;
	private Timestamp 						dhOcorrencia;
	private String 							observacao;
	
	public String getChaveCte() {
		return chaveCte;
	}

	public void setChaveCte(String chaveCte) {
		this.chaveCte = chaveCte;
	}

	public String getChaveNFe() {
		return chaveNFe;
	}

	public void setChaveNFe(String chaveNFe) {
		this.chaveNFe = chaveNFe;
	}

	public BigDecimal getCodOco() {
		return codOco;
	}

	public void setCodOco(BigDecimal codOco) {
		this.codOco = codOco;
	}

	public Timestamp getDhOcorrencia() {
		return dhOcorrencia;
	}

	public void setDhOcorrencia(Timestamp dhOcorrencia) {
		this.dhOcorrencia = dhOcorrencia;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public void loadByPlanilha(Map<String, List<LinhaPlanilhaOcorrencia>> linhasMap) throws Exception {
		processaPlanilha(linhasMap);
	}
	
	private void processaPlanilha(Map<String, List<LinhaPlanilhaOcorrencia>> linhasMap) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					
					for (Map.Entry<String, List<LinhaPlanilhaOcorrencia>> entry : linhasMap.entrySet())
						processarPlanilha(entry);
					
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível processar Planilha.", e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	protected void processarPlanilha(Entry<String, List<LinhaPlanilhaOcorrencia>> entry) throws SkalaModelException {
		String chaveCTe = entry.getKey();
		List<LinhaPlanilhaOcorrencia> linhas = entry.getValue();

		ResultSet rsChavesNFe = null;
		try {
			rsChavesNFe = CallCenterProcessor.getChavesNFe(chaveCTe);

			DynamicVO nfeVO = null;
			String chaveNfe;
			CallCenter callCenter = null;
			while (rsChavesNFe.next()) {
				chaveNfe = rsChavesNFe.getString("CHAVENFE");
				nfeVO = CabecalhoNotaDAO.getCabecalhoFromChaveNFe(chaveNfe);

				buildHubTransport(linhas, nfeVO);

				callCenter = CallCenterProcessor.buscaCallCenterNoSistema(nfeVO);
			}
			
			geraOcorrenciaCallCenter(callCenter, linhas);

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível gerar Ocorrência.", e);
		} finally {
			JdbcUtils.closeResultSet(rsChavesNFe);
		}

	}

	private void geraOcorrenciaCallCenter(CallCenter callCenter, List<LinhaPlanilhaOcorrencia> linhas)
			throws SkalaModelException {
		OcorrenciaCallCenter ocorrencia;

		BigDecimal codOcoPlatina = null;
		BigDecimal codOcoTransp = null;
		for (LinhaPlanilhaOcorrencia linha : linhas) {
			ocorrencia = new OcorrenciaCallCenter();

			ocorrencia.setCodCal(callCenter.getCodCal());

			codOcoTransp = linha.getCodOco();
			codOcoPlatina = OcorrenciaTransportadoraFinder.buscaOcorrenciaPlatina(codOcoTransp, BigDecimal.ZERO);

			ocorrencia.setCodOco(codOcoPlatina);
			ocorrencia.setDhOcorrencia(linha.getDhOcorrencia());

			if (!callCenter.getOcorrencias().contains(ocorrencia)) {
				ocorrencia.setObservacao(linha.getObservacao());
				ocorrencia.persists();

				callCenter.getOcorrencias().add(ocorrencia);
			}

		}
	}

	protected HubTransport buildHubTransport(List<LinhaPlanilhaOcorrencia> linhas, DynamicVO cabVO)
			throws SkalaModelException {

		HubTransport hubTransport = new HubTransport();
		try {
			BigDecimal codEmp = cabVO.asBigDecimal(SklFieldName.CODEMP);
			BigDecimal ordemCarga = cabVO.asBigDecimal(SklFieldName.ORDEMCARGA);

			BigDecimal ocPai = cabVO.asBigDecimal("OrdemCarga.AD_ORDEMCARGA");
			BigDecimal ordemCargaPai = (ocPai == null) ? ordemCarga : ocPai;

			BigDecimal nuNota = cabVO.asBigDecimal(SklFieldName.NUNOTA);
			String chaveNFe = cabVO.asString("CHAVENFE");

			JapeWrapper dao = JapeFactory.dao("AD_PLTHUBTRA");

			DynamicVO vo = dao.findByPK(codEmp, ordemCargaPai, nuNota, chaveNFe);

			if (vo == null) {
				hubTransport.setCodEmp(codEmp);
				hubTransport.setNuNota(nuNota);
				hubTransport.setNumNota(cabVO.asBigDecimalOrZero(SklFieldName.NUMNOTA));
				hubTransport.setChaveNfe(chaveNFe);
				hubTransport.setOrdemCarga(ocPai);
				hubTransport.setOrdemCargaFilha(ordemCarga.toString());
				hubTransport.persists();
			} else {
				hubTransport.loadByVO(vo);
			}

			buildListOccurrence(hubTransport, linhas);

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível construir HubTransport", e);
		}

		return hubTransport;
	}

	private void buildListOccurrence(HubTransport hubTransport, List<LinhaPlanilhaOcorrencia> linhas) throws SkalaModelException {
		HubTransportOccurrence occurrence = new HubTransportOccurrence(hubTransport);

		for (LinhaPlanilhaOcorrencia linha : linhas) {
			occurrence.setNuOccurrenceType(linha.getCodOco());
			occurrence.setDhInc(linha.getDhOcorrencia());
			occurrence.setCodEmp(hubTransport.getCodEmp());
			occurrence.setObservacao(linha.getObservacao());
			occurrence.persists();

			hubTransport.getOccurrences().add(occurrence);
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(chaveCte);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof LinhaPlanilhaOcorrencia))
			return false;
		LinhaPlanilhaOcorrencia other = (LinhaPlanilhaOcorrencia) obj;
		return Objects.equals(chaveCte, other.chaveCte);
	}

	@Override
	public String toString() {
		return String.format(
				"LinhaPlanilhaOcorrencia [chaveCte=%s, chaveNFe=%s, codOco=%s, dhOcorrencia=%s, observacao=%s]",
				chaveCte, chaveNFe, codOco, dhOcorrencia, observacao);
	}
	
}
