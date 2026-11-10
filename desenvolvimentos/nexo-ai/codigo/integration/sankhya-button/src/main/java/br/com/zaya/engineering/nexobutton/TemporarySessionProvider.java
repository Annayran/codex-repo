package br.com.zaya.engineering.nexobutton;

import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import java.io.InputStream;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

/** Individual, short-lived fixture credential. Not a production secret store. */
public final class TemporarySessionProvider implements ButtonConfigurationProvider {
    public Configuration load(ContextoAcao context) throws Exception {
        Properties settings = new Properties();
        try (InputStream input = getClass().getResourceAsStream("/nexo-temporary-session.properties")) {
            if (input == null) throw new IllegalStateException("Pacote temporário não provisionado.");
            settings.load(input);
        }
        return from(settings);
    }
    static Configuration from(Properties settings) {
        if (!Instant.now().isBefore(expiration(required(settings, "expiresAt"))))
            throw new IllegalStateException("Sessão de homologação Nexo expirada. Solicite novo pacote à Zaya.");
        return new Configuration(required(settings, "endpoint"), required(settings, "principal"),
                required(settings, "password"), required(settings, "erpVersion"));
    }
    static Instant expiration(String value) {
        // Java 8 ISO_INSTANT parsing differs from newer JDKs for explicit offsets.
        return OffsetDateTime.parse(value, DateTimeFormatter.ISO_OFFSET_DATE_TIME).toInstant();
    }
    private static String required(Properties settings, String key) {
        String value = settings.getProperty(key);
        if (value == null || value.trim().isEmpty()) throw new IllegalStateException("Pacote temporário incompleto.");
        return value;
    }
}
