/*
 * Decompiled with CFR 0.152.
 */
package org.ImportadorPedido.helper;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ImportadorLogger {
    private ImportadorLogger() {
    }

    public static Map<String, Object> ctx(Object ... kvPairs) {
        LinkedHashMap<String, Object> context = new LinkedHashMap<String, Object>();
        if (kvPairs == null) {
            return context;
        }
        for (int i = 0; i < kvPairs.length - 1; i += 2) {
            String key = String.valueOf(kvPairs[i]);
            Object value = kvPairs[i + 1];
            context.put(key, value);
        }
        return context;
    }

    public static void info(Logger logger, String event, String message, Map<String, Object> context) {
        logger.info(ImportadorLogger.format(event, message, context));
    }

    public static void warn(Logger logger, String event, String message, Map<String, Object> context) {
        logger.warning(ImportadorLogger.format(event, message, context));
    }

    public static void error(Logger logger, String event, String message, Throwable throwable, Map<String, Object> context) {
        logger.log(Level.SEVERE, ImportadorLogger.format(event, message, context), throwable);
    }

    private static String format(String event, String message, Map<String, Object> context) {
        StringBuilder out = new StringBuilder();
        out.append("[").append(event).append("] ").append(message);
        if (context != null && !context.isEmpty()) {
            out.append(" | ");
            boolean first = true;
            for (Map.Entry<String, Object> entry : context.entrySet()) {
                if (!first) {
                    out.append(" ");
                }
                out.append(entry.getKey()).append("=").append(entry.getValue());
                first = false;
            }
        }
        return out.toString();
    }
}

