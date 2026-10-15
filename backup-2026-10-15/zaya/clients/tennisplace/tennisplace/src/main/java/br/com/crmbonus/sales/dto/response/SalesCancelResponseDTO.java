package br.com.crmbonus.sales.dto.response;

public class SalesCancelResponseDTO {

    private String status;
    private String message;

    public SalesCancelResponseDTO() {
    }

    public SalesCancelResponseDTO(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

