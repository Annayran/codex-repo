package tech.skala.action.integration.tangerino.model.integracao;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.EntityPrimaryKey;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import com.google.gson.annotations.SerializedName;
import tech.skala.action.integration.tangerino.model.sistema.MensagemLog;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.EntityUpdate;

import java.math.BigDecimal;

public class Colaborador implements EntityBean, Persistable {
	public static final String ENTITY_NAME = DynamicEntityNames.FUNCIONARIO;

	private transient BigDecimal codFuncionario;
	private transient BigDecimal codEmpresa;

	@SerializedName(value = "admissionDate")
	private Long dataAdmissao;

	@SerializedName(value = "birthDate")
	private Long dataNascimento;
	
	private String ci;
	private BigDecimal company;
	private BigDecimal companyExternalId;
	//costCenter

	@SerializedName(value = "cpf")
	private String cpf;
	
	@SerializedName(value = "ctps")
	private String ctps;
	
	@SerializedName(value = "effectiveDate")
	private Long dataEfetivacao;
	
	@SerializedName(value = "email")
	private String email;
	
	@SerializedName(value = "externalId")
	private String matricula;
	
	//foreignNational
	
	@SerializedName(value = "id")
	private BigDecimal idTangerino;
	
	//intern

	private BigDecimal jobRole;
	private String jobRoleDescription;
	private String jobRoleExternalId;

	//message
	//modulePro
	//modulePro
	
	@SerializedName(value = "name")
	private String  nomeColaborador;

	@SerializedName(value = "phone")
	private String telefone;

	@SerializedName(value = "pis")
	private String pis;
	
	//punchRuleDateInMillis
	//punchRuleExternalId
	
	@SerializedName(value = "series")
	private String series;
	
	//state
	//tangerinoId

	@SerializedName(value = "timeZone")
	private String timeZone;
	
	@SerializedName(value = "workSchedule")
	private BigDecimal escala;
	
	//workScheduleDateInMillis
	//workScheduleExternalId

	@SerializedName(value = "workplace")
	private BigDecimal setor;
	
	//workplaceExternalId

	public BigDecimal getCodFuncionario() {
		return codFuncionario;
	}

	public void setCodFuncionario(BigDecimal codFuncionario) {
		this.codFuncionario = codFuncionario;
	}

	public BigDecimal getCodEmpresa() {
		return codEmpresa;
	}

	public void setCodEmpresa(BigDecimal codEmpresa) {
		this.codEmpresa = codEmpresa;
	}

	public Long getDataAdmissao() {
		return dataAdmissao;
	}

	public void setDataAdmissao(Long dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

	public Long getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Long dataNascimento) {
		this.dataNascimento = dataNascimento;
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

	public BigDecimal getCompanyExternalId() {
		return companyExternalId;
	}

	public void setCompanyExternalId(BigDecimal companyExternalId) {
		this.companyExternalId = companyExternalId;
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

	public Long getDataEfetivacao() {
		return dataEfetivacao;
	}

	public void setDataEfetivacao(Long dataEfetivacao) {
		this.dataEfetivacao = dataEfetivacao;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public BigDecimal getIdTangerino() {
		return idTangerino;
	}

	public void setIdTangerino(BigDecimal idTangerino) {
		this.idTangerino = idTangerino;
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

	public String getNomeColaborador() {
		return nomeColaborador;
	}

	public void setNomeColaborador(String nomeColaborador) {
		this.nomeColaborador = nomeColaborador;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getPis() {
		return pis;
	}

	public void setPis(String pis) {
		this.pis = pis;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public BigDecimal getEscala() {
		return escala;
	}

	public void setEscala(BigDecimal escala) {
		this.escala = escala;
	}

	public BigDecimal getSetor() {
		return setor;
	}

	public void setSetor(BigDecimal setor) {
		this.setor = setor;
	}

	@Override
	public DynamicVO persists() throws DataDictionaryExceptions.UpdateNotPossibleException {
		JapeSession.SessionHandle hnd = null;
		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);
			hnd.setPriorityLevel(JapeSession.LOW_PRIORITY);
			hnd.execWithTX(new JapeSession.TXBlock() {
				@Override
				public void doWithTx() throws Exception {
					EntityPrimaryKey pk = new EntityPrimaryKey(new Object[] { codEmpresa, codFuncionario });
					EntityUpdate entityUpd = new EntityUpdate(ENTITY_NAME, pk);
					entityUpd.setField("AD_ID_TANGERINO", idTangerino);
					entityUpd.setField("AD_SINC_TANGERINO", "N");
					entityUpd.fluidUpdate();
				}
			});
		} catch (Exception e) {
			new LogItem("Erro ao salvar colaborador: ", e.getMessage(), "C", MensagemLog.ERRO);
		} finally {
			JapeSession.close(hnd);
		}
		return null;
	}

	@Override
	public String toString() {
		return String.format(
				"Colaborador [dataAdmissao=%s, dataNascimento=%s, ci=%s, company=%s, companyExternalId=%s, cpf=%s, ctps=%s, dataEfetivacao=%s, email=%s, matricula=%s, idTangerino=%s, jobRole=%s, jobRoleDescription=%s, jobRoleExternalId=%s, nomeColaborador=%s, telefone=%s, pis=%s, series=%s, timeZone=%s, escala=%s, setor=%s]",
				dataAdmissao, dataNascimento, ci, company, companyExternalId, cpf, ctps, dataEfetivacao, email,
				matricula, idTangerino, jobRole, jobRoleDescription, jobRoleExternalId, nomeColaborador, telefone, pis,
				series, timeZone, escala, setor);
	}
}
