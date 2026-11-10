package br.com.zaya.mahospitalar.genesis.service;

import br.com.zaya.mahospitalar.genesis.client.*;
import br.com.zaya.mahospitalar.genesis.domain.TipoEntidade;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;

/** Only renames an existing generated model identity; never creates a model. */
public final class MigracaoModeloGenesisService {
    public interface AntesPut { void validar() throws Exception; }
    public static final class Resultado {
        public final GenesisApiResult consulta,put,confirmacao;
        Resultado(GenesisApiResult consulta,GenesisApiResult put,GenesisApiResult confirmacao){this.consulta=consulta;this.put=put;this.confirmacao=confirmacao;}
    }
    private final GenesisApiClient client;
    public MigracaoModeloGenesisService(GenesisApiClient client){this.client=client;}
    public Resultado migrar(long id,String anterior,String destino,AntesPut antesPut) throws Exception {
        if(id<=0||anterior==null||!anterior.matches("SNK-MOD-[0-9]+")||destino==null||destino.trim().isEmpty()||destino.length()>200)
            throw new IllegalStateException("Identidade de modelo ausente/invalida; migracao bloqueada.");
        GenesisApiResult atual=client.buscarPorId(TipoEntidade.MODELO,String.valueOf(id));
        validarId(atual,id,"GET_ID");
        boolean aplicado=atual.campoCorresponde("codigoExterno",destino,false);
        if(!aplicado&&!atual.campoCorresponde("codigoExterno",anterior,false))
            throw new IllegalStateException("Codigo remoto nao corresponde ao legado esperado; nenhum PUT autorizado.");
        GenesisApiResult conflito=client.buscarPorCodigoExterno(TipoEntidade.MODELO,destino);
        if(conflito.isSucesso()) {
            validarId(conflito,id,"GET_CODIGO_DESTINO");
            if(!conflito.campoCorresponde("codigoExterno",destino,false))
                throw new IllegalStateException("Busca por codigo retornou identidade diferente do destino; nenhum PUT autorizado.");
            if(aplicado)return new Resultado(atual,null,atual);
            throw new IllegalStateException("Busca do destino contradiz GET por ID; revisar antes de PUT.");
        }
        if(conflito.getClassificacao()!=GenesisApiResult.Classificacao.NAO_ENCONTRADO)
            throw new GenesisSyncService.FalhaIntegracao(TipoEntidade.MODELO,"GET_CODIGO_DESTINO",conflito);
        if(aplicado)throw new IllegalStateException("GET por ID ja tem destino, mas busca por codigo nao confirma; revisar sem PUT.");
        ObjectNode payload=(ObjectNode)atual.registroParaPersistencia();
        payload.put("codigoExterno",destino);
        antesPut.validar();
        GenesisApiResult put=client.atualizar(TipoEntidade.MODELO,String.valueOf(id),payload);
        if(!put.isSucesso())throw new GenesisSyncService.FalhaIntegracao(TipoEntidade.MODELO,"PUT_CODIGO_MODELO",put);
        // A 2xx/empty PUT response is not local confirmation: read the existing ID again.
        GenesisApiResult confirmado=client.buscarPorId(TipoEntidade.MODELO,String.valueOf(id));
        validarId(confirmado,id,"GET_CONFIRMACAO");
        if(!confirmado.campoCorresponde("codigoExterno",destino,false))
            throw new IllegalStateException("PUT sem codigo destino confirmado; preservar controle para nova consulta.");
        validarCampos(payload,confirmado.registroParaPersistencia());
        return new Resultado(atual,put,confirmado);
    }
    private static void validarId(GenesisApiResult r,long id,String etapa) throws Exception {
        if(!r.isSucesso())throw new GenesisSyncService.FalhaIntegracao(TipoEntidade.MODELO,etapa,r);
        if(!String.valueOf(id).equals(r.getIdGenesis())||!r.campoCorresponde("id",String.valueOf(id),false)||r.registroParaPersistencia()==null)
            throw new IllegalStateException("ID remoto divergente/ambiguo na migracao; nenhuma criacao autorizada.");
    }
    private static void validarCampos(JsonNode esperado,JsonNode recebido) {
        Iterator<Map.Entry<String,JsonNode>> fields=esperado.fields();
        while(fields.hasNext()){
            Map.Entry<String,JsonNode> f=fields.next();
            if("dtAlteracao".equals(f.getKey())||"idUsuarioAlteracao".equals(f.getKey()))continue;
            JsonNode atual=recebido.get(f.getKey()),valor=f.getValue();
            boolean igual=atual!=null && (valor.isNumber()&&atual.isNumber()
                    ?new BigDecimal(valor.asText()).compareTo(new BigDecimal(atual.asText()))==0:valor.equals(atual));
            if(!igual)throw new IllegalStateException("Campo remoto alterado alem do codigo: "+f.getKey()+"; revisar migracao.");
        }
    }
}
