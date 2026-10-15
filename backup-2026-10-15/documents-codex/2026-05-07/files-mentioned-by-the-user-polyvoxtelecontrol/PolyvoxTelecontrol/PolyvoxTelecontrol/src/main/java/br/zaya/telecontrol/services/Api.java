package br.zaya.telecontrol.services;

import br.com.sankhya.jape.core.JapeSession;
import br.zaya.telecontrol.utils.ConfigTelecontrol;
import br.zaya.telecontrol.utils.LogUtil;
import java.io.BufferedReader;
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
		LogUtil.REQUEST = "";
		LogUtil.RESPONSE = null;
		LogUtil.METHOD = "DELETE";
		LogUtil.URL = url;
		URL obj = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
		connection.setRequestMethod("DELETE");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("Access-Application-Key", Autenticacao.accessToken);
		connection.setRequestProperty("Access-Env", Autenticacao.env);
		responseCode = connection.getResponseCode();
		StringBuffer response = new StringBuffer();
		StringBuffer responseBody = new StringBuffer();
		response.append("\nDELETE Response Code :  " + responseCode);
		response.append("\nDELETE Response Message : " + connection.getResponseMessage());
		if (responseCode != 200 && responseCode != 201 && responseCode != 202 && responseCode != 204) {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
			String str;
			while ((str = bufferedReader.readLine()) != null)
				responseBody.append("\n" + str);
			bufferedReader.close();
			response.append("\nDELETE NOT WORKED");
			throw new Exception(String.valueOf(response.toString()) + responseBody.toString());
		}
		if (connection.getInputStream() != null) {
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				responseBody.append("\n" + inputLine);
			in.close();
		}
		LogUtil.RESPONSE = String.valueOf(response.toString()) + responseBody.toString();
		if ((new ConfigTelecontrol()).debugMode)
			LogUtil.saveLog(new Exception("Debug"));
		return convertUTF8toISO(responseBody.toString());
	}

	private static String doRequest(String url, String content, String method) throws Exception {
		Autenticacao.Login();
		content = convertUnicode(content);
		LogUtil.REQUEST = content;
		LogUtil.RESPONSE = null;
		LogUtil.METHOD = method;
		LogUtil.URL = url;
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
		StringBuffer response = new StringBuffer();
		StringBuffer responseBody = new StringBuffer();
		response.append("\nPOST Response Code :  " + responseCode);
		response.append("\nPOST Response Message : " + postConnection.getResponseMessage());
		if (responseCode != 200 && responseCode != 201 && responseCode != 202) {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(postConnection.getErrorStream()));
			String str;
			while ((str = bufferedReader.readLine()) != null)
				responseBody.append("\n" + str);
			bufferedReader.close();
			response.append("\nPOST NOT WORKED");
			throw new Exception(String.valueOf(response.toString()) + responseBody.toString());
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null)
			responseBody.append("\n" + inputLine);
		in.close();
		LogUtil.RESPONSE = String.valueOf(response.toString()) + responseBody.toString();
		if ((new ConfigTelecontrol()).debugMode)
			LogUtil.saveLog(new Exception("Debug"));
		return convertUTF8toISO(responseBody.toString());
	}

	public static String get(String url) throws Exception {
		Autenticacao.Login();
		LogUtil.METHOD = "GET";
		LogUtil.URL = url;
		URL urlForGetRequest = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("Access-Application-Key", Autenticacao.accessToken);
		connection.setRequestProperty("Access-Env", Autenticacao.env);
		responseCode = connection.getResponseCode();
		StringBuffer response = new StringBuffer();
		StringBuffer responseBody = new StringBuffer();
		if (responseCode != 200 && responseCode != 201 && responseCode != 202) {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
			String str;
			while ((str = bufferedReader.readLine()) != null)
				responseBody.append("\n" + str);
			bufferedReader.close();
			response.append("\nGET NOT WORKED");
			throw new Exception(String.valueOf(response.toString()) + responseBody.toString());
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null)
			responseBody.append("\n" + inputLine);
		in.close();
		LogUtil.RESPONSE = String.valueOf(response.toString()) + responseBody.toString();
		if ((new ConfigTelecontrol()).debugMode)
			LogUtil.saveLog(new Exception("Debug"));
		return convertUTF8toISO(responseBody.toString());
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
}
