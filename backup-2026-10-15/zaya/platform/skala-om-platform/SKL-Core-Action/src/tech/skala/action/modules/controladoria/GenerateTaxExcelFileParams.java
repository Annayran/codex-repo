package tech.skala.action.modules.controladoria;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class GenerateTaxExcelFileParams {

	private String type;
	private Timestamp initialDate;
	private Timestamp finalDate;
	private BigDecimal cfop;
	private String companyId;
	private BigDecimal stateFederationId;
	private BigDecimal operationTypeId;
	private BigDecimal ncm;
	private BigDecimal invoiceId;
	private BigDecimal partnerId;
	private String typeMovementId;
	private BigDecimal accountingAccountId;

	public GenerateTaxExcelFileParams(String type, Timestamp initialDate, Timestamp finalDate, BigDecimal cfop,
			String companyId, BigDecimal stateFederationId, BigDecimal operationTypeId, BigDecimal ncm,
			BigDecimal invoiceId, BigDecimal partnerId, String typeMovementId, BigDecimal accountingAccountId) {
		this.type = type;
		this.initialDate = initialDate;
		this.finalDate = finalDate;
		this.cfop = cfop;
		this.companyId = companyId;
		this.stateFederationId = stateFederationId;
		this.operationTypeId = operationTypeId;
		this.ncm = ncm;
		this.invoiceId = invoiceId;
		this.partnerId = partnerId;
		this.typeMovementId = typeMovementId;
		this.accountingAccountId = accountingAccountId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Timestamp getInitialDate() {
		return initialDate;
	}

	public void setInitialDate(Timestamp initialDate) {
		this.initialDate = initialDate;
	}

	public Timestamp getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(Timestamp finalDate) {
		this.finalDate = finalDate;
	}

	public BigDecimal getCfop() {
		return cfop;
	}

	public void setCfop(BigDecimal cfop) {
		this.cfop = cfop;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getStateFederationId() {
		return stateFederationId;
	}

	public void setStateFederationId(BigDecimal stateFederationId) {
		this.stateFederationId = stateFederationId;
	}

	public BigDecimal getOperationTypeId() {
		return operationTypeId;
	}

	public void setOperationTypeId(BigDecimal operationTypeId) {
		this.operationTypeId = operationTypeId;
	}

	public BigDecimal getNcm() {
		return ncm;
	}

	public void setNcm(BigDecimal ncm) {
		this.ncm = ncm;
	}

	public BigDecimal getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(BigDecimal invoiceId) {
		this.invoiceId = invoiceId;
	}

	public BigDecimal getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(BigDecimal partnerId) {
		this.partnerId = partnerId;
	}

	public String getTypeMovementId() {
		return typeMovementId;
	}

	public void setTypeMovementId(String typeMovementId) {
		this.typeMovementId = typeMovementId;
	}

	public BigDecimal getAccountingAccountId() {
		return accountingAccountId;
	}

	public void setAccountingAccountId(BigDecimal accountingAccountId) {
		this.accountingAccountId = accountingAccountId;
	}

	@Override
	public String toString() {
		return "GenerateTaxExcelFileParams [type=" + type + ", initialDate=" + initialDate + ", finalDate=" + finalDate
				+ ", cfop=" + cfop + ", companyId=" + companyId + ", stateFederationId=" + stateFederationId
				+ ", operationTypeId=" + operationTypeId + ", ncm=" + ncm + ", invoiceId=" + invoiceId + ", partnerId="
				+ partnerId + ", typeMovementId=" + typeMovementId + ", accountingAccountId=" + accountingAccountId
				+ "]";
	}

}
