package tech.skala.events;

import java.math.BigDecimal;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.ModifingFields;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;

public class ParceiroEvent implements EventoProgramavelJava {

	private static final Logger LOGGER = new LoggerFactory(ParceiroEvent.class.getCanonicalName()).getLogger();

	@Override
	public void afterDelete(PersistenceEvent event) throws Exception {

	}

	@Override
	public void afterInsert(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();

		BarramentoRegra.validaCamposObrigatoriosParceiro(vo);
	}

	@Override
	public void afterUpdate(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();

		BarramentoRegra.validaCamposObrigatoriosParceiro(vo);
	}

	@Override
	public void beforeCommit(TransactionContext arg0) throws Exception {

	}

	@Override
	public void beforeDelete(PersistenceEvent event) throws Exception {

	}

	@Override
	public void beforeInsert(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();

		vo.setProperty("NOMEPARC", vo.asString("NOMEPARC").toUpperCase());
		vo.setProperty("RAZAOSOCIAL", vo.asString("RAZAOSOCIAL").toUpperCase());

		if ("J".equalsIgnoreCase(vo.asString("TIPPESSOA")))
			BarramentoRegra.validaCamposObrigatoriosParceiro(vo);

		BigDecimal limCred = vo.asBigDecimal("LIMCRED");
		BigDecimal limCredMensal = vo.asBigDecimal("LIMCREDMENSAL");
		String grupoAutor = vo.asString("GRUPOAUTOR");

		if (limCred != null || limCredMensal != null || grupoAutor != null) {
			vo.setProperty("LIMCRED", null);
			vo.setProperty("LIMCREDMENSAL", null);
			vo.setProperty("GRUPOAUTOR", null);
		}

	}

	@Override
	public void beforeUpdate(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();
		ModifingFields modifingFields = event.getModifingFields();
		String fieldList = null;

		String msg = String.format("beforeUpdate CODPARC %s", vo.asBigDecimal("CODPARC"));
		LOGGER.info(msg);

		fieldList = "LIMCRED,LIMCREDMENSAL,GRUPOAUTOR";
		if (modifingFields.isModifingAny(fieldList))
			BarramentoRegra.validaAlteracaoCredito(vo);

//		fieldList = "CODEMPPREF,CODTAB";
//		if (modifingFields.isModifingAny(fieldList))
//			BarramentoRegra.validaCamposObrigatoriosParceiro(vo);

		if (modifingFields.isModifing("NOMEPARC"))
			vo.setProperty("NOMEPARC", vo.asString("NOMEPARC").toUpperCase());

		if (modifingFields.isModifing("RAZAOSOCIAL"))
			vo.setProperty("RAZAOSOCIAL", vo.asString("RAZAOSOCIAL").toUpperCase());

	}

}
