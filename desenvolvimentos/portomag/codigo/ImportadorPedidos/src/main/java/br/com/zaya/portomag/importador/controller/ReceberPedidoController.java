package br.com.zaya.portomag.importador.controller;

import java.math.BigDecimal;
import br.com.zaya.portomag.importador.model.*;

public final class ReceberPedidoController {
    public String receber(String xml,BigDecimal usuario) throws Exception {
        PedidoRecebido pedido=new LeituraXml().ler(xml);
        BigDecimal id=new PedidoRepository().inserir(pedido,usuario);
        return "Pedido recebido: "+id+". Itens: "+pedido.itens.size()+". Pendencias de valores: "+pedido.alertas.size()+". Documento comercial nao gerado.";
    }
}
