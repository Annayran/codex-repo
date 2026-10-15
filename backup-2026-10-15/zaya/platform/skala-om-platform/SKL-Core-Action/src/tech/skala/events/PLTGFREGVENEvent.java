package tech.skala.events;

import java.sql.Timestamp;

import com.sankhya.util.TimeUtils;
import com.sankhya.util.TimeUtils.TipoPeriodo;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class PLTGFREGVENEvent implements EventoProgramavelJava {

	@Override
	public void afterDelete(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterInsert(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterUpdate(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeCommit(TransactionContext arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeDelete(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeInsert(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();

		Timestamp dtVigor = vo.asTimestamp("DTVIGOR");

		if (dtVigor != null)
			vo.setProperty("DTVIGOR", getReferencia(dtVigor));
	}

	@Override
	public void beforeUpdate(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();

		if (event.getModifingFields().isModifing("DTVIGOR")) {
			Timestamp dtVigor = vo.asTimestamp("DTVIGOR");
			if (dtVigor != null)
				vo.setProperty("DTVIGOR", getReferencia(dtVigor));
		}

	}

	private Timestamp getReferencia(Timestamp data) throws SkalaModelException {
		final long DTREF_MAIOR_DTREF_ATUAL = 1;

		Timestamp dtRef = TimeUtils.getInicioPeriodo(data, TipoPeriodo.MES);
		Timestamp dtRefAtual = TimeUtils.getInicioPeriodo(TimeUtils.getNow(), TipoPeriodo.MES);

		if (dtRef.compareTo(dtRefAtual) != DTREF_MAIOR_DTREF_ATUAL) {
			String msg = String.format("DTVIGOR[%s] Data Vigor não pode ser menor ou igual a referência atual.",
					TimeUtils.formataDDMMYYYY(dtRef));
			SkalaModelException.throwMe(msg, null);
		}

		return dtRef;
	}

}
