package br.zaya.telecontrol.serie.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.zaya.telecontrol.serie.application.EnviaSerieService;
import br.zaya.telecontrol.shared.utils.LogUtil;

public class PostSerieBt implements AcaoRotinaJava {
    public void doAction(ContextoAcao ctx) throws Exception {
        try {
            EnviaSerieService.postSerie();
            ctx.setMensagemRetorno("Dados Enviados: Verificar Logs");
        } catch (Exception e) {
            LogUtil.saveLog(e);
            ctx.setMensagemRetorno(br.zaya.telecontrol.shared.utils.ActionMessageUtil.erroAmigavel("Erro no envio de serie", e));
        }
    }
}
