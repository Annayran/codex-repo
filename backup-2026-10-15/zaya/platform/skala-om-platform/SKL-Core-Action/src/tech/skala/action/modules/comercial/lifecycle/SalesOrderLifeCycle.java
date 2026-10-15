package tech.skala.action.modules.comercial.lifecycle;

import java.math.BigDecimal;
import java.sql.Timestamp;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.exceptions.SkalaModelException;

public abstract class SalesOrderLifeCycle implements EntityBean, Persistable {

	private static final String ENTITY_NAME = "AD_PLTGFCIV";
	private BigDecimal nuNotaPv;
	private Timestamp dtComercial;
	
	protected SalesOrderLifeCycle() {}

	protected SalesOrderLifeCycle(BigDecimal nuNotaPv, Timestamp dtComercial) throws InsertNotPossibleException {
		this.nuNotaPv = nuNotaPv;
		this.dtComercial = dtComercial;
		
		this.persists();
	}

	public BigDecimal getNuNotaPv() {
		return nuNotaPv;
	}

	public void setNuNotaPv(BigDecimal nuNotaPv) {
		this.nuNotaPv = nuNotaPv;
	}

	public Timestamp getDtComercial() {
		return dtComercial;
	}

	public void setDtComercial(Timestamp dtComercial) {
		this.dtComercial = dtComercial;
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);

		try {
			DynamicVO vo = dao.findByPK(this.nuNotaPv);

			if (vo == null) {
				EntityInsert entityInsert = new EntityInsert(ENTITY_NAME);
				entityInsert.setField("NUNOTAPV", this.nuNotaPv);
				entityInsert.setField("DTCOMERCIAL", this.dtComercial);

				entityInsert.fluidInsert();
			}
		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}

		return null;
	}

	@Override
	public String toString() {
		return "SalesOrderLifeCycle [nuNotaPv=" + nuNotaPv + ", dtComercial=" + dtComercial + "]";
	}

	public abstract void buildItems(BigDecimal nuNotaPv, String etapa, Timestamp dhInc, BigDecimal nuNota) throws SkalaModelException;
}
