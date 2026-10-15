package tech.skala.action.modules.comercial.estoque.beans;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;

public class ItemEstoque implements EntityBean, Persistable {
	public static final String ENTITY_NAME = "AD_PLTGFESTNEGITE";
	
	private BigDecimal 							codEmp;
	private String	 							controle;
	private String	 							tipo;
	private BigDecimal 							codParc;
	private BigDecimal 							codLocal;
	private BigDecimal 							codProd;
	private BigDecimal 							sequencia;
	private BigDecimal							estoque;
	private BigDecimal 							nuNota;
	
	public BigDecimal getCodEmp() {
		return codEmp;
	}

	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}

	public String getControle() {
		return controle;
	}

	public void setControle(String controle) {
		this.controle = controle;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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

	public BigDecimal getCodProd() {
		return codProd;
	}

	public void setCodProd(BigDecimal codProd) {
		this.codProd = codProd;
	}

	public BigDecimal getSequencia() {
		return sequencia;
	}

	public void setSequencia(BigDecimal sequencia) {
		this.sequencia = sequencia;
	}

	public BigDecimal getEstoque() {
		return estoque;
	}

	public void setEstoque(BigDecimal estoque) {
		this.estoque = estoque;
	}

	public BigDecimal getNuNota() {
		return nuNota;
	}

	public void setNuNota(BigDecimal nuNota) {
		this.nuNota = nuNota;
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);

		try {
			DynamicVO vo = dao.findByPK(codEmp, codParc, codLocal, controle, codProd, sequencia, nuNota, tipo);

			if (vo == null) {
				EntityInsert entityInsert = new EntityInsert(ENTITY_NAME);
				entityInsert.setField(SklFieldName.CODEMP, codEmp);
				entityInsert.setField(SklFieldName.CONTROLE, controle);
				entityInsert.setField(SklFieldName.CODPARC, codParc);
				entityInsert.setField("TIPO", tipo);
				entityInsert.setField(SklFieldName.SEQUENCIA, sequencia);
				entityInsert.setField(SklFieldName.CODPROD, codProd);
				entityInsert.setField(SklFieldName.CODLOCAL, codLocal);
				entityInsert.setField(SklFieldName.NUNOTA, nuNota);
				entityInsert.setField("ESTOQUE", estoque);

				entityInsert.fluidInsert();
			}
		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}

		return null;
	}

}
