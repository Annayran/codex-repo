package br.zaya.telecontrol.parsers;

import br.zaya.telecontrol.models.FamiliaGet;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;

public class ParseFamilia {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static List<FamiliaGet> getFamilia(String json) throws Exception {
        List<FamiliaGet> familias = new ArrayList<FamiliaGet>();
        JsonNode node = localizarArray(MAPPER.readTree(json), "familias");
        if (node == null) {
            return familias;
        }
        if (node.isArray()) {
            for (JsonNode item : node) {
                familias.add(MAPPER.treeToValue(item, FamiliaGet.class));
            }
        } else {
            familias.add(MAPPER.treeToValue(node, FamiliaGet.class));
        }
        return familias;
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
        if (root.has("familia")) {
            return root;
        }
        return null;
    }
}
