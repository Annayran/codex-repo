package br.com.zaya.nac.planocontas.util;

import br.com.zaya.nac.planocontas.exception.RegraNegocioException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NumeroUtilsTest {

    @Test
    void deveConverterTextoNumerico() throws Exception {
        assertEquals(new BigDecimal("1"), NumeroUtils.paraBigDecimal("1", "CAMPO"));
    }

    @Test
    void deveConverterNumeroInteiro() throws Exception {
        assertEquals(new BigDecimal("1"), NumeroUtils.paraBigDecimal(1, "CAMPO"));
    }

    @Test
    void devePreservarBigDecimal() throws Exception {
        BigDecimal valor = new BigDecimal("22");
        assertEquals(valor, NumeroUtils.paraBigDecimal(valor, "CAMPO"));
    }

    @Test
    void deveRetornarNuloParaValorVazio() throws Exception {
        assertNull(NumeroUtils.paraBigDecimal(" ", "CAMPO"));
    }

    @Test
    void deveExplicarValorNaoNumerico() {
        assertThrows(
                RegraNegocioException.class,
                () -> NumeroUtils.paraBigDecimal("EMPRESA", "TCBEMP.CODINSTRESP"));
    }
}

