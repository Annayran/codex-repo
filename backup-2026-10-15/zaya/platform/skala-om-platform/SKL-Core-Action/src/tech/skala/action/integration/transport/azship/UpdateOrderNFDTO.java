package tech.skala.action.integration.transport.azship;

import java.math.BigDecimal;

public class UpdateOrderNFDTO {

	private BigDecimal  						numNotaOR;
	private BigDecimal  						numNotaNFe;

	public BigDecimal getNumNotaOR() {
		return numNotaOR;
	}

	public void setNumNotaOR(BigDecimal numNotaOR) {
		this.numNotaOR = numNotaOR;
	}

	public BigDecimal getNumNotaNFe() {
		return numNotaNFe;
	}

	public void setNumNotaNFe(BigDecimal numNotaNFe) {
		this.numNotaNFe = numNotaNFe;
	}

	@Override
	public String toString() {
		return String.format("UpdateOrderNFDTO [numNotaOR=%s, numNotaNFe=%s]", numNotaOR, numNotaNFe);
	}
	
}
