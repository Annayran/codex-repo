package tech.skala.core.beans.suprimento.recebimento;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;

import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.dao.EntityPrimaryKey;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.EntityUpdate;

public class Recebimento implements EntityBean, LoadableByValueObject, Persistable {
	public static final String				ENTITY_NAME = SklEntityName.MOBILE_RECEBIMENTO;	

	private BigDecimal						nuRecebimento;
	private String							situacao;
	private boolean							concluido;
	private Timestamp						dtRecebimento;
	private BigDecimal						nuNota;
	private BigDecimal						numNota;
	private BigDecimal						codParc;
	private String							cnpj;
	private Conferencia						conferencia;
	
	public BigDecimal getNuRecebimento() {
		return nuRecebimento;
	}

	public void setNuRecebimento(BigDecimal nuRecebimento) {
		this.nuRecebimento = nuRecebimento;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public boolean isConcluido() {
		return concluido;
	}

	public void setConcluido(boolean concluido) {
		this.concluido = concluido;
	}

	public Timestamp getDtRecebimento() {
		return dtRecebimento;
	}

	public void setDtRecebimento(Timestamp dtRecebimento) {
		this.dtRecebimento = dtRecebimento;
	}

	public BigDecimal getNuNota() {
		return nuNota;
	}

	public void setNuNota(BigDecimal nuNota) {
		this.nuNota = nuNota;
	}

	public BigDecimal getNumNota() {
		return numNota;
	}

	public void setNumNota(BigDecimal numNota) {
		this.numNota = numNota;
	}

	public BigDecimal getCodParc() {
		return codParc;
	}

	public void setCodParc(BigDecimal codParc) {
		this.codParc = codParc;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Conferencia getConferencia() {
		return conferencia;
	}

	public void setConferencia(Conferencia conferencia) {
		this.conferencia = conferencia;
	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		try {
			this.nuRecebimento = vo.asBigDecimal(SklFieldName.NURECEBIMENTO);
			this.situacao = vo.asString(SklFieldName.SITUACAO);
			this.dtRecebimento = vo.asTimestamp(SklFieldName.DTRECEBIMENTO);
			this.nuNota = vo.asBigDecimal(SklFieldName.NUNOTA);
			this.numNota = vo.asDymamicVO(DynamicEntityNames.CABECALHO_NOTA).asBigDecimal(SklFieldName.NUMNOTA);
			this.codParc = vo.asBigDecimal(SklFieldName.CODPARC);
			this.cnpj = vo.asDymamicVO(DynamicEntityNames.PARCEIRO).asString(SklFieldName.CGC_CPF);
			
			JapeWrapper recebimentoConferenciaDAO = JapeFactory.dao(SklEntityName.MOBILE_RECEBIMENTO_CONFERENCIA);
			JapeWrapper conferenciaDAO = JapeFactory.dao(SklEntityName.MOBILE_CONFERENCIA);
			
			Collection<DynamicVO> recebimentoConferenciasVO = recebimentoConferenciaDAO.find("this.NURECEBIMENTO = ?", nuRecebimento);
			for (DynamicVO recebimentoConferenciaVO : recebimentoConferenciasVO) {
				BigDecimal nuConferencia = recebimentoConferenciaVO.asBigDecimal(SklFieldName.NUCONFERENCIA);
				
				Conferencia novaConferencia = null;
				Collection<DynamicVO> conferenciasVO = conferenciaDAO.find("this.NUCONFERENCIA = ?", nuConferencia);
				for (DynamicVO conferenciaVO : conferenciasVO) {
					novaConferencia = new Conferencia();
					novaConferencia.setNuNota(nuNota);
					novaConferencia.setCodParc(codParc);
					novaConferencia.loadByVO(conferenciaVO);
				}
				this.conferencia = novaConferencia;
			}
			
		} catch (Exception e) {
			throw new RecordNotFoundException(e);
		}
		
	}
	
	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		DynamicVO vo = null;
		this.dtRecebimento = TimeUtils.clearTime(TimeUtils.getNow());

		if (nuRecebimento == null) {
			EntityInsert entityNew = new EntityInsert(ENTITY_NAME);
			entityNew.setField(SklFieldName.NUNOTA, nuNota);
			entityNew.setField(SklFieldName.CODPARC, codParc);
			entityNew.setField(SklFieldName.SITUACAO, situacao);
			entityNew.setField(SklFieldName.DTRECEBIMENTO, dtRecebimento);
			vo = entityNew.fluidInsert();
			
			this.nuRecebimento = vo.asBigDecimal(SklFieldName.NURECEBIMENTO);

		} else {
			EntityPrimaryKey pk = new EntityPrimaryKey(new Object[] { nuRecebimento });
			EntityUpdate entityUpd = new EntityUpdate(ENTITY_NAME, pk);

			entityUpd.setField(SklFieldName.SITUACAO, situacao);
			entityUpd.fluidUpdate();
		}

		return null;
	}

	@Override
	public String toString() {
		return String.format(
				"Recebimento [nuRecebimento=%s, situacao=%s, concluido=%s, dtRecebimento=%s, nuNota=%s, numNota=%s, codParc=%s, cnpj=%s, conferencia=%s]",
				nuRecebimento, situacao, concluido, dtRecebimento, nuNota, numNota, codParc, cnpj, conferencia);
	}

}
