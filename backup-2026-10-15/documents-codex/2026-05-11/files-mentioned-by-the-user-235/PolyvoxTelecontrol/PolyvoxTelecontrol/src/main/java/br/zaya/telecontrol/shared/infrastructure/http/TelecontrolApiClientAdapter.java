package br.zaya.telecontrol.shared.infrastructure.http;

import br.zaya.telecontrol.shared.application.port.ITelecontrolApiClient;

/**
 * Adapter to delegate HTTP calls to the existing Api class.
 */
public class TelecontrolApiClientAdapter implements ITelecontrolApiClient {

    @Override
    public String get(String url) throws Exception {
        return Api.get(url);
    }

    @Override
    public String post(String url, String content) throws Exception {
        return Api.post(url, content);
    }

    @Override
    public String put(String url, String content) throws Exception {
        return Api.put(url, content);
    }
}


