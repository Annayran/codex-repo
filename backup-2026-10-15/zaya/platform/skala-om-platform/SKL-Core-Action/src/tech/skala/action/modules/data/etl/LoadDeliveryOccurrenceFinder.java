package tech.skala.action.modules.data.etl;

import java.math.BigDecimal;
import java.sql.ResultSet;

import br.com.sankhya.jape.core.Jape;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class LoadDeliveryOccurrenceFinder {

	private LoadDeliveryOccurrenceFinder() {
		throw new IllegalStateException("Utility class");
	}
	
	public static ResultSet buscaNotasNaoIncluidas() throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		ResultSet rs = null;
		NativeSql sql = null;

		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			hnd = JapeSession.open();
			sql = new NativeSql(jdbc);
			
			sql.loadSql(LoadDeliveryOccurrenceProcessor.class, "LoadDeliveryOccurrence_queNotasNaoIncluidas.sql");
			rs = sql.executeQuery();

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível buscar Notas não incluidas.", e);
		} finally {
			JapeSession.close(hnd);
		}

		return rs;

	}
	
	public static String buscaChaveCTeCallCenter(BigDecimal codCal) throws SkalaModelException {
		String chaveCTe = "0";
		
		if(codCal == null)
			return chaveCTe;
		
		try {
			chaveCTe = NativeSql.getString("CHAVECTE", "AD_TMSCAL", "CHAVECTE IS NOT NULL AND CODCAL = ?",
					new Object[] { codCal });
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar ChaveCTe", e);
		}
		
		return chaveCTe;
	}
	
	public static BigDecimal buscaNuNotaPelaChaveCTe(String chaveCTe) throws SkalaModelException {
		BigDecimal nuNota = null;
		
		try {
			nuNota = NativeSql.getBigDecimal(SklFieldName.NUNOTA, "TGFCAB", "TO_NUMBER(CHAVECTE) = TO_NUMBER(?)",
					new Object[] { chaveCTe });
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar ChaveCTe", e);
		}
		
		return nuNota;
	}

	public static BigDecimal buscaProximaSequenciaOcorrencia(BigDecimal nuNota) throws SkalaModelException {
		BigDecimal sequencia = BigDecimal.ZERO;
		
		try {
			sequencia = NativeSql.getBigDecimal("COALESCE(MAX(SEQUENCIA), 0) + 1", "AD_PLTGFENTOCO", "NUNOTA = ?",
					new Object[] { nuNota });
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar a Proxima Sequência da Nota.", e);
		}
		
		return sequencia;
	}
}
