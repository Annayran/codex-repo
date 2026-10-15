package tech.skala.action.modules.centrais;

import java.math.BigDecimal;
import java.sql.Timestamp;

import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.beans.exceptions.EntityBeanExceptions.SetFromRecordNotPossibleException;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class CabecalhoNotaDadosPedidoOrigem implements EntityBean, LoadableByValueObject {
	private BigDecimal nuNota;
	private Timestamp dataComercial;
	private BigDecimal codParc;

	public BigDecimal getNuNota() {
		return nuNota;
	}

	public void setNuNota(BigDecimal nuNota) {
		this.nuNota = nuNota;
	}

	public Timestamp getDataComercial() {
		return dataComercial;
	}

	public void setDataComercial(Timestamp dataCOmercial) {
		this.dataComercial = dataCOmercial;
	}

	public BigDecimal getCodParc() {
		return codParc;
	}

	public void setCodParc(BigDecimal codParc) {
		this.codParc = codParc;
	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.nuNota = vo.asBigDecimal(SklFieldName.NUNOTA);
		this.dataComercial = vo.asTimestamp("DTNEG");
		this.codParc = vo.asBigDecimal(SklFieldName.CODPARC);
	}

	@Override
	public String toString() {
		return String.format("CabecalhoNotaDadosPedidoOrigem [nuNota=%s, dataComercial=%s, codParc=%s]", nuNota,
				dataComercial, codParc);
	}
}