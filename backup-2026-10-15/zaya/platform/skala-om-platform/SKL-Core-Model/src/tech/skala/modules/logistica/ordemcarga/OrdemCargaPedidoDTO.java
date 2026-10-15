package tech.skala.modules.logistica.ordemcarga;

import java.math.BigDecimal;
import java.util.Objects;

import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DTO;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class OrdemCargaPedidoDTO implements DTO, LoadableByValueObject {

	private BigDecimal							nuNota;
	private BigDecimal							ordemCarga;
	private BigDecimal							ordemCargaPai;

	public OrdemCargaPedidoDTO() {
		
	}

	public OrdemCargaPedidoDTO(BigDecimal nuNota, BigDecimal ordemCarga, BigDecimal ordemCargaPai) {
		this.nuNota = nuNota;
		this.ordemCarga = ordemCarga;
		this.ordemCargaPai = ordemCargaPai;
	}

	public BigDecimal getNuNota() {
		return nuNota;
	}

	public void setNuNota(BigDecimal nuNota) {
		this.nuNota = nuNota;
	}

	public BigDecimal getOrdemCarga() {
		return ordemCarga;
	}

	public void setOrdemCarga(BigDecimal ordemCarga) {
		this.ordemCarga = ordemCarga;
	}

	public BigDecimal getOrdemCargaPai() {
		return ordemCargaPai;
	}

	public void setOrdemCargaPai(BigDecimal ordemCargaPai) {
		this.ordemCargaPai = ordemCargaPai;
	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.nuNota = vo.asBigDecimal(SklFieldName.NUNOTA);
		this.ordemCarga = vo.asBigDecimal(SklFieldName.ORDEMCARGA);
		this.ordemCargaPai = vo.asBigDecimal(SklFieldName.AD_ORDEMCARGA);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ordemCarga, ordemCargaPai);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof OrdemCargaPedidoDTO))
			return false;
		OrdemCargaPedidoDTO other = (OrdemCargaPedidoDTO) obj;
		return Objects.equals(ordemCarga, other.ordemCarga) && Objects.equals(ordemCargaPai, other.ordemCargaPai);
	}

	@Override
	public String toString() {
		return String.format("OrdemCargaPedidoDTO [nuNota=%s, ordemCarga=%s, ordemCargaPai=%s]", nuNota, ordemCarga,
				ordemCargaPai);
	}

	
}
