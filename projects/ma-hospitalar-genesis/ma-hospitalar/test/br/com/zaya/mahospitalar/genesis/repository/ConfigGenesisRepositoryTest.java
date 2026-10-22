package br.com.zaya.mahospitalar.genesis.repository;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConfigGenesisRepositoryTest {
    @Test void aceitaRaizOuApiSemDuplicarSufixo() {
        assertEquals("https://example.com/App/api/v1/", ConfigGenesisRepository.normalizarUrl(" https://example.com/App/ "));
        assertEquals("https://example.com/App/api/v1/", ConfigGenesisRepository.normalizarUrl("https://example.com/App/api/v1"));
        assertEquals("https://example.com/App/api/v1/", ConfigGenesisRepository.normalizarUrl("https://example.com/App/api/v1/"));
    }
    @Test void rejeitaConfiguracaoInseguraOuAusente() {
        for (String url : new String[]{null, " ", "invalida", "http://example.com/App", "https://user:secret@example.com/App", "https://example.com/App?token=x"}) {
            assertThrows(IllegalStateException.class, () -> ConfigGenesisRepository.normalizarUrl(url));
        }
    }
}
