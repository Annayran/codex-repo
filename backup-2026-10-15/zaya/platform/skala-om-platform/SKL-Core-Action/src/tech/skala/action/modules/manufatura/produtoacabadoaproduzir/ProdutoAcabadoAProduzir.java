package tech.skala.action.modules.manufatura.produtoacabadoaproduzir;

import java.math.BigDecimal;
import java.util.Objects;

import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

/**
 * A classe {@code ProdutoAcabadoAProduzir} representa a tabela {@code TPRIPA}.
 *  
 * <p>
 * A classe {@code ProdutoAcabado} representa a entidade <i>ProdutoAcabadoAProduzir</i> {@code TPRIPA} 
 * que sao os Produtos que serao produzidos na <b>Ordem de Producao</b>.
 * </p> 
 * 
 * @author hiagoalexandre
 * @author cassiomenezes
 * @since 2022-07-28
 *
 */
public class ProdutoAcabadoAProduzir implements EntityBean, LoadableByValueObject {

	private BigDecimal						codProdPA;
	private String							controlePA;
	private BigDecimal						qtdProduzir;
	private String							nroLote;
	private BigDecimal						nuLop;
	private BigDecimal						seqOP;
	private BigDecimal						idiProc;
	private BigDecimal						saldoOP;
	private BigDecimal						idProc;
	private BigDecimal						qtdCapacidadePad;
	private boolean							concluido;
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
		return concluido;
	}

	public void setConcluido(boolean concluido) {
		this.concluido = concluido;
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
	
	public BigDecimal getIdProc() {
		return idProc;
	}

	public void setIdProc(BigDecimal idProc) {
		this.idProc = idProc;
	}

	public BigDecimal getQtdCapacidadePad() {
		return qtdCapacidadePad;
	}

	public void setQtdCapacidadePad(BigDecimal qtdCapacidadePad) {
		this.qtdCapacidadePad = qtdCapacidadePad;
	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.codProdPA = vo.asBigDecimal(SklFieldName.CODPRODPA);
		this.controlePA = vo.asString(SklFieldName.CONTROLEPA);
		this.qtdProduzir = vo.asBigDecimal(SklFieldName.QTDPRODUZIR);
		this.nroLote = vo.asString(SklFieldName.NROLOTE);
		this.idiProc = vo.asBigDecimal(SklFieldName.IDIPROC);
		this.saldoOP = vo.asBigDecimal("SALDOOP");
		this.concluido = "S".equals(vo.asString(SklFieldName.CONCLUIDO));
		this.terceiro = false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codProdPA, nuLop, seqOP);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProdutoAcabadoAProduzir other = (ProdutoAcabadoAProduzir) obj;
		return Objects.equals(codProdPA, other.codProdPA) && Objects.equals(nuLop, other.nuLop)
				&& Objects.equals(seqOP, other.seqOP);
	}

	@Override
	public String toString() {
		return String.format(
				"ProdutoAcabadoAProduzir [codProdPA=%s, controlePA=%s, qtdProduzir=%s, nroLote=%s, nuLop=%s, seqOP=%s, idiProc=%s, saldoOP=%s, concluido=%s, terceiro=%s, codTarifaTerc=%s]",
				codProdPA, controlePA, qtdProduzir, nroLote, nuLop, seqOP, idiProc, saldoOP, concluido, terceiro,
				codTarifaTerc);
	}

}
