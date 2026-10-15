package br.com.crmbonus.clientes.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PinSendResponseDTO {

    private boolean status;
    private String message;
    private Data data;

    // also accept top-level fields because API sometimes returns flat JSON
    private String user_id;
    private boolean request_pin;
    private String text_pin;

    public PinSendResponseDTO() {
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    // top-level accessors (fallback)
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

    public String getText_pin() {
        return text_pin;
    }

    public void setText_pin(String text_pin) {
        this.text_pin = text_pin;
    }

    public static class Data {
        private String user_id;
        private boolean request_pin;
        private String text_pin;

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

        public String getText_pin() {
            return text_pin;
        }

        public void setText_pin(String text_pin) {
            this.text_pin = text_pin;
        }
    }
}
