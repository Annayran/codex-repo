package br.zaya.telecontrol.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.zaya.telecontrol.services.BuscaOrdemServico;
import br.zaya.telecontrol.utils.LogUtil;
import java.math.BigDecimal;

public class SyncOrdemServicoBt implements AcaoRotinaJava {

    @Override
    public void doAction(ContextoAcao ctx) throws Exception {
        try {
            Registro[] linhas = ctx.getLinhas();
            for (Registro linha : linhas) {
                BigDecimal nuNota = (BigDecimal) linha.getCampo("NUNOTA");
                if (nuNota != null) {
                    BuscaOrdemServico.syncOrdemServicoByNota(nuNota);
                }
            }
            ctx.setMensagemRetorno("Sincronização de OS finalizada com sucesso!");
        } catch (Exception e) {
            LogUtil.saveLog(e);
            ctx.setMensagemRetorno("Erro na sincronização de OS: " + e.getMessage());
        }
    }
}
