package br.com.crmbonus.clientes.dto.response;

public class UserVerifyResponse {

    private boolean resource_id;
    private String user_id;
    private boolean request_pin;

    public boolean isResource_id() {
        return resource_id;
    }

    public void setResource_id(boolean resource_id) {
        this.resource_id = resource_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public boolean isRequest_pin() {
        return request_pin;
    }

    public void setRequest_pin(boolean request_pin) {
        this.request_pin = request_pin;
    }
}