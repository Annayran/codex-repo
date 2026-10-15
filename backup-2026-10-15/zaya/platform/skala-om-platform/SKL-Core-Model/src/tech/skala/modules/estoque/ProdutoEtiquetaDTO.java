package tech.skala.modules.estoque;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import tech.skala.core.beans.interfaces.LoadableByResultSet;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class ProdutoEtiquetaDTO implements LoadableByResultSet {

	private BigDecimal codProd;
	private String descrProd;
	private String marca;
	private String codBarra;
	private BigDecimal quantidade;
	private String linha;

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

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getCodBarra() {
		return codBarra;
	}

	public void setCodBarra(String codBarra) {
		this.codBarra = codBarra;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public String getLinha() {
		return linha;
	}

	public void setLinha(String linha) {
		this.linha = linha;
	}

	@Override
	public void loadByResultSet(ResultSet rs) throws RecordNotFoundException {

		try {
			this.codProd = rs.getBigDecimal(SklFieldName.CODPROD);
			this.descrProd = rs.getString(SklFieldName.DESCRPROD);
			this.marca = rs.getString(SklFieldName.MARCA);
			this.codBarra = rs.getString(SklFieldName.CODBARRA);
			this.quantidade = rs.getBigDecimal(SklFieldName.QUANTIDADE);
			this.linha = rs.getString(SklFieldName.LINHA);
		} catch (SQLException e) {
			throw new RecordNotFoundException(e);
		}

	}

	@Override
	public String toString() {
		return String.format(
				"ProdutoEtiquetaDTO [codProd=%s, descrProd=%s, marca=%s, codBarra=%s, quantidade=%s, linha=%s]",
				codProd, descrProd, marca, codBarra, quantidade, linha);
	}

}
