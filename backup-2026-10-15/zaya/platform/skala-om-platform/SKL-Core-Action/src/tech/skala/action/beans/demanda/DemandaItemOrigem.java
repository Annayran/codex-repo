package tech.skala.action.beans.demanda;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.action.beans.produto.ProdutoComercial;
import tech.skala.action.modules.centrais.CabecalhoNota;
import tech.skala.action.modules.centrais.ItemNota;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.EntityUpdate;

public class DemandaItemOrigem implements EntityBean, Persistable {

	private BigDecimal nuDemanda = BigDecimal.ZERO;
	private BigDecimal semana = BigDecimal.ZERO;
	private BigDecimal nuNota;
	private BigDecimal sequencia;
	private BigDecimal codEmp;
	private BigDecimal codLocalOrig;
	private String controle;
	private ProdutoComercial produto;
	private String pendente;
	private String reserva;
	private BigDecimal qtdNeg;
	private String codVol;
	private BigDecimal qtdConferida;
	private BigDecimal qtdEntregue;
	private BigDecimal qtdPendente;
	private BigDecimal vlrUnit;
	private BigDecimal vlrTot;
	private BigDecimal vlrDesc;
	private BigDecimal vlrUnitLiq;
	private BigDecimal vlrPendente;
	private String statusNota;
	private BigDecimal codTipOper;
	private Timestamp dhTipOper;
	private String tipMov;
	private String tipo;
	private String comEx;
	private String programado;
	private Timestamp dtNeg;
	private Timestamp dtComercial;
	private Timestamp dhAgendamento;
	private BigDecimal codParc;

	public BigDecimal getNuDemanda() {
		return nuDemanda;
	}

	public void setNuDemanda(BigDecimal nuDemanda) {
		this.nuDemanda = nuDemanda;
	}

	public BigDecimal getSemana() {
		return semana;
	}

	public void setSemana(BigDecimal semana) {
		this.semana = semana;
	}

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

	public ProdutoComercial getProduto() {
		return produto;
	}

