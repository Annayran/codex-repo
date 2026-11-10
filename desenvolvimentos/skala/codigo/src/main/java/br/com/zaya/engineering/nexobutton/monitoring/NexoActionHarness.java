package br.com.zaya.engineering.nexobutton.monitoring;

import br.com.sankhya.extensions.actionbutton.ContextoAcao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

/** Wraps an existing production action without changing its business implementation. */
public final class NexoActionHarness {
    private static final String TEST_VERSION = "NEXO-FAULT-TEST";

    private NexoActionHarness() {
    }

    public static void execute(
            Object target,
            ContextoAcao context,
            String productionMethod,
            String component
    ) throws Exception {
        try {
            NexoTestFaults.begin(context, component);
            NexoTestFaults.beforeExecution();
            Method method = findMethod(target.getClass(), productionMethod);
            method.setAccessible(true);
            method.invoke(target, context);
        } catch (InvocationTargetException invocationFailure) {
            rethrowAfterCapture(invocationFailure.getCause(), component);
        } catch (Throwable failure) {
            rethrowAfterCapture(failure, component);
        } finally {
            NexoTestFaults.clear();
        }
    }

    private static Method findMethod(Class<?> type, String name) throws NoSuchMethodException {
        Class<?> current = type;
        while (current != null) {
            try {
                return current.getDeclaredMethod(name, ContextoAcao.class);
            } catch (NoSuchMethodException ignored) {
                current = current.getSuperclass();
            }
        }
        throw new NoSuchMethodException(name);
    }

    private static void rethrowAfterCapture(Throwable failure, String component) throws Exception {
        NexoTestFaults.logObservedFailure(failure, component);
        FailureTelemetry.reportSafely(
                failure,
                component,
                TEST_VERSION,
                UUID.randomUUID().toString()
        );

        if (failure instanceof Exception) {
            throw (Exception) failure;
        }
        if (failure instanceof Error) {
            throw (Error) failure;
        }
        throw new IllegalStateException("Falha nao recuperavel na action instrumentada.", failure);
    }
}
