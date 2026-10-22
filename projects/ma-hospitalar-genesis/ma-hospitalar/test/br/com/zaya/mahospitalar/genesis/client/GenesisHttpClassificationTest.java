package br.com.zaya.mahospitalar.genesis.client;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GenesisHttpClassificationTest {
    @Test void falhasTransitoriasSaoReprocessaveis() {
        for (int http : new int[]{408,429,500,502,503,504})
            assertEquals(GenesisApiResult.Classificacao.TRANSITORIO,GenesisHttpClient.classificar(http));
    }
    @Test void errosFuncionaisNaoEntramEmLoop() {
        for (int http : new int[]{302,400,405,422})
            assertEquals(GenesisApiResult.Classificacao.ERRO_FUNCIONAL,GenesisHttpClient.classificar(http));
        assertEquals(GenesisApiResult.Classificacao.AUTENTICACAO,GenesisHttpClient.classificar(401));
        assertEquals(GenesisApiResult.Classificacao.AUTENTICACAO,GenesisHttpClient.classificar(403));
        assertEquals(GenesisApiResult.Classificacao.CONFLITO,GenesisHttpClient.classificar(409));
    }
}
