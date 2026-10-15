package tech.skala.action.modules.data.etl;

import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;

import com.sankhya.util.TimeUtils;

import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import tech.skala.action.core.filamensagem.FilaMensagemUtils;
import tech.skala.core.commons.extensions.JobAction;
import tech.skala.core.main.Skl;
import tech.skala.core.util.time.TimerExceptions.TemporizadorNaoIniciadoException;

/**
 * Atualização de Estoque Diário
 * <p>
 * 
 * Ação agendada criada para realizar a atualizacao de tabelas do <i>Data
 * Lake</i> e <i>Data Warehouse</i> relativas a lancamentos das centrais
 * <code>TGFCAB e TGFITE</code>. Insere nas tabelas <b>{@code DLTGFCAB} e
 * {@code DLTGFITE}</b>, entre 30 dias atrás até o dia D-1, alem das tabelas de
 * <b>DW</b>.
 * 
 * @author cassiomenezes
 * @author hiagoalexandre
 * @since 2023-03-29
 */
public class PipelineLancamentoCentralJob implements ScheduledAction {

	@Override
	public void onTime(ScheduledActionContext contexto) {
		contexto.log(Skl.getVersion(this.getClass().getCanonicalName()));

		JobAction action = new JobAction(this.getClass().getCanonicalName());

		PipelineReturn pipelineReturn = new PipelineReturn();
		try {
			action.start();
			action.setupContext(contexto.getJobMetadata().getUid());

			contexto.log("Executando pipeline...");
			pipelineReturn = PipelineLancamentoCentral.doPipeline();

			action.appendMessage(pipelineReturn.getMessage());

		} catch (Exception e) {
			action.error(e);
		} finally {
			
			if (!pipelineReturn.isSuccess()) {
				String email = "suporte@platinacsc.com.br";
				String assunto = "Consolidação não calculada.";
				String mensagem = String.format("A Consolidação do dia %s não foi gerada.",
						TimeUtils.formataDDMMYYYY(TimeUtils.getNow()));

				FilaMensagemUtils.enviaAvisoEmail(email, assunto, mensagem,
						AuthenticationInfo.getCurrent().getUserID());
			}

			try {
				action.stop();
			} catch (TemporizadorNaoIniciadoException e) {
				e.printStackTrace();
			}

			contexto.info(action.getMensagemRetorno());
		}

	}

}
