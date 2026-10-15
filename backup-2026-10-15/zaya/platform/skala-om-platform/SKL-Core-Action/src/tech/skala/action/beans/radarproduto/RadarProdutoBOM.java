package tech.skala.action.beans.radarproduto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.action.beans.produto.ProdutoMeta;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.EntityUpdate;

public class RadarProdutoBOM implements EntityBean, Persistable {
	
	private final Timestamp					referencia;
	private final BigDecimal				semana;
	private final ProdutoMeta				produto;
	private final ProdutoMeta				materiaPrima;
	private final String					codVolMP;
	private BigDecimal						qtdMistura;
	private BigDecimal						qtdDemanda;
	private BigDecimal						qtdPendente;
	private BigDecimal						cusSemIcm;

	public RadarProdutoBOM(Timestamp referencia, BigDecimal semana, ProdutoMeta produto,
			ProdutoMeta materiaPrima, String codVolMP) {
		this.referencia = referencia;
		this.semana = semana;
		this.produto = produto;
		this.materiaPrima = materiaPrima;
		this.codVolMP = codVolMP;
		this.qtdMistura = BigDecimal.ZERO;
		this.qtdDemanda = BigDecimal.ZERO;
		this.qtdPendente = BigDecimal.ZERO;
		this.cusSemIcm = BigDecimal.ZERO;
	}

	public BigDecimal getQtdMistura() {
		return qtdMistura;
	}

	public void setQtdMistura(BigDecimal qtdMistura) {
		this.qtdMistura = qtdMistura;
	}

	public BigDecimal getQtdDemanda() {
		return qtdDemanda;
	}

	public void setQtdDemanda(BigDecimal qtdDemanda) {
		this.qtdDemanda = qtdDemanda;
	}

	public BigDecimal getQtdPendente() {
		return qtdPendente;
	}

	public void setQtdPendente(BigDecimal qtdPendente) {
		this.qtdPendente = qtdPendente;
	}

	public BigDecimal getCusSemIcm() {
		return cusSemIcm;
	}

	public void setCusSemIcm(BigDecimal cusSemIcm) {
		this.cusSemIcm = cusSemIcm;
	}

	public Timestamp getReferencia() {
		return referencia;
	}

	public BigDecimal getSemana() {
		return semana;
	}

	public ProdutoMeta getProduto() {
		return produto;
	}

	public ProdutoMeta getMateriaPrima() {
		return materiaPrima;
	}

	public String getCodVolMP() {
		return codVolMP;
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao("AD_PLTGFRADARBOM");

		try {
			DynamicVO vo = dao.findOne(
					"this.REFERENCIA = ? AND this.SEMANA = ? AND this.CODPROD = ? AND this.CODPRODMP = ?",
					new Object[] { referencia, semana, produto.getCodProd(), materiaPrima.getCodProd() });

			if (vo == null) {
				EntityInsert insert = new EntityInsert("AD_PLTGFRADARBOM");

				insert.setField("SEMANA", semana);
				insert.setField("CODPROD", produto.getCodProd());
				insert.setField("SEQUENCIA", null);
				insert.setField("CODPRODMP", materiaPrima.getCodProd());
				insert.setField("QTDMISTURA", qtdMistura);
				insert.setField("QTDDEMANDA", qtdDemanda);
				insert.setField("QTDPENDENTE", qtdPendente);
				insert.setField("CODVOLMP", codVolMP);
				insert.setField("CUSSEMICM", cusSemIcm);
				insert.fluidInsert();

			} else {
				EntityUpdate update = new EntityUpdate("AD_PLTGFRADARBOM", vo);

				update.setField("QTDMISTURA", qtdMistura);
				update.setField("QTDDEMANDA", qtdDemanda);
				update.setField("QTDPENDENTE", qtdPendente);
				update.setField("CUSSEMICM", cusSemIcm);
				update.fluidUpdate();

			}

		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}
		return null;
	}

	@Override
	public String toString() {
		return String.format(
				"RadarProdutoBOM [referencia=%s, semana=%s, produto=%s, materiaPrima=%s, codVolMP=%s, qtdMistura=%s, qtdDemanda=%s, qtdPendente=%s, cusSemIcm=%s]",
				referencia, semana, produto, materiaPrima, codVolMP, qtdMistura, qtdDemanda, qtdPendente, cusSemIcm);
	}

}
