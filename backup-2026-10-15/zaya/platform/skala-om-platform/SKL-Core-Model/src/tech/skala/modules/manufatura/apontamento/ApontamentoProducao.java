package tech.skala.modules.manufatura.apontamento;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByPrimaryKey;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
	
public class ApontamentoProducao implements EntityBean, LoadableByValueObject , LoadableByPrimaryKey {
	public static final String				ENTITY_NAME = DynamicEntityNames.CABECALHO_APONTAMENTO;
	
	private BigDecimal						nuApo;
	private BigDecimal						idiAtv;
	private String 							observacao;
	private String 							situacao;

	public BigDecimal getNuApo() {
		return nuApo;
	}

	public void setNuApo(BigDecimal nuApo) {
		this.nuApo = nuApo;
	}

	public BigDecimal getIdiAtv() {
		return idiAtv;
	}

	public void setIdiAtv(BigDecimal idiAtv) {
		this.idiAtv = idiAtv;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.nuApo = vo.asBigDecimal("NUAPO");
		this.idiAtv = vo.asBigDecimal("IDIATV");
		this.observacao = vo.asString("OBSERVACAO");
		this.situacao = vo.asString("SITUACAO");
		
	}
	
	@Override
	public void loadByPk(Object... keyValue) throws RecordNotFoundException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);
		try {
			DynamicVO vo = (nuApo == null) ? dao.findByPK(keyValue) : dao.findByPK(nuApo);
			loadByVO(vo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RecordNotFoundException(e);
		}
	}
	
	@Override
	public String toString() {
		return String.format("ApontamentoProducao [nuApo=%s, idiAtv=%s, observacao=%s, situacao=%s]", nuApo, idiAtv,
				observacao, situacao);
	}


}

