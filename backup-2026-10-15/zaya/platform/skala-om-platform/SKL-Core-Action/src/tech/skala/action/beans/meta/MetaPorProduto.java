package tech.skala.action.beans.meta;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.TimeUtils;
import com.sankhya.util.TimeUtils.TipoPeriodo;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.action.beans.produto.ProdutoMeta;
import tech.skala.action.modules.comercial.meta.ComercialUtilities;
import tech.skala.action.modules.comercial.meta.MetaPorProdutoFinder;
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
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.modules.suprimentos.recebimento.TipoEstoqueEnum;

public class MetaPorProduto implements EntityBean, LoadableByValueObject, Persistable {
	public static final String 				ENTITY_NAME	= "AD_PLTGFFAMMETSKU";

	private BigDecimal						codEmp;
    private Timestamp                       dtRef;
	private BigDecimal						codFam;
	private BigDecimal						codProd;
	private ProdutoMeta						produtoMeta;
	private BigDecimal						qtdPrevC;
	private BigDecimal						qtdPrevM;
	private BigDecimal						qtdAjuC;
    private BigDecimal						qtdAjuM;
	private BigDecimal						qtdAjuCPad = BigDecimal.ZERO;;
    private BigDecimal						qtdAjuMPad = BigDecimal.ZERO;;
	private BigDecimal						qtdPrev;
	private BigDecimal						percABC;
	private BigDecimal						percAcumulado;
	private String							categoriaABC;
	private BigDecimal						qtdPendAtu;
	private BigDecimal						qtdMedia;
	private BigDecimal						qtdPendente;
	private BigDecimal						vlrPendente;
	private BigDecimal						vlrPendAtu;
	private BigDecimal						vlrUnit;
	private BigDecimal						vlrPrev;
	private BigDecimal						estoqueDisp = BigDecimal.ZERO;
	private BigDecimal						estoqueIni = BigDecimal.ZERO;
	private BigDecimal						estoqueFinal = BigDecimal.ZERO;

	public MetaPorProduto(BigDecimal codFam, BigDecimal codEmp, Timestamp dtRef, BigDecimal codProd)
			throws SkalaModelException {

		this.codFam = codFam;
		this.codEmp = codEmp;
		this.dtRef = dtRef;
		this.codProd = codProd;

		this.produtoMeta = new ProdutoMeta(codProd);
		produtoMeta.loadByPk();
	}

