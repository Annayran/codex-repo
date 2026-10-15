package br.com.crmbonus.bonus.dto.request;

public class BonusAvailableRequestDTO {

    private String user_id;
    private double value;
    private ItemDTO[] items;

    public BonusAvailableRequestDTO() {
    }

    public BonusAvailableRequestDTO(String user_id, double value, ItemDTO[] items) {
        this.user_id = user_id;
        this.value = value;
        this.items = items;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public ItemDTO[] getItems() {
        return items;
    }

    public void setItems(ItemDTO[] items) {
        this.items = items;
    }

    public static class ItemDTO {
        private String sku;
        private double value_of;
        private double value_per;
        private int quantity;

        public ItemDTO() {
        }

        public ItemDTO(String sku, double value_of, double value_per, int quantity) {
            this.sku = sku;
            this.value_of = value_of;
            this.value_per = value_per;
            this.quantity = quantity;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public double getValue_of() {
            return value_of;
        }

        public void setValue_of(double value_of) {
            this.value_of = value_of;
        }

        public double getValue_per() {
            return value_per;
        }

        public void setValue_per(double value_per) {
            this.value_per = value_per;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}

