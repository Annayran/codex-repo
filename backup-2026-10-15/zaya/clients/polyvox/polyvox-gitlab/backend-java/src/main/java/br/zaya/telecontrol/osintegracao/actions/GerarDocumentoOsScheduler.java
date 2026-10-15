package br.zaya.telecontrol.osintegracao.actions;

import br.com.sankhya.jape.core.JapeSession;
import br.zaya.telecontrol.osintegracao.application.LegacyOsIntegracaoUseCase;
import br.zaya.telecontrol.osintegracao.application.OsIntegracaoUseCase;
import br.zaya.telecontrol.osintegracao.application.SincronizacaoResultado;
import br.zaya.telecontrol.shared.utils.LogUtil;
import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;

/**
 * Scheduler para gerar documentos da integracao de OS:
 * - Nota de compra para OS com peca (controlada por parametro do sistema)
 * - Financeiro clone para OS sem peca
 */
public class GerarDocumentoOsScheduler implements ScheduledAction {
    private final OsIntegracaoUseCase osUseCase;

    public GerarDocumentoOsScheduler() {
        this(new LegacyOsIntegracaoUseCase());
    }

    public GerarDocumentoOsScheduler(OsIntegracaoUseCase osUseCase) {
        this.osUseCase = osUseCase;
    }

    @Override
    public void onTime(ScheduledActionContext ctx) {
        JapeSession.SessionHandle hnd = null;
        try {
            hnd = JapeSession.open();
            hnd.setPriorityLevel(JapeSession.LOW_PRIORITY);
            hnd.execWithTX(() -> executar(ctx));
        } catch (Exception e) {
            ctx.info("[ERRO] Erro ao executar scheduler de documentos de OS: " + e.getMessage());
            try {
                LogUtil.saveLog(e);
            } catch (Exception ignore) {
            }
        } finally {
            JapeSession.close(hnd);
        }
    }

    private void executar(ScheduledActionContext ctx) throws Exception {
        ctx.info("Iniciando processamento documental de OS (nota/financeiro)...");

        SincronizacaoResultado resultado = osUseCase.processarDocumentosOs(null);
        if (resultado.getErro() != null) {
            throw new Exception("Erro ao gerar documentos de OS: " + resultado.getErro());
        }

        ctx.info("[SUCESSO] Documentos de OS processados | os=" + resultado.getOsProcessadas()
                + " | gruposComErro=" + resultado.getOsComErro()
                + " | detalhe=" + (resultado.getAviso() == null ? "-" : resultado.getAviso()));
    }
}

