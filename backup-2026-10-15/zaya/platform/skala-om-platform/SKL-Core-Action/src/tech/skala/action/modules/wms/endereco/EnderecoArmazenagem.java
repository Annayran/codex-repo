package tech.skala.action.modules.wms.endereco;

import java.math.BigDecimal;

import br.com.sankhya.jape.dao.EntityPrimaryKey;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;

public class EnderecoArmazenagem implements EntityBean, LoadableByValueObject, Persistable {
	
	public static final String ENTITY_NAME = DynamicEntityNames.ENDERECO_ARMAZENAGEM;
	
	private BigDecimal codEnd;
	private String ativo;

	public BigDecimal getCodEnd() {
		return codEnd;
	}

	public void setCodEnd(BigDecimal codEnd) {
		this.codEnd = codEnd;
	}

	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {	
		
		if (this.codEnd == null)
			return null;
		
		EntityPrimaryKey pk = new EntityPrimaryKey(new Object[] { this.codEnd });
		
		EntityUpdate entityUpdate = new EntityUpdate(ENTITY_NAME, pk);
		entityUpdate.setField(SklFieldName.ATIVO, this.ativo);
		entityUpdate.fluidUpdate();
		
		return null;
	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.codEnd = vo.asBigDecimal("CODEND");
		this.ativo = vo.asString(SklFieldName.ATIVO);
	}

	@Override
	public String toString() {
		return "EnderecoArmazenagem [codEnd=" + codEnd + ", ativo=" + ativo + "]";
	}

}
