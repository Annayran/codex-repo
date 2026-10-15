package tech.skala.action.integration.tangerino.model.integracao;

import java.math.BigDecimal;

public class EmployerDTO {
	private String 								accountStatus;
	private String 								channelName;
	private String 								companyName;
	private String 								countryOrigin;
	private String 								currentlyPerformsPunch;
	private String 								document;
	private String 								email;
	private String 								employeeRange;
	private String 								externalCode;
	private BigDecimal 							id;
	private String 								message;
	private String 								origin;
	private String 								password;
	private String 								phone;
	private String 								planExternalCode;
	private String 								planNumberLicenses;
	private String 								platform;
	private boolean 							receiveNews;
	private String 								role;
	private String 								sellerCode;
	private String 								sponsorName;
	private String 								token;
	
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCountryOrigin() {
		return countryOrigin;
	}
	public void setCountryOrigin(String countryOrigin) {
		this.countryOrigin = countryOrigin;
	}
	public String getCurrentlyPerformsPunch() {
		return currentlyPerformsPunch;
	}
	public void setCurrentlyPerformsPunch(String currentlyPerformsPunch) {
		this.currentlyPerformsPunch = currentlyPerformsPunch;
	}
	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmployeeRange() {
		return employeeRange;
	}
	public void setEmployeeRange(String employeeRange) {
		this.employeeRange = employeeRange;
	}
	public String getExternalCode() {
		return externalCode;
	}
	public void setExternalCode(String externalCode) {
		this.externalCode = externalCode;
	}
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPlanExternalCode() {
		return planExternalCode;
	}
	public void setPlanExternalCode(String planExternalCode) {
		this.planExternalCode = planExternalCode;
	}
	public String getPlanNumberLicenses() {
		return planNumberLicenses;
	}
	public void setPlanNumberLicenses(String planNumberLicenses) {
		this.planNumberLicenses = planNumberLicenses;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public boolean isReceiveNews() {
		return receiveNews;
	}
	public void setReceiveNews(boolean receiveNews) {
		this.receiveNews = receiveNews;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getSellerCode() {
		return sellerCode;
	}
	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}
	public String getSponsorName() {
		return sponsorName;
	}
	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	@Override
	public String toString() {
		return String.format(
				"EmployerDTO [accountStatus=%s, channelName=%s, companyName=%s, countryOrigin=%s, currentlyPerformsPunch=%s, document=%s, email=%s, employeeRange=%s, externalCode=%s, id=%s, message=%s, origin=%s, password=%s, phone=%s, planExternalCode=%s, planNumberLicenses=%s, platform=%s, receiveNews=%s, role=%s, sellerCode=%s, sponsorName=%s, token=%s]",
				accountStatus, channelName, companyName, countryOrigin, currentlyPerformsPunch, document, email,
				employeeRange, externalCode, id, message, origin, password, phone, planExternalCode, planNumberLicenses,
				platform, receiveNews, role, sellerCode, sponsorName, token);
	}
	
}
