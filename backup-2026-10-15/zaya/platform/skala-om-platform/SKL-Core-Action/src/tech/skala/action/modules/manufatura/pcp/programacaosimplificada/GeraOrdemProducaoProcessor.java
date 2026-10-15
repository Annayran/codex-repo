package tech.skala.action.modules.manufatura.pcp.programacaosimplificada;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import tech.skala.action.modules.manufatura.lancamentodeop.LancamentoDeOP;
import tech.skala.action.modules.manufatura.lancamentodeop.LancamentoDeOPExceptions;
import tech.skala.action.modules.manufatura.lancamentodeop.LancamentoDeOPExceptions.DeleteProductionOrderLaunchNotPossibleException;
import tech.skala.action.modules.manufatura.lancamentodeop.LancamentoDeOPExceptions.ProductionOrderCreationNotPossibleException;
import tech.skala.action.modules.manufatura.lancamentodeop.LancamentoDeOPExceptions.ProductionOrderNotIncludedException;
import tech.skala.action.modules.manufatura.lancamentodeop.LancamentoDeOPExceptions.QuantidadeDeLotesMenorQueUmException;
import tech.skala.action.modules.manufatura.lancamentodeop.LancamentoDeOPExceptions.WorkCenterNotSetException;
import tech.skala.action.modules.manufatura.lancamentodeop.LancamentoDeOPProcessor;
import tech.skala.action.modules.manufatura.pcp.programacaosimplificada.ProgramacaoProducaoSimplificadaExceptions.ProgramacaoNaoAtualizadaException;
import tech.skala.action.modules.manufatura.produtoacabadoaproduzir.ProdutoAcabadoAProduzir;
import tech.skala.action.modules.manufatura.sequenciamento.SequenciamentoDeOP;
import tech.skala.action.modules.manufatura.sequenciamento.SequenciamentoDeOPExceptions;
import tech.skala.action.modules.manufatura.sequenciamento.SequenciamentoDeOPExceptions.SequenciamentoNaoIncluidoException;
import tech.skala.action.modules.manufatura.sequenciamento.SequenciamentoProcessor;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.modules.manufatura.ordemproducao.VolumeProducao;
import tech.skala.modules.manufatura.ordemproducao.VolumeProducaoFinder;

/**
 * Classe utilitaria para geracao automatica de Ordens de Producao conforme <b>Programacao de Producao Simplificada</b>.
 * <p>
 * Contem metodos para todo o processamento e persistencia de dados do <b>Lancamento de OP</b> e da propria <b>OP</b>.
 * <p>
 * 
 * @author cassiomenezes
 * @author hiagoalexandre
 * @since 2022-01-03
 *
 */
public class GeraOrdemProducaoProcessor {

	private ProgramacaoProducaoSimplificada				programacao;
	private LancamentoDeOP								lancamentoDeOP;
	
	public GeraOrdemProducaoProcessor(ProgramacaoProducaoSimplificada programacao) {
		this.programacao = programacao;
	}

	public LancamentoDeOP gerarOP() throws SkalaModelException {
		
		if (!programacaoEmElaboracao())
			throw new LancamentoDeOPExceptions.ProgramacaoNaoEstaEmElaboracaoException(programacao.getNuProgP());

		iniciarLancamentoProducao();
		incluirNovoLancamentoOP();
		
		LancamentoDeOPProcessor processor = new LancamentoDeOPProcessor(lancamentoDeOP);
		
		insereProdutoOrdemProducao(processor);
		alocaCentroDeTrabalho(processor);
		lancaOrdensProducao(processor);

		return lancamentoDeOP;
		
	}

	private void iniciarLancamentoProducao() throws QuantidadeDeLotesMenorQueUmException {
		this.lancamentoDeOP = new LancamentoDeOP();
		this.lancamentoDeOP.setDescricao(programacao.getDescricao());
		this.lancamentoDeOP.setReutilizar(false);
		this.lancamentoDeOP.setUsarSomenteProcessosPadrao(false);
		this.lancamentoDeOP.setTerceiro(false);
		this.lancamentoDeOP.setCodPlP(programacao.getCodPlp());
		this.lancamentoDeOP.setCodWcp(programacao.getCodWcp());
		this.lancamentoDeOP.setIdProc(programacao.getIdProc());
		this.lancamentoDeOP.setQtdCapacidadePad(programacao.getQtdCapacidadePad());
				
		setProdutosAProduzir(programacao.getQtdLotes().longValue());
	}

	private void setProdutosAProduzir(long qtdLotes) throws QuantidadeDeLotesMenorQueUmException {

		if (qtdLotes < 1)
			throw new LancamentoDeOPExceptions.QuantidadeDeLotesMenorQueUmException();

		ProdutoAcabadoAProduzir produtoAcabado;
		for (int i = 0; i < qtdLotes; i++) {
			produtoAcabado = new ProdutoAcabadoAProduzir();
			produtoAcabado.setCodProdPA(programacao.getCodProdPA());
			produtoAcabado.setControlePA(" ");
			produtoAcabado.setQtdProduzir(programacao.getQtdCapacidadePad());
			this.lancamentoDeOP.getProdutosAProduzir().add(produtoAcabado);
		}
		
	}

