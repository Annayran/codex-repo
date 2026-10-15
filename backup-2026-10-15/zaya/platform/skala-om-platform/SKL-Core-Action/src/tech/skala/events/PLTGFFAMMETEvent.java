package tech.skala.events;

import java.sql.Timestamp;

import com.sankhya.util.TimeUtils;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.ModifingFields;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class PLTGFFAMMETEvent implements EventoProgramavelJava {

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

		dataConverter(vo);
	}

	@Override
	public void beforeUpdate(PersistenceEvent event) throws Exception {
		ModifingFields modifingFields = event.getModifingFields();

		DynamicVO vo = (DynamicVO) event.getVo();
		DynamicVO oldVO = (DynamicVO) event.getOldVO();

		if (modifingFields.isModifing("QTDPREV")) {
			String msg = "Não é possível atualizar o campo <b>Qtd. Prevista</b>.";
			msg = String.format("%s%nFavor alterar o campo <b>Qtd. Ajustada</b>.", msg);
			throw new SkalaModelException(msg);
		}

		String oldConfirmado = oldVO.asString("CONFIRMADO");

		if ("S".equalsIgnoreCase(oldConfirmado)) {
			String msg = "Não é possivel alterar a meta após a sua confirmação.";
			throw new SkalaModelException(msg);
		}

	}

	private void dataConverter(DynamicVO vo) {
		Timestamp dtRef = TimeUtils.getInicioPeriodo(vo.asTimestamp("DTREF"), 4);

		vo.setProperty("DTREF", dtRef);
	}

}
