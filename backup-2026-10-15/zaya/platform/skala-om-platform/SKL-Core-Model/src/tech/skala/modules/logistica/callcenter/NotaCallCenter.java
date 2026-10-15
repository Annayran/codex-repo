package tech.skala.modules.logistica.callcenter;

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
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;

/**
 * A classe {@code NotaCallCenter} representa a tabela {@code AD_TMSCALNOTA}.
 * 
 * @author hiagoalexandre
 * @since 2022-11-24
 */
public class NotaCallCenter implements EntityBean, Persistable, LoadableByPrimaryKey, LoadableByValueObject {
	public static final String						ENTITY_NAME = SklEntityName.NOTA_CALL_CENTER;
	
	private	BigDecimal 								codCal;
	private	BigDecimal  							nuNota;

	public BigDecimal getCodCal() {
		return codCal;
	}

	public void setCodCal(BigDecimal codCal) {
		this.codCal = codCal;
	}

	public BigDecimal getNuNota() {
		return nuNota;
	}

	public void setNuNota(BigDecimal nuNota) {
		this.nuNota = nuNota;
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
		this.nuNota = vo.asBigDecimal(SklFieldName.NUNOTA);
	}
	
	
	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);

		try {
			if (dao.findByPK(codCal, nuNota) == null) {
				EntityInsert entityNew = new EntityInsert(ENTITY_NAME);
				entityNew.setField("CODCAL", codCal);
				entityNew.setField(SklFieldName.NUNOTA, nuNota);
				entityNew.fluidInsert();
			}
		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}

		return null;
	}
	
	@Override
	public String toString() {
		return String.format("NotaCallCenter [codCal=%s, nuNota=%s]", codCal, nuNota);
	}

}
