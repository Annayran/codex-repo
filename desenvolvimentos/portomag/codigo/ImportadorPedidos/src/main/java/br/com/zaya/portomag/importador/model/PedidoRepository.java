package br.com.zaya.portomag.importador.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.fluid.FluidCreateVO;

/** Usa a transacao automatica do botao. Nao realiza commit, nao captura falhas de persistencia. */
public final class PedidoRepository {
    public BigDecimal inserir(PedidoRecebido pedido,BigDecimal usuario) throws Exception {
        if(usuario==null)throw new IllegalArgumentException("Usuario autenticado obrigatorio.");
        String key=pedido.cabecalho.get("CNPJFORN")+"|"+pedido.cabecalho.get("CNPJCLI")+"|"+pedido.cabecalho.get("NUMPED");
        if(JapeFactory.dao("ZPM_PEDREC").findOne("CHAVEIMP = ?",key)!=null)throw new IllegalArgumentException("Pedido ja recebido. Consulte o registro existente; revisoes exigem analise.");
        FluidCreateVO master=JapeFactory.dao("ZPM_PEDREC").create();
        for(Map.Entry<String,Object> field:pedido.cabecalho.entrySet())master.set(field.getKey(),field.getValue());
        master.set("CHAVEIMP",key).set("SITUACAO",pedido.alertas.isEmpty()?"RECEBIDO":"DIVERGENTE").set("CODUSU",usuario).set("DHIMPORT",new Timestamp(System.currentTimeMillis()));
        DynamicVO saved=master.save(); BigDecimal id=saved.asBigDecimal("ID");
        for(Map<String,Object> item:pedido.itens){FluidCreateVO detail=JapeFactory.dao("ZPM_PEDITE").create().set("IDPED",id);for(Map.Entry<String,Object> field:item.entrySet())detail.set(field.getKey(),field.getValue());detail.save();}
        return id;
    }
}
