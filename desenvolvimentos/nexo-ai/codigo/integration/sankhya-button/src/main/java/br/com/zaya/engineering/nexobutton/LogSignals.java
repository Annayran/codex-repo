package br.com.zaya.engineering.nexobutton;

import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Bounded diagnostic signals only: never sends log lines, messages, paths or request bodies. */
public final class LogSignals {
    private static final int MAX_BYTES = 65536;
    private static final Pattern EXCEPTION = Pattern.compile("\\b((?:java|javax|jakarta|org\\.hibernate)\\.[A-Za-z0-9_.$]{1,120}(?:Exception|Error))\\b");
    private LogSignals() {}
    public static String collect() {
        try {
            String file = System.getProperty("org.jboss.boot.log.file");
            if (file == null || file.trim().isEmpty()) return "Server log unavailable: runtime property absent";
            return read(Paths.get(file));
        } catch (Exception ignored) { return "Server log unavailable: access or read failed"; }
    }
    static String read(Path path) throws Exception {
        byte[] bytes;
        boolean clipped;
        try (RandomAccessFile file = new RandomAccessFile(path.toFile(), "r")) {
            long size = file.length();
            clipped = size > MAX_BYTES;
            file.seek(Math.max(0, size - MAX_BYTES));
            bytes = new byte[(int)Math.min(size, MAX_BYTES)];
            file.readFully(bytes);
        }
        String text = new String(bytes, StandardCharsets.UTF_8);
        if (clipped) {
            int boundary = text.indexOf('\n');
            text = boundary < 0 ? "" : text.substring(boundary + 1);
        }
        String[] lines = text.split("\\r?\\n", -1);
        int errors=0, warnings=0, count=0;
        Set<String> types = new LinkedHashSet<String>();
        for (int i=Math.max(0, lines.length-1000); i<lines.length; i++) {
            if (lines[i].isEmpty()) continue;
            count++;
            if (lines[i].contains(" ERROR ") || lines[i].contains("[ERROR]")) errors++;
            if (lines[i].contains(" WARN ") || lines[i].contains("[WARN]")) warnings++;
            Matcher matcher=EXCEPTION.matcher(lines[i]);
            while (types.size()<5 && matcher.find()) types.add(matcher.group(1));
        }
        return "Server log aggregate (not correlated to selected note): scannedBytes="+bytes.length
                +", scannedLines="+count+", errorLines="+errors+", warningLines="+warnings
                +", standardExceptionTypes="+types+"; raw content and file path omitted";
    }
}
