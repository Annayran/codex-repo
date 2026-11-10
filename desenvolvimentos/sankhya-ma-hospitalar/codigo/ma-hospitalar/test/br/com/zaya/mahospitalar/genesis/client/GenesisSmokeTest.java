package br.com.zaya.mahospitalar.genesis.client;

import br.com.zaya.mahospitalar.genesis.config.ParametrosGenesis;
import br.com.zaya.mahospitalar.genesis.domain.TipoEntidade;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("smoke")
class GenesisSmokeTest {
    @Test void consultaReadOnlyOptIn() throws Exception {
        String url = System.getProperty("mah.genesis.smoke.url");
        String codigo = System.getProperty("mah.genesis.smoke.codigoExterno");
        Assumptions.assumeTrue(url != null && codigo != null);
        ParametrosGenesis p = new ParametrosGenesis(1,"HML",url,true,false,10000,20000,1,1,"MS");
        GenesisApiResult result = new GenesisHttpClient(p, new br.com.zaya.mahospitalar.genesis.config.EnvironmentGenesisSecretProvider())
                .buscarPorCodigoExterno(TipoEntidade.CLIENTE,codigo);
        assertNotNull(result.getClassificacao());
    }
}
