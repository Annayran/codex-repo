package br.com.zaya.engineering.nexobutton.monitoring;

import java.time.Instant;
import java.util.ServiceLoader;
import java.util.UUID;
import java.text.Normalizer;

/** Capture in an outer catch; hosting transaction boundaries require homologation. No raw messages or stacks. */
public final class FailureTelemetry {
    private FailureTelemetry() {}
    public interface Sink { void send(Event event) throws Exception; }
    public static final class Event {
        public final String eventId, executionId, component, version, category, exceptionType, occurredAt;
        public final String impact="LOCAL_BLOCK", origin="LIVE";
        public Event(Throwable failure,String component,String version,String executionId) {
            this.eventId=UUID.randomUUID().toString();this.executionId=executionId;this.component=component;this.version=version;
            this.occurredAt=Instant.now().toString();this.category=category(failure);
            Throwable cause=failure;for(int i=0;i<8&&cause.getCause()!=null&&cause.getCause()!=cause;i++)cause=cause.getCause();
            this.exceptionType=cause.getClass().getName();
        }
    }
    public static String category(Throwable failure) {
        Throwable cause=failure;
        for(int i=0;i<8&&cause!=null;i++) {
            String category=categoryMessage(cause.getMessage());
            if(!"UNCLASSIFIED".equals(category))return category;
            Throwable next=cause.getCause();if(next==cause)break;cause=next;
        }
        return "UNCLASSIFIED";
    }
    public static String categoryMessage(String raw) {
        String bounded=String.valueOf(raw);bounded=bounded.substring(0,Math.min(bounded.length(),2048));
        String message=Normalizer.normalize(bounded,Normalizer.Form.NFD).replaceAll("\\p{M}","").toLowerCase(java.util.Locale.ROOT);
        if(message.contains("produt")&&message.contains("sem familia"))return "PRODUCT_FAMILY_MISSING";
        if(message.contains("produto")&&(message.contains("nao esta ativo")||message.contains("inativo")))return "PRODUCT_INACTIVE";
        if(message.contains("quantidade entregue"))return "DELIVERED_QTY";
        if(message.contains("estoque insuficiente"))return "INSUFFICIENT_STOCK";
        if(message.contains("particionamento ativo"))return "NO_ACTIVE_PARTITION";
        if(message.contains("combinacao global"))return "NO_GLOBAL_COMBINATION";
        return "UNCLASSIFIED";
    }
    public static void reportSafely(Throwable failure,String component,String version,String executionId) {
        try {
            Event event=new Event(failure,component,version,executionId);
            for(Sink sink:ServiceLoader.load(Sink.class,FailureTelemetry.class.getClassLoader())) {sink.send(event);break;}
        } catch(Throwable telemetryFailure) {
            // Deliberately preserve the original failure and ERP transaction semantics. No secret-bearing logs.
        }
    }
}
