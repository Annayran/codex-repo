package tech.skala.action.modules.manufatura.ordemproducao;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.cbmsoft.modelcore.CbmModelException;
import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.EntityPrimaryKey;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.action.modules.manufatura.lancamentodeop.LancamentoDeOP;
import tech.skala.action.modules.manufatura.ordemproducao.OrdemProducaoExceptions.LoteMaiorQueUltimoNumeroUltilizadoException;
import tech.skala.action.modules.manufatura.ordemproducao.OrdemProducaoExceptions.LoteMaximoJaUltilizadoException;
import tech.skala.action.modules.manufatura.ordemproducao.OrdemProducaoExceptions.LoteNaoAtualizadoException;
import tech.skala.action.modules.manufatura.ordemproducao.OrdemProducaoExceptions.StatusOrdemProducaoInvalidException;
import tech.skala.action.modules.manufatura.processoprodutivo.ProcessoProdutivoFinder;
import tech.skala.action.modules.manufatura.processoprodutivo.ProcessoProdutivoResumo;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class OrdemProducaoProcessor {
	
	private final int				year = TimeUtils.getYear(TimeUtils.getNow());
	private DynamicVO				cabecalhoNotaVO;
	
	public void setLote(String menorNroLote, BigDecimal codProdPA, BigDecimal codEmp) throws LoteNaoAtualizadoException {
		SessionHandle hnd = null;
		
		JapeWrapper controleNumeracaoDAO = JapeFactory.dao(DynamicEntityNames.CONTROLE_NUMERACAO);
		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {

					BigDecimal numeracaoDoLote = BigDecimal.ZERO;
					StringBuilder sb = new StringBuilder(menorNroLote);

					int indice = sb.indexOf("/");
					String prefixoLote = null;
					if (indice > 0) {
						
						String numeracaoStr = (sb.charAt(sb.length()-1) == 'K')
								? sb.substring(indice + 1, sb.length() - 1)
								: sb.substring(indice + 1, sb.length());
						numeracaoDoLote = BigDecimalUtil.getBigDecimal(numeracaoStr);
						prefixoLote = sb.substring(0, indice);
					}

					verificaLoteLancado(codProdPA, menorNroLote);

					String arquivo = String.format("TPR%s:%d", codProdPA, year);
					DynamicVO numVO = controleNumeracaoDAO.findOne(
							"this.ARQUIVO = ? AND this.CODEMP = ? AND this.SERIE = '.' AND this.CODMODDOC = 0", arquivo,
							codEmp);

					BigDecimal ultCod = numVO.asBigDecimal("ULTCOD");

					String nroLoteMax = buscarLoteMaximo(codProdPA, prefixoLote, menorNroLote);

					validaNumeracaoLote(menorNroLote, nroLoteMax, numeracaoDoLote, ultCod, codProdPA);

					EntityUpdate entityUpd = new EntityUpdate(DynamicEntityNames.CONTROLE_NUMERACAO, numVO);
					entityUpd.setField("ULTCOD", numeracaoDoLote.subtract(BigDecimal.ONE));
					entityUpd.fluidUpdate();
				}
			});

		} catch (Exception e) {
			throw new OrdemProducaoExceptions.LoteNaoAtualizadoException(e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	private void validaNumeracaoLote(String menorNroLote, String nroLoteMax, BigDecimal numeracaoDoLote,
			BigDecimal ultCod, BigDecimal codProdPA)
			throws LoteMaximoJaUltilizadoException, LoteMaiorQueUltimoNumeroUltilizadoException {

		if (menorNroLote.equals(nroLoteMax))
			throw new OrdemProducaoExceptions.LoteMaximoJaUltilizadoException(menorNroLote, nroLoteMax, codProdPA);

		if (numeracaoDoLote.intValue() > ultCod.intValue())
			throw new OrdemProducaoExceptions.LoteMaiorQueUltimoNumeroUltilizadoException(numeracaoDoLote, ultCod, codProdPA);

	}
	
	public String buscarLoteMaximo(BigDecimal codProdPA, String prefixoLote, String menorNroLote)
			throws RecordNotFoundException {
		JapeWrapper produtoAcabadoAProduzirDAO = JapeFactory.dao(DynamicEntityNames.PRODUTO_ACABADO_A_PRODUZIR);

		Collection<DynamicVO> loteMaximoVO;
		try {
			loteMaximoVO = produtoAcabadoAProduzirDAO.find(
					"this.CODPRODPA = ? AND this.NROLOTE = (select MAX(T.NROLOTE) from TPRIPA T where T.NROLOTE LIKE '?%' AND T.CODPRODPA = this.CODPRODPA)",
					codProdPA, prefixoLote);
		} catch (Exception e) {
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		}

		return (!loteMaximoVO.isEmpty()) ? loteMaximoVO.iterator().next().asString("NROLOTE") : menorNroLote;
	}
	
	private void verificaLoteLancado(BigDecimal codProdPA, String menorNroLote) throws RecordNotFoundException {

		JapeWrapper produtoAcabadoAProduzirDAO = JapeFactory.dao(DynamicEntityNames.PRODUTO_ACABADO_A_PRODUZIR);
		JapeWrapper cabecalhoInstanciaProcessoDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_INSTANCIA_PROCESSO);

		Collection<DynamicVO> produtosComLoteVO;
		try {
			produtosComLoteVO = produtoAcabadoAProduzirDAO.find("this.CODPRODPA = ? AND this.NROLOTE = ?", codProdPA,
					menorNroLote);

			if (!produtosComLoteVO.isEmpty()) {
				BigDecimal idiProcComLote = produtosComLoteVO.iterator().next().asBigDecimal(SklFieldName.IDIPROC);
				DynamicVO cabProcVO = cabecalhoInstanciaProcessoDAO.findByPK(idiProcComLote);

				if ("A".equalsIgnoreCase(cabProcVO.asString(SklFieldName.STATUSPROC)))
					throw new CbmModelException(
							String.format("Lote %s já existe para produto %s para OP %s <b>Em andamento</b>.",
									menorNroLote, codProdPA, idiProcComLote));
			}
		} catch (Exception e) {
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		}

	}

	public void validaStatusOP(String statusProc, BigDecimal idiProc) throws StatusOrdemProducaoInvalidException {
		List<String> statusList = Arrays.asList("C", "C2", "F", "S", "S2");
		
		if (statusList.contains(statusProc)) {
			throw new OrdemProducaoExceptions.StatusOrdemProducaoInvalidException(idiProc, statusProc);
		}
	}

	public void cancelaOP(BigDecimal idiProc) {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					JapeSession.putProperty("br.com.sankhya.mgeprod.libera.wc", true);
					cancelarOP(idiProc);
				}

			});

		} catch (Exception e) {
			
		} finally {
			JapeSession.close(hnd);
		}
	}
	
	private void cancelarOP(BigDecimal idiProc) throws UpdateNotPossibleException {
		EntityPrimaryKey pk = new EntityPrimaryKey(new Object[] { idiProc });
		EntityUpdate entityUpdate = new EntityUpdate(DynamicEntityNames.CABECALHO_INSTANCIA_PROCESSO, pk);
		entityUpdate.setField(SklFieldName.STATUSPROC, OrdemProducaoStatusEnum.CANCELADO.getValue());
		entityUpdate.fluidUpdate();
	}

	public LancamentoDeOP geraOPTerceiros(BigDecimal nuNota) throws SkalaModelException {
		
		getCabecalhoNota(nuNota);
		
		return null;
	}


	private Collection<DynamicVO> getItensNota(BigDecimal nuNota) throws SkalaModelException {

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

	private void getCabecalhoNota(BigDecimal nuNota) throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);

		try {
			this.cabecalhoNotaVO = dao.findByPK(nuNota);
			
			validaNota();
			
		} catch (Exception e) {
			String msg = "[NUNOTA %s] Nota de Compra não foi localizada.%nVerificar se o <b>número único</b> do documento informado está correto.";

			String msgFormat = String.format(msg, nuNota);
			SkalaModelException.throwMe(msgFormat, e);
		}
	}

	private void validaNota() throws SkalaModelException {
		String tipMov = cabecalhoNotaVO.asString(SklFieldName.TIPMOV);
		BigDecimal codTipOper = cabecalhoNotaVO.asBigDecimal(SklFieldName.CODTIPOPER);
		
		if (!"C".equalsIgnoreCase(tipMov) || codTipOper.longValue() != 2109L)
			throw new SkalaModelException("Documento informado não é Nota de Industrialização de Terceiro.");
			
	}

	public void validaTarifaTerceiro(BigDecimal nuNota, BigDecimal codEmp) throws SkalaModelException {
		
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					validarTarifaTerceiro(nuNota);
				}

			});

		} catch (Exception e) {
			throw new SkalaModelException("", e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	private void validarTarifaTerceiro(BigDecimal nuNota) throws SkalaModelException {

		getCabecalhoNota(nuNota);

		BigDecimal codParc = this.cabecalhoNotaVO.asBigDecimal(SklFieldName.CODPARC);

		DynamicVO vo = null;
		BigDecimal codTarifaTerceiro = null;
		JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.TERCEIRO_PROCESSO_PRODUTIVO);

		ProcessoProdutivoResumo processoProdutivo = ProcessoProdutivoFinder.buscaProcessoProdutivoPorCodPrc(BigDecimal.valueOf(160));

		for (DynamicVO itemVO : getItensNota(nuNota)) {
			try {
				vo = dao.findByPK(processoProdutivo.getIdProc(), BigDecimal.ZERO,
						itemVO.asBigDecimal(SklFieldName.CODPROD), codParc);

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
	}
	
	public static void finalizaOrdemProducao(BigDecimal idiProc) throws SkalaModelException {
		SessionHandle hnd = null;
		
		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);
		
			hnd.execWithTX(new JapeSession.TXBlock() {
		
				@Override
				public void doWithTx() throws SkalaModelException {
					finalizarOrdemProducaoStatus(idiProc);
				}
		
			});
		
		} catch (Exception e) {
			SkalaModelException.throwMe(null, e);
		} finally {
			JapeSession.close(hnd);
		}
	}
	public static void finalizarOrdemProducaoStatus(BigDecimal idiProc) throws SkalaModelException {
		CabecalhoInstanciaProcessoStatus status = new CabecalhoInstanciaProcessoStatus(false);
		setSituacaoAtividade(status, idiProc);
		
		alterarStatusProdutoAcabado(idiProc);
		setStatusProc(status, idiProc);
	}
	private static boolean setSituacaoAtividade(CabecalhoInstanciaProcessoStatus status, BigDecimal idiProc) throws SkalaModelException {
		boolean hasAtividade = false;
		
		try {
			JapeWrapper instanciaAtividadeDAO = JapeFactory.dao(DynamicEntityNames.INSTANCIA_ATIVIDADE);
			Collection<DynamicVO> atividadesVO = instanciaAtividadeDAO.find("this.IDIPROC = ?", idiProc);
			hasAtividade = !atividadesVO.isEmpty();
			
			BigDecimal tempoGastoMin = (status.getDhTermino() == null) ? BigDecimal.ZERO : BigDecimalUtil.getBigDecimal(TimeUtils.getDifferenceInMinutes(TimeUtils.getNow(), status.getDhTermino()));
			
			EntityUpdate entityUpdate;
			for (DynamicVO atividadeVO : atividadesVO) {
				entityUpdate = new EntityUpdate (DynamicEntityNames.INSTANCIA_ATIVIDADE,atividadeVO);
				entityUpdate.setField("CODUSUFIN", status.getCodUsuFinal());
				entityUpdate.setField("DHFINAL", status.getDhTermino());
				entityUpdate.setField("TEMPOGASTOMIN", tempoGastoMin);
				entityUpdate.fluidUpdate();
			}
			
		} catch (Exception e) {
			String msg = String.format("[OP %s] Não foi possível atualizar a situação da Atividade.", idiProc);
			msg = SkalaModelException.buildErrorMessage(msg, e);
			SkalaModelException.throwMe(msg, e);
		}
		
		return hasAtividade;
		
	}
	public static void alterarStatusProdutoAcabado(BigDecimal idiProc) throws SkalaModelException {

		try {
			JapeWrapper produtoAcabadoAProduzirDAO = JapeFactory.dao(DynamicEntityNames.PRODUTO_ACABADO_A_PRODUZIR);
			Collection<DynamicVO> produtoAcabadoVO = produtoAcabadoAProduzirDAO.find("this.IDIPROC = ? AND this.CONCLUIDO = 'N'", idiProc);

			if (produtoAcabadoVO.isEmpty()) {
				return;
			}

			EntityUpdate entityUpdate;
			for (DynamicVO vo : produtoAcabadoVO) {
				entityUpdate = new EntityUpdate(DynamicEntityNames.PRODUTO_ACABADO_A_PRODUZIR, vo);
				entityUpdate.setField("CONCLUIDO", "S");
				entityUpdate.fluidUpdate();
			}
		} catch (Exception e) {
			String msg = String.format("[IDPROC %s] Não foi possível concluir o Produto Acabado a Produzir.", idiProc);
			msg = SkalaModelException.buildErrorMessage(msg, e);
			SkalaModelException.throwMe(msg, e);
		}

	}
	protected static void setStatusProc(CabecalhoInstanciaProcessoStatus status, BigDecimal idiProc) throws SkalaModelException {
		
		try {
			JapeWrapper cabInstanciaProcessoDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_INSTANCIA_PROCESSO);
			DynamicVO cabInstanciaProcessoVO = cabInstanciaProcessoDAO.findByPK(idiProc);
			EntityUpdate entityUpdate = new EntityUpdate(DynamicEntityNames.CABECALHO_INSTANCIA_PROCESSO, cabInstanciaProcessoVO);
			entityUpdate.setField("STATUSPROC", status.getStatusProc());
			entityUpdate.setField("DHTERMINO", status.getDhTermino());
			entityUpdate.setField("CODUSUFINAL", status.getCodUsuFinal());
			entityUpdate.fluidUpdate();
		} catch (Exception e) {
			String msg = String.format("[OP %s] Não foi possível atualizar o <i>status</i> da Ordem de Produção.", idiProc);
			msg = SkalaModelException.buildErrorMessage(msg, e);
			SkalaModelException.throwMe(msg, e);
		}
		
	}

	public static void reabreOrdemProducao(BigDecimal idiProc) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					reabrirOrdemProducao(idiProc);
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe(null, e);
		} finally {
			JapeSession.close(hnd);
		}
	}
	public static void reabrirOrdemProducao(BigDecimal idiProc) throws SkalaModelException {
		CabecalhoInstanciaProcessoStatus status = new CabecalhoInstanciaProcessoStatus(true);
		boolean hasAtividade = setSituacaoAtividade(status, idiProc);
		
		if (hasAtividade)
			status.setStatusProc(CabecalhoInstanciaProcessoStatusEnum.EM_ANDAMENTO.getValue());
		
		restaurarSaldoAProduzir(idiProc);
		setStatusProc(status,idiProc);
	}
	private static void restaurarSaldoAProduzir(BigDecimal idiProc) throws SkalaModelException {
		JdbcWrapper jdbc = null;
		
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			NativeSql sql = new NativeSql(jdbc);
			sql.setReuseStatements(true);
			
			sql.setNamedParameter("IDIPROC", idiProc);
			sql.executeUpdate("UPDATE TPRIPA SET CONCLUIDO = 'N' WHERE IDIPROC = :IDIPROC AND CONCLUIDO = 'S'");
			
			StringBuilder updateApontamento = new StringBuilder();
			updateApontamento.append("UPDATE TPRAPA ");
			updateApontamento.append("SET QTDPERDA = 0 ");
			updateApontamento.append("WHERE ");
			updateApontamento.append("NUAPO IN ");
			updateApontamento.append("(SELECT NUAPO FROM TPRAPO WHERE IDIATV IN ");
			updateApontamento.append("(SELECT IDIATV FROM TPRIATV WHERE IDIPROC = :IDIPROC)) ");
			updateApontamento.append("AND QTDPERDA > 0");
			
			sql.executeUpdate(updateApontamento.toString());
			
		} catch (Exception e) {
			String msg = String.format("[IDIPROC %s] Não foi possível restaurar Saldo a Produzir. ", idiProc);
			msg = SkalaModelException.buildErrorMessage(msg, e);
			SkalaModelException.throwMe(msg, e);
		}

	}
	
	
}
