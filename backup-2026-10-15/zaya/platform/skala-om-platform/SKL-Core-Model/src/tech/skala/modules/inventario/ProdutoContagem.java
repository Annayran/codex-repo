package tech.skala.modules.inventario;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;

public class ProdutoContagem implements EntityBean, Persistable, LoadableByValueObject {
	public static final String					ENTITY_NAME = SklEntityName.PRODUTO_CONTAGEM;
	
	private transient BigDecimal				codEmp;
	private transient BigDecimal				codLocal;
	private transient BigDecimal				codParc;
	private transient BigDecimal				sequencia;
	private transient BigDecimal				idIvt;
	private transient String					tipo;
	private BigDecimal							codProd;
	private BigDecimal							sequenciaProd;
	private BigDecimal							qtdContagem;
	private String								controle;
	private Timestamp							dtVal;
	private Timestamp							dtFabricacao;

	public BigDecimal getCodEmp() {
		return codEmp;
	}

	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}

	public BigDecimal getCodLocal() {
		return codLocal;
	}

	public void setCodLocal(BigDecimal codLocal) {
		this.codLocal = codLocal;
	}

	public BigDecimal getCodParc() {
		return codParc;
	}

	public void setCodParc(BigDecimal codParc) {
		this.codParc = codParc;
	}

	public BigDecimal getSequencia() {
		return sequencia;
	}

	public void setSequencia(BigDecimal sequencia) {
		this.sequencia = sequencia;
	}

	public BigDecimal getIdIvt() {
		return idIvt;
	}

	public void setIdIvt(BigDecimal idIvt) {
		this.idIvt = idIvt;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getCodProd() {
		return codProd;
	}

	public void setCodProd(BigDecimal codProd) {
		this.codProd = codProd;
	}

	public BigDecimal getSequenciaProd() {
		return sequenciaProd;
	}

	public void setSequenciaProd(BigDecimal sequenciaProd) {
		this.sequenciaProd = sequenciaProd;
	}

	public BigDecimal getQtdContagem() {
		return qtdContagem;
	}

	public void setQtdContagem(BigDecimal qtdContagem) {
		this.qtdContagem = qtdContagem;
	}

	public String getControle() {
		return controle;
	}

	public void setControle(String controle) {
		this.controle = controle;
	}
	
	public Timestamp getDtVal() {
		return dtVal;
	}

	public void setDtVal(Timestamp dtVal) {
		this.dtVal = dtVal;
	}

	public Timestamp getDtFabricacao() {
		return dtFabricacao;
	}

	public void setDtFabricacao(Timestamp dtFabricacao) {
		this.dtFabricacao = dtFabricacao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codProd, controle);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ProdutoContagem))
			return false;
		ProdutoContagem other = (ProdutoContagem) obj;
		return Objects.equals(codProd, other.codProd) && Objects.equals(controle, other.controle);
	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.codEmp = vo.asBigDecimal(SklFieldName.CODEMP);
		this.codLocal = vo.asBigDecimal(SklFieldName.CODLOCAL);
		this.codParc = vo.asBigDecimal(SklFieldName.CODPARC);
		this.sequencia = vo.asBigDecimal(SklFieldName.SEQUENCIA);
		this.idIvt = vo.asBigDecimal(SklFieldName.IDIVT);
		this.tipo = vo.asString(SklFieldName.TIPO);
		this.codProd = vo.asBigDecimal(SklFieldName.CODPROD);
		this.sequenciaProd = vo.asBigDecimal("SEQUENCIAPROD");
		this.qtdContagem = vo.asBigDecimal(SklFieldName.QTDCONTAGEM);
		this.controle = vo.asString(SklFieldName.CONTROLE);
		this.dtVal = vo.asTimestamp(SklFieldName.DTVAL);
		this.dtFabricacao = vo.asTimestamp(SklFieldName.DTFABRICACAO);
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		EntityInsert entityNew = new EntityInsert(ENTITY_NAME);
		entityNew.setField(SklFieldName.CODEMP, codEmp);
		entityNew.setField(SklFieldName.CODLOCAL, codLocal);
		entityNew.setField(SklFieldName.CODPARC, codParc);
		entityNew.setField(SklFieldName.SEQUENCIA, sequencia);
		entityNew.setField(SklFieldName.CODPROD, codProd);
		entityNew.setField(SklFieldName.TIPO, tipo);
		entityNew.setField(SklFieldName.CONTROLE, controle);
		entityNew.setField(SklFieldName.IDIVT, idIvt);
		entityNew.setField(SklFieldName.QTDCONTAGEM, qtdContagem);
		entityNew.setField(SklFieldName.DTVAL, dtVal);
		entityNew.setField(SklFieldName.DTFABRICACAO, dtFabricacao);
		
		DynamicVO vo = entityNew.fluidInsert();
		this.sequenciaProd = vo.asBigDecimal("SEQUENCIAPROD");
		return null;
	}

}
