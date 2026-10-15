package br.com.zaya.mahospitalar.genesis.client;

import br.com.zaya.mahospitalar.genesis.config.ParametrosGenesis;
import br.com.zaya.mahospitalar.genesis.dto.EstoqueGenesisDTO;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@Tag("smoke")
class GenesisApiSmokeTest {
    @Test
    void deveAutenticarNaApiGenesisQuandoHabilitado() {
        assumeTrue("true".equalsIgnoreCase(env("GENESIS_SMOKE_ENABLED")),
                "Smoke test desabilitado. Defina GENESIS_SMOKE_ENABLED=true para executar.");

        String baseUrl = requiredEnv("GENESIS_BASE_URL");
        String token = requiredEnv("GENESIS_TOKEN");

        GenesisHttpClient client = new GenesisHttpClient(
                new ParametrosGenesis(baseUrl, token, Collections.<Integer>emptyList()));

        GenesisResponse auth = client.validarAutenticacao();
        assertTrue(auth.isSucesso(), "Genesis deveria autenticar com HTTP 200, mas retornou: "
                + auth.getTipo() + " HTTP " + auth.getHttpStatus() + " - " + auth.getMensagem());

        if ("true".equalsIgnoreCase(env("GENESIS_SMOKE_CREATE_ENABLED"))) {
            GenesisResponse create = client.enviarEstoque(montarPayloadTeste());
            assertTrue(create.isSucesso(), "Genesis deveria criar estoque com HTTP 201, mas retornou: "
                    + create.getTipo() + " HTTP " + create.getHttpStatus() + " - " + create.getMensagem());
        }
    }

    private EstoqueGenesisDTO montarPayloadTeste() {
        String suffix = String.valueOf(System.currentTimeMillis());
        EstoqueGenesisDTO dto = new EstoqueGenesisDTO();
        dto.setCodigo(envOrDefault("GENESIS_SMOKE_CODIGO", "PRD-CODEX"));
        dto.setCodigoExterno("SMOKE-CODEX-" + suffix);
        dto.setDescTecnica("TESTE CODEX - INTEGRACAO SANKHYA GENESIS");
        dto.setNserie(envOrDefault("GENESIS_SMOKE_SERIE", "SER-CODEX-" + suffix));
        dto.setStatus("O");
        dto.setRgAnvisa(envOrDefault("GENESIS_SMOKE_ANVISA", "00000000000"));
        dto.setPeriodoGarantia("12");
        dto.setQtd(java.math.BigDecimal.ONE);
        dto.setVlrVenda(new java.math.BigDecimal("1.00"));
        dto.setControlarEstoque(Boolean.TRUE);
        dto.setDtEntrada(System.currentTimeMillis() / 1000L);
        return dto;
    }

    private String requiredEnv(String name) {
        String value = env(name);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException("Variavel de ambiente obrigatoria nao definida: " + name);
        }
        return value;
    }

    private String envOrDefault(String name, String defaultValue) {
        String value = env(name);
        return value == null || value.trim().isEmpty() ? defaultValue : value;
    }

    private String env(String name) {
        return System.getenv(name);
    }
}