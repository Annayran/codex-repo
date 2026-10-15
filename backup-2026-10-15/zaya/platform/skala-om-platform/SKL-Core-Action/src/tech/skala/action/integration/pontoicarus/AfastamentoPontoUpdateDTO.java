package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;
import java.sql.Timestamp;

import tech.skala.core.commons.dd.DTO;

public class AfastamentoPontoUpdateDTO implements DTO {
	private Timestamp							diaFim;
	private BigDecimal							idAfastamentoPonto;
	private String								justificativa;
	
	public Timestamp getDiaFim() {
		return diaFim;
	}
	public void setDiaFim(Timestamp diaFim) {
		this.diaFim = diaFim;
	}
	public BigDecimal getIdAfastamentoPonto() {
		return idAfastamentoPonto;
	}
	public void setIdAfastamentoPonto(BigDecimal idAfastamentoPonto) {
		this.idAfastamentoPonto = idAfastamentoPonto;
	}
	public String getJustificativa() {
		return justificativa;
	}
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}
	
	@Override
	public String toString() {
		return String.format("AfastamentoPontoUpdateDTO [diaFim=%s, idAfastamentoPonto=%s, justificativa=%s]", diaFim,
				idAfastamentoPonto, justificativa);
	}

}
