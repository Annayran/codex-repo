package br.com.zaya.mahospitalar.genesis.client;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GenesisCooldownTest {
    @Test void lePrazoPersistido() { assertEquals(120000, GenesisCooldown.prazoPersistidoMs("Falha: COOLDOWN_MS=120000; limite")); }
    @Test void mensagemLegadaUsaMinimo() { assertEquals(60000, GenesisCooldown.prazoPersistidoMs("limite")); }
    @Test void prazoPadrao() { assertEquals(60000, GenesisCooldown.prazoMs(null, null, 0)); }
    @Test void usaHeaderMaior() { assertEquals(120000, GenesisCooldown.prazoMs("120", "", 0)); }
    @Test void usaMensagem() { assertEquals(90000, GenesisCooldown.prazoMs(null, "after 90000 milliseconds", 0)); }
    @Test void headerInvalidoNaoReduzPrazo() { assertEquals(60000, GenesisCooldown.prazoMs("abc", "", 0)); }
    @Test void usaDataHttp() { assertEquals(120000, GenesisCooldown.prazoMs("Thu, 1 Jan 1970 00:02:00 GMT", "", 0)); }
}
