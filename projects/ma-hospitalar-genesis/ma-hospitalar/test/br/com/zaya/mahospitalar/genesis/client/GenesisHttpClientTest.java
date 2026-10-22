package br.com.zaya.mahospitalar.genesis.client;

import br.com.zaya.mahospitalar.genesis.config.ParametrosGenesis;
import br.com.zaya.mahospitalar.genesis.domain.TipoEntidade;
import br.com.zaya.mahospitalar.genesis.dto.MaterialGenesisDto;
import br.com.zaya.mahospitalar.genesis.dto.ClienteGenesisDto;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.*;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenesisHttpClientTest {
    private HttpServer server;
    private String baseUrl;

    @BeforeEach void start() throws Exception {
        server = HttpServer.create(new InetSocketAddress(0), 0);
        baseUrl = "http://127.0.0.1:" + server.getAddress().getPort();
        server.start();
    }
    @AfterEach void stop() { server.stop(0); }

    @Test void consultaPorIdEDocumentoSemExporDocumentoNoLog() throws Exception {
        server.createContext("/clientes/95", exchange -> respond(exchange, 200,
                "{\"id\":95,\"cnpj\":\"12345678000190\"}"));
        server.createContext("/clientes/", exchange -> respond(exchange, 200,
                "[{\"id\":95,\"cnpj\":\"12345678000190\"}]"));
        List<String> logs = new ArrayList<String>();
        ParametrosGenesis p = new ParametrosGenesis(1,"HML",baseUrl,true,false,2000,2000,3,10,"MS");
        GenesisHttpClient client = new GenesisHttpClient(p, () -> "segredo-teste", logs::add);

        assertEquals("95", client.buscarPorId(TipoEntidade.CLIENTE, "95").getIdGenesis());
        assertEquals("95", client.buscarPorDocumento(TipoEntidade.CLIENTE,
                "12.345.678/0001-90").getIdGenesis());
        assertFalse(String.join("\n", logs).contains("12345678000190"));
    }

    @Test void classificaGetPostPutEExtraiId() throws Exception {
        server.createContext("/materiais/", exchange -> {
            assertEquals("segredo-teste", exchange.getRequestHeaders().getFirst("token"));
            if ("GET".equals(exchange.getRequestMethod())) respond(exchange, 404, "{}");
            else if ("POST".equals(exchange.getRequestMethod())) respond(exchange, 201, "{\"id\":\"mat-10\"}");
            else respond(exchange, 200, "{\"data\":{\"id\":\"mat-10\"}}");
        });
        GenesisHttpClient client = client();
        assertEquals(GenesisApiResult.Classificacao.NAO_ENCONTRADO,
                client.buscarPorCodigoExterno(TipoEntidade.MATERIAL,"A 1").getClassificacao());
        MaterialGenesisDto dto = new MaterialGenesisDto(); dto.codigoExterno="A 1"; dto.nome="Material";
        assertEquals("mat-10", client.criar(TipoEntidade.MATERIAL,dto).getIdGenesis());
        assertTrue(client.atualizar(TipoEntidade.MATERIAL,"mat-10",dto).isSucesso());
    }

    @Test void classificaErrosSemExporDocumento() throws Exception {
        server.createContext("/clientes/", exchange -> respond(exchange, 422,
                "{\"cpf\":\"12345678900\",\"token\":\"nao-persistir\"}"));
        ClienteGenesisDto dto = new ClienteGenesisDto(); dto.codigoExterno = "C-1";
        GenesisApiResult result = client().criar(TipoEntidade.CLIENTE, dto);
        assertEquals(GenesisApiResult.Classificacao.ERRO_FUNCIONAL, result.getClassificacao());
        assertFalse(result.getCorpoSanitizado().contains("12345678900"));
        assertFalse(result.getCorpoSanitizado().contains("nao-persistir"));
    }

    @Test void trataListaVaziaComoAusenteEMultiplaComoAmbigua() throws Exception {
        server.createContext("/materiais/", exchange -> {
            String query = exchange.getRequestURI().getQuery();
            respond(exchange, 200, query.contains("VAZIO") ? "{\"data\":[]}" : "{\"data\":[{\"id\":\"1\"},{\"id\":\"2\"}]}");
        });
        assertEquals(GenesisApiResult.Classificacao.NAO_ENCONTRADO,
                client().buscarPorCodigoExterno(TipoEntidade.MATERIAL,"VAZIO").getClassificacao());
        assertEquals(GenesisApiResult.Classificacao.CONFLITO,
                client().buscarPorCodigoExterno(TipoEntidade.MATERIAL,"DUPLO").getClassificacao());
    }

    @Test void interpretaRespostaPaginadaSpring() throws Exception {
        server.createContext("/materiais/", exchange -> respond(exchange, 200,
                "{\"content\":[{\"id\":\"mat-20\"}],\"totalElements\":1}"));

        GenesisApiResult result = client().buscarPorCodigoExterno(TipoEntidade.MATERIAL, "MAT-20");

        assertTrue(result.isSucesso());
        assertEquals("mat-20", result.getIdGenesis());
    }

    @Test void registraRequestResponseEDecisaoSemExporDadosSensiveis() throws Exception {
        server.createContext("/clientes/", exchange -> respond(exchange, 201,
                "{\"id\":\"cli-10\",\"token\":\"token-resposta\",\"cnpj\":\"12345678000190\"}"));
        List<String> logs = new ArrayList<String>();
        ParametrosGenesis p = new ParametrosGenesis(1,"HML",baseUrl,true,false,2000,2000,3,10,"MS");
        GenesisHttpClient client = new GenesisHttpClient(p, () -> "segredo-teste", logs::add);
        ClienteGenesisDto dto = new ClienteGenesisDto();
        dto.codigoExterno = "C-1";
        dto.cnpj = "12345678000190";
        dto.email = "cliente@exemplo.com";

        GenesisApiResult result = client.criar(TipoEntidade.CLIENTE, dto);
        String logCompleto = String.join("\n", logs);

        assertEquals("cli-10", result.getIdGenesis());
        assertTrue(logCompleto.contains("HTTP request: metodo=POST"));
        assertTrue(logCompleto.contains("HTTP request body"));
        assertTrue(logCompleto.contains("HTTP response: metodo=POST"));
        assertTrue(logCompleto.contains("status=201"));
        assertTrue(logCompleto.contains("[MASCARADO]"));
        assertFalse(logCompleto.contains("segredo-teste"));
        assertFalse(logCompleto.contains("token-resposta"));
        assertFalse(logCompleto.contains("12345678000190"));
        assertFalse(logCompleto.contains("cliente@exemplo.com"));
    }

    private GenesisHttpClient client() {
        ParametrosGenesis p = new ParametrosGenesis(1,"HML",baseUrl,true,false,2000,2000,3,10,"MS");
        return new GenesisHttpClient(p, () -> "segredo-teste");
    }
    @Test void inativaSomenteItemPorIdNumericoSemCorpo() throws Exception {
        List<String> chamadas = new ArrayList<String>();
        server.createContext("/itens-materiais/8190", exchange -> {
            chamadas.add(exchange.getRequestMethod());
            assertEquals("segredo-teste",exchange.getRequestHeaders().getFirst("token"));
            assertEquals(-1,exchange.getRequestBody().read());
            respond(exchange,200,"");
        });
        assertTrue(client().inativarItem("8190").isSucesso());
        assertEquals(java.util.Collections.singletonList("DELETE"),chamadas);
        assertThrows(IllegalArgumentException.class,() -> client().inativarItem("../clientes/1"));
        assertThrows(IllegalArgumentException.class,() -> client().inativarItem("0"));
        assertThrows(IllegalArgumentException.class,() -> client().inativarItem(null));
    }

    @Test void deletePreservaClassificacaoDeFalhas() throws Exception {
        server.createContext("/itens-materiais/1",exchange -> respond(exchange,401,"{}"));
        server.createContext("/itens-materiais/2",exchange -> respond(exchange,404,"{}"));
        server.createContext("/itens-materiais/3",exchange -> respond(exchange,500,"{}"));
        assertEquals(GenesisApiResult.Classificacao.AUTENTICACAO,client().inativarItem("1").getClassificacao());
        assertEquals(GenesisApiResult.Classificacao.NAO_ENCONTRADO,client().inativarItem("2").getClassificacao());
        assertEquals(GenesisApiResult.Classificacao.TRANSITORIO,client().inativarItem("3").getClassificacao());
    }
    private static void respond(HttpExchange exchange, int status, String body) throws java.io.IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(status, bytes.length);
        exchange.getResponseBody().write(bytes); exchange.close();
    }
}
