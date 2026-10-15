package br.zaya.telecontrol.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.zaya.telecontrol.produto.application.AtualizaProdutoService;
import br.zaya.telecontrol.services.BuscaCadastrosTelecontrolService;
import br.zaya.telecontrol.shared.utils.LogUtil;

/**
 * Bridge de compatibilidade para cadastros antigos do Sankhya.
 *
 * A atualizacao de produtos consulta a Telecontrol e grava o codigo retornado
 * em AD_TCLPRO.COD_TELECONTROL usando o campo "produto" do JSON.
 */
@Deprecated
public class AtualizaProdutoBt implements AcaoRotinaJava {

    @Override
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
