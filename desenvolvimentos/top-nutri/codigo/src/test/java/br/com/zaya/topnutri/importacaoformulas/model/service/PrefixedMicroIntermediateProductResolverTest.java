package br.com.zaya.topnutri.importacaoformulas.model.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PrefixedMicroIntermediateProductResolverTest {
    private final PrefixedMicroIntermediateProductResolver resolver =
            new PrefixedMicroIntermediateProductResolver();

    @Test
    void shouldPrefixCanonicalProductCodeWithTwoHundred() {
        assertEquals("20022", resolver.findExclusiveIntermediateProduct("000022").orElse(null));
        assertEquals("200847", resolver.findExclusiveIntermediateProduct("847").orElse(null));
    }

    @Test
    void shouldRejectInvalidProductCode() {
        assertFalse(resolver.findExclusiveIntermediateProduct("ABC").isPresent());
        assertFalse(resolver.findExclusiveIntermediateProduct("0").isPresent());
    }
}
