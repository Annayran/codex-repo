package tech.skala.events;

import java.math.BigDecimal;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;

public class CidadeEvent implements EventoProgramavelJava {
	final private Logger logger = new LoggerFactory(this.getClass().getCanonicalName()).getLogger();

	@Override
	public void beforeInsert(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();
		preencheCodMunFiscal(vo);

		vo.setProperty("NOMECID", vo.asString("NOMECID").toUpperCase());
	}

	private void preencheCodMunFiscal(DynamicVO vo) throws Exception {
		JapeWrapper dao = JapeFactory.dao("Cidade");
		BigDecimal codCidMae = vo.asBigDecimal("AD_CODCIDMAE");

		if (codCidMae != null) {
			DynamicVO cidadeMaeVO = dao.findByPK(codCidMae);
			BigDecimal codMunFis = cidadeMaeVO.asBigDecimal("CODMUNFIS");

			vo.setProperty("CODMUNFIS", codMunFis);

		}
	}

	@Override
	public void beforeUpdate(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();
		preencheCodMunFiscal(vo);

		vo.setProperty("NOMECID", vo.asString("NOMECID").toUpperCase());
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

}
