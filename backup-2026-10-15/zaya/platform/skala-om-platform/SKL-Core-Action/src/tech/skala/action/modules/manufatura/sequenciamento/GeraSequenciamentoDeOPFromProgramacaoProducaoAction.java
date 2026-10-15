package tech.skala.action.modules.manufatura.sequenciamento;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.action.modules.manufatura.pcp.programacaosimplificada.GeraOrdemProducaoProcessor;
import tech.skala.action.modules.manufatura.pcp.programacaosimplificada.ProgramacaoProducaoSimplificada;
import tech.skala.core.commons.extensions.Action;

public class GeraSequenciamentoDeOPFromProgramacaoProducaoAction implements AcaoRotinaJava {
	private static final String			TITULO_MENSAGEM_CONFIRMACAO = "Geração de Sequenciamento de Produção";
	private static final String			MENSAGEM_CONFIRMACAO = "Confirma a geração do Sequenciamento de Produção para os registros selecionados?";

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

				BigDecimal nuPrgP;
				GeraOrdemProducaoProcessor processor;
				for (Registro linha:linhas) {
					nuPrgP = BigDecimalUtil.getBigDecimal(linha.getCampo("NUPRGP"));
					programacao = new ProgramacaoProducaoSimplificada(nuPrgP);
					programacao.loadByPk(nuPrgP);
					
					processor = new GeraOrdemProducaoProcessor(programacao);
					processor.geraSequenciamentoOP();					
				}
			}

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}

	}

}
