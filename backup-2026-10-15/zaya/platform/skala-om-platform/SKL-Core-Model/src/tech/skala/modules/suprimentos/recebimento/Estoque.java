package tech.skala.modules.suprimentos.recebimento;

import java.math.BigDecimal;
import java.sql.Timestamp;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.EntityUpdate;

public class Estoque implements EntityBean, Persistable {
	
	private static final String 			ENTITY_NAME = DynamicEntityNames.ESTOQUE;
	
	private BigDecimal						codEmp;
	private BigDecimal						codLocal;
	private BigDecimal						codProd;
	private String							controle;
	private BigDecimal						estoque;
	private BigDecimal						reservado;
	private String							codBarra;
	private String							ativo;
	private Timestamp						dtVal;
	private BigDecimal						codParc;
	private String							tipo;
	private String							statusLote;
	private Timestamp						dtFabricacao;
	private BigDecimal						wmsBloqueado;

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

	public BigDecimal getEstoque() {
		return estoque;
	}

	public void setEstoque(BigDecimal estoque) {
		this.estoque = estoque;
	}

	public BigDecimal getReservado() {
		return reservado;
	}

	public void setReservado(BigDecimal reservado) {
		this.reservado = reservado;
	}

	public String getCodBarra() {
		return codBarra;
	}

	public void setCodBarra(String codBarra) {
		this.codBarra = codBarra;
	}

	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

	public Timestamp getDtVal() {
		return dtVal;
	}

	public void setDtVal(Timestamp dtVal) {
		this.dtVal = dtVal;
	}

	public BigDecimal getCodParc() {
		return codParc;
	}

	public void setCodParc(BigDecimal codParc) {
		this.codParc = codParc;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getStatusLote() {
		return statusLote;
	}

	public void setStatusLote(String statusLote) {
		this.statusLote = statusLote;
	}

	public Timestamp getDtFabricacao() {
		return dtFabricacao;
	}

	public void setDtFabricacao(Timestamp dtFabricacao) {
		this.dtFabricacao = dtFabricacao;
	}

	public BigDecimal getWmsBloqueado() {
		return wmsBloqueado;
	}

	public void setWmsBloqueado(BigDecimal wmsBloqueado) {
		this.wmsBloqueado = wmsBloqueado;
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {

		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);

		DynamicVO vo = null;
		if (codEmp != null && codProd != null && codLocal != null && controle != null && codParc != null
				&& tipo != null) {

			try {
				vo = dao.findByPK(codEmp, codProd, codLocal, controle, codParc, tipo);
			} catch (Exception e) {
				throw new UpdateNotPossibleException(e);
			}

		}

		if (vo == null) {
			EntityInsert entityInsert = new EntityInsert(ENTITY_NAME);
			entityInsert.setField(SklFieldName.CODEMP, codEmp);
			entityInsert.setField(SklFieldName.CODLOCAL, codLocal);
			entityInsert.setField(SklFieldName.CODPROD, codProd);
			entityInsert.setField(SklFieldName.CONTROLE, controle);
			entityInsert.setField(SklFieldName.CODPARC, codParc);
			entityInsert.setField("TIPO", tipo);
			entityInsert.setField("ESTOQUE", estoque);
			entityInsert.setField("RESERVADO", reservado);
			entityInsert.setField(SklFieldName.CODBARRA, codBarra);
			entityInsert.setField(SklFieldName.ATIVO, ativo);
			entityInsert.setField("STATUSLOTE", statusLote);
			entityInsert.setField(SklFieldName.DTVAL, dtVal);
			entityInsert.setField(SklFieldName.DTFABRICACAO, dtFabricacao);
			entityInsert.setField("WMSBLOQUEADO", wmsBloqueado);

			entityInsert.fluidInsert();
		} else {
			EntityUpdate entityUpdate = new EntityUpdate(ENTITY_NAME, vo);
			entityUpdate.setField(SklFieldName.DTVAL, dtVal);
			entityUpdate.setField(SklFieldName.DTFABRICACAO, dtFabricacao);
			entityUpdate.fluidUpdate();
		}

		return null;
	}

	@Override
	public String toString() {
		return String.format(
				"Estoque [codEmp=%s, codLocal=%s, codProd=%s, controle=%s, estoque=%s, reservado=%s, codBarra=%s, ativo=%s, dtVal=%s, codParc=%s, tipo=%s, dtFabricacao=%s, wmsBloqueado=%s]",
				codEmp, codLocal, codProd, controle, estoque, reservado, codBarra, ativo, dtVal, codParc, tipo,
				dtFabricacao,  wmsBloqueado);
	}
	
}
