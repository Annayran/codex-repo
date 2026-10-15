package br.com.crmbonus.bonus.dto.request;

public class BonusCampaignsRequestDTO {

    private String sales_type;
    private String user_id;
    private TotalsDTO totals;

    public BonusCampaignsRequestDTO() {
    }

    public BonusCampaignsRequestDTO(String sales_type, String user_id, TotalsDTO totals) {
        this.sales_type = sales_type;
        this.user_id = user_id;
        this.totals = totals;
    }

    public String getSales_type() {
        return sales_type;
    }

    public void setSales_type(String sales_type) {
        this.sales_type = sales_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public TotalsDTO getTotals() {
        return totals;
    }

    public void setTotals(TotalsDTO totals) {
        this.totals = totals;
    }

    public static class TotalsDTO {
        private double total;

        public TotalsDTO() {
        }

        public TotalsDTO(double total) {
            this.total = total;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }
    }
}

