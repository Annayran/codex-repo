package tech.skala.modules.manufatura.ordemproducao;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.beans.produto.ProdutoVenda;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.EntityUpdate;

/**
 * A classe {@code VolumeProducao} representa um tipo de agrupamento de uma {@link OrdemProducao}.
 *  
 * <p>
 * A classe {@code VolumeProducao} eh um agrupamento de uma {@link OrdemProducao} em embalagens 
 * onde um {@link ProdutoVenda} eh armazenado ap�s a producao.<br>
 * Esta divisao eh utilizada principalmente no processo produtivo de embalagens.<br>
 * Para o atributo status utilizar o <i>Enum</i> {@link VolumeProducaoStatusEnum}.
 * </p> 
 * 
 * @author marcos.damasceno
 * @author cassiomenezes
 * @author hiagoalexandre
 * @author odilon
 * @since 2022-07-13
 */
public class VolumeProducao implements EntityBean, LoadableByValueObject, Persistable {
	
	public static final String				ENTITY_NAME = "AD_PLTPRIVOP";
	
	private BigDecimal						idiProc;
	private BigDecimal						nuvOP;
	private BigDecimal						capacidade;
	private String 							status;
	private BigDecimal						nuApo;

	public BigDecimal getIdiProc() {
		return idiProc;
	}

	public void setIdiProc(BigDecimal idiProc) {
		this.idiProc = idiProc;
	}

	public BigDecimal getNuvOP() {
		return nuvOP;
	}

	public void setNuvOP(BigDecimal nuvOP) {
		this.nuvOP = nuvOP;
	}

	public BigDecimal getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(BigDecimal capacidade) {
		this.capacidade = capacidade;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getNuApo() {
		return nuApo;
	}

	public void setNuApo(BigDecimal nuApo) {
		this.nuApo = nuApo;
	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.idiProc = vo.asBigDecimal(SklFieldName.IDIPROC);
		this.nuvOP = vo.asBigDecimal("NUVOP");
		this.capacidade = vo.asBigDecimal(SklFieldName.CAPACIDADE);
		this.status = vo.asString("STATUS");
		this.nuApo = vo.asBigDecimal("NUAPO");
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {

		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);

		try {
			
			DynamicVO vo = null;
			
			if (idiProc != null && nuvOP != null) 
				vo = dao.findByPK(idiProc, nuvOP);
			
			if(vo == null) {
				EntityInsert entityNew = new EntityInsert(ENTITY_NAME);
				entityNew.setField(SklFieldName.IDIPROC, idiProc);
				entityNew.setField("NUVOP", nuvOP);
				entityNew.setField(SklFieldName.CAPACIDADE, capacidade);
				entityNew.setField("STATUS", status);
				entityNew.setField("NUAPO", nuApo);
				
				entityNew.fluidInsert();				
			} else {
				EntityUpdate entityUpd = new EntityUpdate(ENTITY_NAME, vo);
				entityUpd.setField("STATUS", status);
				entityUpd.setField("NUAPO", nuApo);
				
				entityUpd.fluidUpdate();
			}

		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e); 	
		}
		return null;
	}
	
	@Override
	public String toString() {
		return String.format("VolumeProducao [idiProc=%s, nuvOP=%s, capacidade=%s, status=%s, nuApo=%s]", idiProc, nuvOP,
				capacidade, status, nuApo);
	}

	
	
}
