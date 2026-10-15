package tech.skala.action.modules.manufatura.ordemproducaoold;

import java.math.BigDecimal;
import java.sql.Timestamp;

import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.beans.interfaces.EntityBean;

public class InstanciaAtividade implements EntityBean {

	private BigDecimal						idiProc;
	private BigDecimal						idiAtv;
	private BigDecimal						idEfx;
	private BigDecimal						codWcp;
	private String							situacao;
	private Timestamp						dhInclusao;
	private Timestamp						dhAceite;
	private Timestamp						dhInicio;
	private Timestamp						dhFinal;
	
	public BigDecimal getIdiProc() {
		return idiProc;
	}

	public void setIdiProc(BigDecimal idiProc) {
		this.idiProc = idiProc;
	}

	public BigDecimal getIdiAtv() {
		return idiAtv;
	}

	public void setIdiAtv(BigDecimal idiAtv) {
		this.idiAtv = idiAtv;
	}

	public BigDecimal getIdEfx() {
		return idEfx;
	}

	public void setIdEfx(BigDecimal idEfx) {
		this.idEfx = idEfx;
	}

	public BigDecimal getCodWcp() {
		return codWcp;
	}

	public void setCodWcp(BigDecimal codWcp) {
		this.codWcp = codWcp;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Timestamp getDhInclusao() {
		return dhInclusao;
	}

	public void setDhInclusao(Timestamp dhInclusao) {
		this.dhInclusao = dhInclusao;
	}

	public Timestamp getDhAceite() {
		return dhAceite;
	}

	public void setDhAceite(Timestamp dhAceite) {
		this.dhAceite = dhAceite;
	}

	public Timestamp getDhInicio() {
		return dhInicio;
	}

	public void setDhInicio(Timestamp dhInicio) {
		this.dhInicio = dhInicio;
	}

	public Timestamp getDhFinal() {
		return dhFinal;
	}

	public void setDhFinal(Timestamp dhFinal) {
		this.dhFinal = dhFinal;
	}

	public void setFromVO(DynamicVO vo) throws Exception {
		this.idiProc = vo.asBigDecimal("IDIPROC");
		this.idiAtv = vo.asBigDecimal("IDIATV");
		this.idEfx = vo.asBigDecimal("IDEFX");
		this.codWcp = vo.asBigDecimal("CODWCP");
		this.situacao = vo.asString("SITUACAO");
		this.dhInclusao = vo.asTimestamp("DHINCLUSAO");
		this.dhAceite = vo.asTimestamp("DHACEITE");
		this.dhInicio = vo.asTimestamp("DHINICIO");
		this.dhFinal = vo.asTimestamp("DHFINAL");
	}

	@Override
	public String toString() {
		return String.format(
				"InstanciaAtividade [idiProc=%s, idiAtv=%s, idEfx=%s, codWcp=%s, situacao=%s, dhInclusao=%s, dhAceite=%s, dhInicio=%s, dhFinal=%s]",
				idiProc, idiAtv, idEfx, codWcp, situacao, dhInclusao, dhAceite, dhInicio, dhFinal);
	}

}
