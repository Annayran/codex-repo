package tech.skala.action.modules.centrais;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;

import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.action.beans.produto.ProdutoComercial;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;

/**
 * A classe {@code ItemNota} representa a tabela {@code TGFITE}. Devera ser
 * extendida em uma classe expecífica para ser utilizada.
 * <p>
 * 
 * @author hiagoalexandre
 * @since 2022-09-21
 */
public class ItemNota implements EntityBean {
	protected static final String ENTITY_NAME = DynamicEntityNames.CABECALHO_NOTA;

	private BigDecimal nuNota;
	private BigDecimal sequencia;
	private ProdutoComercial produto;
	private BigDecimal qtdNeg;
	private String codVol;
	private BigDecimal codEmp;
	private BigDecimal codLocalOrig;
	private String controle;
	private BigDecimal qtdEntregue;
	private BigDecimal qtdConferida;
	private String pendente;
	private String reserva;
	private BigDecimal vlrUnit;
	private BigDecimal vlrUnidPad;
	private BigDecimal vlrDesc;
	private BigDecimal percDesc;
	private BigDecimal vlrTot;
	private BigDecimal vlrRepRed;
	private BigDecimal vlrSubst;
	private BigDecimal vlrIpi;
	private BigDecimal vlrItem;
	private Timestamp dtInicio;
	private Custo custo;

	public BigDecimal getNuNota() {
		return nuNota;
	}

	public void setNuNota(BigDecimal nuNota) {
		this.nuNota = nuNota;
	}

	public BigDecimal getSequencia() {
		return sequencia;
	}

	public void setSequencia(BigDecimal sequencia) {
		this.sequencia = sequencia;
	}

	public ProdutoComercial getProduto() {
		return produto;
	}

	public void setProduto(ProdutoComercial produto) {
		this.produto = produto;
	}

	public BigDecimal getQtdNeg() {
		return qtdNeg;
	}

	public void setQtdNeg(BigDecimal qtdNeg) {
		this.qtdNeg = qtdNeg;
	}

	public String getCodVol() {
		return codVol;
	}

	public void setCodVol(String codVol) {
		this.codVol = codVol;
	}

