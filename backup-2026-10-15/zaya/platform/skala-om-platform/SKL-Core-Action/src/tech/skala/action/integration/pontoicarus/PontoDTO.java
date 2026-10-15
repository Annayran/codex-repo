package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;

import tech.skala.core.commons.dd.DTO;

public class PontoDTO implements DTO {
	private BigDecimal							total;
	private String								batidas;
	private BigDecimal							normal;
	private BigDecimal							faltas;
	private BigDecimal							noturna;
	private BigDecimal							atestado;
	private BigDecimal							trabalhado;
	private String								dtmov;
	private String								codemp;
	private String								codfunc;
	
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public String getBatidas() {
		return batidas;
	}
	public void setBatidas(String batidas) {
		this.batidas = batidas;
	}
	public BigDecimal getNormal() {
		return normal;
	}
	public void setNormal(BigDecimal normal) {
		this.normal = normal;
	}
	public BigDecimal getFaltas() {
		return faltas;
	}
	public void setFaltas(BigDecimal faltas) {
		this.faltas = faltas;
	}
	public BigDecimal getNoturna() {
		return noturna;
	}
	public void setNoturna(BigDecimal noturna) {
		this.noturna = noturna;
	}
	public BigDecimal getAtestado() {
		return atestado;
	}
	public void setAtestado(BigDecimal atestado) {
		this.atestado = atestado;
	}
	public BigDecimal getTrabalhado() {
		return trabalhado;
	}
	public void setTrabalhado(BigDecimal trabalhado) {
		this.trabalhado = trabalhado;
	}
	public String getDtmov() {
		return dtmov;
	}
	public void setDtmov(String dtmov) {
		this.dtmov = dtmov;
	}
	public String getCodemp() {
		return codemp;
	}
	public void setCodemp(String codemp) {
		this.codemp = codemp;
	}
	public String getCodfunc() {
		return codfunc;
	}
	public void setCodfunc(String codfunc) {
		this.codfunc = codfunc;
	}
	
	@Override
	public String toString() {
		return String.format(
				"PontoDTO [total=%s, batidas=%s, normal=%s, faltas=%s, noturna=%s, atestado=%s, trabalhado=%s, dtmov=%s, codemp=%s, codfunc=%s]",
				total, batidas, normal, faltas, noturna, atestado, trabalhado, dtmov, codemp, codfunc);
	}

}
