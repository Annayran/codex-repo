package br.com.zaya.mahospitalar.genesis.tools;

import br.com.zaya.mahospitalar.genesis.client.GenesisHttpClient;
import br.com.zaya.mahospitalar.genesis.client.GenesisResponse;
import br.com.zaya.mahospitalar.genesis.config.ParametrosGenesis;
import br.com.zaya.mahospitalar.genesis.domain.MovimentoEstoque;
import br.com.zaya.mahospitalar.genesis.dto.EstoqueGenesisDTO;
import br.com.zaya.mahospitalar.genesis.service.PayloadBuilder;
import br.com.zaya.mahospitalar.genesis.support.JsonUtil;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class GenesisPostLinhaViewMain {
    public static void main(String[] args) {
        String baseUrl = envOrDefault("GENESIS_BASE_URL", "https://sistema.genesis-s.com:7818/G4VAPI");
        String token = requiredEnv("GENESIS_TOKEN");

        PayloadBuilder builder = new PayloadBuilder();
        MovimentoEstoque movimento = montarLinhaExportadaDaView();
        List<String> faltantes = builder.validar(movimento);
        if (!faltantes.isEmpty()) {
            throw new IllegalStateException("Linha da view nao possui dados minimos para o POST: " + faltantes);
        }

        EstoqueGenesisDTO dto = builder.montar(movimento);
        System.out.println("Payload que sera enviado para Genesis:");
        System.out.println(JsonUtil.toJson(dto));

        if (!"true".equalsIgnoreCase(env("GENESIS_POST_CONFIRM"))) {
            System.out.println("POST nao executado. Defina GENESIS_POST_CONFIRM=true para enviar de fato.");
            return;
        }

        GenesisHttpClient client = new GenesisHttpClient(
                new ParametrosGenesis(baseUrl, token, Collections.<Integer>emptyList()));
        GenesisResponse response = client.enviarEstoque(dto);

        System.out.println("Resultado POST Genesis:");
        System.out.println("tipo=" + response.getTipo());
        System.out.println("httpStatus=" + response.getHttpStatus());
        System.out.println("mensagem=" + response.getMensagem());

        if (!response.isSucesso()) {
            throw new IllegalStateException("POST Genesis falhou: HTTP "
                    + response.getHttpStatus() + " - " + response.getMensagem());
        }
    }

    private static MovimentoEstoque montarLinhaExportadaDaView() {
        MovimentoEstoque movimento = new MovimentoEstoque();
        movimento.setCodEmpresa(Integer.valueOf(1));
        movimento.setNomeFantasia("MA HOSPITALAR - 0101");
        movimento.setRazaoEmpresa("MONTEIRO ANTUNES INSUMOS HOSPITALARES SO");
        movimento.setCnpjEmpresa("04078043000140");
        movimento.setNumUnico(Long.valueOf(42229L));
        movimento.setNumNota(Long.valueOf(3L));
        movimento.setDataNegociacao(dataUtc(2026, Calendar.JUNE, 26));
        movimento.setDataInstalacao(dataUtc(2026, Calendar.JUNE, 26));
        movimento.setCodTipoOperacao(Integer.valueOf(1308));
        movimento.setCodParceiro(Integer.valueOf(18019));
        movimento.setNomeParceiro("WELCH ALLYN INC");
        movimento.setRazaoParceiro("WELCH ALLYN INC");
        movimento.setCnpjCpfParceiro("99999999999999");
        movimento.setParcAtivo("Ativo");
        movimento.setCodProduto("995");
        movimento.setProduto("995 - MONITOR CSM 7500 PNI SP02 NELLCOR TEMP SURET WIFI");
        movimento.setGrupoProduto("0 - <SEM GRUPO>");
        movimento.setMarca(null);
        movimento.setRgAnvisa(null);
        movimento.setSerie("LOT1");
        movimento.setProdAtivo("Ativo");
        movimento.setPoder("PROPRIO");
        movimento.setGarantiaMeses(null);
        movimento.setStatusEstoque("O");
        movimento.setQuantidade(BigDecimal.ONE);
        movimento.setValorUnitario(new BigDecimal("5189.20"));
        movimento.setLocalizacao("EM TRANSITO");
        movimento.setChaveMovimento("1|42229|1|995|LOT1");
        movimento.setTipoMovimento("A_CONFIRMAR");
        return movimento;
    }

    private static Date dataUtc(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();
        calendar.set(year, month, day, 0, 0, 0);
        return calendar.getTime();
    }

    private static String requiredEnv(String name) {
        String value = env(name);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException("Variavel de ambiente obrigatoria nao definida: " + name);
        }
        return value;
    }

    private static String envOrDefault(String name, String defaultValue) {
        String value = env(name);
        return value == null || value.trim().isEmpty() ? defaultValue : value;
    }

    private static String env(String name) {
        return System.getenv(name);
    }
}