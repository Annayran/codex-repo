package br.zaya.telecontrol.shared.application.port;

/**
 * Simple interface to abstract Telecontrol HTTP operations (delegates to legacy Api).
 */
public interface ITelecontrolApiClient {
    String get(String url) throws Exception;
    String post(String url, String content) throws Exception;
    String put(String url, String content) throws Exception;
}


