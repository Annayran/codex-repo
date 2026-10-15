package tech.skala.action.beans.funcionario;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.action.beans.cargo.Cargo;
import tech.skala.action.beans.cargo.CargoFinder;
import tech.skala.action.beans.departamento.Departamento;
import tech.skala.action.beans.departamento.DepartamentoFinder;
import tech.skala.action.beans.endereco.Cidade;
import tech.skala.action.beans.endereco.EnderecoFinder;
import tech.skala.action.integration.tangerino.utils.ConversorUtils;
import tech.skala.action.modules.empresas.Empresa;
import tech.skala.action.modules.empresas.EmpresaFinder;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByPrimaryKey;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

/**
 * A classe {@code Funcionario} representa a tabela {@code TFPFUN}.
 *  
 * <p>
 * A classe {@code Funcionario} representa um funcionario ou colaborador 
 * no modelo de negocios. Informacoes relevantes da propria entidade e de 
 * entidades relacionadas sao tratadas aqui.
 * </p> 
 * 
 * @author cassiomenezes
 * @since 2023-04-16
 */
public class Funcionario implements EntityBean, LoadableByPrimaryKey, LoadableByValueObject {
	public static final String					ENTITY_NAME = DynamicEntityNames.FUNCIONARIO;

	private Empresa								empresa;
	private BigDecimal							codFunc;
	private String								nomeFunc;
	private Timestamp							dtNasc;
	private String								nomeMae;
	private String								nomePai;
	private BigDecimal							racaFuncionario;
	private String								sexo;
	private BigDecimal							nacionalidade;
	private Cidade								cidadeNascimento;
	private String								cpf;
	private String								identidade;
	private String								pis;
	private BigDecimal							estadoCivil;
	private String								celular;
	private String								telefone;
	private String								email;
	private BigDecimal							nivEsc;
	private Timestamp							dtAdm;
	private BigDecimal							matricula;
	private Cargo								cargo;
	private Departamento						departamento;
	private BigDecimal							numCps;
	private String								serieCps;
	private BigDecimal							ufCps;
	private BigDecimal							vinculo;
	private String								situacao;
	private BigDecimal							regimeTrab;
	private BigDecimal							codCargaHor;
	private boolean								dispensaPonto;
	private Timestamp							dtDem;
	private TipoRescisao						tipoRescisao;

