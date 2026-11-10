import br.com.zaya.mahospitalar.genesis.domain.CandidatoGenesis;
import br.com.zaya.mahospitalar.genesis.service.GenesisSyncService;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

/** Checks the isolated release JAR rather than the current M1/M3 classes. */
public final class VerifyGenesisModeloHotfix {
    public static void main(String[] args) throws Exception {
        Method guard = GenesisSyncService.class.getDeclaredMethod("validarTipoProduto", CandidatoGenesis.class);
        guard.setAccessible(true);
        CandidatoGenesis c = new CandidatoGenesis();
        c.tipoProduto = "M1";
        guard.invoke(null, c);
        c.tipoProduto = "M3";
        try {
            guard.invoke(null, c);
            throw new AssertionError("Hotfix must not enable M3.");
        } catch (InvocationTargetException expected) {
            if (!(expected.getCause() instanceof IllegalStateException)) throw expected;
        }
        System.out.println("PASS: isolated JAR accepts M1 and rejects M3.");
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        org.junit.platform.launcher.Launcher launcher = LauncherFactory.create();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(LauncherDiscoveryRequestBuilder.request()
                .selectors(DiscoverySelectors.selectClass("br.com.zaya.mahospitalar.genesis.service.MigracaoModeloGenesisServiceTest"),
                        DiscoverySelectors.selectClass("br.com.zaya.mahospitalar.genesis.repository.MigracaoModeloFonteTest"))
                .build());
        listener.getSummary().printTo(new PrintWriter(System.out));
        listener.getSummary().printFailuresTo(new PrintWriter(System.out));
        if (listener.getSummary().getTestsFoundCount() != 18 || listener.getSummary().getTotalFailureCount() != 0)
            throw new AssertionError("Isolated migration tests failed or coverage changed.");
    }
}
