package tech.skala.modules.logistica.commons;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

/**
 * A classe {@code OcorrenciaTransportadoraFinder} prove recursos para busca de
 * informacoes relacionadas a uma {@link Ocorrencia}.
 * 
 * <p>
 * A classe {@code OcorrenciaTransportadoraFinder} prove metodos diversos
 * especializados em localizar uma {@link Ocorrencia} (ou varias) segundo os
 * mais diversos criterios, sendo uma classe especializada para este fim.
 * </p>
 * 
 * @author hiagoalexandre
 * @since 2022-11-24
 *
 */
public class OcorrenciaTransportadoraFinder {

	private OcorrenciaTransportadoraFinder() {

	}

	public static BigDecimal buscaOcorrenciaPlatina(BigDecimal codOcoTransp, BigDecimal codParcTransp)
			throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao("AD_PLTGFITOCO");

		BigDecimal codOcoPlatina = null;
		DynamicVO vo = null;
		try {
			vo = dao.findByPK(codParcTransp, codOcoTransp);

			if (vo != null)
				codOcoPlatina = vo.asBigDecimal(SklFieldName.CODOCO);

		} catch (Exception e) {
			String msg = String.format("[CODOCOTRANSP %s CODPARCTRANSP %s] Ocorrência não mapeada.", codOcoTransp,
					codParcTransp);
			SkalaModelException.throwMe(msg, e);
		}

		return codOcoPlatina;
	}

}
