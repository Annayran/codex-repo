package tech.skala.action.modules.comercial.vendas;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;
import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.EntityPrimaryKey;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.util.JapeSessionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.PrePersistEntityState;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.com.sankhya.modelcore.comercial.BarramentoRegra;
import br.com.sankhya.modelcore.comercial.CentralCabecalhoNota;
import br.com.sankhya.modelcore.comercial.CentralFinanceiro;
import br.com.sankhya.modelcore.comercial.ComercialUtils;
import br.com.sankhya.modelcore.comercial.centrais.CACHelper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.action.modules.centrais.CabecalhoNotaSkl;
import tech.skala.action.modules.centrais.TipoCarteira;
import tech.skala.action.modules.inventario.Params;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class ComercialHelper {
	private static final BigDecimal OCORRENCIA_AGENDAMENTO = new BigDecimal(10);
	private static final BigDecimal OCORRENCIA_REAGENDAMENTO = new BigDecimal(11);

	private final Logger logger = new LoggerFactory(this.getClass().getCanonicalName()).getLogger();
	private final JapeWrapper callOcoDAO = JapeFactory.dao("AD_TMSCALOCO");
	private final JapeWrapper callCenterDAO = JapeFactory.dao("AD_TMSCAL");
	private final JapeWrapper callNotaDAO = JapeFactory.dao("AD_TMSCALNOTA");
	private final JapeWrapper cabecalhoNotaDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);
	private final JapeWrapper itemNotaDAO = JapeFactory.dao(DynamicEntityNames.ITEM_NOTA);

	public void alteraTipoNegociacao(final BigDecimal nuNota, final BigDecimal codTipVenda) throws Exception {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.getCurrentSession().getTopMostHandle();
			hnd.setCanTimeout(true);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					alterarTipoNegociacao(nuNota, codTipVenda);
				}
			});

		} finally {
			hnd = null;
		}
	}

	public void alterarTipoNegociacao(BigDecimal nuNota, BigDecimal codTipVenda) throws Exception {

		CentralCabecalhoNota centralCabecalhoNota = new CentralCabecalhoNota();
		DynamicVO tipoNegociacaoVO = ComercialUtils.getTipoNegociacao(codTipVenda);

		JapeWrapper cabecalhoNotaDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);
		DynamicVO cabVO = cabecalhoNotaDAO.findByPK(nuNota);
		FluidUpdateVO updVO = cabecalhoNotaDAO.prepareToUpdate(cabVO);

		updVO.set("CODTIPVENDA", codTipVenda);

		Timestamp dhTipVenda = tipoNegociacaoVO.asTimestamp("DHALTER");
		updVO.set("DHTIPVENDA", dhTipVenda);
		updVO.update();

		centralCabecalhoNota.inicializaNota(nuNota);
		centralCabecalhoNota.alteraTipoNegociacao(cabVO, cabVO, false);

		CentralFinanceiro financeiro = new CentralFinanceiro();
		financeiro.inicializaNota(nuNota);
		financeiro.refazerFinanceiro();

		String msg = String.format("O Tipo de negociação da Nota %s foi alterado para %s", nuNota, codTipVenda);

		logger.info(msg);
	}

	public void alteraDataComercial(BigDecimal nuNota, Timestamp dtComercial) throws Exception {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.getCurrentSession().getTopMostHandle();
			hnd.setCanTimeout(true);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					DynamicVO vo = cabecalhoNotaDAO.findByPK(nuNota);

					CentralProcessor processor = new CentralProcessor();
					processor.alteraPedidoFinal(vo, dtComercial);
				}
			});

		} finally {
			hnd = null;
		}
	}

	public void marcaItemComSaldoComoPendente(BigDecimal nuNota) throws Exception {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.getCurrentSession().getTopMostHandle();
			hnd.setCanTimeout(true);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					DynamicVO cabVO = cabecalhoNotaDAO.findByPK(nuNota);
					Collection<DynamicVO> itensVO = itemNotaDAO.find("this.NUNOTA = ?", nuNota);

					CentralProcessor processor = new CentralProcessor();
					processor.marcarItemComSaldoComoPendente(cabVO, itensVO);
				}
			});

		} finally {
			hnd = null;
		}

	}

	public void setCampoReajuste(final BigDecimal nuNota, final String reajuste) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.getCurrentSession().getTopMostHandle();
			hnd.setCanTimeout(true);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					CentralProcessor processor = new CentralProcessor();
					processor.definirCampoReajuste(nuNota, reajuste);
				}
			});

		} catch (Exception e) {
			String msg = String.format("Não foi possível definir o Reajuste para a nota %s.", nuNota);

			throw new SkalaModelException(msg);
		} finally {
			hnd = null;
		}

	}

	public void setCampoProgramado(final BigDecimal nuNota, final String programado) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.getCurrentSession().getTopMostHandle();
			hnd.setCanTimeout(true);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					CentralProcessor processor = new CentralProcessor();
					processor.definirCampoProgramado(nuNota, programado);
				}
			});

		} catch (Exception e) {
			String msg = String.format("Não foi possível definir o campo Programado para a nota %s.", nuNota);

			throw new SkalaModelException(msg);
		} finally {
			hnd = null;
		}

	}

	public static DynamicVO criaCabecalhoLancamento(Params params) throws Exception {
		JapeWrapper cabecalhoNotaDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);
		EntityFacade dwFacade = EntityFacadeFactory.getDWFFacade();
		Timestamp dtNeg = TimeUtils.clearTime(TimeUtils.getNow());

		BigDecimal codTipOper = params.getCodTipOperDestino();

		DynamicVO modeloVO = cabecalhoNotaDAO.findByPK(params.getNuNotaOrig());
		DynamicVO newCabVO = modeloVO.buildClone();
		DynamicVO topVO = ComercialUtils.getTipoOperacao(codTipOper, dtNeg);

		newCabVO.setPrimaryKey(null);
		newCabVO.clearReferences();

		String[] camposParaAnular = { "NUNOTA", "DHTIPOPER", "STATUSNOTA", "DTNEG", "HRMOV", "DTMOV", "VOLUME",
				"QTDVOL", "CHAVENFE", "CODMAQ", "DANFE", "DTENTSAI", "DTFATUR", "DHPROTOC", "DHREGDPEC", "NULOTENFE",
				"NULOTENFSE", "NUMALEATORIO", "NUMCOTACAO", "NUMNFSE", "NUMPROTOC", "NUMREGDPEC", "NUNOTA",
				"NUNOTAPEDFRET", "TPEMISNFE", "INDPRESNFCE", "TPEMISNFSE", "STATUSNFE", "STATUSNFSE", "COMGER", "NUREM",
				"VLRDESCTOTITEM", "VLRNOTA", "PESO" };

		for (String nomeCampo : camposParaAnular) {
			newCabVO.setProperty(nomeCampo, null);
		}

		newCabVO.setProperty("DTNEG", dtNeg);
		newCabVO.setProperty("CODTIPOPER", params.getCodTipOperDestino());
		newCabVO.setProperty("TIPMOV", topVO.asString("TIPMOV"));
		newCabVO.setProperty("NUMNOTA", BigDecimal.ZERO);
		newCabVO.setProperty("TIPFRETE", "N");
		newCabVO.setProperty("CIF_FOB", "F");
		newCabVO.setProperty("ORDEMCARGA", BigDecimal.ZERO);

		if (params.getDhAgendamento() != null)
			newCabVO.setProperty("AD_DHAGENDAMENTO", params.getDhAgendamento());

		if (params.getObservacaoPedido() != null)
			newCabVO.setProperty("AD_OBSERPED", params.getObservacaoPedido());

		String observacaoModelo = modeloVO.asString(SklFieldName.OBSERVACAO);
		String observacaoParams = params.getObservacao();

		String observacao = null;
		if (observacaoModelo != null && observacaoParams != null) {
			observacao = String.format("%s | %s", observacaoModelo, observacaoParams);
		} else if (observacaoModelo != null) {
			observacao = String.format("%s", observacaoModelo);
		} else if (observacaoParams != null) {
			observacao = String.format("%s", observacaoParams);
		}
		if (observacao != null)
			newCabVO.setProperty(SklFieldName.OBSERVACAO, observacao);

		BarramentoRegra bRegrasCab;
		CACHelper cacHelper = new CACHelper();
		DynamicVO cabecalhoVO;

		PrePersistEntityState cabPreState = PrePersistEntityState.build(dwFacade, DynamicEntityNames.CABECALHO_NOTA,
				newCabVO);

		bRegrasCab = cacHelper.incluirAlterarCabecalho(params.getAuthInfo(), cabPreState);

		cabecalhoVO = bRegrasCab.getState().getNewVO();
		return cabecalhoVO;
	}

	public static BigDecimal calcularUnidadesPorPalete(BigDecimal codGProd) throws Exception {
		JapeWrapper grupoProducaoProdutoDAO = JapeFactory.dao("GrupoProducaoProduto");

		DynamicVO grupoProducaoProdutoVO = grupoProducaoProdutoDAO.findByPK(codGProd);
		BigDecimal lastro = BigDecimalUtil.getValueOrZero(grupoProducaoProdutoVO.asBigDecimal("AD_LASTRO"));
		BigDecimal camadas = BigDecimalUtil.getValueOrZero(grupoProducaoProdutoVO.asBigDecimal("AD_CAMADAS"));
		BigDecimal quantidadePorCaixa = BigDecimalUtil.getValueOrZero(grupoProducaoProdutoVO.asBigDecimal("AD_QTDCX"));

		return lastro.multiply(quantidadePorCaixa).multiply(camadas, BigDecimalUtil.MATH_CTX);
	}

	public void preencheDataAgendamento(BigDecimal nuNota, Timestamp dhAgendamento, String observacao)
			throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					preencherDataAgendamento(nuNota, dhAgendamento, observacao);

				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível atualizar a Data de Agendamento.", e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	private void preencherDataAgendamento(BigDecimal nuNota, Timestamp dhAgendamento, String observacao)
			throws SkalaModelException {

		try {
			EntityPrimaryKey pk = new EntityPrimaryKey(new Object[] { nuNota });
			EntityUpdate update = new EntityUpdate(DynamicEntityNames.CABECALHO_NOTA, pk);

			update.setField(SklFieldName.OBSERVACAO, observacao);
			update.setField("AD_DHAGENDAMENTO", dhAgendamento);
			update.fluidUpdate();

		} catch (UpdateNotPossibleException e) {
			String msg = String.format("[NUNOTA %s] não foi possível atualizar Data de Agendamento.", nuNota);
			SkalaModelException.throwMe(msg, e);
		}

	}

	public static void recalculaQuantidadeVolumes(BigDecimal nuNota) throws SkalaModelException {

		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {

					recalcularQuantidadeVolume(nuNota);
				}

			});

		} catch (Exception e) {
			String msg = String.format("[NUNOTA %s] não foi possível recalcular a Quantidade de Volumes", nuNota);
			SkalaModelException.throwMe(msg, e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	public static void recalcularQuantidadeVolume(BigDecimal nuNota) throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);

		try {
			DynamicVO cabecalhoNotaVO = dao.findByPK(nuNota);
			BigDecimal qtdVol = cabecalhoNotaVO.asBigDecimal(SklFieldName.QTDVOL);
			BigDecimal qtdVolCalc = NativeSql.getBigDecimal("NVL(SUM(QTDEVOLPADRAO(CODPROD, QTDNEG, CODVOL)),0)",
					"TGFITE", "NUNOTA = ?", new Object[] { nuNota });

			if (qtdVol != qtdVolCalc) {
				EntityUpdate entityUpdate = new EntityUpdate(DynamicEntityNames.CABECALHO_NOTA, cabecalhoNotaVO);
				entityUpdate.setField(SklFieldName.QTDVOL, qtdVolCalc);
				entityUpdate.fluidUpdate();
			}

		} catch (Exception e) {
			String msg = String.format("[NUNOTA %s não foi possível calcular quantidade de volumes.", nuNota);
			SkalaModelException.throwMe(msg, e);
		}

	}

	public static TipoCarteira getTipoCarteiraFromTOP(BigDecimal codTipOper) throws SkalaModelException {
		TipoCarteira tipoCarteira = null;

		try {
			JapeWrapper topCarteiraDAO = JapeFactory.dao("AD_TPLTTOPCART");
			DynamicVO topCarteiraVO = topCarteiraDAO.findByPK(codTipOper);

			if (topCarteiraVO != null) {
				tipoCarteira = new TipoCarteira();
				tipoCarteira.loadByVO(topCarteiraVO);
			}

			return tipoCarteira;

		} catch (Exception e) {
			String msg = String.format("[CODTIPOPER %s] Não foi possível localizar o Tipo de Carteira desta TOP.",
					codTipOper);
			throw new SkalaModelException(msg, e);
		}
	}

	public static CabecalhoNotaSkl getCabecalhoNotaFromNota(BigDecimal nuNota) throws SkalaModelException {
		CabecalhoNotaSkl cabecalhoNotaSkl = null;

		try {
			JapeWrapper cabecalhoNotaSklDAO = JapeFactory.dao("AD_PLTGFCAB");
			DynamicVO cabecalhoNotaSklVO = cabecalhoNotaSklDAO.findByPK(nuNota);

			cabecalhoNotaSkl = new CabecalhoNotaSkl();
			cabecalhoNotaSkl.loadByVO(cabecalhoNotaSklVO);

		} catch (Exception e) {

		}
		return cabecalhoNotaSkl;
	}

	public static void refazerFinanceiroWithTX(BigDecimal nuNota) throws SkalaModelException {

		SessionHandle hnd = null;

		JapeSessionContext.putProperty("br.com.sankhya.com.CentralCompraVenda", Boolean.TRUE);
		JapeSessionContext.putProperty("ItemNota.incluindo.alterando.pela.central", Boolean.TRUE);
		JapeSessionContext.putProperty("calcular.outros.impostos", "true");

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					CentralFinanceiro financeiro = new CentralFinanceiro();
					financeiro.inicializaNota(nuNota);
					financeiro.refazerFinanceiro();
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível refazer financeiro.", e);
		} finally {
			JapeSession.close(hnd);
            JapeSessionContext.putProperty("br.com.sankhya.com.CentralCompraVenda", Boolean.FALSE);
            JapeSessionContext.putProperty("ItemNota.incluindo.alterando.pela.central", Boolean.FALSE);
            JapeSessionContext.putProperty("calcular.outros.impostos", "false");
		}
	}

}
