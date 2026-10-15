package tech.skala.action.modules.manufatura.workcenter;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.EntityUpdate;

/**
 * A classe {@code WorkCenterInstanciaPorProcesso} representa a tabela {@code TPRWXIP}.
 *  
 * <p>
 * A classe {@code WorkCenterInstanciaPorProcesso} representa a entidade <i>WorkCenterPorInstanciaProcesso</i> {@code TPRWXIP} 
 * que é a relação da Ordem de Produção com os Centros de Trabalho.
 * </p> 
 * 
 * @author hiagoalexandre
 * @since 2022-08-04
 *
 */
public class WorkCenterInstanciaPorProcesso implements EntityBean, LoadableByValueObject, Persistable {
public static final String ENTITY_NAME = DynamicEntityNames.WORK_CENTER_INSTANCIA_PROCESSO;
	
	private BigDecimal								idiProc;
	private BigDecimal								idAwc;
	private BigDecimal								codWcp;
	private BigDecimal								idEfx;
	private BigDecimal								prioridade;

	public BigDecimal getIdiProc() {
		return idiProc;
	}

	public void setIdiProc(BigDecimal idiProc) {
		this.idiProc = idiProc;
	}

	public BigDecimal getIdAwc() {
		return idAwc;
	}

	public void setIdAwc(BigDecimal idAwc) {
		this.idAwc = idAwc;
	}

	public BigDecimal getCodWcp() {
		return codWcp;
	}

	public void setCodWcp(BigDecimal codWcp) {
		this.codWcp = codWcp;
	}

	public BigDecimal getIdEfx() {
		return idEfx;
	}

	public void setIdEfx(BigDecimal idEfx) {
		this.idEfx = idEfx;
	}

	public BigDecimal getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(BigDecimal prioridade) {
		this.prioridade = prioridade;
	}
	
	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.idiProc = vo.asBigDecimal(SklFieldName.IDIPROC);
		this.idAwc = vo.asBigDecimal("IDAWC");
		this.codWcp = vo.asBigDecimal(SklFieldName.CODWCP);
		this.idEfx = vo.asBigDecimal("IDEFX");
		this.prioridade = vo.asBigDecimal(SklFieldName.PRIORIDADE);
	}
	
	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);

		DynamicVO vo;
		try {
			vo = dao.findByPK(idiProc, idAwc, codWcp, idEfx);
		} catch (Exception e) {
			throw new UpdateNotPossibleException(e);
		}

		EntityUpdate entityUpd = new EntityUpdate(ENTITY_NAME, vo);
		entityUpd.setField(SklFieldName.IDIPROC, idiProc);
		entityUpd.setField("IDAWC", idAwc);
		entityUpd.setField(SklFieldName.CODWCP, codWcp);
		entityUpd.setField("IDEFX", idEfx);
		entityUpd.setField(SklFieldName.PRIORIDADE, prioridade);
		entityUpd.fluidUpdate();

		return null;
	}
	
	@Override
	public String toString() {
		return String.format(
				"WorkCenterInstanciaPorProcesso [idiProc=%s, idAwc=%s, codWcp=%s, idEfx=%s, prioridade=%s]", idiProc,
				idAwc, codWcp, idEfx, prioridade);
	}

}
