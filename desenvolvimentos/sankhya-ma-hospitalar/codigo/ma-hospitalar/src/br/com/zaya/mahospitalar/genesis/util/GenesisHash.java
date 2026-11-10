package br.com.zaya.mahospitalar.genesis.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public final class GenesisHash {
    private GenesisHash() {}

    public static String sha256(String value) {
        try {
            byte[] bytes = MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8));
            StringBuilder result = new StringBuilder(64);
            for (byte b : bytes) result.append(String.format("%02x", b & 0xff));
            return result.toString();
        } catch (Exception e) {
            throw new IllegalStateException("Nao foi possivel gerar hash.", e);
        }
    }
}
