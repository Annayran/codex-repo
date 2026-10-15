package tech.skala.action.modules.manufatura.ordemproducaoold;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.beans.interfaces.EntityBean;

public class ProdutoAcabadoAProduzir implements EntityBean {

	private BigDecimal						codProdPA;
	private String							controlePA;
	private BigDecimal						qtdProduzir;
	private String							nroLote;
	private BigDecimal						nuLop;
	private BigDecimal						seqOP;
	private BigDecimal						idiProc;
	private BigDecimal						saldoOP;
	private boolean							isConcluido;
	private boolean							terceiro;
	private BigDecimal						codTarifaTerc;

	public BigDecimal getCodProdPA() {
		return codProdPA;
	}

	public void setCodProdPA(BigDecimal codProdPA) {
		this.codProdPA = codProdPA;
	}

	public String getControlePA() {
		return controlePA;
	}

	public void setControlePA(String controlePA) {
		this.controlePA = controlePA;
	}

	public BigDecimal getQtdProduzir() {
		return qtdProduzir;
	}

	public void setQtdProduzir(BigDecimal qtdProduzir) {
		this.qtdProduzir = qtdProduzir;
	}

	public String getNroLote() {
		return nroLote;
	}

	public void setNroLote(String nroLote) {
		this.nroLote = nroLote;
	}

	public BigDecimal getNuLop() {
		return nuLop;
	}

	public void setNuLop(BigDecimal nuLop) {
		this.nuLop = nuLop;
	}

	public BigDecimal getSeqOP() {
		return seqOP;
	}

	public void setSeqOP(BigDecimal seqOP) {
		this.seqOP = seqOP;
	}

	public BigDecimal getIdiProc() {
		return idiProc;
	}

	public void setIdiProc(BigDecimal idiProc) {
		this.idiProc = idiProc;
	}

	public BigDecimal getSaldoOP() {
		return saldoOP;
	}

	public void setSaldoOP(BigDecimal saldoOP) {
		this.saldoOP = saldoOP;
	}

	public boolean isConcluido() {
		return isConcluido;
	}

	public void setConcluido(boolean isConcluido) {
		this.isConcluido = isConcluido;
	}

	public boolean isTerceiro() {
		return terceiro;
	}

	public void setTerceiro(boolean terceiro) {
		this.terceiro = terceiro;
	}

	public BigDecimal getCodTarifaTerc() {
		return codTarifaTerc;
	}

	public void setCodTarifaTerc(BigDecimal codTarifaTerc) {
		this.codTarifaTerc = codTarifaTerc;
	}

	public void setFromVO(DynamicVO vo) throws Exception {
		this.codProdPA = vo.asBigDecimal("CODPRODPA");
		this.controlePA = vo.asString("CONTROLEPA");
		this.qtdProduzir = vo.asBigDecimal("QTDPRODUZIR");
		this.nroLote = vo.asString("NROLOTE");
		this.idiProc = vo.asBigDecimal("IDIPROC");
		this.saldoOP = vo.asBigDecimal("SALDOOP");
		this.isConcluido = "S".equals(vo.asString("CONCLUIDO")) ? true : false;
		this.terceiro = false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codProdPA == null) ? 0 : codProdPA.hashCode());
		result = prime * result + ((nuLop == null) ? 0 : nuLop.hashCode());
		result = prime * result + ((seqOP == null) ? 0 : seqOP.hashCode());
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		if (obj instanceof ProdutoAcabadoAProduzir) {
			ProdutoAcabadoAProduzir toCompare = (ProdutoAcabadoAProduzir) obj;
			if (!this.codProdPA.equals(toCompare.codProdPA))
				return false;
			if (!this.controlePA.equals(toCompare.controlePA))
				return false;
			if (!this.nroLote.equals(toCompare.nroLote))
				return false;
			if (!this.nuLop.equals(toCompare.nuLop))
				return false;
			if (!this.seqOP.equals(toCompare.seqOP))
				return false;
			
			return true;
		}
		
		return false;
	}

	@Override
	public String toString() {
		return String.format(
				"ProdutoAcabadoAProduzir [codProdPA=%s, controlePA=%s, qtdProduzir=%s, nroLote=%s, nuLop=%s, seqOP=%s, idiProc=%s, saldoOP=%s, isConcluido=%s, terceiro=%s, codTarifaTerc=%s]",
				codProdPA, controlePA, qtdProduzir, nroLote, nuLop, seqOP, idiProc, saldoOP, isConcluido, terceiro,
				codTarifaTerc);
	}

}
