package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByPrimaryKey;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class ConfiguracaoHistoricoOcorrenciaIcarus implements EntityBean, LoadableByPrimaryKey, LoadableByValueObject {
	public static final String					ENTITY_NAME = "AD_PLTPICFGHIS";
	
	private BigDecimal							codHistOcor;
	private String								tipo;
	private String								unidade;
	private String								tipoAbono;
	
	public BigDecimal getCodHistOcor() {
		return codHistOcor;
	}
	public void setCodHistOcor(BigDecimal codHistOcor) {
		this.codHistOcor = codHistOcor;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getUnidade() {
		return unidade;
	}
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	public String getTipoAbono() {
		return tipoAbono;
	}
	public void setTipoAbono(String tipoAbono) {
		this.tipoAbono = tipoAbono;
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
		this.tipo = vo.asString("TIPO");
		this.unidade = vo.asString("UNIDADE");
		this.tipoAbono = vo.asString("TIPOABONO");
	}
	
	@Override
	public String toString() {
		return String.format(
				"ConfiguracaoHistoricoOcorrenciaIcarus [codHistOcor=%s, tipo=%s, unidade=%s, tipoAbono=%s]",
				codHistOcor, tipo, unidade, tipoAbono);
	}

}
