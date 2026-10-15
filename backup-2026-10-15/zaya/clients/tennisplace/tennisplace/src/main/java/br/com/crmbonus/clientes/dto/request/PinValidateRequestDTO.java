package br.com.crmbonus.clientes.dto.request;

public class PinValidateRequestDTO {

    private String pin;
    private String user_id;

    public PinValidateRequestDTO() {
    }

    public PinValidateRequestDTO(String pin, String user_id) {
        this.pin = pin;
        this.user_id = user_id;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}

