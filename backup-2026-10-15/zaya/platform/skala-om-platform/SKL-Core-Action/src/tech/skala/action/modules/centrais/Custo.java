package tech.skala.action.modules.centrais;

import java.math.BigDecimal;

import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class Custo implements LoadableByValueObject {
	
	private BigDecimal						codEmp;
	private BigDecimal						codLocal;
	private String							controle;
	private BigDecimal						cusMedIcm;
	private BigDecimal						cusSemIcm;
	private BigDecimal						cusRep;

	
	public BigDecimal getCodEmp() {
		return codEmp;
	}

	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}

	public BigDecimal getCodLocal() {
		return codLocal;
	}

	public void setCodLocal(BigDecimal codLocal) {
		this.codLocal = codLocal;
	}

	public String getControle() {
		return controle;
	}

	public void setControle(String controle) {
		this.controle = controle;
	}

	public BigDecimal getCusMedIcm() {
		return cusMedIcm;
	}

	public void setCusMedIcm(BigDecimal cusMedIcm) {
		this.cusMedIcm = cusMedIcm;
	}

	public BigDecimal getCusSemIcm() {
		return cusSemIcm;
	}

	public void setCusSemIcm(BigDecimal cusSemIcm) {
		this.cusSemIcm = cusSemIcm;
	}

	public BigDecimal getCusRep() {
		return cusRep;
	}

	public void setCusRep(BigDecimal cusRep) {
		this.cusRep = cusRep;
	}

	@Override
	public String toString() {
		return String.format("Custo [codEmp=%s, codLocal=%s, controle=%s, cusMedIcm=%s, cusSemIcm=%s, cusRep=%s]",
				codEmp, codLocal, controle, cusMedIcm, cusSemIcm, cusRep);
	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.codEmp = vo.asBigDecimal("CODEMP");
	    this.codLocal = vo.asBigDecimal("CODLOCAL");
	    this.controle = vo.asString("CONTROLE");
	    this.cusMedIcm = vo.asBigDecimal("CUSMEDICM");
	    this.cusSemIcm = vo.asBigDecimal("CUSSEMICM");
	    this.cusRep = vo.asBigDecimal("CUSREP");
		
	}
}
