package tech.skala.action.integration.transport.ocorrencianaentrega;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;

/**
 * A classe {@code ConhecimentoDeTransporte} representa a tabela {@code AD_PLTGFENTCTE}.
 * 
 * @author hiagoalexandre
 * @since 2023-03-10
 */
public class ConhecimentoDeTransporte implements EntityBean, Persistable, LoadableByValueObject {

	public static final String						ENTITY_NAME = "AD_PLTGFENTCTE";
	
	private	BigDecimal 								nuNotaCte;
	private	BigDecimal 								nuNota;

	public BigDecimal getNuNotaCte() {
		return nuNotaCte;
	}

	public void setNuNotaCte(BigDecimal nuNotaCte) {
		this.nuNotaCte = nuNotaCte;
	}

	public BigDecimal getNuNota() {
		return nuNota;
	}

	public void setNuNota(BigDecimal nuNota) {
		this.nuNota = nuNota;
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);

		try {
			DynamicVO vo = null;

			if (nuNota != null && nuNotaCte != null)
				vo = dao.findByPK(nuNota, nuNotaCte);

			if (vo == null) {
				EntityInsert entityNew = new EntityInsert(ENTITY_NAME);
				entityNew.setField(SklFieldName.NUNOTA, nuNota);
				entityNew.setField("NUNOTACTE", nuNotaCte);
				
				entityNew.fluidInsert();
			}

		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}

		return null;
	}
	
	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.nuNota = vo.asBigDecimal(SklFieldName.NUNOTA);
		this.nuNotaCte = vo.asBigDecimal("NUNOTACTE");
	}

	@Override
	public String toString() {
		return String.format("ConhecimentoDeTransporte [nuNotaCte=%s, nuNota=%s]", nuNotaCte, nuNota);
	}

}
