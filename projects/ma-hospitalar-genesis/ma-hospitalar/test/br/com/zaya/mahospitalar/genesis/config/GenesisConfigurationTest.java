package br.com.zaya.mahospitalar.genesis.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GenesisConfigurationTest {
    @Test void exigeEpochUrlEAtivacaoExplicitos() {
        assertThrows(IllegalStateException.class, () ->
                new ParametrosGenesis(1,"HML","CONFIGURAR_URL_HOMOLOGACAO",true,true,1,1,1,1,null).validarParaExecucao());
        assertDoesNotThrow(() ->
                new ParametrosGenesis(1,"HML","https://example.invalid/api",true,true,1,1,1,1,"MS").validarParaExecucao());
    }

    @Test void segredoPodeSerProvisionadoPorPropriedadeSemEntrarNoArtefato() {
        String anterior = System.getProperty(EnvironmentGenesisSecretProvider.SYSTEM_PROPERTY);
        try {
            System.setProperty(EnvironmentGenesisSecretProvider.SYSTEM_PROPERTY,"token-de-teste");
            assertEquals("token-de-teste", new EnvironmentGenesisSecretProvider().obterToken());
        } finally {
            if (anterior == null) System.clearProperty(EnvironmentGenesisSecretProvider.SYSTEM_PROPERTY);
            else System.setProperty(EnvironmentGenesisSecretProvider.SYSTEM_PROPERTY, anterior);
        }
    }
}
