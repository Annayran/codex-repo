package br.com.zaya.teleworld.fornecedorxml.shared.config;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import br.com.sankhya.modelcore.util.MGECoreParameter;

public final class FornecedorXmlConfig {
    private final Set<String> statuses;
    private final BigDecimal codPrn;
    private final BigDecimal codUsu;

    private FornecedorXmlConfig(Set<String> statuses, BigDecimal codPrn, BigDecimal codUsu) {
        this.statuses = statuses;
        this.codPrn = codPrn;
        this.codUsu = codUsu;
    }

    public static FornecedorXmlConfig carregar() throws Exception {
        return new FornecedorXmlConfig(
                listaObrigatoria("AD_STXMLFLOW"),
                decimalObrigatorio("AD_PRNFLOWFOR"),
                decimalObrigatorio("AD_USUFLOWFOR"));
    }

    public boolean aceitaStatus(String value) {
        return value != null && statuses.contains(value.trim().toUpperCase());
    }

    public boolean aceitaOrigem(String value) {
        return "MDE".equalsIgnoreCase(value) || "PORTAL_XML".equalsIgnoreCase(value);
    }

    public BigDecimal getCodPrn() { return codPrn; }
    public BigDecimal getCodUsu() { return codUsu; }
    public int getMaxTentativas() { return 3; }
    public int getLote() { return 20; }

    private static Set<String> listaObrigatoria(String chave) throws Exception {
        Set<String> valores = lista(obrigatorio(chave));
        if (valores.isEmpty()) throw new Exception("Parametro " + chave + " nao possui valores validos.");
        return valores;
    }

    private static Set<String> lista(String valor) {
        Set<String> resultado = new HashSet<String>();
        if (valor == null || valor.trim().isEmpty()) return resultado;
        for (String item : valor.split("[,;]")) {
            if (!item.trim().isEmpty()) resultado.add(item.trim().toUpperCase());
        }
        return resultado;
    }

    private static String obrigatorio(String chave) throws Exception {
        String valor = opcional(chave);
        if (valor == null || valor.trim().isEmpty()) {
            throw new Exception("Parametro obrigatorio nao configurado: " + chave + '.');
        }
        return valor.trim();
    }

    private static String opcional(String chave) throws Exception {
        Object valor = MGECoreParameter.getParameter(chave);
        return valor == null ? null : valor.toString();
    }

    private static BigDecimal decimalObrigatorio(String chave) throws Exception {
        return new BigDecimal(obrigatorio(chave));
    }

}
