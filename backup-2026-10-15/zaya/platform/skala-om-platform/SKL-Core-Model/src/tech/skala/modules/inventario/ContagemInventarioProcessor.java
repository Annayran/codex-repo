package tech.skala.modules.inventario;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import tech.skala.core.commons.exceptions.SkalaModelException;

/**
 * 
 * @author hiagoalexandre
 * @since 2022-11-04
 */
public class ContagemInventarioProcessor {

	public static void atualizaStatusTarefa(ContagemInventario tarefa, ContagemInventarioStatusEnum status) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					tarefa.setStatus(status.getValue());
					tarefa.persists();
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível atualizar status da tarefa de contagem.", e);
		} finally {
			JapeSession.close(hnd);
		}
	}
}
