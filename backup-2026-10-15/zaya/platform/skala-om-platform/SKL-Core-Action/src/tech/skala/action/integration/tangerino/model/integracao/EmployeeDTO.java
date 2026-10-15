package tech.skala.action.integration.tangerino.model.integracao;

import java.math.BigDecimal;

import tech.skala.core.commons.dd.DTO;

public class EmployeeDTO implements DTO {
	private transient BigDecimal				codEmp;
	private transient BigDecimal				codFunc;
	private BigDecimal							admissionDate;
	private BigDecimal							birthDate;
	private String								ci;
	private BigDecimal							company;
	private String								companyExternalId;
	private String								costCenter;
	private String								cpf;
	private String								ctps;
	private BigDecimal							effectiveDate;
	private String 								email;
	private String 								externalId;
	private boolean								foreignNational;
	private String								functionExternalId;
	private BigDecimal							functionId;
	private BigDecimal							id;
	private boolean								intern;
	private BigDecimal							jobRole;
	private String								jobRoleDescription;
	private String								jobRoleExternalId;
	private String								message;
	private String 								name;
	private String 								phone;
	private String 								pis;
	private BigDecimal							punchRuleDateInMillis;
	private String								punchRuleExternalId;
	private String								series;
	private String								state;
	private BigDecimal							tangerinoId;
	private String								timeZone;
	private BigDecimal							workSchedule;
	private BigDecimal							workScheduleDateInMillis;
	private String 								workScheduleExternalId;
	private BigDecimal							workplace;
	private String 								workplaceExternalId;
	
	public BigDecimal getCodEmp() {
		return codEmp;
	}
	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}
	public BigDecimal getCodFunc() {
		return codFunc;
	}
	public void setCodFunc(BigDecimal codFunc) {
		this.codFunc = codFunc;
	}
	public BigDecimal getAdmissionDate() {
		return admissionDate;
	}
	public void setAdmissionDate(BigDecimal admissionDate) {
		this.admissionDate = admissionDate;
	}
	public BigDecimal getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(BigDecimal birthDate) {
		this.birthDate = birthDate;
	}
	public String getCi() {
		return ci;
	}
	public void setCi(String ci) {
		this.ci = ci;
	}
	public BigDecimal getCompany() {
		return company;
	}
	public void setCompany(BigDecimal company) {
		this.company = company;
	}
	public String getCompanyExternalId() {
		return companyExternalId;
	}
	public void setCompanyExternalId(String companyExternalId) {
		this.companyExternalId = companyExternalId;
	}
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getCtps() {
		return ctps;
	}
	public void setCtps(String ctps) {
		this.ctps = ctps;
	}
	public BigDecimal getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(BigDecimal effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public boolean isForeignNational() {
		return foreignNational;
	}
	public void setForeignNational(boolean foreignNational) {
		this.foreignNational = foreignNational;
	}
	public String getFunctionExternalId() {
		return functionExternalId;
	}
	public void setFunctionExternalId(String functionExternalId) {
		this.functionExternalId = functionExternalId;
	}
	public BigDecimal getFunctionId() {
		return functionId;
	}
	public void setFunctionId(BigDecimal functionId) {
		this.functionId = functionId;
	}
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public boolean isIntern() {
		return intern;
	}
	public void setIntern(boolean intern) {
		this.intern = intern;
	}
	public BigDecimal getJobRole() {
		return jobRole;
	}
	public void setJobRole(BigDecimal jobRole) {
		this.jobRole = jobRole;
	}
	public String getJobRoleDescription() {
		return jobRoleDescription;
	}
	public void setJobRoleDescription(String jobRoleDescription) {
		this.jobRoleDescription = jobRoleDescription;
	}
	public String getJobRoleExternalId() {
		return jobRoleExternalId;
	}
	public void setJobRoleExternalId(String jobRoleExternalId) {
		this.jobRoleExternalId = jobRoleExternalId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPis() {
		return pis;
	}
	public void setPis(String pis) {
		this.pis = pis;
	}
	public BigDecimal getPunchRuleDateInMillis() {
		return punchRuleDateInMillis;
	}
	public void setPunchRuleDateInMillis(BigDecimal punchRuleDateInMillis) {
		this.punchRuleDateInMillis = punchRuleDateInMillis;
	}
	public String getPunchRuleExternalId() {
		return punchRuleExternalId;
	}
	public void setPunchRuleExternalId(String punchRuleExternalId) {
		this.punchRuleExternalId = punchRuleExternalId;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public BigDecimal getTangerinoId() {
		return tangerinoId;
	}
	public void setTangerinoId(BigDecimal tangerinoId) {
		this.tangerinoId = tangerinoId;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public BigDecimal getWorkSchedule() {
		return workSchedule;
	}
	public void setWorkSchedule(BigDecimal workSchedule) {
		this.workSchedule = workSchedule;
	}
	public BigDecimal getWorkScheduleDateInMillis() {
		return workScheduleDateInMillis;
	}
	public void setWorkScheduleDateInMillis(BigDecimal workScheduleDateInMillis) {
		this.workScheduleDateInMillis = workScheduleDateInMillis;
	}
	public String getWorkScheduleExternalId() {
		return workScheduleExternalId;
	}
	public void setWorkScheduleExternalId(String workScheduleExternalId) {
		this.workScheduleExternalId = workScheduleExternalId;
	}
	public BigDecimal getWorkplace() {
		return workplace;
	}
	public void setWorkplace(BigDecimal workplace) {
		this.workplace = workplace;
	}
	public String getWorkplaceExternalId() {
		return workplaceExternalId;
	}
	public void setWorkplaceExternalId(String workplaceExternalId) {
		this.workplaceExternalId = workplaceExternalId;
	}
	
	@Override
	public String toString() {
		return String.format(
				"EmployeeDTO [codEmp=%s, codFunc=%s, admissionDate=%s, birthDate=%s, ci=%s, company=%s, companyExternalId=%s, costCenter=%s, cpf=%s, ctps=%s, effectiveDate=%s, email=%s, externalId=%s, foreignNational=%s, functionExternalId=%s, functionId=%s, id=%s, intern=%s, jobRole=%s, jobRoleDescription=%s, jobRoleExternalId=%s, message=%s, name=%s, phone=%s, pis=%s, punchRuleDateInMillis=%s, punchRuleExternalId=%s, series=%s, state=%s, tangerinoId=%s, timeZone=%s, workSchedule=%s, workScheduleDateInMillis=%s, workScheduleExternalId=%s, workplace=%s, workplaceExternalId=%s]",
				codEmp, codFunc, admissionDate, birthDate, ci, company, companyExternalId, costCenter, cpf, ctps,
				effectiveDate, email, externalId, foreignNational, functionExternalId, functionId, id, intern, jobRole,
				jobRoleDescription, jobRoleExternalId, message, name, phone, pis, punchRuleDateInMillis,
				punchRuleExternalId, series, state, tangerinoId, timeZone, workSchedule, workScheduleDateInMillis,
				workScheduleExternalId, workplace, workplaceExternalId);
	}

}
