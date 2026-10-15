package tech.skala.action.integration.tangerino.model.sistema;

public class RespostaRequisicao {

	private Boolean isSucess;
    private String response;
    private Integer status;

    public RespostaRequisicao() {
    }

    public RespostaRequisicao(Boolean isSucess, String response, Integer status) {
        this.isSucess = isSucess;
        this.response = response;
        this.status = status;
    }

    public Boolean getSucess() {
        return isSucess;
    }

    public String getResponse() {
        return response;
    }

    public Integer getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "RespostaRequisicao{" +
                "isSucess=" + isSucess +
                ", response='" + response + '\'' +
                ", status=" + status +
                '}';
    }
}
