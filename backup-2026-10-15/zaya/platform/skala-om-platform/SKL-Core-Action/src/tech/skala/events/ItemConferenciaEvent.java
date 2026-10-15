package tech.skala.events;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;

/**
 * O evento {@code ItemConferenciaEvent} faz o tratamento de dados e implementa
 * regras de negócio.
 * <p>
 * 
 * O evento {@code ItemConferenciaEvent} faz o tratamento de dados e implementa
 * regras de negócio específicas para a instância
 * {@code TGWCOI - ItemConferencia}.
 * <p>
 * 
 * @author cassiomenezes
 * @since 2020-05-22
 * 
 */
public class ItemConferenciaEvent implements EventoProgramavelJava {

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

		BigDecimal qtdeConf = BigDecimalUtil.getRounded(vo.asBigDecimal("QTDECONF"), 0);
		vo.setProperty("QTDECONF", qtdeConf);
	}

	@Override
	public void beforeUpdate(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

}
