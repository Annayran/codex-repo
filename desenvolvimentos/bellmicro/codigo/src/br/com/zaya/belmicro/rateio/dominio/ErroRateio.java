package br.com.zaya.belmicro.rateio.dominio;

public final class ErroRateio extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final String codigo;

    public ErroRateio(String codigo, String mensagem) {
        super(mensagem);
        this.codigo = codigo;
    }

    public String getCodigo() { return codigo; }
}
