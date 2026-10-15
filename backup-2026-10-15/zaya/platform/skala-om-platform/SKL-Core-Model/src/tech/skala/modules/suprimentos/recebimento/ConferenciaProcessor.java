package tech.skala.modules.suprimentos.recebimento;

import java.math.BigDecimal;

import com.sankhya.util.TimeUtils;

import tech.skala.core.beans.suprimento.recebimento.Conferencia;
import tech.skala.core.beans.suprimento.recebimento.ConferenciaItem;
import tech.skala.core.beans.suprimento.recebimento.ConferenciaSituacaoEnum;
import tech.skala.core.beans.suprimento.recebimento.Recebimento;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;

public class ConferenciaProcessor {
	
	public Conferencia cadastrarConferencia(final BigDecimal nuNota, final BigDecimal codParc, Recebimento recebimento)
			throws InsertNotPossibleException, UpdateNotPossibleException {
		
		Conferencia conferencia = new Conferencia();
		conferencia.setNuNota(nuNota);
		conferencia.setCodParc(codParc);
		conferencia.setSituacao(ConferenciaSituacaoEnum.EM_ANDAMENTO.getValue());
		conferencia.setDhInicioConf(TimeUtils.getNow());
		conferencia.setTipConf(TipoConferenciaEnum.RECEBIMENTO.getValue());
		conferencia.persists();

		recebimento.setConferencia(conferencia);

		return conferencia;
	}

	public boolean verificarDivergencia(Conferencia conferencia) {
		boolean temDivergencia = false;
		double qtdUnidPad;
		double qtdConf;
		
		for (ConferenciaItem conferenciaItem : conferencia.getItens()) {
			qtdUnidPad = conferenciaItem.getQtdUnidPad().doubleValue();
			qtdConf = conferenciaItem.getQtdConf().doubleValue();
			
			temDivergencia = qtdUnidPad != qtdConf;
			if (temDivergencia)
				break;
		}

		return temDivergencia;
	}	
	
	
	
}
