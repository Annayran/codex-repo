package br.zaya.telecontrol;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Teste de integração com API Telecontrol
 * Chamada HTTP pura (sem Sankhya)
 *
 * Diagnóstico detalhado de erros HTTP 500
 *
 * Uso: java -cp "build/classes/java/main:build/libs/*" br.zaya.telecontrol.TestOsIntegracao
 */
public class TestOsIntegracao {

    private static final String LINHA = repeat("=", 80);
    private static final String SUBLINHA = repeat("-", 80);

    // Configurações da API - AJUSTE CONFORME NECESSÁRIO
    private static final String BASE_URL = "http://api2.telecontrol.com.br/os/osvisaogeral";
    private static final String TOKEN = "0013f9724e3abb68e651888f7e0690cd965fa799";
    private static final String AMBIENTE = "PRODUCTION";

    public static void main(String[] args) {
        System.out.println(LINHA);
        System.out.println("TESTE HTTP - API TELECONTROL (DIAGNÓSTICO DETALHADO)");
        System.out.println(LINHA);
        System.out.println();

        try {
            // Teste 0: Validação de configurações
            System.out.println("[TESTE 0] Validação de Configurações");
            System.out.println(SUBLINHA);
            validarConfiguracoes();
            System.out.println();
            System.out.println();

            // Teste 1: D-1
            System.out.println("[TESTE 1] GET /os/osvisaogeral (D-1)");
            System.out.println(SUBLINHA);
            testarEndpointDia1();

            System.out.println();
            System.out.println();

            // Teste 2: Período customizado
            System.out.println("[TESTE 2] GET /os/osvisaogeral (Período Customizado)");
            System.out.println(SUBLINHA);
            testarEndpointPeriodo("2026-04-01", "2026-04-29");

            System.out.println();
            System.out.println(LINHA);
            System.out.println("TESTES CONCLUÍDOS");
            System.out.println(LINHA);

        } catch (Exception e) {
            System.err.println();
            System.err.println("[ERRO] Falha durante os testes:");
            System.err.println(SUBLINHA);
            e.printStackTrace();
            System.err.println(SUBLINHA);
            System.exit(1);
        }
    }

    /**
     * Valida as configurações antes de fazer as requisições
     */
    private static void validarConfiguracoes() {
        System.out.println("URL Base: " + BASE_URL);
        System.out.println("Token: " + (TOKEN != null && TOKEN.length() > 0 ? "✅ Configurado" : "❌ NÃO CONFIGURADO"));
        System.out.println("Ambiente: " + AMBIENTE);
        System.out.println();

        if (TOKEN == null || TOKEN.isEmpty()) {
            System.err.println("❌ ERRO: Token não configurado!");
            System.err.println("   Edite a variável TOKEN em TestOsIntegracao.java");
            System.exit(1);
        }

        if (!BASE_URL.startsWith("http")) {
            System.err.println("❌ ERRO: URL inválida!");
            System.exit(1);
        }

        System.out.println("✅ Todas as configurações validadas");
    }

    /**
     * Testa o endpoint D-1
     */
    private static void testarEndpointDia1() throws Exception {
        Date ontem = adicionarDias(new Date(), -1);
        String dataFormatada = formatarData(ontem, "yyyy-MM-dd");
        testarEndpoint(dataFormatada, dataFormatada);
    }

    /**
     * Testa o endpoint com período customizado
     */
    private static void testarEndpointPeriodo(String dataInicio, String dataFim) throws Exception {
        testarEndpoint(dataInicio, dataFim);
    }

    /**
     * Faz a chamada HTTP GET ao endpoint
     */
    private static void testarEndpoint(String dataInicio, String dataFim) throws Exception {
        String url = BASE_URL + "/dataInicio/" + dataInicio + "/dataFim/" + dataFim;

        System.out.println("URL: " + url);
        System.out.println("Método: GET");
        System.out.println("Headers:");
        System.out.println("  - Content-Type: application/json");
        System.out.println("  - Access-Application-Key: " + (TOKEN.length() > 10 ? TOKEN.substring(0, 10) + "..." : TOKEN));
        System.out.println("  - Access-Env: " + AMBIENTE);
        System.out.println();
        System.out.println("Enviando requisição...");
        System.out.println();

        long inicio = System.currentTimeMillis();

        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Access-Application-Key", TOKEN);
            conn.setRequestProperty("Access-Env", AMBIENTE);
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);

