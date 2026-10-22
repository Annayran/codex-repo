package br.com.zaya.mahospitalar.genesis.service;

import br.com.zaya.mahospitalar.genesis.client.*;
import br.com.zaya.mahospitalar.genesis.config.ParametrosGenesis;
import br.com.zaya.mahospitalar.genesis.domain.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class GenesisCadastroSyncServiceTest {
    private static final TipoEntidade[] TIPOS = {TipoEntidade.CLIENTE, TipoEntidade.SETOR,
            TipoEntidade.MATERIAL, TipoEntidade.FABRICANTE, TipoEntidade.MODELO, TipoEntidade.ITEM_MATERIAL};

    @Test void criaOsSeisTiposApenasAposReservaEConfirmaId() throws Exception {
        for (TipoEntidade tipo : TIPOS) {
            Fixture f = new Fixture();
            GenesisSyncService.Resultado r = f.service().resolver(payload(tipo), null);
            assertEquals(GenesisSyncService.Operacao.CRIADO, r.operacao);
            assertEquals(Arrays.asList("GET_CODIGO", "RESERVA", "POST", "CONFIRMA"), f.eventos);
            assertEquals(90L, f.id);
        }
    }

    @Test void idLocalUsaSomenteGetSemPutParaTodosOsTipos() throws Exception {
        for (TipoEntidade tipo : TIPOS) {
            Fixture f = new Fixture();
            f.get = sucesso(tipo);
            assertEquals(GenesisSyncService.Operacao.SEM_ALTERACAO,
                    f.service().resolver(payload(tipo), 90L).operacao);
            assertEquals(Arrays.asList("GET_ID", "CONFIRMA"), f.eventos);
        }
    }

    @Test void registroExistentePorCodigoNaoGeraPost() throws Exception {
        Fixture f = new Fixture(); f.codigo = sucesso(TipoEntidade.MODELO);
        f.service().resolver(payload(TipoEntidade.MODELO), null);
        assertEquals(Arrays.asList("GET_CODIGO", "CONFIRMA"), f.eventos);
    }

    @Test void fabricanteVinculadoAceitaDocumentoZeroECodigoExternoNulo() throws Exception {
        Fixture f=new Fixture();
        f.get=new GenesisApiResult(200,GenesisApiResult.Classificacao.SUCESSO,"54",
                "{\"id\":54,\"cnpj\":\"0\",\"codigoExterno\":null,\"nomeFantasia\":\"MASIMO\"}");
        GenesisSyncService.Resultado r=f.service().resolver(payload(TipoEntidade.FABRICANTE),54L);
        assertEquals(GenesisSyncService.Operacao.SEM_ALTERACAO,r.operacao);
        assertEquals(Arrays.asList("GET_ID","CONFIRMA"),f.eventos);
        assertEquals(54L,f.id);
    }

    @Test void fabricanteVinculadoNaoAceitaOutroIdNoCorpo() throws Exception {
        Fixture f=new Fixture();
        f.get=new GenesisApiResult(200,GenesisApiResult.Classificacao.SUCESSO,"54","{\"id\":55}");
        assertThrows(IllegalStateException.class,()->f.service().resolver(payload(TipoEntidade.FABRICANTE),54L));
        assertEquals(Collections.singletonList("GET_ID"),f.eventos);
        assertNull(f.id);
    }

    @Test void fabricanteVinculadoNaoRecriaEm404() {
        Fixture f=new Fixture();
        assertThrows(Exception.class,()->f.service().resolver(payload(TipoEntidade.FABRICANTE),54L));
        assertEquals(Collections.singletonList("GET_ID"),f.eventos);
    }

    @Test void fabricanteSemVinculoContinuaValidandoIdentidadePorDocumento() {
        Fixture f=new Fixture();
        f.codigo=new GenesisApiResult(200,GenesisApiResult.Classificacao.SUCESSO,"54",
                "{\"id\":54,\"cnpj\":\"0\",\"codigoExterno\":null}");
        assertThrows(IllegalStateException.class,()->f.service().resolver(payload(TipoEntidade.FABRICANTE),null));
        assertEquals(Collections.singletonList("GET_CODIGO"),f.eventos);
    }

    @Test void clienteVinculadoContinuaExigindoDocumentoCompativel() {
        Fixture f=new Fixture();
        f.get=new GenesisApiResult(200,GenesisApiResult.Classificacao.SUCESSO,"54","{\"id\":54,\"cnpj\":\"0\"}");
        assertThrows(IllegalStateException.class,()->f.service().resolver(payload(TipoEntidade.CLIENTE),54L));
        assertNull(f.id);
    }

    @Test void naoRecriaIdLocalAusenteOuIndisponivel() throws Exception {
        for (int http : new int[]{404, 429, 500}) {
            Fixture f = new Fixture(); f.get = falha(http);
            assertThrows(Exception.class, () -> f.service().resolver(payload(TipoEntidade.CLIENTE), 90L));
            assertEquals(Collections.singletonList("GET_ID"), f.eventos);
        }
    }

    @Test void consultaExternaComErroNaoAutorizaPost() throws Exception {
        for (int http : new int[]{400, 429, 500}) {
            Fixture f = new Fixture(); f.codigo = falha(http);
            assertThrows(Exception.class, () -> f.service().resolver(payload(TipoEntidade.MATERIAL), null));
            assertEquals(Collections.singletonList("GET_CODIGO"), f.eventos);
        }
    }

    @Test void postIncertoNaoSeRepeteAposReiniciarServico() throws Exception {
        Fixture f = new Fixture(); f.post = falha(500);
        assertThrows(Exception.class, () -> f.service().resolver(payload(TipoEntidade.MODELO), null));
        assertThrows(Exception.class, () -> f.service().resolver(payload(TipoEntidade.MODELO), null));
        assertEquals(1, Collections.frequency(f.eventos, "POST"));
        assertTrue(f.incerto);
    }

    @Test void retornoConfirmadoRecuperaPorIdSemNovoPost() throws Exception {
        Fixture f = new Fixture();
        f.service().resolver(payload(TipoEntidade.MODELO), null);
        f.eventos.clear(); f.get = sucesso(TipoEntidade.MODELO);
        f.service().resolver(payload(TipoEntidade.MODELO), null);
        assertEquals(Arrays.asList("GET_ID", "CONFIRMA"), f.eventos);
    }

    @Test void postIncertoRecuperaCodigoConfirmadoSemRepetirCriacao() throws Exception {
        Fixture f = new Fixture(); f.post = falha(500);
        assertThrows(Exception.class, () -> f.service().resolver(payload(TipoEntidade.MODELO), null));
        f.codigo = sucesso(TipoEntidade.MODELO);
        GenesisSyncService.Resultado r = f.service().resolver(payload(TipoEntidade.MODELO), null);
        assertEquals(GenesisSyncService.Operacao.SEM_ALTERACAO, r.operacao);
        assertEquals(1, Collections.frequency(f.eventos, "POST"));
        assertEquals(90L, f.id);
    }

    @Test void idOuIdentidadeDivergenteNaoConfirma() throws Exception {
        Fixture f = new Fixture();
        f.get = new GenesisApiResult(200, GenesisApiResult.Classificacao.SUCESSO, "91",
                "{\"id\":91,\"codigoExterno\":\"COD\"}");
        assertThrows(Exception.class, () -> f.service().resolver(payload(TipoEntidade.MODELO), 90L));
        assertNull(f.id);
    }

    @Test void postSemIdMantemReservaIncerta() throws Exception {
        Fixture f = new Fixture();
        f.post = new GenesisApiResult(201, GenesisApiResult.Classificacao.SUCESSO, null, "{}");
        assertThrows(Exception.class, () -> f.service().resolver(payload(TipoEntidade.MODELO), null));
        assertTrue(f.incerto);
        assertFalse(f.eventos.contains("CONFIRMA"));
    }
    @Test void post200ComListaVaziaExplicaBloqueioSemConfirmarOuRepetirPost() throws Exception {
        Fixture f = new Fixture();
        f.post = new GenesisApiResult(200,GenesisApiResult.Classificacao.SUCESSO,null,"[]");
        Exception erro = assertThrows(GenesisPostGuard.EnvioIncerto.class,
                () -> f.service().resolver(payload(TipoEntidade.ITEM_MATERIAL),null));
        assertTrue(erro.getMessage().contains("HTTP=200"));
        assertTrue(erro.getMessage().contains("corpo=[]"));
        assertTrue(erro.getMessage().contains("criacao nao confirmada"));
        assertTrue(f.incerto);
        assertFalse(f.eventos.contains("CONFIRMA"));
        assertThrows(GenesisPostGuard.EnvioIncerto.class,
                () -> f.service().resolver(payload(TipoEntidade.ITEM_MATERIAL),null));
        assertEquals(1,Collections.frequency(f.eventos,"POST"));
    }
    @Test void reservaEncapsuladaRecuperaItemPorGetSemPost() throws Exception {
        Fixture f=new Fixture();f.incerto=true;f.encapsular=true;f.codigo=sucesso(TipoEntidade.ITEM_MATERIAL);
        f.service().resolver(payload(TipoEntidade.ITEM_MATERIAL),null);
        assertEquals(Arrays.asList("GET_CODIGO","CONFIRMA"),f.eventos);assertEquals(90L,f.id);
    }
    @Test void reservaEncapsuladaAusenteContinuaProtegida() {
        Fixture f=new Fixture();f.incerto=true;f.encapsular=true;
        Exception e=assertThrows(GenesisPostGuard.EnvioIncerto.class,()->f.service().resolver(payload(TipoEntidade.ITEM_MATERIAL),null));
        assertTrue(e.getMessage().contains("GET_RECONCILIACAO executado"));
        assertEquals(Collections.singletonList("GET_CODIGO"),f.eventos);assertTrue(f.incerto);
    }
    @Test void erroComTextoIncertoSemCausaTipadaNaoAutorizaConsulta() {
        Fixture f=new Fixture();f.erroLeitura=new IllegalStateException("ENVIO_INCERTO");
        assertThrows(IllegalStateException.class,()->f.service().resolver(payload(TipoEntidade.ITEM_MATERIAL),null));
        assertTrue(f.eventos.isEmpty());
    }
    @Test void reservaEncapsuladaRespeitaErro429SemPost() {
        Fixture f=new Fixture();f.incerto=true;f.encapsular=true;f.codigo=falha(429);
        assertThrows(GenesisSyncService.FalhaIntegracao.class,()->f.service().resolver(payload(TipoEntidade.ITEM_MATERIAL),null));
        assertEquals(Collections.singletonList("GET_CODIGO"),f.eventos);assertTrue(f.incerto);
    }

    private static PayloadGenesis payload(TipoEntidade tipo) throws Exception {
        Map<String,Object> dados = new LinkedHashMap<String,Object>();
        dados.put("codigoExterno", "COD"); dados.put("cnpj", "12345678000190");
        dados.put("idCliente", 10); dados.put("idModelo", 20); dados.put("nserie", "SERIE");
        return new PayloadGenesis(tipo, "CHAVE", "COD", dados,
                new ObjectMapper().writeValueAsString(dados), "HASH");
    }

    private static GenesisApiResult sucesso(TipoEntidade tipo) throws Exception {
        String json=payload(tipo).getJson();
        return new GenesisApiResult(200, GenesisApiResult.Classificacao.SUCESSO, "90", "{\"id\":90,"+json.substring(1));
    }
    private static GenesisApiResult falha(int http) {
        return new GenesisApiResult(http, http == 404 ? GenesisApiResult.Classificacao.NAO_ENCONTRADO :
                http >= 429 ? GenesisApiResult.Classificacao.TRANSITORIO :
                        GenesisApiResult.Classificacao.ERRO_FUNCIONAL, null, "{}");
    }

    private static final class Fixture implements GenesisApiClient, GenesisPostGuard {
        final List<String> eventos = new ArrayList<String>();
        GenesisApiResult codigo = falha(404), get = falha(404);
        GenesisApiResult post = new GenesisApiResult(201, GenesisApiResult.Classificacao.SUCESSO, "90", "{}");
        Long id; boolean incerto, encapsular; Exception erroLeitura;
        GenesisCadastroSyncService service() {
            return new GenesisCadastroSyncService(new ParametrosGenesis(1, "HML", "https://teste/api/v1/",
                    true, false, 1000, 1000, 3, 1, "MS"), this, this);
        }
        public GenesisApiResult buscarPorCodigoExterno(TipoEntidade t, String c) { eventos.add("GET_CODIGO"); return codigo; }
        public GenesisApiResult buscarPorId(TipoEntidade t, String i) { eventos.add("GET_ID"); return get; }
        public GenesisApiResult buscarPorDocumento(TipoEntidade t, String d) { throw new AssertionError("GET documento proibido"); }
        public GenesisApiResult criar(TipoEntidade t, Object p) { eventos.add("POST"); assertTrue(incerto); return post; }
        public GenesisApiResult atualizar(TipoEntidade t, String i, Object p) { throw new AssertionError("PUT proibido"); }
        public Long idConfirmado(PayloadGenesis p) throws Exception {
            if(erroLeitura!=null)throw erroLeitura;
            if (incerto && id == null) {
                GenesisPostGuard.EnvioIncerto erro=new GenesisPostGuard.EnvioIncerto("ENVIO_INCERTO");
                if(encapsular)throw new IllegalStateException("Transacao autonoma",erro);
                throw erro;
            }
            return id;
        }
        public Long reservar(PayloadGenesis p) { eventos.add("RESERVA"); incerto = true; return id; }
        public void confirmar(PayloadGenesis p, GenesisApiResult r, boolean criado) {
            eventos.add("CONFIRMA"); id = Long.valueOf(r.getIdGenesis()); incerto = false;
        }
        public void falhar(PayloadGenesis p, GenesisApiResult r) { }
    }
}
