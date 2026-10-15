package tech.skala.action.integration.tangerino.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.exception.ExceptionUtils;

import tech.skala.action.integration.tangerino.model.integracao.Autenticacao;
import tech.skala.action.integration.tangerino.model.sistema.RespostaRequisicao;

public class HttpUtils {
	
	 public static RespostaRequisicao executeRequest(final Autenticacao autenticacao, final String method, final String body){

		HttpURLConnection urlConnection = null;
		RespostaRequisicao respostaRequisicao = null;

		try {
			trustManager();
			System.out.println("URL: SERVICO: " + autenticacao.getUrlCompleta());

			urlConnection = (HttpURLConnection) new URL(autenticacao.getUrlCompleta()).openConnection();
			urlConnection.setUseCaches(false);
			urlConnection.setRequestProperty("Content-Type", "application/json");
			urlConnection.setRequestProperty("charset", "utf-8");
			urlConnection.setRequestProperty("Authorization", "Basic " + autenticacao.getToken() );

			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);

			if ("GET".equals(method) ) {
				urlConnection.setRequestMethod("GET");
			} else if ("POST".equals(method)) {
				urlConnection.setDoOutput(true);
				urlConnection.setRequestMethod("POST");
			} else {
				urlConnection.setRequestMethod(method);
			}

			if ("POST".equals(method) && body != null) {
				OutputStreamWriter wr= new OutputStreamWriter(urlConnection.getOutputStream());
				wr.write(body);
				wr.flush();
			}

			int responseCode = urlConnection.getResponseCode();
			BufferedReader reader = null;
			String line;

			if (responseCode == 200) {
				reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
				StringBuilder response = new StringBuilder();

				while ((line = reader.readLine()) != null) {
					response.append(line);
				}
				respostaRequisicao =  new RespostaRequisicao(true, response.toString(), 200);
				//RotinaDao.criarLog("Resposta Requisição: ", respostaRequisicao.toString(), EnumTipoLog.OK);

			}else {
				reader = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
				StringBuilder response = new StringBuilder();
				while ((line = reader.readLine()) != null) {
					response.append(line);
				}
				respostaRequisicao =  new RespostaRequisicao(false, response.toString(), responseCode);
				//RotinaDao.criarLog("Resposta Requisição: ", respostaRequisicao.toString(), EnumTipoLog.AVISO);
			}
			reader.close();

			urlConnection.disconnect();
		} catch (Exception e) {
			respostaRequisicao =  new RespostaRequisicao(true, ExceptionUtils.getMessage(e), 500);
			//RotinaDao.criarLog("Resposta Requisição: ", ExceptionUtils.getMessage(e), EnumTipoLog.ERRO);
		}
		finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}
		return  respostaRequisicao;
	}
	
	
	private static void trustManager(){
        SSLContext ctx = null;
        TrustManager[] trustAllCerts = new X509TrustManager[]{new X509TrustManager(){
            public X509Certificate[] getAcceptedIssuers(){return null;}
            public void checkClientTrusted(X509Certificate[] certs, String authType){}
            public void checkServerTrusted(X509Certificate[] certs, String authType){}
        }};
        try {
            ctx = SSLContext.getInstance("SSL");
            ctx.init(null, trustAllCerts, null);
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            System.out.println("Erro na contexto SSL: " +  ExceptionUtils.getMessage(e));
        }
        SSLContext.setDefault(ctx);
    }

}
