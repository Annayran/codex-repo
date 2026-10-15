package org.ImportadorPedido.repository;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository {

    public static class ProdutoInfo {
        private final BigDecimal codProd;
        private final String referencia;

        public ProdutoInfo(BigDecimal codProd, String referencia) {
            this.codProd = codProd;
            this.referencia = referencia;
        }

        public BigDecimal getCodProd() {
            return codProd;
        }

        public String getReferencia() {
            return referencia;
        }
    }

    private final JdbcWrapper jdbc;

    public ProdutoRepository(JdbcWrapper jdbc) {
        this.jdbc = jdbc;
    }

    public ProdutoInfo buscarPorCod(BigDecimal codProd) throws Exception {
        if (codProd == null) {
            return null;
        }

        NativeSql sql = new NativeSql(jdbc);
        sql.setReuseStatements(false);
        ResultSet rs = null;

        try {
            rs = sql.executeQuery(
                    "SELECT CODPROD, REFERENCIA FROM TGFPRO WHERE CODPROD = " + codProd
            );
            if (rs.next()) {
                return new ProdutoInfo(rs.getBigDecimal("CODPROD"), rs.getString("REFERENCIA"));
            }
            return null;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    public List<ProdutoInfo> buscarPorReferenciaNormalizada(String referencia) throws Exception {
        List<ProdutoInfo> encontrados = new ArrayList<>();
        if (referencia == null || referencia.trim().isEmpty()) {
            return encontrados;
        }

        String referenciaEscapada = escapeSqlLiteral(referencia.trim());
        NativeSql sql = new NativeSql(jdbc);
        sql.setReuseStatements(false);
        ResultSet rs = null;

        try {
            rs = sql.executeQuery(
                    "SELECT CODPROD, REFERENCIA FROM TGFPRO " +
                            "WHERE UPPER(TRIM(REFERENCIA)) = UPPER(TRIM('" + referenciaEscapada + "')) " +
                            "ORDER BY CODPROD"
            );

            while (rs.next()) {
                encontrados.add(new ProdutoInfo(rs.getBigDecimal("CODPROD"), rs.getString("REFERENCIA")));
            }

            return encontrados;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    private String escapeSqlLiteral(String value) {
        return value == null ? "" : value.replace("'", "''");
    }
}

