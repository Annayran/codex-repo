package br.com.zaya.mahospitalar.genesis.importacao;

import com.fasterxml.jackson.databind.JsonNode;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public final class MapeadorPlanilhaGenesis {
    private static final DateTimeFormatter[] DATE_TIME_FORMATS = new DateTimeFormatter[]{
            DateTimeFormatter.ISO_DATE_TIME,
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    };
    private static final DateTimeFormatter[] DATE_FORMATS = new DateTimeFormatter[]{
            DateTimeFormatter.ISO_LOCAL_DATE,
            DateTimeFormatter.ofPattern("dd/MM/yyyy")
    };

    public RegistroBaseGenesis mapear(AbaBaseGenesis aba, JsonNode linha, int numeroLinha) {
        if (linha == null || !linha.isObject()) {
            throw erro(numeroLinha, "conteudo da linha nao e um objeto");
        }
        Map<String, JsonNode> origem = normalizar(linha);
        BigDecimal idGenesis = numeroObrigatorio(origem, numeroLinha, "id", "IDGENESIS");
        Map<String, Object> destino = new LinkedHashMap<String, Object>();
        destino.put("IDGENESIS", idGenesis);

        switch (aba) {
            case PROPRIEDADE:
            case SITUACAO:
                destino.put("TIPODOMINIO", aba.getTipoDominio());
                texto(destino, "NOME", origem, 100, "nome");
                texto(destino, "STATUSGENESIS", origem, 20, "status", "STATUSGENESIS");
                break;
            case CLIENTE:
                textoPresente(destino, "CODIGOEXTERNO", origem, 200, "codigoExterno", "CODIGOEXTERNO");
                texto(destino, "RAZAOSOCIAL", origem, 255, "razaoSocial", "RAZAOSOCIAL");
                texto(destino, "NOMEFANTASIA", origem, 255, "nomeFantasia", "NOMEFANTASIA");
                digitos(destino, "CNPJ", origem, 20, "cnpj", "CNPJ");
                digitos(destino, "CPF", origem, 14, "cpf", "CPF");
                texto(destino, "ENDERECO", origem, 255, "endereco", "ENDERECO");
                texto(destino, "ENDERECONUMERO", origem, 30, "enderecoNumero", "ENDERECONUMERO");
                texto(destino, "ENDERECOBAIRRO", origem, 120, "enderecoBairro", "ENDERECOBAIRRO");
                texto(destino, "ENDERECOCIDADE", origem, 120, "enderecoCidade", "ENDERECOCIDADE");
                texto(destino, "ENDERECOUF", origem, 2, "enderecoUF", "ENDERECOUF");
                digitos(destino, "CEP", origem, 10, "cep", "CEP");
                texto(destino, "TELEFONE", origem, 30, "telefone", "TELEFONE");
                texto(destino, "EMAIL", origem, 255, "email", "EMAIL");
                texto(destino, "STATUSGENESIS", origem, 20, "status", "STATUSGENESIS");
                vinculoCliente(destino, origem, numeroLinha);
                break;
            case SETOR:
                numero(destino, "IDCLIENTEGEN", origem, "idCliente", "IDCLIENTEGEN");
                texto(destino, "CODIGOEXTERNO", origem, 200, "codigoExterno", "CODIGOEXTERNO");
                texto(destino, "NOME", origem, 255, "nome", "NOME");
                texto(destino, "STATUSGENESIS", origem, 20, "status", "STATUSGENESIS");
                break;
            case MATERIAL:
                texto(destino, "CODIGOEXTERNO", origem, 200, "codigoExterno", "CODIGOEXTERNO");
                texto(destino, "NOME", origem, 255, "nome", "NOME");
                texto(destino, "TIPO", origem, 30, "tipo", "TIPO");
                texto(destino, "GRAURISCO", origem, 30, "grauRisco", "GRAURISCO");
                texto(destino, "STATUSGENESIS", origem, 20, "status", "STATUSGENESIS");
                break;
            case EMPRESA:
                texto(destino, "CODIGOEXTERNO", origem, 200, "codigo_externo", "codigoExterno", "CODIGOEXTERNO");
                texto(destino, "RAZAOSOCIAL", origem, 255, "razaoSocial", "RAZAOSOCIAL");
                texto(destino, "NOMEFANTASIA", origem, 255, "nomeFantasia", "NOMEFANTASIA");
                numero(destino, "FORNECEDOR", origem, "fornecedor", "FORNECEDOR");
                numero(destino, "FABRICANTE", origem, "fabricante", "FABRICANTE");
                texto(destino, "STATUSGENESIS", origem, 20, "status", "STATUSGENESIS");
                break;
            case MODELO:
                numero(destino, "IDEMPRESAGEN", origem, "idEmpresaFabricante", "IDEMPRESAGEN");
                numero(destino, "IDMATERIALGEN", origem, "idMaterial", "IDMATERIALGEN");
                texto(destino, "CODIGOEXTERNO", origem, 200, "codigoExterno", "CODIGOEXTERNO");
                texto(destino, "NOME", origem, 255, "nome", "NOME");
                texto(destino, "STATUSGENESIS", origem, 20, "status", "STATUSGENESIS");
                texto(destino, "NRANVISA", origem, 50, "N_R_ANVISA", "NRANVISA");
                break;
            case ITEM_MATERIAL:
                numero(destino, "IDMODELOGEN", origem, "idModelo", "IDMODELOGEN");
                numero(destino, "IDCLIENTEGEN", origem, "idCliente", "IDCLIENTEGEN");
                numero(destino, "IDSETORGEN", origem, "idSetor", "IDSETORGEN");
                texto(destino, "CODIGOEXTERNO", origem, 200, "codigoExterno", "CODIGOEXTERNO");
                texto(destino, "NSERIE", origem, 150, "nSerie", "NSERIE");
                texto(destino, "RM", origem, 100, "rm", "RM");
                texto(destino, "STATUSGENESIS", origem, 20, "status", "STATUSGENESIS");
                numero(destino, "VALORUNITARIO", origem, "valorUnitario", "VALORUNITARIO");
                data(destino, "DATAAQUISICAO", origem, numeroLinha, "DataAquisicao", "DATAAQUISICAO");
                data(destino, "DATAINSTALACAO", origem, numeroLinha, "DataInstalacao", "DATAINSTALACAO");
                data(destino, "ULTIMAINSTALACAO", origem, numeroLinha, "UltimaInstalacao", "ULTIMAINSTALACAO");
                texto(destino, "MARCA", origem, 100, "Marca", "MARCA");
                texto(destino, "OUTROS", origem, 255, "outros", "OUTROS");
                numero(destino, "GARANTIA", origem, "garantia", "GARANTIA");
                numero(destino, "IDPROPRIEDADEGEN", origem, "id_propriedade_equipamento", "IDPROPRIEDADEGEN");
                texto(destino, "DONO", origem, 50, "dono", "DONO");
                numero(destino, "IDSITUACAOGEN", origem, "id_situacao", "IDSITUACAOGEN");
                texto(destino, "SITUACAO", origem, 50, "situacao", "SITUACAO");
                break;
            default:
                throw erro(numeroLinha, "aba nao suportada");
        }
        return new RegistroBaseGenesis(aba, idGenesis, destino);
    }

    private Map<String, JsonNode> normalizar(JsonNode linha) {
        Map<String, JsonNode> resultado = new HashMap<String, JsonNode>();
        Iterator<Map.Entry<String, JsonNode>> campos = linha.fields();
        while (campos.hasNext()) {
            Map.Entry<String, JsonNode> campo = campos.next();
            resultado.put(chave(campo.getKey()), campo.getValue());
        }
        return resultado;
    }

    private String chave(String valor) {
        String semAcento = Normalizer.normalize(valor == null ? "" : valor, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return semAcento.replaceAll("[^A-Za-z0-9]", "").toUpperCase(Locale.ROOT);
    }

    private JsonNode localizar(Map<String, JsonNode> origem, String... aliases) {
        for (String alias : aliases) {
            JsonNode valor = origem.get(chave(alias));
            if (valor != null && !valor.isNull() && !valor.asText().trim().isEmpty()) {
                return valor;
            }
        }
        return null;
    }

    private JsonNode localizarPresente(Map<String, JsonNode> origem, String... aliases) {
        for (String alias : aliases) {
            String chaveAlias = chave(alias);
            if (origem.containsKey(chaveAlias)) {
                return origem.get(chaveAlias);
            }
        }
        return null;
    }

    private boolean possuiCampo(Map<String, JsonNode> origem, String... aliases) {
        for (String alias : aliases) {
            if (origem.containsKey(chave(alias))) {
                return true;
            }
        }
        return false;
    }

    private BigDecimal numeroObrigatorio(Map<String, JsonNode> origem, int linha, String... aliases) {
        JsonNode valor = localizar(origem, aliases);
        if (valor == null) {
            throw erro(linha, "campo ID Genesis nao informado");
        }
        try {
            return new BigDecimal(valor.asText().trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            throw erro(linha, "campo ID Genesis invalido: " + valor.asText());
        }
    }

    private void numero(Map<String, Object> destino, String campo, Map<String, JsonNode> origem, String... aliases) {
        JsonNode valor = localizar(origem, aliases);
        if (valor == null) return;
        try {
            destino.put(campo, new BigDecimal(valor.asText().trim().replace(',', '.')));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Valor numerico invalido em " + campo + ": " + valor.asText());
        }
    }

    private void texto(Map<String, Object> destino, String campo, Map<String, JsonNode> origem,
                       int tamanho, String... aliases) {
        JsonNode valor = localizar(origem, aliases);
        if (valor == null) return;
        String texto = valor.asText().trim();
        if (texto.length() > tamanho) {
            throw new IllegalArgumentException(campo + " excede " + tamanho + " caracteres.");
        }
        destino.put(campo, texto);
    }

    private void textoPresente(Map<String, Object> destino, String campo, Map<String, JsonNode> origem,
                               int tamanho, String... aliases) {
        if (!possuiCampo(origem, aliases)) return;
        JsonNode valor = localizarPresente(origem, aliases);
        if (valor == null || valor.isNull() || valor.asText().trim().isEmpty()) {
            destino.put(campo, null);
            return;
        }
        String texto = valor.asText().trim();
        if (texto.length() > tamanho) {
            throw new IllegalArgumentException(campo + " excede " + tamanho + " caracteres.");
        }
        destino.put(campo, texto);
    }

    private void vinculoCliente(Map<String, Object> destino, Map<String, JsonNode> origem, int linha) {
        if (!possuiCampo(origem, "CODPARC")) return;

        JsonNode valor = localizarPresente(origem, "CODPARC");
        String texto = valor == null || valor.isNull() ? "" : valor.asText().trim();
        String marcador = chave(texto);
        Timestamp agora = new Timestamp(System.currentTimeMillis());

        if (texto.isEmpty() || "NA".equals(marcador) || "NAOLOCALIZADO".equals(marcador)) {
            destino.put("CODPARC", null);
            destino.put("STATUSCONC", "PENDENTE");
            destino.put("MENSAGEMCONC", "VINCULO_IMPORTADO_PLANILHA: CODPARC_NAO_LOCALIZADO");
            destino.put("DHCONCILIACAO", agora);
            return;
        }

        BigDecimal codParc;
        try {
            codParc = new BigDecimal(texto.replace(',', '.'));
        } catch (NumberFormatException e) {
            throw erro(linha, "CODPARC invalido: " + texto);
        }
        if (codParc.signum() <= 0 || codParc.stripTrailingZeros().scale() > 0) {
            throw erro(linha, "CODPARC deve ser um inteiro positivo: " + texto);
        }

        destino.put("CODPARC", codParc);
        destino.put("STATUSCONC", "CONCILIADO");
        destino.put("MENSAGEMCONC", "VINCULO_IMPORTADO_PLANILHA: CODPARC_INFORMADO");
        destino.put("DHCONCILIACAO", agora);
    }

    private void digitos(Map<String, Object> destino, String campo, Map<String, JsonNode> origem,
                         int tamanho, String... aliases) {
        JsonNode valor = localizar(origem, aliases);
        if (valor == null) return;
        String texto = valor.asText().replaceAll("\\D", "");
        if (texto.length() > tamanho) {
            throw new IllegalArgumentException(campo + " excede " + tamanho + " caracteres.");
        }
        if (!texto.isEmpty()) destino.put(campo, texto);
    }

    private void data(Map<String, Object> destino, String campo, Map<String, JsonNode> origem,
                      int linha, String... aliases) {
        JsonNode valor = localizar(origem, aliases);
        if (valor == null) return;
        String texto = valor.asText().trim();
        try {
            destino.put(campo, Timestamp.from(Instant.parse(texto)));
            return;
        } catch (DateTimeParseException ignored) {
            // Tenta formatos locais abaixo.
        }
        for (DateTimeFormatter formato : DATE_TIME_FORMATS) {
            try {
                LocalDateTime data = LocalDateTime.parse(texto, formato);
                destino.put(campo, Timestamp.valueOf(data));
                return;
            } catch (DateTimeParseException ignored) {
                // Continua tentando.
            }
        }
        for (DateTimeFormatter formato : DATE_FORMATS) {
            try {
                LocalDate data = LocalDate.parse(texto, formato);
                destino.put(campo, Timestamp.valueOf(data.atStartOfDay(ZoneId.systemDefault()).toLocalDateTime()));
                return;
            } catch (DateTimeParseException ignored) {
                // Continua tentando.
            }
        }
        throw erro(linha, "data invalida em " + campo + ": " + texto);
    }

    private IllegalArgumentException erro(int linha, String mensagem) {
        return new IllegalArgumentException("Linha " + linha + ": " + mensagem + ".");
    }
}
