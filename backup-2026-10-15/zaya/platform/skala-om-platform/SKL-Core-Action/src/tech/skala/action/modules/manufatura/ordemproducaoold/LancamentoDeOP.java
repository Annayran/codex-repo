package tech.skala.action.modules.manufatura.ordemproducaoold;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.beans.exceptions.EntityBeanExceptions.SetFromRecordNotPossibleException;
import tech.skala.core.beans.interfaces.EntityBean;

public class LancamentoDeOP implements EntityBean {

	private BigDecimal								nuLop;
	private String									descricao;
	private boolean									reutilizar;
	private boolean									usarSomenteProcessosPadrao;
	private boolean									terceiro;
	private BigDecimal								codPlP;
	private BigDecimal								codWcp;
	private BigDecimal								codParc;
	private BigDecimal								nuNotaOrig;
	private Collection<ProdutoAcabadoAProduzir>		produtosAProduzir = new ArrayList<>();
	private Collection<BigDecimal>					ordensDeProducaoLancadas = new ArrayList<>();
	private ArrayList<String>						erros = new ArrayList<>();

	public BigDecimal getNuLop() {
		return nuLop;
	}

	public void setNuLop(BigDecimal nuLop) {
		this.nuLop = nuLop;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isReutilizar() {
		return reutilizar;
	}

	public void setReutilizar(boolean reutilizar) {
		this.reutilizar = reutilizar;
	}

	public boolean isUsarSomenteProcessosPadrao() {
		return usarSomenteProcessosPadrao;
	}

	public void setUsarSomenteProcessosPadrao(boolean usarSomenteProcessosPadrao) {
		this.usarSomenteProcessosPadrao = usarSomenteProcessosPadrao;
	}

	public boolean isTerceiro() {
		return terceiro;
	}

	public void setTerceiro(boolean terceiro) {
		this.terceiro = terceiro;
	}

	public BigDecimal getCodPlP() {
		return codPlP;
	}

	public void setCodPlP(BigDecimal codPlP) {
		this.codPlP = codPlP;
	}

	public BigDecimal getCodWcp() {
		return codWcp;
	}

	public void setCodWcp(BigDecimal codWcp) {
		this.codWcp = codWcp;
	}

	public BigDecimal getCodParc() {
		return codParc;
	}

	public void setCodParc(BigDecimal codParc) {
		this.codParc = codParc;
	}

	public BigDecimal getNuNotaOrig() {
		return nuNotaOrig;
	}

	public void setNuNotaOrig(BigDecimal nuNotaOrig) {
		this.nuNotaOrig = nuNotaOrig;
	}

	public Collection<ProdutoAcabadoAProduzir> getProdutosAProduzir() {
		return produtosAProduzir;
	}

	public void setProdutosAProduzir(Collection<ProdutoAcabadoAProduzir> produtosAProduzir) {
		this.produtosAProduzir = produtosAProduzir;
	}

	public Collection<BigDecimal> getOrdensDeProducaoLancadas() {
		return ordensDeProducaoLancadas;
	}

	public void setOrdensDeProducaoLancadas(Collection<BigDecimal> ordensDeProducaoLancadas) {
		this.ordensDeProducaoLancadas = ordensDeProducaoLancadas;
	}

	public ArrayList<String> getErros() {
		return erros;
	}

	public void setErros(ArrayList<String> erros) {
		this.erros = erros;
	}

	public void setFromVO(DynamicVO arg0) throws Exception {
		throw new UnsupportedOperationException();
	}
	
	public void setFromRecord(Registro linha) throws SetFromRecordNotPossibleException {
		throw new UnsupportedOperationException();
	}

	public void persist() throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return String.format(
				"LancamentoDeOP [nuLop=%s, descricao=%s, reutilizar=%s, usarSomenteProcessosPadrao=%s, terceiro=%s, codPlP=%s, codWcp=%s, codParc=%s, nuNotaOrig=%s, produtosAProduzir=%s, ordensDeProducaoLancadas=%s, erros=%s]",
				nuLop, descricao, reutilizar, usarSomenteProcessosPadrao, terceiro, codPlP, codWcp, codParc, nuNotaOrig,
				produtosAProduzir, ordensDeProducaoLancadas, erros);
	}

}
