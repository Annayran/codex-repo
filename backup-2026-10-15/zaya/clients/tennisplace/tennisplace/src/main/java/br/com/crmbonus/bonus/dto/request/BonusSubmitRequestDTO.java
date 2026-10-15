package br.com.crmbonus.bonus.dto.request;

public class BonusSubmitRequestDTO {

    private String user_id;
    private String campaign_id;
    private String internal_order_id;

    public BonusSubmitRequestDTO() {
    }

    public BonusSubmitRequestDTO(String user_id, String campaign_id, String internal_order_id) {
        this.user_id = user_id;
        this.campaign_id = campaign_id;
        this.internal_order_id = internal_order_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(String campaign_id) {
        this.campaign_id = campaign_id;
    }

    public String getInternal_order_id() {
        return internal_order_id;
    }

    public void setInternal_order_id(String internal_order_id) {
        this.internal_order_id = internal_order_id;
    }
}

