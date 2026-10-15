package tech.skala.modules.suprimentos.abastecimento;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByPrimaryKey;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;

public class ProdutoAtendimentoRequisicao implements EntityBean, LoadableByValueObject, LoadableByPrimaryKey, Persistable {
	private static final String 					ENTITY_NAME = SklEntityName.ATENDIMENTO_PRODUTO_REQUISICOES;
	
	private transient BigDecimal					nuReq;
	private transient BigDecimal 					sequencia;
	private BigDecimal 								sequenciaAtd;
	private BigDecimal								codProd; 
	private String 									codVol; 
	private String 									controle;
	private BigDecimal 								qtdAtendida;
	private BigDecimal 								qtdVol;
	private BigDecimal 								qtdUnit;
	
	public BigDecimal getNuReq() {
		return nuReq;
	}

	public void setNuReq(BigDecimal nuReq) {
		this.nuReq = nuReq;
	}

	public BigDecimal getSequencia() {
		return sequencia;
	}

	public void setSequencia(BigDecimal sequencia) {
		this.sequencia = sequencia;
	}

	public BigDecimal getSequenciaAtd() {
		return sequenciaAtd;
	}

	public void setSequenciaAtd(BigDecimal sequenciaAtd) {
		this.sequenciaAtd = sequenciaAtd;
	}

	public BigDecimal getCodProd() {
		return codProd;
	}

	public void setCodProd(BigDecimal codProd) {
		this.codProd = codProd;
	}

	public String getCodVol() {
		return codVol;
	}

	public void setCodVol(String codVol) {
		this.codVol = codVol;
	}

	public String getControle() {
		return controle;
	}

	public void setControle(String controle) {
		this.controle = controle;
	}

	public BigDecimal getQtdAtendida() {
		return qtdAtendida;
	}

	public void setQtdAtendida(BigDecimal qtdAtendida) {
		this.qtdAtendida = qtdAtendida;
	}

	public BigDecimal getQtdVol() {
		return qtdVol;
	}

	public void setQtdVol(BigDecimal qtdVol) {
		this.qtdVol = qtdVol;
	}

	public BigDecimal getQtdUnit() {
		return qtdUnit;
	}

	public void setQtdUnit(BigDecimal qtdUnit) {
		this.qtdUnit = qtdUnit;
	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.nuReq = vo.asBigDecimal(SklFieldName.NUREQ);
		this.sequencia = vo.asBigDecimal(SklFieldName.SEQUENCIA);
		this.sequenciaAtd = vo.asBigDecimal(SklFieldName.SEQUENCIAATD);
		this.codProd = vo.asBigDecimal(SklFieldName.CODPROD);
		this.codVol = vo.asString(SklFieldName.CODVOL);
		this.controle = vo.asString(SklFieldName.CONTROLE);
		this.qtdAtendida = vo.asBigDecimal(SklFieldName.QTDATENDIDA);
		this.qtdVol = vo.asBigDecimal(SklFieldName.QTDVOL);
		this.qtdUnit = vo.asBigDecimal(SklFieldName.QTDUNIT);
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
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);

		DynamicVO vo = null;
		if (nuReq != null && sequencia != null && sequenciaAtd != null) {

			try {
				vo = dao.findByPK(nuReq, sequencia, sequenciaAtd);
			} catch (Exception e) {
				throw new DataDictionaryExceptions.InsertNotPossibleException(e);
			}

		}

		if (vo == null) {
			EntityInsert entityInsert = new EntityInsert(ENTITY_NAME);
			entityInsert.setField(SklFieldName.NUREQ, nuReq);
			entityInsert.setField(SklFieldName.SEQUENCIA, sequencia);
			entityInsert.setField(SklFieldName.CODPROD, codProd);
			entityInsert.setField(SklFieldName.CODVOL, codVol);
			entityInsert.setField(SklFieldName.CONTROLE, controle);
			entityInsert.setField(SklFieldName.QTDATENDIDA, qtdAtendida);
			entityInsert.setField(SklFieldName.QTDVOL, qtdVol);
			entityInsert.setField(SklFieldName.QTDUNIT, qtdUnit);

			DynamicVO newVO = entityInsert.fluidInsert();
			this.sequenciaAtd = newVO.asBigDecimal(SklFieldName.SEQUENCIAATD);
		}

		return null;
	}
		
	
	@Override
	public String toString() {
		return String.format(
				"ProdutoAtendimentoRequisicao [nuReq=%s, sequencia=%s, sequenciaAtd=%s, codProd=%s, codVol=%s, controle=%s, qtdAtendida=%s, qtdVol=%s, qtdUnit=%s]",
				nuReq, sequencia, sequenciaAtd, codProd, codVol, controle, qtdAtendida, qtdVol, qtdUnit);
	}

}
