package br.com.zaya.proauto.compensacao;

import java.math.BigDecimal;

public interface CompensacaoGateway {
    BigDecimal compensar(BigDecimal credito, BigDecimal receita, BigDecimal valor) throws Exception;
}
