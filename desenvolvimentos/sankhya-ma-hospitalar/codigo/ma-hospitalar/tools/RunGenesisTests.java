import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.TagFilter;
import org.junit.platform.engine.discovery.ClassNameFilter;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import java.io.PrintWriter;

/** Offline fallback when Gradle cannot open its loopback transport. */
public final class RunGenesisTests {
    public static void main(String[] args) {
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        org.junit.platform.launcher.Launcher launcher = LauncherFactory.create();
        launcher.registerTestExecutionListeners(listener);
        LauncherDiscoveryRequestBuilder request = LauncherDiscoveryRequestBuilder.request()
                .selectors(DiscoverySelectors.selectPackage("br.com.zaya.mahospitalar.genesis"))
                .filters(TagFilter.excludeTags("smoke"));
        if (args.length > 0 && "unit-only".equals(args[0])) {
            request.filters(ClassNameFilter.excludeClassNamePatterns(".*GenesisHttpClientTest"));
            System.out.println("CANDIDATE ONLY: HTTP loopback tests excluded; environment validation required.");
        }
        launcher.execute(request.build());
        listener.getSummary().printTo(new PrintWriter(System.out));
        listener.getSummary().printFailuresTo(new PrintWriter(System.out));
        if (listener.getSummary().getTestsFoundCount()==0 || listener.getSummary().getTotalFailureCount()>0)
            System.exit(1);
    }
}
