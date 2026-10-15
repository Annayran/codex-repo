package br.com.zaya.mahospitalar.genesis.client;

public class GenesisResponse {
    public enum Tipo { SUCESSO, ERRO_FUNCIONAL, TRANSITORIO }

    private final Tipo tipo;
    private final int httpStatus;
    private final String mensagem;
    private final String corpo;

    private GenesisResponse(Tipo tipo, int httpStatus, String mensagem, String corpo) {
        this.tipo = tipo;
        this.httpStatus = httpStatus;
        this.mensagem = mensagem;
        this.corpo = corpo;
    }

    public static GenesisResponse sucesso(int httpStatus, String corpo) {
        return new GenesisResponse(Tipo.SUCESSO, httpStatus, null, corpo);
    }

    public static GenesisResponse erroFuncional(int httpStatus, String mensagem) {
        return new GenesisResponse(Tipo.ERRO_FUNCIONAL, httpStatus, mensagem, null);
    }

    public static GenesisResponse transitorio(String mensagem) {
        return new GenesisResponse(Tipo.TRANSITORIO, 0, mensagem, null);
    }

    public boolean isSucesso() { return tipo == Tipo.SUCESSO; }
    public boolean isTransitorio() { return tipo == Tipo.TRANSITORIO; }
    public Tipo getTipo() { return tipo; }
    public int getHttpStatus() { return httpStatus; }
    public String getMensagem() { return mensagem; }
    public String getCorpo() { return corpo; }
}
