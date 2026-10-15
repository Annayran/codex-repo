package tech.skala.action.modules.inventario;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.util.FinderWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import tech.skala.core.commons.exceptions.SkalaModelException;


public class InventarioCabecalho {
	private BigDecimal						nuInventario;
	private String							descricao;
	private String							observacao;
	private String							tipo;
	private Timestamp						dtCriacao;
	private Timestamp						dtAbertura;
	private Timestamp						dtContagem;
	private BigDecimal						codEmp;
	private BigDecimal						codLocal;
	private List<InventarioItem>			itens = new LinkedList<>();
	public BigDecimal getNuInventario() {
		return nuInventario;
	}
	public void setNuInventario(BigDecimal nuInventario) {
		this.nuInventario = nuInventario;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Timestamp getDtCriacao() {
		return dtCriacao;
	}
	public void setDtCriacao(Timestamp dtCriacao) {
		this.dtCriacao = dtCriacao;
	}
	public Timestamp getDtAbertura() {
		return dtAbertura;
	}
	public void setDtAbertura(Timestamp dtAbertura) {
		this.dtAbertura = dtAbertura;
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
	public List<InventarioItem> getItens() {
		return itens;
	}
	public void setItens(List<InventarioItem> itens) {
		this.itens = itens;
	}
	
	public void setUp(Timestamp dtContagem, BigDecimal codEmp) throws SkalaModelException {
		String msg;
		SimpleDateFormat sdfDataPadrao = new SimpleDateFormat("dd/MM/yyyy");  
		
		if (nuInventario == null)
			this.nuInventario = BigDecimal.ONE;
		
		this.dtContagem = TimeUtils.clearTime(dtContagem);
		this.codEmp = codEmp;
		this.tipo = InventarioCabecalhoTipoEnum.GERAL.getValue();
		
		String where = "this.CODEMP = ? AND this.DTCONTAGEM = ?";
		String orderBy = "DTCONTAGEM, CODEMP, CODLOCAL, CODPROD, CONTROLE, CODVOL, CODPARC, TIPO, SEQUENCIA";
		FinderWrapper finder = new FinderWrapper("ContagemEstoque", where, new Object[]{codEmp, dtContagem});
		finder.setOrderBy(orderBy);
		finder.setMaxResults(-1);
		
		Collection<DynamicVO> contagensVO = null;
		try {
			contagensVO = JapeFactory.getEntityFacade().findByDynamicFinderAsVO(finder);
		} catch (Exception e) {
			msg = String.format("[EMP %03d DATA %s] Não foi possível localizar contagens.", codEmp.longValue(),
					sdfDataPadrao.format(dtContagem));
			
			SkalaModelException.throwMe(orderBy, e);
		}
		
		InventarioItem item = null;
		InventarioContagem contagem;
		
		for (DynamicVO contagemVO:contagensVO) {
			contagem = new InventarioContagem();
			try {
				contagem.setFromVO(contagemVO);
				
				item = new InventarioItem();
				item.setFromContagem(contagem);

				if (this.itens.contains(item)) {
					this.itens.get(this.getItens().indexOf(item)).addContagem(contagem);
					item.addContagem(contagem);

					this.itens.add(item);
				}
				
			} catch (Exception e) {
				msg = String.format("[EMP %03d DATA %s] Não foi possível inicializar a contagem.", codEmp.longValue(),
						sdfDataPadrao.format(dtContagem));
				msg = SkalaModelException.buildErrorMessage(msg, e);
				
				e.printStackTrace();
				SkalaModelException.throwMe(orderBy, e);
			}
		}
		
	}
	@Override
	public String toString() {
		String str;
		StringBuilder sbRetorno = new StringBuilder();
		SimpleDateFormat sdfDataPadrao = new SimpleDateFormat("dd/MM/yyyy");
		
		str = String.format("Inventário da Empresa %03d contagem em %s%n", codEmp.longValue(), sdfDataPadrao.format(dtContagem));
		sbRetorno.append(str);
		
		str = String.format("Total de itens: %05d", this.itens.size());
		sbRetorno.append(str);
		
		return sbRetorno.toString();
	}
	
	
}
