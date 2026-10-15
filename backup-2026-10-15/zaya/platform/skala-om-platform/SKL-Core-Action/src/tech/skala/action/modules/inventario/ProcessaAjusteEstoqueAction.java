package tech.skala.action.modules.inventario;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;
import com.cbmsoft.modelcore.MessageFactory;
import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class ProcessaAjusteEstoqueAction implements AcaoRotinaJava {
	private final Logger				logger = new LoggerFactory(this.getClass().getCanonicalName()).getLogger();
	private final MessageFactory		msgI18N = new MessageFactory();

	private JdbcWrapper					jdbc;
	private String						mensagemRetorno;
	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		String							msg;
		StringBuilder					sbMessage = new StringBuilder();
		
		logger.info(msgI18N.getMessage("Processing"));
		sbMessage.append(msgI18N.getMessage("SuccessfulProcessing") + "<br>");

		Timestamp dtContagem = TimeUtils.clearTime((Timestamp) contexto.getParam("DTCONTAGEM"));
		BigDecimal codEmp = BigDecimalUtil.getBigDecimal(contexto.getParam("CODEMP"));

		try {
			TimeUtils.Timer.start();

			this.jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			InventarioCabecalho inventario = new InventarioCabecalho();
			inventario.setUp(dtContagem, codEmp);

			Params params = new Params();
			params.setCodEmp(codEmp);
			params.setAuthInfo(AuthenticationInfo.getCurrent());

			ProcessaAjusteEstoque helper = new ProcessaAjusteEstoque(inventario);
			helper.getModelos(codEmp);

			List<InventarioAjuste> ajustesEntrada = new LinkedList<>();
			for (InventarioItem item : inventario.getItens())
				ajustesEntrada.addAll(item.getAjustesEntrada());
			
			if (!ajustesEntrada.isEmpty()) {
				msg = String.format("Entradas: %03d itens", ajustesEntrada.size());
				logger.info(msg);
				
				helper.setModelo(params, InventarioAjusteTipoAjusteEnum.ENTRADA.getValue());
				msg = String.format("params %s", params);
				logger.info(msg);
				
				helper.processaAjusteEstoque(params, ajustesEntrada);
				msg = String.format("params %s", params);
				logger.info(msg);
			}

			List<InventarioAjuste> ajustesSaida = new LinkedList<>();
			for (InventarioItem item : inventario.getItens())
				ajustesSaida.addAll(item.getAjustesSaida());

			if (!ajustesSaida.isEmpty()) {
				msg = String.format("Saídas: %03d itens", ajustesSaida.size());
				logger.info(msg);

				helper.setModelo(params, InventarioAjusteTipoAjusteEnum.SAIDA.getValue());
				msg = String.format("params %s", params);
				logger.info(msg);

				helper.processaAjusteEstoque(params, ajustesSaida);
				msg = String.format("params %s", params);
				logger.info(msg);
			}

			msg = String.format("%n%s", inventario);
			sbMessage.append(msg);
			
			this.mensagemRetorno = sbMessage.toString();
			logger.info(mensagemRetorno);

		} catch (Exception e) {
			this.mensagemRetorno = SkalaModelException.buildErrorMessage(msgI18N.getMessage("RecordNotProcessed"), e);

			logger.severe(mensagemRetorno);
			e.printStackTrace();
		} finally {
			JdbcWrapper.closeSession(jdbc);
			
			TimeUtils.Timer.stop();
			String elapsedTime = TimeUtils.milisegundos2HHmmss(TimeUtils.Timer.elapsedTime);
			msg = String.format("%s<br><br><b>Tempo gasto</b>: %s <br>", mensagemRetorno,
					elapsedTime);

			contexto.setMensagemRetorno(msg);
		}
	}

}
