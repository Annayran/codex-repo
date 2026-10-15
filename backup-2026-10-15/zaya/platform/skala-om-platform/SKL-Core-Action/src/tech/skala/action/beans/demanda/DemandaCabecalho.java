package tech.skala.action.beans.demanda;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.EntityUpdate;

public class DemandaCabecalho implements EntityBean, LoadableByValueObject, Persistable{
	private BigDecimal 						nuDemanda;
	private BigDecimal 						codEmp;
	private Timestamp 						dtDemanda;
	private String 							descricao;
	private String 							origem;
	private Timestamp 						dhAlter;
	private BigDecimal 						codUsu;
	
	public DemandaCabecalho() {
		this.dtDemanda = TimeUtils.clearTime(TimeUtils.getNow());
		this.descricao = String.format("Demanda calculada em %s", TimeUtils.formataDDMMYY(dtDemanda));
		this.codEmp = new BigDecimal(2);
	}

	public BigDecimal getNuDemanda() {
		return nuDemanda;
	}

	public void setNuDemanda(BigDecimal nuDemanda) {
		this.nuDemanda = nuDemanda;
	}

	public BigDecimal getCodEmp() {
		return codEmp;
	}

	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}

	public Timestamp getDtDemanda() {
		return dtDemanda;
	}

	public void setDtDemanda(Timestamp dtDemanda) {
		this.dtDemanda = dtDemanda;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public Timestamp getDhAlter() {
		return dhAlter;
	}

	public void setDhAlter(Timestamp dhAlter) {
		this.dhAlter = dhAlter;
	}

	public BigDecimal getCodUsu() {
		return codUsu;
	}

	public void setCodUsu(BigDecimal codUsu) {
		this.codUsu = codUsu;
	}
	
	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.nuDemanda = vo.asBigDecimal("NUDEMANDA");
		this.descricao = vo.asString(SklFieldName.DESCRICAO);
		this.codEmp = vo.asBigDecimal(SklFieldName.CODEMP);
		this.dtDemanda = vo.asTimestamp("DTDEMANDA");		
	}
	
	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao("AD_PLTGFDMCAB");
		
		try {
			DynamicVO demandaVO = dao.findOne("this.DTDEMANDA = ?", TimeUtils.getNow("dd/MM/yyyy"));
			
			if (demandaVO == null) {
				EntityInsert insert = new EntityInsert("AD_PLTGFDMCAB");
				insert.setField("DESCRICAO", descricao);
				insert.setField("DTDEMANDA", dtDemanda);
				insert.setField("CODEMP", codEmp);
				insert.setField("ORIGEM", origem);
				DynamicVO vo  = insert.fluidInsert();
				
				this.nuDemanda = vo.asBigDecimal("NUDEMANDA");
				
			} else {
				EntityUpdate update = new EntityUpdate("AD_PLTGFDMCAB", demandaVO);
				update.setField(descricao, origem);
				update.fluidUpdate();

				this.nuDemanda = demandaVO.asBigDecimal("NUDEMANDA");

			}
		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}
		return null;
	}


	
	
}
