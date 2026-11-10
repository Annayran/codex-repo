package br.com.zaya.mahospitalar.genesis.service;

import br.com.zaya.mahospitalar.genesis.client.GenesisApiClient;
import br.com.zaya.mahospitalar.genesis.client.GenesisApiResult;
import br.com.zaya.mahospitalar.genesis.domain.PayloadGenesis;
import br.com.zaya.mahospitalar.genesis.domain.TipoEntidade;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;

/** Updates only the state of an identified equipment; never transfers ownership or creates a duplicate. */
public final class ReativacaoItemGenesisService {
    private final GenesisApiClient client;
    public ReativacaoItemGenesisService(GenesisApiClient client){this.client=client;}

    public GenesisSyncService.Resultado reativar(PayloadGenesis p,long id) throws Exception {
        if(p.getTipo()!=TipoEntidade.ITEM_MATERIAL || id<=0)
            throw new IllegalStateException("REATIVACAO_NAO_AUTORIZADA: recurso ou ID invalido.");
        JsonNode esperado=new ObjectMapper().valueToTree(p.getDto());
        for(String field:new String[]{"idCliente","idSetor","idModelo","idSituacaoEquipamento"})
            if(!esperado.path(field).asText("").matches("[1-9][0-9]*"))
                throw new IllegalStateException("REATIVACAO_NAO_AUTORIZADA: vinculo ou situacao nao conciliado: "+field);
        if(esperado.path("situacao").asText("").trim().isEmpty() || esperado.path("nserie").asText("").trim().isEmpty())
            throw new IllegalStateException("REATIVACAO_NAO_AUTORIZADA: serie ou situacao ausente.");
        GenesisApiResult antes=client.buscarPorId(TipoEntidade.ITEM_MATERIAL,String.valueOf(id));
        validarIdentidade(antes,id,esperado);
        ObjectNode payload=(ObjectNode)antes.registroParaPersistencia();
        String estado=payload.path("status").asText("").trim();
        if("Ativo".equalsIgnoreCase(estado)) {
            validarEstado(antes,esperado);
            return resultado(p,antes,GenesisSyncService.Operacao.SEM_ALTERACAO);
        }
        if(!"Inativo".equalsIgnoreCase(estado))
            throw new IllegalStateException("REATIVACAO_NAO_AUTORIZADA: estado remoto nao reconhecido.");
        payload.put("status","Ativo");
        payload.set("idSituacaoEquipamento",esperado.get("idSituacaoEquipamento"));
        payload.put("situacao",esperado.get("situacao").asText());
        br.com.zaya.mahospitalar.genesis.repository.GenesisRequestRepository.identidade(antes);
        GenesisApiResult put=client.atualizar(TipoEntidade.ITEM_MATERIAL,String.valueOf(id),payload);
        if(!put.isSucesso() || put.getHttpStatus()!=200)
            throw new GenesisSyncService.FalhaIntegracao(TipoEntidade.ITEM_MATERIAL,"PUT_REATIVACAO",put);
        if(put.getIdGenesis()!=null && !String.valueOf(id).equals(put.getIdGenesis()))
            throw new IllegalStateException("REATIVACAO_NAO_CONFIRMADA: PUT retornou outro ID.");
        GenesisApiResult depois=client.buscarPorId(TipoEntidade.ITEM_MATERIAL,String.valueOf(id));
        validarIdentidade(depois,id,esperado);
        validarEstado(depois,esperado);
        validarPreservacao(payload,depois.registroParaPersistencia());
        br.com.zaya.mahospitalar.genesis.repository.GenesisRequestRepository.identidade(depois);
        return resultado(p,depois,GenesisSyncService.Operacao.ATUALIZADO);
    }
    private static void validarIdentidade(GenesisApiResult r,long id,JsonNode esperado) throws Exception {
        if(!r.isSucesso())throw new GenesisSyncService.FalhaIntegracao(TipoEntidade.ITEM_MATERIAL,"GET_REATIVACAO",r);
        if(!String.valueOf(id).equals(r.getIdGenesis()) || !r.campoCorresponde("id",String.valueOf(id),false)
                || r.registroParaPersistencia()==null)
            throw new IllegalStateException("REATIVACAO_NAO_AUTORIZADA: ID remoto divergente ou ambiguo.");
        for(String field:new String[]{"nserie","idModelo","idCliente","idSetor"})
            if(!r.campoCorresponde(field,esperado.path(field).asText(),false))
                throw new IllegalStateException("REATIVACAO_VINCULO_DIVERGENTE: o equipamento na Genesis possui "+
                        ("idCliente".equals(field)?"cliente":"idSetor".equals(field)?"setor":"idModelo".equals(field)?"modelo":"numero de serie")+
                        " diferente do movimento de origem. Envio bloqueado para conciliacao; nenhum vinculo foi transferido e nenhum novo equipamento foi criado.");
    }
    private static void validarEstado(GenesisApiResult r,JsonNode esperado) {
        if(!r.campoCorresponde("status","Ativo",false)
                || !r.campoCorresponde("idSituacaoEquipamento",esperado.get("idSituacaoEquipamento").asText(),false)
                || !r.campoCorresponde("situacao",esperado.get("situacao").asText(),false))
            throw new IllegalStateException("REATIVACAO_NAO_CONFIRMADA: GET nao confirma estado e situacao esperados; nao repetir POST.");
    }
    private static void validarPreservacao(JsonNode esperado,JsonNode recebido) {
        Iterator<Map.Entry<String,JsonNode>> campos=esperado.fields();
        while(campos.hasNext()) {
            Map.Entry<String,JsonNode> campo=campos.next();
            if("dtAlteracao".equals(campo.getKey()) || "idUsuarioAlteracao".equals(campo.getKey()))continue;
            JsonNode a=campo.getValue(),b=recebido.get(campo.getKey());
            boolean igual=b!=null && (a.isNumber() && b.isNumber()
                    ? new BigDecimal(a.asText()).compareTo(new BigDecimal(b.asText()))==0:a.equals(b));
            if(!igual)throw new IllegalStateException("REATIVACAO_NAO_CONFIRMADA: campo remoto alterado: "+campo.getKey());
        }
    }
    private static GenesisSyncService.Resultado resultado(PayloadGenesis p,GenesisApiResult r,GenesisSyncService.Operacao op){
        return new GenesisSyncService.Resultado(p,r,false,op);
    }
}
