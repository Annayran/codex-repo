package br.zaya.telecontrol.preco.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.zaya.telecontrol.preco.application.EnvioPrecoService;
import br.zaya.telecontrol.shared.utils.LogUtil;
import java.math.BigDecimal;

public class EnvioPrecosBt implements AcaoRotinaJava {
    public void doAction(ContextoAcao ctx) throws Exception {
        try {
            Registro[] linhas = ctx.getLinhas();
            if (linhas != null) {
                for (Registro item : linhas) {
                    BigDecimal codprod = (BigDecimal) item.getCampo("CODPROD");
                    EnvioPrecoService.postPrecoProduto(codprod);
                }
            }
            ctx.setMensagemRetorno("Processo Finalizado, examinar os logs");
        } catch (Exception e) {
            LogUtil.saveLog(e);
            ctx.setMensagemRetorno(br.zaya.telecontrol.shared.utils.ActionMessageUtil.erroAmigavel("Erro na rotina Telecontrol", e));
        }
    }
}
