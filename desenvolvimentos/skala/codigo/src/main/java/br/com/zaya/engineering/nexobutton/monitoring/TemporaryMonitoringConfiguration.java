package br.com.zaya.engineering.nexobutton.monitoring;

import java.util.Properties;
import java.io.InputStream;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.Instant;

/** Optional expiring homologation resource; ordinary artifact contains no identity. */
public final class TemporaryMonitoringConfiguration implements HttpFailureSink.ProtectedConfiguration {
    public HttpFailureSink.Configuration load() throws Exception {
        Properties values=new Properties();
        try(InputStream input=getClass().getResourceAsStream("/nexo-temporary-session.properties")) {
            if(input==null)return null;values.load(input);
        }
        if(!OffsetDateTime.parse(values.getProperty("expiresAt")).toInstant().isAfter(Instant.now()))return null;
        URI receiver=URI.create(values.getProperty("endpoint"));
        if(!"https".equals(receiver.getScheme())||receiver.getUserInfo()!=null||receiver.getHost()==null)return null;
        String endpoint=new URI("https",null,receiver.getHost(),receiver.getPort(),"/monitoring/events",null,null).toString();
        return new HttpFailureSink.Configuration(endpoint,values.getProperty("principal"),values.getProperty("password"));
    }
}
