package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;
import java.sql.Timestamp;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByPrimaryKey;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.EntityUpdate;

public class RegistroOcorrenciaIcarus implements EntityBean, LoadableByPrimaryKey, LoadableByValueObject, Persistable {
	public static final String					ENTITY_NAME = IcarusConstants.PLTPIOCO;

	private BigDecimal							nuOcor;
	private String								tipoAfastamento;
	private BigDecimal							idAfastamentoPonto;
	private Timestamp							dhAtuRetorno;
	
	public BigDecimal getNuOcor() {
		return nuOcor;
	}
	public void setNuOcor(BigDecimal nuOcor) {
		this.nuOcor = nuOcor;
	}
	public String getTipoAfastamento() {
		return tipoAfastamento;
	}
	public void setTipoAfastamento(String tipoAfastamento) {
		this.tipoAfastamento = tipoAfastamento;
	}
	public BigDecimal getIdAfastamentoPonto() {
		return idAfastamentoPonto;
	}
	public void setIdAfastamentoPonto(BigDecimal idAfastamentoPonto) {
		this.idAfastamentoPonto = idAfastamentoPonto;
	}
	public Timestamp getDhAtuRetorno() {
		return dhAtuRetorno;
	}
	public void setDhAtuRetorno(Timestamp dhAtuRetorno) {
		this.dhAtuRetorno = dhAtuRetorno;
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
		this.tipoAfastamento = vo.asString("TIPOAFASTAMENTO");
		this.idAfastamentoPonto = vo.asBigDecimal("IDAFASTAMENTOPONTO");
		this.dhAtuRetorno = vo.asTimestamp("DHINTEG");		
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);
		
		DynamicVO vo = null;
		try {
			vo = dao.findByPK(nuOcor);
			
			if (vo == null) {
				EntityInsert entityInsert = new EntityInsert(ENTITY_NAME);
				entityInsert.setField("NUOCOR", nuOcor);
				entityInsert.setField("TIPOAFASTAMENTO", tipoAfastamento);
			
				vo = entityInsert.fluidInsert();
			} else {
				EntityUpdate entityUpdate = new EntityUpdate(ENTITY_NAME, vo);
				
				if (idAfastamentoPonto != null)
					entityUpdate.setField("IDAFASTAMENTOPONTO", idAfastamentoPonto);
				
				if (dhAtuRetorno != null)
					entityUpdate.setField("DHATURETORNO", dhAtuRetorno);
				
				entityUpdate.fluidUpdate();
			}
		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}
		
		return vo;
	}
	
	@Override
	public String toString() {
		return String.format(
				"RegistroOcorrenciaIcarus [nuOcor=%s, tipoAfastamento=%s, idAfastamentoPonto=%s, dhAtuRetorno=%s]",
				nuOcor, tipoAfastamento, idAfastamentoPonto, dhAtuRetorno);
	}

}
