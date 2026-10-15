package tech.skala.events;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.sankhya.util.TimeUtils;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.util.FinderWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.action.integration.transport.ocorrencianaentrega.EntregasDAO;
import tech.skala.action.integration.transport.ocorrencianaentrega.OcorrenciaOrigemEnum;
import tech.skala.action.modules.data.etl.LoadDeliveryOccurrenceProcessor;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class TMSCALOCOEvent implements EventoProgramavelJava {

	@Override
	public void afterDelete(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();

		vo.setProperty("DHOCORRENCIA", null);
		atualizaDataEntrega(vo);
//		deleteOcorrenciaEntrega(vo);
	}

	private void deleteOcorrenciaEntrega(DynamicVO vo) throws Exception {

		BigDecimal codOco = vo.asBigDecimal(SklFieldName.CODOCO);
		BigDecimal codCal = vo.asBigDecimal(SklFieldName.CODCAL);
		Timestamp dhOcorrencia = vo.asTimestamp(SklFieldName.DHOCORRENCIA);

		JapeWrapper callCenterNotaDAO = JapeFactory.dao(SklEntityName.NOTA_CALL_CENTER);
		Collection<DynamicVO> notasVO = callCenterNotaDAO.find("this.CODCAL = ?", codCal);

		JapeWrapper ocorrenciaEntregaDAO = JapeFactory.dao("AD_PLTGFENTOCO");
		BigDecimal nuNota;
		for (DynamicVO notaVO : notasVO) {
			nuNota = notaVO.asBigDecimal(SklFieldName.NUNOTA);

			if (nuNota != null && codOco != null) {
				DynamicVO ocorrenciaVO = null;

				if (dhOcorrencia != null) {
					ocorrenciaVO = ocorrenciaEntregaDAO.findOne(
							"this.NUNOTA = ? AND this.CODOCO = ? AND this.DHOCORRENCIA = ?", nuNota, codOco,
							dhOcorrencia);
				} else {
					ocorrenciaVO = ocorrenciaEntregaDAO.findOne("this.NUNOTA = ? AND this.CODOCO = ?", nuNota, codOco);
				}
				if (ocorrenciaVO != null)
					ocorrenciaEntregaDAO.delete("this.NUNOTA = ? AND this.CODOCO = ? AND this.DHOCORRENCIA = ?", nuNota,
							codOco, dhOcorrencia);
			}

		}

	}

	@Override
	public void afterInsert(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();
		atualizaDataEntrega(vo);

//		insertOcorrenciaEntrega(vo);
	}

	private void insertOcorrenciaEntrega(DynamicVO vo) throws Exception {

		BigDecimal codCal = vo.asBigDecimal(SklFieldName.CODCAL);
		Timestamp dhOcorrencia = vo.asTimestamp(SklFieldName.DHOCORRENCIA);
		BigDecimal codOco = vo.asBigDecimal(SklFieldName.CODOCO);
		String observacao = vo.asString(SklFieldName.OBSERVACAO);
		BigDecimal vlrOcorrencia = vo.asBigDecimal("VLROCORRENCIA");
		BigDecimal codUsu = vo.asBigDecimal(SklFieldName.CODUSU);
		Timestamp dhAlter = vo.asTimestamp(SklFieldName.DHALTER);
		Timestamp dhInc = vo.asTimestamp("DHINCLUSAO");

		JapeWrapper callCenterNotaDAO = JapeFactory.dao(SklEntityName.NOTA_CALL_CENTER);

		if (codCal != null) {
			Collection<DynamicVO> notasVO = callCenterNotaDAO.find("this.CODCAL = ?", codCal);

			BigDecimal nuNota;
			EntityInsert entityNew;
			JapeWrapper ocorrenciaEntregaDAO = JapeFactory.dao("AD_PLTGFENTOCO");
			for (DynamicVO notaVO : notasVO) {
				nuNota = notaVO.asBigDecimal(SklFieldName.NUNOTA);

				if (EntregasDAO.buscaCabecalhoEntregaPorNuNota(nuNota) == null) {
					criaCabecalhoEntrega(codCal, nuNota);
				}

				DynamicVO ocorrenciaVO = ocorrenciaEntregaDAO.findOne(
						"this.NUNOTA = ? AND this.CODOCO = ? AND this.DHOCORRENCIA = ?", nuNota, codOco,
						dhOcorrencia == null ? TimeUtils.getNow() : dhOcorrencia);

				if (ocorrenciaVO != null) {
					entityNew = new EntityInsert("AD_PLTGFENTOCO");
					entityNew.setField(SklFieldName.NUNOTA, nuNota);
					entityNew.setField(SklFieldName.CODOCO, codOco);
					entityNew.setField(SklFieldName.DHOCORRENCIA, dhOcorrencia);
					entityNew.setField("VLROCORRENCIA", vlrOcorrencia);
					entityNew.setField(SklFieldName.OBSERVACAO, observacao);
					entityNew.setField("ORIGEM", OcorrenciaOrigemEnum.CALL_CENTER.getValue());

					if (dhInc != null && codUsu != null && dhAlter != null) {
						entityNew.setField(SklFieldName.CODUSU, codUsu);
						entityNew.setField(SklFieldName.DHALTER, dhAlter);
						entityNew.setField("DHINC", dhInc);
						entityNew.setField("CODUSUINC", codUsu);
					}

					entityNew.fluidInsert();
				} else {
					EntityUpdate entityUpdate = new EntityUpdate("AD_PLTGFENTOCO", ocorrenciaVO);
					entityUpdate.setField(SklFieldName.NUNOTA, nuNota);
					entityUpdate.setField(SklFieldName.CODOCO, codOco);
					entityUpdate.setField(SklFieldName.DHOCORRENCIA, dhOcorrencia);
					entityUpdate.setField("VLROCORRENCIA", vlrOcorrencia);
					entityUpdate.setField(SklFieldName.OBSERVACAO, observacao);

					if (dhInc != null && codUsu != null && dhAlter != null) {
						entityUpdate.setField(SklFieldName.CODUSU, codUsu);
						entityUpdate.setField(SklFieldName.DHALTER, dhAlter);
						entityUpdate.setField("DHINC", dhInc);
						entityUpdate.setField("CODUSUINC", codUsu);
					}

					entityUpdate.fluidUpdate();
				}

			}

		}

	}

	private void criaCabecalhoEntrega(BigDecimal codCal, BigDecimal nuNota) throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		NativeSql sql = null;

		try {
			hnd = JapeSession.open();

			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);
			sql.loadSql(LoadDeliveryOccurrenceProcessor.class, "LoadDeliveryOccurrence_insCabecalhoEntrega.sql");
			sql.setNamedParameter(SklFieldName.CODCAL, codCal);
			sql.setNamedParameter(SklFieldName.NUNOTA, nuNota);
			sql.executeUpdate();

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível incluir cabecalho.", e);
		} finally {
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

	}

	@Override
	public void afterUpdate(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();
		atualizaDataEntrega(vo);
//		insertOcorrenciaEntrega(vo);
	}

	@Override
	public void beforeCommit(TransactionContext event) throws Exception {

	}

	@Override
	public void beforeDelete(PersistenceEvent event) throws Exception {

	}

	@Override
	public void beforeInsert(PersistenceEvent event) throws Exception {

	}

	@Override
	public void beforeUpdate(PersistenceEvent event) throws Exception {

	}

	private void atualizaDataEntrega(DynamicVO vo) throws Exception {

		List<Integer> ocorrenciasValidas = Arrays.asList(2, 30);

		int codOco = vo.asInt("CODOCO");

		if (ocorrenciasValidas.contains(codOco)) {
			JdbcWrapper jdbc = null;

			try {
				EntityFacade dwfEntityFacade = EntityFacadeFactory.getDWFFacade();
				jdbc = dwfEntityFacade.getJdbcWrapper();
				jdbc.openSession();

				Collection<DynamicVO> notas = dwfEntityFacade.findByDynamicFinderAsVO(new FinderWrapper("AD_TMSCALNOTA",
						"this.CODCAL = ?", new Object[] { vo.asBigDecimal("CODCAL") }));

				for (DynamicVO notaCallVO : notas) {
					NativeSql sql = new NativeSql(jdbc);
					sql.appendSql(" UPDATE TGFCAB SET AD_DTENTEFETIVA = :DTENTEFETIVA ");
					sql.appendSql(" WHERE NUNOTA = :NUNOTA ");

					sql.setNamedParameter("DTENTEFETIVA", vo.asTimestamp("DHOCORRENCIA"));
					sql.setNamedParameter("NUNOTA", notaCallVO.asBigDecimal("NUNOTA"));

					sql.executeUpdate();
				}
			} finally {
				JdbcWrapper.closeSession(jdbc);
			}
		}
	}

}
