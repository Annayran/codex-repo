package tech.skala.modules.inventario;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByRecord;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class ContagemInventario implements EntityBean, LoadableByValueObject, LoadableByRecord,Persistable {
	public static final String				ENTITY_NAME = SklEntityName.CONTAGEM_INVENTARIO;
	
	private	BigDecimal								codEmp;
	private	BigDecimal 								codLocal;
	private String									descrLocal;
	private	BigDecimal 								codParc;
	private	String 									tipo;
	private	BigDecimal 								sequencia;
	private	BigDecimal 								idIvt;
	private	BigDecimal 								codExecutante;
	private	String 									status;
	private List<ProdutoContagem>					produtos = new ArrayList<>();
	
	public BigDecimal getCodEmp() {
		return codEmp;
	}

	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}

	public BigDecimal getCodLocal() {
		return codLocal;
	}

	public void setCodLocal(BigDecimal codLocal) {
		this.codLocal = codLocal;
	}

	public String getDescrLocal() {
		return descrLocal;
	}

	public void setDescrLocal(String descrLocal) {
		this.descrLocal = descrLocal;
	}

	public BigDecimal getCodParc() {
		return codParc;
	}

	public void setCodParc(BigDecimal codParc) {
		this.codParc = codParc;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getSequencia() {
		return sequencia;
	}

	public void setSequencia(BigDecimal sequencia) {
		this.sequencia = sequencia;
	}

	public BigDecimal getIdIvt() {
		return idIvt;
	}

	public void setIdIvt(BigDecimal idIvt) {
		this.idIvt = idIvt;
	}

	public BigDecimal getCodExecutante() {
		return codExecutante;
	}

	public void setCodExecutante(BigDecimal codExecutante) {
		this.codExecutante = codExecutante;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ProdutoContagem> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<ProdutoContagem> produtos) {
		this.produtos = produtos;
	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.codEmp = vo.asBigDecimal(SklFieldName.CODEMP);
		this.codLocal = vo.asBigDecimal(SklFieldName.CODLOCAL);
		this.descrLocal = vo.asString("LocalFinanceiro.DESCRLOCAL");
		this.codParc = vo.asBigDecimal(SklFieldName.CODPARC);
		this.tipo = vo.asString(SklFieldName.TIPO);
		this.sequencia = vo.asBigDecimal(SklFieldName.SEQUENCIA);
		this.idIvt = vo.asBigDecimal(SklFieldName.IDIVT);
		this.codExecutante = vo.asBigDecimal(SklFieldName.CODEXECUTANTE);
		this.status = vo.asString(SklFieldName.STATUS);
	}
	
	@Override
	public void loadByRecord(Registro linha) throws RecordNotFoundException {
		this.codEmp = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.CODEMP));
		this.codLocal = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.CODLOCAL));
		this.codParc = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.CODPARC));
		this.tipo = String.valueOf(linha.getCampo(SklFieldName.TIPO));
		this.sequencia = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.SEQUENCIA));
		this.idIvt = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.IDIVT));
		this.codExecutante = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.CODEXECUTANTE));
		this.status = String.valueOf(linha.getCampo(SklFieldName.STATUS));

		try {
			this.produtos = ProdutoContagemFinder.buscaProdutosContagem(this);
		} catch (SkalaModelException e) {
			throw new RecordNotFoundException(e);
		}
	}
	
	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);

		DynamicVO vo = null;
		if (idIvt != null && codEmp != null && codLocal != null && codParc != null && tipo != null && sequencia != null) {
			try {
				vo = dao.findByPK(idIvt, codEmp, codLocal, codParc, tipo, sequencia);
			} catch (Exception e) {
				throw new UpdateNotPossibleException(e);
			}

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
				"ContagemInventario [codEmp=%s, codLocal=%s, codParc=%s, tipo=%s, sequencia=%s, idIvt=%s, codExecutante=%s, status=%s, produtos=%s]",
				codEmp, codLocal, codParc, tipo, sequencia, idIvt, codExecutante, status, produtos);
	}

}
