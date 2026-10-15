package tech.skala.action.modules.comercial.estoque.beans;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;

public class EstoquePorParceiro implements EntityBean, Persistable {
	public static final String ENTITY_NAME = "AD_PLTGFESTNEGPAR";
	
	private BigDecimal							codEmp;
	private BigDecimal							codParc;
	private BigDecimal							codLocal;
	private String								tipo;
	
	public BigDecimal getCodEmp() {
		return codEmp;
	}

	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}

	public BigDecimal getCodParc() {
		return codParc;
	}

	public void setCodParc(BigDecimal codParc) {
		this.codParc = codParc;
	}

	public BigDecimal getCodLocal() {
		return codLocal;
	}

	public void setCodLocal(BigDecimal codLocal) {
		this.codLocal = codLocal;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);

		try {
			DynamicVO vo = dao.findByPK(codEmp, codParc, codLocal, tipo);

			if (vo == null) {
				EntityInsert entityInsert = new EntityInsert(ENTITY_NAME);
				entityInsert.setField(SklFieldName.CODEMP, codEmp);
				entityInsert.setField(SklFieldName.CODPARC, codParc);
				entityInsert.setField(SklFieldName.CODLOCAL, codLocal);
				entityInsert.setField("TIPO", tipo);

				entityInsert.fluidInsert();
			}
		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}
		
		return null;
	}

}
