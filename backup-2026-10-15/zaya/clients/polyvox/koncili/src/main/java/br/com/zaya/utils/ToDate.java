//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package br.com.zaya.utils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.logging.Logger;

public class ToDate {
    private static final Logger LOG = Logger.getLogger(ToDate.class.getName());

    public Timestamp toTimestamp(String data, DateTimeFormatter formatter) {
        if (data == null || data.trim().isEmpty()) {
            return null;
        }

        String valor = data.trim();

        try {
            TemporalAccessor parsed = formatter.parseBest(valor, LocalDateTime::from, LocalDate::from);
            if (parsed instanceof LocalDateTime) {
                return Timestamp.valueOf((LocalDateTime) parsed);
            }
            return Timestamp.valueOf(((LocalDate) parsed).atStartOfDay());
        } catch (DateTimeParseException e) {
            String normalizado = valor.replace('T', ' ');
            int idxPonto = normalizado.indexOf('.');
            if (idxPonto > 0) {
                normalizado = normalizado.substring(0, idxPonto);
            }
            if (normalizado.endsWith("Z")) {
                normalizado = normalizado.substring(0, normalizado.length() - 1);
            }

            try {
                return Timestamp.valueOf(normalizado);
            } catch (IllegalArgumentException ex) {
                LOG.warning(String.format("[TO_DATE_INVALIDO] Nao foi possivel converter data | valor=%s erro=%s", data, ex.getMessage()));
                return null;
            }
        }
    }
}
