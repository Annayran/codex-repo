package br.zaya.telecontrol.osintegracao.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.jape.core.JapeSession;
import br.zaya.telecontrol.osintegracao.application.LegacyOsIntegracaoUseCase;
import br.zaya.telecontrol.osintegracao.application.OsIntegracaoUseCase;
import br.zaya.telecontrol.osintegracao.application.SincronizacaoResultado;
import br.zaya.telecontrol.shared.utils.DateUtil;
import br.zaya.telecontrol.shared.utils.LogUtil;

import java.util.Date;

/**
 * Action para sincronizacao manual de OS
 * Permite ao usuario informar periodo desejado
 * Chamada via tela de Ordem de Servico no Sankhya
 */
public class SincronizarOsAction implements AcaoRotinaJava {
    private final OsIntegracaoUseCase osUseCase;

    public SincronizarOsAction() {
        this(new LegacyOsIntegracaoUseCase());
    }

    public SincronizarOsAction(OsIntegracaoUseCase osUseCase) {
        this.osUseCase = osUseCase;
    }

    @Override
    public void doAction(ContextoAcao ctx) {
        JapeSession.SessionHandle hnd = null;

        try {
            hnd = JapeSession.open();
            hnd.setPriorityLevel(JapeSession.LOW_PRIORITY);
            hnd.execWithTX(() -> executarSincronizacao(ctx));
        } catch (Exception e) {
            ctx.setMensagemRetorno(br.zaya.telecontrol.shared.utils.ActionMessageUtil.erroAmigavel("Erro ao sincronizar OS", e));
            try {
                LogUtil.saveLog(e);
            } catch (Exception logError) {
                ctx.setMensagemRetorno(br.zaya.telecontrol.shared.utils.ActionMessageUtil.erroAmigavel("Erro ao salvar log Telecontrol", logError));
            }
        } finally {
            JapeSession.close(hnd);
        }
    }

    private void executarSincronizacao(ContextoAcao ctx) throws Exception {
        try {
            Object dataInicioParam = ctx.getParam("P_DATA_INICIO");
            Object dataFimParam = ctx.getParam("P_DATA_FIM");

            if (dataInicioParam == null || dataFimParam == null) {
                ctx.setMensagemRetorno("Data de Inicio e Data de Fim sao obrigatorias");
                return;
            }

            Date dataInicio = (Date) dataInicioParam;
            Date dataFim = (Date) dataFimParam;

            ctx.setMensagemRetorno(String.format(
                    "Iniciando sincronizacao manual de OS de %s a %s",
                    DateUtil.formatBrDate(dataInicio),
                    DateUtil.formatBrDate(dataFim)
            ));

            SincronizacaoResultado resultado = osUseCase.sincronizarPorPeriodo(dataInicio, dataFim);

            if (resultado.getErro() != null) {
                ctx.setMensagemRetorno(String.format("Erro: %s", resultado.getErro()));
            } else if (resultado.getAviso() != null) {
                ctx.setMensagemRetorno(resultado.getAviso());
            } else {
                ctx.setMensagemRetorno(String.format(
                        "Sincronizacao concluida com sucesso!\n" +
                                "OS Processadas: %d\n" +
                                "OS com Erro: %d",
                        resultado.getOsProcessadas(), resultado.getOsComErro()
                ));
            }

        } catch (Exception e) {
            throw new Exception("Erro ao executar sincronizacao manual: " + e.getMessage(), e);
        }
    }
}

