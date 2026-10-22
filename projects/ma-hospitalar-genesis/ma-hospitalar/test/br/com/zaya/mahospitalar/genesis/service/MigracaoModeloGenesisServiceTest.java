package br.com.zaya.mahospitalar.genesis.service;

import br.com.zaya.mahospitalar.genesis.client.*;
import br.com.zaya.mahospitalar.genesis.domain.TipoEntidade;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class MigracaoModeloGenesisServiceTest {
    @Test void migraSomenteCodigoPreservandoTodosOsDemaisCampos()throws Exception{
        Fake f=new Fake();MigracaoModeloGenesisService.Resultado r=f.run();
        assertEquals(Arrays.asList("GET_ID","GET_DESTINO","ANTES_PUT","PUT","GET_ID"),f.calls);
        assertEquals("MC-EP90IMB",f.payload.get("codigoExterno").asText());assertEquals("Nome original",f.payload.get("nome").asText());
        assertEquals(311,f.payload.get("idMaterial").asInt());assertTrue(f.payload.get("foto").isNull());assertNotNull(r.put);
    }
    @Test void destinoJaAplicadoRecuperaSemOutroPut()throws Exception{
        Fake f=new Fake();f.current=ok("MC-EP90IMB",6156);f.target=ok("MC-EP90IMB",6156);
        assertNull(f.run().put);assertEquals(Arrays.asList("GET_ID","GET_DESTINO"),f.calls);
    }
    @Test void codigoDestinoEmOutroIdBloqueia(){
        Fake f=new Fake();f.target=ok("MC-EP90IMB",999);
        assertThrows(Exception.class,f::run);assertFalse(f.calls.contains("PUT"));
    }
    @Test void idAtualDivergenteBloqueia(){
        Fake f=new Fake();f.current=ok("SNK-MOD-14862",999);
        assertThrows(Exception.class,f::run);assertEquals(Collections.singletonList("GET_ID"),f.calls);
    }
    @Test void codigoRemotoAlheioNaoESobrescrito(){
        Fake f=new Fake();f.current=ok("OUTRO",6156);assertThrows(Exception.class,f::run);assertFalse(f.calls.contains("PUT"));
    }
    @Test void erro429NaBuscaNaoAutorizaPut(){
        Fake f=new Fake();f.target=bad(429);assertThrows(Exception.class,f::run);assertFalse(f.calls.contains("PUT"));
    }
    @Test void vazioNoDestinoMasIdJaCorrigidoNaoAutorizaPut(){
        Fake f=new Fake();f.current=ok("MC-EP90IMB",6156);assertThrows(Exception.class,f::run);assertFalse(f.calls.contains("PUT"));
    }
    @Test void put200SemCorpoPrecisaDeGetConfirmado()throws Exception{
        Fake f=new Fake();f.putResult=new GenesisApiResult(200,GenesisApiResult.Classificacao.SUCESSO,null,"[]");assertNotNull(f.run().confirmacao);
    }
    @Test void getDepoisDoPutSemAlteracaoNaoConfirma(){
        Fake f=new Fake();f.after=ok("SNK-MOD-14862",6156);assertThrows(Exception.class,f::run);
    }
    @Test void mudancaDeOutroCampoNaoConfirma(){
        Fake f=new Fake();f.after=new GenesisApiResult(200,GenesisApiResult.Classificacao.SUCESSO,"6156",body("MC-EP90IMB",6156).replace("Nome original","Nome alterado"));
        assertThrows(Exception.class,f::run);
    }
    @Test void destinoVazioEIdInvalidoNaoChamamApi(){
        Fake f=new Fake();assertThrows(Exception.class,()->new MigracaoModeloGenesisService(f).migrar(0,"SNK-MOD-14862","MC",()->{}));
        assertThrows(Exception.class,()->new MigracaoModeloGenesisService(f).migrar(6156,"SNK-MOD-14862"," ",()->{}));assertTrue(f.calls.isEmpty());
    }
    @Test void reservaAlteradaAntesDoPutBloqueia(){
        Fake f=new Fake();assertThrows(Exception.class,()->new MigracaoModeloGenesisService(f).migrar(6156,"SNK-MOD-14862","MC-EP90IMB",()->{throw new IllegalStateException("Lease alterada");}));
        assertFalse(f.calls.contains("PUT"));
    }
    private static String body(String code,long id){return "{\"id\":"+id+",\"codigoExterno\":\""+code+"\",\"nome\":\"Nome original\",\"idMaterial\":311,\"idEmpresaFabricante\":4,\"foto\":null,\"status\":\"Ativo\",\"dtAlteracao\":1}";}
    private static GenesisApiResult ok(String code,long id){return new GenesisApiResult(200,GenesisApiResult.Classificacao.SUCESSO,String.valueOf(id),body(code,id));}
    private static GenesisApiResult bad(int http){return new GenesisApiResult(http,http==404?GenesisApiResult.Classificacao.NAO_ENCONTRADO:GenesisApiResult.Classificacao.TRANSITORIO,null,"[]");}
    private static final class Fake implements GenesisApiClient{
        List<String> calls=new ArrayList<String>();JsonNode payload;
        GenesisApiResult current=ok("SNK-MOD-14862",6156),target=bad(404),after=ok("MC-EP90IMB",6156),putResult=ok("MC-EP90IMB",6156);
        MigracaoModeloGenesisService.Resultado run()throws Exception{return new MigracaoModeloGenesisService(this).migrar(6156,"SNK-MOD-14862","MC-EP90IMB",()->calls.add("ANTES_PUT"));}
        public GenesisApiResult buscarPorId(TipoEntidade t,String id){assertEquals(TipoEntidade.MODELO,t);assertEquals("6156",id);calls.add("GET_ID");return calls.contains("PUT")?after:current;}
        public GenesisApiResult buscarPorCodigoExterno(TipoEntidade t,String code){assertEquals("MC-EP90IMB",code);calls.add("GET_DESTINO");return target;}
        public GenesisApiResult atualizar(TipoEntidade t,String id,Object p){assertEquals(TipoEntidade.MODELO,t);assertEquals("6156",id);calls.add("PUT");payload=((JsonNode)p).deepCopy();return putResult;}
        public GenesisApiResult criar(TipoEntidade t,Object p){throw new AssertionError("POST proibido");}
        public GenesisApiResult buscarPorDocumento(TipoEntidade t,String d){throw new AssertionError("GET documento proibido");}
    }
}
