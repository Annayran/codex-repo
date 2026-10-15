package tech.skala.action.modules.comercial.estoque.terceiros;

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
import tech.skala.core.commons.exceptions.SkalaModelException;

public class Worksheet implements EntityBean, Persistable {
	
	private static final String ENTITY_NAME = "AD_PLTTGFCTETERITE";

	private BigDecimal id;
	private Timestamp countDate;
	private BigDecimal sequente;
	private BigDecimal productId;
	private String control;
	private BigDecimal quantity;
	private BigDecimal partnerId;
	private BigDecimal locationId;
	private Timestamp manufacturingDate;
	private Timestamp expirationDate;
	private BigDecimal userId;
	private Timestamp createdAt;
	private BigDecimal companyId;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public Timestamp getCountDate() {
		return countDate;
	}

	public void setCountDate(Timestamp countDate) {
		this.countDate = countDate;
	}

	public BigDecimal getSequente() {
		return sequente;
	}

	public void setSequente(BigDecimal sequente) {
		this.sequente = sequente;
	}

	public BigDecimal getProductId() {
		return productId;
	}

	public void setProductId(BigDecimal productId) {
		this.productId = productId;
	}

	public String getControl() {
		return control;
	}

	public void setControl(String control) {
		this.control = control;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(BigDecimal partnerId) {
		this.partnerId = partnerId;
	}

	public BigDecimal getLocationId() {
		return locationId;
	}

	public void setLocationId(BigDecimal locationId) {
		this.locationId = locationId;
	}

	public Timestamp getManufacturingDate() {
		return manufacturingDate;
	}

	public void setManufacturingDate(Timestamp manufacturingDate) {
		this.manufacturingDate = manufacturingDate;
	}

	public Timestamp getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Timestamp expirationDate) {
		this.expirationDate = expirationDate;
	}

	public BigDecimal getUserId() {
		return userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		try {
			EntityInsert entity = new EntityInsert(ENTITY_NAME);

			entity.setField("NUNICO", this.id);
			entity.setField("DTCONTAGEM", this.countDate);
			entity.setField(SklFieldName.CODPROD, this.productId);
			entity.setField(SklFieldName.CONTROLE, this.control);
			entity.setField("QTDEST", this.quantity);
			entity.setField(SklFieldName.CODPARC, this.partnerId);
			entity.setField(SklFieldName.CODLOCAL, this.locationId);
			entity.setField(SklFieldName.DTFABRICACAO, this.manufacturingDate);
			entity.setField(SklFieldName.DTVAL, this.expirationDate);
			entity.setField(SklFieldName.CODEMP, this.companyId);

			entity.fluidInsert();
		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}

		return null;
	}

	public void clear(BigDecimal id, Timestamp countDate) throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);

		try {
			dao.deleteByCriteria("this.NUNICO = ? AND this.DTCONTAGEM = ?", id, countDate);
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível excluir as informações processadas pelo arquivo anexo.", e);
		}

	}

}
