/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  br.com.sankhya.jape.dao.JdbcWrapper
 *  br.com.sankhya.jape.sql.NativeSql
 */
package org.ImportadorPedido.repository;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository {
    private final JdbcWrapper jdbc;

    public ProdutoRepository(JdbcWrapper jdbc) {
        this.jdbc = jdbc;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ProdutoInfo buscarPorCod(BigDecimal codProd) throws Exception {
        if (codProd == null) {
            return null;
        }
        NativeSql sql = new NativeSql(this.jdbc);
        sql.setReuseStatements(false);
        try (ResultSet rs = sql.executeQuery("SELECT CODPROD, REFERENCIA FROM TGFPRO WHERE CODPROD = " + codProd)) {
            if (rs.next()) {
                ProdutoInfo produtoInfo = new ProdutoInfo(rs.getBigDecimal("CODPROD"), rs.getString("REFERENCIA"));
                return produtoInfo;
            }
            ProdutoInfo produtoInfo = null;
            return produtoInfo;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<ProdutoInfo> buscarPorReferenciaNormalizada(String referencia) throws Exception {
        ArrayList<ProdutoInfo> encontrados = new ArrayList<ProdutoInfo>();
        if (referencia == null || referencia.trim().isEmpty()) {
            return encontrados;
        }
        String referenciaEscapada = this.escapeSqlLiteral(referencia.trim());
        NativeSql sql = new NativeSql(this.jdbc);
        sql.setReuseStatements(false);
        try (ResultSet rs = sql.executeQuery("SELECT CODPROD, REFERENCIA FROM TGFPRO WHERE UPPER(TRIM(REFERENCIA)) = UPPER(TRIM('" + referenciaEscapada + "')) ORDER BY CODPROD")) {
            while (rs.next()) {
                encontrados.add(new ProdutoInfo(rs.getBigDecimal("CODPROD"), rs.getString("REFERENCIA")));
            }
            ArrayList<ProdutoInfo> arrayList = encontrados;
            return arrayList;
        }
    }

    private String escapeSqlLiteral(String value) {
        return value == null ? "" : value.replace("'", "''");
    }

    public static class ProdutoInfo {
        private final BigDecimal codProd;
        private final String referencia;

        public ProdutoInfo(BigDecimal codProd, String referencia) {
            this.codProd = codProd;
            this.referencia = referencia;
        }

        public BigDecimal getCodProd() {
            return this.codProd;
        }

        public String getReferencia() {
            return this.referencia;
        }
    }
}

