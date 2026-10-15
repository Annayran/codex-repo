package br.com.crmbonus.bonus.dto.response;

public class BonusSubmitResponseDTO {

    private String status;
    private String message;
    private double discount_applied;

    public BonusSubmitResponseDTO() {
    }

    public BonusSubmitResponseDTO(String status, String message, double discount_applied) {
        this.status = status;
        this.message = message;
        this.discount_applied = discount_applied;
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

    public double getDiscount_applied() {
        return discount_applied;
    }

    public void setDiscount_applied(double discount_applied) {
        this.discount_applied = discount_applied;
    }
}

