package br.com.zaya.teleworld.fornecedorxml.shared.utils;

public final class DocumentoUtils {
    private DocumentoUtils() { }

    public static String somenteDigitos(Object valor) {
        if (valor == null) return null;
        String normalizado = valor.toString().replaceAll("[^0-9]", "");
        return normalizado.isEmpty() ? null : normalizado;
    }

    public static String texto(Object valor) {
        if (valor == null) return null;
        String texto = valor.toString().trim();
        return texto.isEmpty() ? null : texto;
    }

    public static String limitar(String valor, int tamanho) {
        return valor == null || valor.length() <= tamanho ? valor : valor.substring(0, tamanho);
    }

    public static String mascarar(String documento) {
        String digitos = somenteDigitos(documento);
        if (digitos == null || digitos.length() <= 4) return "****";
        return "**********" + digitos.substring(digitos.length() - 4);
    }
}
