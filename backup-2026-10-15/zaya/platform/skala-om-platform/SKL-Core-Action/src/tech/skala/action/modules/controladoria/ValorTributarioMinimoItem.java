package tech.skala.action.modules.controladoria;

import java.math.BigDecimal;
import java.sql.Timestamp;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.EntityInsert;

public class ValorTributarioMinimoItem implements EntityBean, Persistable {

	private static final String ENTITY_NAME = "AD_PLTGFVTMITE";

	private BigDecimal codEmp;
	private Timestamp dtRef;
	private BigDecimal codProd;
	private BigDecimal vlrUnitario;

	public BigDecimal getCodEmp() {
		return codEmp;
	}

	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}

	public Timestamp getDtRef() {
		return dtRef;
	}

	public void setDtRef(Timestamp dtRef) {
		this.dtRef = dtRef;
	}

	public BigDecimal getCodProd() {
		return codProd;
	}

	public void setCodProd(BigDecimal codProd) {
		this.codProd = codProd;
	}

	public BigDecimal getVlrTributario() {
		return vlrUnitario;
	}

	public void setVlrTributario(BigDecimal vlrTributario) {
		this.vlrUnitario = vlrTributario;
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {

		EntityInsert entity = new EntityInsert(ENTITY_NAME);
		entity.setField(SklFieldName.CODEMP, codEmp);
		entity.setField(SklFieldName.DTREF, dtRef);
		entity.setField(SklFieldName.CODPROD, codProd);
		entity.setField("VLRUNITMEDIO", vlrUnitario);
		entity.fluidInsert();

		return null;

	}
	
	public void clear(BigDecimal codEmp, Timestamp dtRef) throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);

		try {
			dao.deleteByCriteria("this.CODEMP = ? AND this.DTREF = ?", codEmp, dtRef);
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível excluir as informações processadas pelo arquivo anexo.", e);
		}

	}

}