            int statusCode = conn.getResponseCode();
            long duracao = System.currentTimeMillis() - inicio;

            System.out.println("RESPOSTA:");
            System.out.println(SUBLINHA);
            System.out.println("Status Code: " + statusCode);
            System.out.println("Tempo: " + duracao + "ms");
            System.out.println();

            if (statusCode == 200 || statusCode == 201 || statusCode == 202) {
                String responseBody = lerInputStream(conn.getInputStream());
                exibirJsonFormatado(responseBody);
                System.out.println("✅ SUCESSO - Dados recebidos e parseados");
            } else if (statusCode == 500) {
                System.err.println("❌ ERRO HTTP 500 - Erro no servidor da API");
                String errorBody = lerInputStream(conn.getErrorStream());
                if (errorBody == null || errorBody.isEmpty()) {
                    errorBody = lerInputStream(conn.getInputStream());
                }

                exibirErro500(errorBody);
                sugerirSolucoes();

            } else if (statusCode == 401 || statusCode == 403) {
                System.err.println("❌ ERRO HTTP " + statusCode + " - Problema de autenticação");
                String errorBody = lerInputStream(conn.getErrorStream());
                System.err.println("Resposta: " + (errorBody != null ? errorBody : "Sem corpo"));
                System.err.println();
                System.err.println("⚠️  Possíveis soluções:");
                System.err.println("  1. Valide o token em AD_TCLCFG (tabela Sankhya)");
                System.err.println("  2. Verifique se o token expirou");
                System.err.println("  3. Confirme o ambiente (PRODUCTION/HOMOLOGATION)");

            } else if (statusCode == 404) {
                System.err.println("❌ ERRO HTTP 404 - Endpoint não encontrado");
                String errorBody = lerInputStream(conn.getErrorStream());
                System.err.println("Resposta: " + (errorBody != null ? errorBody : "Sem corpo"));
                System.err.println();
                System.err.println("⚠️  Verifique:");
                System.err.println("  1. URL: " + BASE_URL);
                System.err.println("  2. Formato da data: yyyy-MM-dd");

            } else {
                System.err.println("❌ ERRO HTTP " + statusCode);
                String errorBody = lerInputStream(conn.getErrorStream());
                if (errorBody != null && !errorBody.isEmpty()) {
                    System.err.println("Corpo: " + errorBody);
                }
            }

