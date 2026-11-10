package br.com.zaya.topnutri.importacaoformulas;

import org.junit.platform.engine.DiscoverySelector;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

/**
 * Executor auxiliar para ambientes em que o daemon do Gradle esteja bloqueado.
 * O fluxo oficial do projeto continua sendo a task {@code test} do Gradle.
 */
public final class TestSuiteMain {
    private TestSuiteMain() {
    }

    public static void main(String[] args) {
        DiscoverySelector selector = selectPackage("br.com.zaya.topnutri.importacaoformulas");
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selector)
                .build();
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        Launcher launcher = LauncherFactory.create();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);

        listener.getSummary().printTo(new java.io.PrintWriter(System.out, true));
        if (listener.getSummary().getTotalFailureCount() > 0) {
            listener.getSummary().printFailuresTo(new java.io.PrintWriter(System.err, true));
            System.exit(1);
        }
    }
}
