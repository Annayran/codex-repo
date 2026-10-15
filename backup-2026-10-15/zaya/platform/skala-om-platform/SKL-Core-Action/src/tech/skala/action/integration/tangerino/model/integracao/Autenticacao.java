package tech.skala.action.integration.tangerino.model.integracao;

public class Autenticacao {
    private String url;
    private String token;
    private String endpoint;

    public Autenticacao() {
    }

    public Autenticacao(String url, String token) {
        this.url = url;
        this.token = token;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getUrlCompleta(){
        return getUrl()+getEndpoint();
    }

    @Override
    public String toString() {
        return "Autenticacao{" +
                "url='" + url + '\'' +
                ", token='" + token + '\'' +
                ", endpoint='" + endpoint + '\'' +
                '}';
    }
}
