package br.com.zaya.mahospitalar.genesis.util;

public final class GenesisSanitizer {
    private static final int MAX_LENGTH = 4000;
    private static final int MAX_LOG_LENGTH = 65536;
    private GenesisSanitizer() {}

    public static String sanitize(String value) {
        return sanitize(value, MAX_LENGTH);
    }

    public static String sanitizeForLog(String value) {
        return sanitize(value, MAX_LOG_LENGTH);
    }

    public static String sanitizeForHistory(String value) {
        if (value == null) return null;
        try {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            com.fasterxml.jackson.databind.JsonNode node = mapper.readTree(value);
            mascarar(node);
            return sanitizeForLog(mapper.writeValueAsString(node));
        } catch (Exception e) {
            return sanitizeForLog(value);
        }
    }

    private static void mascarar(com.fasterxml.jackson.databind.JsonNode node) {
        if (node == null) return;
        if (node.isObject()) {
            java.util.Iterator<String> campos = node.fieldNames();
            java.util.List<String> nomes = new java.util.ArrayList<String>();
            while(campos.hasNext()) nomes.add(campos.next());
            for(String campo:nomes) {
                if(campo.toLowerCase(java.util.Locale.ROOT).matches(".*(token|authorization|senha|password|secret|cpf|cnpj|email|telefone|celular|endereco|logradouro|cep|contato|nomeusuario).*"))
                    ((com.fasterxml.jackson.databind.node.ObjectNode)node).put(campo,"[MASCARADO]");
                else mascarar(node.get(campo));
            }
        } else if(node.isArray()) for(com.fasterxml.jackson.databind.JsonNode item:node) mascarar(item);
    }

    private static String sanitize(String value, int maxLength) {
        if (value == null) return null;
        String clean = value
                .replaceAll("(?i)(\"(?:authorization|token|cpf|cnpj|cnpjcpf|email|telefone|cep|endereco|enderecobairro|enderecocidade)\"\\s*:\\s*)\"(?:\\\\.|[^\"])*\"", "$1\"[MASCARADO]\"")
                .replaceAll("(?i)([?&](?:cpf|cnpj)=)[^&\\s]+", "$1[MASCARADO]")
                .replaceAll("(?i)(?<![\"\\w])(authorization|token)\\s*[\"':=]+\\s*[^,}\\s]+", "$1:[MASCARADO]");
        return clean.length() > maxLength ? clean.substring(0, maxLength) + "...[TRUNCADO]" : clean;
    }
}
