package br.zaya.telecontrol.utils;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class LogUtil {
	public static String SYNC;

	public static String URL;

	public static String REGISTRO;

	public static String METHOD;

	public static String REQUEST;

	public static String RESPONSE;

	public static void saveLog(Exception e) throws Exception {
		StackTraceElement[] trace = e.getStackTrace();
		String error = e.toString();
		StringBuffer msg = new StringBuffer();
		for (int var5 = 0; var5 < trace.length; var5++) {
			StackTraceElement erro = trace[var5];
			msg.append("\nFile:" + erro.getFileName() + " Class:" + erro.getClassName() + " Method: "
					+ erro.getMethodName() + " Line:" + Integer.toString(erro.getLineNumber()));
			if (erro.getClassName().contains("snkcps")) {
				break;
			}
		}

		String log = String.valueOf(e.getMessage()) + msg.toString();
		JapeSession.SessionHandle hnd = null;
		try {
			EntityFacade dwfEntityFacade = EntityFacadeFactory.getDWFFacade();
			if (REQUEST != null) {
				try {
					Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
					JsonParser jp = new JsonParser();
					JsonElement je = jp.parse(REQUEST);
					REQUEST = gson.toJson(je);
				} catch (Exception var10) {
					// Se nao for JSON, mantem o original.
				}
			}

			DynamicVO logVO = (DynamicVO) dwfEntityFacade.getDefaultValueObjectInstance("AD_TCLLGT");
			logVO.setProperty("SYNC", SYNC);
			logVO.setProperty("URL", URL);
			logVO.setProperty("REGISTRO", REGISTRO);
			logVO.setProperty("METHOD", METHOD);

			int errorLength = error.length();
			logVO.setProperty("ERROR", error.substring(0, Math.min(errorLength, 100)));

			if (log != null) {
				logVO.setProperty("LOG", log.toCharArray());
			}
			if (REQUEST != null) {
				logVO.setProperty("REQUEST", REQUEST.toCharArray());
			}
			if (RESPONSE != null) {
				logVO.setProperty("RESPONSE", RESPONSE.toCharArray());
			}

			JapeSession currentSession = null;
			try {
				currentSession = JapeSession.getCurrentSession();
			} catch (Exception ignored) {
				currentSession = null;
			}

			if (currentSession != null && currentSession.hasTransaction()) {
				dwfEntityFacade.createEntity("AD_TCLLGT", (EntityVO) logVO);
				return;
			}

			hnd = JapeSession.open();
			hnd.execWithTX(new JapeSession.TXBlock() {
				public void doWithTx() throws Exception {
					dwfEntityFacade.createEntity("AD_TCLLGT", (EntityVO) logVO);
				}
			});
		} finally {
			if (hnd != null) {
				JapeSession.close(hnd);
			}
		}
	}
}
