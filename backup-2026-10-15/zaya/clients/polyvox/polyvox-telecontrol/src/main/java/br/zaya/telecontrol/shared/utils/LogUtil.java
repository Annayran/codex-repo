package br.zaya.telecontrol.shared.utils;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.sql.Timestamp;

public class LogUtil {
	public static String SYNC;

	public static String URL;

	public static String REGISTRO;

	public static String METHOD;

	public static String REQUEST;

	public static String RESPONSE;

	public static void iniciarHttp(String method, String url, String request) {
		METHOD = method;
		URL = url;
		REQUEST = request;
		RESPONSE = null;
	}

	public static void finalizarHttp(String method, String url, int statusCode, String statusMessage, String responseBody,
			long elapsedMillis) {
		METHOD = method;
		URL = url;
		RESPONSE = montarResumoHttp(method, statusCode, statusMessage, responseBody, elapsedMillis);
	}

	public static String montarResumoHttp(String method, int statusCode, String statusMessage, String responseBody,
			long elapsedMillis) {
		StringBuilder response = new StringBuilder();
		response.append(method).append(" Response Code :  ").append(statusCode);
		response.append("\n").append(method).append(" Response Message : ").append(statusMessage);
		response.append("\nElapsed Time (ms) : ").append(elapsedMillis);
		if (statusCode < 200 || statusCode > 299) {
			response.append("\n").append(method).append(" NOT WORKED");
		}
		if (responseBody != null && responseBody.trim().length() > 0) {
			response.append("\n").append(responseBody.trim());
		}
		return response.toString();
	}

	public static void saveLog(Exception e) throws Exception {
		StackTraceElement[] trace = e == null ? new StackTraceElement[0] : e.getStackTrace();
		final String error = e == null ? "Exception sem detalhes" : e.toString();
		StringBuffer msg = new StringBuffer();
		for (int var5 = 0; var5 < trace.length; var5++) {
			StackTraceElement erro = trace[var5];
			msg.append("\nFile:" + erro.getFileName() + " Class:" + erro.getClassName() + " Method: "
					+ erro.getMethodName() + " Line:" + Integer.toString(erro.getLineNumber()));
			if (erro.getClassName().contains("snkcps"))
				break;
		}

		final String log = String.valueOf(e == null ? "Exception sem detalhes" : e.getMessage()) + msg.toString();
		JapeSession currentSession = null;
		try {
			currentSession = JapeSession.getCurrentSession();
		} catch (Exception ignored) {
			currentSession = null;
		}

		if (currentSession != null && currentSession.hasTransaction()) {
			gravarLog(error, log);
			return;
		}

		JapeSession.SessionHandle hnd = null;
		try {
			hnd = JapeSession.open();
			hnd.execWithTX(() -> {
				gravarLog(error, log);
			});
		} finally {
			if (hnd != null) {
				JapeSession.close(hnd);
			}
		}
	}

	private static void gravarLog(String error, String log) throws Exception {
		EntityFacade dwfEntityFacade = EntityFacadeFactory.getDWFFacade();
		DynamicVO logVO = (DynamicVO) dwfEntityFacade.getDefaultValueObjectInstance("AD_TCLLGT");
		logVO.setProperty("DHALTER", new Timestamp(System.currentTimeMillis()));
		logVO.setProperty("SYNC", SYNC);
		logVO.setProperty("URL", URL);
		logVO.setProperty("REGISTRO", REGISTRO);
		logVO.setProperty("METHOD", METHOD);

		String errorResumo = limitar(error, 100);
		logVO.setProperty("ERROR", errorResumo);

		if (log != null)
			logVO.setProperty("LOG", log.toCharArray());
		if (REQUEST != null)
			logVO.setProperty("REQUEST", formatarJsonSePossivel(REQUEST).toCharArray());
		if (RESPONSE != null)
			logVO.setProperty("RESPONSE", formatarJsonSePossivel(RESPONSE).toCharArray());
		dwfEntityFacade.createEntity("AD_TCLLGT", (EntityVO) logVO);
	}

	private static String formatarJsonSePossivel(String value) {
		if (value == null) {
			return null;
		}
		try {
			Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
			JsonElement je = jp.parse(value);
			return gson.toJson(je);
		} catch (Exception ignored) {
			return value;
		}
	}

	private static String limitar(String value, int maxLength) {
		if (value == null) {
			return null;
		}
		return value.substring(0, Math.min(value.length(), maxLength));
	}
}
