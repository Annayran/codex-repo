package tech.skala.action.integration.pontoicarus;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import tech.skala.core.commons.dd.DTO;
import tech.skala.core.commons.exceptions.SkalaModelException;

/**
 * Classe responsavel pela conexao
 * 
 * @author cassiomenezes
 * @since 2023-04-17
 */
public class ApiConn {
	private static final Logger LOGGER = Logger.getLogger("tech.skala.action.integration.pontoicarus.ApiConn");
	private static final String BASEURL = "https://backendicarus.pontoicarus.com.br";

	private static Gson gson = new GsonBuilder()
			.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
			.create();
	
	private ApiConn() {
		throw new IllegalStateException("Utility class");
	}
	
	public static ApiResponseOld doConnect(String bearerAuth, String path, DTO dto, String method) throws SkalaModelException {
		HttpURLConnection urlConnection = null;
		ApiResponseOld apiResponseOld = null;
		String response = null;

		String completeUrl = String.format("%s%s", BASEURL, path);
		String msg = String.format("completeUrl: %s", completeUrl);
		LOGGER.info(msg);
		try {
			URL url = new URL(completeUrl);
			
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setUseCaches(false);
			urlConnection.setRequestMethod(method);

			urlConnection.setRequestProperty("User-Agent", "Sankhya Om");
			urlConnection.setRequestProperty("accept", "*/*");
			urlConnection.setRequestProperty("Content-Type", "application/json");
			if (bearerAuth != null)
				urlConnection.setRequestProperty("Authorization", bearerAuth);
			
			urlConnection.setDoInput(true);
			
			if (method.equalsIgnoreCase("POST")) {
				urlConnection.setDoOutput(true);
				try (Writer writer = new OutputStreamWriter(urlConnection.getOutputStream())) {
					writer.write(gson.toJson(dto, dto.getClass()));
					writer.flush();
				}
			}

			int responseCode = urlConnection.getResponseCode();
			msg = String.format("responseCode %d", responseCode);
			LOGGER.info(msg);
			
			StringBuilder sbResponse = new StringBuilder();
			boolean success;
			InputStream inputStream;
			if (responseCode == HttpURLConnection.HTTP_OK) {
				success = true;
				inputStream = urlConnection.getInputStream();
			} else {
				success = true;
				inputStream = urlConnection.getErrorStream();
			}
			
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
				String line;
				
				while ((line = reader.readLine()) != null)
					sbResponse.append(line);
				
				if (sbResponse.length() > 0)
					response = sbResponse.toString();
			}

			apiResponseOld = new ApiResponseOld(success, response, responseCode);
			
		} catch (Exception e) {
			SkalaModelException.throwMe("Requisição não efetuada.", e);
		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();

		}
		
		return apiResponseOld;

	}
}
