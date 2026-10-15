package br.com.crmbonus.clientes.dto.request;

public class PinResendRequestDTO {

    private String user_id;

    public PinResendRequestDTO() {
    }

    public PinResendRequestDTO(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}

