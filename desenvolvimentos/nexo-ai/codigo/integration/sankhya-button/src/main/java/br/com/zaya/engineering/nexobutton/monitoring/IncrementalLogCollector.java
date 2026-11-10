package br.com.zaya.engineering.nexobutton.monitoring;

import com.google.gson.Gson;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.*;
import java.util.regex.*;

/** Single-file incremental reader with checkpoint/outbox written before network delivery. */
public final class IncrementalLogCollector {
    static final int MAX_READ=65536, MAX_PENDING=10, MAX_EXCERPT=2000;
    static final int MAX_FOCUSED_READ=2097152;
    private static final Pattern HEADER=Pattern.compile("^(?:(?:\\d{4}-\\d{2}-\\d{2}[ T])?\\d{2}:\\d{2}:\\d{2}[,.]\\d{3,9}\\s+)?(TRACE|DEBUG|INFO|WARN|ERROR|FATAL)\\b");
    private static final Pattern ORACLE=Pattern.compile("ORA-(\\d{5})");
    private static final Pattern WRAPPED=Pattern.compile("^(?:\\d{4}-\\d{2}-\\d{2}[ T])?\\d{2}:\\d{2}:\\d{2}[,.]\\d{3,9}\\s+(TRACE|DEBUG|INFO|WARN|ERROR|FATAL)\\s+\\[([^]]+)\\]\\s+\\(([^)]+)\\)\\s?(.*)$");
    private static final Pattern STACK=Pattern.compile("^\\s*(?:at\\s|Caused by:|Suppressed:|\\.\\.\\.\\s+\\d+\\s+more|ORA-\\d{5})");
    private static final Pattern EXCEPTION=Pattern.compile("(?:[A-Za-z_$][\\w$]*\\.)+(?:[A-Z_$][\\w$]*)?(?:Exception|Error)\\b");
    public interface Delivery { void send(LogEvent event) throws Exception; }
    public static final class LogEvent {
        public String eventId,executionId,component="sankhya.serverlog",version="UNVERIFIED_LOG",category,exceptionType;
        public String occurredAt,impact="UNKNOWN",origin="LIVE",logExcerpt;
    }
    static final class State {
        int schemaVersion=1;String identity,target,generation,anchor,level="",excerpt="",category="UNCLASSIFIED",exceptionType="log.ReportedFailure";
        String streamKey="";
        long position,recordStart,lastSize,rotations,filtered;int recordLines;boolean discardLongLine,ignoreRecord,focusSkala,recordRelevant,hasMessage;
        List<LogEvent> pending=new ArrayList<LogEvent>();
    }
    public static final class Result {
        public int bytesRead,sent,pending;public long rotations,lagBytes,filtered;public boolean initialized,deliveryPending,overlap;
        public String failureCode="NENHUMA";
    }
    private final Gson gson=new com.google.gson.GsonBuilder().disableHtmlEscaping().create();

