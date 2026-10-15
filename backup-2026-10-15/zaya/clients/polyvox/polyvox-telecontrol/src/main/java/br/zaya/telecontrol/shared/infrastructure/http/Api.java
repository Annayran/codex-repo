package br.zaya.telecontrol.shared.infrastructure.http;

import br.zaya.telecontrol.shared.config.ConfigTelecontrol;
import br.zaya.telecontrol.shared.utils.LogUtil;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Api {
	public static final int maxResult = 1000;

	public static int responseCode;

	public static String post(String url, String content) throws Exception {
		return doRequest(url, content, "POST");
	}

	public static String put(String url, String content) throws Exception {
		return doRequest(url, content, "PUT");
	}

	public static String delete(String url) throws Exception {
		Autenticacao.Login();
		LogUtil.iniciarHttp("DELETE", url, "");
		long start = System.currentTimeMillis();
		URL obj = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
		connection.setRequestMethod("DELETE");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("Access-Application-Key", Autenticacao.accessToken);
		connection.setRequestProperty("Access-Env", Autenticacao.env);
		responseCode = connection.getResponseCode();
		String responseBody;
		if (responseCode != 200 && responseCode != 201 && responseCode != 202 && responseCode != 204) {
			responseBody = readBody(connection.getErrorStream());
			LogUtil.finalizarHttp("DELETE", url, responseCode, connection.getResponseMessage(), responseBody,
					System.currentTimeMillis() - start);
			throw new Exception(LogUtil.RESPONSE);
		}
		responseBody = "";
		if (responseCode != 204) {
			responseBody = readBody(connection.getInputStream());
		}
		LogUtil.finalizarHttp("DELETE", url, responseCode, connection.getResponseMessage(), responseBody,
				System.currentTimeMillis() - start);
		if ((new ConfigTelecontrol()).debugMode)
			LogUtil.saveLog(new Exception("Debug"));
		return convertUTF8toISO(responseBody);
	}

	private static String doRequest(String url, String content, String method) throws Exception {
		Autenticacao.Login();
		content = convertUnicode(content);
		LogUtil.iniciarHttp(method, url, content);
		long start = System.currentTimeMillis();
		URL obj = new URL(url);
		HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
		postConnection.setRequestMethod(method);
		postConnection.setRequestProperty("Content-Type", "application/json");
		postConnection.setRequestProperty("Access-Application-Key", Autenticacao.accessToken);
		postConnection.setRequestProperty("Access-Env", Autenticacao.env);
		postConnection.setDoOutput(true);
		OutputStream os = postConnection.getOutputStream();
		os.write(content.getBytes());
		os.flush();
		os.close();
		responseCode = postConnection.getResponseCode();
		String responseBody;
		if (responseCode != 200 && responseCode != 201 && responseCode != 202) {
			responseBody = readBody(postConnection.getErrorStream());
			LogUtil.finalizarHttp(method, url, responseCode, postConnection.getResponseMessage(), responseBody,
					System.currentTimeMillis() - start);
			throw new Exception(LogUtil.RESPONSE);
		}
		responseBody = readBody(postConnection.getInputStream());
		LogUtil.finalizarHttp(method, url, responseCode, postConnection.getResponseMessage(), responseBody,
				System.currentTimeMillis() - start);
		if ((new ConfigTelecontrol()).debugMode)
			LogUtil.saveLog(new Exception("Debug"));
		return convertUTF8toISO(responseBody);
	}

	public static String get(String url) throws Exception {
		Autenticacao.Login();
		LogUtil.iniciarHttp("GET", url, "");
		long start = System.currentTimeMillis();
		URL urlForGetRequest = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("Access-Application-Key", Autenticacao.accessToken);
		connection.setRequestProperty("Access-Env", Autenticacao.env);
		responseCode = connection.getResponseCode();
		String responseBody;
		if (responseCode != 200 && responseCode != 201 && responseCode != 202) {
			responseBody = readBody(connection.getErrorStream());
			LogUtil.finalizarHttp("GET", url, responseCode, connection.getResponseMessage(), responseBody,
					System.currentTimeMillis() - start);
			throw new Exception(LogUtil.RESPONSE);
		}
		responseBody = readBody(connection.getInputStream());
		LogUtil.finalizarHttp("GET", url, responseCode, connection.getResponseMessage(), responseBody,
				System.currentTimeMillis() - start);
		if ((new ConfigTelecontrol()).debugMode)
			LogUtil.saveLog(new Exception("Debug"));
		return convertUTF8toISO(responseBody);
	}

	public static String postLogin(String url, String content) throws Exception {
		URL obj = new URL(url);
		HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
		postConnection.setRequestMethod("POST");
		postConnection.setRequestProperty("Content-Type", "application/json");
		postConnection.setDoOutput(true);
		OutputStream os = postConnection.getOutputStream();
		os.write(content.getBytes());
		os.flush();
		os.close();
		responseCode = postConnection.getResponseCode();
		StringBuffer response = new StringBuffer();
		response.append("\nPOST Response Code :  " + responseCode);
		response.append("\nPOST Response Message : " + postConnection.getResponseMessage());
		if (responseCode != 200 && responseCode != 201 && responseCode != 202) {
			response.append("\nPOST NOT WORKED");
			throw new Exception(response.toString());
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null)
			response.append("\n" + inputLine);
		in.close();
		return response.toString();
	}

	public static String convertUTF8toISO(String str) {
		String ret = null;
		try {
			ret = new String(str.getBytes("ISO-8859-1"), "UTF-8");
			return ret;
		} catch (UnsupportedEncodingException var3) {
			return null;
		}
	}

	public static String convertUnicode(String str) {
		StringBuffer ostr = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (ch >= ' ' && ch <= '~') {
				ostr.append(ch);
			} else {
				ostr.append("\\u");
				String hex = Integer.toHexString(str.charAt(i) & Character.MAX_VALUE);
				for (int j = 0; j < 4 - hex.length(); j++)
					ostr.append("0");
				ostr.append(hex.toLowerCase());
			}
		}
		return new String(ostr);
	}

	private static String readBody(InputStream stream) throws Exception {
		if (stream == null) {
			return "";
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		StringBuilder body = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			if (body.length() > 0) {
				body.append("\n");
			}
			body.append(line);
		}
		reader.close();
		return body.toString();
	}
}


