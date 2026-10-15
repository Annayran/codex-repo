package tech.skala.action.modules.centrais;

import java.math.BigDecimal;

import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.beans.exceptions.EntityBeanExceptions.SetFromRecordNotPossibleException;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class TipoCarteira implements EntityBean, LoadableByValueObject {

	private BigDecimal codTipOper;
	private String tipo;
	private String ordem;
	private boolean isAntecipado;

	public BigDecimal getCodTipOper() {
		return codTipOper;
	}

	public void setCodTipOper(BigDecimal codTipOper) {
		this.codTipOper = codTipOper;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getOrdem() {
		return ordem;
	}

	public void setOrdem(String ordem) {
		this.ordem = ordem;
	}

	public boolean isAntecipado() {
		return isAntecipado;
	}

	public void setAntecipado(boolean isAntecipado) {
		this.isAntecipado = isAntecipado;
	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.codTipOper = vo.asBigDecimal("CODTIPOPER");
		this.tipo = vo.asString("TIPO");
		this.ordem = vo.asString("ORDEM");
		this.isAntecipado = "S".equalsIgnoreCase(vo.asString("ANTECIPADO"));
		
	}

}
