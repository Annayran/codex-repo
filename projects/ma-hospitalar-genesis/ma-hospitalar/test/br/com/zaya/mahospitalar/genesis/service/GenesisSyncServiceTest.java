package br.com.zaya.mahospitalar.genesis.service;

import br.com.zaya.mahospitalar.genesis.client.GenesisApiClient;
import br.com.zaya.mahospitalar.genesis.client.GenesisApiResult;
import br.com.zaya.mahospitalar.genesis.config.ParametrosGenesis;
import br.com.zaya.mahospitalar.genesis.domain.CandidatoGenesis;
import br.com.zaya.mahospitalar.genesis.domain.PayloadGenesis;
import br.com.zaya.mahospitalar.genesis.domain.ReferenciasBaseInstaladaGenesis;
import br.com.zaya.mahospitalar.genesis.domain.TipoEntidade;
import br.com.zaya.mahospitalar.genesis.dto.ItemMaterialGenesisDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenesisSyncServiceTest {
    @Test void fluxoPorIdNaoRecriaItemVinculadoAusente() {
        FakeClient client = clientePorId();
        client.resultadoItemPorId = new GenesisApiResult(404,
                GenesisApiResult.Classificacao.NAO_ENCONTRADO, null, "{}");
        assertThrows(GenesisSyncService.FalhaIntegracao.class, () ->
                new GenesisSyncService(parametros(), client).sincronizarPorId(candidato(),
                        new ReferenciasBaseInstaladaGenesis(101, 201, 501, 9001L, null)));
        assertEquals(2, client.consultasPorId);
        assertTrue(client.criados.isEmpty());
        assertTrue(client.atualizados.isEmpty());
    }

    @Test void fluxoPorIdAtualizaItemSemAlterarCadastros() throws Exception {
        FakeClient client = clientePorId();
        client.resultadoItemPorId = new GenesisApiResult(200,
                GenesisApiResult.Classificacao.SUCESSO, "9001",
                "{\"id\":9001,\"nserie\":\"SER-1\",\"idModelo\":501}");
        GenesisSyncService.Resultado resultado = new GenesisSyncService(parametros(), client)
                .sincronizarPorId(candidato(), new ReferenciasBaseInstaladaGenesis(101, 201, 501, 9001L, null));
        assertEquals(GenesisSyncService.Operacao.ATUALIZADO, resultado.operacao);
        assertEquals("9001", resultado.api.getIdGenesis());
        assertEquals(java.util.Collections.singletonList(TipoEntidade.ITEM_MATERIAL), client.atualizados);
        assertTrue(client.criados.isEmpty());
        assertEquals(0, client.consultas);
    }

    @Test void fluxoPorIdNaoSobrescreveOutraSerie() {
        FakeClient client = clientePorId();
        client.resultadoItemPorId = new GenesisApiResult(200,
                GenesisApiResult.Classificacao.SUCESSO, "9001",
                "{\"id\":9001,\"nserie\":\"OUTRA\",\"idModelo\":501}");
        assertThrows(GenesisSyncService.FalhaIntegracao.class, () ->
                new GenesisSyncService(parametros(), client).sincronizarPorId(candidato(),
                        new ReferenciasBaseInstaladaGenesis(101, 201, 501, 9001L, null)));
        assertTrue(client.atualizados.isEmpty());
        assertTrue(client.criados.isEmpty());
    }

    private static FakeClient clientePorId() {
        FakeClient client = new FakeClient();
        client.resultadoPorId = new GenesisApiResult(200, GenesisApiResult.Classificacao.SUCESSO,
                "101", "{\"id\":101,\"cnpj\":\"12345678000190\"}");
        return client;
    }

    @Test void fluxoPorIdCriaSomenteItemSemPesquisaOuCadastroDeCliente() throws Exception {
        FakeClient client = new FakeClient();
        client.resultadoPorId = new GenesisApiResult(200, GenesisApiResult.Classificacao.SUCESSO,
                "101", "{\"id\":101,\"cnpj\":\"12345678000190\"}");
        GenesisSyncService.Resultado resultado = new GenesisSyncService(parametros(), client)
                .sincronizarPorId(candidato(), referenciasNovas());
        assertEquals(GenesisSyncService.Operacao.CRIADO, resultado.operacao);
        assertEquals(1, client.consultasPorId);
        assertEquals(0, client.consultas);
        assertEquals(0, client.consultasDocumento);
        assertEquals(java.util.Collections.singletonList(TipoEntidade.ITEM_MATERIAL), client.criados);
        assertTrue(client.atualizados.isEmpty());
    }

    @Test void fluxoPorIdNaoCriaQuandoClienteNaoExiste() {
        FakeClient client = new FakeClient();
        assertThrows(GenesisSyncService.FalhaIntegracao.class, () ->
                new GenesisSyncService(parametros(), client).sincronizarPorId(candidato(), referenciasNovas()));
        assertEquals(1, client.consultasPorId);
        assertEquals(0, client.consultas);
        assertTrue(client.criados.isEmpty());
    }

    @Test void fluxoPorIdBloqueiaVinculoDivergente() {
        FakeClient client = new FakeClient();
        client.resultadoPorId = new GenesisApiResult(200, GenesisApiResult.Classificacao.SUCESSO,
                "999", "{\"id\":999,\"cnpj\":\"12345678000190\"}");
        assertThrows(GenesisSyncService.FalhaIntegracao.class, () ->
                new GenesisSyncService(parametros(), client).sincronizarPorId(candidato(), referenciasNovas()));
        assertTrue(client.criados.isEmpty());
        assertTrue(client.atualizados.isEmpty());
    }

    @Test void fluxoPorIdExigeCadastrosAntesDeChamarApi() {
        FakeClient client = new FakeClient();
        assertThrows(IllegalStateException.class, () ->
                new GenesisSyncService(parametros(), client).sincronizarPorId(candidato(),
                        new ReferenciasBaseInstaladaGenesis(0, 201, 501, null, null)));
        assertEquals(0, client.consultasPorId);
        assertTrue(client.criados.isEmpty());
    }

    @Test void reutilizaFalhaDoClienteSemRepetirConsultasNoLote() throws Exception {
        FakeClient client = new FakeClient();
        client.resultadoPorId = new GenesisApiResult(500, GenesisApiResult.Classificacao.TRANSITORIO, null, "Indisponivel");
        PayloadGenesis payload = new PayloadGenesisFactory("MS").cliente(candidato());
        GenesisSyncService sync = new GenesisSyncService(parametros(), client);
        for (int i=0; i<40; i++) assertThrows(GenesisSyncService.FalhaIntegracao.class, () -> sync.garantir(payload, 95L));
        assertEquals(1, client.consultas);
        assertEquals(1, client.consultasPorId);
        assertTrue(client.criados.isEmpty());
        assertThrows(GenesisSyncService.FalhaIntegracao.class, () -> new GenesisSyncService(parametros(), client).garantir(payload, 95L));
        assertEquals(2, client.consultas);
    }

    @Test void reutilizaClienteCriadoMesmoAposPersistirIdLocal() throws Exception {
        FakeClient client = new FakeClient();
        PayloadGenesis payload = new PayloadGenesisFactory("MS").cliente(candidato());
        GenesisSyncService sync = new GenesisSyncService(parametros(), client);
        GenesisSyncService.Resultado primeiro = sync.garantir(payload, null);
        assertSame(primeiro, sync.garantir(payload, Long.valueOf(primeiro.api.getIdGenesis())));
        assertEquals(1, client.consultas);
        assertEquals(1, client.criados.size());
    }

    @Test void cadastroAlteradoNaoReutilizaPayloadAnterior() throws Exception {
        FakeClient client = new FakeClient();
        GenesisSyncService sync = new GenesisSyncService(parametros(), client);
        CandidatoGenesis c = candidato();
        sync.garantir(new PayloadGenesisFactory("MS").cliente(c), null);
        c.razaoParceiro = "Outra razao";
        sync.garantir(new PayloadGenesisFactory("MS").cliente(c), null);
        assertEquals(2, client.consultas);
    }
    @Test
    void consultaDocumentoIndisponivelNaoAutorizaPost() throws Exception {
        for (int http : new int[]{400, 405, 429, 500}) {
            FakeClient client = new FakeClient();
            client.resultadoDocumento = new GenesisApiResult(http,
                    http >= 500 || http == 429 ? GenesisApiResult.Classificacao.TRANSITORIO
                            : GenesisApiResult.Classificacao.ERRO_FUNCIONAL, null, "Consulta indisponivel");
            PayloadGenesis payload = new PayloadGenesisFactory("MS").cliente(candidato());
            assertThrows(GenesisSyncService.FalhaIntegracao.class,
                    () -> new GenesisSyncService(parametros(), client).garantir(payload, null));
            assertTrue(client.criados.isEmpty());
            assertTrue(client.atualizados.isEmpty());
        }
    }

    @Test
    void itemComIdLocalENaoEncontradoNaoPodeSerRecriado() {
        FakeClient client = new FakeClient();
        ReferenciasBaseInstaladaGenesis referencias =
                new ReferenciasBaseInstaladaGenesis(101, 201, 501, 9001L, "LEGADO-9001");
        assertThrows(GenesisSyncService.FalhaIntegracao.class,
                () -> new GenesisSyncService(parametros(), client).sincronizar(candidato(), referencias));
        assertTrue(client.criados.isEmpty());
        assertTrue(client.atualizados.isEmpty());
    }

    @Test
    void falha500NoIdLocalNaoCriaCliente() throws Exception {
        FakeClient client = new FakeClient();
        client.resultadoPorId = new GenesisApiResult(500, GenesisApiResult.Classificacao.TRANSITORIO,
                null, "Consulta indisponivel");
        PayloadGenesis payload = new PayloadGenesisFactory("MS").cliente(candidato());
        assertThrows(GenesisSyncService.FalhaIntegracao.class,
                () -> new GenesisSyncService(parametros(), client).garantir(payload, 95L));
        assertEquals(0, client.consultasDocumento);
        assertTrue(client.criados.isEmpty());
    }

    @Test
    void enviaSomenteItemMaterialComIdsDaBaseInstalada() throws Exception {
        FakeClient client = new FakeClient();
        ParametrosGenesis parametros = new ParametrosGenesis(1, "HML", "https://teste/api/v1/",
                true, false, 1000, 1000, 3, 1, "MS");
        GenesisSyncService.Resultado resultado = new GenesisSyncService(parametros, client)
                .sincronizar(candidato(), referenciasNovas());

        assertEquals(java.util.Collections.singletonList(TipoEntidade.ITEM_MATERIAL), client.criados);
        ItemMaterialGenesisDto item = (ItemMaterialGenesisDto) resultado.payload.getDto();
        assertEquals(Long.valueOf(101), item.idCliente);
        assertEquals(Long.valueOf(201), item.idSetor);
        assertEquals(Long.valueOf(501), item.idModelo);
        assertEquals("SER-1", item.nserie);
        assertEquals("1|100|1|30|SER-1", item.observacao.replace("Movimento Sankhya: ", ""));
    }

    @Test
    void identificaFalhaNaConsultaDoItemEIncluiResposta() {
        FakeClient client = new FakeClient();
        client.falhaGet = new GenesisApiResult(400, GenesisApiResult.Classificacao.ERRO_FUNCIONAL,
                null, "codigoExterno invalido");

        GenesisSyncService.FalhaIntegracao erro = assertThrows(GenesisSyncService.FalhaIntegracao.class,
                () -> new GenesisSyncService(parametros(), client).sincronizar(candidato(), referenciasNovas()));

        assertTrue(erro.getMessage().contains("ITEM_MATERIAL na etapa GET"));
        assertTrue(erro.getMessage().contains("codigoExterno invalido"));
    }

    @Test
    void identificaFalhaNaCriacaoDoItemEIncluiResposta() {
        FakeClient client = new FakeClient();
        client.falhaPost = new GenesisApiResult(400, GenesisApiResult.Classificacao.ERRO_FUNCIONAL,
                null, "nomeFantasia obrigatorio");

        GenesisSyncService.FalhaIntegracao erro = assertThrows(GenesisSyncService.FalhaIntegracao.class,
                () -> new GenesisSyncService(parametros(), client).sincronizar(candidato(), referenciasNovas()));

        assertTrue(erro.getMessage().contains("ITEM_MATERIAL na etapa POST"));
        assertTrue(erro.getMessage().contains("nomeFantasia obrigatorio"));
    }

    @Test
    void validaItemExistenteNaApiEAtualizaPeloIdRemoto() throws Exception {
        FakeClient client = new FakeClient();
        client.resultadoGet = new GenesisApiResult(200, GenesisApiResult.Classificacao.SUCESSO,
                "9001", "{}");
        ReferenciasBaseInstaladaGenesis referencias =
                new ReferenciasBaseInstaladaGenesis(101, 201, 501, 9001L, "LEGADO-9001");

        GenesisSyncService.Resultado resultado = new GenesisSyncService(parametros(), client)
                .sincronizar(candidato(), referencias);

        assertEquals(1, client.consultas);
        assertEquals(java.util.Collections.singletonList(TipoEntidade.ITEM_MATERIAL), client.atualizados);
        assertEquals("9001", resultado.api.getIdGenesis());
        assertEquals("LEGADO-9001", resultado.payload.getCodigoExterno());
    }

    @Test
    void registraPayloadEDecisoesDoUpsert() throws Exception {
        FakeClient client = new FakeClient();
        List<String> logs = new ArrayList<String>();

        new GenesisSyncService(parametros(), client, logs::add)
                .sincronizar(candidato(), referenciasNovas());

        String logCompleto = String.join("\n", logs);
        assertTrue(logCompleto.contains("Payload final montado"));
        assertTrue(logCompleto.contains("\"idModelo\":501"));
        assertTrue(logCompleto.contains("Resultado do GET por codigoExterno"));
        assertTrue(logCompleto.contains("nao encontrado por nenhuma identidade segura; executar POST"));
        assertTrue(logCompleto.contains("Sincronizacao aceita"));
    }

    @Test
    void dispensaPutQuandoCadastroRemotoJaEstaAlinhado() throws Exception {
        FakeClient client = new FakeClient();
        PayloadGenesis payload = new PayloadGenesisFactory("MS").cliente(candidato());
        client.resultadoGet = new GenesisApiResult(200, GenesisApiResult.Classificacao.SUCESSO,
                "95", payload.getJson(), "[resposta protegida]");

        GenesisSyncService.Resultado resultado = new GenesisSyncService(parametros(), client)
                .garantir(payload, 95L);

        assertEquals(GenesisSyncService.Operacao.SEM_ALTERACAO, resultado.operacao);
        assertTrue(client.atualizados.isEmpty());
        assertTrue(client.criados.isEmpty());
    }

    @Test
    void atualizaCadastroRemotoQuandoGetApontaDivergencia() throws Exception {
        FakeClient client = new FakeClient();
        PayloadGenesis payload = new PayloadGenesisFactory("MS").cliente(candidato());
        client.resultadoGet = new GenesisApiResult(200, GenesisApiResult.Classificacao.SUCESSO,
                "95", "{\"codigoExterno\":\"SNK-CLI-10\",\"razaoSocial\":\"Nome antigo\"}");

        GenesisSyncService.Resultado resultado = new GenesisSyncService(parametros(), client)
                .garantir(payload, 95L);

        assertEquals(GenesisSyncService.Operacao.ATUALIZADO, resultado.operacao);
        assertEquals(java.util.Collections.singletonList(TipoEntidade.CLIENTE), client.atualizados);
        assertEquals("95", resultado.api.getIdGenesis());
    }

    @Test
    void reconciliaClientePeloIdLocalQuandoCodigoExternoMudou() throws Exception {
        FakeClient client = new FakeClient();
        PayloadGenesis payload = new PayloadGenesisFactory("MS").cliente(candidato());
        client.resultadoPorId = new GenesisApiResult(200, GenesisApiResult.Classificacao.SUCESSO,
                "95", "{\"id\":95,\"codigoExterno\":\"ANTIGO\",\"cnpj\":\"12.345.678/0001-90\"}");

        GenesisSyncService.Resultado resultado = new GenesisSyncService(parametros(), client)
                .garantir(payload, 95L);

        assertEquals(GenesisSyncService.Operacao.ATUALIZADO, resultado.operacao);
        assertEquals("95", resultado.api.getIdGenesis());
        assertEquals(1, client.consultasPorId);
        assertEquals(0, client.consultasDocumento);
        assertTrue(client.criados.isEmpty());
    }

    @Test
    void ignoraIdLocalDeOutroDocumentoEReconciliaClientePeloCnpj() throws Exception {
        FakeClient client = new FakeClient();
        PayloadGenesis payload = new PayloadGenesisFactory("MS").cliente(candidato());
        client.resultadoPorId = new GenesisApiResult(200, GenesisApiResult.Classificacao.SUCESSO,
                "999", "{\"id\":999,\"cnpj\":\"99999999000199\"}");
        client.resultadoDocumento = new GenesisApiResult(200, GenesisApiResult.Classificacao.SUCESSO,
                "95", "{\"id\":95,\"cnpj\":\"12345678000190\"}");

        GenesisSyncService.Resultado resultado = new GenesisSyncService(parametros(), client)
                .garantir(payload, 999L);

        assertEquals("95", resultado.api.getIdGenesis());
        assertEquals(1, client.consultasDocumento);
        assertEquals(java.util.Collections.singletonList(TipoEntidade.CLIENTE), client.atualizados);
        assertTrue(client.criados.isEmpty());
    }

    @Test
    void bloqueiaProdutoQueNaoSejaEquipamentoM1AntesDoHttp() {
        FakeClient client = new FakeClient();
        CandidatoGenesis consumivel = candidato();
        consumivel.tipoProduto = "M4";

        IllegalStateException erro = assertThrows(IllegalStateException.class,
                () -> new GenesisSyncService(parametros(), client)
                        .sincronizar(consumivel, referenciasNovas()));

        assertTrue(erro.getMessage().contains("somente tipo efetivo='M1'"));
        assertEquals(0, client.consultas);
        assertTrue(client.criados.isEmpty());
        assertTrue(client.atualizados.isEmpty());
    }

    @Test
    void bloqueiaSetorDeOutroClienteEncontradoPorCodigoExterno() throws Exception {
        verificarConflitoSetor("{\"id\":1499,\"idCliente\":999}", false);
    }

    @Test
    void bloqueiaSetorSemClienteNaRespostaPorCodigoExterno() throws Exception {
        verificarConflitoSetor("{\"id\":1499}", false);
    }

    @Test
    void bloqueiaSetorDeOutroClienteEncontradoPeloIdLocal() throws Exception {
        verificarConflitoSetor("{\"id\":1499,\"idCliente\":999}", true);
    }

    private static void verificarConflitoSetor(String resposta, boolean porId) throws Exception {
        FakeClient client = new FakeClient();
        GenesisApiResult consulta = new GenesisApiResult(200,
                GenesisApiResult.Classificacao.SUCESSO, "1499", resposta);
        if (porId) client.resultadoPorId = consulta;
        else client.resultadoGet = consulta;
        PayloadGenesis payload = new PayloadGenesisFactory("MS").setor(candidato(), "1369");

        GenesisSyncService.FalhaIntegracao erro = assertThrows(GenesisSyncService.FalhaIntegracao.class,
                () -> new GenesisSyncService(parametros(), client).garantir(payload, 1499L));

        assertEquals(TipoEntidade.SETOR, erro.entidade);
        assertEquals(GenesisApiResult.Classificacao.CONFLITO, erro.resultado.getClassificacao());
        assertTrue(erro.getMessage().contains("Cliente esperado=1369"));
        assertTrue(client.criados.isEmpty());
        assertTrue(client.atualizados.isEmpty());
    }

    @Test
    void atualizaSetorDoMesmoClienteSemCriarOutro() throws Exception {
        FakeClient client = new FakeClient();
        client.resultadoGet = new GenesisApiResult(200, GenesisApiResult.Classificacao.SUCESSO,
                "1499", "{\"id\":1499,\"idCliente\":1369,\"nome\":\"Nome antigo\"}");
        PayloadGenesis payload = new PayloadGenesisFactory("MS").setor(candidato(), "1369");

        GenesisSyncService.Resultado resultado = new GenesisSyncService(parametros(), client)
                .garantir(payload, 1499L);

        assertEquals(GenesisSyncService.Operacao.ATUALIZADO, resultado.operacao);
        assertEquals("1499", resultado.api.getIdGenesis());
        assertEquals(java.util.Collections.singletonList(TipoEntidade.SETOR), client.atualizados);
        assertTrue(client.criados.isEmpty());
    }

    @Test
    void reutilizaSetorDoMesmoClientePeloIdLocal() throws Exception {
        FakeClient client = new FakeClient();
        client.resultadoPorId = new GenesisApiResult(200, GenesisApiResult.Classificacao.SUCESSO,
                "1499", "{\"id\":1499,\"idCliente\":1369}");
        PayloadGenesis payload = new PayloadGenesisFactory("MS").setor(candidato(), "1369");

        GenesisSyncService.Resultado resultado = new GenesisSyncService(parametros(), client)
                .garantir(payload, 1499L);

        assertEquals("1499", resultado.api.getIdGenesis());
        assertEquals(1, client.consultasPorId);
        assertEquals(java.util.Collections.singletonList(TipoEntidade.SETOR), client.atualizados);
        assertTrue(client.criados.isEmpty());
    }

    @Test
    void dispensaPutParaSetorAlinhadoDoMesmoCliente() throws Exception {
        FakeClient client = new FakeClient();
        PayloadGenesis payload = new PayloadGenesisFactory("MS").setor(candidato(), "1369");
        client.resultadoGet = new GenesisApiResult(200, GenesisApiResult.Classificacao.SUCESSO,
                "1499", payload.getJson());

        GenesisSyncService.Resultado resultado = new GenesisSyncService(parametros(), client)
                .garantir(payload, 1499L);

        assertEquals(GenesisSyncService.Operacao.SEM_ALTERACAO, resultado.operacao);
        assertTrue(client.criados.isEmpty());
        assertTrue(client.atualizados.isEmpty());
    }

    @Test
    void criaSetorQuandoNaoExisteNaApi() throws Exception {
        FakeClient client = new FakeClient();
        PayloadGenesis payload = new PayloadGenesisFactory("MS").setor(candidato(), "1369");

        GenesisSyncService.Resultado resultado = new GenesisSyncService(parametros(), client)
                .garantir(payload, 1499L);

        assertEquals(GenesisSyncService.Operacao.CRIADO, resultado.operacao);
        assertEquals(java.util.Collections.singletonList(TipoEntidade.SETOR), client.criados);
        assertTrue(client.atualizados.isEmpty());
    }

    private static ParametrosGenesis parametros() {
        return new ParametrosGenesis(1, "HML", "https://teste/api/v1/",
                true, false, 1000, 1000, 3, 1, "MS");
    }

    private static ReferenciasBaseInstaladaGenesis referenciasNovas() {
        return new ReferenciasBaseInstaladaGenesis(101, 201, 501, null, null);
    }

    private static CandidatoGenesis candidato() {
        CandidatoGenesis c = new CandidatoGenesis();
        c.codParc=BigDecimal.TEN; c.codGrupoProd=new BigDecimal("20"); c.codProd=new BigDecimal("30");
        c.tipoProduto="M1";
        c.numNota=new BigDecimal("900"); c.dataMovimento=new Date(1700000000000L);
        c.valorUnitario=new BigDecimal("100"); c.tipoEvento="SAIDA"; c.tipoMovimento="SAIDA";
        c.situacaoGenesis="Operacional"; c.statusEstoque="O"; c.statusEstoqueDescricao="Operacional";
        c.descricaoGrupo="Equipamento"; c.descricaoProduto="Monitor"; c.serie="SER-1"; c.garantiaMeses=12;
        c.nomeParceiro="Cliente"; c.razaoParceiro="Cliente SA"; c.cnpjCpf="12345678000190";
        c.parceiroAtivo="Ativo"; c.produtoAtivo="Ativo"; c.marca="Marca"; c.nomeFabricante="Marca";
        c.cnpjFabricante="12345678000190";
        c.poder="PROPRIO"; c.localizacao="Cliente"; c.chaveMovimento="1|100|1|30|SER-1";
        c.chaveCliente="10"; c.chaveSetor="10|PADRAO"; c.chaveMaterial="20"; c.chaveFabricante="MARCA";
        c.chaveModelo="30"; c.chaveItem="30|SER-1"; c.codigoExternoCliente="SNK-CLI-10";
        c.codigoExternoSetor="SNK-SET-10"; c.codigoExternoMaterial="SNK-MAT-20";
        c.codigoExternoFabricante="SNK-FAB-MARCA"; c.codigoExternoModelo="SNK-MOD-30";
        c.codigoExternoItem="SNK-ITEM-30-SER-1"; return c;
    }

    private static final class FakeClient implements GenesisApiClient {
        final List<TipoEntidade> criados = new ArrayList<TipoEntidade>();
        final List<TipoEntidade> atualizados = new ArrayList<TipoEntidade>();
        int consultas;
        int consultasPorId;
        int consultasDocumento;
        GenesisApiResult falhaGet;
        GenesisApiResult falhaPost;
        GenesisApiResult resultadoGet;
        GenesisApiResult resultadoPorId;
        GenesisApiResult resultadoItemPorId;
        GenesisApiResult resultadoDocumento;
        public GenesisApiResult buscarPorCodigoExterno(TipoEntidade tipo, String codigo) {
            consultas++;
            if (falhaGet != null) return falhaGet;
            if (resultadoGet != null) return resultadoGet;
            return new GenesisApiResult(404, GenesisApiResult.Classificacao.NAO_ENCONTRADO, null, "{}");
        }
        public GenesisApiResult buscarPorId(TipoEntidade tipo, String id) {
            consultasPorId++;
            if (tipo == TipoEntidade.ITEM_MATERIAL && resultadoItemPorId != null) return resultadoItemPorId;
            if (resultadoPorId != null) return resultadoPorId;
            return new GenesisApiResult(404, GenesisApiResult.Classificacao.NAO_ENCONTRADO, null, "{}");
        }
        public GenesisApiResult buscarPorDocumento(TipoEntidade tipo, String documento) {
            consultasDocumento++;
            if (resultadoDocumento != null) return resultadoDocumento;
            return new GenesisApiResult(200, GenesisApiResult.Classificacao.NAO_ENCONTRADO, null, "[]");
        }
        public GenesisApiResult criar(TipoEntidade tipo, Object payload) {
            if (falhaPost != null) return falhaPost;
            criados.add(tipo);
            return new GenesisApiResult(201, GenesisApiResult.Classificacao.SUCESSO,
                    String.valueOf((tipo.ordinal() + 1) * 100 + 1), "{}");
        }
        public GenesisApiResult atualizar(TipoEntidade tipo, String id, Object payload) {
            atualizados.add(tipo);
            return new GenesisApiResult(200, GenesisApiResult.Classificacao.SUCESSO, null, "{}");
        }
    }
}