            System.out.println(SUBLINHA);

        } catch (Exception e) {
            long duracao = System.currentTimeMillis() - inicio;
            System.err.println("❌ Erro na requisição (após " + duracao + "ms):");
            System.err.println("   " + e.getClass().getSimpleName() + ": " + e.getMessage());
            System.err.println();
            System.err.println("⚠️  Possíveis causas:");
            System.err.println("  1. API Telecontrol inacessível");
            System.err.println("  2. Problema de conectividade/firewall");
            System.err.println("  3. Timeout na conexão (30s)");
            throw e;
        }
    }

    /**
     * Exibe detalhes do erro HTTP 500
     */
    private static void exibirErro500(String errorBody) {
        System.err.println();
        System.err.println("DETALHES DO ERRO:");
        System.err.println(SUBLINHA);

        if (errorBody == null || errorBody.isEmpty()) {
            System.err.println("Corpo vazio - nenhum detalhe disponível");
            return;
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(errorBody);

            if (node.has("message")) {
                String message = node.get("message").asText();
                System.err.println("Mensagem: " + message);

                // Análise específica de mensagens conhecidas
                if (message.contains("Rule not loaded")) {
                    System.err.println();
                    System.err.println("Erro: 'Rule not loaded'");
                    System.err.println("Isso significa que uma regra/configuração não foi carregada no servidor.");
                }
            }

            if (node.has("exception")) {
                System.err.println("Exceção: " + node.get("exception").asText());
            }

            if (node.has("status")) {
                System.err.println("Status: " + node.get("status").asText());
            }

            System.err.println();
            System.err.println("JSON completo:");
            System.err.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node));

        } catch (Exception e) {
            System.err.println("Resposta bruta:");
            System.err.println(errorBody);
        }
    }

    /**
     * Sugere soluções para o erro 500 "Rule not loaded"
     */
    private static void sugerirSolucoes() {
        System.err.println();
        System.err.println("⚠️  SOLUÇÕES SUGERIDAS:");
        System.err.println(SUBLINHA);
        System.err.println();
        System.err.println("1. Verifique as configurações no Sankhya (AD_TCLCFG):");
        System.err.println("   - URL da API");
        System.err.println("   - Token de autenticação");
        System.err.println("   - Ambiente (PRODUCTION / HOMOLOGATION)");
        System.err.println("   - Endpoint: /os/osvisaogeral");
        System.err.println();
        System.err.println("2. Valide o acesso à conta Telecontrol:");
        System.err.println("   - Conta ativa?");
        System.err.println("   - Permissões configuradas?");
        System.err.println("   - Módulo de OS habilitado?");
        System.err.println();
        System.err.println("3. Teste a conectividade:");
        System.err.println("   curl -v -H 'Access-Application-Key: " + TOKEN.substring(0, 10) + "...'");
        System.err.println("        -H 'Access-Env: " + AMBIENTE + "'");
        System.err.println("        " + BASE_URL + "/dataInicio/2026-04-28/dataFim/2026-04-28");
        System.err.println();
        System.err.println("4. Contate o suporte Telecontrol com:");
        System.err.println("   - Token: " + TOKEN.substring(0, 10) + "...");
        System.err.println("   - Ambiente: " + AMBIENTE);
        System.err.println("   - Erro: Rule not loaded");
        System.err.println();
    }

    /**
     * Lê o conteúdo de um InputStream
     */
    private static String lerInputStream(java.io.InputStream stream) throws Exception {
        if (stream == null) return null;

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String linha;
        while ((linha = reader.readLine()) != null) {
            sb.append(linha).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    /**
     * Exibe JSON formatado (pretty print)
     */
    private static void exibirJsonFormatado(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(json);

            System.out.println("✅ JSON válido recebido");
            System.out.println();

            if (node.has("data")) {
                JsonNode dataArray = node.get("data");
                int totalItens = dataArray.size();
                System.out.println("Total de registros (data): " + totalItens);

                if (totalItens > 0) {
                    System.out.println();
                    System.out.println("Primeiro registro (resumido):");
                    String primeiro = mapper.writerWithDefaultPrettyPrinter()
                        .writeValueAsString(dataArray.get(0));
                    String[] linhas = primeiro.split("\n");
                    for (int i = 0; i < Math.min(10, linhas.length); i++) {
                        System.out.println(linhas[i]);
                    }
                    if (linhas.length > 10) {
                        System.out.println("  ...");
                    }
                }
            }

            if (node.has("total_items")) {
                System.out.println();
                System.out.println("Total items (da API): " + node.get("total_items").asInt());
            }

        } catch (Exception e) {
            System.out.println("⚠️ Resposta recebida (não é JSON válido):");
            System.out.println(json.substring(0, Math.min(300, json.length())));
        }
    }

    /**
     * Formata uma data
     */
    private static String formatarData(Date data, String formato) {
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        return sdf.format(data);
    }

    /**
     * Adiciona dias a uma data
     */
    private static Date adicionarDias(Date data, int dias) {
        long tempo = data.getTime();
        tempo += (long) dias * 24 * 60 * 60 * 1000;
        return new Date(tempo);
    }

    /**
     * Helper para repetir strings
     */
    private static String repeat(String str, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
}

