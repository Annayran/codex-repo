package tech.skala.action.beans.demanda;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.TimeUtils;
import com.sankhya.util.TimeUtils.TipoPeriodo;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.action.beans.produto.ProdutoComercial;
import tech.skala.action.modules.comercial.estoque.TipoEstoqueEnum;
import tech.skala.action.modules.comercial.meta.ComercialUtilities;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class DemandaItem implements EntityBean, Persistable {
	private BigDecimal						nuDemanda = BigDecimal.ZERO;
	private BigDecimal						semana = BigDecimal.ZERO;
	private BigDecimal						codEmp;
	private BigDecimal						codLocal;
	private String							controle;
	private BigDecimal						codParc;
	private BigDecimal						codFam;
	private BigDecimal						codProd;
	private String							tipo;
	private ProdutoComercial				produto;
	private BigDecimal						estoque = BigDecimal.ZERO;
	private BigDecimal						wmsBloqueado = BigDecimal.ZERO;
	private BigDecimal						reservado = BigDecimal.ZERO;
	private BigDecimal						disponivel = BigDecimal.ZERO;
	private BigDecimal						estAFaturar = BigDecimal.ZERO;
	private BigDecimal						estPreReserva = BigDecimal.ZERO;
	private BigDecimal						qtdPedido = BigDecimal.ZERO;
	private BigDecimal						qtdPedidoExp = BigDecimal.ZERO;
	private BigDecimal						qtdPendente = BigDecimal.ZERO;
	private BigDecimal 						vlrPendente = BigDecimal.ZERO;
	private BigDecimal 						vlrUnit = BigDecimal.ZERO;
	private BigDecimal 						vlrTot = BigDecimal.ZERO;
	private BigDecimal 						vlrDesc = BigDecimal.ZERO;
	private BigDecimal 						vlrLiq = BigDecimal.ZERO;
	private BigDecimal						qtdDemanda = BigDecimal.ZERO;
	private String							comEx = "N";
	private BigDecimal						qtdNeg = BigDecimal.ZERO;
	private BigDecimal						qtdCarteiraInicial;
	private BigDecimal						qtdMediaProd;
	private List<DemandaItemOrigem>			itensOrigem = new LinkedList<>();

	public BigDecimal getNuDemanda() {
		return nuDemanda;
	}

	public void setNuDemanda(BigDecimal nuDemanda) {
		this.nuDemanda = nuDemanda;
	}

	public BigDecimal getSemana() {
		return semana;
	}

	public void setSemana(BigDecimal semana) {
		this.semana = semana;
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

	public String getControle() {
		return controle;
	}

	public void setControle(String controle) {
		this.controle = controle;
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

	public ProdutoComercial getProduto() {
		return produto;
	}

	public void setProduto(ProdutoComercial produto) {
		this.produto = produto;
	}

	public BigDecimal getEstoque() {
		return estoque;
	}

	public void setEstoque(BigDecimal estoque) {
		this.estoque = estoque;
	}

	public BigDecimal getWmsBloqueado() {
		return wmsBloqueado;
	}

	public void setWmsBloqueado(BigDecimal wmsBloqueado) {
		this.wmsBloqueado = wmsBloqueado;
	}

	public BigDecimal getReservado() {
		return reservado;
	}

	public void setReservado(BigDecimal reservado) {
		this.reservado = reservado;
	}

	public BigDecimal getDisponivel() {
		return disponivel;
	}

	public void setDisponivel(BigDecimal disponivel) {
		this.disponivel = disponivel;
	}

	public BigDecimal getEstAFaturar() {
		return estAFaturar;
	}

	public void setEstAFaturar(BigDecimal estAFaturar) {
		this.estAFaturar = estAFaturar;
	}

	public BigDecimal getEstPreReserva() {
		return estPreReserva;
	}

	public void setEstPreReserva(BigDecimal estPreReserva) {
		this.estPreReserva = estPreReserva;
	}

	public BigDecimal getQtdPedido() {
		return qtdPedido;
	}

	public void setQtdPedido(BigDecimal qtdPedido) {
		this.qtdPedido = qtdPedido;
	}

	public BigDecimal getQtdPedidoExp() {
		return qtdPedidoExp;
	}

	public void setQtdPedidoExp(BigDecimal qtdPedidoExp) {
		this.qtdPedidoExp = qtdPedidoExp;
	}

	public BigDecimal getQtdPendente() {
		return qtdPendente;
	}

	public void setQtdPendente(BigDecimal qtdPendente) {
		this.qtdPendente = qtdPendente;
	}

	public BigDecimal getVlrPendente() {
		return vlrPendente;
	}

	public void setVlrPendente(BigDecimal vlrPendente) {
		this.vlrPendente = vlrPendente;
	}

	public BigDecimal getVlrUnit() {
		return vlrUnit;
	}

	public void setVlrUnit(BigDecimal vlrUnit) {
		this.vlrUnit = vlrUnit;
	}

	public BigDecimal getVlrTot() {
		return vlrTot;
	}

	public void setVlrTot(BigDecimal vlrTot) {
		this.vlrTot = vlrTot;
	}

	public BigDecimal getVlrDesc() {
		return vlrDesc;
	}

	public void setVlrDesc(BigDecimal vlrDesc) {
		this.vlrDesc = vlrDesc;
	}

	public BigDecimal getVlrLiq() {
		return vlrLiq;
	}

	public void setVlrLiq(BigDecimal vlrLiq) {
		this.vlrLiq = vlrLiq;
	}

	public BigDecimal getQtdDemanda() {
		return qtdDemanda;
	}

	public void setQtdDemanda(BigDecimal qtdDemanda) {
		this.qtdDemanda = qtdDemanda;
	}

	public String getComEx() {
		return comEx;
	}

	public void setComEx(String comEx) {
		this.comEx = comEx;
	}

	public BigDecimal getQtdNeg() {
		return qtdNeg;
	}

	public void setQtdNeg(BigDecimal qtdNeg) {
		this.qtdNeg = qtdNeg;
	}

	public BigDecimal getQtdCarteiraInicial() {
		return qtdCarteiraInicial;
	}
	
	public void setQtdCarteiraInicial(BigDecimal qtdCarteiraInicial) {
		this.qtdCarteiraInicial = qtdCarteiraInicial;
	}

	public BigDecimal getQtdMediaProd() {
		return qtdMediaProd;
	}

	public void setQtdMediaProd(BigDecimal qtdMediaProd) {
		this.qtdMediaProd = qtdMediaProd;
	}

	public List<DemandaItemOrigem> getItensOrigem() {
		return itensOrigem;
	}

	public void setItensOrigem(List<DemandaItemOrigem> itensOrigem) {
		this.itensOrigem = itensOrigem;
	}
	
	public BigDecimal getCodFam() {
		return codFam;
	}

	public void setCodFam(BigDecimal codFam) {
		this.codFam = codFam;
	}

	public BigDecimal getCodProd() {
		return codProd;
	}

	public void setCodProd(BigDecimal codProd) {
		this.codProd = codProd;
	}

	
	public void addItemOrigem (DemandaItemOrigem itemOrigem) throws SkalaModelException {
		if (itensOrigem.isEmpty()) {
			this.codEmp = itemOrigem.getCodEmp();
			this.codLocal = itemOrigem.getCodLocalOrig();
			this.controle = itemOrigem.getControle();
			this.codParc = BigDecimal.ZERO;
			this.tipo = itemOrigem.getTipo();
			this.produto = itemOrigem.getProduto();
			
			loadEstoque();
			Timestamp dtRef = TimeUtils.getInicioPeriodo(TimeUtils.getNow(), TipoPeriodo.MES);
			this.qtdCarteiraInicial = ComercialUtilities.getCarteiraInicial(codEmp, produto.getCodProd(), dtRef);
			this.qtdMediaProd = ComercialUtilities.getQtdMediaProd(codEmp, produto.getCodProd(), dtRef);
			this.qtdDemanda = disponivel; 
			
			if ("S".equals(itemOrigem.getComEx())) {
				this.qtdPedidoExp = this.qtdPedidoExp.add(itemOrigem.getQtdPendente());			
			}else {
				this.qtdPedido = this.qtdPedido.add(itemOrigem.getQtdPendente());
			}
			this.qtdPendente = this.qtdPendente.add(itemOrigem.getQtdPendente());
			this.vlrPendente = this.vlrPendente.add(BigDecimalUtil.getValueOrZero(itemOrigem.getVlrPendente()));
			
			this.vlrTot = this.vlrTot.add(BigDecimalUtil.getValueOrZero(itemOrigem.getVlrTot()));
			this.vlrDesc = this.vlrDesc.add(BigDecimalUtil.getValueOrZero(itemOrigem.getVlrDesc()));
			this.vlrLiq = vlrTot.subtract(vlrDesc);
			this.qtdNeg = this.qtdNeg.add(BigDecimalUtil.getValueOrZero(itemOrigem.getQtdNeg()));
			this.vlrUnit = vlrTot.divide(qtdNeg, BigDecimalUtil.MATH_CTX);

			this.qtdDemanda = this.qtdDemanda.subtract(qtdPendente);
			
			if("S".equals(itemOrigem.getComEx()))
				this.comEx = "S";
			
			this.itensOrigem.add(itemOrigem);
			

		}
	}
	
	private void loadEstoque() throws SkalaModelException {
		this.estoque = ComercialUtilities.getEstoqueDisponivel(codEmp, produto.getCodProd(), codLocal, controle, BigDecimal.ZERO, TipoEstoqueEnum.PROPRIO.getValue(), "E", "S");
		this.wmsBloqueado = ComercialUtilities.getEstoqueDisponivel(codEmp, produto.getCodProd(), codLocal, controle, BigDecimal.ZERO, TipoEstoqueEnum.PROPRIO.getValue(), "W", "S");
		this.reservado = ComercialUtilities.getEstoqueDisponivel(codEmp, produto.getCodProd(), codLocal, controle, BigDecimal.ZERO, TipoEstoqueEnum.PROPRIO.getValue(), "R", "S");
		this.disponivel = ComercialUtilities.getEstoqueDisponivel(codEmp, produto.getCodProd(), codLocal, controle, BigDecimal.ZERO, TipoEstoqueEnum.PROPRIO.getValue(), "D", "S");
	}
	@Override
	public int hashCode() {
		return Objects.hash(nuDemanda, produto, semana);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof DemandaItem))
			return false;
		DemandaItem other = (DemandaItem) obj;
		return Objects.equals(nuDemanda, other.nuDemanda) && Objects.equals(produto, other.produto)
				&& Objects.equals(semana, other.semana);
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper itemDemandaDAO = JapeFactory.dao("AD_PLTGFDMITE");
		
		try  {
			DynamicVO vo = itemDemandaDAO.findOne("this.NUDEMANDA = ? AND this.SEMANA = ? AND this.CODPROD = ?",
					nuDemanda, semana, produto.getCodProd());
			if (vo == null) {
				EntityInsert insert = new EntityInsert("D_PLTGFDMITE");
				insert.setField("NUDEMANDA", nuDemanda);
				insert.setField("SEMANA", semana);
				insert.setField("CODEMP", codEmp);
				insert.setField("CODLOCAL", codLocal);
				insert.setField("CONTROLE", controle);
				insert.setField("CODPARC", codParc);
				insert.setField("TIPO", tipo);
				insert.setField("CODPROD", produto.getCodProd());
				insert.setField("MARCA", produto.getMarca());
				insert.setField("ESTOQUE", estoque);
				insert.setField("WMSBLOQUEADO", wmsBloqueado);
				insert.setField("RESERVADO", reservado);
				insert.setField("DISPONIVEL", disponivel);
				insert.setField("ESTAFATURAR", estAFaturar);
				insert.setField("ESTPRERESERVA", estPreReserva);
				insert.setField("QTDPEDIDO", qtdPedido);
				insert.setField("QTDPEDIDOEXP", qtdPedidoExp);
				insert.setField("QTDPENDENTE", qtdPendente);
				insert.setField("VLRPENDENTE", vlrPendente);
				insert.setField("VLRUNIT", vlrUnit);
				insert.setField("VLRTOT", vlrTot);
				insert.setField("VLRDESC", vlrDesc);
				insert.setField("VLRLIQ", vlrLiq);
				insert.setField("QTDDEMANDA", qtdDemanda);
				insert.setField("COMEX", comEx);
				insert.setField("CODFAM", produto.getFamilia().getCodFam());
				insert.setField("CODGPROD", produto.getCodGProd());
				insert.setField("CODGRUPOPROD", produto.getCodGrupoProd());
				insert.fluidInsert();

			}
			else {
				EntityUpdate update = new EntityUpdate("AD_PLTGRADAR", vo);
				update.setField("CODEMP", codEmp);
				update.setField("CODLOCAL", codLocal);
				update.setField("CONTROLE", controle);
				update.setField("CODPARC", codParc);
				update.setField("TIPO", tipo);
				update.setField("MARCA", produto.getMarca());
				update.setField("ESTOQUE", estoque);
				update.setField("WMSBLOQUEADO", wmsBloqueado);
				update.setField("RESERVADO", reservado);
				update.setField("DISPONIVEL", disponivel);
				update.setField("ESTAFATURAR", estAFaturar);
				update.setField("ESTPRERESERVA", estPreReserva);
				update.setField("QTDPEDIDO", qtdPedido);
				update.setField("QTDPEDIDOEXP", qtdPedidoExp);
				update.setField("QTDPENDENTE", qtdPendente);
				update.setField("VLRPENDENTE", vlrPendente);
				update.setField("VLRUNIT", vlrUnit);
				update.setField("VLRTOT", vlrTot);
				update.setField("VLRDESC", vlrDesc);
				update.setField("VLRLIQ", vlrLiq);
				update.setField("QTDDEMANDA", qtdDemanda);
				update.setField("COMEX", comEx);
				update.setField("CODFAM", produto.getFamilia().getCodFam());
				update.setField("CODGPROD", produto.getCodGProd());
				update.setField("CODGRUPOPROD", produto.getCodGrupoProd());
				update.fluidUpdate();

			}
			
		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}
		
		
		return null;
	}

	@Override
	public String toString() {
		return String.format(
				"DemandaItem [nuDemanda=%s, semana=%s, codEmp=%s, codLocal=%s, controle=%s, codParc=%s, tipo=%s, produto=%s, estoque=%s, wmsBloqueado=%s, reservado=%s, disponivel=%s, estAFaturar=%s, estPreReserva=%s, qtdPedido=%s, qtdPedidoExp=%s, qtdPendente=%s, vlrPendente=%s, vlrUnit=%s, vlrTot=%s, vlrDesc=%s, vlrLiq=%s, qtdDemanda=%s, comEx=%s, qtdNeg=%s, qtdCarteiraInicial=%s, qtdMediaProd=%s, itensOrigem=%s]",
				nuDemanda, semana, codEmp, codLocal, controle, codParc, tipo, produto, estoque, wmsBloqueado, reservado,
				disponivel, estAFaturar, estPreReserva, qtdPedido, qtdPedidoExp, qtdPendente, vlrPendente, vlrUnit,
				vlrTot, vlrDesc, vlrLiq, qtdDemanda, comEx, qtdNeg, qtdCarteiraInicial, qtdMediaProd, itensOrigem);
	}

//	public void addItemOrigem(DemandaItemOrigem itemOrigem, JdbcWrapper jdbcHeir) {
//		// TODO Auto-generated method stub
//		
//	}



	
	
	

}
