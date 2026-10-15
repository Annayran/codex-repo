package br.com.crmbonus.bonus.dto.response;

public class BonusAvailableResponseDTO {

    private double available_value;
    private String message;

    public BonusAvailableResponseDTO() {
    }

    public BonusAvailableResponseDTO(double available_value, String message) {
        this.available_value = available_value;
        this.message = message;
    }

    public double getAvailable_value() {
        return available_value;
    }

    public void setAvailable_value(double available_value) {
        this.available_value = available_value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