	private void incluirNovoLancamentoOP() throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					lancamentoDeOP.persists();
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Nao foi possivel", e);
		} finally {
			JapeSession.close(hnd);
		}		
	}

	private void insereProdutoOrdemProducao(LancamentoDeOPProcessor processor) throws SkalaModelException {
		processor.insereProdutoOrdemProducao();
	}

	private void alocaCentroDeTrabalho(LancamentoDeOPProcessor processor) throws WorkCenterNotSetException {
		processor.alocaCentroDeTrabalho();
	}
	
	private void lancaOrdensProducao(LancamentoDeOPProcessor processor) throws ProductionOrderCreationNotPossibleException {
		processor.lancaOrdensProducao();
	}

	private boolean programacaoEmElaboracao() {
		return ProgramacaoProducaoSimplificadaStatusEnum.EM_ELABORACAO.getValue().equals(programacao.getStatus());
	}

	public void geraSequenciamentoOP() throws SequenciamentoNaoIncluidoException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					
					List<BigDecimal> opsDuplicadas = ProgramacaoSimplificadaFinder.findSequenciamentoProducaoDuplicado(programacao.getNuProgP());
					
					if (!opsDuplicadas.isEmpty()) {
						throw new SkalaModelException("Já existe um sequenciamento criado a partir dessa programação.");
					}
							
					SequenciamentoDeOP sequenciamento = new SequenciamentoDeOP(programacao);
					sequenciamento.setOrdensDeProducaoLancadas(programacao.getOrdensDeProducaoLancadas());
					
					SequenciamentoProcessor sequenciamentoProcessor = new SequenciamentoProcessor(sequenciamento);
					sequenciamentoProcessor.incluirOPsNoSequenciamento();
				}

			});

		} catch (Exception e) {
			throw new SequenciamentoDeOPExceptions.SequenciamentoNaoIncluidoException(e);
		} finally {
			JapeSession.close(hnd);
		}
		
	}
	
	public void geraSequenciamentoOP(BigDecimal seqOP) throws SequenciamentoNaoIncluidoException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					SequenciamentoDeOP sequenciamento = new SequenciamentoDeOP(programacao);
					sequenciamento.setSeqOP(seqOP);
					sequenciamento.setOrdensDeProducaoLancadas(programacao.getOrdensDeProducaoLancadas());
					
					SequenciamentoProcessor sequenciamentoProcessor = new SequenciamentoProcessor(sequenciamento);
					sequenciamentoProcessor.incluirOPsNoSequenciamento();
				}

			});

		} catch (Exception e) {
			throw new SequenciamentoDeOPExceptions.SequenciamentoNaoIncluidoException(e);
		} finally {
			JapeSession.close(hnd);
		}
		
	}

	public void atualizaStatusProgramacao(BigDecimal nuPrgp) throws ProgramacaoNaoAtualizadaException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					ProgramacaoSimplificadaProcessor programacaoProcessor = new ProgramacaoSimplificadaProcessor(programacao);
					programacaoProcessor.atualizarStatusProgramacao();
					programacaoProcessor.incluirOrdensDeProducao(nuPrgp);
				}

			});

		} catch (Exception e) {
			throw new ProgramacaoProducaoSimplificadaExceptions.ProgramacaoNaoAtualizadaException(e);
		} finally {
			JapeSession.close(hnd);
		}
		
	}

	public void geraVolumeProducao() throws RecordNotFoundException, InsertNotPossibleException {
		List<BigDecimal> gruposProducaoEmbalagem = Arrays.asList(BigDecimal.valueOf(30101), BigDecimal.valueOf(30201),
				BigDecimal.valueOf(30901));

		BigDecimal codGProd = programacao.getProduto().getCodGProd();
		if (gruposProducaoEmbalagem.contains(codGProd))
			gerarVolumeProducao();
	}
	
	private void gerarVolumeProducao() throws RecordNotFoundException, InsertNotPossibleException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					BigDecimal qtdProduzir = programacao.getQtdCapacidadePad();
					BigDecimal codGProd = programacao.getProduto().getCodGProd();
					VolumeProducaoFinder finder = new VolumeProducaoFinder();
					BigDecimal capacidadePorCaixa = finder.buscaCapacidadePorCaixa(codGProd);
					BigDecimal qtdVolumes = qtdProduzir.divide(capacidadePorCaixa, 0, RoundingMode.CEILING);
					qtdVolumes = qtdVolumes.multiply(BigDecimal.valueOf(1.10)).add(BigDecimal.ONE);

					VolumeProducao volume;
					for (BigDecimal idiProc : programacao.getOrdensDeProducaoLancadas()) {
						for (int i = 1; i <= qtdVolumes.intValue(); i++) {
							volume = new VolumeProducao();
							volume.setCapacidade(capacidadePorCaixa);
							volume.setIdiProc(idiProc);
							volume.setNuvOP(BigDecimal.valueOf(i));
							volume.persists();

						}
					}
				}

			});

		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		} finally {
			JapeSession.close(hnd);
		}
	}
	
}
