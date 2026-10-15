package tech.skala.action.integration.traycorp;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class EcommLigacaoClienteParceiro implements EntityBean, Persistable {
	private BigDecimal						idCliente;
	private BigDecimal						codParc;

	public BigDecimal getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(BigDecimal idCliente) {
		this.idCliente = idCliente;
	}

	public BigDecimal getCodParc() {
		return codParc;
	}

	public void setCodParc(BigDecimal codParc) {
		this.codParc = codParc;
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper ligacaoParceiroDAO = JapeFactory.dao(TrayCorpUtils.ECOMM_LIGACAO_PARCEIRO);
		try {
			if (ligacaoParceiroDAO.find("this.ID_CLIENTE = ? AND this.CODPARC = ?", idCliente, codParc).isEmpty()) {
				EntityInsert insert = new EntityInsert(TrayCorpUtils.ECOMM_LIGACAO_PARCEIRO);
				insert.setField("ID_CLIENTE", idCliente);
				insert.setField("CODPARC", codParc);
				insert.fluidInsert();
			}

		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}
		return null;
	}

	@Override
	public String toString() {
		return String.format("EcommLigacaoClienteParceiro [idCliente=%s, codParc=%s]", idCliente, codParc);
	}

}
