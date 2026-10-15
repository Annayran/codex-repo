package tech.skala.flow.modules.central;

import java.math.BigDecimal;
import java.sql.ResultSet;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class SolicitacaoCompraFinder {

	private SolicitacaoCompraFinder() {
		throw new IllegalStateException("Utility class");
	}
	
	public static ResultSet buscaInfoCabecalhoByIdSolicitacao(BigDecimal idSolicitacao) throws SkalaModelException {

		SessionHandle hnd = null;
		NativeSql sql = null;
		ResultSet rs = null;

		JdbcWrapper jdbc = null;
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			hnd = JapeSession.open();
			sql = new NativeSql(jdbc);

			sql.loadSql(SolicitacaoCompraFinder.class, "queCabecalhoSolicitacao.sql");
			sql.setNamedParameter("IDINSTPRN", idSolicitacao);

			rs = sql.executeQuery();
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível buscar informações para da Solicitação de Compra informada",
					e);
		} finally {
			JapeSession.close(hnd);
		}

		return rs;
	}
	
	public static ResultSet buscaInfoItensByIdSolicitacao(BigDecimal idSolicitacao, BigDecimal codParc) throws SkalaModelException {

		SessionHandle hnd = null;
		NativeSql sql = null;
		ResultSet rs = null;

		JdbcWrapper jdbc = null;
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			hnd = JapeSession.open();
			sql = new NativeSql(jdbc);

			sql.loadSql(SolicitacaoCompraFinder.class, "queItensSolicitacao.sql");
			sql.setNamedParameter("IDINSTPRN", idSolicitacao);
			sql.setNamedParameter(SklFieldName.CODPARC, codParc);

			rs = sql.executeQuery();
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível buscar informações para da Solicitação de Compra informada",
					e);
		} finally {
			JapeSession.close(hnd);
		}

		return rs;
	}
}
