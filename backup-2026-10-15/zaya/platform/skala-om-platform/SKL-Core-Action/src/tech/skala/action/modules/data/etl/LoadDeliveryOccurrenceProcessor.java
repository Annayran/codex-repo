package tech.skala.action.modules.data.etl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;
import com.sankhya.util.JdbcUtils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.action.integration.transport.ocorrencianaentrega.ConhecimentoDeTransporte;
import tech.skala.action.integration.transport.ocorrencianaentrega.EntregasDAO;
import tech.skala.action.integration.transport.ocorrencianaentrega.OcorrenciaDeEntrega;
import tech.skala.action.integration.transport.ocorrencianaentrega.OcorrenciaOrigemEnum;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class LoadDeliveryOccurrenceProcessor {

	private final Logger logger = new LoggerFactory(this.getClass().getCanonicalName()).getLogger();

	public void carregarOcorrencias() throws SkalaModelException {

		ResultSet rsNotas = null;
		try {
			rsNotas = LoadDeliveryOccurrenceFinder.buscaNotasNaoIncluidas();

			while (rsNotas.next())
				gravarOcorrencia(rsNotas);

		} catch (Exception e) {
//			SkalaModelException.throwMe("Não foi possível carregar ocorrências.", e);
			e.printStackTrace();
		} finally {
			JdbcUtils.closeResultSet(rsNotas);
		}
	}

	private void gravarOcorrencia(ResultSet rsNotas) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					gravaOcorrencia(rsNotas);
				}

			});

		} catch (Exception e) {
//			SkalaModelException.throwMe("", e);
			e.printStackTrace();
		} finally {
			JapeSession.close(hnd);
		}
	}

	private void gravaOcorrencia(ResultSet rsNotas) throws SkalaModelException {

		ResultSet rsOcorrencias = null;

		BigDecimal codCal;
		BigDecimal nuNota = null;
		BigDecimal nuNotaAnt = BigDecimal.ZERO;

		String chaveCTe;
		String newChave;
		boolean gravarCTe = true;
		ConhecimentoDeTransporte cte = null;
		try {

			nuNota = rsNotas.getBigDecimal(SklFieldName.NUNOTA);
			codCal = rsNotas.getBigDecimal(SklFieldName.CODCAL);

			if (nuNota == null || codCal == null)
				return;

			if (!nuNotaAnt.equals(nuNota)) {
				nuNotaAnt = nuNota;

				if (EntregasDAO.buscaCabecalhoEntregaPorNuNota(nuNota) == null)
					criaCabecalhoEntrega(nuNota, codCal);
			}

			chaveCTe = LoadDeliveryOccurrenceFinder.buscaChaveCTeCallCenter(codCal);

			gravarCTe = !("null".equals(chaveCTe));

			if (gravarCTe && chaveCTe != null) {
				newChave = chaveCTe.replaceAll(" ", "");

				BigDecimal nuNotaCTe = null;
				try {
					nuNotaCTe = LoadDeliveryOccurrenceFinder.buscaNuNotaPelaChaveCTe(newChave);
				} catch (Exception e) {
				} finally {
					if (nuNotaCTe != null) {
						cte = new ConhecimentoDeTransporte();
						cte.setNuNota(nuNota);
						cte.setNuNotaCte(nuNotaCTe);
						cte.persists();
					}
				}

			}

			rsOcorrencias = queOcorrencias(codCal);
			while (rsOcorrencias.next())
				criaOcorrencias(nuNota, rsOcorrencias);

		} catch (Exception e) {
			String msg = String.format(
					"[NUNOTA %s] Não foi possível gravar as Ocorrências na estrutura de Ocorrências de Entrega.",
					nuNota);

			SkalaModelException.throwMe(msg, e);
		} finally {
			JdbcUtils.closeResultSet(rsOcorrencias);
		}

	}

	private ResultSet queOcorrencias(BigDecimal codCal) throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		ResultSet rs = null;
		NativeSql sql = null;

		try {
			hnd = JapeSession.open();

			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);
			sql.loadSql(LoadDeliveryOccurrenceProcessor.class, "LoadDeliveryOccurrence_queOcorrenciaEntrega.sql");
			sql.setNamedParameter(SklFieldName.CODCAL, codCal);
			rs = sql.executeQuery();

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível buscar Ocorrências não incluidas.", e);
		} finally {
//			NativeSql.releaseResources(sql);
//			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

		return rs;

	}

	private void criaOcorrencias(BigDecimal nuNota, ResultSet rsOcorrencias) throws SkalaModelException {

		JapeWrapper ocorrenciaEntregaDAO = JapeFactory.dao("AD_PLTGFENTOCO");
		
		try {
			BigDecimal codOco = rsOcorrencias.getBigDecimal(SklFieldName.CODOCO);
			Timestamp dhOcorrencia = rsOcorrencias.getTimestamp(SklFieldName.DHOCORRENCIA);
			
			DynamicVO ocorrenciaVO = ocorrenciaEntregaDAO.findOne("this.NUNOTA = ? AND this.CODOCO = ? AND this.DHOCORRENCIA = ?", nuNota, codOco,
					dhOcorrencia);
			
			if(ocorrenciaVO != null) {
				OcorrenciaDeEntrega ocorrencia = new OcorrenciaDeEntrega();
				ocorrencia.setNuNota(nuNota);
				ocorrencia.setCodOco(codOco);
				
				ocorrencia.setCodUsu(rsOcorrencias.getBigDecimal(SklFieldName.CODUSU));
				ocorrencia.setCodUsuInc(rsOcorrencias.getBigDecimal("CODUSUINC"));
				ocorrencia.setDhAlter(rsOcorrencias.getTimestamp(SklFieldName.DHALTER));
				ocorrencia.setDhInc(rsOcorrencias.getTimestamp("DHALTER"));
				ocorrencia.setDhOcorrencia(dhOcorrencia);
				ocorrencia.setObservacao(rsOcorrencias.getString(SklFieldName.OBSERVACAO));
				ocorrencia.setOrigem(OcorrenciaOrigemEnum.CALL_CENTER.getValue());
				ocorrencia.setVlrOcorrencia(rsOcorrencias.getBigDecimal("VLROCORRENCIA"));
				
				ocorrencia.persists();
				
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível salvar Ocorrência na Estrutura Nova.", e);
		}
	}

	private void criaCabecalhoEntrega(BigDecimal nuNota, BigDecimal codCal) throws SkalaModelException {
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

}
