package br.com.zaya.topnutri.importacaoformulas.model.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ProductCategoryTest {
    @Test
    void shouldMapCategoryCodesToStoredMaterialTypes() {
        assertEquals("Micro", ProductCategory.fromCode("M").get().getMaterialType());
        assertEquals("Macro", ProductCategory.fromCode("A").get().getMaterialType());
        assertEquals("EmbRotulo", ProductCategory.fromCode("E").get().getMaterialType());
        assertEquals("Outro", ProductCategory.fromCode("O").get().getMaterialType());
    }

    @Test
    void shouldNotInventMappingForMissingOrUnknownCategory() {
        assertFalse(ProductCategory.fromCode(null).isPresent());
        assertFalse(ProductCategory.fromCode("").isPresent());
        assertFalse(ProductCategory.fromCode("Micro 2").isPresent());
        assertFalse(ProductCategory.fromCode("X").isPresent());
    }
}
