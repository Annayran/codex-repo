package tech.skala.action.modules.centrais;

import java.math.BigDecimal;
import java.sql.Timestamp;

import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;

public class CabecalhoNotaSkl implements LoadableByValueObject, Persistable {
	private BigDecimal nuNota;
	private boolean isProgramado = false;
	private boolean isOrigemReserva = false;
	private String temReajuste;
	private String programado;
	private String origemReserva;
	private Timestamp dtLibCom;

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

	public String getProgramado() {
		return programado;
	}

	public boolean isOrigemReserva() {
		return isOrigemReserva;
	}

	public void setOrigemReserva(boolean isOrigemReserva) {
		this.isOrigemReserva = isOrigemReserva;
	}

	public String getOrigemReserva() {
		return origemReserva;
	}

	public String getTemReajuste() {
		return temReajuste;
	}

	public void setTemReajuste(String temReajuste) {
		this.temReajuste = temReajuste;
	}

	public Timestamp getDtLibCom() {
		return dtLibCom;
	}

	public void setDtLibCom(Timestamp dtLibCom) {
		this.dtLibCom = dtLibCom;
	}

	@Override
	public String toString() {
		return String.format("CabecalhoNotaPlt [nuNota=%s, isProgramado=%s, isOrigemReserva=%s]", nuNota, isProgramado,
				isOrigemReserva);
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		// JapeWrapper cabecalhoNotaDAO = JapeFactory.dao(SklEntityName.CABECALHO_NOTA);

		EntityInsert newCabecalhoNotaVO = new EntityInsert(SklEntityName.CABECALHO_NOTA);
		newCabecalhoNotaVO.setField("NUNOTA", nuNota);
		newCabecalhoNotaVO.setField("PROGRAMADO", (isProgramado) ? "S" : "N");
		newCabecalhoNotaVO.setField("ORIGEMRESERVA", (isOrigemReserva) ? "S" : "N");
		newCabecalhoNotaVO.setField("DTLIBCOM", dtLibCom);
		newCabecalhoNotaVO.setField("TEMREAJUSTE", temReajuste);
		newCabecalhoNotaVO.fluidInsert();
		return null;
	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.nuNota = vo.asBigDecimal("NUNOTA");
		this.isProgramado = "S".equalsIgnoreCase(vo.asString("PROGRAMADO"));
		this.isOrigemReserva = "S".equalsIgnoreCase(vo.asString("ORIGEMRESERVA"));
		this.temReajuste = vo.asString("TEMREAJUSTE");
		this.dtLibCom = vo.asTimestamp("DTLIBCOM");

		this.programado = (isProgramado) ? "S" : "N";
		this.origemReserva = (isOrigemReserva) ? "S" : "N";

	}

}
