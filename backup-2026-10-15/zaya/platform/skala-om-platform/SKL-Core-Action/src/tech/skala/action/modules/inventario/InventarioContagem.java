package tech.skala.action.modules.inventario;

import java.math.BigDecimal;
import java.sql.Timestamp;

import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.beans.interfaces.EntityBean;

public class InventarioContagem implements EntityBean {
	private BigDecimal						nuInventario;
	private Timestamp						dtContagem;
	private BigDecimal						codEmp;
	private BigDecimal						codLocal;
	private BigDecimal						codProd;
	private String							controle;
	private String							codVol;
	private BigDecimal						codParc;
	private String							tipo;
	private BigDecimal						sequencia;
	private BigDecimal						qtdEst;
	private BigDecimal						qtdEstUnCad;
	
	public BigDecimal getNuInventario() {
		return nuInventario;
	}
	public void setNuInventario(BigDecimal nuInventario) {
		this.nuInventario = nuInventario;
	}
	public Timestamp getDtContagem() {
		return dtContagem;
	}
	public void setDtContagem(Timestamp dtContagem) {
		this.dtContagem = dtContagem;
	}
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
	public BigDecimal getCodProd() {
		return codProd;
	}
	public void setCodProd(BigDecimal codProd) {
		this.codProd = codProd;
	}
	public String getControle() {
		return controle;
	}
	public void setControle(String controle) {
		this.controle = controle;
	}
	public String getCodVol() {
		return codVol;
	}
	public void setCodVol(String codVol) {
		this.codVol = codVol;
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
	public BigDecimal getQtdEst() {
		return qtdEst;
	}
	public void setQtdEst(BigDecimal qtdEst) {
		this.qtdEst = qtdEst;
	}
	public BigDecimal getQtdEstUnCad() {
		return qtdEstUnCad;
	}
	public void setQtdEstUnCad(BigDecimal qtdEstUnCad) {
		this.qtdEstUnCad = qtdEstUnCad;
	}
	
	public void setFromVO(DynamicVO vo) throws Exception {
		this.nuInventario = BigDecimal.ONE;
		this.dtContagem = vo.asTimestamp("DTCONTAGEM");
		this.codEmp = vo.asBigDecimal("CODEMP");
		this.codLocal = vo.asBigDecimal("CODLOCAL");
		this.codProd = vo.asBigDecimal("CODPROD");
		this.controle = vo.asString("CONTROLE");
		this.codVol = vo.asString("CODVOL");
		this.codParc = vo.asBigDecimal("CODPARC");
		this.tipo = vo.asString("TIPO");
		this.sequencia = vo.asBigDecimal("SEQUENCIA");
		this.qtdEst = vo.asBigDecimal("QTDEST");
		this.qtdEstUnCad = vo.asBigDecimal("QTDESTUNCAD");
	}
	@Override
	public String toString() {
		return String.format(
				"InventarioContagem [nuInventario=%s, dtContagem=%s, codEmp=%s, codLocal=%s, codProd=%s, controle=%s, codVol=%s, codParc=%s, tipo=%s, sequencia=%s, qtdEst=%s, qtdEstUnCad=%s]",
				nuInventario, dtContagem, codEmp, codLocal, codProd, controle, codVol, codParc, tipo, sequencia, qtdEst,
				qtdEstUnCad);
	}
	
}
