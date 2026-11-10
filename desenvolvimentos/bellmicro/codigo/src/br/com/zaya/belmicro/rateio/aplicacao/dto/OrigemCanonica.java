package br.com.zaya.belmicro.rateio.aplicacao.dto;

import br.com.zaya.belmicro.rateio.dominio.ErroRateio;

public final class OrigemCanonica {
    public enum Tipo { NOTA, TITULO }
    private final Tipo tipo;
    private final long id;

    public OrigemCanonica(Tipo tipo, long id) {
        if (tipo == null || id <= 0) throw new ErroRateio("BASE_INCOMPLETA", "Origem invalida.");
        this.tipo = tipo;
        this.id = id;
    }
    public Tipo getTipo() { return tipo; }
    public long getId() { return id; }
    @Override public boolean equals(Object objeto) {
        if (!(objeto instanceof OrigemCanonica)) return false;
        OrigemCanonica outra = (OrigemCanonica) objeto;
        return tipo == outra.tipo && id == outra.id;
    }
    @Override public int hashCode() { return 31 * tipo.hashCode() + Long.valueOf(id).hashCode(); }
}
