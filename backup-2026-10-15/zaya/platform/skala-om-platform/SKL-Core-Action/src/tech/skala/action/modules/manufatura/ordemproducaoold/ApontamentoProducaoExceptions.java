package tech.skala.action.modules.manufatura.ordemproducaoold;

import java.math.BigDecimal;

import tech.skala.core.commons.exceptions.SkalaModelException;

public class ApontamentoProducaoExceptions {
	private static final String NOTA_PRODUCAO_NAO_GERADA_MESSAGE = "Não foi possível gerar Nota de Produção.";

	private ApontamentoProducaoExceptions() {
		throw new IllegalStateException("Utility class");
	}
	
	public static class NotaProducaoNaoGeradaException extends SkalaModelException {
		private static final long serialVersionUID = 1L;

		private final BigDecimal					nuApo;

		public NotaProducaoNaoGeradaException() {
			super(NOTA_PRODUCAO_NAO_GERADA_MESSAGE);
			this.nuApo = null;
		}
		
		public NotaProducaoNaoGeradaException(BigDecimal nuApo, Throwable initCause) {
			super(NOTA_PRODUCAO_NAO_GERADA_MESSAGE, initCause);
			this.nuApo = nuApo;
		}

		public BigDecimal getNuApo() {
			return nuApo;
		}
		
	}

}
