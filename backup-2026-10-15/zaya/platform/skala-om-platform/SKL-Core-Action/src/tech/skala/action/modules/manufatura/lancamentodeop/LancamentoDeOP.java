package tech.skala.action.modules.manufatura.lancamentodeop;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.mgeprod.model.lancamento.LancamentoOP;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.action.modules.manufatura.produtoacabadoaproduzir.ProdutoAcabadoAProduzir;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;

public class LancamentoDeOP implements EntityBean, Persistable {
	public static final String ENTITY_NAME = DynamicEntityNames.LANCAMENTO_DE_OP;

	private BigDecimal								nuLop;
	private String									descricao;
	private boolean									reutilizar;
	private boolean									usarSomenteProcessosPadrao;
	private boolean									terceiro;
	private BigDecimal								codPlP;
	private BigDecimal								codWcp;
	private BigDecimal								codParc;
	private BigDecimal								nuNotaOrig;
	private BigDecimal								idProc;
	private BigDecimal								qtdCapacidadePad;
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

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JdbcWrapper jdbc = null;
		
		LancamentoOP lancamentoOPBean = new LancamentoOP(jdbc);
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			
			lancamentoOPBean.setDescricao(descricao);
			lancamentoOPBean.setReutilizar(reutilizar);
			lancamentoOPBean.setUsarSomenteProcessoPadrao(usarSomenteProcessosPadrao);

			lancamentoOPBean.persistir();
			this.nuLop = lancamentoOPBean.getNulop();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new InsertNotPossibleException(e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
		}

		return null;
	}

	@Override
	public String toString() {
		return String.format(
				"LancamentoDeOP [nuLop=%s, descricao=%s, reutilizar=%s, usarSomenteProcessosPadrao=%s, terceiro=%s, codPlP=%s, codWcp=%s, codParc=%s, nuNotaOrig=%s, idProc=%s, produtosAProduzir=%s, ordensDeProducaoLancadas=%s, erros=%s]",
				nuLop, descricao, reutilizar, usarSomenteProcessosPadrao, terceiro, codPlP, codWcp, codParc, nuNotaOrig,
				idProc, produtosAProduzir, ordensDeProducaoLancadas, erros);
	}



}
