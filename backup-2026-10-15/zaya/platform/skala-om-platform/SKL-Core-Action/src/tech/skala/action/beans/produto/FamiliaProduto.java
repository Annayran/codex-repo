package tech.skala.action.beans.produto;

import java.math.BigDecimal;
import java.util.Objects;

import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByPrimaryKey;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class FamiliaProduto implements EntityBean, LoadableByPrimaryKey, LoadableByValueObject {
	public static final String				ENTITY_NAME = SklEntityName.FAMILIA_PRODUTO;

	private BigDecimal                      codFam;
	private String                          descrFam;
	private BigDecimal                      ordem; 
	private BigDecimal						tamLotePad;
	private BigDecimal						qtdCapacidadeMax;
	
	public BigDecimal getCodFam() {
		return codFam;
	}

	public void setCodFam(BigDecimal codFam) {
		this.codFam = codFam;
	}

	public String getDescrFam() {
		return descrFam;
	}

	public void setDescrFam(String descrFam) {
		this.descrFam = descrFam;
	}

	public BigDecimal getOrdem() {
		return ordem;
	}

	public void setOrdem(BigDecimal ordem) {
		this.ordem = ordem;
	}

	public BigDecimal getTamLotePad() {
		return tamLotePad;
	}

	public void setTamLotePad(BigDecimal tamLotePad) {
		this.tamLotePad = tamLotePad;
	}
	
	public BigDecimal getQtdCapacidadeMax() {
		return qtdCapacidadeMax;
	}

	public void setQtdCapacidadeMax(BigDecimal qtdCapacidadeMax) {
		this.qtdCapacidadeMax = qtdCapacidadeMax;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codFam, descrFam);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof FamiliaProduto))
			return false;
		FamiliaProduto other = (FamiliaProduto) obj;
		return Objects.equals(codFam, other.codFam) && Objects.equals(descrFam, other.descrFam);
	}

	@Override
	public void loadByPk(Object... keyValue) throws RecordNotFoundException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.codFam = vo.asBigDecimal("CODFAM");
		this.descrFam = vo.asString("DESCRFAM");
		this.ordem = vo.asBigDecimal("ORDEM");
		this.tamLotePad = vo.asBigDecimalOrZero("TAMLOTEPAD");
		this.qtdCapacidadeMax = vo.asBigDecimalOrZero("QTDCAPACIDADEMAX");		
	}
	
	@Override
	public String toString() {
		return String.format("FamiliaProduto [codFam=%s, descrFam=%s, ordem=%s, tamLotePad=%s]", codFam, descrFam,
				ordem, tamLotePad);
	}

}
