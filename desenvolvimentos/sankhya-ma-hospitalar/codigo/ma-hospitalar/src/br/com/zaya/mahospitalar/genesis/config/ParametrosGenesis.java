package br.com.zaya.mahospitalar.genesis.config;

public final class ParametrosGenesis {
    private final long idConfig;
    private final String ambiente;
    private final String baseUrl;
    private final boolean ativo;
    private final boolean simulacao;
    private final int connectTimeoutMs;
    private final int readTimeoutMs;
    private final int maxTentativas;
    private final int tamanhoLote;
    private final String unidadeEpoch;

    public ParametrosGenesis(long idConfig, String ambiente, String baseUrl, boolean ativo,
                             boolean simulacao, int connectTimeoutMs, int readTimeoutMs,
                             int maxTentativas, int tamanhoLote, String unidadeEpoch) {
        this.idConfig = idConfig;
        this.ambiente = ambiente;
        this.baseUrl = baseUrl;
        this.ativo = ativo;
        this.simulacao = simulacao;
        this.connectTimeoutMs = connectTimeoutMs;
        this.readTimeoutMs = readTimeoutMs;
        this.maxTentativas = maxTentativas;
        this.tamanhoLote = tamanhoLote;
        this.unidadeEpoch = unidadeEpoch;
    }

    public void validarParaExecucao() {
        if (!ativo) throw new IllegalStateException("Integracao Genesis desativada.");
        if (baseUrl == null || baseUrl.trim().isEmpty() || baseUrl.startsWith("CONFIGURAR_")) {
            throw new IllegalStateException("BASEURL Genesis nao configurada.");
        }
        if (connectTimeoutMs <= 0 || readTimeoutMs <= 0 || tamanhoLote <= 0) {
            throw new IllegalStateException("Timeouts e tamanho do lote devem ser positivos.");
        }
        if (unidadeEpoch == null || !("S".equals(unidadeEpoch) || "MS".equals(unidadeEpoch) || "US".equals(unidadeEpoch))) {
            throw new IllegalStateException("UNIDADEEPOCH deve ser confirmada antes da ativacao.");
        }
    }

    public long getIdConfig() { return idConfig; }
    public String getAmbiente() { return ambiente; }
    public String getBaseUrl() { return baseUrl; }
    public boolean isAtivo() { return ativo; }
    public boolean isSimulacao() { return simulacao; }
    public int getConnectTimeoutMs() { return connectTimeoutMs; }
    public int getReadTimeoutMs() { return readTimeoutMs; }
    public int getMaxTentativas() { return maxTentativas; }
    public int getTamanhoLote() { return tamanhoLote; }
    public String getUnidadeEpoch() { return unidadeEpoch; }
}
