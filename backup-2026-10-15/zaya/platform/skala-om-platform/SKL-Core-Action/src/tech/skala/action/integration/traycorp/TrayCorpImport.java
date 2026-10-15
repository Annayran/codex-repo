package tech.skala.action.integration.traycorp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sankhya.util.StringUtils;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import tech.skala.action.core.filamensagem.FilaMensagemUtils;
import tech.skala.action.core.http.ApiResponse;
import tech.skala.action.integration.traycorp.TrayCorpException.EnderecoNaoDefinidoException;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class TrayCorpImport {

	private final Logger logger = new LoggerFactory(TrayCorpImport.class.getCanonicalName()).getLogger();

	public TrayCorpImportLog importaPedidos() throws SkalaModelException {
		SimpleDateFormat frmData = new SimpleDateFormat("yyy-MM-dd");

		Timestamp tomorrow = TimeUtils.dataAdd(TimeUtils.getNow(), 1, Calendar.DAY_OF_MONTH);
		Timestamp start = TimeUtils.dataAdd(TimeUtils.getNow(), -2, Calendar.MONTH);
		String startDate = frmData.format(start);
		String endDate = frmData.format(tomorrow);

		ApiResponse apiResponse = TrayCorpConnector.getPedidos(startDate, endDate);
		String response = apiResponse.getResponse();

		TrayCorpImportLog importLog = new TrayCorpImportLog();
		if (response == null) {
			logger.info("Nenhum pedido processado.");
		} else {
			importarPedidos(response, importLog);
		}

		return importLog;
	}

	private void importarPedidos(String response, TrayCorpImportLog importLog) {

		JsonArray pedidosJA = (JsonArray) new JsonParser().parse(response);
		importLog.setTotalPedidos(pedidosJA.size());

		for (JsonElement pedidoJE : pedidosJA)
			processaPedido(importLog, pedidoJE);

	}

	private void processaPedido(TrayCorpImportLog importLog, JsonElement pedidoJE) {

		try {
			TrayPedido pedido = new Gson().fromJson(pedidoJE, TrayPedido.class);
			processarPedido(pedido, importLog);

		} catch (Exception e) {
			importLog.addError();

			String msg = String.format(
					"[PEDIDO %s] O Pedido não foi processado.%n, teve problema no cast do JSON para a Classe",
					pedidoJE.toString());
			SkalaModelException.buildErrorMessage(msg, e);
			logger.info(msg);
		}

	}

	private void processarPedido(final TrayPedido pedido, TrayCorpImportLog importLog) throws SkalaModelException {
		SessionHandle hnd = null;

		TrayCorpImportProcessor processor = new TrayCorpImportProcessor(pedido);
		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					processor.processarPedido();
			
					processor.atualizarSituacaoPedido();
					importLog.add();
				}

			});

		} catch (EnderecoNaoDefinidoException e) {
			BigDecimal pedidoId = BigDecimal.valueOf(pedido.getPedidoId());

			TrayEndereco trayEndereco = pedido.getPedidoEndereco().iterator().next();

			String estado = StringUtils.getEmptyAsNull(trayEndereco.getEstado());
			String cidade = StringUtils.getEmptyAsNull(trayEndereco.getCidade());
			String nomeCidade = ("DF".equalsIgnoreCase(estado)) ? "BRASILIA" : cidade;
			String cep = StringUtils.getEmptyAsNull(trayEndereco.getCep());

			enviaEmailErroCadastroParceiro(pedidoId, nomeCidade, cep);

			importLog.addError();

			String msg = String.format("[PEDIDO %d] Pedido não processado.%n", pedido.getPedidoId());
			SkalaModelException.buildErrorMessage(msg, e);
			logger.info(msg);
		} catch (Exception e) {
			importLog.addError();

			String msg = String.format("[PEDIDO %d] Pedido não processado.%n", pedido.getPedidoId());
			SkalaModelException.buildErrorMessage(msg, e);
			logger.info(msg);
		} finally {
			JapeSession.close(hnd);
		}

	}

	private void enviaEmailErroCadastroParceiro(BigDecimal pedidoId, String nomeCidade, String cep) {

		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					JapeWrapper dao = JapeFactory.dao("AD_PLTGFLOGEC");
					try {
						DynamicVO vo = dao.findByPK(pedidoId);
						if (vo == null) {

							EntityInsert entityInsert = new EntityInsert("AD_PLTGFLOGEC");
							entityInsert.setField("PEDIDO", pedidoId);
							entityInsert.fluidInsert();

							String email = "suporte.controladoria@skala.com.br";
							String assunto = "Cadastro de cidade.";
							String mensagem = String.format("Favor cadastrar a cidade: %s - %s no sistema.", nomeCidade,
									cep);

							FilaMensagemUtils.enviarAvisoEmail(email, assunto, mensagem,
									AuthenticationInfo.getCurrent().getUserID());

						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

			});

		} catch (Exception e) {
			logger.info(e.getMessage());
		} finally {
			JapeSession.close(hnd);
		}
	}

}
