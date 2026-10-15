package tech.skala.modules.inventario;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByResultSet;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class ProdutoLote implements EntityBean, LoadableByResultSet {
	
	private transient BigDecimal					codProd;
	private String									controle;
	private Timestamp								dtFabricacao;
	private Timestamp								dtVal;
	private BigDecimal								idiProc;
	
	public BigDecimal getCodProd() {
		return codProd;
	}

	public void setCodProd(BigDecimal codProd) {
		this.codProd = codProd;
	}

	public String getControle() {
		return controle;
	}

	public void setControle(String controle) {
		this.controle = controle;
	}

	public Timestamp getDtFabricacao() {
		return dtFabricacao;
	}

	public void setDtFabricacao(Timestamp dtFabricacao) {
		this.dtFabricacao = dtFabricacao;
	}

	public Timestamp getDtVal() {
		return dtVal;
	}

	public void setDtVal(Timestamp dtVal) {
		this.dtVal = dtVal;
	}

	public BigDecimal getIdiProc() {
		return idiProc;
	}

	public void setIdiProc(BigDecimal idiProc) {
		this.idiProc = idiProc;
	}

	@Override
	public void loadByResultSet(ResultSet rs) throws RecordNotFoundException {
		try {
			this.codProd = rs.getBigDecimal(SklFieldName.CODPROD);
			this.controle = rs.getString(SklFieldName.CONTROLE);
			this.dtFabricacao = rs.getTimestamp(SklFieldName.DTFABRICACAO);
			this.dtVal = rs.getTimestamp(SklFieldName.DTVAL);
			this.idiProc = rs.getBigDecimal(SklFieldName.IDIPROC);
		} catch (SQLException e) {
			throw new RecordNotFoundException(e);
		}
	}

	@Override
	public String toString() {
		return String.format("ProdutoLote [controle=%s, dtFabricacao=%s, dtVal=%s, idiProc=%s]", controle, dtFabricacao,
				dtVal, idiProc);
	}
	
}
