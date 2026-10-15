package tech.skala.action.integration.transport.ocorrencianaentrega;

import java.math.BigDecimal;
import java.util.Collection;

import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.events.BarramentoRegra;

public class OcorrenciaDeEntregaProcessor {

	public Entregas buildEntregaOcorrencia(DynamicVO nfeVO) throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao("AD_PLTGFENT");
		Entregas entrega = null;

		DynamicVO vo = null;
		try {
			BigDecimal nuNota = nfeVO.asBigDecimal(SklFieldName.NUNOTA);
			vo = dao.findByPK(nuNota);

			if (vo != null) {
				entrega = new Entregas();
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

		JapeWrapper ocorrenciaDAO = JapeFactory.dao("AD_PLTGFENTOCO");
		try {

			Collection<DynamicVO> ocorrenciaAcompanhamentoEntregaVO = ocorrenciaDAO
					.find("this.NUNOTA = ? AND this.CODOCO = 1", entrega.getNuNota());

			if (ocorrenciaAcompanhamentoEntregaVO.isEmpty()) {
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
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível gerar Ocorrência na Entrega.", e);
		}

	}

	public void criaAcompanhamentoDeEntrega(BigDecimal nuNota) throws SkalaModelException {
		SessionHandle hnd = null;
		
		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);
			
			JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);
		 	DynamicVO cabecalhoNotaVO = dao.findByPK(nuNota);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					BarramentoRegra.criaAcompanhamentoDeEntrega(cabecalhoNotaVO);
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível criar Acompanhamento de Entrega", e);
		} finally {
			JapeSession.close(hnd);
		}
	}
}
