package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;

import tech.skala.core.commons.dd.DTO;

public class LocalDateTimeDTO implements DTO {
	private BigDecimal							year;
	private BigDecimal							monthValue;
	private BigDecimal							dayOfMonth;
	private BigDecimal							hour;
	private BigDecimal							minute;
	private BigDecimal							second;
	
	public BigDecimal getYear() {
		return year;
	}
	public void setYear(BigDecimal year) {
		this.year = year;
	}
	public BigDecimal getMonthValue() {
		return monthValue;
	}
	public void setMonthValue(BigDecimal monthValue) {
		this.monthValue = monthValue;
	}
	public BigDecimal getDayOfMonth() {
		return dayOfMonth;
	}
	public void setDayOfMonth(BigDecimal dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}
	public BigDecimal getHour() {
		return hour;
	}
	public void setHour(BigDecimal hour) {
		this.hour = hour;
	}
	public BigDecimal getMinute() {
		return minute;
	}
	public void setMinute(BigDecimal minute) {
		this.minute = minute;
	}
	public BigDecimal getSecond() {
		return second;
	}
	public void setSecond(BigDecimal second) {
		this.second = second;
	}
	
	@Override
	public String toString() {
		return String.format("LocalDateTimeDTO [year=%s, monthValue=%s, dayOfMonth=%s, hour=%s, minute=%s, second=%s]",
				year, monthValue, dayOfMonth, hour, minute, second);
	}

}
