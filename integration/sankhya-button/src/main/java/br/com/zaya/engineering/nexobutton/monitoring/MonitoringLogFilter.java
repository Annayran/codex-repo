package br.com.zaya.engineering.nexobutton.monitoring;

/** Heuristic redaction, not a guarantee of anonymization. */
final class MonitoringLogFilter {
    private MonitoringLogFilter() {}
    static String filter(String raw) {
        String line=raw.substring(0,Math.min(raw.length(),2048));
        if(line.matches("(?is).*\\b(password|passwd|senha|secret|token|authorization|cookie|api[_-]?key|payload|username|usuario|login)\\b.*")
                ||line.matches("(?is).*\\b(request\\s*body|select\\s|insert\\s+into|update\\s|delete\\s+from)\\b.*")
                ||line.contains("{")||line.contains("}"))return "[LINHA OMITIDA: conteúdo potencialmente sensível]";
        line=line.replaceAll("(?i)[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}","[EMAIL OMITIDO]")
                .replaceAll("\\b\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}\\b|\\b\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}\\b|\\b\\d{9,}\\b","[IDENTIFICADOR OMITIDO]")
                .replaceAll("(?i)(?:https?://|jdbc:|file:)[^\\s]+","[URL OMITIDA]")
                .replaceAll("[A-Za-z]:\\\\[^\\s]+|(?<![\\w])/(?:[\\w.-]+/)+[\\w.-]*","[CAMINHO OMITIDO]")
                .replaceAll("[\\p{Cntrl}&&[^\\t]]"," ");
        return line.substring(0,Math.min(line.length(),500));
    }
}
