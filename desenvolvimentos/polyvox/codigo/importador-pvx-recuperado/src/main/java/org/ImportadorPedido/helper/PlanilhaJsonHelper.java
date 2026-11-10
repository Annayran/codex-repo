package org.ImportadorPedido.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Iterator;
import java.util.Map;

public final class PlanilhaJsonHelper {
    private PlanilhaJsonHelper() {}

    public static JsonNode limparCabecalhos(JsonNode node, ObjectMapper mapper) {
        if (node.isObject()) {
            ObjectNode resultado = mapper.createObjectNode();
            Iterator<Map.Entry<String, JsonNode>> campos = node.fields();
            while (campos.hasNext()) {
                Map.Entry<String, JsonNode> campo = campos.next();
                String chave = campo.getKey().trim();
                // SheetJS cria estas chaves para colunas sem cabecalho.
                if (chave.matches("__EMPTY(?:_[0-9]+)?")) {
                    continue;
                }
                resultado.set(chave, limparCabecalhos(campo.getValue(), mapper));
            }
            return resultado;
        }
        if (node.isArray()) {
            ArrayNode resultado = mapper.createArrayNode();
            for (JsonNode item : node) {
                resultado.add(limparCabecalhos(item, mapper));
            }
            return resultado;
        }
        return node;
    }
}
