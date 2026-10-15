package br.zaya.telecontrol.listabasica.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.zaya.telecontrol.listabasica.application.BuscaListaBasicaService;
import br.zaya.telecontrol.shared.utils.ActionMessageUtil;
import br.zaya.telecontrol.shared.utils.LogUtil;
import java.math.BigDecimal;

public class DeleteListaBasicaProdutoBt implements AcaoRotinaJava {
    public void doAction(ContextoAcao ctx) throws Exception {
        try {
            int processados = 0;
            Registro[] linhas = ctx.getLinhas();
            if (linhas != null && linhas.length > 0) {
                for (Registro linha : linhas) {
                    BuscaListaBasicaService.deletarNaApiPorProduto((BigDecimal) linha.getCampo("CODPROD"));
                    processados++;
                }
            } else {
                Object referencia = ctx.getParam("REFERENCIA");
                if (referencia == null) {
                    throw new Exception("Selecione um produto da AD_TCLPRO ou informe o parametro REFERENCIA.");
                }
                BuscaListaBasicaService.deletarNaApiPorReferencia(String.valueOf(referencia));
                processados++;
            }

            ctx.setMensagemRetorno(ActionMessageUtil.sucesso("Delete enviado para lista basica de "
                    + processados + " produto(s)."));
        } catch (Exception e) {
            LogUtil.saveLog(e);
            ctx.setMensagemRetorno(ActionMessageUtil.erroAmigavel("Erro ao deletar lista basica", e));
        }
    }
}
