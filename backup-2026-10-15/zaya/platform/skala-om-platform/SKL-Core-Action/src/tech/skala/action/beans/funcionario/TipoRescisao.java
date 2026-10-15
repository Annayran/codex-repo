package tech.skala.action.beans.funcionario;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.action.integration.tangerino.utils.ConversorUtils;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByPrimaryKey;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class TipoRescisao implements EntityBean, LoadableByPrimaryKey, LoadableByValueObject {
	public static final String					ENTITY_NAME = "TipoRescisao";
	
	private BigDecimal							codTpr;
	private String								descrTipResc;
	
	public TipoRescisao() {
		super();
	}

	public BigDecimal getCodTpr() {
		return codTpr;
	}
	public void setCodTpr(BigDecimal codTpr) {
		this.codTpr = codTpr;
	}
	public String getDescrTipResc() {
		return descrTipResc;
	}
	public void setDescrTipResc(String descrTipResc) {
		this.descrTipResc = descrTipResc;
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
		this.codTpr = vo.asBigDecimal("CODTPR");
		
		if (vo.asString("DESCRTIPRESC") != null)
			this.descrTipResc = ConversorUtils.semAcento(vo.asString("DESCRTIPRESC")).toUpperCase();		
	}
	
	@Override
	public String toString() {
		return String.format("TipoRescisao [codTpr=%s, descrTipResc=%s]", codTpr, descrTipResc);
	}
	
}
