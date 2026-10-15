package tech.skala.action.modules.manufatura.ordemproducao;

import static org.mockito.Mockito.never;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;
import com.sankhya.util.StringUtils;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.EntityPrimaryKey;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.action.modules.centrais.CabecalhoNotaCompra;
import tech.skala.action.modules.manufatura.lancamentodeop.LancamentoDeOP;
import tech.skala.action.modules.manufatura.lancamentodeop.LancamentoDeOPProcessor;
import tech.skala.action.modules.manufatura.processoprodutivo.ProcessoProdutivoFinder;
import tech.skala.action.modules.manufatura.produtoacabado.ProdutoAcabadoFinder;
import tech.skala.action.modules.manufatura.produtoacabadoaproduzir.ProdutoAcabadoAProduzir;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class GeraOrdemProducaoTerceiroProcessor {

	private BigDecimal nuNota;
	private BigDecimal codPrc;
	private CabecalhoNotaCompra cabecalhoNota;
	private LancamentoDeOP lancamentoDeOP;

	private final Logger logger = new LoggerFactory(this.getClass().getCanonicalName()).getLogger();

	public GeraOrdemProducaoTerceiroProcessor(BigDecimal nuNota, BigDecimal codPrc) {
		this.nuNota = nuNota;
		this.codPrc = codPrc;
	}

	public LancamentoDeOP geraOP3() throws SkalaModelException {

		try {
			validaOPsGeradas();

			getCabecalhoNota();
			validarNota();

			validarTarifaTerceiro();

			iniciarLancamentoProducao();
			incluirNovoLancamentoOP();

			processarLancamento();
		} catch (Exception e) {
			excluirLog();

			SkalaModelException.throwMe("Não foi possível gerar OP de Industrialização", e);
		}

		return lancamentoDeOP;
	}

	private void validaOPsGeradas() throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();

			Collection<DynamicVO> vos = null;
			JapeWrapper dao = JapeFactory.dao("AD_PLTPRNXOP");
			try {
				vos = dao.find("this.NUNOTA = ?", nuNota);

				if (!vos.isEmpty())
					throw new SkalaModelException("Ordem(ns) de Produção já foi(ram) gerada(s) para esta nota.");

			} catch (Exception e) {
				String msg = String.format("[NUNOTA %s] Não foi possível localizar as OP's geradas para esta nota.",
						nuNota);
				SkalaModelException.throwMe(msg, e);
			}

		} finally {
			JapeSession.close(hnd);
		}

	}

	private void getCabecalhoNota() throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();

			this.cabecalhoNota = new CabecalhoNotaCompra();
			cabecalhoNota.loadByPk(nuNota);
		} catch (Exception e) {
			String msg = String.format("[NUNOTA %s] Nota de Compra não foi localizada.%n", nuNota);
			msg += "Verificar se o <b>número único</b> do documento informado está correto.";

			SkalaModelException.throwMe(msg, e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	private void validarNota() throws SkalaModelException {
		String tipMov = cabecalhoNota.getTipMov();
		BigDecimal codTipOper = cabecalhoNota.getCodTipOper();

		List<BigDecimal> codTipOperIndustrializacao = Arrays.asList(BigDecimal.valueOf(2109), BigDecimal.valueOf(3403));
		
		if (!"C".equalsIgnoreCase(tipMov) || !codTipOperIndustrializacao.contains(codTipOper))
			throw new SkalaModelException("Documento informado não é Nota de Industrialização de Terceiro.");

	}

	/**
	 * 
	 * @deprecated 2023-09-18
	 * @throws SkalaModelException
	 */
	private void validaTarifaTerceiro() throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					validarTarifaTerceiro();
				}

			});

		} catch (Exception e) {
			throw new SkalaModelException("Não foi possível localizar Tarifa de Terceiros.", e);
		} finally {
			JapeSession.close(hnd);
		}
	}

	private void validarTarifaTerceiro() throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();

			BigDecimal codParc = cabecalhoNota.getCodParc();

			DynamicVO vo = null;
			BigDecimal codTarifaTerceiro = null;
			JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.TERCEIRO_PROCESSO_PRODUTIVO);

			ProdutoAcabadoFinder finder;
			BigDecimal idProc;
			for (DynamicVO itemVO : getItensNota()) {
				try {
					finder = new ProdutoAcabadoFinder(itemVO.asBigDecimal(SklFieldName.CODPROD), null);
					idProc = finder.findProcessoProdutivoTerceiro();

					if (idProc != null)
						vo = dao.findByPK(idProc, BigDecimal.ZERO, itemVO.asBigDecimal(SklFieldName.CODPROD), codParc);

					if (vo != null)
						codTarifaTerceiro = vo.asBigDecimal("AD_CODPROD");

					if (vo == null || codTarifaTerceiro == null) {
						String msg = String.format(
								"[CODPROD %s CODPARC %s] Não foi encontrado registro da tarifa de terceiro para este Produto.",
								itemVO.asBigDecimal(SklFieldName.CODPROD), codParc);
						throw new SkalaModelException(msg);
					}

				} catch (Exception e) {
					SkalaModelException.throwMe("Não foi possível localizar tarifa de terceiro.", e);
				}
			}
		} finally {
			JapeSession.close(hnd);
		}

	}

	private void iniciarLancamentoProducao() throws SkalaModelException {

		logger.info("1.1 - iniciarLancamentoProducao");

		String descricao = String.format("Industrialização ref. NUNOTA %08d", nuNota.longValue());

		this.lancamentoDeOP = new LancamentoDeOP();
		this.lancamentoDeOP.setDescricao(descricao);
		this.lancamentoDeOP.setReutilizar(false);
		this.lancamentoDeOP.setUsarSomenteProcessosPadrao(false);
		this.lancamentoDeOP.setTerceiro(true);
		this.lancamentoDeOP.setCodPlP(BigDecimal.valueOf(4));
		this.lancamentoDeOP.setNuNotaOrig(nuNota);
		this.lancamentoDeOP.setCodParc(cabecalhoNota.getCodParc());

		setProdutosAProduzir();
	}

	private void setProdutosAProduzir() throws SkalaModelException {

		logger.info("1.1.1 - setProdutosAProduzir");
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();

			ProdutoAcabadoAProduzir produtoAProduzir;

			ProdutoAcabadoFinder finder;
			for (DynamicVO itemNotaVO : getItensNota()) {

				if (nuNota.longValue() == BigDecimal.valueOf(2318280).longValue() && itemNotaVO
						.asBigDecimal(SklFieldName.CODPROD).longValue() == BigDecimal.valueOf(3521).longValue()) {
					continue;
				}

				String nroLote = StringUtils.getEmptyAsNull(itemNotaVO.asString(SklFieldName.CONTROLE));
				nroLote = (nroLote == null) ? nuNota.toString() : nroLote;

				produtoAProduzir = new ProdutoAcabadoAProduzir();
				produtoAProduzir.setCodProdPA(itemNotaVO.asBigDecimal(SklFieldName.CODPROD));
				produtoAProduzir.setControlePA(" ");
				produtoAProduzir.setQtdProduzir(itemNotaVO.asBigDecimal(SklFieldName.QTDNEG));
				produtoAProduzir.setNroLote(nroLote);
				produtoAProduzir.setNuLop(lancamentoDeOP.getNuLop());

				finder = new ProdutoAcabadoFinder(produtoAProduzir.getCodProdPA(), null);

				produtoAProduzir.setIdProc((codPrc == null) ? finder.findProcessoProdutivoTerceiro()
						: ProcessoProdutivoFinder.buscaProcessoProdutivoPorCodPrc(codPrc).getIdProc());

				// se codprc is null entao finder.findProcessoProdutivoTerceiro() else
				// ProcessoProdutivoFinder.buscaProcessoProdutivoPorCodPrc(CODPRC).getIdProc();
				// produtoAProduzir.setIdProc(finder.findProcessoProdutivoTerceiro());

				// senao pega codprc

				lancamentoDeOP.getProdutosAProduzir().add(produtoAProduzir);
			}
		} finally {
			JapeSession.close(hnd);
		}

	}

	private void incluirNovoLancamentoOP() throws SkalaModelException {
		logger.info("1.2 - incluirNovoLancamentoOP");
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					lancamentoDeOP.persists();

					EntityPrimaryKey pk = new EntityPrimaryKey(new Object[] { nuNota });
					EntityUpdate update = new EntityUpdate("AD_PLTPRLOGCABIND", pk);
					update.setField("NULOP", lancamentoDeOP.getNuLop());
					update.fluidUpdate();

				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Nao foi possivel incluir novo Lançamento de OP", e);
		} finally {
			JapeSession.close(hnd);
		}
	}

	private void processarLancamento() throws SkalaModelException {
		logger.info("1.3 - processarLancamento");

		LancamentoDeOPProcessor processor = new LancamentoDeOPProcessor(lancamentoDeOP);
		processor.insereProdutoOrdemProducao();

		for (ProdutoAcabadoAProduzir produtoAcabado : lancamentoDeOP.getProdutosAProduzir())
			processor.alteraNumeroLoteProdutos(produtoAcabado);

		processor.atualizaTerceiro();
		processor.lancaOrdensProducao(nuNota);
		processor.getTarifasTerceiro();

	}

	private Collection<DynamicVO> getItensNota() throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.ITEM_NOTA);
		Collection<DynamicVO> itensNotaVO = null;

		try {
			itensNotaVO = dao.find("this.NUNOTA = ?", nuNota);
		} catch (Exception e) {
			String msg = String.format("[NUNOTA %s] Não foi possível localizar os itens do lançamento.%n", nuNota);
			SkalaModelException.throwMe(msg, e);
		}

		return itensNotaVO;
	}

	public void validaLog() throws SkalaModelException {

		/*
		 * começou e nulop ta nulo, para o código - ta tentando gerar começou e nulop ta
		 * preenchido e existe no banco - ta processando começou, nulop ta preenchido e
		 * n existe na tprlop - deu erro de timeout ai deixa gerar
		 */

		SessionHandle hnd = null;

		DynamicVO logVO = null;
		DynamicVO nulopVO = null;
		JapeWrapper logDAO = JapeFactory.dao("AD_PLTPRLOGCABIND");
		JapeWrapper nulopDAO = JapeFactory.dao(DynamicEntityNames.LANCAMENTO_DE_OP);
		try {
			hnd = JapeSession.open();

			logVO = logDAO.findByPK(nuNota);

			if (logVO != null) {

				BigDecimal nuLop = logVO.asBigDecimal("NULOP");
				nulopVO = nulopDAO.findByPK(nuLop);

				if (nuLop == null || nulopVO == null)
					throw new SkalaModelException("Processo de geração de OPs já inicializada");
			}

		} catch (Exception e) {
			String msg = String.format("[NUNOTA %s] Não foi possível localizar as OP's geradas para esta nota.",
					nuNota);
			SkalaModelException.throwMe(msg, e);

		} finally {
			JapeSession.close(hnd);
		}

	}

	public void definirDataInicial() throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {

					DynamicVO vo = JapeFactory.dao("AD_PLTPRLOGCABIND").findByPK(nuNota);
					
					if(vo == null) {
						EntityInsert insert = new EntityInsert("AD_PLTPRLOGCABIND");
						insert.setField(SklFieldName.NUNOTA, nuNota);
						insert.setField("DHINI", TimeUtils.getNow());
						insert.fluidInsert();
					} else {
						EntityUpdate update = new EntityUpdate("AD_PLTPRLOGCABIND", vo);
						update.setField("DHINI", TimeUtils.getNow());
						update.fluidUpdate();
					}
					

				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível definir data inicial.", e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	public void definirDataFinal() throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {

					EntityPrimaryKey pk = new EntityPrimaryKey(new Object[] { nuNota });
					EntityUpdate update = new EntityUpdate("AD_PLTPRLOGCABIND", pk);
					update.setField("DHFIN", TimeUtils.getNow());
					update.fluidUpdate();

				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível definir data final.", e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	public void excluirLog() throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					JapeWrapper dao = JapeFactory.dao("AD_PLTPRLOGCABIND");
					dao.delete(nuNota);

				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível excluir log.", e);
		} finally {
			JapeSession.close(hnd);
		}
	}

	public void incluiErro(String message) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {

					DynamicVO vo = JapeFactory.dao("AD_PLTPRLOGCABIND").findByPK(nuNota);

					if (vo != null) {
						EntityUpdate update = new EntityUpdate("AD_PLTPRLOGCABIND", vo);
						update.setField("MSGERRO", message.toCharArray());
						update.fluidUpdate();
					}
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível registrar erro de operação", e);
		} finally {
			JapeSession.close(hnd);
		}

	}

}
