package br.com.zaya.topnutri.importacaoformulas.model.entity;

import java.util.Optional;

public enum ProductCategory {
    MICRONUTRIENT("M", "Micro"),
    OTHER("O", "Outro"),
    MACRONUTRIENT("A", "Macro"),
    PACKAGING("E", "EmbRotulo");

    private final String code;
    private final String materialType;

    ProductCategory(String code, String materialType) {
        this.code = code;
        this.materialType = materialType;
    }

    public String getCode() {
        return code;
    }

    /** Valor de TPRLMP.AD_TIPOMP correspondente a TGFPRO.AD_CATEGORIA. */
    public String getMaterialType() {
        return materialType;
    }

    public static Optional<ProductCategory> fromCode(String code) {
        for (ProductCategory category : values()) {
            if (category.code.equals(code)) {
                return Optional.of(category);
            }
        }
        return Optional.empty();
    }
}