	public BigDecimal getCodEmp() {
		return codEmp;
	}

	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}

	public BigDecimal getCodLocalOrig() {
		return codLocalOrig;
	}

	public void setCodLocalOrig(BigDecimal codLocalOrig) {
		this.codLocalOrig = codLocalOrig;
	}

	public String getControle() {
		return controle;
	}

	public void setControle(String controle) {
		this.controle = controle;
	}

	public BigDecimal getQtdEntregue() {
		return qtdEntregue;
	}

	public void setQtdEntregue(BigDecimal qtdEntregue) {
		this.qtdEntregue = qtdEntregue;
	}

	public BigDecimal getQtdConferida() {
		return qtdConferida;
	}

	public void setQtdConferida(BigDecimal qtdConferida) {
		this.qtdConferida = qtdConferida;
	}

	public String getPendente() {
		return pendente;
	}

	public void setPendente(String pendente) {
		this.pendente = pendente;
	}

	public String getReserva() {
		return reserva;
	}

	public void setReserva(String reserva) {
		this.reserva = reserva;
	}

	public BigDecimal getVlrUnit() {
		return vlrUnit;
	}

	public void setVlrUnit(BigDecimal vlrUnit) {
		this.vlrUnit = vlrUnit;
	}

	public BigDecimal getVlrUnidPad() {
		return vlrUnidPad;
	}

	public void setVlrUnidPad(BigDecimal vlrUnidPad) {
		this.vlrUnidPad = vlrUnidPad;
	}

	public BigDecimal getVlrDesc() {
		return vlrDesc;
	}

	public void setVlrDesc(BigDecimal vlrDesc) {
		this.vlrDesc = vlrDesc;
	}

	public BigDecimal getPercDesc() {
		return percDesc;
	}

	public void setPercDesc(BigDecimal percDesc) {
		this.percDesc = percDesc;
	}

	public BigDecimal getVlrTot() {
		return vlrTot;
	}

	public void setVlrTot(BigDecimal vlrTot) {
		this.vlrTot = vlrTot;
	}

	public BigDecimal getVlrRepRed() {
		return vlrRepRed;
	}

	public void setVlrRepRed(BigDecimal vlrRepRed) {
		this.vlrRepRed = vlrRepRed;
	}

	public BigDecimal getVlrSubst() {
		return vlrSubst;
	}

	public void setVlrSubst(BigDecimal vlrSubst) {
		this.vlrSubst = vlrSubst;
	}

	public BigDecimal getVlrIpi() {
		return vlrIpi;
	}

	public void setVlrIpi(BigDecimal vlrIpi) {
		this.vlrIpi = vlrIpi;
	}

	public BigDecimal getVlrItem() {
		return vlrItem;
	}

	public void setVlrItem(BigDecimal vlrItem) {
		this.vlrItem = vlrItem;
	}

	public Timestamp getDtInicio() {
		return dtInicio;
	}

	public void setDtInicio(Timestamp dtInicio) {
		this.dtInicio = dtInicio;
	}

	public Custo getCusto() {
		return custo;
	}

	public void setCusto(Custo custo) {
		this.custo = custo;
	}

	public void persists() throws Exception {
		throw new UnsupportedOperationException();
	}

	public void setFromRecord(Registro linha) throws InsertNotPossibleException {
		throw new UnsupportedOperationException();
	}

	public void setFromVO(DynamicVO vo) throws Exception {

		this.nuNota = vo.asBigDecimal("NUNOTA");
		this.sequencia = vo.asBigDecimal("SEQUENCIA");

		this.produto = new ProdutoComercial((BigDecimal) vo.asDymamicVO(DynamicEntityNames.PRODUTO));
		this.produto.loadByVO(vo.asDymamicVO(DynamicEntityNames.PRODUTO));

		this.qtdNeg = vo.asBigDecimal(SklFieldName.QTDNEG);
		this.codVol = vo.asString(SklFieldName.CODVOL);
		this.codEmp = vo.asBigDecimal(SklFieldName.CODEMP);
		this.codLocalOrig = vo.asBigDecimal(SklFieldName.CODLOCALORIG);
		this.controle = vo.asString(SklFieldName.CONTROLE);
		this.qtdEntregue = vo.asBigDecimal("QTDENTREGUE");
		this.qtdConferida = vo.asBigDecimal("QTDCONFERIDA");
		this.pendente = vo.asString(SklFieldName.PENDENTE);
		this.reserva = vo.asString("RESERVA");
		this.vlrUnit = BigDecimalUtil.getValueOrZero(vo.asBigDecimal("VLRUNIT"));
		this.vlrUnidPad = BigDecimalUtil.getValueOrZero(vo.asBigDecimal("VLRUNIDPAD"));
		this.vlrDesc = BigDecimalUtil.getValueOrZero(vo.asBigDecimal("VLRDESC"));
		this.percDesc = BigDecimalUtil.getValueOrZero(vo.asBigDecimal("PERCDESC"));
		this.vlrTot = BigDecimalUtil.getValueOrZero(vo.asBigDecimal("VLRTOT"));
		this.vlrRepRed = BigDecimalUtil.getValueOrZero(vo.asBigDecimal("VLRREPRED"));
		this.vlrSubst = BigDecimalUtil.getValueOrZero(vo.asBigDecimal("VLRSUBST"));
		this.vlrIpi = BigDecimalUtil.getValueOrZero(vo.asBigDecimal("VLRIPI"));

		this.vlrItem = vlrTot.subtract(vlrDesc, BigDecimalUtil.MATH_CTX).subtract(vlrRepRed, BigDecimalUtil.MATH_CTX)
				.add(vlrSubst, BigDecimalUtil.MATH_CTX).add(vlrIpi, BigDecimalUtil.MATH_CTX);
		this.vlrItem = BigDecimalUtil.getRounded(vlrItem, 2);

		if (vo.asTimestamp("DTINICIO") != null)
			this.dtInicio = TimeUtils.clearTime(vo.asTimestamp("DTINICIO"));

	}

	public void setFromItemNotaRS(ResultSet rsItem) throws Exception {

		this.nuNota = rsItem.getBigDecimal(SklFieldName.NUNOTA);
		this.sequencia = rsItem.getBigDecimal(SklFieldName.SEQUENCIA);

		this.produto = new ProdutoComercial(rsItem.getBigDecimal(SklFieldName.CODPROD));
		this.qtdNeg = rsItem.getBigDecimal(SklFieldName.QTDNEG);
		this.codVol = rsItem.getString(SklFieldName.CODVOL);
		this.codEmp = rsItem.getBigDecimal(SklFieldName.CODEMP);
		this.codLocalOrig = rsItem.getBigDecimal(SklFieldName.CODLOCALORIG);
		this.controle = rsItem.getString(SklFieldName.CONTROLE);
		this.qtdEntregue = rsItem.getBigDecimal("QTDENTREGUE");
		this.qtdConferida = rsItem.getBigDecimal("QTDCONFERIDA");
		this.pendente = rsItem.getString(SklFieldName.PENDENTE);
		this.reserva = rsItem.getString("RESERVA");
		this.vlrUnit = BigDecimalUtil.getValueOrZero(rsItem.getBigDecimal("VLRUNIT"));
//		this.vlrUnidPad = BigDecimalUtil.getValueOrZero(rsItem.getBigDecimal("VLRUNIDPAD"));
		this.vlrDesc = BigDecimalUtil.getValueOrZero(rsItem.getBigDecimal("VLRDESC"));
		this.percDesc = BigDecimalUtil.getValueOrZero(rsItem.getBigDecimal("PERCDESC"));
		this.vlrTot = BigDecimalUtil.getValueOrZero(rsItem.getBigDecimal("VLRTOT"));
		this.vlrRepRed = BigDecimalUtil.getValueOrZero(rsItem.getBigDecimal("VLRREPRED"));
		this.vlrSubst = BigDecimalUtil.getValueOrZero(rsItem.getBigDecimal("VLRSUBST"));
		this.vlrIpi = BigDecimalUtil.getValueOrZero(rsItem.getBigDecimal("VLRIPI"));

		this.vlrItem = vlrTot.subtract(vlrDesc, BigDecimalUtil.MATH_CTX).subtract(vlrRepRed, BigDecimalUtil.MATH_CTX)
				.add(vlrSubst, BigDecimalUtil.MATH_CTX).add(vlrIpi, BigDecimalUtil.MATH_CTX);
		this.vlrItem = BigDecimalUtil.getRounded(vlrItem, 2);

		if (rsItem.getTimestamp("DTINICIO") != null)
			this.dtInicio = TimeUtils.clearTime(rsItem.getTimestamp("DTINICIO"));

	}

	@Override
	public String toString() {
		return String.format(
				"ItemNota [nuNota=%s, sequencia=%s, produto=%s, qtdNeg=%s, codVol=%s, codEmp=%s, codLocalOrig=%s, controle=%s, qtdEntregue=%s, qtdConferida=%s, pendente=%s, reserva=%s, vlrUnit=%s, vlrUnidPad=%s, vlrDesc=%s, percDesc=%s, vlrTot=%s, vlrRepRed=%s, vlrSubst=%s, vlrIpi=%s, vlrItem=%s, dtInicio=%s, custo=%s]",
				nuNota, sequencia, produto, qtdNeg, codVol, codEmp, codLocalOrig, controle, qtdEntregue, qtdConferida,
				pendente, reserva, vlrUnit, vlrUnidPad, vlrDesc, percDesc, vlrTot, vlrRepRed, vlrSubst, vlrIpi, vlrItem,
				dtInicio, custo);
	}
}
