package tech.skala.action.modules.comercial.vendas;

import java.math.BigDecimal;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;
import com.cbmsoft.modelcore.MessageFactory;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.util.JapeSessionContext;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.extensions.Action;

public class AlteraTipoNegociacaoAction implements AcaoRotinaJava {
	private final Logger								logger = new LoggerFactory(this.getClass().getCanonicalName()).getLogger();
	private final MessageFactory						msgI18N = new MessageFactory();
	
	private JdbcWrapper									jdbc;
	private String										mensagemRetorno;


	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		StringBuilder 			sbMessage = new StringBuilder();

		logger.info(msgI18N.getMessage("Processing"));
		sbMessage.append(msgI18N.getMessage("SuccessfulProcessing") + "<br>");
		final BigDecimal codTipVenda = new BigDecimal((String) contexto.getParam("CODTIPVEND"));
		final Registro[] linhas = contexto.getLinhas();
		try {
			this.jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			JapeSessionContext.putProperty("br.com.sankhya.com.CentralCompraVenda", Boolean.TRUE);

			ComercialHelper comercial = new ComercialHelper();
			BigDecimal nuNota;

			for (final Registro linha : linhas) {
				nuNota = (BigDecimal) linha.getCampo(SklFieldName.NUNOTA);
				comercial.alteraTipoNegociacao(nuNota, codTipVenda);
			}
			sbMessage.append("Tipo de Negociação Alterado com sucesso!");
			
			this.mensagemRetorno = sbMessage.toString();
			logger.info(mensagemRetorno);

		} catch (Exception e) {
			this.mensagemRetorno = SkalaModelException.buildErrorMessage(msgI18N.getMessage("RecordNotProcessed"), e);

			logger.severe(mensagemRetorno);
			e.printStackTrace();
		} finally {
			contexto.setMensagemRetorno(mensagemRetorno);
			JdbcWrapper.closeSession(jdbc);

			JapeSessionContext.putProperty("br.com.sankhya.com.CentralCompraVenda", Boolean.FALSE);
		}
	}

}