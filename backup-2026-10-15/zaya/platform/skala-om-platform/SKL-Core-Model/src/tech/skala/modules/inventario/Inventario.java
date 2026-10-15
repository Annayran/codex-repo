package tech.skala.modules.inventario;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByPrimaryKey;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class Inventario implements EntityBean, LoadableByValueObject, LoadableByPrimaryKey {
	public static final String				ENTITY_NAME = SklEntityName.INVENTARIO;
	
	private BigDecimal								idIvt;
	private String									descricao;
	private Timestamp								dtIvt;
	private List<ContagemInventario>				contagens = new ArrayList<>();

	public BigDecimal getIdIvt() {
		return idIvt;
	}

	public void setIdIvt(BigDecimal idIvt) {
		this.idIvt = idIvt;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Timestamp getDtIvt() {
		return dtIvt;
	}

	public void setDtIvt(Timestamp dtIvt) {
		this.dtIvt = dtIvt;
	}

	public List<ContagemInventario> getContagens() {
		return contagens;
	}

	public void setContagens(List<ContagemInventario> contagens) {
		this.contagens = contagens;
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
		this.idIvt = vo.asBigDecimal(SklFieldName.IDIVT);
		this.descricao = vo.asString(SklFieldName.DESCRICAO);
		this.dtIvt = vo.asTimestamp(SklFieldName.DTIVT);					
	}

	@Override
	public int hashCode() {
		return Objects.hash(contagens, descricao, dtIvt, idIvt);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Inventario))
			return false;
		Inventario other = (Inventario) obj;
		return Objects.equals(contagens, other.contagens) && Objects.equals(descricao, other.descricao)
				&& Objects.equals(dtIvt, other.dtIvt) && Objects.equals(idIvt, other.idIvt);
	}

	@Override
	public String toString() {
		return String.format("Inventario [idIvt=%s, descricao=%s, dtIvt=%s, contagens=%s]", idIvt,
				descricao, dtIvt, contagens);
	}
	
}
