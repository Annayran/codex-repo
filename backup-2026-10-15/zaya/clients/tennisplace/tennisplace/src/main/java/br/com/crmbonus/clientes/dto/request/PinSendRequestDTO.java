package br.com.crmbonus.clientes.dto.request;

import java.math.BigDecimal;

public class PinSendRequestDTO {

    private CustomerDTO customer;
    private BigDecimal gross_value;
    private ItemDTO[] items;

    public PinSendRequestDTO() {
    }

    public PinSendRequestDTO(CustomerDTO customer, BigDecimal gross_value, ItemDTO[] items) {
        this.customer = customer;
        this.gross_value = gross_value;
        this.items = items;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public BigDecimal getGross_value() {
        return gross_value;
    }

    public void setGross_value(BigDecimal gross_value) {
        this.gross_value = gross_value;
    }

    public ItemDTO[] getItems() {
        return items;
    }

    public void setItems(ItemDTO[] items) {
        this.items = items;
    }

    public static class CustomerDTO {
        private BigDecimal country_code;
        private String name;
        private String phone;
        private String email;
        private String document_number;
        private String birth;
        private String gender;
        private String document_type;
        private String category_custom;
        // custom_data can be any object; keep as Object for flexibility
        private Object custom_data;

        public CustomerDTO() {
        }

        public CustomerDTO(BigDecimal country_code, String name, String phone, String email, String document_number) {
            this.country_code = country_code;
            this.name = name;
            this.phone = phone;
            this.email = email;
            this.document_number = document_number;
        }

        public BigDecimal getCountry_code() {
            return country_code;
        }

        public void setCountry_code(BigDecimal country_code) {
            this.country_code = country_code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getDocument_number() {
            return document_number;
        }

        public void setDocument_number(String document_number) {
            this.document_number = document_number;
        }

        public String getBirth() {
            return birth;
        }

        public void setBirth(String birth) {
            this.birth = birth;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getDocument_type() {
            return document_type;
        }

        public void setDocument_type(String document_type) {
            this.document_type = document_type;
        }

        public String getCategory_custom() {
            return category_custom;
        }

        public void setCategory_custom(String category_custom) {
            this.category_custom = category_custom;
        }

        public Object getCustom_data() {
            return custom_data;
        }

        public void setCustom_data(Object custom_data) {
            this.custom_data = custom_data;
        }
    }

    public static class ItemDTO {
        private String sku;
        private BigDecimal value_of;
        private BigDecimal value_per;
        private BigDecimal quantity;

        public ItemDTO() {
        }

        public ItemDTO(String sku, BigDecimal value_of, BigDecimal value_per, BigDecimal quantity) {
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

        public BigDecimal getValue_of() {
            return value_of;
        }

        public void setValue_of(BigDecimal value_of) {
            this.value_of = value_of;
        }

        public BigDecimal getValue_per() {
            return value_per;
        }

        public void setValue_per(BigDecimal value_per) {
            this.value_per = value_per;
        }

        public BigDecimal getQuantity() {
            return quantity;
        }

        public void setQuantity(BigDecimal quantity) {
            this.quantity = quantity;
        }
    }
}
