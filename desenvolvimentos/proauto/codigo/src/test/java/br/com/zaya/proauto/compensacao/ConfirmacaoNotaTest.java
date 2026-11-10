package br.com.zaya.proauto.compensacao;

import org.junit.Test;
import static org.junit.Assert.*;

public class ConfirmacaoNotaTest {
    @Test public void confirmaVenda() {
        assertTrue(ConfirmacaoNota.deveExecutar("V", "A", "L"));
        assertTrue(ConfirmacaoNota.deveExecutar("V", "P", "L"));
    }
    @Test public void ignoraEdicaoEEstorno() {
        assertFalse(ConfirmacaoNota.deveExecutar("V", "L", "L"));
        assertFalse(ConfirmacaoNota.deveExecutar("V", "L", "A"));
        assertFalse(ConfirmacaoNota.deveExecutar("V", "A", "A"));
    }
    @Test public void ignoraOutrosMovimentosEInsercao() {
        assertFalse(ConfirmacaoNota.deveExecutar("C", "A", "L"));
        assertFalse(ConfirmacaoNota.deveExecutar("D", "A", "L"));
        assertFalse(ConfirmacaoNota.deveExecutar("V", null, "L"));
    }
}
