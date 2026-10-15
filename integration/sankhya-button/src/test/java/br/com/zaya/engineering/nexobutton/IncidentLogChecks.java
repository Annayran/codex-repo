package br.com.zaya.engineering.nexobutton;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
public final class IncidentLogChecks {
    public static void main(String[] args) throws Exception {
        Path log=Files.createTempFile("nexo-excerpt-synthetic-", ".log");
        try {
            String text="INFO unrelated customer context\nORA-20101: Produto 19391 nao esta ativo synthetic@example.invalid\n"
                    +"ORA-06512: em TESTE.TRG_INC_TGFITE line 437\n"
                    +" at demo.Trigger.run(Trigger.java:437)\n"
                    +"ORA-20101: password=SYNTHETIC-CREDENTIAL\n"
                    +"INFO personal unrelated name\n";
            Files.write(log,text.getBytes(StandardCharsets.UTF_8));
            String result=IncidentLogCollector.read(log,5031796,"ORA-20101 Produto 19391 TESTE.TRG_INC_TGFITE");
            if (!result.contains("\"status\":\"FOUND\"") || !result.contains("TRG_INC_TGFITE") || !result.contains("Trigger.java:437"))
                throw new AssertionError("Matching error and stack context missing");
            if (result.contains("synthetic@example.invalid") || result.contains("SYNTHETIC-CREDENTIAL") || result.contains("personal unrelated"))
                throw new AssertionError("Sensitive or unrelated content leaked");
            if (!IncidentLogCollector.read(log,42,"ORA-99999").contains("\"status\":\"NOT_FOUND\""))
                throw new AssertionError("Unrelated log must not be returned");
            StringBuilder huge=new StringBuilder("ORA-99999 old match outside tail\n");
            for (int i=0;i<40000;i++) huge.append("INFO synthetic padding 1234567890\n");
            Files.write(log,huge.toString().getBytes(StandardCharsets.UTF_8));
            result=IncidentLogCollector.read(log,42,"ORA-99999");
            if (!result.contains("\"status\":\"NOT_FOUND\"") || !result.contains("\"scannedBytes\":1048576") || !result.contains("\"truncated\":true"))
                throw new AssertionError("Bounded tail must report omitted history");
            System.out.println("Incident log: error/trigger matching, bounded tail, stack context, redaction and not-found checks passed.");
        } finally { Files.deleteIfExists(log); }
    }
}
