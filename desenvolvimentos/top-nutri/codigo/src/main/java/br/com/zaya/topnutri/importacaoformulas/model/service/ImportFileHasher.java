package br.com.zaya.topnutri.importacaoformulas.model.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class ImportFileHasher {
    public String sha256(byte[] content) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256").digest(content);
            StringBuilder value = new StringBuilder(digest.length * 2);
            for (byte item : digest) {
                value.append(String.format("%02x", item & 0xff));
            }
            return value.toString();
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 indisponível no runtime Java.", exception);
        }
    }
}
