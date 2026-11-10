package br.com.zaya.topnutri.importacaoformulas.sankhya.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MaterialStockControlPolicyTest {
    private final MaterialStockControlPolicy policy = new MaterialStockControlPolicy();

    @Test
    void shouldUseLiteralWithoutControlWhenMaterialHasNoAdditionalControl() {
        MaterialStockControlPolicy.Selection selection = policy.resolve("N", "N", null, null);
        assertEquals("L", selection.getType());
        assertNull(selection.getControl());
    }

    @Test
    void shouldUseAutomaticWithoutLiteralValueForLotControlledMaterial() {
        MaterialStockControlPolicy.Selection selection = policy.resolve("N", "L", null, null);
        assertEquals("I", selection.getType());
        assertNull(selection.getControl());
    }

    @Test
    void shouldKeepLiteralListControlWhenItExistsInCopiedVersion() {
        MaterialStockControlPolicy.Selection selection = policy.resolve("N", "S", "L", "AZUL");
        assertEquals("L", selection.getType());
        assertEquals("AZUL", selection.getControl());
    }

    @Test
    void shouldInheritListControlWhenBothProductsUseLists() {
        MaterialStockControlPolicy.Selection selection = policy.resolve("S", "S", null, null);
        assertEquals("H", selection.getType());
        assertNull(selection.getControl());
    }

    @Test
    void shouldUseAutomaticForNewSerialControlledMaterial() {
        MaterialStockControlPolicy.Selection selection = policy.resolve("N", "E", null, null);
        assertEquals("I", selection.getType());
        assertNull(selection.getControl());
    }

    @Test
    void shouldRejectListControlThatCannotBeDetermined() {
        assertThrows(IllegalStateException.class,
                () -> policy.resolve("N", "S", null, null));
    }
}
