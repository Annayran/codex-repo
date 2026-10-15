package tech.skala.action.modules.manufatura.ordemproducao;

import java.math.BigDecimal;
import java.util.List;

import tech.skala.action.modules.manufatura.planta.PlantaManufatura;
import tech.skala.action.modules.manufatura.processoprodutivo.ProcessoProdutivo;
import tech.skala.action.modules.manufatura.produtoacabadoaproduzir.ProdutoAcabadoAProduzir;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.modules.manufatura.atividade.InstanciaAtividade;
import tech.skala.modules.manufatura.workcenter.WorkCenter;

/**
 * A classe {@code OrdemProducao} representa a tabela {@code TPRIPROC}.
 *  
 * <p>
 * A classe {@code OrdemProducao} representa a entidade <i>CabecalhoInstanciaProcesso</i> {@code TPRIPROC} 
 * que representa um lançamento de Ordem de Producao utilizadas na producao.
 * </p> 
 * 
 * @author cassiomenezes
 * @since 2022-09-28
 *
 */
public class OrdemProducao implements EntityBean {

	private BigDecimal						idiProc;
	private ProcessoProdutivo				processoProdutivo;
	private PlantaManufatura				planta;
	private WorkCenter						workCenter;
	private String							nroLote;
	private String							statusProc;
	private ProdutoAcabadoAProduzir			produtoAcabadoAProduzir;
	private List<InstanciaAtividade>		atividades;
	
	public BigDecimal getIdiProc() {
		return idiProc;
	}
	public void setIdiProc(BigDecimal idiProc) {
		this.idiProc = idiProc;
	}
	public ProcessoProdutivo getProcessoProdutivo() {
		return processoProdutivo;
	}
	public void setProcessoProdutivo(ProcessoProdutivo processoProdutivo) {
		this.processoProdutivo = processoProdutivo;
	}
	public PlantaManufatura getPlanta() {
		return planta;
	}
	public void setPlanta(PlantaManufatura planta) {
		this.planta = planta;
	}
	public WorkCenter getWorkCenter() {
		return workCenter;
	}
	public void setWorkCenter(WorkCenter workCenter) {
		this.workCenter = workCenter;
	}
	public String getNroLote() {
		return nroLote;
	}
	public void setNroLote(String nroLote) {
		this.nroLote = nroLote;
	}
	public String getStatusProc() {
		return statusProc;
	}
	public void setStatusProc(String statusProc) {
		this.statusProc = statusProc;
	}
	public ProdutoAcabadoAProduzir getProdutoAcabadoAProduzir() {
		return produtoAcabadoAProduzir;
	}
	public void setProdutoAcabadoAProduzir(ProdutoAcabadoAProduzir produtoAcabadoAProduzir) {
		this.produtoAcabadoAProduzir = produtoAcabadoAProduzir;
	}
	public List<InstanciaAtividade> getAtividades() {
		return atividades;
	}
	public void setAtividades(List<InstanciaAtividade> atividades) {
		this.atividades = atividades;
	}
	
	@Override
	public String toString() {
		return String.format(
				"OrdemProducao [idiProc=%s, processoProdutivo=%s, planta=%s, workCenter=%s, nroLote=%s, statusProc=%s, produtoAcabadoAProduzir=%s, atividades=%s]",
				idiProc, processoProdutivo, planta, workCenter, nroLote, statusProc, produtoAcabadoAProduzir,
				atividades);
	}

}
