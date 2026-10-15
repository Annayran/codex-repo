package tech.skala.events;

import java.sql.Timestamp;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.ModifingFields;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;

@Deprecated
public class CentralBeneficioEvent implements EventoProgramavelJava {
	private final Logger logger = new LoggerFactory(this.getClass().getCanonicalName()).getLogger();
	private final JapeWrapper referenciaDAO = JapeFactory.dao("AD_PLVFPREFERENCIAS");
	private String msg;

	@Override
	public void beforeInsert(PersistenceEvent event) throws Exception {
		logger.info(">>> Before Insert");
		realizaConversao(event);

	}

	@Override
	public void beforeUpdate(PersistenceEvent event) throws Exception {
		logger.info(">>> Before Update");

		ModifingFields modifingFields = event.getModifingFields();

		if (modifingFields.isModifing("REFERENCIA")) {
			realizaConversao(event);
		}

	}

	@Override
	public void beforeDelete(PersistenceEvent var1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterInsert(PersistenceEvent var1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterUpdate(PersistenceEvent var1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterDelete(PersistenceEvent var1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeCommit(TransactionContext var1) throws Exception {
		// TODO Auto-generated method stub

	}

	private void realizaConversao(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();

		Timestamp dtRef = vo.asTimestamp("REFERENCIA");

		DynamicVO referenciaVO = referenciaDAO.findOne("this.DTREF = ?", dtRef);
		Timestamp dtApuraIni = referenciaVO.asTimestamp("DTINI");
		Timestamp dtApuraFin = referenciaVO.asTimestamp("DTFIM");

		vo.setProperty("DTINI", dtApuraIni);
		vo.setProperty("DTFIM", dtApuraFin);

		this.msg = String.format("Referencia recebida: %s %n Data inicio %s - Data final %s", dtRef, dtApuraIni,
				dtApuraFin);
		logger.info(msg);

	}

}
