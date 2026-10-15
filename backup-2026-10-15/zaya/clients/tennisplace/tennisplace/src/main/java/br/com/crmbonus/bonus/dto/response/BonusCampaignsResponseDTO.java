package br.com.crmbonus.bonus.dto.response;

public class BonusCampaignsResponseDTO {

    private CampaignDTO[] campaigns;
    private String message;

    public BonusCampaignsResponseDTO() {
    }

    public BonusCampaignsResponseDTO(CampaignDTO[] campaigns, String message) {
        this.campaigns = campaigns;
        this.message = message;
    }

    public CampaignDTO[] getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(CampaignDTO[] campaigns) {
        this.campaigns = campaigns;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class CampaignDTO {
        private String campaign_id;
        private String campaign_name;
        private double discount_percentage;
        private String description;

        public CampaignDTO() {
        }

        public CampaignDTO(String campaign_id, String campaign_name, double discount_percentage, String description) {
            this.campaign_id = campaign_id;
            this.campaign_name = campaign_name;
            this.discount_percentage = discount_percentage;
            this.description = description;
        }

        public String getCampaign_id() {
            return campaign_id;
        }

        public void setCampaign_id(String campaign_id) {
            this.campaign_id = campaign_id;
        }

        public String getCampaign_name() {
            return campaign_name;
        }

        public void setCampaign_name(String campaign_name) {
            this.campaign_name = campaign_name;
        }

        public double getDiscount_percentage() {
            return discount_percentage;
        }

        public void setDiscount_percentage(double discount_percentage) {
            this.discount_percentage = discount_percentage;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}

