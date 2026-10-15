package br.com.zaya.engineering.nexobutton;
import java.time.Instant;
import java.util.Properties;
public final class TemporarySessionChecks {
    public static void main(String[] args) throws Exception {
        Instant expected=Instant.parse("2026-10-09T19:27:14.318975900Z");
        if (!TemporarySessionProvider.expiration("2026-10-09T19:27:14.3189759+00:00").equals(expected)
                || !TemporarySessionProvider.expiration("2026-10-09T16:27:14.3189759-03:00").equals(expected)
                || !TemporarySessionProvider.expiration("2026-10-09T19:27:14.3189759Z").equals(expected))
            throw new AssertionError("Explicit offsets and seven fractional digits must preserve expiry");
        Properties settings=new Properties();
        settings.setProperty("endpoint","https://example.invalid/investigations/packages");
        settings.setProperty("principal","synthetic-trial");
        settings.setProperty("password","synthetic-not-a-secret");
        settings.setProperty("erpVersion","unverified-test");
        settings.setProperty("expiresAt",Instant.now().plusSeconds(60).toString());
        if (!TemporarySessionProvider.from(settings).principal.equals("synthetic-trial")) throw new AssertionError("Binding missing");
        settings.setProperty("expiresAt",Instant.now().minusSeconds(1).toString());
        expectFailure(settings);
        settings.setProperty("expiresAt",Instant.now().plusSeconds(60).toString());
        settings.remove("password");
        expectFailure(settings);
        System.out.println("Temporary session: provisioning, expired credential rejection and missing credential rejection passed.");
    }
    private static void expectFailure(Properties settings) {
        try { TemporarySessionProvider.from(settings); }
        catch (IllegalStateException expected) { return; }
        throw new AssertionError("Expected rejection");
    }
}
