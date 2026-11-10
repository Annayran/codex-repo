package br.com.zaya.mahospitalar.genesis.service;

import br.com.zaya.mahospitalar.genesis.client.*;
import br.com.zaya.mahospitalar.genesis.domain.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class ReativacaoItemGenesisServiceTest {
    @Test void getPutGetNoMesmoItemPreservaCamposMesmoComPutVazio() throws Exception {
        Fixture f=new Fixture();
        GenesisSyncService.Resultado r=f.service().reativar(payload(),66618);
        assertEquals(GenesisSyncService.Operacao.ATUALIZADO,r.operacao);
        assertEquals(Arrays.asList("GET:66618","PUT:66618","GET:66618"),f.calls);
        assertEquals("Legado-001",f.enviado.path("codigoExterno").asText());
        assertEquals("nao substituir",f.enviado.path("observacao").asText());
        assertTrue(f.enviado.has("campoCustomizado"));assertTrue(f.enviado.get("campoCustomizado").isNull());
        assertEquals("Ativo",f.enviado.path("status").asText());assertEquals(1,f.enviado.path("idSituacaoEquipamento").asInt());
    }
    @Test void jaAtivoComSituacaoCorretaNaoRepetePut() throws Exception {
        Fixture f=new Fixture();f.remoto.put("status","Ativo");f.remoto.put("situacao","Operacional");f.remoto.put("idSituacaoEquipamento",1);
        assertEquals(GenesisSyncService.Operacao.SEM_ALTERACAO,f.service().reativar(payload(),66618).operacao);
        assertEquals(Collections.singletonList("GET:66618"),f.calls);
    }
    @Test void clienteSetorModeloOuSerieDivergenteBloqueiaAntesDoPut() throws Exception {
        for(String campo:new String[]{"idCliente","idSetor","idModelo","nserie"}) {
            Fixture f=new Fixture();f.remoto.put(campo,"999");
            IllegalStateException erro=assertThrows(IllegalStateException.class,()->f.service().reativar(payload(),66618));
            assertTrue(erro.getMessage().contains("Envio bloqueado para conciliacao"));
            assertFalse(erro.getMessage().contains(campo));
            assertEquals(Collections.singletonList("GET:66618"),f.calls);
        }
    }
    @Test void idDivergenteNoCorpoNaoAutorizaPut() throws Exception {
        Fixture f=new Fixture();f.remoto.put("id",7);
        assertThrows(IllegalStateException.class,()->f.service().reativar(payload(),66618));
        assertFalse(f.calls.toString().contains("PUT"));
    }
    @Test void semSituacaoConciliadaNaoFazHttp() throws Exception {
        Fixture f=new Fixture();PayloadGenesis p=payload();((ObjectNode)p.getDto()).remove("idSituacaoEquipamento");
        assertThrows(IllegalStateException.class,()->f.service().reativar(p,66618));assertTrue(f.calls.isEmpty());
    }
    @Test void putSemEfeitoNaoConfirma() throws Exception {
        Fixture f=new Fixture();f.aplicar=false;
        assertThrows(IllegalStateException.class,()->f.service().reativar(payload(),66618));
        assertEquals(3,f.calls.size());
    }
    @Test void alteracaoDeOutroCampoDepoisDoPutExigeRevisao() throws Exception {
        Fixture f=new Fixture();f.alterarCampo=true;
        assertThrows(IllegalStateException.class,()->f.service().reativar(payload(),66618));
    }
    @Test void falhaNoPutNaoGeraPostNemGetDeConfirmacao() throws Exception {
        Fixture f=new Fixture();f.httpPut=500;
        assertThrows(GenesisSyncService.FalhaIntegracao.class,()->f.service().reativar(payload(),66618));
        assertEquals(Arrays.asList("GET:66618","PUT:66618"),f.calls);
    }
    @Test void timeoutDepoisDoPutRetomaComGetSemNovaMutacao() throws Exception {
        Fixture f=new Fixture();f.falharSegundoGet=true;
        assertThrows(GenesisSyncService.FalhaIntegracao.class,()->f.service().reativar(payload(),66618));
        f.falharSegundoGet=false;
        assertEquals(GenesisSyncService.Operacao.SEM_ALTERACAO,f.service().reativar(payload(),66618).operacao);
        assertEquals(1,Collections.frequency(f.calls,"PUT:66618"));
    }
    @Test void recursoEstoqueNaoEhAceitoParaReativarEquipamento() throws Exception {
        Fixture f=new Fixture();PayloadGenesis p=new PayloadGenesis(TipoEntidade.ESTOQUE,"x","x",new HashMap<>(),"{}","x");
        assertThrows(IllegalStateException.class,()->f.service().reativar(p,66618));assertTrue(f.calls.isEmpty());
    }
    private static PayloadGenesis payload() throws Exception {
        ObjectNode d=new ObjectMapper().createObjectNode();d.put("idCliente",10);d.put("idSetor",11);d.put("idModelo",20);
        d.put("idSituacaoEquipamento",1);d.put("situacao","Operacional");d.put("nserie","SERIE");d.put("status","Ativo");
        return new PayloadGenesis(TipoEntidade.ITEM_MATERIAL,"30|SERIE","SNK-ITEM",d,d.toString(),"hash");
    }
    private static final class Fixture implements GenesisApiClient {
        ObjectNode remoto=new ObjectMapper().createObjectNode(),enviado;
        List<String> calls=new ArrayList<>();int httpPut=200,gets;boolean aplicar=true,alterarCampo,falharSegundoGet;
        Fixture(){remoto.put("id",66618);remoto.put("nserie","SERIE");remoto.put("idCliente",10);remoto.put("idSetor",11);
            remoto.put("idModelo",20);remoto.put("idSituacaoEquipamento",4);remoto.put("situacao","Desativado");
            remoto.put("status","Inativo");remoto.put("codigoExterno","Legado-001");remoto.put("observacao","nao substituir");
            remoto.putNull("campoCustomizado");}
        ReativacaoItemGenesisService service(){return new ReativacaoItemGenesisService(this);}
        public GenesisApiResult buscarPorId(TipoEntidade t,String id){assertEquals(TipoEntidade.ITEM_MATERIAL,t);calls.add("GET:"+id);gets++;
            if(falharSegundoGet&&gets==2)return new GenesisApiResult(408,GenesisApiResult.Classificacao.TRANSITORIO,null,"{}");
            return new GenesisApiResult(200,GenesisApiResult.Classificacao.SUCESSO,"66618",remoto.toString());}
        public GenesisApiResult atualizar(TipoEntidade t,String id,Object obj){assertEquals(TipoEntidade.ITEM_MATERIAL,t);calls.add("PUT:"+id);
            enviado=((ObjectNode)obj).deepCopy();if(httpPut==200&&aplicar){remoto=enviado.deepCopy();if(alterarCampo)remoto.put("observacao","alterado");}
            return new GenesisApiResult(httpPut,httpPut==200?GenesisApiResult.Classificacao.SUCESSO:GenesisApiResult.Classificacao.TRANSITORIO,null,"{}");}
        public GenesisApiResult buscarPorCodigoExterno(TipoEntidade t,String v){throw new AssertionError("Busca externa inesperada");}
        public GenesisApiResult buscarPorDocumento(TipoEntidade t,String v){throw new AssertionError("Documento inesperado");}
        public GenesisApiResult criar(TipoEntidade t,Object d){throw new AssertionError("POST proibido na reativacao");}
    }
}
