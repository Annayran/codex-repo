package tech.skala.action.integration.tangerino.model.integracao;

import java.math.BigDecimal;

public class CompanyReturnDTO {
	private String 								accountStatus;
	private String								descriptionName;
	private String								externalId;
	private String								fantasyName;
	private BigDecimal							id;
	private String								sponsor;
	private String								sponsorEmail;
	private boolean								standard;
	
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getDescriptionName() {
		return descriptionName;
	}
	public void setDescriptionName(String descriptionName) {
		this.descriptionName = descriptionName;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getFantasyName() {
		return fantasyName;
	}
	public void setFantasyName(String fantasyName) {
		this.fantasyName = fantasyName;
	}
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public String getSponsor() {
		return sponsor;
	}
	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}
	public String getSponsorEmail() {
		return sponsorEmail;
	}
	public void setSponsorEmail(String sponsorEmail) {
		this.sponsorEmail = sponsorEmail;
	}
	public boolean isStandard() {
		return standard;
	}
	public void setStandard(boolean standard) {
		this.standard = standard;
	}
	
	@Override
	public String toString() {
		return String.format(
				"CompanyReturnDTO [accountStatus=%s, descriptionName=%s, externalId=%s, fantasyName=%s, id=%s, sponsor=%s, sponsorEmail=%s, standard=%s]",
				accountStatus, descriptionName, externalId, fantasyName, id, sponsor, sponsorEmail, standard);
	}
	
}
