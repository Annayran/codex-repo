package br.com.zaya.engineering.nexobutton;

import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

/** Bounded textual matching; no log paths or commands accepted from the user. */
public final class IncidentLogCollector {
    private static final int MAX_BYTES=1024*1024;
    private IncidentLogCollector() {}
    public static String collect(long note,String error) {
        try {
            String path=System.getProperty("org.jboss.boot.log.file");
            if (path==null || path.trim().isEmpty()) return result("UNAVAILABLE",0,false,"");
            return read(Paths.get(path),note,error);
        } catch (Exception ignored) { return result("UNAVAILABLE",0,false,""); }
    }
    static String read(Path path,long note,String error) throws Exception {
        byte[] bytes; boolean truncated;
        try (RandomAccessFile file=new RandomAccessFile(path.toFile(),"r")) {
            long size=file.length(); truncated=size>MAX_BYTES;
            file.seek(Math.max(0,size-MAX_BYTES)); bytes=new byte[(int)Math.min(size,MAX_BYTES)]; file.readFully(bytes);
        }
        String text=new String(bytes,StandardCharsets.UTF_8);
        if (truncated) { int first=text.indexOf('\n'); text=first<0 ? "" : text.substring(first+1); }
        String[] lines=text.split("\\r?\\n",-1);
        int start=Math.max(0,lines.length-5000);
        truncated=truncated || start>0;
        List<String> codes=terms(error,"\\bORA-\\d{5}\\b");
        List<String> triggers=terms(error,"\\bTRG_[A-Z0-9_$]{1,80}\\b");
        List<String> products=terms(error,"\\bPRODUTO\\s*[:=]?\\s*\\d{1,12}\\b");
        Pattern noteToken=Pattern.compile("(?<![0-9])"+note+"(?![0-9])");
        List<Integer> hits=new ArrayList<Integer>(); int best=0;
        for (int i=start;i<lines.length;i++) {
            String upper=lines[i].toUpperCase(Locale.ROOT);
            int score=noteToken.matcher(upper).find()?4:0;
            if (contains(upper,triggers)) score+=3;
            if (contains(upper,products)) score+=3;
            if (score==0 && (!triggers.isEmpty() || !products.isEmpty())) continue;
            if (contains(upper,codes)) score++;
            if (score>best) {best=score;hits.clear();}
            if (score>0 && score==best) hits.add(i);
        }
        if (hits.isEmpty()) return result("NOT_FOUND",bytes.length,truncated,"");
        SortedSet<Integer> selected=new TreeSet<Integer>();
        for (int hit=hits.size()-1;hit>=Math.max(0,hits.size()-3);hit--) {
            for (int i=Math.max(start,hits.get(hit)-2);i<=Math.min(lines.length-1,hits.get(hit)+6);i++) selected.add(i);
        }
        StringBuilder content=new StringBuilder();
        for (int index:selected) {
            String line=lines[index];
            if (line.length()>2048) {line=line.substring(0,2048);truncated=true;}
            String safe=sanitize(line);
            if (safe.length()>512) {safe=safe.substring(0,512)+" [TRUNCATED]";truncated=true;}
            if (content.length()+safe.length()+1>12000) {truncated=true;break;}
            content.append(safe).append('\n');
        }
        return result("FOUND",bytes.length,truncated,content.toString());
    }
    private static List<String> terms(String error,String expression) {
        List<String> values=new ArrayList<String>();
        Matcher matcher=Pattern.compile(expression).matcher(error.toUpperCase(Locale.ROOT));
        while (matcher.find() && values.size()<10) values.add(matcher.group());
        return values;
    }
    private static boolean contains(String line,List<String> values) {
        for (String value:values) if (line.contains(value)) return true;
        return false;
    }
    static String sanitize(String line) {
        if (line.matches("(?is).*\\b(password|passwd|senha|secret|token|authorization|cookie|api[_-]?key|payload)\\b.*")
                || line.matches("(?is).*\\b(request\\s*body|select\\s|insert\\s+into|update\\s|delete\\s+from)\\b.*")
                || line.contains("{") || line.contains("}")) return "[LINE OMITTED: potentially sensitive content]";
        // Keep only diagnostic message/stack portions; omit unrelated surrounding user/request text.
        Matcher diagnostic=Pattern.compile("(?i)(ORA-\\d{5}|TRG_[A-Z0-9_$]+|Produto\\s*[:=]?\\s*\\d+|Caused by:|^\\s*at\\s+[\\w.$]+\\()").matcher(line);
        if (!diagnostic.find()) return "[CONTEXT LINE OMITTED]";
        String safe=line.substring(diagnostic.start()).trim();
        if (safe.regionMatches(true,0,"Caused by:",0,10)) {
            Matcher type=Pattern.compile("(?i)Caused by:\\s*([A-Za-z0-9_.$]+)").matcher(safe);
            return type.find()?"Caused by: "+type.group(1)+" [message omitted]":"[EXCEPTION MESSAGE OMITTED]";
        }
        return safe.replaceAll("(?i)[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}","[EMAIL REMOVED]")
                .replaceAll("\\b\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}\\b|\\b\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}\\b|\\b\\d{9,}\\b","[IDENTIFIER REMOVED]")
                .replaceAll("(?i)(?:https?://|jdbc:|file:)[^\\s]+","[URL REMOVED]")
                .replaceAll("[A-Za-z]:\\\\[^\\s]+|(?<![\\w])/(?:[\\w.-]+/)+[\\w.-]*","[PATH REMOVED]")
                .replaceAll("[\\p{Cntrl}&&[^\\t]]", " ");
    }
    private static String result(String status,int bytes,boolean truncated,String content) {
        return "{\"status\":"+MinimalPackage.quote(status)+",\"correlation\":\"TEXT_MATCH_UNVERIFIED\",\"scannedBytes\":"+bytes
                +",\"truncated\":"+truncated+",\"content\":"+MinimalPackage.quote(content)+"}";
    }
}
