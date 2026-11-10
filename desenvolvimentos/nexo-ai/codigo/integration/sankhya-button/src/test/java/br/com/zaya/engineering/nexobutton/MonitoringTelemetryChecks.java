package br.com.zaya.engineering.nexobutton;

import br.com.zaya.engineering.nexobutton.monitoring.FailureTelemetry;
import java.util.UUID;

public final class MonitoringTelemetryChecks {
    public static void main(String[] args) throws Exception {
        Exception original=new Exception("private-note password=secret",new IllegalStateException("Produto não está ativo"));
        FailureTelemetry.Event event=new FailureTelemetry.Event(original,"test.component","test-v1",UUID.randomUUID().toString());
        if(!"PRODUCT_INACTIVE".equals(event.category))throw new AssertionError("Nested classification failed");
        for(java.lang.reflect.Field field:FailureTelemetry.Event.class.getFields()) {
            Object value=field.get(event);
            if(String.valueOf(value).contains("secret")||String.valueOf(value).contains("private-note"))throw new AssertionError("Raw message exported");
        }
        if(!"DELIVERED_QTY".equals(FailureTelemetry.category(new Exception("A quantidade entregue não pode ser maior"))))throw new AssertionError("Quantity category");
        if(!"NO_ACTIVE_PARTITION".equals(FailureTelemetry.category(new Exception("Nao foi encontrado particionamento ativo"))))throw new AssertionError("Missing partition category");
        if(!"NO_GLOBAL_COMBINATION".equals(FailureTelemetry.category(new Exception("Nao existe combinação global de SKU"))))throw new AssertionError("Combination category");
        FailureTelemetry.reportSafely(original,"test.component","test-v1",UUID.randomUUID().toString());
        System.out.println("Monitoring telemetry checks PASS; original exception and raw messages preserved locally");
    }
}
