package br.com.zaya.engineering.nexobutton;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
public final class LogSignalChecks {
    public static void main(String[] args) throws Exception {
        Path file=Files.createTempFile("nexo-synthetic-log-", ".txt");
        try {
            StringBuilder text=new StringBuilder("[ERROR] old exception outside tail\n");
            for (int i=0;i<3000;i++) text.append("INFO synthetic-padding-1234567890\n");
            text.append("[ERROR] java.sql.SQLException password=SYNTHETIC-SECRET email=synthetic@example.invalid\n");
            text.append("[WARN] request body SYNTHETIC-PRIVATE-BODY\n");
            Files.write(file,text.toString().getBytes(StandardCharsets.UTF_8));
            String signal=LogSignals.read(file);
            if (!signal.contains("errorLines=1") || !signal.contains("warningLines=1") || !signal.contains("java.sql.SQLException"))
                throw new AssertionError("Expected aggregate signals");
            if (signal.contains("SYNTHETIC-SECRET") || signal.contains("example.invalid") || signal.contains("PRIVATE-BODY") || signal.contains(file.toString()))
                throw new AssertionError("Raw data leaked");
            String packet=MinimalPackage.collect(123,"synthetic error","test-version",signal);
            if (!packet.contains("server-log-signals")) throw new AssertionError("Evidence missing");
            System.out.println("Log signals: bounded tail, error/warning counts, standard exception types, no raw messages/path passed.");
        } finally { Files.deleteIfExists(file); }
    }
}
