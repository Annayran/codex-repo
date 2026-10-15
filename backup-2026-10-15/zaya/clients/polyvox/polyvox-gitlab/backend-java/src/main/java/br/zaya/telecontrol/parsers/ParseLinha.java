package br.zaya.telecontrol.parsers;

import br.zaya.telecontrol.models.LinhaGet;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;

public class ParseLinha {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static List<LinhaGet> getLinha(String json) throws Exception {
        List<LinhaGet> linhas = new ArrayList<LinhaGet>();
        JsonNode node = localizarArray(MAPPER.readTree(json), "linhas");
        if (node == null) {
            return linhas;
        }
        if (node.isArray()) {
            for (JsonNode item : node) {
                linhas.add(MAPPER.treeToValue(item, LinhaGet.class));
            }
        } else {
            linhas.add(MAPPER.treeToValue(node, LinhaGet.class));
        }
        return linhas;
    }

    private static JsonNode localizarArray(JsonNode root, String nomeArray) {
        if (root == null || root.isNull()) {
            return null;
        }
        if (root.isArray()) {
            return root;
        }
        if (root.has(nomeArray)) {
            return root.get(nomeArray);
        }
        if (root.has("dados")) {
            return root.get("dados");
        }
        if (root.has("data")) {
            return root.get("data");
        }
        if (root.has("linha")) {
            return root;
        }
        return null;
    }
}
