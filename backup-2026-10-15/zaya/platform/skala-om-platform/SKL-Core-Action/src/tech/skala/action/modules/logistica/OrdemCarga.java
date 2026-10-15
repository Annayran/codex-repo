package tech.skala.action.modules.logistica;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByPrimaryKey;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class OrdemCarga implements EntityBean, LoadableByPrimaryKey, LoadableByValueObject{

	public static final String						ENTITY_NAME = DynamicEntityNames.ORDEM_CARGA;
	
	private	BigDecimal								codEmp;
	private	BigDecimal 								ordemCarga;
	private	BigDecimal 								ordemCargaPai;
	private	BigDecimal 								codParcTransp;
	private String									tipCarga;
	
	public BigDecimal getCodEmp() {
		return codEmp;
	}

	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
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

	public BigDecimal getCodParcTransp() {
		return codParcTransp;
	}

	public void setCodParcTransp(BigDecimal codParcTransp) {
		this.codParcTransp = codParcTransp;
	}

	public String getTipCarga() {
		return tipCarga;
	}

	public void setTipCarga(String tipCarga) {
		this.tipCarga = tipCarga;
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
		this.codEmp = vo.asBigDecimal(SklFieldName.CODEMP);
		this.ordemCarga = vo.asBigDecimal(SklFieldName.ORDEMCARGA);
		this.tipCarga = vo.asString("TIPCARGA");

		BigDecimal ocPai = vo.asBigDecimal("AD_ORDEMCARGA");

		this.ordemCargaPai = ocPai == null ? ordemCarga : ocPai;
		this.codParcTransp = vo.asBigDecimal(SklFieldName.CODPARCTRANSP);
	}

	@Override
	public String toString() {
		return String.format("OrdemCarga [codEmp=%s, ordemCarga=%s, ordemCargaPai=%s, codParcTransp=%s, tipCarga=%s]",
				codEmp, ordemCarga, ordemCargaPai, codParcTransp, tipCarga);
	}

}
