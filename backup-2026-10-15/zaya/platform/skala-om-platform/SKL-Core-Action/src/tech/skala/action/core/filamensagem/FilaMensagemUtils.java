package tech.skala.action.core.filamensagem;

import java.math.BigDecimal;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class FilaMensagemUtils {
	
	private FilaMensagemUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static void enviaAvisoEmail(String email, String assunto, String mensagem, BigDecimal codUsu) {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.getCurrentSession().getTopMostHandle();
			hnd.setCanTimeout(true);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					enviarAvisoEmail(email, assunto, mensagem, codUsu);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			hnd = null;
		}

	}

	public static void enviarAvisoEmail(String email, String assunto, String mensagem, BigDecimal codUsu) throws SkalaModelException {
		FilaMensagem filaMensagem = new FilaMensagem(mensagem, assunto, email, codUsu);
		filaMensagem.persists();
	}
	
}
