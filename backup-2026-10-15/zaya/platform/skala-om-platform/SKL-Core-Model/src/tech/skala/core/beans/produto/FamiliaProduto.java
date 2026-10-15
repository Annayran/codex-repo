package tech.skala.core.beans.produto;

import java.math.BigDecimal;
import java.util.Objects;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
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
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);
		try {
			DynamicVO vo = (codFam == null) ? dao.findByPK(keyValue) : dao.findByPK(codFam);
			loadByVO(vo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RecordNotFoundException(e);
		}
		
	}
	
	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString() {
		return String.format("FamiliaProduto [codFam=%s, descrFam=%s, ordem=%s, tamLotePad=%s]", codFam, descrFam,
				ordem, tamLotePad);
	}

}
