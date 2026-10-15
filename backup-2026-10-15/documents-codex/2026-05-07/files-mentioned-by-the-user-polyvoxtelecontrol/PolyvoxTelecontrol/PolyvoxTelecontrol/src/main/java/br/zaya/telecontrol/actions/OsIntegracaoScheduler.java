package br.zaya.telecontrol.actions;

import br.zaya.telecontrol.services.OsIntegracaoService;
import br.zaya.telecontrol.utils.DateUtil;
import br.zaya.telecontrol.utils.LogUtil;
import br.com.sankhya.jape.core.JapeSession;
import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;

import java.util.Date;

/**
 * Scheduler para sincronização automática de OS
 * Executa diariamente às 01:00 da manhã
 * Sincroniza OS do dia anterior (D-1)
 */
public class OsIntegracaoScheduler implements ScheduledAction {
    
    @Override
    public void onTime(ScheduledActionContext ctx) {
        JapeSession.SessionHandle hnd = null;
        
        try {
            hnd = JapeSession.open();
            hnd.setPriorityLevel(JapeSession.LOW_PRIORITY);
            hnd.execWithTX(new JapeSession.TXBlock() {
                public void doWithTx() throws Exception {
                    executarSincronizacao(ctx);
                }
            });
        } catch (Exception e) {
            ctx.info("[ERRO] Erro ao executar scheduler de OS: " + e.getMessage());
            try {
                LogUtil.saveLog(e);
            } catch (Exception logError) {
                ctx.info("[ERRO] Erro ao salvar log: " + logError.getMessage());
            }
        } finally {
            JapeSession.close(hnd);
        }
    }
    
    private void executarSincronizacao(ScheduledActionContext ctx) throws Exception {
        try {
            ctx.info("Iniciando sincronização automática de OS (D-1)...");
            
            Date ontem = DateUtil.getYesterday();
            String dataFormatada = DateUtil.formatBrDate(ontem);
            
            ctx.info(String.format("Data de sincronização: %s", dataFormatada));
            
            OsIntegracaoService.SincronizacaoResultado resultado = 
                OsIntegracaoService.sincronizacaoDia1();
            
            if (resultado.erro != null) {
                ctx.info("[ERRO] Erro na sincronização: " + resultado.erro);
            } else if (resultado.aviso != null) {
                ctx.info("[AVISO] " + resultado.aviso);
            } else {
                ctx.info(String.format(
                    "[SUCESSO] Sincronização concluída: %d OS processadas, %d com erro",
                    resultado.osProcessadas, resultado.osComErro
                ));
            }
            
        } catch (Exception e) {
            ctx.info("[ERRO] Erro ao executar sincronização D-1: " + e.getMessage());
            LogUtil.saveLog(e);
        }
    }
}

