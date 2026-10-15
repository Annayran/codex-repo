package tech.skala.action.beans.meta;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.action.modules.comercial.meta.MetaPorProdutoFinder;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByRecord;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;

/**
 * A classe {@code MetaPorFamilia} representa a tabela {@code AD_PLVGFFAMPRO}. 
 * <p>
 * 
 * A classe {@code MetaPorFamilia} representa a tabela {@code AD_PLVGFFAMPRO} e 
 * carrega a lista com os respectivos produtos de uma Família.
 *  
 * @author hiagoalexandre
 * @author cassiomenezes
 * @since 2023-04-25
 *
 */
public class MetaPorFamilia implements EntityBean, LoadableByRecord, LoadableByValueObject, Persistable {

	private static final String				ENTITY_NAME = "AD_PLTGFFAMMET";
	
	private BigDecimal						codEmp;
    private Timestamp                       dtRef;
	private BigDecimal						codFam;
	private BigDecimal						qtdPrev;
	private BigDecimal						qtdPrevAju;
	private BigDecimal						qtdPrevSku;
	private List<MetaPorProduto>			produtos = new LinkedList<>();
	private BigDecimal						qtdPendente = BigDecimal.ZERO;
	private BigDecimal						qtdMedia = BigDecimal.ZERO;
	private BigDecimal						vlrPendente;
	private BigDecimal						vlrUnit;
	private BigDecimal						vlrPrev;
	private boolean							confirmado;
	private MetaParams						params;
	
	public MetaPorFamilia(BigDecimal codEmp, Timestamp dtRef, BigDecimal codFam, boolean buildProdutos,
			boolean usaCarteira) {
		this.codEmp = codEmp;
		this.dtRef = dtRef;
		this.codFam = codFam;
		this.params = new MetaParams(buildProdutos, usaCarteira);
	}
	
	public MetaPorFamilia(boolean buildProdutos, boolean usaCarteira) {
		this.params = new MetaParams(buildProdutos, usaCarteira);
	}

	public BigDecimal getCodEmp() {
		return codEmp;
	}

	public Timestamp getDtRef() {
		return dtRef;
	}
	
	public void setDtRef(Timestamp dtRef) {
		this.dtRef = dtRef;
	}

	public BigDecimal getCodFam() {
		return codFam;
	}

	public BigDecimal getQtdPrev() {
		return qtdPrev;
	}

	public void setQtdPrev(BigDecimal qtdPrev) {
		this.qtdPrev = qtdPrev;
	}

	public BigDecimal getQtdPrevAju() {
		return qtdPrevAju;
	}

	public void setQtdPrevAju(BigDecimal qtdPrevAju) {
		this.qtdPrevAju = qtdPrevAju;
	}

	public BigDecimal getQtdPrevSku() {
		return qtdPrevSku;
	}

	public void setQtdPrevSku(BigDecimal qtdPrevSku) {
		this.qtdPrevSku = qtdPrevSku;
	}

	private void addProdutos(List<MetaPorProduto> produtos) {
		for (MetaPorProduto metaPorProduto : produtos)
			addProduto(metaPorProduto);
	}

	public void addProduto(MetaPorProduto produto) {
		this.qtdPendente = this.qtdPendente.add(produto.getQtdPendente());
		this.qtdMedia = this.qtdMedia.add(produto.getQtdMedia());
		this.produtos.add(produto);
	}

	public List<MetaPorProduto> getProdutos() {
		return produtos;
	}

	public BigDecimal getQtdPendente() {
		return qtdPendente;
	}

	public BigDecimal getQtdMedia() {
		return qtdMedia;
	}

	public BigDecimal getVlrPendente() {
		return vlrPendente;
	}

	public void setVlrPendente(BigDecimal vlrPendente) {
		this.vlrPendente = vlrPendente;
	}

	public BigDecimal getVlrUnit() {
		return vlrUnit;
	}

	public void setVlrUnit(BigDecimal vlrUnit) {
		this.vlrUnit = vlrUnit;
	}

	public BigDecimal getVlrPrev() {
		return vlrPrev;
	}

	public void setVlrPrev(BigDecimal vlrPrev) {
		this.vlrPrev = vlrPrev;
	}

	public BigDecimal getPercCob() {
		return this.params.getPercCob();
	}

	public String getUsarReferencia() {
		return this.params.getUsarReferencia();
	}

	public boolean isConfirmado() {
		return confirmado;
	}

	public void setConfirmado(boolean confirmado) {
		this.confirmado = confirmado;
	}

	public boolean usaCarteira() {
		return this.params.isUsaCarteira();
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);

		DynamicVO vo;
		try {
			vo = dao.findByPK(codFam, codEmp, dtRef);
		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}

