package tech.skala.action.modules.comercial.exportacao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.modelcore.comercial.CentralFinanceiro;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.action.modules.comercial.vendas.AlterarValorDescontoTotalDAO;
import tech.skala.action.modules.comercial.vendas.ComercialHelper;
import tech.skala.core.beans.cabecalhonota.CabecalhoNotaProcessor;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class ConfirmaNotaExportacaoProcessor {

	private BigDecimal nuNota;
	private BigDecimal vlrDescTot;

	public ConfirmaNotaExportacaoProcessor(BigDecimal nuNota, BigDecimal vlrDescTot) {
		this.nuNota = nuNota;
		this.vlrDescTot = vlrDescTot;
	}

	public void confirmarNotaExportacao() throws SkalaModelException {
		
		try {
			AlterarValorDescontoTotalDAO.alteraValorDescontoTotal(nuNota, vlrDescTot);
			ComercialHelper.refazerFinanceiroWithTX(nuNota);
			
			preparaItensParaExportacao();
			
			ComercialHelper.recalculaQuantidadeVolumes(nuNota);
			
			CabecalhoNotaProcessor.confirmarNota(nuNota);
		}catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível confirmar Nota de Exportação. ", e);
		} finally {
			desfazAlteracaoNosItens();
		}
	}

	private void preparaItensParaExportacao() throws SkalaModelException {
		SessionHandle hnd = null;

		Map<String, Object> params = new HashMap<>();
		params.put(SklFieldName.NUNOTA, nuNota);

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					executeSql("ConfirmaNotaExportacao_createProdutoTemporario.sql", params);
					executeSql("ConfirmaNotaExportacao_createProdutoImpostoTemporario.sql", params);
					executeSql("ConfirmaNotaExportacao_createVolumeAlternativoTemporario.sql", params);
					executeSql("ConfirmaNotaExportacao_updProduto.sql", null);
					executeSql("ConfirmaNotaExportacao_updVolumeAlternativo1.sql", null);
					executeSql("ConfirmaNotaExportacao_updVolumeAlternativo2.sql", null);
					executeSql("ConfirmaNotaExportacao_updVolumeAlternativo3.sql", null);
					executeSql("ConfirmaNotaExportacao_updItensNota.sql", params);
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível preparar Itens para Exportação!", e);
		} finally {
			JapeSession.close(hnd);
		}
	}

	private void desfazAlteracaoNosItens() throws SkalaModelException {
		SessionHandle hnd = null;

		Map<String, Object> params = new HashMap<>();
		params.put(SklFieldName.NUNOTA, nuNota);

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					executeSql("ConfirmaNotaExportacao_desfazUpdProduto.sql", null);
					executeSql("ConfirmaNotaExportacao_desfazUpdVolumeAlternativo.sql", null);
					executeSql("ConfirmaNotaExportacao_dropProdutoTemporario.sql", null);
					executeSql("ConfirmaNotaExportacao_dropProdutoImpostoTemporario.sql", null);
					executeSql("ConfirmaNotaExportacao_dropVolumeAlternativoTemporario.sql", null);
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível desfazer alteração nos Itens para Exportação!", e);
		} finally {
			JapeSession.close(hnd);
		}
	}

	private void executeSql(String resourcePath, Map<String, Object> params) throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		NativeSql sql = null;

		try {
			hnd = JapeSession.open();

			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);

			if (params != null)
				for (Map.Entry<String, Object> entry : params.entrySet())
					sql.setNamedParameter(entry.getKey(), entry.getValue());

			
			sql.loadSql(ConfirmaNotaExportacaoProcessor.class, resourcePath);

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
