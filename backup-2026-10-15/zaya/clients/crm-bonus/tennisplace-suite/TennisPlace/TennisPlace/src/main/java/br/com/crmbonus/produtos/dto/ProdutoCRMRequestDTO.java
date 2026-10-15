package br.com.crmbonus.produtos.dto;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ProdutoCRMRequestDTO {

    public String sku;
    public String ean;
    public String name;
    public String description;
    public String unit_measurement;
    public String manufacturer;
    public Price price;
    public Map<String, String> categories = new HashMap<>();
    public Map<String, String> metadata = new HashMap<>();
    public Boolean status;

    public static class Price {
        public BigDecimal value_of;
        public BigDecimal value_per;
        public String currency;
    }
}