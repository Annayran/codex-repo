package br.com.zaya.belmicro.rateio.infraestrutura.jdbc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

final class ConsultasRateio {
    private ConsultasRateio() { }
    static String ler(String nome) {
        try (InputStream entrada = ConsultasRateio.class.getResourceAsStream(nome)) {
            if (entrada == null) throw new IllegalStateException("Recurso SQL de rateio ausente.");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int lidos;
            while ((lidos = entrada.read(buffer)) != -1) bytes.write(buffer,0,lidos);
            return new String(bytes.toByteArray(),StandardCharsets.UTF_8);
        } catch (IOException e) { throw new IllegalStateException("Falha ao carregar recurso SQL de rateio."); }
    }
}
