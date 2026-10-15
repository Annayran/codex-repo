package tech.skala.action.beans.funcionario;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.action.integration.tangerino.utils.ConversorUtils;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByPrimaryKey;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class HistoricoOcorrencia implements EntityBean, LoadableByPrimaryKey, LoadableByValueObject {
	public static final String					ENTITY_NAME = DynamicEntityNames.HISTORICO_OCORRENCIA;
	
	private BigDecimal							codHistOcor;
	private String								descrHistOcor;
	
	public BigDecimal getCodHistOcor() {
		return codHistOcor;
	}
	public void setCodHistOcor(BigDecimal codHistOcor) {
		this.codHistOcor = codHistOcor;
	}
	public String getDescrHistOcor() {
		return descrHistOcor;
	}
	public void setDescrHistOcor(String descrHistOcor) {
		this.descrHistOcor = descrHistOcor;
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
		this.codHistOcor = vo.asBigDecimal("CODHISTOCOR");
		this.descrHistOcor = ConversorUtils.semAcento(vo.asString("DESCRHISTOCOR").trim()).toUpperCase();
	}
	
	@Override
	public String toString() {
		return String.format("HistoricoOcorrencia [codHistOcor=%s, descrHistOcor=%s]", codHistOcor, descrHistOcor);
	}

}
