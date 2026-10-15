package tech.skala.modules.logistica.callcenter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByPrimaryKey;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;

/**
 * A classe {@code CallCenter} representa a tabela {@code AD_TMSCAL}.
 * 
 * @author hiagoalexandre
 * @since 2022-11-24
 */
public class CallCenter implements EntityBean, Persistable, LoadableByPrimaryKey, LoadableByValueObject {
	public static final String						ENTITY_NAME = SklEntityName.CALL_CENTER;
	
	private	BigDecimal 								codCal;
	private	String 									chaveCte;
	private	BigDecimal 								numCte;
	private	String 									serieCte;
	private	BigDecimal 								codParcCliente;
	private	BigDecimal 								codParcTransp;
	private	Timestamp 								dtLimiteEntrega;
	private	Timestamp 								dtEntrega;
	private	BigDecimal 								qtdReagendas;
	private	Timestamp 								dtEntCanhoto;
	private	Timestamp 								dtDevTotal;
	private	Timestamp 								dtChegParceiro;
	private	Timestamp 								dhAgendamento;
	private	Timestamp  								dhInclusao;
	
	private List<OcorrenciaCallCenter>				ocorrencias = new LinkedList<>();
	private List<NotaCallCenter>					notas = new LinkedList<>();
	
	public BigDecimal getCodCal() {
		return codCal;
	}

	public void setCodCal(BigDecimal codCal) {
		this.codCal = codCal;
	}

	public String getChaveCte() {
		return chaveCte;
	}

	public void setChaveCte(String chaveCte) {
		this.chaveCte = chaveCte;
	}

	public BigDecimal getNumCte() {
		return numCte;
	}

	public void setNumCte(BigDecimal numCte) {
		this.numCte = numCte;
	}

	public String getSerieCte() {
		return serieCte;
	}

	public void setSerieCte(String serieCte) {
		this.serieCte = serieCte;
	}

	public BigDecimal getCodParcCliente() {
		return codParcCliente;
	}

	public void setCodParcCliente(BigDecimal codParcCliente) {
		this.codParcCliente = codParcCliente;
	}

	public BigDecimal getCodParcTransp() {
		return codParcTransp;
	}

	public void setCodParcTransp(BigDecimal codParcTransp) {
		this.codParcTransp = codParcTransp;
	}

	public Timestamp getDtLimiteEntrega() {
		return dtLimiteEntrega;
	}

	public void setDtLimiteEntrega(Timestamp dtLimiteEntrega) {
		this.dtLimiteEntrega = dtLimiteEntrega;
	}

	public Timestamp getDtEntrega() {
		return dtEntrega;
	}

	public void setDtEntrega(Timestamp dtEntrega) {
		this.dtEntrega = dtEntrega;
	}

	public BigDecimal getQtdReagendas() {
		return qtdReagendas;
	}

	public void setQtdReagendas(BigDecimal qtdReagendas) {
		this.qtdReagendas = qtdReagendas;
	}

	public Timestamp getDtEntCanhoto() {
		return dtEntCanhoto;
	}

	public void setDtEntCanhoto(Timestamp dtEntCanhoto) {
		this.dtEntCanhoto = dtEntCanhoto;
	}

	public Timestamp getDtDevTotal() {
		return dtDevTotal;
	}

	public void setDtDevTotal(Timestamp dtDevTotal) {
		this.dtDevTotal = dtDevTotal;
	}

	public Timestamp getDtChegParceiro() {
		return dtChegParceiro;
	}

	public void setDtChegParceiro(Timestamp dtChegParceiro) {
		this.dtChegParceiro = dtChegParceiro;
	}

	public Timestamp getDhAgendamento() {
		return dhAgendamento;
	}

	public void setDhAgendamento(Timestamp dhAgendamento) {
		this.dhAgendamento = dhAgendamento;
	}
	
	public Timestamp getDhInclusao() {
		return dhInclusao;
	}

	public void setDhInclusao(Timestamp dhInclusao) {
		this.dhInclusao = dhInclusao;
	}

	public List<OcorrenciaCallCenter> getOcorrencias() {
		return ocorrencias;
	}

	public void setOcorrencias(List<OcorrenciaCallCenter> ocorrencias) {
		this.ocorrencias = ocorrencias;
	}

	public List<NotaCallCenter> getNotas() {
		return notas;
	}

	public void setNotas(List<NotaCallCenter> notas) {
		this.notas = notas;
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);

