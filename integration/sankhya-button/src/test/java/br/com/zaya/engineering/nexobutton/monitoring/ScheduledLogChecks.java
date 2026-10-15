package br.com.zaya.engineering.nexobutton.monitoring;

import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class ScheduledLogChecks {
    private static Path root;
    private static final class Fixture {
        final Path log,checkpoint;final List<IncrementalLogCollector.LogEvent> events=new ArrayList<IncrementalLogCollector.LogEvent>();
        Fixture()throws Exception {this("ERROR old error before activation\n");}
        Fixture(String initial)throws Exception {
            Path dir=Files.createDirectory(root.resolve(UUID.randomUUID().toString()));log=dir.resolve("server.log");checkpoint=dir.resolve("state/checkpoint.json");
            Files.write(log,initial.getBytes(StandardCharsets.UTF_8));tick(false);
        }
        void append(String text)throws Exception{Files.write(log,text.getBytes(StandardCharsets.UTF_8),StandardOpenOption.APPEND);}
        IncrementalLogCollector.Result tick(final boolean fail)throws Exception {
            return new IncrementalLogCollector().tick(log,checkpoint,"test-identity",new IncrementalLogCollector.Delivery(){
                public void send(IncrementalLogCollector.LogEvent e)throws Exception{if(fail)throw new java.io.IOException("offline");events.add(e);}});
        }
    }
    private static void require(boolean value,String message){if(!value)throw new AssertionError(message);}
    public static void main(String[] args)throws Exception {
        root=Paths.get(System.getProperty("nexo.test.dir"));Files.createDirectories(root);
        Fixture f=new Fixture();require(f.events.isEmpty(),"Initial history must be skipped");
        f.append("2026-10-15 15:00:00,000 INFO normal\n2026-10-15 15:00:01,000 WARN harmless\nORA-20101: warning context\n"
                +"2026-10-15 15:00:02,000 ERROR Produto 19391 não está ativo\njava.sql.SQLException: ORA-20101: bloqueado\npassword=secret\nuser@example.org 123.456.789-00\n"
                +"2026-10-15 15:00:03,000 INFO done\n");
        require(f.tick(false).bytesRead<=65536,"Read budget");require(f.events.size()==1,"Only ERROR record, no WARN or initial history");
        require("PRODUCT_INACTIVE".equals(f.events.get(0).category),"Category");
        require(!f.events.get(0).logExcerpt.contains("secret")&&!f.events.get(0).logExcerpt.contains("user@example.org")
                &&!f.events.get(0).logExcerpt.contains("123.456.789-00"),"Redaction");f.tick(false);require(f.events.size()==1,"No repeat on quiet poll");

        Fixture partial=new Fixture();partial.append("ERROR Produto não está ati");partial.tick(false);require(partial.events.isEmpty(),"Partial line");
        partial.append("vo\njava.lang.IllegalStateException: fail\n");partial.tick(false);require(partial.events.isEmpty(),"Keep open record for continuation");
        partial.tick(false);require(partial.events.size()==1,"Quiet record flushed");
        Fixture baselinePartial=new Fixture("ERROR old partial");baselinePartial.append(" tail\nERROR new failure\nINFO end\n");baselinePartial.tick(false);
        require(baselinePartial.events.size()==1&&!baselinePartial.events.get(0).logExcerpt.contains("old partial"),"Initial partial record skipped");
        try(java.nio.channels.FileChannel channel=java.nio.channels.FileChannel.open(partial.checkpoint.resolveSibling("checkpoint.json.lock"),StandardOpenOption.WRITE);
            java.nio.channels.FileLock lock=channel.lock()) {require(partial.tick(false).overlap,"Concurrent invocation skips same checkpoint");}

        Fixture offline=new Fixture();offline.append("ERROR quantidade entregue inválida\nINFO end\n");
        require(offline.tick(true).pending==1,"Persistent pending queue");String pending=new String(Files.readAllBytes(offline.checkpoint),StandardCharsets.UTF_8);
        require(pending.contains("DELIVERED_QTY"),"Pending checkpoint");offline.tick(false);require(offline.events.size()==1,"Restart retries pending");
        offline.tick(false);require(offline.events.size()==1,"Acknowledged event not repeated");
        boolean wrongTarget=false;try{new IncrementalLogCollector().tick(offline.log,offline.checkpoint,"other-identity",e->{throw new AssertionError("Wrong recipient");});}
        catch(java.io.IOException expected){wrongTarget=true;}require(wrongTarget,"Identity change must stop collector");

        Fixture rotated=new Fixture();rotated.append("ERROR alpha failure\nINFO done\n");rotated.tick(false);
        Files.move(rotated.log,rotated.log.resolveSibling("server.log.1"));Files.write(rotated.log,"ERROR bravo failure\nINFO end\n".getBytes(StandardCharsets.UTF_8));
        require(rotated.tick(false).rotations==1,"Rename rotation");require(rotated.events.size()==2,"Rotated file new failure");
        Files.write(rotated.log,"ERROR delta failure\nINFO end\n".getBytes(StandardCharsets.UTF_8));
        require(rotated.tick(false).rotations==2,"Same-length rewrite detected by anchor");require(rotated.events.size()==3,"Rewritten file read");

        Fixture huge=new Fixture();char[] chars=new char[100000];Arrays.fill(chars,'x');huge.append(new String(chars)+"\nERROR java.lang.RuntimeException: bounded\nINFO end\n");
        require(huge.tick(false).bytesRead<=65536,"Long line bounded");huge.tick(false);require(huge.events.size()==1,"Discard long line and resume");

        Fixture queued=new Fixture();for(int i=0;i<25;i++)queued.append("ERROR failure "+i+"\n");queued.append("INFO end\n");
        require(queued.tick(true).pending==10,"Queue limit and source backpressure");
        for(int i=0;i<12;i++)queued.tick(false);
        Set<String> ids=new HashSet<String>();for(IncrementalLogCollector.LogEvent e:queued.events)ids.add(e.eventId);
        require(queued.events.size()==25&&ids.size()==25,"Drain backlog without lost or repeated records");

        Fixture self=new Fixture();self.append("ERROR collector issue\nat br.com.zaya.engineering.nexobutton.monitoring.ScheduledLogMonitor.onTime\nINFO end\n");
        self.tick(false);require(self.events.isEmpty(),"Self feedback suppressed");
        Files.write(self.checkpoint,"{bad".getBytes(StandardCharsets.UTF_8));boolean corrupt=false;
        try{self.tick(false);}catch(Exception expected){corrupt=true;}require(corrupt,"Corrupt state must not silently reset cursor");
        System.out.println("Scheduled log checks PASS: baseline, ERROR filter, partial lines, durable retry, identity, rotation, bounded reads, backpressure, redaction, self-noise");
    }
}
