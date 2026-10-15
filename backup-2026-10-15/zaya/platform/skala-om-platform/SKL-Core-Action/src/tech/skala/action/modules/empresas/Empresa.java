package tech.skala.action.modules.empresas;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByPrimaryKey;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

/**
 * A classe {@code Empresa} representa a tabela {@code TSIEMP} e tabelas de configuracoes.
 *  
 * <p>
 * A classe {@code Empresa} devera ser extendida como classes que representem os diversos 
 * tipos de configuracoes de empresas do sistema.
 * </p> 
 * 
 * @author cassiomenezes
 * @since 2023-01-22
 */
public class Empresa implements EntityBean, LoadableByPrimaryKey, LoadableByValueObject {
	private static final String					ENTITY_NAME = DynamicEntityNames.EMPRESA;
	
	private BigDecimal							codEmp;
	private String								nomeFantasia;
	private String								razaoSocial;
	private String								cgc;
	private String								email;
	private String								telefone;
	
	
	public Empresa() {
		super();
	}
	
	public BigDecimal getCodEmp() {
		return codEmp;
	}
	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}
	public String getNomeFantasia() {
		return nomeFantasia;
	}
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	public String getCgc() {
		return cgc;
	}
	public void setCgc(String cgc) {
		this.cgc = cgc;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
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
		this.nomeFantasia = vo.asString("NOMEFANTASIA");
		this.razaoSocial = vo.asString(SklFieldName.RAZAOSOCIAL);
		this.cgc = vo.asString("CGC");
		this.email = vo.asString("EMAIL");
		this.telefone = vo.asString("TELEFONE");
	}
	
	@Override
	public String toString() {
		return String.format("Empresa [codEmp=%s, nomeFantasia=%s, razaoSocial=%s, cgc=%s, email=%s, telefone=%s]",
				codEmp, nomeFantasia, razaoSocial, cgc, email, telefone);
	}
	
}
