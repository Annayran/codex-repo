package br.zaya.telecontrol.listabasica.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.core.JapeSession;
import br.zaya.telecontrol.listabasica.application.SincronizaListaBasicaService;
import br.zaya.telecontrol.shared.utils.LogUtil;

import java.math.BigDecimal;

public class SincronizaListaBasicaBt implements AcaoRotinaJava {

    @Override
    public void doAction(ContextoAcao ctx) throws Exception {
        JapeSession.SessionHandle hnd = null;
        try {
            br.zaya.telecontrol.services.SincronizaListaBasicaService.Resultado resultado;
            JapeSession currentSession = JapeSession.getCurrentSession();

            if (currentSession != null && currentSession.hasTransaction()) {
                resultado = executarSincronizacao(ctx);
            } else {
                hnd = JapeSession.open();
                hnd.setPriorityLevel(JapeSession.LOW_PRIORITY);
                final br.zaya.telecontrol.services.SincronizaListaBasicaService.Resultado[] resultadoTx =
                        new br.zaya.telecontrol.services.SincronizaListaBasicaService.Resultado[]{new br.zaya.telecontrol.services.SincronizaListaBasicaService.Resultado()};

                hnd.execWithTX(new JapeSession.TXBlock() {
                    public void doWithTx() throws Exception {
                        resultadoTx[0] = executarSincronizacao(ctx);
                    }
                });
                resultado = resultadoTx[0];
            }

            ctx.setMensagemRetorno("Sincronizacao de Lista Basica finalizada: " + resultado);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                LogUtil.saveLog(e);
            } catch (Exception logError) {
                logError.printStackTrace();
            }
            ctx.setMensagemRetorno(e.toString() + " : " + e.getLocalizedMessage());
            throw e;
        } finally {
            if (hnd != null) {
                JapeSession.close(hnd);
            }
        }
    }

    private br.zaya.telecontrol.services.SincronizaListaBasicaService.Resultado executarSincronizacao(ContextoAcao ctx) throws Exception {
        br.zaya.telecontrol.services.SincronizaListaBasicaService.Resultado resultado =
                new br.zaya.telecontrol.services.SincronizaListaBasicaService.Resultado();
        Registro[] linhas = ctx.getLinhas();

        if (linhas != null && linhas.length > 0) {
            for (Registro linha : linhas) {
                String where = montarWhereLinha(linha);

                System.out.println("[LISTA-BASICA][BOTAO] Executando filtro: " + where);
                resultado.somar(SincronizaListaBasicaService.sincronizarMateriasPrimas(where));
            }
        } else {
            System.out.println("[LISTA-BASICA][BOTAO] Nenhuma linha selecionada/contexto informado. Executando fluxo geral do scheduler.");
            resultado = SincronizaListaBasicaService.sincronizarMateriasPrimas();
        }

        return resultado;
    }

    private String montarWhereLinha(Registro linha) throws Exception {
        BigDecimal codProdPa = obterBigDecimal(linha, "CODPRODPA");
        BigDecimal codProdMp = obterBigDecimal(linha, "CODPRODMP");
        BigDecimal seqMp = obterBigDecimal(linha, "SEQMP");

        if (codProdPa != null && codProdMp != null && seqMp != null) {
            return montarWhereSelecionado(codProdPa, codProdMp, seqMp);
        }

        BigDecimal codProd = obterBigDecimal(linha, "CODPROD");
        if (codProd != null) {
            return "this.AD_ENVLISTTLC = 'S' AND this.CODPRODPA = " + codProd;
        }

        return "this.AD_ENVLISTTLC = 'S'";
    }

    private BigDecimal obterBigDecimal(Registro linha, String campo) {
        try {
            Object valor = linha.getCampo(campo);
            return valor instanceof BigDecimal ? (BigDecimal) valor : null;
        } catch (Exception e) {
            return null;
        }
    }

    private String montarWhereSelecionado(BigDecimal codProdPa, BigDecimal codProdMp, BigDecimal seqMp) throws Exception {
        if (codProdPa == null || codProdMp == null || seqMp == null) {
            throw new Exception("Para processar selecionados, CODPRODPA, CODPRODMP e SEQMP devem estar preenchidos.");
        }

        return "this.AD_ENVLISTTLC = 'S'"
                + " AND this.CODPRODPA = " + codProdPa
                + " AND this.CODPRODMP = " + codProdMp
                + " AND this.SEQMP = " + seqMp;
    }
}
