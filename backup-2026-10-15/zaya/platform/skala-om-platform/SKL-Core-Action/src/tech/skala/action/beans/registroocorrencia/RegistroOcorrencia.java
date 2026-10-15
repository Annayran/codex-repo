package tech.skala.action.beans.registroocorrencia;

import java.math.BigDecimal;
import java.sql.Timestamp;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.action.beans.funcionario.Funcionario;
import tech.skala.action.integration.tangerino.utils.ConversorUtils;
import tech.skala.action.modules.empresas.Empresa;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByPrimaryKey;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class RegistroOcorrencia implements EntityBean, LoadableByPrimaryKey, LoadableByValueObject {
	public static final String					ENTITY_NAME = DynamicEntityNames.REGISTRO_OCORRENCIA;

	private BigDecimal							nuOcor;
	private String								descrOcor;
	private Empresa								empresa;
	private Funcionario							funcionario;
	private BigDecimal							codHistOcor;
	private Timestamp							dtInicOcor;
	private Timestamp							dtFinalOcor;
	private Timestamp							dtAlter;
	
	public BigDecimal getNuOcor() {
		return nuOcor;
	}
	public void setNuOcor(BigDecimal nuOcor) {
		this.nuOcor = nuOcor;
	}
	public String getDescrOcor() {
		return descrOcor;
	}
	public void setDescrOcor(String descrOcor) {
		this.descrOcor = descrOcor;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	public BigDecimal getCodHistOcor() {
		return codHistOcor;
	}
	public void setCodHistOcor(BigDecimal codHistOcor) {
		this.codHistOcor = codHistOcor;
	}
	public Timestamp getDtInicOcor() {
		return dtInicOcor;
	}
	public void setDtInicOcor(Timestamp dtInicOcor) {
		this.dtInicOcor = dtInicOcor;
	}
	public Timestamp getDtFinalOcor() {
		return dtFinalOcor;
	}
	public void setDtFinalOcor(Timestamp dtFinalOcor) {
		this.dtFinalOcor = dtFinalOcor;
	}
	public Timestamp getDtAlter() {
		return dtAlter;
	}
	public void setDtAlter(Timestamp dtAlter) {
		this.dtAlter = dtAlter;
	}
	public BigDecimal getCodEmp() {
		if (empresa != null)
			return empresa.getCodEmp();
		
		return null;
	}
	public BigDecimal getCodFunc() {
		if (funcionario != null)
			return funcionario.getCodFunc();
		
		return null;
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
		this.nuOcor = vo.asBigDecimal("NUOCOR");
		if (vo.asString("DESCROCOR") != null)
			this.descrOcor = ConversorUtils.semAcento(vo.asString("DESCROCOR").trim()).toUpperCase();
		
		this.empresa = new Empresa();
		empresa.loadByPk(vo.asBigDecimal(SklFieldName.CODEMP));
		
		this.funcionario = new Funcionario();
		funcionario.loadByPk(vo.asBigDecimal(SklFieldName.CODEMP), vo.asBigDecimal(SklFieldName.CODFUNC));
		
		this.codHistOcor = vo.asBigDecimal("CODHISTOCOR");
		this.dtInicOcor = vo.asTimestamp("DTINICOCOR");
		this.dtFinalOcor = vo.asTimestamp("DTFINALOCOR");
	}
	
	@Override
	public String toString() {
		return String.format(
				"RegistroOcorrencia [nuOcor=%s, descrOcor=%s, empresa=%s, funcionario=%s, codHistOcor=%s, dtInicOcor=%s, dtFinalOcor=%s, dtAlter=%s]",
				nuOcor, descrOcor, empresa, funcionario, codHistOcor, dtInicOcor, dtFinalOcor, dtAlter);
	}

}
