package tech.skala.core.beans.cabecalhonota;

import java.math.BigDecimal;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.modelcore.PlatformService;
import br.com.sankhya.modelcore.PlatformServiceFactory;
import br.com.sankhya.modelcore.comercial.AtributosRegras;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

/**
 * 
 * @author hiagoalexandre
 * @since 2022-09-02
 */
public class CabecalhoNotaProcessor {

	/**
	 * Este método {@code confirmarNota} como finalidade realizar a confirmação de
	 * uma nota.
	 * 
	 * <p>
	 * <b>Importante: <br> <br>
	 * Este método abre uma transação própria.<br>
	 * deve-se chama-lo fora de uma transação já aberta.</b>
	 * </p>
	 * 
	 * @param nuNota que é o identificador único da tabela {@code TGFCAB}.
	 * @throws SkalaModelException dispara um erro, caso ocorra um problema durante
	 *                             a confirmação da Nota.
	 */
	public static void confirmarNota(BigDecimal nuNota) throws SkalaModelException {

		JapeSession.putProperty(AtributosRegras.CONFIRMACAO_DIRETA_NOTA, Boolean.TRUE);
		try {
			PlatformService confirmacaoNotaService = PlatformServiceFactory.getInstance()
					.lookupService("@core:confirmacao.nota.service");

			confirmacaoNotaService.set(SklFieldName.NUNOTA, nuNota);
			confirmacaoNotaService.execute();
		} catch (Exception e) {
			String msg = String.format("[NUNOTA %s] Não foi possível confirmar a Nota.", nuNota);
			SkalaModelException.throwMe(msg, e);
		}
	}
}
