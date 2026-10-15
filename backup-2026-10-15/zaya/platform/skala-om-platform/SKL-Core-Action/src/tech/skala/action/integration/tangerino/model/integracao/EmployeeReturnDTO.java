package tech.skala.action.integration.tangerino.model.integracao;

import java.math.BigDecimal;
import java.util.List;

public class EmployeeReturnDTO {
	private String 								admissionDate;
	private String 								birthDate;
	private boolean								canViewWorkgroup;
	private CompanyReturnDTO					company;
	private String								cpf;
	private String								ctps;
	private WorkScheduleReturnDTO				currentWorkSchedule;
	private WorkplaceReturnDTO					currentWorkplaceDTO;
	private String 								effectiveDate;
	private String 								email;
	private String 								externalId;
	private boolean								fired;
	private BigDecimal							id;
	private JobRoleDTO							jobRoleDTO;
	private ManagerReturnDTO					lastManager;
	private List<ManagerReturnDTO>				managers;
	private String 								message;
	private String 								name;
	private String 								phone;
	private String 								pin;
	private String 								pis;
	private String 								resignationDate;
	private String 								series;
	private String 								state;
	private String 								updateDate;
	private List<WorkScheduleDTO>				workScheduleList;
	private List<WorkplaceReturnDTO>			workplaceList;
	
	public String getAdmissionDate() {
		return admissionDate;
	}
	public void setAdmissionDate(String admissionDate) {
		this.admissionDate = admissionDate;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public boolean isCanViewWorkgroup() {
		return canViewWorkgroup;
	}
	public void setCanViewWorkgroup(boolean canViewWorkgroup) {
		this.canViewWorkgroup = canViewWorkgroup;
	}
	public CompanyReturnDTO getCompany() {
		return company;
	}
	public void setCompany(CompanyReturnDTO company) {
		this.company = company;
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
	public WorkScheduleReturnDTO getCurrentWorkSchedule() {
		return currentWorkSchedule;
	}
	public void setCurrentWorkSchedule(WorkScheduleReturnDTO currentWorkSchedule) {
		this.currentWorkSchedule = currentWorkSchedule;
	}
	public WorkplaceReturnDTO getCurrentWorkplaceDTO() {
		return currentWorkplaceDTO;
	}
	public void setCurrentWorkplaceDTO(WorkplaceReturnDTO currentWorkplaceDTO) {
		this.currentWorkplaceDTO = currentWorkplaceDTO;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
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
	public boolean isFired() {
		return fired;
	}
	public void setFired(boolean fired) {
		this.fired = fired;
	}
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public JobRoleDTO getJobRoleDTO() {
		return jobRoleDTO;
	}
	public void setJobRoleDTO(JobRoleDTO jobRoleDTO) {
		this.jobRoleDTO = jobRoleDTO;
	}
	public ManagerReturnDTO getLastManager() {
		return lastManager;
	}
	public void setLastManager(ManagerReturnDTO lastManager) {
		this.lastManager = lastManager;
	}
	public List<ManagerReturnDTO> getManagers() {
		return managers;
	}
	public void setManagers(List<ManagerReturnDTO> managers) {
		this.managers = managers;
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
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getPis() {
		return pis;
	}
	public void setPis(String pis) {
		this.pis = pis;
	}
	public String getResignationDate() {
		return resignationDate;
	}
	public void setResignationDate(String resignationDate) {
		this.resignationDate = resignationDate;
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
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public List<WorkScheduleDTO> getWorkScheduleList() {
		return workScheduleList;
	}
	public void setWorkScheduleList(List<WorkScheduleDTO> workScheduleList) {
		this.workScheduleList = workScheduleList;
	}
	public List<WorkplaceReturnDTO> getWorkplaceList() {
		return workplaceList;
	}
	public void setWorkplaceList(List<WorkplaceReturnDTO> workplaceList) {
		this.workplaceList = workplaceList;
	}
	
	@Override
	public String toString() {
		return String.format(
				"EmployeeReturnDTO [admissionDate=%s, birthDate=%s, canViewWorkgroup=%s, company=%s, cpf=%s, ctps=%s, currentWorkSchedule=%s, currentWorkplaceDTO=%s, effectiveDate=%s, email=%s, externalId=%s, fired=%s, id=%s, jobRoleDTO=%s, lastManager=%s, managers=%s, message=%s, name=%s, phone=%s, pin=%s, pis=%s, resignationDate=%s, series=%s, state=%s, updateDate=%s, workScheduleList=%s, workplaceList=%s]",
				admissionDate, birthDate, canViewWorkgroup, company, cpf, ctps, currentWorkSchedule,
				currentWorkplaceDTO, effectiveDate, email, externalId, fired, id, jobRoleDTO, lastManager, managers,
				message, name, phone, pin, pis, resignationDate, series, state, updateDate, workScheduleList,
				workplaceList);
	}
	
}
