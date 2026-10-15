package br.com.zaya.engineering.nexobutton.monitoring;

import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.net.URI;

public final class ScheduledLogMonitor implements ScheduledAction {
    @Override public void onTime(ScheduledActionContext context) {
        try {
            final HttpFailureSink.Configuration config=HttpFailureSink.loadConfiguration();
            if(config==null){context.info("NEXO_LOG_MONITOR: instalação não provisionada ou identidade expirada");return;}
            String log=System.getProperty("org.jboss.boot.log.file"),data=System.getProperty("jboss.server.data.dir");
            if(log==null||data==null)throw new IllegalStateException("Native log/data properties unavailable");
            Path checkpoint=Paths.get(data,"nexo-log-monitor","checkpoint.json");
            String target=config.installationId!=null?config.installationId:config.principal+"@"+URI.create(config.endpoint).getHost();
            IncrementalLogCollector.Result result=new IncrementalLogCollector().tick(Paths.get(log),checkpoint,target,
                    new IncrementalLogCollector.Delivery(){public void send(IncrementalLogCollector.LogEvent event)throws Exception{HttpFailureSink.deliver(event,config,2000);}});
            context.info("NEXO_LOG_MONITOR: bytes="+result.bytesRead+"; enviados="+result.sent+"; pendentes="+result.pending
                    +"; rotações="+result.rotations+"; inicializado="+result.initialized+"; envioPendente="+result.deliveryPending+"; concorrente="+result.overlap);
        }catch(Exception failure){context.info("NEXO_LOG_MONITOR: coleta não concluída; verificar configuração, fila e acesso aos arquivos. Nenhum caminho ou segredo é registrado.");}
    }
}
