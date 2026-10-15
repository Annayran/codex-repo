package tech.skala.modules.suprimentos.recebimento;

import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.i18n.I18nMessageFactory;

/**
 * A classe {@code RecebimentoMercadoriaExceptions} contem as diversas excecoes usadas 
 * no pacote {@link tech.skala.modules.suprimentos.recebimento}.
 * 
 * <p>
 * Todas as excecoes deverao ser metodos estaticos (<i>static</i>) e deverao extender a 
 * classe {@link SkalaModelException} utilizando as mensagens em um pacote de internacionalizacao.
 * </p>
 * 
 * @author cassiomenezes
 * 
 * @see SkalaModelException
 * @since 2022-07-14
 *
 */
public class RecebimentoMercadoriaExceptions {
	private static final String				EXCEPTION_BASE_NAME = "tech.skala.modules.suprimentos.recebimento.Messages";

	private RecebimentoMercadoriaExceptions() {
		throw new IllegalStateException("Utility class");
	}
	
	public static class ChaveNfeNaoEncontradaException extends SkalaModelException {

		private static final long serialVersionUID = 2706063089610979592L;
		
		public ChaveNfeNaoEncontradaException(Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("invoice-not-found"), args));
		}

		public ChaveNfeNaoEncontradaException(Throwable error, Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("invoice-not-found"), args), error);
		}

	}

	public static class NotaNaoEncontradaException extends SkalaModelException {

		private static final long serialVersionUID = 3619225159453530937L;

		public NotaNaoEncontradaException(Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("invoice-not-found-cnpj"), args));
		}

		public NotaNaoEncontradaException(Throwable error, Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("invoice-not-found-cnpj"), args), error);
		}

	}
	
	public static class ChaveNfeDuplicidadeException extends SkalaModelException {

		private static final long serialVersionUID = -682480647273178525L;
		
		public ChaveNfeDuplicidadeException() {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("invoice-key-duplicated"));
		}

		public ChaveNfeDuplicidadeException(Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("invoice-key-duplicated"), args));
		}
		
	}
	
	public static class NumNotaCnpjDuplicidadeException extends SkalaModelException {

		private static final long serialVersionUID = 8697448775989824305L;

		public NumNotaCnpjDuplicidadeException() {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("invoice-keys-duplicated"));
		}

		public NumNotaCnpjDuplicidadeException(Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("invoice-keys-duplicated"), args));
		}
		
	}

	public static class RecebimentoDuplicadoException extends SkalaModelException {

		private static final long serialVersionUID = -5259952343085781069L;

		public RecebimentoDuplicadoException(Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("receiving-duplicated"), args));
		}

	}
	
	public static class RecebimentoNaoEncontradoException extends SkalaModelException {

		private static final long serialVersionUID = 6862504161140152435L;
		
		public RecebimentoNaoEncontradoException() {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("receiving-not-found"));
		}
		
	}

	public static class RecebimentoNaoCadastradoException extends SkalaModelException {

		private static final long serialVersionUID = 6862504161140152435L;
		
		public RecebimentoNaoCadastradoException(Throwable error) {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("receiving-not-inserted"), error);
		}
		
	}
	
	public static class RecebimentoNaoFinalizadoException extends SkalaModelException {

		private static final long serialVersionUID = -2837417806200106918L;

		public RecebimentoNaoFinalizadoException(Throwable error) {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("receiving-not-completed"), error);
		}
		
	}

	public static class LancamentoSemItensException extends SkalaModelException {

		private static final long serialVersionUID = 8138460916820574074L;
		
		public LancamentoSemItensException(Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("itens-not-found"), args));
		}

		public LancamentoSemItensException(Throwable error, Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("itens-not-found"), args), error);
		}

	}

	public static class DataEntradaSaidaNaoAtualizadaException extends SkalaModelException {

		private static final long serialVersionUID = 2100223467431330517L;

		public DataEntradaSaidaNaoAtualizadaException(Throwable error) {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("update-date-failed"), error);
		}
		
	}
	
	public static class NotaNaoConfirmadaException extends SkalaModelException {

		private static final long serialVersionUID = -8939178000460632343L;

		public NotaNaoConfirmadaException(Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("invoice-not-found-confirmed"), args));
		}

		public NotaNaoConfirmadaException(Throwable error, Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("invoice-not-confirmed"), args), error);
		}

	}
	
	public static class ConferenciaNaoProcessadaException extends SkalaModelException {

		private static final long serialVersionUID = -2275266016104348111L;

		public ConferenciaNaoProcessadaException(Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("conference-not-processed"), args));
		}

		public ConferenciaNaoProcessadaException(Throwable error, Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("conference-not-processed"), args), error);
		}

	}

}
