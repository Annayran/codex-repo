package tech.skala.events;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.dao.EntityPrimaryKey;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.util.FinderWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.action.modules.comercial.lifecycle.SalesOrderLifeCycleHelper;
import tech.skala.action.modules.comercial.lifecycle.StageLifeCycleEnum;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class PLTGFENTOCOEvent implements EventoProgramavelJava {

	private static final Logger LOGGER = new LoggerFactory(PLTGFENTOCOEvent.class.getCanonicalName()).getLogger();
	private String msg;

	private final static BigDecimal ENTREGA_REALIZADA_NORMALMENTE = BigDecimal.valueOf(2);

	@Override
	public void afterDelete(PersistenceEvent event) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterInsert(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();
		
		atualizaDataAgendamento(vo);
		atualizaDataEntrega(vo);
	}

	@Override
	public void afterUpdate(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();

		atualizaDataAgendamento(vo);
		atualizaDataEntrega(vo);

		BigDecimal codOco = vo.asBigDecimal(SklFieldName.CODOCO);
		msg = String.format("Nova Ocorrencia cadastrada. Codigo da Ocorrencia %s", codOco);
		LOGGER.info(msg);
		if (ENTREGA_REALIZADA_NORMALMENTE.equals(codOco)) {
			msg = "Ocorrencia Cadastrada eh de Entrega Realizada Normalmente";
			LOGGER.info(msg);

			validaEtapaCicloVidaPedido(vo);
		}

	}

	private void atualizaDataAgendamento(DynamicVO vo) throws SkalaModelException {
		List<Integer> ocorrenciasValidas = Arrays.asList(10);

		int codOco = vo.asInt("CODOCO");

		BigDecimal nuNota = vo.asBigDecimal(SklFieldName.NUNOTA);
		JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);
		if (ocorrenciasValidas.contains(codOco)) {

			try {
				DynamicVO cabVO = dao.findByPK(nuNota);
				Timestamp dhAgendamento = cabVO.asTimestamp("AD_DHAGENDAMENTO");
				
				if(dhAgendamento == null) {
					EntityUpdate entityUpdate = new EntityUpdate(DynamicEntityNames.CABECALHO_NOTA, cabVO);
					entityUpdate.setField("AD_DHAGENDAMENTO", vo.asTimestamp(SklFieldName.DHOCORRENCIA));
					entityUpdate.fluidUpdate();
				}

			} catch (Exception e) {
				SkalaModelException.throwMe("Não foi possível atualizar data de entrega da NF", e);
			}

		}
		
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
		DynamicVO vo = (DynamicVO) event.getVo();

		BigDecimal codOco = vo.asBigDecimal(SklFieldName.CODOCO);

		msg = String.format("Nova Ocorrencia cadastrada. Codigo da Ocorrencia %s", codOco);
		LOGGER.info(msg);
		if (ENTREGA_REALIZADA_NORMALMENTE.equals(codOco)) {
			msg = "Ocorrencia Cadastrada eh de Entrega Realizada Normalmente";
			LOGGER.info(msg);

			validaEtapaCicloVidaPedido(vo);
		}

	}

	@Override
	public void beforeUpdate(PersistenceEvent event) throws Exception {
		// TODO Auto-generated method stub

	}

	private void validaEtapaCicloVidaPedido(DynamicVO vo) throws SkalaModelException {
		BigDecimal nuNota = vo.asBigDecimal(SklFieldName.NUNOTA);

		if (nuNota == null)
			return;

		DynamicVO cabVO;
		DynamicVO cicloVidaVO;
		try {
			cabVO = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA).findByPK(nuNota);

			BigDecimal nuNotaPV = cabVO.asBigDecimal("AD_NROUNICOPED");
			Timestamp dhInc = vo.asTimestamp(SklFieldName.DHOCORRENCIA);

			if (nuNotaPV == null)
				return;

			cicloVidaVO = JapeFactory.dao("AD_PLTGFCIV").findByPK(nuNotaPV);

			if (cicloVidaVO != null) {
				SalesOrderLifeCycleHelper helper = new SalesOrderLifeCycleHelper();
				helper.buildItems(nuNotaPV, StageLifeCycleEnum.ENTREGA_REALIZADA.getValue(), dhInc, nuNota);

				msg = String.format("[NUNOTA %s]Etapa 5 Ciclo de Vida do Pedido, cadastrado com sucesso", nuNota);
				LOGGER.info(msg);
			}

		} catch (Exception e) {
			msg = String.format("[NUNOTA %s]Não foi possível gravar a ultima etapa do Ciclo de Vida do Pedido.",
					nuNota);
			SkalaModelException.throwMe(msg, e);
		}

	}

	private void atualizaDataEntrega(DynamicVO vo) throws SkalaModelException {

		List<Integer> ocorrenciasValidas = Arrays.asList(2, 30);

		int codOco = vo.asInt("CODOCO");

		BigDecimal nuNota = vo.asBigDecimal(SklFieldName.NUNOTA);
		if (ocorrenciasValidas.contains(codOco)) {

			try {
				EntityUpdate entityUpdate = new EntityUpdate(DynamicEntityNames.CABECALHO_NOTA,
						new EntityPrimaryKey(new Object[] { nuNota }));
				entityUpdate.setField("AD_DTENTEFETIVA", vo.asTimestamp(SklFieldName.DHOCORRENCIA));

				entityUpdate.fluidUpdate();
			} catch (UpdateNotPossibleException e) {
				SkalaModelException.throwMe("Não foi possível atualizar data de entrega da NF", e);
			}

		}
	}

}
