package tech.skala.action.modules.comercial.exportacao;

import java.math.BigDecimal;

public class ItemDTO {

	private BigDecimal productId;
	private BigDecimal quantity;
	private BigDecimal value;
	private BigDecimal origLocalId;

	public BigDecimal getProductId() {
		return productId;
	}

	public void setProductId(BigDecimal productId) {
		this.productId = productId;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public BigDecimal getOrigLocalId() {
		return origLocalId;
	}

	public void setOrigLocalId(BigDecimal origLocalId) {
		this.origLocalId = origLocalId;
	}

	@Override
	public String toString() {
		return String.format("ItemDTO [productId=%s, quantity=%s, value=%s, origLocalId=%s]", productId, quantity,
				value, origLocalId);
	}

}
