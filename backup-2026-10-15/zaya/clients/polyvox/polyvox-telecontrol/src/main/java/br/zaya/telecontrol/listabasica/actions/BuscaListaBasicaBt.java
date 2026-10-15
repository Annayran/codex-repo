package br.zaya.telecontrol.listabasica.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.zaya.telecontrol.listabasica.application.BuscaListaBasicaService;
import br.zaya.telecontrol.shared.utils.ActionMessageUtil;
import br.zaya.telecontrol.shared.utils.LogUtil;
import java.math.BigDecimal;

public class BuscaListaBasicaBt implements AcaoRotinaJava {
    public void doAction(ContextoAcao ctx) throws Exception {
        try {
            br.zaya.telecontrol.services.BuscaListaBasicaService.Resultado total =
                    new br.zaya.telecontrol.services.BuscaListaBasicaService.Resultado();

            Registro[] linhas = ctx.getLinhas();
            if (linhas != null && linhas.length > 0) {
                for (Registro linha : linhas) {
                    somar(total, BuscaListaBasicaService.buscarPorProduto((BigDecimal) linha.getCampo("CODPROD")));
                }
            } else {
                Object referencia = ctx.getParam("REFERENCIA");
                if (referencia == null) {
                    throw new Exception("Selecione um produto da AD_TCLPRO ou informe o parametro REFERENCIA.");
                }
                somar(total, BuscaListaBasicaService.buscarPorReferencia(String.valueOf(referencia)));
            }

            ctx.setMensagemRetorno(ActionMessageUtil.sucesso("Lista basica sincronizada da Telecontrol. " + total));
        } catch (Exception e) {
            LogUtil.saveLog(e);
            ctx.setMensagemRetorno(ActionMessageUtil.erroAmigavel("Erro ao buscar lista basica", e));
        }
    }

    private void somar(br.zaya.telecontrol.services.BuscaListaBasicaService.Resultado total,
                      br.zaya.telecontrol.services.BuscaListaBasicaService.Resultado parcial) {
        if (parcial == null) {
            return;
        }
        total.inseridas += parcial.inseridas;
        total.atualizadas += parcial.atualizadas;
        total.ignoradas += parcial.ignoradas;
        total.pecaNaoEncontrada += parcial.pecaNaoEncontrada;
    }
}
