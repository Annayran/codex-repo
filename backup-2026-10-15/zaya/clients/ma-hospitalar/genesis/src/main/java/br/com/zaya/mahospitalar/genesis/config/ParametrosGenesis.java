package br.com.zaya.mahospitalar.genesis.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParametrosGenesis {
    private static final int DEFAULT_CONNECT_TIMEOUT_MILLIS = 15000;
    private static final int DEFAULT_READ_TIMEOUT_MILLIS = 30000;
    private static final int DEFAULT_MAX_TENTATIVAS = 3;

    private final String baseUrl;
    private final String token;
    private final int connectTimeoutMillis;
    private final int readTimeoutMillis;
    private final int maxTentativas;
    private final List<Integer> topsElegiveis;

    public ParametrosGenesis(String baseUrl, String token, List<Integer> topsElegiveis) {
        this(baseUrl, token, DEFAULT_CONNECT_TIMEOUT_MILLIS, DEFAULT_READ_TIMEOUT_MILLIS,
                DEFAULT_MAX_TENTATIVAS, topsElegiveis);
    }

    public ParametrosGenesis(String baseUrl, String token, int connectTimeoutMillis, int readTimeoutMillis,
            int maxTentativas, List<Integer> topsElegiveis) {
        this.baseUrl = baseUrl;
        this.token = token;
        this.connectTimeoutMillis = connectTimeoutMillis;
        this.readTimeoutMillis = readTimeoutMillis;
        this.maxTentativas = maxTentativas;
        this.topsElegiveis = topsElegiveis == null ? Collections.<Integer>emptyList()
                : Collections.unmodifiableList(new ArrayList<Integer>(topsElegiveis));
    }

    public String getBaseUrl() { return baseUrl; }
    public String getToken() { return token; }
    public int getConnectTimeoutMillis() { return connectTimeoutMillis; }
    public int getReadTimeoutMillis() { return readTimeoutMillis; }
    public int getMaxTentativas() { return maxTentativas; }
    public List<Integer> getTopsElegiveis() { return topsElegiveis; }
}