		try {
			DynamicVO vo = null;

			if (codCal != null) {
				vo = dao.findByPK(codCal);
			}

			if (vo == null) {
				EntityInsert entityNew = new EntityInsert(ENTITY_NAME);
				entityNew.setField("CHAVECTE", chaveCte);
				entityNew.setField("NUMCTE", numCte);
				entityNew.setField("SERIECTE", serieCte);
				entityNew.setField("CODPARCCLIENTE", codParcCliente);
				entityNew.setField("CODPARCTRANSP", codParcTransp);
				entityNew.setField("DTLIMITEENTREGA", dtLimiteEntrega);
				entityNew.setField("DTENTREGA", dtEntrega);
				entityNew.setField("QTDREAGENDAS", qtdReagendas);
				entityNew.setField("DTENTCANHOTO", dtEntCanhoto);
				entityNew.setField("DTDEVTOTAL", dtDevTotal);
				entityNew.setField("DTCHEGPARCEIRO", dtChegParceiro);
				entityNew.setField("DHAGENDAMENTO", dhAgendamento);

				DynamicVO newVO = entityNew.fluidInsert();

				this.codCal = newVO.asBigDecimal("CODCAL");
				this.dhInclusao = newVO.asTimestamp("DHINCLUSAO");

			} else {
				EntityUpdate entityUpd = new EntityUpdate(ENTITY_NAME, vo);
				entityUpd.setField("CHAVECTE", chaveCte);
				entityUpd.setField("NUMCTE", numCte);
				entityUpd.setField("SERIECTE", serieCte);
				entityUpd.setField("CODPARCCLIENTE", codParcCliente);
				entityUpd.setField("CODPARCTRANSP", codParcTransp);
				entityUpd.setField("DTLIMITEENTREGA", dtLimiteEntrega);
				entityUpd.setField("DTENTREGA", dtEntrega);
				entityUpd.setField("QTDREAGENDAS", qtdReagendas);
				entityUpd.setField("DTENTCANHOTO", dtEntCanhoto);
				entityUpd.setField("DTDEVTOTAL", dtDevTotal);
				entityUpd.setField("DTCHEGPARCEIRO", dtChegParceiro);
				entityUpd.setField("DHAGENDAMENTO", dhAgendamento);

				entityUpd.fluidUpdate();
			}

		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}

		return null;
	}
	
	@Override
	public void loadByPk(Object... keyValue) throws RecordNotFoundException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);
		try {
			DynamicVO vo = dao.findByPK(keyValue);
			loadByVO(vo);
		} catch (Exception e) {
			throw new RecordNotFoundException(e);
		}
	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.codCal = vo.asBigDecimal("CODCAL");
		this.chaveCte = vo.asString("CHAVECTE");
		this.numCte = vo.asBigDecimal("NUMCTE");
		this.serieCte = vo.asString("SERIECTE");
		this.codParcCliente = vo.asBigDecimal("CODPARCCLIENTE");
		this.codParcTransp = vo.asBigDecimal("CODPARCTRANSP");
		this.dtLimiteEntrega = vo.asTimestamp("DTLIMITEENTREGA");
		this.dtEntrega = vo.asTimestamp("DTENTREGA");
		this.qtdReagendas = vo.asBigDecimal("QTDREAGENDAS");
		this.dtEntCanhoto = vo.asTimestamp("DTENTCANHOTO");
		this.dtDevTotal = vo.asTimestamp("DTDEVTOTAL");
		this.dtChegParceiro = vo.asTimestamp("DTCHEGPARCEIRO");
		this.dhAgendamento = vo.asTimestamp("DHAGENDAMENTO");
		
		try {
			loadOcorrencias();
		} catch (SkalaModelException e) {
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		}
	}
	

	public void loadOcorrencias() throws SkalaModelException {
		try {

			JapeWrapper dao = JapeFactory.dao("AD_TMSCALOCO");
			Collection<DynamicVO> vos = dao.find("this.CODCAL = ?", this.codCal);

			OcorrenciaCallCenter ocorrencia;
			for (DynamicVO vo : vos) {
				ocorrencia = new OcorrenciaCallCenter();
				ocorrencia.loadByVO(vo);

				this.ocorrencias.add(ocorrencia);
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível carregar ocorrências para o Call Center.", e);
		}
	}

	@Override
	public String toString() {
		return String.format(
				"CallCenter [codCal=%s, chaveCte=%s, numCte=%s, serieCte=%s, codParcCliente=%s, codParcTransp=%s, dtLimiteEntrega=%s, dtEntrega=%s, qtdReagendas=%s, dtEntCanhoto=%s, dtDevTotal=%s, dtChegParceiro=%s, dhAgendamento=%s, dhInclusao=%s, notas=%s]",
				codCal, chaveCte, numCte, serieCte, codParcCliente, codParcTransp, dtLimiteEntrega, dtEntrega,
				qtdReagendas, dtEntCanhoto, dtDevTotal, dtChegParceiro, dhAgendamento, dhInclusao, notas);
	}

}
