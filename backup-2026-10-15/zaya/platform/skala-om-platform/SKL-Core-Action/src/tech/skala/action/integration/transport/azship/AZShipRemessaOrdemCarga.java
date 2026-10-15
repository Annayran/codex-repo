package tech.skala.action.integration.transport.azship;

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

public class AZShipRemessaOrdemCarga implements EntityBean, LoadableByPrimaryKey ,LoadableByValueObject {
	public static final String				ENTITY_NAME = "AD_PLVAZSOCP";
	
	private BigDecimal						codEmp;
	private BigDecimal						ordemCarga;
	private String							ordemFilha;

	public BigDecimal getCodEmp() {
		return codEmp;
	}

	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}

	public BigDecimal getOrdemCarga() {
		return ordemCarga;
	}

	public void setOrdemCarga(BigDecimal ordemCarga) {
		this.ordemCarga = ordemCarga;
	}

	public String getOrdemFilha() {
		return ordemFilha;
	}

	public void setOrdemFilha(String ordemFilha) {
		this.ordemFilha = ordemFilha;
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
		this.ordemCarga = vo.asBigDecimal(SklFieldName.CODEMP);
		this.ordemFilha = vo.asString("ORDEMFILHA");
	}

	@Override
	public String toString() {
		return String.format("AZShipRemessaOrdemCarga [codEmp=%s, ordemCarga=%s, ordemFilha=%s]", codEmp, ordemCarga,
				ordemFilha);
	}
}
