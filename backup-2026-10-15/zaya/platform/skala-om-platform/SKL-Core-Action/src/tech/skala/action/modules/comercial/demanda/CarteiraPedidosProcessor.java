package tech.skala.action.modules.comercial.demanda;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.JdbcUtils;
import com.sankhya.util.TimeUtils;
import com.sankhya.util.TimeUtils.TipoPeriodo;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.action.beans.demanda.DemandaCabecalho;
import tech.skala.action.beans.demanda.DemandaItem;
import tech.skala.action.beans.demanda.DemandaItemOrigem;
import tech.skala.action.beans.meta.MetaParams;
import tech.skala.action.beans.meta.MetaPorProduto;
import tech.skala.action.beans.produto.ProdutoComercial;
import tech.skala.action.beans.radarproduto.RadarFamilia;
import tech.skala.action.beans.radarproduto.RadarProduto;
import tech.skala.action.modules.centrais.CabecalhoNota;
import tech.skala.action.modules.centrais.ItemNota;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class CarteiraPedidosProcessor {
	private static final String				QUERY_CABECALHO_CARTEIRA = "CarteiraPedidosProcessor_queCabecalho.sql";
	private static final BigDecimal			CODEMPCOMERCIAL = new BigDecimal(2);
	//private static final BigDecimal			CODLOCALCOMERCIAL = new BigDecimal(200100);
	//private static final BigDecimal			CODGRUPOPRODVENDAINI = new BigDecimal(10000);
	//private static final BigDecimal			CODGRUPOPRODVENDAFIN = new BigDecimal(19899);	

	private JdbcWrapper						jdbcHeir;
	private List<CabecalhoNota>				carteiraCabecalhos = new LinkedList<>();
	private List<DemandaItem>				itensDemanda = new LinkedList<>();
	private List<RadarFamilia>				radarFamilias = new LinkedList<>();

	/**
	 * Método responsável por inicializar e popular Lista de {@code CabecalhoNota}.
	 * <p>
	 * Este método é popula a lista {@code carteiraCabecalhos}<br>
	 * com objetos da classe {@code CabecalhoNota} a partir de consultas realizadas
	 * no banco de dados.<br>
	 * 
	 */
	public void inicializarCarteira() throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;

		NativeSql queCabecalho = null;
		ResultSet rsCabecalho = null;
		CabecalhoNota carteiraCabecalho;
		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			queCabecalho = new NativeSql(jdbc);
			
			queCabecalho.loadSql(getClass(), QUERY_CABECALHO_CARTEIRA);

			try {
				rsCabecalho = queCabecalho.executeQuery();
				while (rsCabecalho.next()) {
					carteiraCabecalho = new CabecalhoNota();
					carteiraCabecalho.setFromCabecalhoNotaRS(rsCabecalho);
					
					carteiraCabecalhos.add(carteiraCabecalho);
				}
			} finally {
				JdbcUtils.closeResultSet(rsCabecalho);
			}

		} catch (Exception e) {
			String msgString = null;

			SkalaModelException.throwMe(msgString, e);
		} finally {
			NativeSql.releaseResources(queCabecalho);
			
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}
	}
	public List<CabecalhoNota> getCarteiraCabecalhos() {
		return carteiraCabecalhos;
	}

	public List<DemandaItem> getItensDemanda() {
		return itensDemanda;
	}

	public List<RadarFamilia> getRadarFamilias() {
		return radarFamilias;
	}

	public void setRadarFamilias(List<RadarFamilia> radarFamilias) {
		this.radarFamilias = radarFamilias;
	}

	/**
	 * Método responsável por popular Objetos das classes {@code DemandaItem} e
	 * {@code DemandaItemOrigem}
	 * <p>
	 * Este método popula os objetos criados das classes abaixo, a partir da lista
	 * do objeto {@code CabecalhoNota}, carregado préviamente.
	 * <ul>
	 * <li>{@code DemandaItem}</li>
	 * <li>{@code DemandaItemOrigem}</li>
	 * </ul>
	 * 
	 */
	public void buildItens() throws SkalaModelException {

		DemandaItem itemDemanda;
		DemandaItemOrigem itemOrigem;

		for (CabecalhoNota cabecalhoNota : getCarteiraCabecalhos()) {

			for (ItemNota item : cabecalhoNota.getItensNota()) {
				itemOrigem = new DemandaItemOrigem();
				itemOrigem.setFromNotaBean(cabecalhoNota, item);

				itemDemanda = new DemandaItem();
				itemDemanda.setProduto(item.getProduto());
				if (!itensDemanda.contains(itemDemanda)) {
					itemDemanda.addItemOrigem(itemOrigem);
					itensDemanda.add(itemDemanda);
				} else {
					itensDemanda.get(itensDemanda.indexOf(itemDemanda)).addItemOrigem(itemOrigem);
				}
			}
		}
	}

	/**
	 * Método responsável por popular Objetos das classes {@code RadarFamilia} e
	 * {@code RadarProduto}
	 * <p>
	 * Este método popula os objetos criados das classes abaixo, a partir da lista
	 * do objeto {@code DemandaItem}, carregado préviamente.
	 * <ul>
	 * <li>{@code RadarFamilia}</li>
	 * <li>{@code RadarProduto}</li>
	 * </ul>
	 * 
	 */
	public void buildRadar() throws SkalaModelException {
		try {
			buildRadarProdutos();
		} catch (Exception e) {
			String msg = "Não foi possível iniciar Radar.";
			SkalaModelException.throwMe(msg, e);
		}

	}

	private void buildRadarProdutos() throws SkalaModelException {
		try {
			RadarProduto radarProduto;
			RadarFamilia radarFamilia;
			
			Timestamp dtRefAtual = TimeUtils.getInicioPeriodo(TimeUtils.getNow(), TipoPeriodo.MES);
			Timestamp dtRefAnterior = TimeUtils.dataAdd(dtRefAtual, -1, Calendar.MONTH);
			
			Timestamp dtRef = (TimeUtils.isFirstDayOfMonth(TimeUtils.getNow().getTime())) ? dtRefAnterior : dtRefAtual;
			
			MetaPorProduto metaPorProduto;
			for (DemandaItem itemDemanda : itensDemanda) {
				radarProduto = new RadarProduto(dtRef, BigDecimal.ZERO, itemDemanda.getProduto());

				metaPorProduto = getMetaPorProduto(itemDemanda);
				
				if (metaPorProduto != null) {
					radarProduto.setFromDemandaItemBean(itemDemanda, metaPorProduto);

					radarFamilia = new RadarFamilia(CODEMPCOMERCIAL, dtRef, radarProduto.getCodFam());

					if (radarFamilias.contains(radarFamilia)) {
						radarFamilias.get(radarFamilias.indexOf(radarFamilia)).addRadarProduto(radarProduto);
					} else {
						radarFamilia.addRadarProduto(radarProduto);
						radarFamilias.add(radarFamilia);
					}
				}
			}

		} catch (Exception e) {
			String msg = "Não foi possível atualizar Radar de Produtos.";
			SkalaModelException.throwMe(msg, e);
		}

	}
	
	private MetaPorProduto getMetaPorProduto(DemandaItem itemDemanda) throws Exception {
		JapeWrapper famMetSkuDAO = JapeFactory.dao("AD_PLTGFFAMMETSKU");
		
		Timestamp dtRefAtual = TimeUtils.getInicioPeriodo(TimeUtils.getNow(), TipoPeriodo.MES);
		Timestamp dtRefAnterior = TimeUtils.dataAdd(dtRefAtual, -1, Calendar.MONTH);
		
		Timestamp dtRef = (TimeUtils.isFirstDayOfMonth(TimeUtils.getNow().getTime())) ? dtRefAnterior : dtRefAtual;

		DynamicVO metaVO;
		MetaPorProduto metaPorProduto = null;
		MetaParams metaParams = new MetaParams(false, false);

		metaVO = famMetSkuDAO.findByPK(itemDemanda.getCodFam(), CODEMPCOMERCIAL, dtRef, itemDemanda.getCodProd());
		if(metaVO != null) {
			metaPorProduto = new MetaPorProduto(jdbcHeir, itemDemanda.getCodFam(), CODEMPCOMERCIAL, dtRef,itemDemanda.getCodProd(), metaParams);
			metaPorProduto.loadByVO(metaVO);
			
		}

		return metaPorProduto;
	}

	/**
	 * Método responsável por gerar/atualizar uma demanda.
	 * <p>
	 * @param origem
	 * @throws PltException
	 */
	public void gerarDemanda(String origem) throws SkalaModelException {
		try {
			DemandaCabecalho cabecalho = new DemandaCabecalho();
			cabecalho.setOrigem(origem);
			cabecalho.persists();
			BigDecimal nuDemanda = cabecalho.getNuDemanda();
			geraItensDemanda(nuDemanda);
			
		} catch (Exception e) {
			String msg = "Não foi possível gerar a demanda.";
			SkalaModelException.throwMe(msg, e);
		}
	}

	private void geraItensDemanda(final BigDecimal nuDemanda) throws SkalaModelException {
		BigDecimal semana = BigDecimalUtil.getBigDecimal(TimeUtils.getWeekOfYear(TimeUtils.getNow()));
		try {

			for (DemandaItem itemDemanda : getItensDemanda()) {
				itemDemanda.setNuDemanda(nuDemanda);
				itemDemanda.setSemana(semana);
				itemDemanda.persists();

				for (DemandaItemOrigem itemOrigem : itemDemanda.getItensOrigem()) {
					itemOrigem.setNuDemanda(nuDemanda);
					itemOrigem.setSemana(semana);
					itemOrigem.persists();
				}
			}
		} catch (Exception e) {
			String msg = "Não foi possível gerar a demanda.";
			SkalaModelException.throwMe(msg, e);
		}
	}
	
	public void atualizaMeta() throws SkalaModelException {
		try {
			Timestamp dtRefAtual = TimeUtils.getInicioPeriodo(TimeUtils.getNow(), TipoPeriodo.MES);
			Timestamp dtRefAnterior = TimeUtils.dataAdd(dtRefAtual, -1, Calendar.MONTH);
			
			Timestamp dtRef = (TimeUtils.isFirstDayOfMonth(TimeUtils.getNow().getTime())) ? dtRefAnterior : dtRefAtual;
			
			JapeWrapper metaSkuDAO = JapeFactory.dao("AD_PLTGFFAMMETSKU");
			Collection<DynamicVO> metasSkuVO = metaSkuDAO.find("this.DTREF = ?", dtRef);

			FluidUpdateVO updVO;

			DemandaItem itemDemanda;
			ProdutoComercial produto;
			for (DynamicVO vo : metasSkuVO) {
				itemDemanda = new DemandaItem();
				
				produto = new ProdutoComercial(vo.asBigDecimal(SklFieldName.CODPROD));
				produto.loadByPk(SklFieldName.CODPROD);
				itemDemanda.setProduto(produto);
				
				if(itensDemanda.contains(itemDemanda)) {
					updVO = metaSkuDAO.prepareToUpdate(vo);

					updVO.set("QTDPENDATU", itensDemanda.get(itensDemanda.indexOf(itemDemanda)).getQtdPendente());
					updVO.set("VLRPENDATU", itensDemanda.get(itensDemanda.indexOf(itemDemanda)).getVlrPendente());
					updVO.update();
				}
				
			}

		} catch (Exception e) {
			String msg = "Não foi possível atualizar a Meta do produto.";
			SkalaModelException.throwMe(msg, e);
		}
	}
	
}
