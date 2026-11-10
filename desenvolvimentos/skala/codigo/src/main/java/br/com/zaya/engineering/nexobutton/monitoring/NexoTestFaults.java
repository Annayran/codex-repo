package br.com.zaya.engineering.nexobutton.monitoring;

import br.com.sankhya.extensions.actionbutton.ContextoAcao;

import java.util.Locale;

/** Controlled fault injection for the homologation-only Nexo test artifact. */
public final class NexoTestFaults {
    public static final String PARAM_ERROR = "NEXO_TESTE_ERRO";
    public static final String PARAM_CONFIRM = "NEXO_TESTE_CONFIRMAR";
    public static final String CONFIRM_VALUE = "FORCAR_ERRO_NEXO";

    private static final ThreadLocal<Scenario> ACTIVE = new ThreadLocal<Scenario>();

    private NexoTestFaults() {
    }

    public static void begin(ContextoAcao context, String component) {
        ACTIVE.remove();
        String confirmation = text(context.getParam(PARAM_CONFIRM));
        if (!CONFIRM_VALUE.equalsIgnoreCase(confirmation)) {
            return;
        }

        String code = text(context.getParam(PARAM_ERROR)).toUpperCase(Locale.ROOT);
        if (code.isEmpty()) {
            throw forced("CENARIO_NAO_INFORMADO", Stage.BEFORE,
                    "Falha controlada Nexo: cenario de erro nao informado.");
        }

        ACTIVE.set(scenario(code, component));
    }

    public static void beforeExecution() {
        throwAt(Stage.BEFORE);
    }

    public static void afterOes() {
        throwAt(Stage.AFTER_OES);
    }

    public static void clear() {
        ACTIVE.remove();
    }

    public static boolean isForced(Throwable failure) {
        Throwable current = failure;
        for (int depth = 0; depth < 8 && current != null; depth++) {
            if (current instanceof ForcedNexoTestException) {
                return true;
            }
            Throwable next = current.getCause();
            if (next == current) {
                break;
            }
            current = next;
        }
        return false;
    }

    public static void logObservedFailure(Throwable failure, String component) {
        String category = FailureTelemetry.category(failure);
        Throwable root = rootCause(failure);
        boolean forced = isForced(failure);
        System.err.println(
                "[NEXO_MONITORING_EVENT] component=" + safe(component)
                        + " category=" + safe(category)
                        + " exceptionType=" + root.getClass().getName()
                        + " forcedTest=" + forced
        );
        if (forced) {
            failure.printStackTrace(System.err);
        }
    }

    private static void throwAt(Stage stage) {
        Scenario scenario = ACTIVE.get();
        if (scenario != null && scenario.stage == stage) {
            throw forced(scenario.code, scenario.stage, scenario.message);
        }
    }

    private static Scenario scenario(String code, String component) {
        if ("PRODUTO_INATIVO".equals(code)) {
            return new Scenario(code, Stage.BEFORE,
                    "Produto nao esta ativo para uso no particionamento. [TESTE CONTROLADO NEXO]");
        }
        if ("QUANTIDADE_ENTREGUE".equals(code)) {
            return new Scenario(code, Stage.AFTER_OES,
                    "Quantidade entregue inconsistente durante o particionamento. [TESTE CONTROLADO NEXO]");
        }
        if ("COMBINACAO_GLOBAL".equals(code)) {
            return new Scenario(code, Stage.BEFORE,
                    "Nao foi possivel encontrar combinacao global para o particionamento. [TESTE CONTROLADO NEXO]");
        }
        if ("SEM_PARTICIONAMENTO_ATIVO".equals(code)) {
            return new Scenario(code, Stage.BEFORE,
                    "Nao foi encontrado particionamento ativo para a nota selecionada. [TESTE CONTROLADO NEXO]");
        }
        if ("NAO_CLASSIFICADO".equals(code)) {
            return new Scenario(code, Stage.BEFORE,
                    "Falha controlada nao classificada no componente " + safe(component) + ". [TESTE CONTROLADO NEXO]");
        }
        throw forced(code, Stage.BEFORE,
                "Falha controlada Nexo: cenario desconhecido. [TESTE CONTROLADO NEXO]");
    }

    private static ForcedNexoTestException forced(String code, Stage stage, String message) {
        return new ForcedNexoTestException(code, stage.name(), message);
    }

    private static Throwable rootCause(Throwable failure) {
        Throwable current = failure == null ? new IllegalStateException("Unknown failure") : failure;
        for (int depth = 0; depth < 8 && current.getCause() != null && current.getCause() != current; depth++) {
            current = current.getCause();
        }
        return current;
    }

    private static String text(Object value) {
        return value == null ? "" : String.valueOf(value).trim();
    }

    private static String safe(String value) {
        if (value == null) {
            return "UNKNOWN";
        }
        return value.replaceAll("[^A-Za-z0-9_.$-]", "_");
    }

    private enum Stage {
        BEFORE,
        AFTER_OES
    }

    private static final class Scenario {
        private final String code;
        private final Stage stage;
        private final String message;

        private Scenario(String code, Stage stage, String message) {
            this.code = code;
            this.stage = stage;
            this.message = message;
        }
    }

    public static final class ForcedNexoTestException extends IllegalStateException {
        private final String scenario;
        private final String stage;

        private ForcedNexoTestException(String scenario, String stage, String message) {
            super(message);
            this.scenario = scenario;
            this.stage = stage;
        }

        public String getScenario() {
            return scenario;
        }

        public String getStage() {
            return stage;
        }
    }
}
