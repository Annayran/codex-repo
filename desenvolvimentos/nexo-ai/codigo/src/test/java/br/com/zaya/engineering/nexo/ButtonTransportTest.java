package br.com.zaya.engineering.nexo;

import org.junit.jupiter.api.Test;
import javax.tools.ToolProvider;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

class ButtonTransportTest {
    @Test void java8TransportWorksWithoutErpSdk() throws Exception {
        Path output = Path.of("target/button-test-classes"); Files.createDirectories(output);
        String base = "integration/sankhya-button/src/";
        int result = ToolProvider.getSystemJavaCompiler().run(null,null,null,"--release","8","-encoding","UTF-8","-d",output.toString(),
                base+"main/java/br/com/zaya/engineering/nexobutton/NexoButtonClient.java",
                base+"main/java/br/com/zaya/engineering/nexobutton/MinimalPackage.java",
                base+"test/java/br/com/zaya/engineering/nexobutton/ButtonTransportChecks.java");
        assertEquals(0,result,"Standalone transport must compile to Java 8 bytecode");
        String java = Path.of(System.getProperty("java.home"),"bin","java.exe").toString();
        if (!Files.exists(Path.of(java))) java = Path.of(System.getProperty("java.home"),"bin","java").toString();
        var process = new ProcessBuilder(java,"-Djdk.net.unixdomain.tmpdir=target","-cp",output.toString(),
                "br.com.zaya.engineering.nexobutton.ButtonTransportChecks").redirectErrorStream(true)
                .redirectOutput(Path.of("target/button-transport-checks.log").toFile()).start();
        try {
            assertTrue(process.waitFor(20,TimeUnit.SECONDS),"Transport checks must complete within budget");
            assertEquals(0,process.exitValue(),Files.readString(Path.of("target/button-transport-checks.log")));
        } finally { if (process.isAlive()) process.destroyForcibly(); }
    }
}
