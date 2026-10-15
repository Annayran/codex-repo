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

public class SalesOrderLifeCycleItem implements EntityBean, Persistable {

	private static final String ENTITY_NAME = "AD_PLTGFCIVET";
	private BigDecimal nuNotaPv;
	private String etapa;
	private BigDecimal nuNota;
	private Timestamp dhInc;
	private BigDecimal codUsuInc;

	public BigDecimal getNuNotaPv() {
		return nuNotaPv;
	}

	public void setNuNotaPv(BigDecimal nuNotaPv) {
		this.nuNotaPv = nuNotaPv;
	}

	public String getEtapa() {
		return etapa;
	}

	public void setEtapa(String etapa) {
		this.etapa = etapa;
	}

	public BigDecimal getNuNota() {
		return nuNota;
	}

	public void setNuNota(BigDecimal nuNota) {
		this.nuNota = nuNota;
	}

	public Timestamp getDhInc() {
		return dhInc;
	}

	public void setDhInc(Timestamp dhInc) {
		this.dhInc = dhInc;
	}

	public BigDecimal getCodUsuInc() {
		return codUsuInc;
	}

	public void setCodUsuInc(BigDecimal codUsuInc) {
		this.codUsuInc = codUsuInc;
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);

		try {
			DynamicVO vo = dao.findByPK(this.nuNotaPv, this.etapa, this.nuNota);

			if (vo == null) {
				EntityInsert entityInsert = new EntityInsert(ENTITY_NAME);
				entityInsert.setField("NUNOTAPV", this.nuNotaPv);
				entityInsert.setField("ETAPA", this.etapa);
				entityInsert.setField("NUNOTA", this.nuNota);
				entityInsert.setField("DHINC", this.dhInc);
				entityInsert.setField("CODUSUINC", this.codUsuInc);

				entityInsert.fluidInsert();
			}
		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}

		return null;
	}

}
