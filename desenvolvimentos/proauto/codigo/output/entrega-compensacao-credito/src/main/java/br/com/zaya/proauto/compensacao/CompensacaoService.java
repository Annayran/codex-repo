package br.com.zaya.proauto.compensacao;

import java.math.BigDecimal;

public final class CompensacaoService {
    private final CompensacaoGateway gateway;

    public CompensacaoService(CompensacaoGateway gateway) {
        if (gateway == null) { throw new IllegalArgumentException("Gateway obrigatorio."); }
        this.gateway = gateway;
    }

    public BigDecimal executar(BigDecimal credito, BigDecimal receita,
                              BigDecimal saldoCredito, BigDecimal saldoReceita) throws Exception {
        if (credito == null || receita == null || credito.signum() <= 0 || receita.signum() <= 0
                || credito.compareTo(receita) == 0) {
            throw new IllegalArgumentException("Titulos invalidos para compensacao.");
        }
        BigDecimal valor = RegraCompensacao.valor(saldoCredito, saldoReceita);
        BigDecimal acerto = gateway.compensar(credito, receita, valor);
        if (acerto == null || acerto.signum() <= 0) {
            throw new IllegalStateException("Biblioteca nao retornou numero de acerto valido.");
        }
        return acerto;
    }
}