	public void setProduto(ProdutoComercial produto) {
		this.produto = produto;
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

	public BigDecimal getQtdConferida() {
		return qtdConferida;
	}

	public void setQtdConferida(BigDecimal qtdConferida) {
		this.qtdConferida = qtdConferida;
	}

	public BigDecimal getQtdEntregue() {
		return qtdEntregue;
	}

	public void setQtdEntregue(BigDecimal qtdEntregue) {
		this.qtdEntregue = qtdEntregue;
	}

	public BigDecimal getQtdPendente() {
		return qtdPendente;
	}

	public void setQtdPendente(BigDecimal qtdPendente) {
		this.qtdPendente = qtdPendente;
	}

	public BigDecimal getVlrUnit() {
		return vlrUnit;
	}

	public void setVlrUnit(BigDecimal vlrUnit) {
		this.vlrUnit = vlrUnit;
	}

	public BigDecimal getVlrTot() {
		return vlrTot;
	}

	public void setVlrTot(BigDecimal vlrTot) {
		this.vlrTot = vlrTot;
	}

	public BigDecimal getVlrDesc() {
		return vlrDesc;
	}

	public void setVlrDesc(BigDecimal vlrDesc) {
		this.vlrDesc = vlrDesc;
	}

	public BigDecimal getVlrUnitLiq() {
		return vlrUnitLiq;
	}

	public void setVlrUnitLiq(BigDecimal vlrUnitLiq) {
		this.vlrUnitLiq = vlrUnitLiq;
	}

	public BigDecimal getVlrPendente() {
		return vlrPendente;
	}

	public void setVlrPendente(BigDecimal vlrPendente) {
		this.vlrPendente = vlrPendente;
	}

	public String getStatusNota() {
		return statusNota;
	}

	public void setStatusNota(String statusNota) {
		this.statusNota = statusNota;
	}

	public BigDecimal getCodTipOper() {
		return codTipOper;
	}

	public void setCodTipOper(BigDecimal codTipOper) {
		this.codTipOper = codTipOper;
	}

	public Timestamp getDhTipOper() {
		return dhTipOper;
	}

	public void setDhTipOper(Timestamp dhTipOper) {
		this.dhTipOper = dhTipOper;
	}

	public String getTipMov() {
		return tipMov;
	}

	public void setTipMov(String tipMov) {
		this.tipMov = tipMov;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getComEx() {
		return comEx;
	}

	public void setComEx(String comEx) {
		this.comEx = comEx;
	}

	public String getProgramado() {
		return programado;
	}

	public void setProgramado(String programado) {
		this.programado = programado;
	}

	public Timestamp getDtNeg() {
		return dtNeg;
	}

	public void setDtNeg(Timestamp dtNeg) {
		this.dtNeg = dtNeg;
	}

	public Timestamp getDtComercial() {
		return dtComercial;
	}

	public void setDtComercial(Timestamp dtComercial) {
		this.dtComercial = dtComercial;
	}

	public Timestamp getDhAgendamento() {
		return dhAgendamento;
	}

	public void setDhAgendamento(Timestamp dhAgendamento) {
		this.dhAgendamento = dhAgendamento;
	}

	public BigDecimal getCodParc() {
		return codParc;
	}

	public void setCodParc(BigDecimal codParc) {
		this.codParc = codParc;
	}

//public void setFromNotaBean(CabecalhoNota cabecalhoNota, ItemNota itemNota) {
//		
//		this.nuNota = cabecalhoNota.getNuNota();
//		this.sequencia = itemNota.getSequencia();
//		this.codEmp = cabecalhoNota.getCodEmp();
//		this.codLocalOrig = itemNota.getCodLocalOrig();
//		this.controle = itemNota.getControle();
//		this.produto = itemNota.getProduto();
//		this.pendente = itemNota.getPendente();
//		this.reserva = itemNota.getReserva();
//		this.qtdNeg = itemNota.getQtdNeg();
//		this.codVol = itemNota.getCodVol();
//		this.qtdConferida = itemNota.getQtdConferida();
//		this.qtdEntregue = itemNota.getQtdEntregue();
//		this.qtdPendente = itemNota.getQtdNeg().subtract(itemNota.getQtdEntregue());
//		this.vlrUnit = itemNota.getVlrUnit();
//		this.vlrTot = itemNota.getVlrTot();
//		this.vlrDesc = itemNota.getVlrDesc();
//		this.vlrUnitLiq = (BigDecimal.ZERO.equals(itemNota.getQtdNeg())) ? BigDecimal.ZERO : (itemNota.getVlrTot().subtract(itemNota.getVlrDesc())).divide(itemNota.getQtdNeg(),
//				BigDecimalUtil.MATH_CTX);
//		this.vlrPendente = vlrUnitLiq.multiply(qtdPendente, BigDecimalUtil.MATH_CTX);
//		this.statusNota = cabecalhoNota.getStatusNota();
//		this.codTipOper = cabecalhoNota.getCodTipOper();
//		this.dhTipOper = cabecalhoNota.getDhTipOper();
//		this.tipMov = cabecalhoNota.getTipMov();
//		this.tipo = cabecalhoNota.getTipoCarteira().getTipo();
//		this.comEx = cabecalhoNota.getComEx();
//		this.programado =  cabecalhoNota.getCabecalhoNotaPlt().getProgramado();
//		this.dtNeg = cabecalhoNota.getDtNeg();
//		this.dtComercial = cabecalhoNota.getDtComercial();
//		this.dhAgendamento = cabecalhoNota.getDhAgendamento();
//		this.codParc = cabecalhoNota.getCodParc();
//	}
	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao("AD_PLTGFDMITEORIG");

		try {
			DynamicVO vo = dao.findOne(
					"this.NUDEMANDA = ? AND this.SEMANA = ? AND this.CODPROD = ? AND this.NUNOTA = ? AND this.SEQUENCIA = ?",
					nuDemanda, semana, produto.getCodProd(), nuNota, sequencia);

			if (vo == null) {
				EntityInsert insert = new EntityInsert("AD_PLTGFDMITEORIG");
				insert.setField("NUDEMANDA", nuDemanda);
				insert.setField("SEMANA", semana);
				insert.setField("NUNOTA", nuNota);
				insert.setField("SEQUENCIA", sequencia);
				insert.setField("CODEMP", codEmp);
				insert.setField("CODLOCALORIG", codLocalOrig);
				insert.setField("CONTROLE", controle);
				insert.setField("CODPROD", produto.getCodProd());
				insert.setField("CODFAM", produto.getFamilia().getCodFam());
				insert.setField("PENDENTE", pendente);
				insert.setField("RESERVA", reserva);
				insert.setField("QTDNEG", qtdNeg);
				insert.setField("CODVOL", codVol);
				insert.setField("QTDCONFERIDA", qtdConferida);
				insert.setField("QTDENTREGUE", qtdEntregue);
				insert.setField("QTDPENDENTE", qtdPendente);
				insert.setField("VLRUNIT", vlrUnit);
				insert.setField("VLRTOT", vlrTot);
				insert.setField("VLRDESC", vlrDesc);
				insert.setField("VLRUNITLIQ", vlrUnitLiq);
				insert.setField("VLRPENDENTE", vlrPendente);
				insert.setField("STATUSNOTA", statusNota);
				insert.setField("CODTIPOPER", codTipOper);
				insert.setField("DHTIPOPER", dhTipOper);
				insert.setField("TIPMOV", tipMov);
				insert.setField("TIPO", tipo);
				insert.setField("COMEX", comEx);
				insert.setField("PROGRAMADO", programado);
				insert.setField("DTNEG", dtNeg);
				insert.setField("DTCOMERCIAL", dtComercial);
				insert.setField("DHAGENDAMENTO", dhAgendamento);
				insert.setField("CODPARC", codParc);
				insert.fluidInsert();
			} else {
				EntityUpdate update = new EntityUpdate("AD_PLTGFDMITEORIG", vo);
				update.setField("CODEMP", codEmp);
				update.setField("CODLOCALORIG", codLocalOrig);
				update.setField("CONTROLE", controle);
				update.setField("CODFAM", produto.getFamilia().getCodFam());
				update.setField("PENDENTE", pendente);
				update.setField("RESERVA", reserva);
				update.setField("QTDNEG", qtdNeg);
				update.setField("CODVOL", codVol);
				update.setField("QTDCONFERIDA", qtdConferida);
				update.setField("QTDENTREGUE", qtdEntregue);
				update.setField("QTDPENDENTE", qtdPendente);
				update.setField("VLRUNIT", vlrUnit);
				update.setField("VLRTOT", vlrTot);
				update.setField("VLRDESC", vlrDesc);
				update.setField("VLRUNITLIQ", vlrUnitLiq);
				update.setField("VLRPENDENTE", vlrPendente);
				update.setField("STATUSNOTA", statusNota);
				update.setField("CODTIPOPER", codTipOper);
				update.setField("DHTIPOPER", dhTipOper);
				update.setField("TIPMOV", tipMov);
				update.setField("TIPO", tipo);
				update.setField("COMEX", comEx);
				update.setField("PROGRAMADO", programado);
				update.setField("DTNEG", dtNeg);
				update.setField("DTCOMERCIAL", dtComercial);
				update.setField("DHAGENDAMENTO", dhAgendamento);
				update.setField("CODPARC", codParc);
				update.fluidUpdate();
			}

		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}

		return null;
	}

	@Override
	public String toString() {
		return String.format(
				"DemandaItemOrigem [nuDemanda=%s, semana=%s, nuNota=%s, sequencia=%s, codEmp=%s, codLocalOrig=%s, controle=%s, produto=%s, pendente=%s, reserva=%s, qtdNeg=%s, codVol=%s, qtdConferida=%s, qtdEntregue=%s, qtdPendente=%s, vlrUnit=%s, vlrTot=%s, vlrDesc=%s, vlrUnitLiq=%s, vlrPendente=%s, statusNota=%s, codTipOper=%s, dhTipOper=%s, tipMov=%s, tipo=%s, comEx=%s, programado=%s, dtNeg=%s, dtComercial=%s, dhAgendamento=%s, codParc=%s]",
				nuDemanda, semana, nuNota, sequencia, codEmp, codLocalOrig, controle, produto, pendente, reserva,
				qtdNeg, codVol, qtdConferida, qtdEntregue, qtdPendente, vlrUnit, vlrTot, vlrDesc, vlrUnitLiq,
				vlrPendente, statusNota, codTipOper, dhTipOper, tipMov, tipo, comEx, programado, dtNeg, dtComercial,
				dhAgendamento, codParc);
	}

	public void setFromNotaBean(CabecalhoNota cabecalhoNota, ItemNota itemNota) {
		this.nuNota = cabecalhoNota.getNuNota();
		this.sequencia = itemNota.getSequencia();
		this.codEmp = cabecalhoNota.getCodEmp();
		this.codLocalOrig = itemNota.getCodLocalOrig();
		this.controle = itemNota.getControle();
		this.produto = itemNota.getProduto();
		this.pendente = itemNota.getPendente();
		this.reserva = itemNota.getReserva();
		this.qtdNeg = itemNota.getQtdNeg();
		this.codVol = itemNota.getCodVol();
		this.qtdConferida = itemNota.getQtdConferida();
		this.qtdEntregue = itemNota.getQtdEntregue();
		this.qtdPendente = itemNota.getQtdNeg().subtract(itemNota.getQtdEntregue());
		this.vlrUnit = itemNota.getVlrUnit();
		this.vlrTot = itemNota.getVlrTot();
		this.vlrDesc = itemNota.getVlrDesc();
		this.vlrUnitLiq = (BigDecimal.ZERO.equals(itemNota.getQtdNeg())) ? BigDecimal.ZERO
				: (itemNota.getVlrTot().subtract(itemNota.getVlrDesc())).divide(itemNota.getQtdNeg(),
						BigDecimalUtil.MATH_CTX);
		this.vlrPendente = vlrUnitLiq.multiply(qtdPendente, BigDecimalUtil.MATH_CTX);
		this.statusNota = cabecalhoNota.getStatusNota();
		this.codTipOper = cabecalhoNota.getCodTipOper();
		this.dhTipOper = cabecalhoNota.getDhTipOper();
		this.tipMov = cabecalhoNota.getTipMov();
		this.tipo = cabecalhoNota.getTipoCarteira().getTipo();
		this.comEx = cabecalhoNota.getComEx();
		this.programado = cabecalhoNota.getCabecalhoNotaSkl().getProgramado();
		this.dtNeg = cabecalhoNota.getDtNeg();
		this.dtComercial = cabecalhoNota.getDtComercial();
		this.dhAgendamento = cabecalhoNota.getDhAgendamento();
		this.codParc = cabecalhoNota.getCodParc();

	}

}
