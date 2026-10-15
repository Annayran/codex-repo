package tech.skala.action.modules.comercial.meta;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.stream.Collectors;

import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.action.beans.demanda.DemandaFinder;
import tech.skala.action.beans.meta.MetaPorFamilia;
import tech.skala.action.beans.meta.MetaPorProduto;
import tech.skala.action.beans.meta.UsaReferenciaEnum;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.extensions.Action;

public class MetasOrcamentoProcessor {

	private Action							action;
	
	public MetasOrcamentoProcessor() {
	
	}
	
	public MetasOrcamentoProcessor(Action action) {
		this.action = action;
	}

	public void calculaMetaPorProduto(MetaPorFamilia metaPorFamilia) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					calcularMetaPorProduto(metaPorFamilia);
				}

			});

		} catch (Exception e) {
			String msg = String.format("[CODFAM %s][DTREF %s] A meta não foi processada.", metaPorFamilia.getCodFam(), metaPorFamilia.getDtRef());
			SkalaModelException.throwMe(msg, e);
		} finally {
			JapeSession.close(hnd);
		}
	}

	private void calcularMetaPorProduto(MetaPorFamilia metaPorFamilia) throws SkalaModelException {
		
		String msg = String.format("Calculando meta para a família %s", metaPorFamilia.getCodFam());
		action.appendMessageWithLineSeparator(msg);
		
		BigDecimal qtdPendenteFam = metaPorFamilia.getQtdPendente();
		BigDecimal qtdMediaFam = metaPorFamilia.getQtdMedia();

//		if (BigDecimalUtil.isEmpty(qtdPendenteFam) || BigDecimalUtil.isEmpty(qtdMediaFam)) {
//			msg = String.format(msg, null)
//		}
//			return;

		BigDecimal qtdPrevFam = metaPorFamilia.getQtdPrev();
		BigDecimal qtdPrevAju = metaPorFamilia.getQtdPrevAju();
		
		BigDecimal fatorCarteira;
		BigDecimal qtdMedia;
		BigDecimal fatorMedia;
		BigDecimal qtdPrevC = BigDecimal.ZERO;
		BigDecimal qtdPrevM = BigDecimal.ZERO;
		BigDecimal qtdAjuC = BigDecimal.ZERO;
	    BigDecimal qtdAjuM = BigDecimal.ZERO;
		BigDecimal qtdPrev = BigDecimal.ZERO;
		BigDecimal estoqueFinal = BigDecimal.ZERO;
		BigDecimal qtdFabrica = BigDecimal.ZERO;
		BigDecimal qtdAjuCPad = BigDecimal.ZERO;
		BigDecimal qtdAjuMPad = BigDecimal.ZERO;
		BigDecimal fatorFabrica;
		
		BigDecimal sumQtdPrevSku = BigDecimal.ZERO;
		BigDecimal sumVlrPendente = BigDecimal.ZERO;
		BigDecimal sumVlrPrev = BigDecimal.ZERO;
		BigDecimal vlrUnitFam = BigDecimal.ZERO;
		
		try {
			Collection<DynamicVO> itensDemandaVO;
			BigDecimal qtdPendente;
			BigDecimal vlrUnit;
			BigDecimal vlrPendente = BigDecimal.ZERO;
			BigDecimal vlrPrev;
			
			for (MetaPorProduto produto : metaPorFamilia.getProdutos()) {
				
				qtdPendente = produto.getQtdPendente();
				qtdMedia = produto.getQtdMedia();
				
				fatorCarteira = (qtdPendenteFam == BigDecimal.ZERO) ? BigDecimal.ZERO : qtdPendente.divide(qtdPendenteFam, BigDecimalUtil.MATH_CTX);
				fatorMedia = (qtdMedia == BigDecimal.ZERO) ? BigDecimal.ZERO : qtdMedia.divide(qtdMediaFam, BigDecimalUtil.MATH_CTX);
				
				qtdPrevC = BigDecimalUtil.getRounded(qtdPrevFam.multiply(fatorCarteira, BigDecimalUtil.MATH_CTX),0);
				qtdPrevM = BigDecimalUtil.getRounded(qtdPrevFam.multiply(fatorMedia, BigDecimalUtil.MATH_CTX),0);
				qtdAjuC = BigDecimalUtil.getRounded(qtdPrevAju.multiply(fatorCarteira, BigDecimalUtil.MATH_CTX),0);
				qtdAjuM = BigDecimalUtil.getRounded(qtdPrevAju.multiply(fatorMedia, BigDecimalUtil.MATH_CTX),0);
				
				qtdPrev = (metaPorFamilia.usaCarteira() ? qtdAjuC : qtdAjuM);

				BigDecimal qtdPrevista = UsaReferenciaEnum.ATUAL.getValue().equals(metaPorFamilia.getUsarReferencia())
						? qtdPrev
						: BigDecimal.ZERO;

				estoqueFinal = (qtdPrevista == BigDecimal.ZERO) ? BigDecimal.ZERO : qtdPrevista
						.multiply(BigDecimalUtil.getValueOrZero(metaPorFamilia.getPercCob()), BigDecimalUtil.MATH_CTX)
						.divide(BigDecimalUtil.CEM_VALUE, BigDecimalUtil.MATH_CTX);
				
				itensDemandaVO = DemandaFinder.getItensDemandaByReferenciaEProduto(metaPorFamilia.getDtRef(), produto.getCodProd());

				vlrPendente = (itensDemandaVO == null) ? BigDecimal.ZERO : itensDemandaVO.stream()
						.map(vo -> BigDecimalUtil.getValueOrZero(vo.asBigDecimal("VLRPENDENTE")))
						.reduce(BigDecimal.ZERO, BigDecimal::add);
				
				vlrUnit = (qtdPendente.compareTo(BigDecimal.ZERO) == 0) ? BigDecimal.ZERO : vlrPendente.divide(qtdPendente, BigDecimalUtil.MATH_CTX);
				vlrPrev = qtdPrev.multiply(vlrUnit, BigDecimalUtil.MATH_CTX);
				
				produto.setQtdPrevC(qtdPrevC);
				produto.setQtdPrevM(qtdPrevM);
				produto.setQtdAjuC(qtdAjuC);
				produto.setQtdAjuM(qtdAjuM);
				produto.setQtdPrev(qtdPrev);
				produto.setVlrPendente(vlrPendente);
				produto.setVlrUnit(vlrUnit);
				produto.setVlrPrev(vlrPrev);
				produto.setEstoqueFinal(estoqueFinal);
				
				sumQtdPrevSku = sumQtdPrevSku.add(qtdPrev);
				sumVlrPendente = sumVlrPendente.add(vlrPendente);
				sumVlrPrev = sumVlrPrev.add(vlrPrev);
			}
			
			for (MetaPorProduto produto : metaPorFamilia.getProdutos()) {
				qtdPendente = produto.getQtdPendente();
				qtdMedia = produto.getQtdMedia();

				fatorCarteira = (qtdPendenteFam == BigDecimal.ZERO) ? BigDecimal.ZERO : qtdPendente.divide(qtdPendenteFam, BigDecimalUtil.MATH_CTX);
				fatorMedia =  (qtdMediaFam == BigDecimal.ZERO) ? BigDecimal.ZERO : qtdMedia.divide(qtdMediaFam, BigDecimalUtil.MATH_CTX);

				qtdFabrica = BigDecimalUtil.getValueOrZero(produto.getProduto().getFamilia().getQtdCapacidadeMax());
				fatorFabrica = (sumQtdPrevSku == BigDecimal.ZERO) ? BigDecimal.ZERO : qtdFabrica.divide(sumQtdPrevSku, BigDecimalUtil.MATH_CTX);

				qtdAjuC = BigDecimalUtil.getRounded(qtdPrevAju.multiply(fatorCarteira, BigDecimalUtil.MATH_CTX), 0);
				qtdAjuM = BigDecimalUtil.getRounded(qtdPrevAju.multiply(fatorMedia, BigDecimalUtil.MATH_CTX), 0);

				qtdAjuCPad = (qtdAjuC.longValue() > qtdFabrica.longValue() && qtdFabrica.longValue() != 0L)
						? BigDecimalUtil.getRounded(qtdAjuC.multiply(fatorFabrica, BigDecimalUtil.MATH_CTX), 0)
						: qtdAjuC;

				qtdAjuMPad = (qtdAjuM.longValue() > qtdFabrica.longValue() && qtdFabrica.longValue() != 0L)
						? BigDecimalUtil.getRounded(qtdAjuM.multiply(fatorFabrica, BigDecimalUtil.MATH_CTX), 0)
						: qtdAjuM;

				produto.setQtdAjuCPad(qtdAjuCPad);
				produto.setQtdAjuMPad(qtdAjuMPad);
				produto.persists();
			}
			
			metaPorFamilia.setVlrPendente(sumVlrPendente);
			metaPorFamilia.setQtdPrevSku(sumQtdPrevSku);
			metaPorFamilia.setVlrPrev(sumVlrPrev);
			
			vlrUnitFam = (qtdPendenteFam.compareTo(BigDecimal.ZERO) == 0) ? BigDecimal.ZERO : sumVlrPendente.divide(qtdPendenteFam, BigDecimalUtil.MATH_CTX);
			metaPorFamilia.setVlrUnit(vlrUnitFam);
			metaPorFamilia.persists();
			
			
		} catch (Exception e) {
			msg = String.format("[CODFAM %s] Não foi possível calcular metas por produto.", metaPorFamilia.getCodFam());
			SkalaModelException.throwMe(msg, e);
			
		}
		
	}

	public void confirmaMetas(Timestamp dtRef) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					confirmarMetas(dtRef);
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível atualizar o valor de Cobertura de Estoque.", e);
		} finally {
			JapeSession.close(hnd);
		}
		
	}
	
	public void confirmarMetas(Timestamp dtRef) throws SkalaModelException {

		try {
			JapeWrapper metaFamiliaDAO = JapeFactory.dao("AD_PLTGFFAMMET");

			Collection<DynamicVO> metasVO = metaFamiliaDAO.find("this.DTREF = ?", dtRef);

			EntityUpdate entityUpdate;
			for (DynamicVO metaVO : metasVO) {
				entityUpdate = new EntityUpdate("AD_PLTGFFAMMET", metaVO);
				entityUpdate.setField("CONFIRMADO", "S");
				entityUpdate.fluidUpdate();
			}
		} catch (Exception e) {
			String msg = "Não foi possível encontrar nenhuma meta com esta data de referência";
			SkalaModelException.throwMe(msg, e);
		}
	}

	public void alteraCoberturaEstoque(BigDecimal coberturaEstoque, MetaPorFamilia meta) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					alterarCoberturaEstoque(coberturaEstoque, meta);
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível atualizar o valor de Cobertura de Estoque.", e);
		} finally {
			JapeSession.close(hnd);
		}
		
	}
	
	public void alterarCoberturaEstoque(BigDecimal percentual, MetaPorFamilia meta) throws SkalaModelException {
		
		BigDecimal codEmp = meta.getCodEmp();
		BigDecimal codFam = meta.getCodFam();
		Timestamp dtRef = meta.getDtRef();
		
		try {
			JapeWrapper metaFamiliaDAO = JapeFactory.dao("AD_PLTGFFAMMET");
			DynamicVO metaVO = metaFamiliaDAO.findByPK(codFam, codEmp, dtRef);
			
			EntityUpdate entityUpdate = new EntityUpdate("AD_PLTGFFAMMET", metaVO);
			entityUpdate.setField("PERCCOB", percentual);
			entityUpdate.fluidUpdate();
			
		} catch (Exception e) {
			String msg = "Não foi possível atualizar o valor de Cobertura de Estoque.";
			SkalaModelException.throwMe(msg, e);
		}
	}

	public void duplicaMeta(MetaPorFamilia meta) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					Timestamp dtRefProxMes = TimeUtils.dataAdd(meta.getDtRef(), 1, Calendar.MONTH);
					meta.setDtRef(dtRefProxMes);

					meta.persists();
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível duplicar Meta", e);
		} finally {
			JapeSession.close(hnd);
		}
	}

	public void calculaCurvaABC(MetaPorFamilia meta) throws SkalaModelException {
		BigDecimal sumQtdPrev = meta.getProdutos().stream().map(MetaPorProduto::getQtdPrev)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		List<MetaPorProduto> produtosOrdenados = meta.getProdutos().stream()
				.sorted(Comparator.comparing(MetaPorProduto::getQtdPrev).reversed())
				.collect(Collectors.toList());
		
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					calcularCurvaABC(sumQtdPrev, produtosOrdenados);
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível calcular Curva ABC para a Meta.", e);
		} finally {
			JapeSession.close(hnd);
		}
	}

	private void calcularCurvaABC(BigDecimal sumQtdPrev, List<MetaPorProduto> produtosOrdenados)
			throws SkalaModelException {

		LongFunction<String> getCategoriaABC = percAcumMeta -> {
			String categoria;

			if (percAcumMeta >= 0L && percAcumMeta < 50L)
				categoria = "A";
			else if (percAcumMeta >= 50L && percAcumMeta < 80L)
				categoria = "B";
			else if (percAcumMeta >= 80L && percAcumMeta < 85L)
				categoria = "C";
			else
				categoria = "D";
			return categoria;
		};

		BigDecimal percABC;
		String categoriaABC;

		BigDecimal percAcumulado = BigDecimal.ZERO;

		for (MetaPorProduto produto : produtosOrdenados) {

			percABC = (sumQtdPrev.longValue() == 0L) ? BigDecimal.ZERO
					: BigDecimalUtil.porcentagem(produto.getQtdPrev(), sumQtdPrev, BigDecimalUtil.MATH_CTX);
			percAcumulado = percAcumulado.add(percABC);

			produto.setPercABC(percABC);
			produto.setPercAcumulado(percAcumulado);

			categoriaABC = getCategoriaABC.apply(percAcumulado.longValue());
			produto.setCategoriaABC(categoriaABC);

			produto.persists();
		}
	}
}
