package br.zaya.telecontrol.ordemservico.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.zaya.telecontrol.ordemservico.application.BuscaOrdemServicoService;
import br.zaya.telecontrol.shared.utils.LogUtil;

import java.math.BigDecimal;

public class SyncOrdemServicoBt implements AcaoRotinaJava {

    @Override
    public void doAction(ContextoAcao ctx) throws Exception {
        try {
            Registro[] linhas = ctx.getLinhas();
            for (Registro linha : linhas) {
                BigDecimal nuNota = (BigDecimal) linha.getCampo("NUNOTA");
                if (nuNota != null) {
                    BuscaOrdemServicoService.syncOrdemServicoByNota(nuNota);
                }
            }
            ctx.setMensagemRetorno("Sincronizacao de OS finalizada com sucesso!");
        } catch (Exception e) {
            LogUtil.saveLog(e);
            ctx.setMensagemRetorno("Erro na sincronizacao de OS: " + e.getMessage());
        }
    }
}

