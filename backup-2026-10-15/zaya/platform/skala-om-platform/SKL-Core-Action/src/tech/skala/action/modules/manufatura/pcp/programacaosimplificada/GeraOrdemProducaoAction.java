package tech.skala.action.modules.manufatura.pcp.programacaosimplificada;

import java.math.BigDecimal;
import java.util.LinkedList;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.action.integration.prodwin.SendProductionOrderHelper;
import tech.skala.action.modules.manufatura.lancamentodeop.LancamentoDeOP;
import tech.skala.core.commons.extensions.Action;

/**
 * A classe {@code GeraOrdemProducaoAction} permite a geracao automatica de
 * Ordens de Producao conforme <b>Programacao de Producao Simplificada</b>.
 * 
 * <p>
 * As OP's geradas serao associadas ao <b>Centro de Trabalho</b> informado.<br>
 * A <b>Quantidade de Lotes</b> informada na programação determina a quantidade
 * de OP's a serem geradas e a quantidade a ser produzida em cada OP é
 * determinada pelo tamanho do lote padrão do <b>Centro de Trabalho</b>.
 * <p>
 * 
 * @author cassiomenezes
 * @since 2022-07-25
 *
 */
public class GeraOrdemProducaoAction implements AcaoRotinaJava {
	private static final String TITULO_MENSAGEM_CONFIRMACAO = "Geração de Ordens de Produção";
	private static final String MENSAGEM_CONFIRMACAO = "Confirma a geração das Ordens de Produção para os registros selecionados?";

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {

		boolean isConfirmar = contexto.confirmarSimNao(TITULO_MENSAGEM_CONFIRMACAO, MENSAGEM_CONFIRMACAO, 1);

		ProgramacaoProducaoSimplificada programacao;
		Action action = new Action(this.getClass().getCanonicalName());
		try {
			action.start();

			if (isConfirmar) {
				Registro[] linhas = contexto.getLinhas();
				action.validateRows(linhas);

				GeraOrdemProducaoProcessor processor;
				LancamentoDeOP lancamento = null;

				SendProductionOrderHelper prodwinProcessor;

				for (Registro linha : linhas) {
					programacao = new ProgramacaoProducaoSimplificada(null);
					programacao.loadByRecord(linha);

					processor = new GeraOrdemProducaoProcessor(programacao);
					lancamento = processor.gerarOP();

					programacao.setOrdensDeProducaoLancadas(new LinkedList<>(lancamento.getOrdensDeProducaoLancadas()));

					action.appendMessage("CODWCP " + programacao.getCodWcp());

					BigDecimal nuPrgp = programacao.getNuProgP();

					processor.geraSequenciamentoOP();
					processor.geraVolumeProducao();

					processor.atualizaStatusProgramacao(nuPrgp);

					if (programacao.getCodWcp().longValue() == 1L) {

						for (BigDecimal idiProc : programacao.getOrdensDeProducaoLancadas()) {

							prodwinProcessor = new SendProductionOrderHelper(action, idiProc, BigDecimal.ZERO,
									nuPrgp);

							prodwinProcessor.execute();
						}
					}
				}

				if (lancamento != null)
					action.appendMessageWithLineSeparator(lancamento.getOrdensDeProducaoLancadas().toString());

			} else {
				action.error(null);
			}

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}

	}

}