	public Funcionario() {
		// tratativa para o erro:
		// Failed to instantiate proxied instance of
		// tech.skala.action.beans.funcionario.Funcionario. Ensure that
		// tech.skala.action.beans.funcionario.Funcionario has a non-private
		// constructor.
		super();
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public BigDecimal getCodEmp() {
		return empresa.getCodEmp();
	}

	public BigDecimal getCodFunc() {
		return codFunc;
	}

	public void setCodFunc(BigDecimal codFunc) {
		this.codFunc = codFunc;
	}

	public String getNomeFunc() {
		return nomeFunc;
	}

	public void setNomeFunc(String nomeFunc) {
		this.nomeFunc = nomeFunc;
	}

	public Timestamp getDtNasc() {
		return dtNasc;
	}

	public void setDtNasc(Timestamp dtNasc) {
		this.dtNasc = dtNasc;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public BigDecimal getRacaFuncionario() {
		return racaFuncionario;
	}

	public void setRacaFuncionario(BigDecimal racaFuncionario) {
		this.racaFuncionario = racaFuncionario;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public BigDecimal getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(BigDecimal nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public Cidade getCidadeNascimento() {
		return cidadeNascimento;
	}

	public void setCidadeNascimento(Cidade cidadeNascimento) {
		this.cidadeNascimento = cidadeNascimento;
	}

	public String getNomeCidadeNascimento() {
		return (nacionalidade.intValue() == 10) ? cidadeNascimento.getNomeCid() : null;
	}

	public String getSiglaUfNascimento() {
		return (nacionalidade.intValue() == 10) ? cidadeNascimento.getSiglaUf() : null;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getIdentidade() {
		return identidade;
	}

	public void setIdentidade(String identidade) {
		this.identidade = identidade;
	}

	public String getPis() {
		return pis;
	}

	public void setPis(String pis) {
		this.pis = pis;
	}

	public BigDecimal getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(BigDecimal estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getNivEsc() {
		return nivEsc;
	}

	public void setNivEsc(BigDecimal nivEsc) {
		this.nivEsc = nivEsc;
	}

	public Timestamp getDtAdm() {
		return dtAdm;
	}

	public void setDtAdm(Timestamp dtAdm) {
		this.dtAdm = dtAdm;
	}

	public BigDecimal getMatricula() {
		return matricula;
	}

	public void setMatricula(BigDecimal matricula) {
		this.matricula = matricula;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public BigDecimal getCodCargo() {
		return cargo.getCodCargo();
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public BigDecimal getCodDep() {
		return departamento.getCodDep();
	}

	public BigDecimal getNumCps() {
		return numCps;
	}

	public void setNumCps(BigDecimal numCps) {
		this.numCps = numCps;
	}

	public String getSerieCps() {
		return serieCps;
	}

	public void setSerieCps(String serieCps) {
		this.serieCps = serieCps;
	}

	public BigDecimal getUfCps() {
		return ufCps;
	}

	public void setUfCps(BigDecimal ufCps) {
		this.ufCps = ufCps;
	}

	public BigDecimal getVinculo() {
		return vinculo;
	}

	public void setVinculo(BigDecimal vinculo) {
		this.vinculo = vinculo;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public BigDecimal getRegimeTrab() {
		return regimeTrab;
	}

	public void setRegimeTrab(BigDecimal regimeTrab) {
		this.regimeTrab = regimeTrab;
	}

	public BigDecimal getCodCargaHor() {
		return codCargaHor;
	}

	public void setCodCargaHor(BigDecimal codCargaHor) {
		this.codCargaHor = codCargaHor;
	}

	public boolean isDispensaPonto() {
		return dispensaPonto;
	}

	public void setDispensaPonto(boolean dispensaPonto) {
		this.dispensaPonto = dispensaPonto;
	}

	public Timestamp getDtDem() {
		return dtDem;
	}

	public void setDtDem(Timestamp dtDem) {
		this.dtDem = dtDem;
	}

	public TipoRescisao getTipoRescisao() {
		return tipoRescisao;
	}

	public void setTipoRescisao(TipoRescisao tipoRescisao) {
		this.tipoRescisao = tipoRescisao;
	}

	public BigDecimal getCodTpr() {
		return tipoRescisao.getCodTpr();
	}

	public String getDescrTipResc() {
		return tipoRescisao.getDescrTipResc();
	}

	@Override
	public void loadByPk(Object... keyValue) throws RecordNotFoundException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);
		try {
			DynamicVO vo = dao.findByPK(keyValue);
			loadByVO(vo);
		} catch (Exception e) {
			throw new RecordNotFoundException(e);
		}
	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.empresa = EmpresaFinder.findByPk(vo.asBigDecimal(SklFieldName.CODEMP));
		this.codFunc = vo.asBigDecimal(SklFieldName.CODFUNC);
		this.nomeFunc = ConversorUtils.semAcento(vo.asString(SklFieldName.NOMEFUNC));

		if (vo.asTimestamp("DTNASC") != null)
			this.dtNasc = TimeUtils.clearTime(vo.asTimestamp("DTNASC"));

		if (vo.asString("NOMEMAE") != null)
			this.nomeMae = ConversorUtils.semAcento(vo.asString("NOMEMAE").trim());

		if (vo.asString("NOMEPAI") != null)
			this.nomePai = ConversorUtils.semAcento(vo.asString("NOMEPAI").trim());

		this.racaFuncionario = vo.asBigDecimal("RACAFUNCIONARIO");
		this.sexo = vo.asString("SEXO");
		this.nacionalidade = vo.asBigDecimal("NACIONALIDADE");
		this.cidadeNascimento = EnderecoFinder.findCidadeByPk(vo.asBigDecimal("CIDNASC"));
		this.cpf = vo.asString(SklFieldName.CPF);
		this.identidade = vo.asString("IDENTIDADE");
		this.pis = vo.asString("PIS");
		this.estadoCivil = vo.asBigDecimal("ESTADOCIVIL");
		this.celular = vo.asString("CELULAR");
		this.telefone = vo.asString("TELEFONE");
		this.email = vo.asString("EMAIL");
		this.nivEsc = vo.asBigDecimal("NIVESC");

		if (vo.asTimestamp("DTADM") != null)
			this.dtAdm = TimeUtils.clearTime(vo.asTimestamp("DTADM"));

		this.matricula = vo.asBigDecimal("MATRICULA");
		this.cargo = CargoFinder.findByPk(vo.asBigDecimal("CODCARGO"));
		this.departamento = DepartamentoFinder.findByPk(vo.asBigDecimal("CODDEP"));
		this.numCps = vo.asBigDecimal("NUMCPS");
		this.serieCps = vo.asString("SERIECPS");
		this.ufCps = vo.asBigDecimal("UFCPS");
		this.vinculo = vo.asBigDecimal("VINCULO");
		this.situacao = vo.asString("SITUACAO");
		this.regimeTrab = vo.asBigDecimal("REGIMETRAB");
		this.codCargaHor = vo.asBigDecimal("CODCARGAHOR");
		this.dispensaPonto = vo.asBoolean("DISPENSAPONTO");

		if (vo.asTimestamp("DTDEM") != null)
			this.dtDem = TimeUtils.clearTime(vo.asTimestamp("DTDEM"));

		if (vo.asBigDecimal("CODTPR") == null) {
			this.tipoRescisao = new TipoRescisao();
			this.tipoRescisao.setCodTpr(BigDecimal.ZERO);
			this.tipoRescisao.setDescrTipResc("TRANSFERENCIA");
		} else {
			this.tipoRescisao = FuncionarioDAO.findTipoRescisaoByPk(vo.asBigDecimal("CODTPR"));
		}
	}

	@Override
	public String toString() {
		return String.format(
				"Funcionario [empresa=%s, codFunc=%s, nomeFunc=%s, dtNasc=%s, nomeMae=%s, nomePai=%s, racaFuncionario=%s, sexo=%s, nacionalidade=%s, cidadeNascimento=%s, cpf=%s, identidade=%s, pis=%s, estadoCivil=%s, celular=%s, telefone=%s, email=%s, nivEsc=%s, dtAdm=%s, matricula=%s, cargo=%s, departamento=%s, numCps=%s, serieCps=%s, ufCps=%s, vinculo=%s, situacao=%s, regimeTrab=%s, codCargaHor=%s, dispensaPonto=%s, dtDem=%s, tipoRescisao=%s]",
				empresa, codFunc, nomeFunc, dtNasc, nomeMae, nomePai, racaFuncionario, sexo, nacionalidade,
				cidadeNascimento, cpf, identidade, pis, estadoCivil, celular, telefone, email, nivEsc, dtAdm, matricula,
				cargo, departamento, numCps, serieCps, ufCps, vinculo, situacao, regimeTrab, codCargaHor, dispensaPonto,
				dtDem, tipoRescisao);
	}

}