    public Result tick(Path log,Path checkpoint,String target,Delivery delivery) throws Exception {
        return tick(log,checkpoint,target,delivery,false);
    }
    public Result tickFocused(Path log,Path checkpoint,String target,Delivery delivery) throws Exception {
        return tick(log,checkpoint,target,delivery,true);
    }
    private Result tick(Path log,Path checkpoint,String target,Delivery delivery,boolean focusSkala) throws Exception {
        secureDirectory(checkpoint.getParent());
        Path lockPath=checkpoint.resolveSibling(checkpoint.getFileName()+".lock");
        try(FileChannel channel=FileChannel.open(lockPath,StandardOpenOption.CREATE,StandardOpenOption.WRITE)) {
            FileLock lock;
            try{lock=channel.tryLock();}catch(java.nio.channels.OverlappingFileLockException busy){lock=null;}
            if(lock==null){Result result=new Result();result.overlap=true;return result;}
            try{return lockedTick(log,checkpoint,target,delivery,focusSkala);}finally{lock.release();}
        }
    }
    private Result lockedTick(Path log,Path checkpoint,String target,Delivery delivery,boolean focusSkala) throws Exception {
        if(!Files.isRegularFile(log)||Files.isSymbolicLink(checkpoint))throw new java.io.IOException("Invalid collector files");
        Result result=new Result();State state=null;
        if(Files.exists(checkpoint)) {
            if(Files.size(checkpoint)>131072)throw new java.io.IOException("Oversized collector checkpoint");
            state=gson.fromJson(new String(Files.readAllBytes(checkpoint),StandardCharsets.UTF_8),State.class);
            if(state==null||state.schemaVersion!=1||state.pending==null||state.pending.size()>MAX_PENDING||state.position<0
                    ||state.excerpt==null||state.excerpt.length()>MAX_EXCERPT||state.generation==null)
                throw new java.io.IOException("Invalid collector checkpoint");
            if(!target.equals(state.target))throw new java.io.IOException("Collector identity changed; review pending state");
            state.focusSkala=focusSkala;
            result.lagBytes=Math.max(0,Files.size(log)-state.position);
            if(!deliver(checkpoint,state,delivery,result))return finish(result,state);
        }
        BasicFileAttributes attributes=Files.readAttributes(log,BasicFileAttributes.class);
        String identity=hash(log.toRealPath()+"|"+attributes.fileKey()+"|"+attributes.creationTime());
        try(RandomAccessFile input=new RandomAccessFile(log.toFile(),"r")) {
            if(state==null) {
                state=new State();state.focusSkala=focusSkala;state.identity=identity;state.target=target;state.generation=UUID.randomUUID().toString();
                state.position=input.length();state.lastSize=state.position;state.anchor=anchor(input,state.position);
                if(state.position>0){input.seek(state.position-1);state.discardLongLine=input.read()!='\n';}
                save(checkpoint,state);result.initialized=true;return result;
            }
            boolean reset=!identity.equals(state.identity)||input.length()<state.position||!anchor(input,state.position).equals(state.anchor);
            if(reset) {
                finishRecord(state);state.position=0;state.identity=identity;state.generation=UUID.randomUUID().toString();
                state.anchor="";state.discardLongLine=false;state.rotations++;clearRecord(state);
            }
            int readLimit=focusSkala?MAX_FOCUSED_READ:MAX_READ;
            long size=input.length();int length=(int)Math.min(readLimit,Math.max(0,size-state.position));
            byte[] bytes=new byte[length];input.seek(state.position);input.readFully(bytes);result.bytesRead=length;
            long base=state.position;int start=0,consumed=0;
            for(int i=0;i<length;i++)if(bytes[i]=='\n') {
                if(state.pending.size()>=MAX_PENDING)break;
                if(state.discardLongLine)state.discardLongLine=false;
                else line(state,new String(bytes,start,i-start,StandardCharsets.UTF_8).replace("\r",""),base+start);
                start=i+1;consumed=start;
            }
            if(length==readLimit&&consumed==0){state.discardLongLine=true;consumed=length;}
            state.position=base+consumed;
            // Keep an open record for one quiet cycle so continuations can join it.
            if(length==0&&size==state.lastSize&&state.pending.size()<MAX_PENDING){finishRecord(state);clearRecord(state);}
            state.lastSize=size;state.anchor=anchor(input,state.position);result.lagBytes=Math.max(0,size-state.position);save(checkpoint,state);
        }
        // Never hold the server's log handle while waiting for HTTPS.
        deliver(checkpoint,state,delivery,result);
        return finish(result,state);
    }
    private void line(State state,String raw,long position) {
        String bounded=raw.substring(0,Math.min(raw.length(),2048));
        Matcher wrapped=WRAPPED.matcher(bounded);
        String content=bounded,key="";
        if(wrapped.matches()){content=wrapped.group(4);key=wrapped.group(2)+"|"+wrapped.group(3);}
        boolean stackLine=STACK.matcher(content).find();
        Matcher header=HEADER.matcher(bounded);
        if(header.find()) {
            boolean continuation=wrapped.matches()&&(stackLine||content.trim().isEmpty())&&key.equals(state.streamKey)&&!state.level.isEmpty()
                    &&header.group(1).equals(state.level);
            if(!continuation){finishRecord(state);clearRecord(state);state.level=header.group(1);state.recordStart=position;state.streamKey=key;}
        } else if(state.level.isEmpty()&&(bounded.startsWith("ORA-")||EXCEPTION.matcher(bounded).lookingAt())) {
            state.level="ERROR";state.recordStart=position;
        }
        if(!"ERROR".equals(state.level)&&!"FATAL".equals(state.level)&&!(state.focusSkala&&"WARN".equals(state.level)))return;
        if(!content.trim().isEmpty()&&!content.trim().startsWith("at ")&&!content.trim().startsWith("..."))state.hasMessage=true;
        if(bounded.contains("NEXO_LOG_MONITOR")||bounded.contains("br.com.zaya.engineering.nexobutton.monitoring.ScheduledLogMonitor"))state.ignoreRecord=true;
        String category=FailureTelemetry.categoryMessage(bounded);
        if(bounded.contains("br.com.zaya.skala.")||"PRODUCT_FAMILY_MISSING".equals(category)
                ||"DELIVERED_QTY".equals(category)||"NO_ACTIVE_PARTITION".equals(category)||"NO_GLOBAL_COMBINATION".equals(category))state.recordRelevant=true;
        if(!"UNCLASSIFIED".equals(category))state.category=category;
        Matcher oracle=ORACLE.matcher(bounded);if("UNCLASSIFIED".equals(state.category)&&oracle.find())state.category="ORA_"+oracle.group(1);
        Matcher exception=EXCEPTION.matcher(bounded);if(exception.find()&&exception.group().length()<=200)state.exceptionType=exception.group();
        boolean important=content.contains("Caused by:")||content.contains("Suppressed:")||content.contains("ORA-");
        if(important&&(state.recordLines>=16||state.excerpt.length()>MAX_EXCERPT-520)){
            String[] previous=state.excerpt.split("\n");StringBuilder retained=new StringBuilder();int kept=0;
            for(String prior:previous){if(prior.trim().startsWith("at ")||prior.trim().startsWith("..."))continue;
                if(retained.length()+prior.length()+1>MAX_EXCERPT-520)break;retained.append(prior).append('\n');kept++;}
            state.excerpt=retained.toString();state.recordLines=kept;
        }
        if(state.excerpt.length()<MAX_EXCERPT&&state.recordLines<16) {
            String safe=MonitoringLogFilter.filter(wrapped.matches()?content:raw);int remaining=MAX_EXCERPT-state.excerpt.length();
            state.excerpt+=safe.substring(0,Math.min(safe.length(),Math.max(0,remaining-1)))+"\n";
            state.recordLines++;
        }
    }
    private void finishRecord(State state) {
        boolean warningFailure=state.focusSkala&&"WARN".equals(state.level)&&state.recordRelevant
                &&!"log.ReportedFailure".equals(state.exceptionType);
        if(state.ignoreRecord||!state.hasMessage||state.excerpt.isEmpty()||(!"ERROR".equals(state.level)&&!"FATAL".equals(state.level)&&!warningFailure))return;
        if(state.focusSkala&&!state.recordRelevant){state.filtered++;return;}
        if(state.pending.size()>=MAX_PENDING)throw new IllegalStateException("Collector queue full");
        LogEvent event=new LogEvent();event.eventId=UUID.nameUUIDFromBytes((state.generation+":"+state.recordStart).getBytes(StandardCharsets.UTF_8)).toString();
        event.executionId=event.eventId;event.category=state.category;event.exceptionType=state.exceptionType;
        event.occurredAt=Instant.now().toString();event.logExcerpt=state.excerpt.trim();state.pending.add(event);
    }
    private void clearRecord(State state){state.level="";state.excerpt="";state.category="UNCLASSIFIED";state.exceptionType="log.ReportedFailure";state.ignoreRecord=false;state.recordRelevant=false;state.hasMessage=false;state.streamKey="";state.recordLines=0;}
    private boolean deliver(Path checkpoint,State state,Delivery delivery,Result result) throws Exception {
        while(!state.pending.isEmpty()&&result.sent<5) {
            try{delivery.send(state.pending.get(0));}catch(Exception unavailable){result.deliveryPending=true;result.failureCode=HttpFailureSink.failureCode(unavailable);return false;}
            state.pending.remove(0);save(checkpoint,state);result.sent++;
        }
        return state.pending.isEmpty();
    }
    private Result finish(Result result,State state){result.pending=state.pending.size();result.rotations=state.rotations;result.filtered=state.filtered;return result;}
    private String anchor(RandomAccessFile input,long position)throws Exception {
        if(position==0)return "";if(position>input.length())return "TRUNCATED";
        int length=(int)Math.min(64,position);byte[] bytes=new byte[length];input.seek(position-length);input.readFully(bytes);return hash(new String(bytes,StandardCharsets.ISO_8859_1));
    }
    private String hash(String value)throws Exception {
        byte[] digest=MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8));StringBuilder hex=new StringBuilder();
        for(byte b:digest)hex.append(String.format(java.util.Locale.ROOT,"%02x",b&255));return hex.toString();
    }
    private void save(Path checkpoint,State state)throws Exception {
        byte[] bytes=gson.toJson(state).getBytes(StandardCharsets.UTF_8);if(bytes.length>131072)throw new java.io.IOException("Collector checkpoint capacity reached");
        Path temporary=Files.createTempFile(checkpoint.getParent(),"checkpoint-",".tmp");
        try{
            Files.write(temporary,bytes);
            for(int attempt=0;;attempt++){
                try{Files.move(temporary,checkpoint,StandardCopyOption.REPLACE_EXISTING,StandardCopyOption.ATOMIC_MOVE);break;}
                catch(java.nio.file.AccessDeniedException busy){if(attempt>=4)throw busy;Thread.sleep(50);}
            }
        }
        finally{Files.deleteIfExists(temporary);}
    }
    private void secureDirectory(Path directory)throws Exception {
        Files.createDirectories(directory);
        PosixFileAttributeView posix=Files.getFileAttributeView(directory,PosixFileAttributeView.class);
        if(posix!=null){Files.setPosixFilePermissions(directory,PosixFilePermissions.fromString("rwx------"));return;}
        AclFileAttributeView acl=Files.getFileAttributeView(directory,AclFileAttributeView.class);
        if(acl!=null)acl.setAcl(Collections.singletonList(AclEntry.newBuilder().setType(AclEntryType.ALLOW).setPrincipal(acl.getOwner())
                .setPermissions(EnumSet.allOf(AclEntryPermission.class)).setFlags(AclEntryFlag.FILE_INHERIT,AclEntryFlag.DIRECTORY_INHERIT).build()));
    }
}
