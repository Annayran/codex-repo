package tech.skala.action.modules.inventario;

import java.math.BigDecimal;
import java.sql.Timestamp;

import br.com.sankhya.modelcore.auth.AuthenticationInfo;

public class Params {
	private BigDecimal						nuNotaOrig;
	private BigDecimal						nuNotaDest;
	private BigDecimal						codTipOperDestino;
	private Timestamp						dhAgendamento;
	private Timestamp						dtLibCom;
	private String							observacaoPedido;
	private String							observacao;
	private AuthenticationInfo				authInfo;
	private BigDecimal						codEmp;
	private BigDecimal						codLocalOrig;

	public BigDecimal getNuNotaOrig() {
		return nuNotaOrig;
	}

	public void setNuNotaOrig(BigDecimal nuNotaOrig) {
		this.nuNotaOrig = nuNotaOrig;
	}

	public BigDecimal getNuNotaDest() {
		return nuNotaDest;
	}

	public void setNuNotaDest(BigDecimal nuNotaDest) {
		this.nuNotaDest = nuNotaDest;
	}

	public final BigDecimal getCodTipOperDestino() {
		return codTipOperDestino;
	}

	public final void setCodTipOperDestino(BigDecimal codTipOperDestino) {
		this.codTipOperDestino = codTipOperDestino;
	}

	public final Timestamp getDhAgendamento() {
		return dhAgendamento;
	}

	public final void setDhAgendamento(Timestamp dhAgendamento) {
		this.dhAgendamento = dhAgendamento;
	}

	public final String getObservacaoPedido() {
		return observacaoPedido;
	}

	public final void setObservacaoPedido(String observacaoPedido) {
		this.observacaoPedido = observacaoPedido;
	}

	public final String getObservacao() {
		return observacao;
	}

	public final void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public final AuthenticationInfo getAuthInfo() {
		return authInfo;
	}

	public final void setAuthInfo(AuthenticationInfo authInfo) {
		this.authInfo = authInfo;
	}

	public BigDecimal getCodEmp() {
		return codEmp;
	}

	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}

	public Timestamp getDtLibCom() {
		return dtLibCom;
	}

	public void setDtLibCom(Timestamp dtLibCom) {
		this.dtLibCom = dtLibCom;
	}

	public BigDecimal getCodLocalOrig() {
		return codLocalOrig;
	}

	public void setCodLocalOrig(BigDecimal codLocalOrig) {
		this.codLocalOrig = codLocalOrig;
	}

	@Override
	public String toString() {
		return String.format(
				"Params [nuNotaOrig=%s, nuNotaDest=%s, codTipOperDestino=%s, dhAgendamento=%s, dtLibCom=%s, observacaoPedido=%s, observacao=%s, authInfo=%s, codEmp=%s, codLocalOrig=%s]",
				nuNotaOrig, nuNotaDest, codTipOperDestino, dhAgendamento, dtLibCom, observacaoPedido, observacao,
				authInfo, codEmp, codLocalOrig);
	}

}
