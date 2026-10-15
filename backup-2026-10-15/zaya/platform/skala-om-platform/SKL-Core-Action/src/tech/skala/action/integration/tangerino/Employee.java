package tech.skala.action.integration.tangerino;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.action.modules.pessoas.funcionario.Funcao;
import tech.skala.action.modules.pessoas.funcionario.Funcionario;
import tech.skala.action.modules.pessoas.funcionario.FuncionarioFinder;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class Employee extends Funcionario {
	private Timestamp							effectiveDate;
	private BigDecimal							id;
	private BigDecimal							tangerinoId;
	private Funcao								funcao;
	private Company								companyBean;
	
	public Employee() {
		super();
		this.effectiveDate = TimeUtils.buildData(1, 12, 2022);
	}
	
	public Timestamp getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Timestamp effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public BigDecimal getTangerinoId() {
		return tangerinoId;
	}
	public void setTangerinoId(BigDecimal tangerinoId) {
		this.tangerinoId = tangerinoId;
	}
	public Funcao getFuncao() {
		return funcao;
	}
	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}
	public Company getCompanyBean() {
		return companyBean;
	}
	public void setCompanyBean(Company company) {
		this.companyBean = company;
	}
	public BigDecimal getCodigoExternoFuncao() {
		return (funcao != null) ? funcao.getCodFuncao() : null;
	}
	public BigDecimal getCodigoTangerinoFuncao() {
		return (funcao != null) ? funcao.getTangerinoId() : null;
	}
	public BigDecimal getCodigoExternoFilial() {
		return (companyBean.getCodEmp() != null) ? companyBean.getCodEmp() : null;
	}
	public BigDecimal getCodigoTangerinoFilial() {
		return (companyBean.getTangerinoId() != null) ? companyBean.getTangerinoId() : null;
	}
	
	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		super.loadByVO(vo);
		
		this.id = vo.asBigDecimal("AD_ID_TANGERINO");
		this.tangerinoId = vo.asBigDecimal("AD_ID_TANGERINO");
		this.funcao = FuncionarioFinder.buscaFuncaoByPk(super.getCodFuncao());
		this.companyBean = FuncionarioFinder.buscaEmpresaByPk(super.getCodEmp());
	}

	@Override
	public String toString() {
		return String.format(
				"Employee [getCodEmp()=%s, getCodFunc()=%s, getCelular()=%s, getCodCargaHor()=%s, getCodCargo()=%s, getCodFuncao()=%s, getDtAdm()=%s, getDtNasc()=%s, getIdentidade()=%s, getCpf()=%s, getEmail()=%s, getNacionalidade()=%s, getNomeFunc()=%s, getNumCps()=%s, getPis()=%s, getSerieCps()=%s, getTelefone()=%s, getUfCps()=%s, effectiveDate=%s, id=%s, tangerinoId=%s, funcao=%s, company=%s]",
				getCodEmp(), getCodFunc(), getCelular(), getCodCargaHor(), getCodCargo(), getCodFuncao(), getDtAdm(),
				getDtNasc(), getIdentidade(), getCpf(), getEmail(), getNacionalidade(), getNomeFunc(), getNumCps(),
				getPis(), getSerieCps(), getTelefone(), getUfCps(), effectiveDate, id, tangerinoId, funcao, companyBean);
	}

}