	public MetaPorProduto(JdbcWrapper jdbcHeir, BigDecimal codFam2, BigDecimal codempcomercial, Timestamp dtRef2,
			Object codProd2, MetaParams metaParams) {
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getCodEmp() {
		return codEmp;
	}

	public Timestamp getDtRef() {
		return dtRef;
	}

	public BigDecimal getCodFam() {
		return codFam;
	}

	public BigDecimal getCodProd() {
		return codProd;
	}

	public ProdutoMeta getProduto() {
		return produtoMeta;
	}

	public BigDecimal getQtdPrevC() {
		return qtdPrevC;
	}

	public void setQtdPrevC(BigDecimal qtdPrevC) {
		this.qtdPrevC = qtdPrevC;
	}

	public BigDecimal getQtdPrevM() {
		return qtdPrevM;
	}

	public void setQtdPrevM(BigDecimal qtdPrevM) {
		this.qtdPrevM = qtdPrevM;
	}

	public BigDecimal getQtdAjuC() {
		return qtdAjuC;
	}

	public void setQtdAjuC(BigDecimal qtdAjuC) {
		this.qtdAjuC = qtdAjuC;
	}

	public BigDecimal getQtdAjuM() {
		return qtdAjuM;
	}

	public void setQtdAjuM(BigDecimal qtdAjuM) {
		this.qtdAjuM = qtdAjuM;
	}

	public BigDecimal getQtdPrev() {
		return qtdPrev;
	}

	public void setQtdPrev(BigDecimal qtdPrev) {
		this.qtdPrev = qtdPrev;
	}

	public BigDecimal getQtdMedia() {
		return qtdMedia;
	}

	public void setQtdMedia(BigDecimal qtdMedia) {
		this.qtdMedia = qtdMedia;
	}

	public BigDecimal getQtdPendente() {
		return qtdPendente;
	}

	public void setQtdPendente(BigDecimal qtdPendente) {
		this.qtdPendente = qtdPendente;
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

	public BigDecimal getPercABC() {
		return percABC;
	}

	public void setPercABC(BigDecimal percABC) {
		this.percABC = percABC;
	}

	public BigDecimal getPercAcumulado() {
		return percAcumulado;
	}

	public void setPercAcumulado(BigDecimal percAcumulado) {
		this.percAcumulado = percAcumulado;
	}

	public String getCategoriaABC() {
		return categoriaABC;
	}

	public void setCategoriaABC(String categoriaABC) {
		this.categoriaABC = categoriaABC;
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

	public BigDecimal getEstoqueIni() {
		return estoqueIni;
	}

	public void setEstoqueIni(BigDecimal estoqueIni) {
		this.estoqueIni = estoqueIni;
	}

	public BigDecimal getEstoqueFinal() {
		return estoqueFinal;
	}

	public void setEstoqueFinal(BigDecimal estoqueFinal) {
		this.estoqueFinal = estoqueFinal;
	}

	public BigDecimal getQtdPendAtu() {
		return qtdPendAtu;
	}

	public void setQtdPendAtu(BigDecimal qtdPendAtu) {
		this.qtdPendAtu = qtdPendAtu;
	}

	private boolean isMesFuturo() {
		return TimeUtils.getInicioPeriodo(TimeUtils.getNow(), TipoPeriodo.MES).compareTo(dtRef) < 0;
	}

	public BigDecimal getVlrPendAtu() {
		return vlrPendAtu;
	}

	public void setVlrPendAtu(BigDecimal vlrPendAtu) {
		this.vlrPendAtu = vlrPendAtu;
	}

	public BigDecimal getQtdAjuCPad() {
		return qtdAjuCPad;
	}

	public void setQtdAjuCPad(BigDecimal qtdAjuCPad) {
		this.qtdAjuCPad = qtdAjuCPad;
	}

	public BigDecimal getQtdAjuMPad() {
		return qtdAjuMPad;
	}

	public void setQtdAjuMPad(BigDecimal qtdAjuMPad) {
		this.qtdAjuMPad = qtdAjuMPad;
	}

	public BigDecimal getEstoqueDisp() {
		return estoqueDisp;
	}

	public void setEstoqueDisp(BigDecimal estoqueDisp) {
		this.estoqueDisp = estoqueDisp;
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);

		DynamicVO vo;
		try {
			vo = dao.findByPK(codFam, codEmp, dtRef, codProd);
		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}

		if (vo == null) {
			EntityInsert entityInsert = new EntityInsert(ENTITY_NAME);
			entityInsert.setField(SklFieldName.CODEMP, codEmp);
			entityInsert.setField(SklFieldName.DTREF, dtRef);
			entityInsert.setField(SklFieldName.CODFAM, codFam);
			entityInsert.setField(SklFieldName.CODPROD, codProd);
			entityInsert.setField("QTDPREVC", qtdPrevC);
			entityInsert.setField("QTDPREVM", qtdPrevM);
			entityInsert.setField("QTDAJUC", qtdAjuC);
			entityInsert.setField("QTDAJUM", qtdAjuM);
			entityInsert.setField("QTDAJUCPAD", qtdAjuCPad);
			entityInsert.setField("QTDAJUMPAD", qtdAjuMPad);
			entityInsert.setField("QTDPREV", qtdPrev);
			entityInsert.setField("PERCABC", percABC);
			entityInsert.setField("PERCACUMULADO", percAcumulado);
			entityInsert.setField("CATEGORIAABC", categoriaABC);
			entityInsert.setField("QTDPENDENTE", qtdPendente);
			entityInsert.setField("VLRPENDENTE", vlrPendente);
			entityInsert.setField("VLRPENDATU", vlrPendAtu);
			entityInsert.setField(SklFieldName.VLRUNIT, vlrUnit);
			entityInsert.setField("VLRPREV", vlrPrev);
			entityInsert.setField("ESTOQUEDISP", estoqueDisp);
			entityInsert.setField("ESTOQUE_INI", estoqueIni);
			entityInsert.setField("ESTOQUE_FINAL", estoqueFinal);
			entityInsert.setField("QTDMEDIA", qtdMedia);
			entityInsert.setField("QTDPENDATU", qtdPendAtu);
			entityInsert.setField("DIGITADO", "N");

			entityInsert.fluidInsert();
		} else {
			EntityUpdate entityUpdate = new EntityUpdate(ENTITY_NAME, vo);
			entityUpdate.setField("QTDPREVC", qtdPrevC);
			entityUpdate.setField("QTDPREVM", qtdPrevM);
			entityUpdate.setField("QTDAJUC", qtdAjuC);
			entityUpdate.setField("QTDAJUM", qtdAjuM);
			entityUpdate.setField("QTDAJUCPAD", qtdAjuCPad);
			entityUpdate.setField("QTDAJUMPAD", qtdAjuMPad);
			entityUpdate.setField("PERCABC", percABC);
			entityUpdate.setField("PERCACUMULADO", percAcumulado);
			entityUpdate.setField("CATEGORIAABC", categoriaABC);
			entityUpdate.setField("QTDPENDENTE", qtdPendente);
			entityUpdate.setField("VLRPENDENTE", vlrPendente);
			entityUpdate.setField("VLRPENDATU", vlrPendAtu);
			entityUpdate.setField(SklFieldName.VLRUNIT, vlrUnit);
			entityUpdate.setField("VLRPREV", vlrPrev);
			entityUpdate.setField("ESTOQUEDISP", estoqueDisp);
			entityUpdate.setField("ESTOQUE_INI", estoqueIni);
			entityUpdate.setField("ESTOQUE_FINAL", estoqueFinal);
			entityUpdate.setField("QTDMEDIA", qtdMedia);
			entityUpdate.setField("QTDPENDATU", qtdPendAtu);
			if ("N".equals(vo.asString("DIGITADO")))
				entityUpdate.setField("QTDPREV", qtdPrev);

			entityUpdate.fluidUpdate();
		}

		return null;
	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		try {
			this.qtdPrevC = vo.asBigDecimalOrZero("QTDPREVC");
			this.qtdPrevM = vo.asBigDecimalOrZero("QTDPREVM");
			this.qtdAjuC = vo.asBigDecimalOrZero("QTDAJUC");
			this.qtdAjuM = vo.asBigDecimalOrZero("QTDAJUM");
			this.qtdAjuCPad = vo.asBigDecimalOrZero("QTDAJUCPAD");
			this.qtdAjuMPad = vo.asBigDecimalOrZero("QTDAJUMPAD");
			this.qtdPrev = vo.asBigDecimalOrZero("QTDPREV");
			this.percABC = vo.asBigDecimalOrZero("PERCABC");
			this.percAcumulado = vo.asBigDecimalOrZero("PERCACUMULADO");
			this.categoriaABC = vo.asString("CATEGORIAABC");
			this.qtdPendente = vo.asBigDecimalOrZero("QTDPENDENTE");
			this.vlrPendente = vo.asBigDecimalOrZero("VLRPENDENTE");
			this.vlrUnit = vo.asBigDecimalOrZero("VLRUNIT");
			this.vlrPrev = qtdPrev.multiply(vlrUnit, BigDecimalUtil.MATH_CTX);
			this.estoqueFinal = vo.asBigDecimalOrZero("ESTOQUE_FINAL");

			initCampos();
		} catch (SkalaModelException e) {
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		}
	}

	public void initCampos() throws SkalaModelException {
		calculaCamposCarteira();

		this.estoqueIni = isMesFuturo()
				? MetaPorProdutoFinder.getEstoqueFinalReferenciaAnterior(codFam, codEmp, codProd, dtRef)
				: ComercialUtilities.getEstoqueInicial(codEmp, BigDecimal.valueOf(200100), codProd, dtRef);
	}

	public void calculaCamposCarteira() throws SkalaModelException {
		this.qtdPendente = ComercialUtilities.getCarteiraInicial(codEmp, codProd, dtRef);

		this.qtdPendAtu = ComercialUtilities.getCarteiraAtual(codEmp, codProd);
		this.vlrPendAtu = qtdPendAtu.multiply(BigDecimalUtil.getValueOrZero(vlrUnit), BigDecimalUtil.MATH_CTX);

		this.qtdMedia = ComercialUtilities.getMediaTrimestral(codEmp, codProd, dtRef);

		this.estoqueDisp = ComercialUtilities.getEstoque(codEmp, codProd, BigDecimal.valueOf(200100), " ",
				BigDecimal.ZERO, TipoEstoqueEnum.PROPRIO.getValue(), "D", "S");
	}

	@Override
	public int hashCode() {
		return Objects.hash(codEmp, codFam, codProd, dtRef);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MetaPorProduto other = (MetaPorProduto) obj;
		return Objects.equals(codEmp, other.codEmp) && Objects.equals(codFam, other.codFam)
				&& Objects.equals(codProd, other.codProd) && Objects.equals(dtRef, other.dtRef);
	}
	
	@Override
	public String toString() {
		return String.format(
				"MetaPorProduto [codEmp=%s, dtRef=%s, codFam=%s, codProd=%s, produtoMeta=%s, qtdPrevC=%s, qtdPrevM=%s, qtdAjuC=%s, qtdAjuM=%s, qtdAjuCPad=%s, qtdAjuMPad=%s, qtdPrev=%s, qtdPendAtu=%s, qtdMedia=%s, qtdPendente=%s, vlrPendente=%s, vlrPendAtu=%s, vlrUnit=%s, vlrPrev=%s, estoqueDisp=%s, estoqueIni=%s, estoqueFinal=%s]",
				codEmp, dtRef, codFam, codProd, produtoMeta, qtdPrevC, qtdPrevM, qtdAjuC, qtdAjuM, qtdAjuCPad,
				qtdAjuMPad, qtdPrev, qtdPendAtu, qtdMedia, qtdPendente, vlrPendente, vlrPendAtu, vlrUnit, vlrPrev,
				estoqueDisp, estoqueIni, estoqueFinal);
	}

}
