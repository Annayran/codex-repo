package br.com.zaya.engineering.nexo.monitoring;

import br.com.zaya.engineering.nexo.diagnosis.Contracts.Scope;
import br.com.zaya.engineering.nexo.investigation.ScopeRegistry;
import br.com.zaya.engineering.nexo.investigation.InvestigationService;
import br.com.zaya.engineering.nexo.monitoring.MonitorContracts.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import java.nio.file.*;
import java.nio.file.Path;
import java.time.Instant;
import java.util.*;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

/** Single-process local desk. One atomic snapshot contains event deduplication and issue updates. */
@ApplicationScoped
public class MonitorService {
    @Inject ObjectMapper mapper;
    @Inject ScopeRegistry scopes;
    @Inject InvestigationService investigations;
    @ConfigProperty(name="nexo.storage") String storage;

    public synchronized EventReceipt receive(Scope scope, FailureEvent event) {
        validate(event);
        String safeLog=event.logExcerpt()==null?null:br.com.zaya.engineering.nexo.investigation.LogExcerptFilter.normalize(
                new br.com.zaya.engineering.nexo.diagnosis.Contracts.LogExcerpt("FOUND","TEXT_MATCH_UNVERIFIED",0,true,event.logExcerpt())).content();
        var path=path(scope);
        if(event.investigationId()!=null) investigations.find(scope,event.investigationId());
        var state=read(path);
        String digest=hash(json(event));
        var prior=state.events().get(event.eventId());
        if(prior!=null) {
            if(!prior.digest().equals(digest)) throw new ClientErrorException("Event ID reused with different data",409);
            var issue=state.issues().get(prior.issueId());
            return new EventReceipt(issue.id(),true,issue.severity(),issue.workOrderId());
        }
        if(event.occurredAt().isAfter(Instant.now().plusSeconds(60))
                ||(event.origin()==Origin.LIVE&&event.occurredAt().isBefore(Instant.now().minusSeconds(86400))))
            throw new BadRequestException("Event timestamp outside allowed window");
        if(state.events().size()>=10000) throw new ServiceUnavailableException("Local desk capacity reached; archive with a reviewed retention procedure");
        String fingerprint=hash(event.component()+"\n"+event.version()+"\n"+event.category()+"\n"+event.exceptionType());
        var previous=state.issues().values().stream().filter(i->i.fingerprint().equals(fingerprint)).findFirst().orElse(null);
        if(previous==null && state.issues().size()>=500) throw new ServiceUnavailableException("Local issue capacity reached");
        var refs=new ArrayList<String>(previous==null?List.of():previous.investigationIds());
        if(event.investigationId()!=null && !refs.contains(event.investigationId())) {
            if(refs.size()>=100) throw new ServiceUnavailableException("Issue evidence capacity reached");
            refs.add(event.investigationId());
        }
        long count=previous==null?1:previous.occurrences()+1;
        long historical=(previous==null?0:previous.historicalOccurrences())+(event.origin()==Origin.HISTORICAL?1:0);
        boolean reopen=previous!=null && previous.status()==Status.RESOLVIDA && event.origin()==Origin.LIVE
                && event.occurredAt().isAfter(previous.audit().getLast().at());
        Instant recentThreshold=Instant.now().minusSeconds(900);
        long recentLive=previous==null?0:state.events().values().stream().filter(index->index.issueId().equals(previous.id())
                &&index.origin()==Origin.LIVE&&index.occurredAt()!=null&&!index.occurredAt().isBefore(recentThreshold)).count();
        if(event.origin()==Origin.LIVE&&!event.occurredAt().isBefore(recentThreshold))recentLive++;
        Severity proposed=event.impact()==Impact.RECOVERED?Severity.BAIXA:
                event.impact()==Impact.PROCESS_OUTAGE || recentLive>=10?Severity.ALTA:Severity.MEDIA;
        Severity severity=previous!=null && previous.severityReviewed()?previous.severity():
                previous!=null && previous.severity().ordinal()>proposed.ordinal()?previous.severity():proposed;
        String reason=previous!=null && previous.severityReviewed()?previous.severityReason():
                "Classificação provisória por impacto reportado e recorrência; indisponibilidade e abrangência não verificadas. Crítica exige revisão humana.";
        var audit=new ArrayList<Audit>(previous==null?List.of():previous.audit());
        if(previous==null) audit.add(new Audit(Instant.now(),"collector","ABERTA","Falha recebida; origem="+event.origin()));
        if(reopen) audit.add(new Audit(Instant.now(),"collector","REABERTA","Nova falha após a resolução"));
        if(audit.size()>200) throw new ServiceUnavailableException("Issue audit capacity reached");
        String id=previous==null?UUID.randomUUID().toString():previous.id();
        var issue=new Issue(id,fingerprint,event.component(),event.version(),event.category(),event.exceptionType(),
                previous==null||event.occurredAt().isBefore(previous.firstSeen())?event.occurredAt():previous.firstSeen(),
                previous==null||event.occurredAt().isAfter(previous.lastSeen())?event.occurredAt():previous.lastSeen(),
                count,historical,severity,reason,previous!=null&&previous.severityReviewed(),
                previous==null||reopen?Status.ABERTA:previous.status(),previous==null?"OS-"+id:previous.workOrderId(),
                previous==null?"Não atribuído":previous.owner(),previous==null?"":previous.proposal(),
                previous==null||reopen?"":previous.validation(),List.copyOf(refs),List.copyOf(audit),
                safeLog!=null?safeLog:previous==null?null:previous.lastLogExcerpt());
        state.issues().put(id,issue);state.events().put(event.eventId(),new EventIndex(digest,id,event.occurredAt(),event.origin()));write(path,state);
        return new EventReceipt(id,false,severity,issue.workOrderId());
    }
    public synchronized List<Issue> list(Scope scope,int offset) {
        if(offset<0||offset>10000) throw new BadRequestException("Invalid offset");
        return read(path(scope)).issues().values().stream().sorted(Comparator.comparing(Issue::lastSeen).reversed())
                .skip(offset).limit(100).toList();
    }
    public synchronized Issue find(Scope scope,String id) {
        var issue=read(path(scope)).issues().get(id);if(issue==null)throw new NotFoundException();return issue;
    }
    public synchronized Issue triage(Scope scope,String id,String actor,Triage update) {
        if(update==null||update.severity()==null||update.status()==null||!text(update.owner(),100,true)
                ||!text(update.reason(),1000,true)||!text(update.proposal(),4000,false)||!text(update.validation(),4000,false))
            throw new BadRequestException("Owner, reason and bounded proposal/validation required");
        if((update.status()==Status.CORRECAO_PROPOSTA||update.status()==Status.EM_VALIDACAO||update.status()==Status.RESOLVIDA)
                && update.proposal().isBlank()) throw new BadRequestException("Correction proposal required");
        if(update.status()==Status.RESOLVIDA&&update.validation().isBlank())throw new BadRequestException("Validation evidence required to resolve");
        var path=path(scope);var state=read(path);var old=state.issues().get(id);if(old==null)throw new NotFoundException();
        if(old.audit().size()>=200)throw new ServiceUnavailableException("Issue audit capacity reached");
        var audit=new ArrayList<>(old.audit());audit.add(new Audit(Instant.now(),actor,update.status().name(),update.reason()));
        var issue=new Issue(old.id(),old.fingerprint(),old.component(),old.version(),old.category(),old.exceptionType(),
                old.firstSeen(),old.lastSeen(),old.occurrences(),old.historicalOccurrences(),update.severity(),update.reason(),true,
                update.status(),old.workOrderId(),update.owner(),update.proposal(),update.validation(),old.investigationIds(),List.copyOf(audit),old.lastLogExcerpt());
        state.issues().put(id,issue);write(path,state);return issue;
    }
    private Path path(Scope scope) {
        if(!scopes.authorized(scope))throw new ForbiddenException();
        return Path.of(storage).resolve(scope.tenant()).resolve(scope.environment()).resolve("monitoring/state.json");
    }
    private Store read(Path path) {
        try {if(!Files.exists(path))return new Store(new LinkedHashMap<>(),new LinkedHashMap<>());
            if(Files.size(path)>8*1024*1024)throw new ServiceUnavailableException("Local desk capacity reached");
            return mapper.readValue(path.toFile(),Store.class);
        }catch(java.io.IOException e){throw new InternalServerErrorException("Issue storage read failed");}
    }
    private void write(Path path,Store state) {
        try {byte[] bytes=mapper.writeValueAsBytes(state);if(bytes.length>8*1024*1024)throw new ServiceUnavailableException("Local desk capacity reached");
            Files.createDirectories(path.getParent());var temporary=Files.createTempFile(path.getParent(),"issues-",".tmp");
            try {Files.write(temporary,bytes);Files.move(temporary,path,StandardCopyOption.REPLACE_EXISTING,StandardCopyOption.ATOMIC_MOVE);}
            finally {Files.deleteIfExists(temporary);}
        }catch(java.io.IOException e){throw new InternalServerErrorException("Issue persistence failed");}
    }
    private void validate(FailureEvent e) {
        if(e==null||!uuid(e.eventId())||!uuid(e.executionId())||!token(e.component(),160)||!token(e.version(),80)
                ||!token(e.category(),80)||!token(e.exceptionType(),200)||e.occurredAt()==null||e.impact()==null||e.origin()==null
                ||(e.investigationId()!=null&&!uuid(e.investigationId()))||e.logExcerpt()!=null&&e.logExcerpt().length()>2400)throw new BadRequestException("Invalid bounded failure event");
    }
    private boolean token(String s,int max){return s!=null&&s.length()<=max&&s.matches("[A-Za-z0-9_.$:+-]+");}
    private boolean uuid(String s){try{return s!=null&&UUID.fromString(s).toString().equals(s);}catch(IllegalArgumentException e){return false;}}
    private boolean text(String s,int max,boolean required){return s!=null&&s.length()<=max&&(!required||!s.isBlank());}
    private String json(FailureEvent value){try{
        // Preserve hashes of events accepted before optional logExcerpt existed.
        var node=(com.fasterxml.jackson.databind.node.ObjectNode)mapper.valueToTree(value);
        if(value.logExcerpt()==null)node.remove("logExcerpt");
        return mapper.writeValueAsString(node);
    }catch(java.io.IOException e){throw new InternalServerErrorException("Event serialization failed");}}
    private String hash(String value){try{return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8)));}
        catch(java.security.NoSuchAlgorithmException e){throw new IllegalStateException(e);}}
}
