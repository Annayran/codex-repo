package br.zaya.telecontrol.produto.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.zaya.telecontrol.produto.application.AtualizaProdutoService;
import br.zaya.telecontrol.services.BuscaCadastrosTelecontrolService;
import br.zaya.telecontrol.shared.utils.LogUtil;

public class AtualizaProdutoBt implements AcaoRotinaJava {
    public void doAction(ContextoAcao ctx) throws Exception {
        try {
            BuscaCadastrosTelecontrolService.Resultado resultado = AtualizaProdutoService.atualizarProdutos();
            ctx.setMensagemRetorno("Atualizacao de produtos finalizada: " + resultado);
        } catch (Exception e) {
            LogUtil.saveLog(e);
            ctx.setMensagemRetorno(e.toString() + " : " + e.getLocalizedMessage());
            throw e;
        }
    }
}
