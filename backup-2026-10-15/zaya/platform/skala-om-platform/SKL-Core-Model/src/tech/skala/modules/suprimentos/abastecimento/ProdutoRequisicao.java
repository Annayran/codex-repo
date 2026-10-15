package tech.skala.modules.suprimentos.abastecimento;

import java.math.BigDecimal;
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
import tech.skala.core.commons.exceptions.SkalaModelException;

public class ProdutoRequisicao implements EntityBean, LoadableByValueObject, LoadableByPrimaryKey, Persistable {
	private static final String 					ENTITY_NAME = SklEntityName.PRODUTO_REQUISICOES;
	
	private transient BigDecimal					nuReq;
	private BigDecimal 								sequencia;
	private BigDecimal								codProd; 
	private String 									descrProd; 
	private String 									codVol; 
	private BigDecimal								qtdSolicitado;
	private String 									status;
	private BigDecimal								codUsuAtd;
	private List<ProdutoAtendimentoRequisicao>      produtoAtendimentos;
	
	public BigDecimal getNuReq() {
		return nuReq;
	}

	public void setNuReq(BigDecimal nuReq) {
		this.nuReq = nuReq;
	}

	public BigDecimal getSequencia() {
		return sequencia;
	}

	public void setSequencia(BigDecimal sequencia) {
		this.sequencia = sequencia;
	}

	public BigDecimal getCodProd() {
		return codProd;
	}

	public void setCodProd(BigDecimal codProd) {
		this.codProd = codProd;
	}

	public String getDescrProd() {
		return descrProd;
	}

	public void setDescrProd(String descrProd) {
		this.descrProd = descrProd;
	}

	public String getCodVol() {
		return codVol;
	}

	public void setCodVol(String codVol) {
		this.codVol = codVol;
	}

	public BigDecimal getQtdSolicitado() {
		return qtdSolicitado;
	}

	public void setQtdSolicitado(BigDecimal qtdSolicitado) {
		this.qtdSolicitado = qtdSolicitado;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getCodUsuAtd() {
		return codUsuAtd;
	}

	public void setCodUsuAtd(BigDecimal codUsuAtd) {
		this.codUsuAtd = codUsuAtd;
	}

	public List<ProdutoAtendimentoRequisicao> getProdutoAtendimentos() {
		return produtoAtendimentos;
	}

	public void setProdutoAtendimentos(List<ProdutoAtendimentoRequisicao> produtoAtendimentos) {
		this.produtoAtendimentos = produtoAtendimentos;
	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.nuReq = vo.asBigDecimal(SklFieldName.NUREQ);
		this.sequencia = vo.asBigDecimal(SklFieldName.SEQUENCIA);
		this.codProd = vo.asBigDecimal(SklFieldName.CODPROD);
		this.descrProd = vo.asString("Produto.DESCRPROD");
		this.codVol = vo.asString(SklFieldName.CODVOL);
		this.qtdSolicitado = vo.asBigDecimal(SklFieldName.QTDSOLICITADO);
		this.status = vo.asString(SklFieldName.STATUS);
		this.codUsuAtd = vo.asBigDecimal(SklFieldName.CODUSUATD);
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
			vo = dao.findByPK(nuReq, sequencia);
		} catch (Exception e) {
			throw new DataDictionaryExceptions.UpdateNotPossibleException(e);
		}

		try {
			if (vo != null) {
				EntityUpdate entityUpdate = new EntityUpdate(ENTITY_NAME, vo);
				entityUpdate.setField(SklFieldName.STATUS, status);
				entityUpdate.setField(SklFieldName.CODUSUATD, codUsuAtd);
				
				entityUpdate.fluidUpdate();
			}
		} catch (SkalaModelException e) {
			throw new DataDictionaryExceptions.UpdateNotPossibleException(e);
		}

		return null;
	}

	@Override
	public String toString() {
		return String.format(
				"ProdutoRequisicao [nuReq=%s, sequencia=%s, codProd=%s, descrProd=%s, codVol=%s, qtdSolicitado=%s, status=%s, codUsuAtd=%s, produtoAtendimentos=%s]",
				nuReq, sequencia, codProd, descrProd, codVol, qtdSolicitado, status, codUsuAtd, produtoAtendimentos);
	}
	
}
