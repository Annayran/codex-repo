package tech.skala.action.integration.transport.azship;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;

public class AZShipRemessaPedidoLog implements EntityBean, Persistable {
	public static final String						ENTITY_NAME = "AD_PLTAZSREMLOG";
	
	private BigDecimal  							nuNota;
	private Timestamp  								dhEnvio;

	public AZShipRemessaPedidoLog() {

	}
	
	public AZShipRemessaPedidoLog(BigDecimal nuNota) {
		this.nuNota = nuNota;
	}

	public BigDecimal getNuNota() {
		return nuNota;
	}

	public void setNuNota(BigDecimal nuNota) {
		this.nuNota = nuNota;
	}

	public Timestamp getDhEnvio() {
		return dhEnvio;
	}

	public void setDhEnvio(Timestamp dhEnvio) {
		this.dhEnvio = dhEnvio;
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {

		try {
			EntityInsert entityNew = new EntityInsert(ENTITY_NAME);
			entityNew.setField(SklFieldName.NUNOTA, nuNota);
			entityNew.setField("DHENVIO", TimeUtils.getNow());

			entityNew.fluidInsert();

		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}
		
		return null;
	}

	@Override
	public String toString() {
		return String.format("AZShipRemessaPedidoLog [nuNota=%s, dhEnvio=%s]", nuNota, dhEnvio);
	}
	
}
