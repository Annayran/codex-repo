package br.com.zaya.nac.planocontas.util;

import br.com.sankhya.jape.util.JapeSessionContext;
import java.math.BigDecimal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ContextoUsuarioJapeTest {

    private static final String PROPRIEDADE = "usuario_logado";

    @AfterEach
    void limparContexto() {
        JapeSessionContext.removeProperty(PROPRIEDADE);
    }

    @Test
    void deveAceitarUsuarioSupERemoverAoFinal() {
        try (ContextoUsuarioJape contexto = ContextoUsuarioJape.abrir(BigDecimal.ZERO)) {
            assertEquals(BigDecimal.ZERO, JapeSessionContext.getProperty(PROPRIEDADE));
        }

        assertNull(JapeSessionContext.getProperty(PROPRIEDADE));
    }

    @Test
    void deveRestaurarUsuarioAnterior() {
        JapeSessionContext.putProperty(PROPRIEDADE, new BigDecimal("15"));

        try (ContextoUsuarioJape contexto = ContextoUsuarioJape.abrir(BigDecimal.ZERO)) {
            assertEquals(BigDecimal.ZERO, JapeSessionContext.getProperty(PROPRIEDADE));
        }

        assertEquals(new BigDecimal("15"), JapeSessionContext.getProperty(PROPRIEDADE));
    }
}
