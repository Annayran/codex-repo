package tech.skala.action.modules.comercial.vendas;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class ExcluiProdutoLancamentoProcessor {

	private BigDecimal codProd;
	private BigDecimal codTipOper;
	private BigDecimal codParc;
	
	public ExcluiProdutoLancamentoProcessor(BigDecimal codProd, BigDecimal codTipOper, BigDecimal codParc) throws SkalaModelException {
		this.codProd = codProd;
		this.codTipOper = codTipOper;
		this.codParc = codParc;
		
		insereProdutoTabela();
	}
	
	public void excluiProdutos() throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {

					Map<String, Object> params = new HashMap<>();
					params.put(SklFieldName.CODPROD, codProd);
					params.put(SklFieldName.CODTIPOPER, codTipOper);

					executeQuery("ExcluiProdutoLancamento_delItensLancamento.sql", params);
					executeQuery("ExcluiProdutoLancamento_delLancamentoSemItens.sql", null);
								
					
				}

			});

		} catch (Exception e) {
			String msg = String.format("[CODPROD %s] Não foi possível excluir o produto dos lançamentos.", codProd);
			SkalaModelException.throwMe(msg, e);
		} finally {
			JapeSession.close(hnd);
		}
	}
	
	public void marcaProdutoComoNaoPendente() throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {

					Map<String, Object> params = new HashMap<>();
					params.put(SklFieldName.CODPROD, codProd);
					params.put(SklFieldName.CODTIPOPER, codTipOper);

					executeQuery("ExcluiProdutoLancamento_setItensNaoPendenteLancamento.sql", params);
					
				}

			});

		} catch (Exception e) {
			String msg = String.format("[CODPROD %s] Não foi possível marcar o produto como Não Pendente nos lançamentos.", codProd);
			SkalaModelException.throwMe(msg, e);
		} finally {
			JapeSession.close(hnd);
		}
	}

	public void truncateTable() throws SkalaModelException {

		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(() -> executeQuery("ExcluiProdutoLancamento_truncateTable.sql", null));

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível truncar a tabela.", e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	public void insereProdutoTabela() throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {

					Map<String, Object> params = new HashMap<>();
					params.put(SklFieldName.CODPROD, codProd);
					params.put(SklFieldName.CODTIPOPER, codTipOper);
					params.put(SklFieldName.CODPARC, codParc);

					executeQuery("ExcluiProdutoLancamento_queInsertTabela.sql", params);

				}

			});

		} catch (Exception e) {
			String msg = String.format("[CODPROD %s] Não foi possível excluir o produto dos lançamentos.", codProd);
			SkalaModelException.throwMe(msg, e);
		} finally {
			JapeSession.close(hnd);
		}
	}

	public void executeQuery(String resourcePath, Map<String, Object> parameters) throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		NativeSql sql = null;

		try {
			hnd = JapeSession.open();

			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);

			if (parameters != null)
				for (Map.Entry<String, Object> entry : parameters.entrySet())
					sql.setNamedParameter(entry.getKey(), entry.getValue());

			sql.loadSql(ExcluiProdutoLancamentoProcessor.class, resourcePath);

			sql.executeUpdate();
		} catch (Exception e) {
			String msg = String.format("Não foi possível executar Script: %s", resourcePath);
			SkalaModelException.throwMe(msg, e);
		} finally {
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

	}
}
