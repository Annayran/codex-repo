package br.com.zaya.portomag.importador.sankhya;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.zaya.portomag.importador.controller.ReceberPedidoController;

/** Cadastrar como Rotina Java, controle de acesso ligado e transacao automatica. */
public final class ReceberPedidoAction implements AcaoRotinaJava {
    @Override public void doAction(ContextoAcao contexto) throws Exception {
        Object xml=contexto.getParam("XMLPEDIDO");
        if(!(xml instanceof String))throw new IllegalArgumentException("Parametro XMLPEDIDO obrigatorio.");
        contexto.setMensagemRetorno(new ReceberPedidoController().receber((String)xml,contexto.getUsuarioLogado()));
    }
}
