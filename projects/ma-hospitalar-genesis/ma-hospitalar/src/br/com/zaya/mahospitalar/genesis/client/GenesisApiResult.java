package br.com.zaya.mahospitalar.genesis.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;

public final class GenesisApiResult {
    public enum Classificacao { SUCESSO, NAO_ENCONTRADO, ERRO_FUNCIONAL, AUTENTICACAO, CONFLITO, TRANSITORIO }

    private final int httpStatus;
    private final Classificacao classificacao;
    private final String idGenesis;
    private final String corpoOriginal;
    private final String corpoSanitizado;
    private String idRastreio;
    public String getIdRastreio() { return idRastreio; }
    public GenesisApiResult comRastreio(String id) { this.idRastreio = id; return this; }

    public GenesisApiResult(int httpStatus, Classificacao classificacao, String idGenesis, String corpoSanitizado) {
        this(httpStatus, classificacao, idGenesis, corpoSanitizado, corpoSanitizado);
    }

    public GenesisApiResult(int httpStatus, Classificacao classificacao, String idGenesis,
                            String corpoOriginal, String corpoSanitizado) {
        this.httpStatus = httpStatus;
        this.classificacao = classificacao;
        this.idGenesis = idGenesis;
        this.corpoOriginal = corpoOriginal;
        this.corpoSanitizado = corpoSanitizado;
    }

    public boolean isSucesso() { return classificacao == Classificacao.SUCESSO; }
    public int getHttpStatus() { return httpStatus; }
    public Classificacao getClassificacao() { return classificacao; }
    public String getIdGenesis() { return idGenesis; }
    public String getCorpoSanitizado() { return corpoSanitizado; }

    public JsonNode registroParaPersistencia() throws Exception {
        JsonNode registro = registroUnico(new ObjectMapper().readTree(corpoOriginal));
        return registro != null && registro.isObject() ? registro.deepCopy() : null;
    }

    public boolean correspondeAo(Object esperado) {
        if (esperado == null || corpoOriginal == null || corpoOriginal.trim().isEmpty()) return false;
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode atual = registroUnico(mapper.readTree(corpoOriginal));
            JsonNode desejado = mapper.valueToTree(esperado);
            return atual != null && atual.isObject() && contem(atual, desejado);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean campoCorresponde(String campo, String esperado, boolean somenteDigitos) {
        if (campo == null || esperado == null || corpoOriginal == null) return false;
        try {
            JsonNode atual = registroUnico(new ObjectMapper().readTree(corpoOriginal));
            if (atual == null || !atual.isObject()) return false;
            JsonNode valor = atual.get(campo);
            if (valor == null || valor.isNull()) return false;
            String recebido = valor.asText();
            if (somenteDigitos) {
                recebido = recebido.replaceAll("\\D", "");
                esperado = esperado.replaceAll("\\D", "");
            }
            return recebido.trim().equalsIgnoreCase(esperado.trim());
        } catch (Exception e) {
            return false;
        }
    }

    private static JsonNode registroUnico(JsonNode root) {
        if (root == null || root.isNull()) return null;
        JsonNode value = root;
        if (root.has("data")) value = root.get("data");
        else if (root.has("content")) value = root.get("content");
        if (value != null && value.isArray()) return value.size() == 1 ? value.get(0) : null;
        return value;
    }

    private static boolean contem(JsonNode atual, JsonNode desejado) {
        if (desejado == null || desejado.isNull()) return true;
        if (desejado.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> campos = desejado.fields();
            while (campos.hasNext()) {
                Map.Entry<String, JsonNode> campo = campos.next();
                if (campo.getValue() == null || campo.getValue().isNull()) continue;
                JsonNode valorAtual = atual.get(campo.getKey());
                if (valorAtual == null || !contem(valorAtual, campo.getValue())) return false;
            }
            return true;
        }
        if (desejado.isNumber() && atual.isNumber()) {
            return new BigDecimal(atual.asText()).compareTo(new BigDecimal(desejado.asText())) == 0;
        }
        return atual.equals(desejado);
    }
}
