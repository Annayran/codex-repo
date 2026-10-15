package tech.skala.action.modules.manufatura.ordemproducaoold;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.cbmsoft.modelcore.CbmModelException;
import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class OrdemProducao implements EntityBean {

	private BigDecimal						codPlp;
	private BigDecimal						codWcp;
	private String							nroLote;
	private BigDecimal						idProc;
	private BigDecimal						nuLop;
	private BigDecimal						idiProc;
	private String							statusProc;
	private ProdutoAcabadoAProduzir			produtoAcabado;
	private List<InstanciaAtividade>		atividades = new LinkedList<>();
	private Collection<ApontamentoProducao>	apontamentos = new ArrayList<>();

	public BigDecimal getCodPlp() {
		return codPlp;
	}

	public void setCodPlp(BigDecimal codPlp) {
		this.codPlp = codPlp;
	}

	public BigDecimal getCodWcp() {
		return codWcp;
	}

	public void setCodWcp(BigDecimal codWcp) {
		this.codWcp = codWcp;
	}

	public String getNroLote() {
		return nroLote;
	}

	public void setNroLote(String nroLote) {
		this.nroLote = nroLote;
	}

	public BigDecimal getIdProc() {
		return idProc;
	}

	public void setIdProc(BigDecimal idProc) {
		this.idProc = idProc;
	}

	public BigDecimal getNuLop() {
		return nuLop;
	}

	public void setNuLop(BigDecimal nuLop) {
		this.nuLop = nuLop;
	}

	public BigDecimal getIdiProc() {
		return idiProc;
	}

	public void setIdiProc(BigDecimal idiProc) {
		this.idiProc = idiProc;
	}

	public String getStatusProc() {
		return statusProc;
	}

	public void setStatusProc(String statusProc) {
		this.statusProc = statusProc;
	}

	public ProdutoAcabadoAProduzir getProdutoAcabado() {
		return produtoAcabado;
	}

	public void setProdutoAcabado(ProdutoAcabadoAProduzir produtoAcabado) {
		this.produtoAcabado = produtoAcabado;
	}

	public List<InstanciaAtividade> getAtividades() {
		return atividades;
	}

	public void setAtividades(List<InstanciaAtividade> atividades) {
		this.atividades = atividades;
	}

	public Collection<ApontamentoProducao> getApontamentos() {
		return apontamentos;
	}

	public void setApontamentos(Collection<ApontamentoProducao> apontamentos) {
		this.apontamentos = apontamentos;
	}

	public void load() throws CbmModelException {
		String msg;

		JapeWrapper cabecalhoInstanciaProcessoDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_INSTANCIA_PROCESSO);
		try {
			
			DynamicVO vo = cabecalhoInstanciaProcessoDAO.findByPK(idiProc);
			setFromVO(vo);
			
		} catch (Exception e) {
			msg = String.format("[OP %s] Não foi possível carregar os dados da Ordem de Produção.%n", idiProc);
			msg = CbmModelException.buildErrorMessage(e, msg);
						
			throw new CbmModelException(msg,e);
		}
		
	}
	
	/**
	 * Busca todas as atividades de uma Ordem de Producao.
	 * <p>
	 * Pesquisa no banco de dados {@code TPRIATV} todas as instancias de atividades 
	 * de uma determinada Ordem de Producao e popula a lista de atividades.
	 * <p> 
	 * 
	 * @throws CbmModelException Excecao caso 
	 */
	public void loadAtividades() throws CbmModelException {
		String msg;
		JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.INSTANCIA_ATIVIDADE);
		InstanciaAtividade atividade;
		
		try {
			Collection<DynamicVO> vos = dao.find("this.IDIPROC = ? AND this.IDEXECWFLOW > 0", idiProc);
			for (DynamicVO vo : vos) {
				atividade = new InstanciaAtividade();
				atividade.setFromVO(vo);
				
				this.atividades.add(atividade);
			}
		} catch (Exception e) {
			msg = String.format("[OP %s] Não foi possível carregar as Atividades da Ordem de Produção.%n", idiProc);
			msg = CbmModelException.buildErrorMessage(e, msg);
						
			throw new CbmModelException(msg);
		}
	}
	
	public void loadApontamentos() throws CbmModelException {
		String msg;
		JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.CABECALHO_APONTAMENTO);
		ApontamentoProducao apontamento;
		
		try {
			
			for (InstanciaAtividade atividade : atividades) {
				Collection<DynamicVO> vos = dao.find("this.IDIATV = ?", atividade.getIdiAtv());
				for (DynamicVO vo : vos) {
					apontamento = new ApontamentoProducao();
					apontamento.setFromVO(vo);
					
					this.apontamentos.add(apontamento);
				}
			}
		} catch (Exception e) {
			msg = String.format("[OP %s] Não foi possível carregar os Apontamentos da Ordem de Produção.%n", idiProc);
			msg = CbmModelException.buildErrorMessage(e, msg);
						
			throw new CbmModelException(msg);
		}
	}
	
	public void loadApontamentos(BigDecimal idiAtv) throws SkalaModelException {
		
		try {
			JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.CABECALHO_APONTAMENTO);
			Collection<DynamicVO> vos = dao.find("this.IDIATV = ?", idiAtv);
			
			ApontamentoProducao apontamento;
			for (DynamicVO vo : vos) {
				apontamento = new ApontamentoProducao();
				apontamento.setFromVO(vo);
				
				this.apontamentos.add(apontamento);
			}	
		} catch (Exception e) {
			String msg = String.format("[IDIATV %s] Não foi possível localizar apontamentos da atividade", idiAtv);
			SkalaModelException.throwMe(msg, e);
		}
	}
	
	public void persist() throws Exception {
		throw new UnsupportedOperationException();
	}

	public void setFromVO(DynamicVO vo) throws Exception {
		this.codPlp = vo.asBigDecimal("CODPLP");
		this.nroLote = vo.asString("NROLOTE");
		this.idProc = vo.asBigDecimal("IDPROC");
		this.idiProc = vo.asBigDecimal("IDIPROC");
		this.statusProc = vo.asString("STATUSPROC");
		
		JapeWrapper produtoAcabadoAProduzirDAO = JapeFactory
				.dao(DynamicEntityNames.PRODUTO_ACABADO_A_PRODUZIR);
		DynamicVO produtoAcabadoAProduzirVO = produtoAcabadoAProduzirDAO.findOne("this.IDIPROC = ?", idiProc);
		this.produtoAcabado = new ProdutoAcabadoAProduzir();
		this.produtoAcabado.setFromVO(produtoAcabadoAProduzirVO);
		
		JapeWrapper workCenterPorInstanciaProcessoDAO = JapeFactory
				.dao(DynamicEntityNames.WORK_CENTER_INSTANCIA_PROCESSO);
		DynamicVO workCenterPorInstanciaProcessoVO = workCenterPorInstanciaProcessoDAO.findOne("this.IDIPROC = ?", idiProc);
		if (workCenterPorInstanciaProcessoVO != null)
			this.codWcp = workCenterPorInstanciaProcessoVO.asBigDecimal("CODWCP");
	}

	public void setFromRecord(Registro linha) {
		this.nroLote = (String) linha.getCampo("NROLOTE");
		this.idProc = BigDecimalUtil.getBigDecimal(linha.getCampo("IDPROC"));
		this.idiProc = BigDecimalUtil.getBigDecimal(linha.getCampo("IDIPROC"));
		this.statusProc = (String) linha.getCampo("STATUSPROC");
	}
	
	@Override
	public String toString() {
		return String.format(
				"OrdemProducao [codPlp=%s, codWcp=%s, nroLote=%s, idProc=%s, nuLop=%s, idiProc=%s, statusProc=%s, produtoAcabado=%s, atividades=%s, apontamentos=%s]",
				codPlp, codWcp, nroLote, idProc, nuLop, idiProc, statusProc, produtoAcabado, atividades, apontamentos);
	}

}
