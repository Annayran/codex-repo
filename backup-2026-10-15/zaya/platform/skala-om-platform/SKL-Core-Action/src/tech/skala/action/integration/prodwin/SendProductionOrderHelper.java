package tech.skala.action.integration.prodwin;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.JdbcUtils;

import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.action.core.http.ApiResponse;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.extensions.Action;

public class SendProductionOrderHelper {

	Action action;
	Registro row;
	BigDecimal productionOrderId;
	BigDecimal releaseNumber;
	BigDecimal scheduleNumber;

	private String pattern = "yyyy-MM-dd' 'HH:mm:ss";
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

	public SendProductionOrderHelper(Action action, Registro row) {
		this.action = action;
		this.row = row;

		this.productionOrderId = BigDecimalUtil.getBigDecimal(row.getCampo(SklFieldName.IDIPROC));
		this.releaseNumber = BigDecimalUtil.getBigDecimal(row.getCampo("NULOP"));
		this.scheduleNumber = BigDecimalUtil.getBigDecimal(row.getCampo("NUPRGP"));
	}
	
	public SendProductionOrderHelper(Action action, BigDecimal idiProc, BigDecimal nuLop, BigDecimal nuPrgp) {
		this.action = action;
		
		this.productionOrderId = idiProc;
		this.releaseNumber = nuLop;
		this.scheduleNumber = nuPrgp;
		
	}

	public void execute() throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.getCurrentSession().getTopMostHandle();
			hnd.setCanTimeout(true);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					sendProductionOrder();
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível processar a ação.", e);
		} finally {
			hnd = null;
		}
	}

	private void sendProductionOrder() throws SkalaModelException {
		ProductionOrderDTO productionOrderDTO = this.getData();

		if (productionOrderDTO != null) {
			final String body = this.createJsonBody(productionOrderDTO);

			if (body != null) {
				ApiResponse response = ProdwinConnector.productionOrder(body);

				if (response.isSuccess()) {
					this.action.appendMessageWithLineSeparator("OP " + productionOrderId + " enviada p/ Coletor");
				} else {
					SkalaModelException.throwMe(response.getResponse(), new Exception());
				}

				this.createRequestResponseLog(BigDecimal.valueOf(response.getResponseCode()),
						String.format("%s/%s", ProdwinUtils.ENDPOINT, ProdwinUtils.PRODUCTION_ORDER), body,
						response.getResponse());
			}
		} else {
			this.action.appendMessageWithLineSeparator("Não existe registro para ser enviado.");
		}
	}

	private ProductionOrderDTO getData() throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;

		ProductionOrderDTO productionOrderDTO = null;

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);

			sql.loadSql(getClass(), "SendProductionOrder_queData.sql");
			sql.setNamedParameter(SklFieldName.IDIPROC, this.productionOrderId);
			sql.setNamedParameter("NULOP", this.releaseNumber);
			sql.setNamedParameter("NUPRGP", this.scheduleNumber);

			rs = sql.executeQuery();

			while (rs.next()) {
				productionOrderDTO = new ProductionOrderDTO();
				productionOrderDTO.setCdOrdemProducao(rs.getBigDecimal(SklFieldName.IDIPROC).toString());
				productionOrderDTO.setCdOperacao("");
				productionOrderDTO.setCdFerramenta("");
				productionOrderDTO.setDescFerramenta("");
				productionOrderDTO.setCdEstrutura("");
				productionOrderDTO.setCdProduto(rs.getBigDecimal(SklFieldName.CODPRODPA).toString());
				productionOrderDTO.setDescProduto(rs.getString(SklFieldName.DESCRPROD));
				productionOrderDTO.setNomeCliente(rs.getString("NOMEFANTASIA"));
				productionOrderDTO.setCdMaquina(rs.getBigDecimal(SklFieldName.CODWCP).toString());
				productionOrderDTO.setTaxaProducao(rs.getBigDecimal("TAXAPRODUCAO"));
				productionOrderDTO.setTipoTaxaProducao(BigDecimal.valueOf(7));
//				productionOrderDTO.setPecasCiclo(BigDecimal.valueOf(6));
				productionOrderDTO.setPecasCiclo(BigDecimal.valueOf(1));
//				productionOrderDTO.setPontosPeca(BigDecimal.ONE);
//				productionOrderDTO.setPontosPeca(BigDecimal.valueOf(7));
				productionOrderDTO.setPontosPeca(BigDecimal.valueOf(1));
				productionOrderDTO.setDtInicio(simpleDateFormat.format(rs.getTimestamp("DHINST")));
				productionOrderDTO.setDtEntrega(simpleDateFormat.format(rs.getTimestamp("DHTERMINO")));
				productionOrderDTO.setQtdPecas(rs.getBigDecimal(SklFieldName.QTDPRODUZIR));
				productionOrderDTO.setComplemento1("");
				productionOrderDTO.setComplemento2("");
				productionOrderDTO.setComplemento3("");
				productionOrderDTO.setComplemento4("");
				productionOrderDTO.setComplemento5("");
				productionOrderDTO.setComplemento6("");
				productionOrderDTO.setComplemento7("");
				productionOrderDTO.setComplemento8("");
				productionOrderDTO.setComplemento9("");
				productionOrderDTO.setComplemento10("");
				productionOrderDTO.setComplemento11("");
				productionOrderDTO.setComplemento12("");
				productionOrderDTO.setComplemento13("");
				productionOrderDTO.setComplemento14("");
				productionOrderDTO.setComplemento15("");
				productionOrderDTO.setComplemento16("");
				productionOrderDTO.setComplemento17("");
				productionOrderDTO.setComplemento18("");
				productionOrderDTO.setComplemento19("");
				productionOrderDTO.setComplemento20("");
				productionOrderDTO.setComplemento21("");
				productionOrderDTO.setComplemento22("");
				productionOrderDTO.setComplemento23("");
				productionOrderDTO.setComplemento24("");
				productionOrderDTO.setComplemento25("");
				productionOrderDTO.setComplemento26("");
				productionOrderDTO.setComplemento27("");
				productionOrderDTO.setComplemento28("");
				productionOrderDTO.setComplemento29("");
				productionOrderDTO.setComplemento30("");
			}
		} catch (Exception e) {
			SkalaModelException.throwMe("Ocorreu erro ao buscar as informações.", e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

		return productionOrderDTO;
	}

	private String createJsonBody(ProductionOrderDTO productionOrderDTO) {
		final String jsonObject = new Gson().toJson(productionOrderDTO, ProductionOrderDTO.class);
		JsonObject json = new JsonObject();
		JsonParser parser = new JsonParser();
		json = (JsonObject) parser.parse(jsonObject);

		if (!json.isJsonNull()) {
			return json.toString();
		}

		return null;
	}

	public void createRequestResponseLog(BigDecimal statusCode, String url, String request, String response)
			throws SkalaModelException {
		try {
			ProdwinRequestResponseLog log = new ProdwinRequestResponseLog();

			log.setProductionOrderId(this.productionOrderId);
			log.setReleaseNumber(this.releaseNumber);
			log.setScheduleNumber(this.scheduleNumber);
			log.setStatusCode(statusCode);
			log.setUrl(url);
			log.setRequest(request);
			log.setResponse(response);
			log.persists();
		} catch (Exception e) {
			SkalaModelException.throwMe("Ocorreu erro ao gravar o Log de Request/Response.", e);
		}
	}
}
