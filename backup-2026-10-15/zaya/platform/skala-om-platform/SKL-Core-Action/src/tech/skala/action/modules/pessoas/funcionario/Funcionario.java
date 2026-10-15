package tech.skala.action.modules.pessoas.funcionario;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.action.integration.tangerino.utils.ConversorUtils;
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
 * @since 2023-01-22
 * 
 * @deprecated 2023-07-19
 */
public class Funcionario implements EntityBean, LoadableByValueObject, LoadableByPrimaryKey {
	private static final String					ENTITY_NAME = DynamicEntityNames.FUNCIONARIO;
	
	private BigDecimal							codEmp;
	private BigDecimal							codFunc;
	private String								celular;
	private BigDecimal							codCargaHor;
	private BigDecimal							codCargo;
	private BigDecimal							codFuncao;
	private Timestamp							dtAdm;
	private Timestamp							dtNasc;
	private String								identidade;
	private String								cpf;
	private String								email;
	private BigDecimal							nacionalidade;
	private String								nomeFunc;
	private BigDecimal							numCps;
	private String								pis;
	private String								serieCps;
	private String								telefone;
	private BigDecimal							ufCps;

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

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public BigDecimal getCodCargaHor() {
		return codCargaHor;
	}

	public void setCodCargaHor(BigDecimal codCargaHor) {
		this.codCargaHor = codCargaHor;
	}

	public BigDecimal getCodCargo() {
		return codCargo;
	}

	public void setCodCargo(BigDecimal codCargo) {
		this.codCargo = codCargo;
	}

	public BigDecimal getCodFuncao() {
		return codFuncao;
	}

	public void setCodFuncao(BigDecimal codFuncao) {
		this.codFuncao = codFuncao;
	}

	public Timestamp getDtAdm() {
		return dtAdm;
	}

	public void setDtAdm(Timestamp dtAdm) {
		this.dtAdm = dtAdm;
	}

	public Timestamp getDtNasc() {
		return dtNasc;
	}

	public void setDtNasc(Timestamp dtNasc) {
		this.dtNasc = dtNasc;
	}

	public String getIdentidade() {
		return identidade;
	}

	public void setIdentidade(String identidade) {
		this.identidade = identidade;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(BigDecimal nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public String getNomeFunc() {
		return nomeFunc;
	}

	public void setNomeFunc(String nomeFunc) {
		this.nomeFunc = nomeFunc;
	}

	public BigDecimal getNumCps() {
		return numCps;
	}

	public void setNumCps(BigDecimal numCps) {
		this.numCps = numCps;
	}

	public String getPis() {
		return pis;
	}

	public void setPis(String pis) {
		this.pis = pis;
	}

	public String getSerieCps() {
		return serieCps;
	}

	public void setSerieCps(String serieCps) {
		this.serieCps = serieCps;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public BigDecimal getUfCps() {
		return ufCps;
	}

	public void setUfCps(BigDecimal ufCps) {
		this.ufCps = ufCps;
	}

	public static String getEntityName() {
		return ENTITY_NAME;
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
		this.codEmp = vo.asBigDecimal(SklFieldName.CODEMP);
		this.codFunc = vo.asBigDecimal("CODFUNC");
		this.celular = vo.asString("CELULAR");
		this.codCargaHor = vo.asBigDecimal("CODCARGAHOR");
		this.codCargo = vo.asBigDecimal("CODCARGO");
		this.codFuncao = vo.asBigDecimal("CODFUNCAO");
		this.dtAdm = TimeUtils.clearTime(vo.asTimestamp("DTADM"));
		this.dtNasc = TimeUtils.clearTime(vo.asTimestamp("DTNASC"));
		this.identidade = vo.asString("IDENTIDADE");
		this.cpf = vo.asString("CPF");
		this.email = vo.asString("EMAIL");
		this.nacionalidade = vo.asBigDecimal("NACIONALIDADE");
		this.nomeFunc = ConversorUtils.semAcento(vo.asString("NOMEFUNC"));
		this.numCps = vo.asBigDecimal("NUMCPS");
		this.pis = vo.asString("PIS");
		this.serieCps = vo.asString("SERIECPS");
		this.telefone = vo.asString("TELEFONE");
		this.ufCps = vo.asBigDecimal("UFCPS");
	}

	@Override
	public String toString() {
		return String.format(
				"Funcionario [codEmp=%s, codFunc=%s, celular=%s, codCargaHor=%s, codCargo=%s, codFuncao=%s, dtAdm=%s, dtNasc=%s, identidade=%s, cpf=%s, email=%s, nacionalidade=%s, nomeFunc=%s, numCps=%s, pis=%s, serieCps=%s, telefone=%s, ufCps=%s]",
				codEmp, codFunc, celular, codCargaHor, codCargo, codFuncao, dtAdm, dtNasc, identidade, cpf, email,
				nacionalidade, nomeFunc, numCps, pis, serieCps, telefone, ufCps);
	}

	
}
