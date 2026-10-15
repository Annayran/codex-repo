package br.zaya.telecontrol.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.zaya.telecontrol.peca.application.AtualizaPecaService;
import br.zaya.telecontrol.services.BuscaCadastrosTelecontrolService;
import br.zaya.telecontrol.shared.utils.LogUtil;

public class AtualizaPecaBt implements AcaoRotinaJava {
    public void doAction(ContextoAcao ctx) throws Exception {
        try {
            BuscaCadastrosTelecontrolService.Resultado resultado = AtualizaPecaService.atualizarPecas();
            ctx.setMensagemRetorno("Atualizacao de pecas finalizada: " + resultado);
        } catch (Exception e) {
            LogUtil.saveLog(e);
            ctx.setMensagemRetorno(e.toString() + " : " + e.getLocalizedMessage());
            throw e;
        }
    }
}
