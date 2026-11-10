package br.com.zaya.topnutri.importacaoformulas.model.dto;

import java.math.BigDecimal;
import java.util.Objects;

public final class SimulatedMaterial {
    private final String role;
    private final String productCode;
    private final String description;
    private final BigDecimal quantity;
    private final BigDecimal sourceQuantity;
    private final String volume;
    private final String source;

    public SimulatedMaterial(
            String role,
            String productCode,
            String description,
            BigDecimal quantity,
            BigDecimal sourceQuantity,
            String volume,
            String source) {
        this.role = Objects.requireNonNull(role, "role");
        this.productCode = Objects.requireNonNull(productCode, "productCode");
        this.description = Objects.requireNonNull(description, "description");
        this.quantity = Objects.requireNonNull(quantity, "quantity");
        this.sourceQuantity = sourceQuantity;
        this.volume = Objects.requireNonNull(volume, "volume");
        this.source = Objects.requireNonNull(source, "source");
    }

    public String getRole() { return role; }
    public String getProductCode() { return productCode; }
    public String getDescription() { return description; }
    public BigDecimal getQuantity() { return quantity; }
    public BigDecimal getSourceQuantity() { return sourceQuantity; }
    public String getVolume() { return volume; }
    public String getSource() { return source; }
}
