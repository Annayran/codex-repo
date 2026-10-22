package br.com.zaya.mahospitalar.genesis.client;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GenesisCooldown {
    private GenesisCooldown() { }

    public static long prazoPersistidoMs(String mensagem) {
        Matcher prazo = Pattern.compile("COOLDOWN_MS=([0-9]{1,15});")
                .matcher(mensagem == null ? "" : mensagem);
        return prazo.find() ? Math.max(60000L, Long.parseLong(prazo.group(1))) : 60000L;
    }

    public static long prazoMs(String retryAfter, String body, long agora) {
        long prazo = 60000L;
        if (retryAfter != null) {
            try {
                long segundos = Long.parseLong(retryAfter.trim());
                if (segundos > 0 && segundos <= Long.MAX_VALUE / 1000L)
                    prazo = Math.max(prazo, segundos * 1000L);
            } catch (Exception ignore) {
                try {
                    prazo = Math.max(prazo, ZonedDateTime.parse(retryAfter,
                            DateTimeFormatter.RFC_1123_DATE_TIME).toInstant().toEpochMilli() - agora);
                } catch (Exception ignored) { }
            }
        }
        Matcher mensagem = Pattern.compile("after\\s+(\\d{1,12})\\s+milliseconds", Pattern.CASE_INSENSITIVE)
                .matcher(body == null ? "" : body);
        if (mensagem.find()) prazo = Math.max(prazo, Long.parseLong(mensagem.group(1)));
        return prazo;
    }
}
