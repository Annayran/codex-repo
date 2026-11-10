package br.com.zaya.mahospitalar.genesis.service;

import br.com.zaya.mahospitalar.genesis.client.*;
import br.com.zaya.mahospitalar.genesis.domain.TipoEntidade;
import br.com.zaya.mahospitalar.genesis.dto.EstoqueGenesisDto;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EstoqueGenesisSyncServiceTest {
    @Test void custoAusenteNaoEEnviadoNoJson() throws Exception {
        br.com.zaya.mahospitalar.genesis.dto.EstoqueGenesisDto d = new br.com.zaya.mahospitalar.genesis.dto.EstoqueGenesisDto();
        d.vlrVenda = BigDecimal.TEN;
        d.ipi = BigDecimal.ZERO;
        String json = new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(d);
        assertFalse(json.contains("vlrCusto"));
        assertTrue(json.contains("vlrVenda"));
        assertTrue(json.contains("ipi"));
    }
    @Test void reativacaoUsaPutSemNovoPost() throws Exception {
        Fake api=new Fake();api.get=encontrado();
        assertEquals("ATUALIZADO",new EstoqueGenesisSyncService(api).sincronizar(dto(),"M2",null,true,9L).operacao);
        assertEquals(1,api.puts);assertEquals(0,api.posts);
    }
    @Test void idDivergenteNuncaInativa() {
        Fake api=new Fake();api.get=new GenesisApiResult(200,GenesisApiResult.Classificacao.SUCESSO,"10","{\"id\":10,\"codigoExterno\":\"P1\"}");
        assertThrows(IllegalStateException.class,()->new EstoqueGenesisSyncService(api).sincronizar(dto(),"M2",null,false,9L));
        assertEquals(0,api.deletes);
    }
    @Test void criaCatalogoSemMexerEmEquipamentos() throws Exception {
        Fake api = new Fake();
        assertEquals("CRIADO", new EstoqueGenesisSyncService(api).sincronizar(dto(), "M2", "M1", true, null).operacao);
        assertEquals(1, api.posts); assertEquals(TipoEntidade.ESTOQUE, api.tipo);
    }
    @Test void hspTemPrioridade() throws Exception {
        Fake api = new Fake();
        assertEquals("IGNORADO", new EstoqueGenesisSyncService(api).sincronizar(dto(), "M1", "M2", true, null).operacao);
        assertEquals(0, api.gets);
    }
    @Test void adSomenteQuandoHspVazio() throws Exception {
        Fake api = new Fake(); EstoqueGenesisDto dto = dto();
        new EstoqueGenesisSyncService(api).sincronizar(dto, " ", "SV", true, null);
        assertTrue(dto.servico);
    }
    @Test void falhaGetNaoCria() {
        Fake api = new Fake(); api.get = new GenesisApiResult(429, GenesisApiResult.Classificacao.TRANSITORIO, null, "espera");
        assertThrows(GenesisSyncService.FalhaIntegracao.class,
                () -> new EstoqueGenesisSyncService(api).sincronizar(dto(), "M3", null, true, null));
        assertEquals(0, api.posts);
    }
    @Test void idLocalAusenteNaApiNaoRecria() {
        Fake api = new Fake();
        assertThrows(GenesisSyncService.FalhaIntegracao.class,
                () -> new EstoqueGenesisSyncService(api).sincronizar(dto(), "M2", null, true, 9L));
        assertEquals(0, api.posts);
    }
    @Test void inativoNuncaCria() throws Exception {
        Fake api = new Fake();
        assertEquals("IGNORADO", new EstoqueGenesisSyncService(api).sincronizar(null, null, null, false, null).operacao);
        assertEquals(0, api.gets);
    }
    @Test void inativaSomenteIdentidadeConfirmada() throws Exception {
        Fake api = new Fake(); api.get = encontrado();
        assertEquals("INATIVADO", new EstoqueGenesisSyncService(api).sincronizar(dto(), "M2", null, false, 9L).operacao);
        assertEquals(1, api.deletes);
    }
    @Test void precoAusenteBloqueiaAntesDaApi() {
        Fake api = new Fake(); EstoqueGenesisDto dto = dto(); dto.vlrVenda = null;
        assertThrows(IllegalArgumentException.class,
                () -> new EstoqueGenesisSyncService(api).sincronizar(dto, "M2", null, true, null));
        assertEquals(0, api.gets);
    }
    @Test void saldoZeroEEnviadoSemInventarUmaUnidade() throws Exception {
        Fake api=new Fake();EstoqueGenesisDto d=dto();d.qtd=BigDecimal.ZERO;
        new EstoqueGenesisSyncService(api).sincronizar(d,"M2",null,true,null);
        assertEquals(BigDecimal.ZERO,d.qtd);assertEquals(1,api.posts);
    }
    @Test void saldoNegativoOuNaoCalculadoBloqueiaAntesDaApi() {
        for(BigDecimal saldo:new BigDecimal[]{null,new BigDecimal("-1")}){
            Fake api=new Fake();EstoqueGenesisDto d=dto();d.qtd=saldo;
            assertThrows(IllegalArgumentException.class,()->new EstoqueGenesisSyncService(api).sincronizar(d,"M2",null,true,null));
            assertEquals(0,api.gets);assertEquals(0,api.posts);
        }
    }
    @Test void reservaObrigatoriaNoFluxoAgendadoPrecedePost() throws Exception {
        final boolean[] reservado={false};
        Fake api=new Fake(){@Override public GenesisApiResult criar(TipoEntidade t,Object d){assertTrue(reservado[0]);return super.criar(t,d);}};
        new EstoqueGenesisSyncService(api,()->{assertEquals(1,api.gets);reservado[0]=true;}).sincronizar(dto(),"M2",null,true,null);
        assertEquals(1,api.posts);
    }
    @Test void reservaIncertaBloqueiaNovoPost() {
        Fake api=new Fake();
        assertThrows(IllegalStateException.class,()->new EstoqueGenesisSyncService(api,()->{
            throw new IllegalStateException("ENVIO_INCERTO");
        }).sincronizar(dto(),"M2",null,true,null));
        assertEquals(1,api.gets);assertEquals(0,api.posts);
    }
    @Test void consultaPreservaZerosDoCodigoExterno() throws Exception {
        Fake api=new Fake();EstoqueGenesisDto d=dto();d.codigo="000076";d.codigoExterno="000076";
        new EstoqueGenesisSyncService(api).sincronizar(d,"M2",null,true,null);
        assertEquals("000076",api.codigoConsultado);
    }
    static EstoqueGenesisDto dto() {
        EstoqueGenesisDto d = new EstoqueGenesisDto(); d.codigo="P1"; d.codigoExterno="P1";
        d.descServico="Peca"; d.descTecnica="Peca"; d.idCategoria=1L; d.idFornecedor=2L; d.idFabricante=3L;
        d.vlrCusto=null; d.vlrVenda=BigDecimal.TEN; d.ipi=BigDecimal.ZERO; d.qtd=BigDecimal.ONE; d.status="Ativo"; return d;
    }
    static GenesisApiResult encontrado() { return new GenesisApiResult(200, GenesisApiResult.Classificacao.SUCESSO, "9", "{\"id\":9,\"codigoExterno\":\"P1\"}"); }
    static class Fake implements GenesisApiClient {
        int gets,posts,deletes,puts; TipoEntidade tipo;String codigoConsultado;
        GenesisApiResult get = new GenesisApiResult(404, GenesisApiResult.Classificacao.NAO_ENCONTRADO, null, "");
        public GenesisApiResult buscarPorCodigoExterno(TipoEntidade t,String s) { gets++; tipo=t; codigoConsultado=s; return get; }
        public GenesisApiResult buscarPorId(TipoEntidade t,String s) { gets++; return get; }
        public GenesisApiResult buscarPorDocumento(TipoEntidade t,String s) { throw new AssertionError(); }
        public GenesisApiResult criar(TipoEntidade t,Object p) { posts++; tipo=t; return new GenesisApiResult(201, GenesisApiResult.Classificacao.SUCESSO,"9",""); }
        public GenesisApiResult atualizar(TipoEntidade t,String id,Object p) { puts++; return encontrado(); }
        public GenesisApiResult inativarEstoque(String s) { deletes++; return new GenesisApiResult(200, GenesisApiResult.Classificacao.SUCESSO,"9",""); }
    }
}
