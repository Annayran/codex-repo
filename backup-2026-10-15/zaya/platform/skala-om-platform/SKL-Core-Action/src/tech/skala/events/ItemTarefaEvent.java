package tech.skala.events;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.ModifingFields;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.comercial.nfse.enuns.SimNao;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.action.modules.wms.endereco.EnderecoArmazenagemProcessor;

public class ItemTarefaEvent implements EventoProgramavelJava {

	private static final Logger LOGGER = new LoggerFactory(ItemTarefaEvent.class.getCanonicalName()).getLogger();
	private String msg;

	@Override
	public void afterDelete(PersistenceEvent event) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterInsert(PersistenceEvent event) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterUpdate(PersistenceEvent event) throws Exception {

	}

	@Override
	public void beforeCommit(TransactionContext event) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeDelete(PersistenceEvent event) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeInsert(PersistenceEvent event) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeUpdate(PersistenceEvent event) throws Exception {

		JapeWrapper tarefaDAO = JapeFactory.dao(DynamicEntityNames.TAREFA);

		DynamicVO newVO = (DynamicVO) event.getVo();
		ModifingFields modifingFields = event.getModifingFields();

		if (modifingFields.isModifing("DHIMPMAPA")) {
			BigDecimal codAreaSep = newVO.asBigDecimal("CODAREASEP");

			// verificacao se eh endereco de pulmao
			if (codAreaSep.longValue() != BigDecimal.valueOf(2).longValue())
				return;

			BigDecimal nuTarefa = newVO.asBigDecimal("NUTAREFA");
			DynamicVO tarefaVO = tarefaDAO.findByPK(nuTarefa);
			BigDecimal codTarefa = tarefaVO.asBigDecimalOrZero("CODTAREFA");
			
			/**
			 * 4 Reabastecimento 
			 * 15 Reabastecimento Corretivo
			 * 32 Reab. Seletivo
			 */
			List<Long> tarefasReabastecimento = Arrays.asList(4L, 15L, 32L);
			
			// verificacao se eh tarefa de 4 - Reabastecimento
			if (!tarefasReabastecimento.contains(codTarefa.longValue()))
				return;

			BigDecimal codEndOrig = newVO.asBigDecimal("CODENDORIGEM");

			try {
				msg = String.format("[NUTAREFA %s][CODEND %s] Inativando Endereço...", nuTarefa, codEndOrig);
				LOGGER.info(msg);

				EnderecoArmazenagemProcessor.ativaInativaEndereco(codEndOrig, SimNao.NAO.getValue());

				msg = String.format("[NUTAREFA %s][CODEND %s] Endereco Inativado.", nuTarefa, codEndOrig);
				LOGGER.info(msg);
			} catch (Exception e) {
				msg = String.format("[NUTAREFA %s][CODEND %s] Não foi possível Inativar Endereço..", nuTarefa,
						codEndOrig);
				LOGGER.info(msg);
			}
		}
	}

}
