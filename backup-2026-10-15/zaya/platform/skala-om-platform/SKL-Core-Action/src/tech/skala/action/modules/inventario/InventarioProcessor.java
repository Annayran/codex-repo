package tech.skala.action.modules.inventario;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.cbmsoft.modelcore.LoggerFactory;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.util.JapeSessionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.PrePersistEntityState;
import br.com.sankhya.modelcore.comercial.AtributosRegras;
import br.com.sankhya.modelcore.comercial.centrais.CACHelper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.sankhya.modelcore.util.ListenerParameters;
import tech.skala.action.modules.comercial.vendas.ComercialHelper;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class InventarioProcessor {
	
	private final Logger								logger = new LoggerFactory(this.getClass().getCanonicalName()).getLogger();

	private Timestamp									dtContagem;
	private BigDecimal									codEmp;
	private InventarioCabecalho							inventario;
	private final EntityFacade							dwFacade = EntityFacadeFactory.getDWFFacade();
	private static final int							MAX_ITENS_NO_LANCAMENTO = 990;	
	private static final int							QUANTIDADE_ITENS_A_INCLUIR = 5;	
	
	public InventarioProcessor(InventarioCabecalho inventario) throws SkalaModelException {
		if (inventario == null)
			throw new SkalaModelException("Inventário inválido!");

		this.inventario = inventario;
		this.dtContagem = inventario.getDtContagem();
		this.codEmp = inventario.getCodEmp();
	}
	public void processaAjusteEstoque(Params params, List<InventarioAjuste> itensAjuste) throws Exception {
		String msg;
		SimpleDateFormat sdfDataPadrao = new SimpleDateFormat("yyyy-MM-dd");  

		List<InventarioAjuste> itens = itensAjuste.stream().collect(Collectors.toList());
		List<InventarioAjuste> itensDaNota;
		
		try {
			JapeSessionContext.putProperty(ListenerParameters.CENTRAIS, Boolean.TRUE);
			JapeSessionContext.putProperty(AtributosRegras.INC_UPD_ITEM_CENTRAL, Boolean.TRUE);
			
			int totalItensAjuste = itensAjuste.size();
			int totalDeNotas = 0;
			int quantidadeItensRestantes = 0;

			if (totalItensAjuste > MAX_ITENS_NO_LANCAMENTO) {
				totalDeNotas = Math.floorDiv(totalItensAjuste, MAX_ITENS_NO_LANCAMENTO);
				quantidadeItensRestantes = totalItensAjuste % MAX_ITENS_NO_LANCAMENTO;
				
				int i = 0;
				while (i < totalDeNotas) {
					itensDaNota = itens.subList(0, MAX_ITENS_NO_LANCAMENTO);
					
					i++;
					msg = String.format("Ajustes do Inventario de %s: %03d de %03d",
							sdfDataPadrao.format(inventario.getDtContagem()), i, totalDeNotas+1);
					params.setObservacao(msg);
					logger.info(msg);
					
					geraLancamentoDeAjuste(params, itensDaNota);
				}
			} else {
				quantidadeItensRestantes = totalItensAjuste;
			}
			
			if (quantidadeItensRestantes > 0) {
				msg = String.format("Ajustes do Inventario de %s: %03d de %03d",
						sdfDataPadrao.format(inventario.getDtContagem()), totalDeNotas+1, totalDeNotas+1);
				params.setObservacao(msg);
				logger.info(msg);

				geraLancamentoDeAjuste(params, itens);
			}

		} finally {
			JapeSessionContext.removeProperty(ListenerParameters.CENTRAIS);
			JapeSessionContext.removeProperty(AtributosRegras.INC_UPD_ITEM_CENTRAL);
		}
		
	}
	private void geraLancamentoDeAjuste(Params params, List<InventarioAjuste> itensDaNota) throws SkalaModelException {
		
		incluiCabecalhoLancamento(params);
		incluiItensNota(params, itensDaNota);
		
	}

	private void incluiCabecalhoLancamento(Params params) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.getCurrentSession().getTopMostHandle();
			hnd.setCanTimeout(true);

			hnd.execWithTX(new JapeSession.TXBlock() {
				@Override
				public void doWithTx() throws Exception {
					incluirCabecalhoLancamento(params);
				}
			});
		} catch (Exception e) {
		SkalaModelException.throwMe(null, e);
		} finally {
			hnd = null;
		}
	}
	private void incluirCabecalhoLancamento(Params params) throws Exception {
		DynamicVO cabecalhoVO = ComercialHelper.criaCabecalhoLancamento(params);
		params.setNuNotaDest(cabecalhoVO.asBigDecimal(SklFieldName.NUNOTA));

		String msg = String.format("Gerando nota de ajuste NUNOTA %s...", params.getNuNotaDest());
		logger.info(msg);
	}
	
	private void incluiItensNota(Params params, List<InventarioAjuste> itensDaNota) throws SkalaModelException {
		List<InventarioAjuste> itensDoLaco;

		int totalItensDaNota = itensDaNota.size();
		int totalDeLacos = Math.floorDiv(totalItensDaNota, QUANTIDADE_ITENS_A_INCLUIR);
		int itensRestantes = totalItensDaNota % QUANTIDADE_ITENS_A_INCLUIR;

		int i=0;
		while (i < totalDeLacos) {
			itensDoLaco = itensDaNota.subList(0, QUANTIDADE_ITENS_A_INCLUIR);
			
			gerarItensNota(params, itensDoLaco);
			
			itensDoLaco.clear();
			i++;
		}
		
		if (itensRestantes > 0)
			gerarItensNota(params, itensDaNota);

	}

	private void gerarItensNota(final Params params, final List<InventarioAjuste> itensAjuste) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.getCurrentSession().getTopMostHandle();
			hnd.setCanTimeout(true);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {

					Collection<PrePersistEntityState> itensNota = new ArrayList<>();

					CACHelper cacHelper = new CACHelper();
					DynamicVO itemVO;
					BigDecimal nuNotaDest = params.getNuNotaDest();
					BigDecimal qtdNeg;

					for (InventarioAjuste ajuste : itensAjuste) {
						itemVO = (DynamicVO) dwFacade.getDefaultValueObjectInstance(DynamicEntityNames.ITEM_NOTA);

						itemVO.setProperty(SklFieldName.CODPROD, ajuste.getCodProd());
						itemVO.setProperty(SklFieldName.CONTROLE, ajuste.getControle());
						itemVO.setProperty(SklFieldName.CODVOL, ajuste.getCodVol());
						itemVO.setProperty("CODLOCALORIG", ajuste.getCodLocal());

						qtdNeg = ajuste.getQtdAjuste().abs();
						itemVO.setProperty(SklFieldName.QTDNEG, qtdNeg);

						itemVO.setProperty("PERCDESC", BigDecimal.ZERO);
						itemVO.setProperty("VLRDESC", BigDecimal.ZERO);

						PrePersistEntityState itePreState = PrePersistEntityState.build(dwFacade,
								DynamicEntityNames.ITEM_NOTA, itemVO);
						itensNota.add(itePreState);

					}
					cacHelper.incluirAlterarItem(nuNotaDest, params.getAuthInfo(), itensNota, true);
				}
			});

		} catch (Exception e) {
			SkalaModelException.throwMe(null, e);
		} finally {
			hnd = null;
		}

	}

	
}
