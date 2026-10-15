package tech.skala.events;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;
import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.com.sankhya.modelcore.util.DynamicEntityNames;

public class ArquivoModuloEvent implements EventoProgramavelJava {
	final private Logger logger = new LoggerFactory(this.getClass().getCanonicalName()).getLogger();

	final private JapeWrapper acoesAgendadasDAO = JapeFactory.dao(DynamicEntityNames.ACAO_AGENDADA);
	final static private String INATIVO = "N";
	final static private String ATIVO = "S";

	private String msg;

	@Override
	public void afterDelete(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterInsert(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();

		logger.info("beforeDelete");
		ativaAcoesAgendadas(vo);

	}

	@Override
	public void afterUpdate(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeCommit(TransactionContext event) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeDelete(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();

		logger.info("beforeDelete");
		inativaAcoesAgendadas(vo);

	}

	private void inativaAcoesAgendadas(DynamicVO vo) {
		BigDecimal codModulo = BigDecimalUtil.getBigDecimal(vo.asBigDecimal("CODMODULO"));

		Collection<DynamicVO> acoesAgendadas = new ArrayList<>();
		try {
			acoesAgendadas = acoesAgendadasDAO.find("this.CODMODULO = ?", codModulo);
		} catch (Exception e) {
			msg = String.format("Não foi possível buscar as Ações agendadas que utilizam o Módulo %s", codModulo);

			logger.severe(msg);
		}

		if (acoesAgendadas.isEmpty()) {
			msg = String.format("Nenhuma Ação Agendada utiliza o Módulo %s", codModulo);

			logger.severe(msg);
			return;
		}
		for (DynamicVO acaoAgendada : acoesAgendadas) {
			inativarAcoesAgendadas(acaoAgendada);
		}
	}

	private void inativarAcoesAgendadas(DynamicVO vo) {
		try {
			FluidUpdateVO updVO = acoesAgendadasDAO.prepareToUpdate(vo);
			updVO.set("ATIVO", INATIVO);
			updVO.update();
		} catch (Exception e) {
			msg = String.format("Não foi possível desativar a Ação Agendada %s-%s", vo.asBigDecimal("NUAAG"),
					vo.asString("DESCRICAO"));
		}
	}

	private void ativaAcoesAgendadas(DynamicVO vo) {
		BigDecimal codModulo = BigDecimalUtil.getBigDecimal(vo.asBigDecimal("CODMODULO"));

		Collection<DynamicVO> acoesAgendadas = new ArrayList<>();
		try {
			acoesAgendadas = acoesAgendadasDAO.find("this.CODMODULO = ?", codModulo);
		} catch (Exception e) {
			msg = String.format("Não foi possível buscar as Ações agendadas que utilizam o Módulo %s", codModulo);

			logger.severe(msg);
		}

		if (acoesAgendadas.isEmpty()) {
			msg = String.format("Nenhuma Ação Agendada utiliza o Módulo %s", codModulo);

			logger.severe(msg);
			return;
		}
		for (DynamicVO acaoAgendada : acoesAgendadas) {
			ativarAcoesAgendadas(acaoAgendada);
		}

	}

	private void ativarAcoesAgendadas(DynamicVO vo) {
		try {
			FluidUpdateVO updVO = acoesAgendadasDAO.prepareToUpdate(vo);
			updVO.set("ATIVO", ATIVO);
			updVO.update();
		} catch (Exception e) {
			msg = String.format("Não foi possível ativar a Ação Agendada %s-%s", vo.asBigDecimal("NUAAG"),
					vo.asString("DESCRICAO"));
		}
	}

	@Override
	public void beforeInsert(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeUpdate(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

}
