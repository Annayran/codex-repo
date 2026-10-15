package tech.skala.action.modules.comercial.estoque.terceiros;

import java.math.BigDecimal;
import java.sql.Timestamp;

import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;

public class ProductCount implements EntityBean, Persistable {

	private static final String ENTITY_NAME = "ContagemEstoque";

	private Timestamp countDate;
	private BigDecimal companyId;
	private BigDecimal locationId;
	private BigDecimal productId;
	private String control;
	private BigDecimal quantity;
	private String volumeId;
	private BigDecimal partnerId;
	private String type;
	private BigDecimal sequence;
	private BigDecimal id;
	private BigDecimal standardUnitQuantity;
	private Timestamp manufacturingDate;
	private Timestamp expirationDate;

	public Timestamp getCountDate() {
		return countDate;
	}

	public void setCountDate(Timestamp countDate) {
		this.countDate = countDate;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getLocationId() {
		return locationId;
	}

	public void setLocationId(BigDecimal locationId) {
		this.locationId = locationId;
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

	public String getVolumeId() {
		return volumeId;
	}

	public void setVolumeId(String volumeId) {
		this.volumeId = volumeId;
	}

	public BigDecimal getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(BigDecimal partnerId) {
		this.partnerId = partnerId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getSequence() {
		return sequence;
	}

	public void setSequence(BigDecimal sequence) {
		this.sequence = sequence;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getStandardUnitQuantity() {
		return standardUnitQuantity;
	}

	public void setStandardUnitQuantity(BigDecimal standardUnitQuantity) {
		this.standardUnitQuantity = standardUnitQuantity;
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

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		try {
			EntityInsert entity = new EntityInsert(ENTITY_NAME);

			entity.setField("DTCONTAGEM", this.countDate);
			entity.setField(SklFieldName.CODEMP, this.companyId);
			entity.setField(SklFieldName.CODLOCAL, this.locationId);
			entity.setField(SklFieldName.CODPROD, this.productId);
			entity.setField(SklFieldName.CONTROLE, this.control);
			entity.setField("QTDEST", this.quantity);
			entity.setField(SklFieldName.CODVOL, this.volumeId);
			entity.setField(SklFieldName.CODPARC, this.partnerId);
			entity.setField(SklFieldName.TIPO, this.type);
			entity.setField(SklFieldName.SEQUENCIA, this.sequence);
			entity.setField("AD_NUNICOCTETERCAB", this.id);
			entity.setField("QTDESTUNCAD", this.standardUnitQuantity);
			entity.setField(SklFieldName.DTFABRICACAO, this.manufacturingDate);
			entity.setField(SklFieldName.DTVAL, this.expirationDate);

			entity.fluidInsert();
		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}

		return null;
	}

}
