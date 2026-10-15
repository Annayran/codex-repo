package tech.skala.action.modules.centrais;

import java.math.BigDecimal;
import java.sql.Timestamp;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByPrimaryKey;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.EntityUpdate;

public class CabecalhoNotaPlt implements EntityBean, LoadableByValueObject, LoadableByPrimaryKey, Persistable {

	private static final String 			ENTITY_NAME = "AD_PLTGFCAB";
	
	private BigDecimal						nuNota;
	private boolean							isProgramado = false;
	private boolean							isOrigemReserva = false;
	private String							temReajuste;
	private String							programado;
	private String							origemReserva;
	private Timestamp						dtLibCom;
	private Timestamp						dtReajuste;
	private BigDecimal						idInstPrn;

	public BigDecimal getNuNota() {
		return nuNota;
	}

	public void setNuNota(BigDecimal nuNota) {
		this.nuNota = nuNota;
	}

	public boolean isProgramado() {
		return isProgramado;
	}

	public void setProgramado(boolean isProgramado) {
		this.isProgramado = isProgramado;
	}

	public boolean isOrigemReserva() {
		return isOrigemReserva;
	}

	public void setOrigemReserva(boolean isOrigemReserva) {
		this.isOrigemReserva = isOrigemReserva;
	}

	public String getTemReajuste() {
		return temReajuste;
	}

	public void setTemReajuste(String temReajuste) {
		this.temReajuste = temReajuste;
	}

	public String getProgramado() {
		return programado;
	}

	public void setProgramado(String programado) {
		this.programado = programado;
	}

	public String getOrigemReserva() {
		return origemReserva;
	}

	public void setOrigemReserva(String origemReserva) {
		this.origemReserva = origemReserva;
	}

	public Timestamp getDtLibCom() {
		return dtLibCom;
	}

	public void setDtLibCom(Timestamp dtLibCom) {
		this.dtLibCom = dtLibCom;
	}

	public Timestamp getDtReajuste() {
		return dtReajuste;
	}

	public void setDtReajuste(Timestamp dtReajuste) {
		this.dtReajuste = dtReajuste;
	}

	public BigDecimal getIdInstPrn() {
		return idInstPrn;
	}

	public void setIdInstPrn(BigDecimal idInstPrn) {
		this.idInstPrn = idInstPrn;
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
		this.nuNota = vo.asBigDecimal(SklFieldName.NUNOTA);
		this.isProgramado = "S".equalsIgnoreCase(vo.asString(SklFieldName.PROGRAMADO));
		this.isOrigemReserva = "S".equalsIgnoreCase(vo.asString("ORIGEMRESERVA"));
		this.temReajuste = vo.asString(SklFieldName.TEMREAJUSTE);
		this.dtLibCom = vo.asTimestamp("DTLIBCOM");
		
		this.programado = (isProgramado) ? "S" : "N";
		this.origemReserva = (isOrigemReserva) ? "S" : "N";
		this.dtReajuste = vo.asTimestamp("DTREAJUSTE");
		
		this.idInstPrn = vo.asBigDecimal("IDINSTPRN");
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);

		DynamicVO vo = null;
		try {
			vo = dao.findByPK(nuNota);
		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}

		if (vo == null) {
			EntityInsert entityInsert = new EntityInsert(ENTITY_NAME);
			entityInsert.setField(SklFieldName.NUNOTA, nuNota);
			entityInsert.setField(SklFieldName.PROGRAMADO, programado);
			entityInsert.setField("ORIGEMRESERVA", origemReserva);
			entityInsert.setField(SklFieldName.TEMREAJUSTE, temReajuste);
			entityInsert.setField("DTLIBCOM", dtLibCom);
			entityInsert.setField("DTREAJUSTE", dtReajuste);
			entityInsert.setField("IDINSTPRN", idInstPrn);

			entityInsert.fluidInsert();
		} else {
			EntityUpdate entityUpdate = new EntityUpdate(ENTITY_NAME, vo);
			entityUpdate.setField(SklFieldName.PROGRAMADO, programado);
			entityUpdate.setField("ORIGEMRESERVA", origemReserva);
			entityUpdate.setField(SklFieldName.TEMREAJUSTE, temReajuste);
			entityUpdate.setField("DTLIBCOM", dtLibCom);
			entityUpdate.setField("DTREAJUSTE", dtReajuste);
			entityUpdate.setField("IDINSTPRN", idInstPrn);

			entityUpdate.fluidUpdate();
		}

		return null;
	}

	@Override
	public String toString() {
		return String.format(
				"CabecalhoNotaPlt [nuNota=%s, isProgramado=%s, isOrigemReserva=%s, temReajuste=%s, programado=%s, origemReserva=%s, dtLibCom=%s, dtReajuste=%s, idInstPrn=%s]",
				nuNota, isProgramado, isOrigemReserva, temReajuste, programado, origemReserva, dtLibCom, dtReajuste,
				idInstPrn);
	}
	
}
