package tech.skala.core.beans.suprimento.recebimento;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;

public class RecebimentoConferencia implements EntityBean, Persistable {
	
	private BigDecimal				nuRecebimento;
	private BigDecimal				nuConferencia;
	
	public BigDecimal getNuRecebimento() {
		return nuRecebimento;
	}

	public void setNuRecebimento(BigDecimal nuRecebimento) {
		this.nuRecebimento = nuRecebimento;
	}

	public BigDecimal getNuConferencia() {
		return nuConferencia;
	}

	public void setNuConferencia(BigDecimal nuConferencia) {
		this.nuConferencia = nuConferencia;
	}
	
	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		try {
			EntityInsert entityNew = new EntityInsert("AD_PLTGFRECXCON");
			entityNew.setField("NURECEBIMENTO", nuRecebimento);
			entityNew.setField("NUCONFERENCIA", nuConferencia);
			entityNew.fluidInsert();
		} catch (InsertNotPossibleException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public String toString() {
		return String.format("RecebimentoConferencia [nuRecebimento=%s, nuConferencia=%s]", nuRecebimento,
				nuConferencia);
	}

	
	

}
