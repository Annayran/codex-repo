package tech.skala.modules.logistica.ocorrenciaentrega;

import java.math.BigDecimal;

import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class OcorrenciaDeEntregaProcessor {

	public Entregas buildEntregaOcorrencia(DynamicVO nfeVO) throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao("AD_PLTGFENT");
		Entregas entrega = new Entregas();

		DynamicVO vo = null;
		try {
			vo = dao.findByPK(nfeVO.asBigDecimal(SklFieldName.NUNOTA));

			if (vo == null) {
				entrega.setNuNota(nfeVO.asBigDecimal(SklFieldName.NUNOTA));
				entrega.setCodEmp(nfeVO.asBigDecimal(SklFieldName.CODEMP));
				entrega.setOrdemCarga(nfeVO.asBigDecimal(SklFieldName.ORDEMCARGA));
				// calcular Data Limite de Entrega
				// entrega.setDtLimiteEntrega(null);

				entrega.persists();
			} else {
				entrega.loadByVO(vo);
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível buscar Ocorrência de Entrega.", e);
		}

		return entrega;
	}

	public void gravaConhecimentoTransporte(Entregas entrega, BigDecimal nuNotaCTe) throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao("AD_PLTGFENTCTE");

		DynamicVO vo = null;
		ConhecimentoDeTransporte cte = null;
		try {
			vo = dao.findByPK(entrega.getNuNota(), nuNotaCTe);

			if (vo == null) {
				cte = new ConhecimentoDeTransporte();

				cte.setNuNota(entrega.getNuNota());
				cte.setNuNotaCte(nuNotaCTe);

				cte.persists();
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível buscar Conhecimento de Transporte.", e);
		}

	}

	public void geraOcorrenciaEntrega(Entregas entrega) throws SkalaModelException {

		try {
			BigDecimal codOcoPlatina;
			OcorrenciaDeEntrega ocorrenciaDeEntrega = new OcorrenciaDeEntrega();

			ocorrenciaDeEntrega.setNuNota(entrega.getNuNota());
			codOcoPlatina = BigDecimal.ONE;

			ocorrenciaDeEntrega.setCodOco(codOcoPlatina);
			ocorrenciaDeEntrega.setDhOcorrencia(TimeUtils.getNow());
			if (!entrega.getOcorrencias().contains(ocorrenciaDeEntrega)) {
				ocorrenciaDeEntrega.setOrigem(OcorrenciaOrigemEnum.INTEGRACAO.getValue());
				ocorrenciaDeEntrega.persists();

				entrega.getOcorrencias().add(ocorrenciaDeEntrega);
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível gerar Ocorrência na Entrega.", e);
		}

	}

}
