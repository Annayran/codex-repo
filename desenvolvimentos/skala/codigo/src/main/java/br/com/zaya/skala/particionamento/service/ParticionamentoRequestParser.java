package br.com.zaya.skala.particionamento.service;

import br.com.zaya.skala.particionamento.model.ParticionamentoItemConfig;
import br.com.zaya.skala.particionamento.model.ParticionamentoOeConfig;
import br.com.zaya.skala.particionamento.model.ParticionamentoRequest;
import br.com.zaya.skala.particionamento.util.NumericUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ParticionamentoRequestParser {
    private final ObjectMapper mapper = new ObjectMapper();

    public ParticionamentoRequest parse(String payload) throws Exception {
        ParticionamentoRequest request = new ParticionamentoRequest();
        if (payload == null || payload.trim().isEmpty()) {
            return request;
        }

        JsonNode root = mapper.readTree(payload);
        request.setOrigemModal(true);
        request.setCapacidadePaletes(doubleValue(root, "capacidadePaletes", "qtdPaletes", "qtdePaletes"));
        request.setModoParticionamento(textValue(root, "modoParticionamento", "modo"));
        request.setObservacao(textValue(root, "observacao"));

        preencherNuNotas(root, request);
        preencherItens(root, request);
        preencherOes(root, request);

        return request;
    }

    private void preencherNuNotas(JsonNode root, ParticionamentoRequest request) {
        JsonNode nuNotas = root.get("nuNotas");
        if (nuNotas != null && nuNotas.isArray()) {
            for (JsonNode node : nuNotas) {
                request.addNuNota(intValue(node));
            }
        }
        request.addNuNota(intValue(root.get("nuNota")));
    }

    private void preencherItens(JsonNode root, ParticionamentoRequest request) {
        JsonNode itens = root.get("itens");
        if (itens == null || !itens.isArray()) {
            itens = root.get("linhasSelecionadas");
        }
        if (itens == null || !itens.isArray()) {
            return;
        }

        for (JsonNode node : itens) {
            ParticionamentoItemConfig item = new ParticionamentoItemConfig();
            item.setNuNota(intValue(first(node, "nunota", "nuNota", "NUNOTA")));
            item.setSequencia(intValue(first(node, "sequencia", "seq", "SEQUENCIA")));
            item.setCodProd(textValue(node, "codProd", "CODPROD"));
            item.setSelecionado(booleanValue(node, true, "selecionado", "usar", "selected"));
            item.setPriorizado(booleanValue(node, false, "priorizado", "prioridade", "priority"));
            item.setOrdemPrioridade(intValue(first(node, "ordemPrioridade", "ordem", "prioridadeOrdem")));
            item.setGrupo(textValue(node, "codFamilia", "grupo", "grupoMix"));
            request.addItem(item);
        }
    }

    private void preencherOes(JsonNode root, ParticionamentoRequest request) {
        JsonNode oes = root.get("oes");
        if (oes == null || !oes.isArray()) {
            oes = root.get("oep");
        }
        if (oes == null || !oes.isArray()) {
            oes = root.get("agendamentos");
        }
        if (oes == null || !oes.isArray()) {
            return;
        }

        for (JsonNode node : oes) {
            ParticionamentoOeConfig oe = new ParticionamentoOeConfig();
            oe.setNumero(intValue(first(node, "numero", "oep", "sequencia")));
            oe.setNuNotaOrigem(intValue(first(node, "nuNotaOrigem", "nunotaOrigem", "NUNOTA_ORIGEM")));
            oe.setDhAgendamento(textValue(node, "adDhAgendamento", "dhAgendamento", "AD_DHAGENDAMENTO"));
            request.addOe(oe);
        }
    }

    private JsonNode first(JsonNode node, String... fields) {
        if (node == null) {
            return null;
        }
        for (String field : fields) {
            JsonNode value = node.get(field);
            if (value != null && !value.isNull()) {
                return value;
            }
        }
        return null;
    }

    private String textValue(JsonNode node, String... fields) {
        JsonNode value = first(node, fields);
        return value == null || value.isNull() ? null : value.asText();
    }

    private int intValue(JsonNode node) {
        if (node == null || node.isNull()) {
            return 0;
        }
        if (node.isNumber()) {
            return node.asInt();
        }
        return (int) NumericUtils.toDouble(node.asText());
    }

    private double doubleValue(JsonNode node, String... fields) {
        JsonNode value = first(node, fields);
        if (value == null || value.isNull()) {
            return 0.0;
        }
        if (value.isNumber()) {
            return value.asDouble();
        }
        return NumericUtils.toDouble(value.asText());
    }

    private boolean booleanValue(JsonNode node, boolean defaultValue, String... fields) {
        JsonNode value = first(node, fields);
        if (value == null || value.isNull()) {
            return defaultValue;
        }
        if (value.isBoolean()) {
            return value.asBoolean();
        }
        String text = value.asText();
        if (text == null) {
            return defaultValue;
        }
        text = text.trim();
        return "S".equalsIgnoreCase(text)
                || "SIM".equalsIgnoreCase(text)
                || "TRUE".equalsIgnoreCase(text)
                || "1".equals(text);
    }
}
