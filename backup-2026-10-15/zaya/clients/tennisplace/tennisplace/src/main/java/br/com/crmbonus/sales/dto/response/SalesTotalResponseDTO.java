package br.com.crmbonus.sales.dto.response;

public class SalesTotalResponseDTO {

    private String sale_id;
    private String status;
    private String message;

    public SalesTotalResponseDTO() {
    }

    public SalesTotalResponseDTO(String sale_id, String status, String message) {
        this.sale_id = sale_id;
        this.status = status;
        this.message = message;
    }

    public String getSale_id() {
        return sale_id;
    }

    public void setSale_id(String sale_id) {
        this.sale_id = sale_id;
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

