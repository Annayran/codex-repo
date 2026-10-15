package tech.skala.action.modules.inventario;

import java.math.BigDecimal;
import java.sql.Timestamp;

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
import tech.skala.core.commons.dd.EntityUpdate;

public class ContagemEstoque implements EntityBean, Persistable {
	private static final String ENTITY_NAME = "ContagemEstoque";

	private BigDecimal codProd;
	private String codVol;
	private BigDecimal qtdEst;
	private Timestamp dtContagem;
	private String controle;
	private BigDecimal codLocal;
	private String tipo;
	private BigDecimal codParc;
	private BigDecimal codEmp;
	private Timestamp dtVal;
	private BigDecimal sequencia;
	private BigDecimal qtdEstUnCad;
	private BigDecimal nuIvt;
	private Timestamp dtFabricacao;

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

	public BigDecimal getQtdEst() {
		return qtdEst;
	}

	public void setQtdEst(BigDecimal qtdEst) {
		this.qtdEst = qtdEst;
	}

	public Timestamp getDtContagem() {
		return dtContagem;
	}

	public void setDtContagem(Timestamp dtContagem) {
		this.dtContagem = dtContagem;
	}

	public String getControle() {
		return controle;
	}

	public void setControle(String controle) {
		this.controle = controle;
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

	public BigDecimal getCodParc() {
		return codParc;
	}

	public void setCodParc(BigDecimal codParc) {
		this.codParc = codParc;
	}

	public BigDecimal getCodEmp() {
		return codEmp;
	}

	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}

	public Timestamp getDtVal() {
		return dtVal;
	}

	public void setDtVal(Timestamp dtVal) {
		this.dtVal = dtVal;
	}

	public BigDecimal getSequencia() {
		return sequencia;
	}

	public void setSequencia(BigDecimal sequencia) {
		this.sequencia = sequencia;
	}

	public BigDecimal getQtdEstUnCad() {
		return qtdEstUnCad;
	}

	public void setQtdEstUnCad(BigDecimal qtdEstUnCad) {
		this.qtdEstUnCad = qtdEstUnCad;
	}

	public BigDecimal getNuIvt() {
		return nuIvt;
	}

	public void setNuIvt(BigDecimal nuIvt) {
		this.nuIvt = nuIvt;
	}

	public Timestamp getDtFabricacao() {
		return dtFabricacao;
	}

	public void setDtFabricacao(Timestamp dtFabricacao) {
		this.dtFabricacao = dtFabricacao;
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);

		DynamicVO vo = null;
		try {
			
			if (dtContagem != null && codEmp != null && codLocal != null && codProd != null && controle != null
					&& codVol != null && codParc != null && tipo != null && sequencia != null) {
				vo = dao.findByPK(dtContagem, codEmp, codLocal, codProd, controle, codVol, codParc, tipo, sequencia);
			}

			if (vo == null) {
				EntityInsert entityNew = new EntityInsert(ENTITY_NAME);

				entityNew.setField(SklFieldName.CODPROD, codProd);
				entityNew.setField(SklFieldName.CODVOL, codVol);
				entityNew.setField("QTDEST", qtdEst);
				entityNew.setField("DTCONTAGEM", dtContagem);
				entityNew.setField(SklFieldName.CONTROLE, controle);
				entityNew.setField(SklFieldName.CODLOCAL, codLocal);
				entityNew.setField(SklFieldName.TIPO, tipo);
				entityNew.setField(SklFieldName.CODPARC, codParc);
				entityNew.setField(SklFieldName.CODEMP, codEmp);
				entityNew.setField(SklFieldName.DTVAL, dtVal);
				entityNew.setField(SklFieldName.SEQUENCIA, sequencia);
				entityNew.setField("QTDESTUNCAD", qtdEstUnCad);
				entityNew.setField("NUIVT", nuIvt);
				entityNew.setField(SklFieldName.DTFABRICACAO, dtFabricacao);
				entityNew.fluidInsert();
			} else {
				EntityUpdate entityUpd = new EntityUpdate(ENTITY_NAME, vo);
				entityUpd.setField("QTDEST", qtdEst);
				entityUpd.fluidUpdate();
			}

		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}
		return null;

	}

}
