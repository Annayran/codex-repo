package br.com.zaya.engineering.nexo.investigation;
import br.com.zaya.engineering.nexo.diagnosis.Contracts.LogExcerpt;
import jakarta.ws.rs.BadRequestException;
import java.nio.charset.StandardCharsets;
import java.util.Set;

/** Defense in depth; log content is never submitted to the language model. */
public final class LogExcerptFilter {
    private LogExcerptFilter() {}
    public static LogExcerpt normalize(LogExcerpt log) {
        if (log==null) return null;
        if (!Set.of("FOUND","NOT_FOUND","UNAVAILABLE").contains(log.status()==null?"":log.status())
                || !"TEXT_MATCH_UNVERIFIED".equals(log.correlation()) || log.scannedBytes()<0 || log.scannedBytes()>1048576
                || log.content()==null || log.content().length()>12000 || log.content().getBytes(StandardCharsets.UTF_8).length>48000
                || log.content().split("\n",-1).length>61
                || !log.status().equals("FOUND") && !log.content().isEmpty()
                || log.status().equals("FOUND") && log.content().isBlank())
            throw new BadRequestException("Invalid bounded log excerpt");
        StringBuilder safe=new StringBuilder();
        for (String line:log.content().split("\n",-1)) {
            if (line.length()>600) throw new BadRequestException("Oversized log line");
            if (line.matches("(?is).*\\b(password|passwd|senha|secret|token|authorization|cookie|api[_-]?key|payload)\\b.*")
                    || line.matches("(?is).*\\b(request\\s*body|select\\s|insert\\s+into|update\\s|delete\\s+from)\\b.*")
                    || line.contains("{") || line.contains("}")) line="[LINE OMITTED: potentially sensitive content]";
            line=line.replaceAll("(?i)[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}","[EMAIL REMOVED]")
                    .replaceAll("\\b\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}\\b|\\b\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}\\b|\\b\\d{9,}\\b","[IDENTIFIER REMOVED]")
                    .replaceAll("(?i)(?:https?://|jdbc:|file:)[^\\s]+","[URL REMOVED]")
                    .replaceAll("[A-Za-z]:\\\\[^\\s]+|(?<![\\w])/(?:[\\w.-]+/)+[\\w.-]*","[PATH REMOVED]")
                    .replaceAll("[\\p{Cntrl}&&[^\\t]]"," ");
            safe.append(line).append('\n');
        }
        String content=log.content().isEmpty()?"":safe.toString().stripTrailing();
        if (content.length()>12000) throw new BadRequestException("Filtered log exceeds size limit");
        return new LogExcerpt(log.status(),"TEXT_MATCH_UNVERIFIED",log.scannedBytes(),log.truncated(),content);
    }
    public static String fileContent(LogExcerpt log) {
        return "Nexo filtered incident log excerpt\nCorrelation: TEXT_MATCH_UNVERIFIED; not proof of causal origin\n"
                +"Scanned bytes: "+log.scannedBytes()+"; truncated: "+log.truncated()+"\n"
                +"Redaction is heuristic; restricted access and retention required.\n\n"+log.content()+"\n";
    }
}
