package br.com.zaya.mahospitalar.genesis.service;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.modelcore.comercial.ComercialUtils;
import java.math.BigDecimal;
import java.sql.Timestamp;

public final class EstoquePrecoSankhya {
    public Valores consultar(JdbcWrapper jdbc, BigDecimal codProd, Timestamp data) throws Exception {
        BigDecimal nuTab = ComercialUtils.getNumeroUnicoTabela(jdbc, data, new BigDecimal("101"));
        if (nuTab == null || nuTab.signum() <= 0)
            throw new IllegalStateException("Tabela 101 sem vigencia; envio de estoque bloqueado.");
        BigDecimal venda = ComercialUtils.obtemPreco2(jdbc, nuTab, codProd, data);
        if (venda == null || venda.signum() <= 0)
            throw new IllegalStateException("Produto sem preco positivo na tabela 101; revisar antes de enviar.");

        // The catalog sends selling price only; absent cost must not become zero.
        return new Valores(venda, null);
    }
    public static final class Valores {
        public final BigDecimal venda, custo;
        Valores(BigDecimal venda, BigDecimal custo) { this.venda = venda; this.custo = custo; }
    }
}
