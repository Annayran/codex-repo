package tech.skala.action.modules.data.etl;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class LoadBatchProductProcessor {

	public void loadProducts() throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					truncateTableLoadProduct();
					loadProduct();
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível atualizar os Lotes dos Produtos.", e);
		} finally {
			JapeSession.close(hnd);
		}
	}

	private void truncateTableLoadProduct() throws SkalaModelException {
		JdbcWrapper jdbc = null;
		SessionHandle hnd = null;
		NativeSql sql = null;

		try {
			hnd = JapeSession.open();

			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);
			sql.appendSql("TRUNCATE TABLE PLTGFPROLTE ");
			sql.executeUpdate();
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível limpar tabela de Lotes dos Produtos.", e);
		} finally {
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}
	}

	private void loadProduct() throws SkalaModelException {
		JdbcWrapper jdbc = null;
		SessionHandle hnd = null;
		NativeSql sql = null;

		try {
			hnd = JapeSession.open();

			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);
			sql.loadSql(getClass(), "loadProdutosContagem.sql");
			sql.executeUpdate();
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível carregar os Lotes dos Produtos.", e);
		} finally {
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}
	}

}
