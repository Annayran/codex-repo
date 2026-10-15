package tech.skala.modules.suprimentos.abastecimento;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByPrimaryKey;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityUpdate;

public class Requisicao implements EntityBean, LoadableByValueObject, LoadableByPrimaryKey, Persistable {
	private static final String 					ENTITY_NAME = SklEntityName.REQUISICOES;
	
	private BigDecimal								nuReq;
	private String									status;
	private BigDecimal 								codUsu;
	private String									nomeUsu;
	private BigDecimal 								idiProc;
	private Timestamp 								dhAlter;
	private BigDecimal 								nuNota;
	private String 									tipoTransf;
	private List<ProdutoRequisicao> 				produtos;
	
	public BigDecimal getNuReq() {
		return nuReq;
	}

	public void setNuReq(BigDecimal nuReq) {
		this.nuReq = nuReq;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getCodUsu() {
		return codUsu;
	}

	public void setCodUsu(BigDecimal codUsu) {
		this.codUsu = codUsu;
	}

	public String getNomeUsu() {
		return nomeUsu;
	}

	public void setNomeUsu(String nomeUsu) {
		this.nomeUsu = nomeUsu;
	}

	public BigDecimal getIdiProc() {
		return idiProc;
	}

	public void setIdiProc(BigDecimal idiProc) {
		this.idiProc = idiProc;
	}

	public Timestamp getDhAlter() {
		return dhAlter;
	}

	public void setDhAlter(Timestamp dhAlter) {
		this.dhAlter = dhAlter;
	}

	public BigDecimal getNuNota() {
		return nuNota;
	}

	public void setNuNota(BigDecimal nuNota) {
		this.nuNota = nuNota;
	}

	public String getTipoTransf() {
		return tipoTransf;
	}

	public void setTipoTransf(String tipoTransf) {
		this.tipoTransf = tipoTransf;
	}

	public List<ProdutoRequisicao> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<ProdutoRequisicao> produtos) {
		this.produtos = produtos;
	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.nuReq = vo.asBigDecimal(SklFieldName.NUREQ);
		this.status = vo.asString(SklFieldName.STATUS);
		this.codUsu = vo.asBigDecimal(SklFieldName.CODUSU);
		this.nomeUsu = vo.asString("Usuario.NOMEUSU");
		this.idiProc = vo.asBigDecimal(SklFieldName.IDIPROC);
		this.dhAlter = vo.asTimestamp(SklFieldName.DHALTER);
		this.nuNota = vo.asBigDecimal(SklFieldName.NUNOTA);
		this.tipoTransf = vo.asString(SklFieldName.TIPOTRANSF);
		
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
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);
		
		DynamicVO vo = null;
		try {
			vo = dao.findByPK(nuReq);
		} catch (Exception e) {
			throw new DataDictionaryExceptions.UpdateNotPossibleException(e);
		}
		
		if (vo != null) {
			EntityUpdate entityUpdate = new EntityUpdate(ENTITY_NAME, vo);
			entityUpdate.setField(SklFieldName.STATUS, status);
			
			entityUpdate.fluidUpdate();
		}
		
		return null;
	}
	
	@Override
	public String toString() {
		return String.format(
				"Requisicao [nuReq=%s, status=%s, codUsu=%s, nomeUsu=%s, idiProc=%s, dhAlter=%s, nuNota=%s, tipoTransf=%s, produtos=%s]",
				nuReq, status, codUsu, nomeUsu, idiProc, dhAlter, nuNota, tipoTransf, produtos);
	}

}
