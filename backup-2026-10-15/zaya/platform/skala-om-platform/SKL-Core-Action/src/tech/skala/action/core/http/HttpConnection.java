package tech.skala.action.core.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.logging.Logger;

import tech.skala.core.commons.exceptions.SkalaModelException;

public class HttpConnection {
	private static final Logger LOGGER = Logger.getLogger(HttpConnection.class.getCanonicalName());
		
	private String								baseUrl;
	private String								path;
	private HttpMethod							method;
	private String								payload;
	private Map<String, String> 				requestProperties;

	public HttpConnection(String baseUrl, HttpMethod method) {
		this.baseUrl = baseUrl;
		this.method = method;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public Map<String, String> getRequestProperties() {
		return requestProperties;
	}

	public void setRequestProperties(Map<String, String> requestProperties) {
		this.requestProperties = requestProperties;
	}
	
	public ApiResponse doConnect() throws SkalaModelException {

		HttpURLConnection urlConnection = null;
		ApiResponse apiResponse = null;
		String response = null;

		String msg;
		String completeUrl = String.format("%s%s", baseUrl, path);

		try {
			URL url = new URL(completeUrl);

			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setUseCaches(false);
			urlConnection.setRequestMethod(method.getValue());

			urlConnection.setRequestProperty("User-Agent", "Sankhya Om");
			urlConnection.setRequestProperty("accept", "*/*");

			if (requestProperties != null)
				for (Map.Entry<String, String> entry : requestProperties.entrySet())
					urlConnection.setRequestProperty(entry.getKey(), entry.getValue());

			urlConnection.setDoInput(true);

			if (method.equals(HttpMethod.POST) || method.equals(HttpMethod.PUT)) {
				urlConnection.setDoOutput(true);

				if (payload != null) {
					try (Writer writer = new OutputStreamWriter(urlConnection.getOutputStream())) {
						writer.write(payload);
						writer.flush();

					}
				}
			}

			int responseCode = urlConnection.getResponseCode();
			msg = String.format("responseCode %d", responseCode);
			LOGGER.info(msg);

			StringBuilder sbResponse = new StringBuilder();
			boolean success = (responseCode == HttpURLConnection.HTTP_OK);
			InputStream inputStream;
			if (responseCode == HttpURLConnection.HTTP_OK) {
				inputStream = urlConnection.getInputStream();
			} else {
				inputStream = urlConnection.getErrorStream();
			}

			try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
				String line;

				while ((line = reader.readLine()) != null)
					sbResponse.append(line);

				if (sbResponse.length() > 0)
					response = sbResponse.toString();
			}

			apiResponse = new ApiResponse(success, response, responseCode);

		} catch (Exception e) {
			SkalaModelException.throwMe("Requisição não efetuada.", e);
		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();

		}

		return apiResponse;
	}

	public static class Builder {
		HttpConnection conn;

		public Builder(String baseUrl, HttpMethod method) {
			this.conn = new HttpConnection(baseUrl, method);
		}

		public Builder setPath(String path) {
			conn.setPath(path);
			return this;
		}

		public Builder setMethod(HttpMethod method) {
			conn.setMethod(method);
			return this;
		}

		public Builder setPayload(String payload) {
			conn.setPayload(payload);
			return this;
		}
		
		public Builder setRequestProperties(Map<String, String> requestProperties) {
			conn.setRequestProperties(requestProperties);
			return this;
		}
		
		public HttpConnection build() {
			return conn;
		}

	}

}
