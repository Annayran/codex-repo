package tech.skala.core.beans.parceiro;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.beans.exceptions.EntityBeanExceptions;
import tech.skala.core.beans.interfaces.LoadableByPrimaryKey;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

/**
 * 
 * @author cassiomenezes
 * @since 2022-07-24
 *
 */
public class Cliente extends Parceiro implements LoadableByPrimaryKey, LoadableByValueObject {
	private boolean							isCliente;
	private BigDecimal						codParcMatriz;
	
	public Cliente() {
		this.isCliente = true;
	}
	
	public Cliente(BigDecimal codParc) {
		super(codParc);
		this.isCliente = true;
	}
	
	public boolean isCliente() {
		return isCliente;
	}

	public void setCliente(boolean isCliente) {
		this.isCliente = isCliente;
	}

	public BigDecimal getCodParcMatriz() {
		return codParcMatriz;
	}

	public void setCodParcMatriz(BigDecimal codParcMatriz) {
		this.codParcMatriz = codParcMatriz;
	}

	@Override
	public void loadByPk(Object... keyValue) throws RecordNotFoundException {
		try {
			JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.PARCEIRO);
			DynamicVO vo = dao.findByPK(keyValue);
			loadByVO(vo);
		} catch (Exception e) {
			throw new RecordNotFoundException(e);
		}
		
	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.isCliente = vo.asBoolean(SklFieldName.CLIENTE);
		if (!isCliente)
			throw new RecordNotFoundException(new EntityBeanExceptions.PersonNotClientException());

		this.codParc = vo.asBigDecimal(SklFieldName.CODPARC);
		this.nomeParc = vo.asString(SklFieldName.NOMEPARC);
		this.razaoSocial = vo.asString(SklFieldName.RAZAOSOCIAL);
		this.cgcCnpj = vo.asString(SklFieldName.CGC_CPF);
		
		this.codParcMatriz = vo.asBigDecimalOrZero(SklFieldName.CODPARCMATRIZ);
	}

	@Override
	public String toString() {
		return String.format(
				"Cliente [codParc=%s, nomeParc=%s, razaoSocial=%s, cgcCnpj=%s, isCliente=%s, codParcMatriz=%s]",
				codParc, nomeParc, razaoSocial, cgcCnpj, isCliente, codParcMatriz);
	}

}
