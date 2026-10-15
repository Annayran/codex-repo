package tech.skala.action.modules.manufatura.sequenciamento;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.action.modules.manufatura.lancamentodeop.LancamentoDeOP;
import tech.skala.action.modules.manufatura.ordemproducao.OrdemProducaoExceptions;
import tech.skala.action.modules.manufatura.ordemproducao.OrdemProducaoExceptions.LoteNaoAtualizadoException;
import tech.skala.action.modules.manufatura.ordemproducao.OrdemProducaoExceptions.OrdemProducaoNaoCriadaException;
import tech.skala.action.modules.manufatura.ordemproducao.OrdemProducaoProcessor;
import tech.skala.action.modules.manufatura.ordemproducao.OrdemProducaoStatusEnum;
import tech.skala.action.modules.manufatura.pcp.programacaosimplificada.GeraOrdemProducaoProcessor;
import tech.skala.action.modules.manufatura.pcp.programacaosimplificada.ProgramacaoProducaoSimplificada;
import tech.skala.action.modules.manufatura.pcp.programacaosimplificada.ProgramacaoProducaoSimplificadaStatusEnum;
import tech.skala.action.modules.manufatura.planta.PlantaManufaturaFinder;
import tech.skala.action.modules.manufatura.workcenter.WorkCenterExceptions.WorkCenterNotFoundException;
import tech.skala.action.modules.manufatura.workcenter.WorkCenterFinder;
import tech.skala.action.modules.manufatura.workcenter.WorkCenterProcessor;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class TrocarLinhaProducaoProcessor {

	/**
	 * O método tem como objetivo realizar a troca da Linha de Trabalho de uma
	 * Programação
	 * 
	 * <p>
	 * O método realiza a troca de um Centro de Trabalho de uma determinada
	 * Programação Esta troca possui duas situações possíveis
	 * 
	 * <li>A primeira é quando a Capacidade Padrão do Centro de Trabalho de destino
	 * for igual da Capacidade padrão do Centro de trabalho de Origem</li> <br>
	 * <li>A segunda situação é quando a Capacidade Padrão do Centro de Trabalho de
	 * destino for diferente da Capacidade padrão do Centro de trabalho de
	 * Origem</li>
	 * 
	 * </p>
	 * 
	 * @param idiProc
	 * @param codWcpOrigem
	 * @param codWcpDestino
	 * @throws UpdateNotPossibleException Caso ocorrer algum erro na atualização da
	 *                                    Linha de Trabalho.
	 */
	public void trocarLinhaProducaoComMesmasCapacidades(BigDecimal idiProc, BigDecimal codWcpOrigem,
			BigDecimal codWcpDestino) throws UpdateNotPossibleException {
		WorkCenterProcessor workCenterProcessor = new WorkCenterProcessor();
		workCenterProcessor.atualizaWorkCenterInstancia(idiProc, codWcpOrigem, codWcpDestino);
	}

	public List<BigDecimal> getInformacoesLote(Registro linha, BigDecimal codWcpDestino)
			throws WorkCenterNotFoundException, RecordNotFoundException {

		BigDecimal qtdTotalAProduzir = getQtdTotalAProduzir(linha);

		BigDecimal qtdCapacidadePad = WorkCenterFinder.findQtdCapacidadePadrao(codWcpDestino);

		BigDecimal qtdLotesRoudingCeiling = qtdTotalAProduzir.divide(qtdCapacidadePad, RoundingMode.CEILING);
		BigDecimal qtdTotalRoudingCeiling = qtdLotesRoudingCeiling.multiply(qtdCapacidadePad);

		BigDecimal qtdLotesRoudingDown = qtdTotalAProduzir.divide(qtdCapacidadePad, RoundingMode.DOWN);
		BigDecimal qtdTotalRoudingDown = qtdLotesRoudingDown.multiply(qtdCapacidadePad);

		return Arrays.asList(qtdLotesRoudingCeiling, qtdCapacidadePad, qtdTotalRoudingCeiling, qtdLotesRoudingDown,
				qtdCapacidadePad, qtdTotalRoudingDown);

	}

	public void cancelarOPs(BigDecimal idiProc, String statusProc) throws OrdemProducaoNaoCriadaException {
		OrdemProducaoProcessor ordemProducaoProcessor = new OrdemProducaoProcessor();

		if (!OrdemProducaoStatusEnum.CRIADO.getValue().equals(statusProc)
				&& !OrdemProducaoStatusEnum.CANCELADO.getValue().equals(statusProc)) {
			throw new OrdemProducaoExceptions.OrdemProducaoNaoCriadaException(idiProc);
		}

		if (OrdemProducaoStatusEnum.CRIADO.getValue().equalsIgnoreCase(statusProc))
			ordemProducaoProcessor.cancelaOP(idiProc);

	}
	
	public void trocarLinhaProducaoComCapacidadesDiferentes(ProgramacaoProducaoSimplificada programacao,
			BigDecimal seqOP) throws SkalaModelException {
		programacao.setStatus(ProgramacaoProducaoSimplificadaStatusEnum.EM_ELABORACAO.getValue());

		LancamentoDeOP lancamento = null;
		GeraOrdemProducaoProcessor geraOrdemProducaoProcessor = new GeraOrdemProducaoProcessor(programacao);
		lancamento = geraOrdemProducaoProcessor.gerarOP();

		programacao.setOrdensDeProducaoLancadas(new LinkedList<>(lancamento.getOrdensDeProducaoLancadas()));

		geraOrdemProducaoProcessor.geraSequenciamentoOP(seqOP);
		// geraOrdemProducaoProcessor.atualizaStatusProgramacao();

	}

	public BigDecimal getQtdTotalAProduzir(Registro linha) throws RecordNotFoundException {
		SequenciamentoDeOPFinder seqFinder = new SequenciamentoDeOPFinder();
		BigDecimal seqOP = BigDecimalUtil.getBigDecimal(linha.getCampo("SEQOP"));

		return seqFinder.findQtdProduzir(seqOP);
	}

	public void trocarControleNumeracaoLote(String nroLote, ProgramacaoProducaoSimplificada programacao) throws RecordNotFoundException, LoteNaoAtualizadoException {
		
		BigDecimal codEmp = PlantaManufaturaFinder.findCodEmp(programacao.getCodPlp());
		
		OrdemProducaoProcessor opProcessor = new OrdemProducaoProcessor();
		opProcessor.setLote(nroLote, programacao.getCodProdPA(), codEmp);
		
	}

}