		if (vo == null) {
			EntityInsert entityInsert = new EntityInsert(ENTITY_NAME);
			entityInsert.setField(SklFieldName.CODFAM, codFam);
			entityInsert.setField(SklFieldName.CODEMP, codEmp);
			entityInsert.setField(SklFieldName.DTREF, dtRef);
			entityInsert.setField("QTDPREV", qtdPrev);
			entityInsert.setField("QTDPREVAJU", qtdPrevAju);
			entityInsert.setField("PERCCOB", params.getPercCob());
			entityInsert.setField("USAREFERENCIA", params.getUsarReferencia());
			entityInsert.setField("QTDPENDENTE", qtdPendente);
			entityInsert.setField("VLRPREV", vlrPrev);
			entityInsert.setField("VLRPENDENTE", vlrPendente);
			entityInsert.setField(SklFieldName.VLRUNIT, vlrUnit);
			entityInsert.fluidInsert();
		} else {
			EntityUpdate entityUpdate = new EntityUpdate(ENTITY_NAME, vo);
			entityUpdate.setField("QTDPREVSKU", qtdPrevSku);
			entityUpdate.setField("VLRPREV", vlrPrev);
			entityUpdate.setField("QTDPENDENTE", qtdPendente);
			entityUpdate.setField("VLRPENDENTE", vlrPendente);
			entityUpdate.setField(SklFieldName.VLRUNIT, vlrUnit);
			entityUpdate.fluidUpdate();
		}

		return null;
	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {

		try {
			this.codEmp = vo.asBigDecimal(SklFieldName.CODEMP);
			this.dtRef = vo.asTimestamp(SklFieldName.DTREF);
			this.codFam = vo.asBigDecimal(SklFieldName.CODFAM);
			this.qtdPrev = vo.asBigDecimal("QTDPREV");
			this.qtdPrevAju = vo.asBigDecimal("QTDPREVAJU");
			this.qtdPrevSku = vo.asBigDecimal("QTDPREVSKU");
			this.confirmado = vo.asBoolean("CONFIRMADO");
			this.params.setPercCob(BigDecimalUtil.getValueOrZero(vo.asBigDecimal("PERCCOB")));
			this.params.setUsarReferencia(vo.asString("USAREFERENCIA"));

			if (params.isBuildProdutos()) {
				addProdutos(MetaPorProdutoFinder.buscaProdutosPorFamilia(codFam, codEmp, dtRef));
			} else {
				this.qtdPendente = vo.asBigDecimal("QTDPENDENTE");
				this.produtos = MetaPorProdutoFinder.buscaProdutosPorMetaPorProduto(codFam, codEmp, dtRef);
			}

		} catch (SkalaModelException e) {
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		}

	}

	@Override
	public void loadByRecord(Registro linha) throws RecordNotFoundException {
		try {
			this.codEmp = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.CODEMP));
			this.dtRef = (Timestamp) linha.getCampo(SklFieldName.DTREF);
			this.codFam = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.CODFAM));
			this.qtdPrev = BigDecimalUtil.getBigDecimal(linha.getCampo("QTDPREV"));
			this.qtdPrevAju = BigDecimalUtil.getBigDecimal(linha.getCampo("QTDPREVAJU"));
			this.confirmado = "S".equals(linha.getCampo("CONFIRMADO"));
			this.params.setPercCob(BigDecimalUtil.getValueOrZero(BigDecimalUtil.getBigDecimal(linha.getCampo("PERCCOB"))));
			this.params.setUsarReferencia((String) linha.getCampo("USAREFERENCIA"));
			
			if (params.isBuildProdutos()) {
				addProdutos(MetaPorProdutoFinder.buscaProdutosPorFamilia(codFam, codEmp, dtRef));
			} else {
				this.qtdPendente = BigDecimalUtil.getBigDecimal(linha.getCampo("QTDPENDENTE"));
				this.produtos = MetaPorProdutoFinder.buscaProdutosPorMetaPorProduto(codFam, codEmp, dtRef);
			}

		} catch (SkalaModelException e) {
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		}
	}

	@Override
	public String toString() {
		return String.format(
				"MetaPorFamilia [codEmp=%s, dtRef=%s, codFam=%s, qtdPrev=%s, qtdPrevAju=%s, qtdPrevSku=%s, produtos=%s, qtdPendente=%s, qtdMedia=%s, vlrPendente=%s, vlrUnit=%s, vlrPrev=%s, confirmado=%s, params=%s]",
				codEmp, dtRef, codFam, qtdPrev, qtdPrevAju, qtdPrevSku, produtos, qtdPendente, qtdMedia, vlrPendente,
				vlrUnit, vlrPrev, confirmado, params);
	}
	
}
