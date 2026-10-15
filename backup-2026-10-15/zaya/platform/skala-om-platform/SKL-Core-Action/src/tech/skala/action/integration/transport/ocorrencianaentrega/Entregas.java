package tech.skala.action.integration.transport.ocorrencianaentrega;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;

/**
 * A classe {@code Entregas} representa a tabela {@code AD_PLTGFENT}.
 * 
 * @author hiagoalexandre
 * @since 2023-03-10
 */
public class Entregas implements EntityBean, Persistable, LoadableByValueObject {

	private static final String					ENTITY_NAME = "AD_PLTGFENT";
	
	private	BigDecimal 							nuNota;
	private	BigDecimal 							codEmp;
	private	BigDecimal 							ordemCarga;
	private	Timestamp 							dtLimiteEntrega;
	private BigDecimal							codParc;
	private BigDecimal							numNota;
	
	private List<ConhecimentoDeTransporte>		conhecimentos = new LinkedList<>();
	private List<OcorrenciaDeEntrega>			ocorrencias = new LinkedList<>();
	
	public BigDecimal getNuNota() {
		return nuNota;
	}

	public void setNuNota(BigDecimal nuNota) {
		this.nuNota = nuNota;
	}

	public BigDecimal getCodEmp() {
		return codEmp;
	}

	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}

	public BigDecimal getOrdemCarga() {
		return ordemCarga;
	}

	public void setOrdemCarga(BigDecimal ordemCarga) {
		this.ordemCarga = ordemCarga;
	}

	public Timestamp getDtLimiteEntrega() {
		return dtLimiteEntrega;
	}

	public void setDtLimiteEntrega(Timestamp dtLimiteEntrega) {
		this.dtLimiteEntrega = dtLimiteEntrega;
	}

	public List<ConhecimentoDeTransporte> getConhecimentos() {
		return conhecimentos;
	}

	public void setConhecimentos(List<ConhecimentoDeTransporte> conhecimentos) {
		this.conhecimentos = conhecimentos;
	}

	public List<OcorrenciaDeEntrega> getOcorrencias() {
		return ocorrencias;
	}

	public void setOcorrencias(List<OcorrenciaDeEntrega> ocorrencias) {
		this.ocorrencias = ocorrencias;
	}

	public BigDecimal getCodParc() {
		return codParc;
	}

	public void setCodParc(BigDecimal codParc) {
		this.codParc = codParc;
	}

	public BigDecimal getNumNota() {
		return numNota;
	}

	public void setNumNota(BigDecimal numNota) {
		this.numNota = numNota;
	}

	public static String getEntityName() {
		return ENTITY_NAME;
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {

		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);

		try {
			DynamicVO vo = null;

			if (nuNota != null)
				vo = dao.findByPK(nuNota);

			if (vo == null) {
				EntityInsert entityNew = new EntityInsert(ENTITY_NAME);
				entityNew.setField(SklFieldName.NUNOTA, nuNota);
				entityNew.setField(SklFieldName.CODEMP, codEmp);
				entityNew.setField(SklFieldName.ORDEMCARGA, ordemCarga);
				entityNew.setField("DTLIMITEENTREGA", dtLimiteEntrega);
				entityNew.setField(SklFieldName.NUMNOTA, numNota);
				entityNew.setField(SklFieldName.CODPARC, codParc);
				entityNew.setField("DTLIMITEENTREGA", dtLimiteEntrega);
				
				entityNew.fluidInsert();
			}

		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}

		return null;
	}
	
	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		try {
			this.nuNota = vo.asBigDecimal(SklFieldName.NUNOTA);
			this.codParc = vo.asBigDecimal(SklFieldName.CODPARC);
			this.numNota = vo.asBigDecimal(SklFieldName.NUMNOTA);
			this.codEmp = vo.asBigDecimal(SklFieldName.CODEMP);
			this.ordemCarga = vo.asBigDecimal(SklFieldName.ORDEMCARGA);
			this.dtLimiteEntrega = vo.asTimestamp("DTLIMITEENTREGA");

			loadConhecimentosTransportes();
			loadOcorrenciasEntrega();
		} catch (Exception e) {
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		}
	}

	private void loadOcorrenciasEntrega() throws Exception {
		JapeWrapper dao = JapeFactory.dao(OcorrenciaDeEntrega.ENTITY_NAME);
		
		Collection<DynamicVO> ocorrenciasVOs = dao.find("this.NUNOTA = ?", nuNota);
		
		OcorrenciaDeEntrega ocorrencia;
		for(DynamicVO vo : ocorrenciasVOs) {
			ocorrencia = new OcorrenciaDeEntrega();
			ocorrencia.loadByVO(vo);
			
			ocorrencias.add(ocorrencia);
		}
	}

	private void loadConhecimentosTransportes() throws Exception {
		JapeWrapper dao = JapeFactory.dao(ConhecimentoDeTransporte.ENTITY_NAME);
		
		Collection<DynamicVO> cteVOs = dao.find("this.NUNOTA = ?", nuNota);
		
		ConhecimentoDeTransporte cte;
		for(DynamicVO vo : cteVOs) {
			cte = new ConhecimentoDeTransporte();
			cte.loadByVO(vo);
			
			conhecimentos.add(cte);
		}
		
	}

	@Override
	public String toString() {
		return String.format(
				"Entregas [nuNota=%s, codEmp=%s, ordemCarga=%s, dtLimiteEntrega=%s, codParc=%s, numNota=%s]", nuNota,
				codEmp, ordemCarga, dtLimiteEntrega, codParc, numNota);
	}
	
}
