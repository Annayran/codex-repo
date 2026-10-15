package br.com.crmbonus.sales.dto.request;

public class SalesCancelRequestDTO {

    private String internal_order_id;

    public SalesCancelRequestDTO() {
    }

    public SalesCancelRequestDTO(String internal_order_id) {
        this.internal_order_id = internal_order_id;
    }

    public String getInternal_order_id() {
        return internal_order_id;
    }

    public void setInternal_order_id(String internal_order_id) {
        this.internal_order_id = internal_order_id;
    }
}

