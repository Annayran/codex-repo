package br.com.zaya.mahospitalar.genesis.action;

import br.com.sankhya.extensions.actionbutton.*;
import br.com.zaya.mahospitalar.genesis.client.GenesisCooldown;
import java.math.BigDecimal;
import java.sql.Timestamp;

public final class AcaoReprocessarEstoqueGenesis implements AcaoRotinaJava {
    public void doAction(ContextoAcao contexto)throws Exception {
        int liberados=0;Timestamp agora=new Timestamp(System.currentTimeMillis());
        Registro[] linhas=contexto.getLinhas();
        if(linhas==null||linhas.length==0){contexto.setMensagemRetorno("Selecione registros do controle de catalogo.");return;}
        for(Registro r:linhas){
            String status=String.valueOf(r.getCampo("STATUS"));
            if(!"ERRO".equals(status)&&!"REVISAO".equals(status)&&!"REPROCESSAR".equals(status))continue;
            Object http=r.getCampo("HTTP_STATUS"),data=r.getCampo("DH_ATUALIZACAO"),msg=r.getCampo("MENSAGEM");
            if(http instanceof Number&&((Number)http).intValue()==429 &&
                    (!(data instanceof java.util.Date)||agora.getTime()-((java.util.Date)data).getTime()<
                            GenesisCooldown.prazoPersistidoMs(msg==null?null:msg.toString())))continue;
            r.setCampo("STATUS","REPROCESSAR");r.setCampo("TENTATIVAS",BigDecimal.ZERO);
            r.setCampo("HTTP_STATUS",null);r.setCampo("MENSAGEM",null);r.setCampo("PROXIMATENTATIVA",null);
            r.setCampo("DH_ATUALIZACAO",agora);r.save();liberados++;
        }
        contexto.setMensagemRetorno(liberados+" registro(s) liberado(s). Corrigir cadastro antes de reprocessar REVISAO.");
    }
}
