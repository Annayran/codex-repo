package tech.skala.action.integration.tangerino.model.integracao;

import java.math.BigDecimal;

public class WorkScheduleTimetableDTO {
	private BigDecimal							day;
	private BigDecimal							endMainInterval;
	private BigDecimal							endShift1;
	private BigDecimal							endShift2;
	private BigDecimal							endShift3;
	private BigDecimal							endShift4;
	private BigDecimal							endShift5;
	private BigDecimal							endShift6;
	private BigDecimal							id;
	private String								mainInterval;
	private String								message;
	private BigDecimal							startMainInterval;
	private BigDecimal							startShift1;
	private BigDecimal							startShift2;
	private BigDecimal							startShift3;
	private BigDecimal							startShift4;
	private BigDecimal							startShift5;
	private BigDecimal							startShift6;
	
	public BigDecimal getDay() {
		return day;
	}
	public void setDay(BigDecimal day) {
		this.day = day;
	}
	public BigDecimal getEndMainInterval() {
		return endMainInterval;
	}
	public void setEndMainInterval(BigDecimal endMainInterval) {
		this.endMainInterval = endMainInterval;
	}
	public BigDecimal getEndShift1() {
		return endShift1;
	}
	public void setEndShift1(BigDecimal endShift1) {
		this.endShift1 = endShift1;
	}
	public BigDecimal getEndShift2() {
		return endShift2;
	}
	public void setEndShift2(BigDecimal endShift2) {
		this.endShift2 = endShift2;
	}
	public BigDecimal getEndShift3() {
		return endShift3;
	}
	public void setEndShift3(BigDecimal endShift3) {
		this.endShift3 = endShift3;
	}
	public BigDecimal getEndShift4() {
		return endShift4;
	}
	public void setEndShift4(BigDecimal endShift4) {
		this.endShift4 = endShift4;
	}
	public BigDecimal getEndShift5() {
		return endShift5;
	}
	public void setEndShift5(BigDecimal endShift5) {
		this.endShift5 = endShift5;
	}
	public BigDecimal getEndShift6() {
		return endShift6;
	}
	public void setEndShift6(BigDecimal endShift6) {
		this.endShift6 = endShift6;
	}
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public String getMainInterval() {
		return mainInterval;
	}
	public void setMainInterval(String mainInterval) {
		this.mainInterval = mainInterval;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public BigDecimal getStartMainInterval() {
		return startMainInterval;
	}
	public void setStartMainInterval(BigDecimal startMainInterval) {
		this.startMainInterval = startMainInterval;
	}
	public BigDecimal getStartShift1() {
		return startShift1;
	}
	public void setStartShift1(BigDecimal startShift1) {
		this.startShift1 = startShift1;
	}
	public BigDecimal getStartShift2() {
		return startShift2;
	}
	public void setStartShift2(BigDecimal startShift2) {
		this.startShift2 = startShift2;
	}
	public BigDecimal getStartShift3() {
		return startShift3;
	}
	public void setStartShift3(BigDecimal startShift3) {
		this.startShift3 = startShift3;
	}
	public BigDecimal getStartShift4() {
		return startShift4;
	}
	public void setStartShift4(BigDecimal startShift4) {
		this.startShift4 = startShift4;
	}
	public BigDecimal getStartShift5() {
		return startShift5;
	}
	public void setStartShift5(BigDecimal startShift5) {
		this.startShift5 = startShift5;
	}
	public BigDecimal getStartShift6() {
		return startShift6;
	}
	public void setStartShift6(BigDecimal startShift6) {
		this.startShift6 = startShift6;
	}
	
	@Override
	public String toString() {
		return String.format(
				"WorkScheduleTimetableDTO [day=%s, endMainInterval=%s, endShift1=%s, endShift2=%s, endShift3=%s, endShift4=%s, endShift5=%s, endShift6=%s, id=%s, mainInterval=%s, message=%s, startMainInterval=%s, startShift1=%s, startShift2=%s, startShift3=%s, startShift4=%s, startShift5=%s, startShift6=%s]",
				day, endMainInterval, endShift1, endShift2, endShift3, endShift4, endShift5, endShift6, id,
				mainInterval, message, startMainInterval, startShift1, startShift2, startShift3, startShift4,
				startShift5, startShift6);
	}
	
}
