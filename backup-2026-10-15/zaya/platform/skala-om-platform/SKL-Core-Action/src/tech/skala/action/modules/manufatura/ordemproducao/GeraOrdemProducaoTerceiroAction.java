package tech.skala.action.modules.manufatura.ordemproducao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.CbmModelException;
import com.cbmsoft.modelcore.LoggerFactory;
import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.action.modules.manufatura.lancamentodeop.LancamentoDeOP;
import tech.skala.action.modules.manufatura.ordemproducaoold.ApontamentoProducaoHelper;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.extensions.Action;

/**
 * Gerar <b>Ordem de Producao de Industrializacao em Terceiros</b> conforme
 * <b>Nota de Industrializacao</b>.
 * <p>
 * Acao utilizada para gerar ordens de producao a partir de notas de
 * industrializacao enviadas pelas empresas que industrializam para o Grupo.<br>
 * Acao chamada em:<br>
 * <b>Gerar OP de industrialização</b><br>
 * Platina » Rotinas » Gerar OP de industrialização<br>
 * ID: br.com.sankhya.menu.adicional.AD_PLVGFCABIND<br>
 * Somente serao geradas as OP's para TOP's especificas de entrada (atualmente
 * <b>TOP</b> 2109).
 * </p>
 * <p>
 * <b>[PLT] Gera OP Industrializacao 3o.</b><br>
 * {@code TGFCAB}
 * </p>
 * 
 * @author cassiomenezes
 * @author hiagoalexandre
 * @since 2022-09-23
 */
public class GeraOrdemProducaoTerceiroAction implements AcaoRotinaJava {

	private static final String TITULO_MENSAGEM_CONFIRMACAO = "Geração de Ordens de Produção";
	private static final String MENSAGEM_CONFIRMACAO = "Confirma a geração das Ordens de Produção de Industrialização para os registros selecionados?";
	private final Logger logger = new LoggerFactory(this.getClass().getCanonicalName()).getLogger();

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		boolean isConfirmar = contexto.confirmarSimNao(TITULO_MENSAGEM_CONFIRMACAO, MENSAGEM_CONFIRMACAO, 0);

		Timestamp now = TimeUtils.getNow();
		String msg;
		BigDecimal nuNota = null;
		try {
			action.start();

			if (isConfirmar) {
				Registro[] linhas = contexto.getLinhas();
				action.validateRows(linhas);

				BigDecimal codPrc;
				LancamentoDeOP lancamentoDeOP = null;
				GeraOrdemProducaoTerceiroProcessor processor = null;
				for (Registro linha : linhas) {
					nuNota = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.NUNOTA));
					codPrc = BigDecimalUtil.getBigDecimal(linha.getCampo("CODPRC"));

					processor = new GeraOrdemProducaoTerceiroProcessor(nuNota, codPrc);

					// Validar se existe registro na tabela
					processor.validaLog();
					// Incluir data inicial
					processor.definirDataInicial();

					lancamentoDeOP = processor.geraOP3();

					msg = String.format("Tempo de geração das OPs %s", action.getElapsedTime());
					logger.info(msg);
					action.appendMessageWithLineSeparator(msg);

					action.appendMessageWithLineSeparator(msg);
					for (BigDecimal idiProc : lancamentoDeOP.getOrdensDeProducaoLancadas()) {

						processaOrdemProducao3(idiProc, lancamentoDeOP);

						msg = String.format("[IDIPROC %s] Tempo para processamento da OPs %s", idiProc,
								action.getElapsedTime());
						logger.info(msg);
						action.appendMessageWithLineSeparator(msg);
					}

					if (!lancamentoDeOP.getErros().isEmpty()) {
						msg = String.format("[NUNOTA %s] Favor verificar as seguintes situações:%n", nuNota);
						action.appendMessage(msg);

						for (String errMsg : lancamentoDeOP.getErros()) {
							msg = String.format("%s%n", errMsg);
							action.appendMessage(msg);
						}
					}

				}
				if (lancamentoDeOP != null)
					action.appendMessageWithLineSeparator(lancamentoDeOP.getOrdensDeProducaoLancadas().toString());

				// Definir dh final
				processor.definirDataFinal();
			}

		} catch (Exception e) {

//			//Registrar o motivo de Erro
			if (nuNota != null) {
				GeraOrdemProducaoTerceiroProcessor processor = new GeraOrdemProducaoTerceiroProcessor(nuNota, null);
				processor.incluiErro(e.getMessage());
			}

			String oldMensagemRetorno = action.getMensagemRetorno();
			action.error(e);
			action.appendMessageWithLineSeparator("");
			action.appendMessageWithLineSeparator(oldMensagemRetorno);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

	/**
	 * @param action
	 * @param idiProc
	 * @param lancamentoDeOP
	 * @throws CbmModelException
	 * @throws SkalaModelException
	 */
	private void processaOrdemProducao3(BigDecimal idiProc, LancamentoDeOP lancamentoDeOP) throws SkalaModelException {
		JdbcWrapper jdbc = null;

		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			ApontamentoProducaoHelper apontamentoHelper;
			apontamentoHelper = new ApontamentoProducaoHelper(jdbc, idiProc, lancamentoDeOP);
			apontamentoHelper.processaOrdemProducao3();

		} catch (SQLException e) {
			SkalaModelException.throwMe("processaOrdemProducao3()", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
		}
	}

}