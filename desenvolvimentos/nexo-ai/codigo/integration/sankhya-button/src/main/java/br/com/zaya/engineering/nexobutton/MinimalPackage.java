package br.com.zaya.engineering.nexobutton;

import java.time.Instant;

/** Sends selected-note context only. No stock or configuration is fabricated. */
public final class MinimalPackage {
    private MinimalPackage() {}
    public static String collect(long nunota, String error, String erpVersion) {
        return collect(nunota, error, erpVersion, "Server log not collected");
    }
    public static String collect(long nunota, String error, String erpVersion, String logSignals) {
        if (nunota <= 0 || error == null || error.trim().isEmpty() || error.length() > 2000
                || erpVersion == null || erpVersion.trim().isEmpty() || erpVersion.length() > 80
                || logSignals == null || logSignals.length() > 2000)
            throw new IllegalArgumentException("Note, exact bounded error and ERP version required");
        return "{\"schemaVersion\":1,\"nunota\":"+nunota+",\"action\":\"CONFIRMAR_NOTA\",\"error\":"+quote(error)
                +",\"collectedAt\":"+quote(Instant.now().toString())+",\"erpVersion\":"+quote(erpVersion)
                +",\"collectorVersion\":\"selected-note-log-signals-v1\",\"simulated\":false,\"items\":[],\"evidence\":[{"
                +"\"id\":\"selected-note\",\"source\":\"collection://sankhya/action-button/selected-note\","
                +"\"version\":\"1\",\"observation\":"+quote("Selected note NUNOTA="+nunota)+"},{"
                +"\"id\":\"server-log-signals\",\"source\":\"collection://sankhya/server-log/aggregate\","
                +"\"version\":\"1\",\"observation\":"+quote(logSignals)+"}],"
                +"\"missing\":[\"Items and stock were not collected by this minimal collector\","
                +"\"Validated operation/product configuration and availability policy missing\","
                +"\"Historical events, related documents and customizations missing\"]}";
    }
    static String quote(String value) {
        StringBuilder output = new StringBuilder("\"");
        for (int i=0; i<value.length(); i++) {
            char character = value.charAt(i);
            if (character == '"' || character == '\\') output.append('\\').append(character);
            else if (character < 32) output.append(String.format("\\u%04x", (int)character));
            else output.append(character);
        }
        return output.append('"').toString();
    }
}
