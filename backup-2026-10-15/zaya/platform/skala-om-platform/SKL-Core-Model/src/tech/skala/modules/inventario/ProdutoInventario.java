package tech.skala.modules.inventario;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByResultSet;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class ProdutoInventario implements EntityBean, LoadableByResultSet {

	private BigDecimal							codProd;
	private String								descrProd;
	private String								codVol;
	private List<ProdutoLote>					produtoLotes = new LinkedList<>();
	
	public BigDecimal getCodProd() {
		return codProd;
	}

	public void setCodProd(BigDecimal codProd) {
		this.codProd = codProd;
	}

	public String getDescrProd() {
		return descrProd;
	}

	public void setDescrProd(String descrProd) {
		this.descrProd = descrProd;
	}

	public String getCodVol() {
		return codVol;
	}

	public void setCodVol(String codVol) {
		this.codVol = codVol;
	}

	public List<ProdutoLote> getProdutoLotes() {
		return produtoLotes;
	}

	public void setProdutoLotes(List<ProdutoLote> produtoLotes) {
		this.produtoLotes = produtoLotes;
	}

	@Override
	public void loadByResultSet(ResultSet rs) throws RecordNotFoundException {
		try {
			this.codProd = rs.getBigDecimal(SklFieldName.CODPROD);
			this.descrProd = rs.getString(SklFieldName.DESCRPROD);
			this.codVol = rs.getString(SklFieldName.CODVOL);
			
			ProdutoLote produtoLote = new ProdutoLote();
			produtoLote.loadByResultSet(rs);
			
			this.produtoLotes.add(produtoLote);
		} catch (SQLException e) {
			throw new RecordNotFoundException(e);
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(codProd);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ProdutoInventario))
			return false;
		ProdutoInventario other = (ProdutoInventario) obj;
		return Objects.equals(codProd, other.codProd);
	}

	@Override
	public String toString() {
		return String.format("ProdutoInventario [codProd=%s, descrProd=%s, codVol=%s, produtoLotes=%s]", codProd,
				descrProd, codVol, produtoLotes);
	}

}
