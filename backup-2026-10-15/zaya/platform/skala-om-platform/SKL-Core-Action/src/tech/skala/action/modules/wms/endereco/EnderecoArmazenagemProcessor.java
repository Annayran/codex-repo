package tech.skala.action.modules.wms.endereco;

import java.math.BigDecimal;
import java.util.logging.Logger;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class EnderecoArmazenagemProcessor {
	private static final Logger LOGGER = Logger.getLogger(EnderecoArmazenagemProcessor.class.getCanonicalName());

	private EnderecoArmazenagemProcessor() {
		throw new IllegalStateException("Utility class");
	}

	public static void ativaInativaEndereco(BigDecimal codEnd, String ativo) throws SkalaModelException {
		EnderecoArmazenagem enderecoArmazenagem = new EnderecoArmazenagem();
		enderecoArmazenagem.setCodEnd(codEnd);
		enderecoArmazenagem.setAtivo(ativo);
		
		ativarInativarEndereco(enderecoArmazenagem);
	}

	private static void ativarInativarEndereco(EnderecoArmazenagem enderecoArmazenagem) throws SkalaModelException {
		LOGGER.info(enderecoArmazenagem.toString());

		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					enderecoArmazenagem.persists();
				}

			});
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível ativar o Endereço de Armazenagem.", e);
		} finally {
			JapeSession.close(hnd);
		}
	}
}
