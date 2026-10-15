package br.com.zaya.mahospitalar.genesis.client;

import br.com.zaya.mahospitalar.genesis.config.ParametrosGenesis;
import br.com.zaya.mahospitalar.genesis.domain.MovimentoEstoque;
import br.com.zaya.mahospitalar.genesis.service.PayloadBuilder;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@Tag("smoke")
class GenesisApiViewRowPostSmokeTest {
    @Test
    void deveEnviarUmaLinhaDaViewGenesisEstoqueQuandoHabilitado() {
        assumeTrue("true".equalsIgnoreCase(env("GENESIS_SMOKE_ENABLED")),
                "Smoke test desabilitado. Defina GENESIS_SMOKE_ENABLED=true para executar.");
        assumeTrue("true".equalsIgnoreCase(env("GENESIS_VIEW_ROW_POST_ENABLED")),
                "POST de linha da view desabilitado. Defina GENESIS_VIEW_ROW_POST_ENABLED=true para executar.");

        String baseUrl = requiredEnv("GENESIS_BASE_URL");
        String token = requiredEnv("GENESIS_TOKEN");

        PayloadBuilder builder = new PayloadBuilder();
        MovimentoEstoque movimento = montarMovimentoDaView();
        List<String> faltantes = builder.validar(movimento);
        assertTrue(faltantes.isEmpty(), "Linha da view nao possui dados minimos para o POST: " + faltantes);

        GenesisHttpClient client = new GenesisHttpClient(
                new ParametrosGenesis(baseUrl, token, Collections.<Integer>emptyList()));

        GenesisResponse create = client.enviarEstoque(builder.montar(movimento));
        assertTrue(create.isSucesso(), "Genesis deveria criar estoque com HTTP 201, mas retornou: "
                + create.getTipo() + " HTTP " + create.getHttpStatus() + " - " + create.getMensagem());
    }

    private MovimentoEstoque montarMovimentoDaView() {
        MovimentoEstoque movimento = new MovimentoEstoque();
        movimento.setCodEmpresa(integerEnvOrNull("GENESIS_VIEW_CODEMP"));
        movimento.setNumUnico(longEnvOrNull("GENESIS_VIEW_NUNOTA"));
        movimento.setNumNota(longEnvOrNull("GENESIS_VIEW_NUMNOTA"));
        movimento.setCodTipoOperacao(integerEnvOrNull("GENESIS_VIEW_CODTIPOPER"));
        movimento.setCodParceiro(integerEnvOrNull("GENESIS_VIEW_CODPARC"));
        movimento.setNomeParceiro(envOrDefault("GENESIS_VIEW_NOMEPARC", null));
        movimento.setCnpjCpfParceiro(envOrDefault("GENESIS_VIEW_CNPJ_CPF_PARCEIRO", null));
        movimento.setCodProduto(envOrDefault("GENESIS_VIEW_CODPROD", null));
        movimento.setProduto(requiredEnv("GENESIS_VIEW_PRODUTO"));
        movimento.setGrupoProduto(envOrDefault("GENESIS_VIEW_GRUPO_PRODUTO", null));
        movimento.setMarca(envOrDefault("GENESIS_VIEW_MARCA", null));
        movimento.setRgAnvisa(envOrDefault("GENESIS_VIEW_CODANVISA", null));
        movimento.setSerie(requiredEnv("GENESIS_VIEW_SERIE"));
        movimento.setPoder(envOrDefault("GENESIS_VIEW_PODER", null));
        movimento.setGarantiaMeses(integerEnvOrNull("GENESIS_VIEW_GARANTIA_MESES"));
        movimento.setStatusEstoque(envOrDefault("GENESIS_VIEW_STATUS_ESTOQUE", "O"));
        movimento.setQuantidade(decimalEnvOrDefault("GENESIS_VIEW_QTD", "1"));
        movimento.setValorUnitario(decimalEnvOrDefault("GENESIS_VIEW_VLRUNIT", "1.00"));
        movimento.setLocalizacao(envOrDefault("GENESIS_VIEW_DESCRLOCAL", null));
        movimento.setChaveMovimento(envOrDefault("GENESIS_VIEW_CHAVE_MOVIMENTO", null));
        movimento.setDataInstalacao(dateFromEpochSecondsOrNow("GENESIS_VIEW_DTINSTALACAO_EPOCH"));
        movimento.setIdCategoria(longEnvOrNull("GENESIS_VIEW_ID_CATEGORIA"));
        movimento.setIdFabricante(longEnvOrNull("GENESIS_VIEW_ID_FABRICANTE"));
        movimento.setIdFornecedor(longEnvOrNull("GENESIS_VIEW_ID_FORNECEDOR"));
        movimento.setIdSubCategoria(longEnvOrNull("GENESIS_VIEW_ID_SUBCATEGORIA"));
        return movimento;
    }

    private Date dateFromEpochSecondsOrNow(String name) {
        Long epochSeconds = longEnvOrNull(name);
        if (epochSeconds == null) {
            return new Date();
        }
        return new Date(epochSeconds.longValue() * 1000L);
    }

    private BigDecimal decimalEnvOrDefault(String name, String defaultValue) {
        String value = envOrDefault(name, defaultValue);
        return new BigDecimal(value.replace(',', '.'));
    }

    private Integer integerEnvOrNull(String name) {
        String value = env(name);
        return value == null || value.trim().isEmpty() ? null : Integer.valueOf(value.trim());
    }

    private Long longEnvOrNull(String name) {
        String value = env(name);
        return value == null || value.trim().isEmpty() ? null : Long.valueOf(value.trim());
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