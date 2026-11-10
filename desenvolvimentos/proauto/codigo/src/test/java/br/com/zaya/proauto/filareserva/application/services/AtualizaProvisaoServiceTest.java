package br.com.zaya.proauto.filareserva.application.services;

import br.com.zaya.proauto.filareserva.application.ports.AtualizacaoProvisaoGateway;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AtualizaProvisaoServiceTest {
    @Test
    public void deveDelegarAtualizacaoUmaUnicaVez() throws Exception {
        GatewayFake gateway = new GatewayFake();
        AtualizaProvisaoService service = new AtualizaProvisaoService(gateway);

        int processados = service.executar();

        assertEquals(1, gateway.execucoes);
        assertEquals(7, processados);
    }

    @Test
    public void devePropagarFalhaDoGateway() {
        AtualizaProvisaoService service = new AtualizaProvisaoService(new GatewayComFalha());

        try {
            service.executar();
            fail("Era esperada a propagacao da falha do banco.");
        } catch (Exception exception) {
            assertEquals("falha controlada", exception.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void deveRejeitarGatewayNulo() {
        new AtualizaProvisaoService(null);
    }

    private static final class GatewayFake implements AtualizacaoProvisaoGateway {
        private int execucoes;

        @Override
        public int atualizarProvisoes() {
            execucoes++;
            return 7;
        }
    }

    private static final class GatewayComFalha implements AtualizacaoProvisaoGateway {
        @Override
        public int atualizarProvisoes() throws Exception {
            throw new Exception("falha controlada");
        }
    }
}
