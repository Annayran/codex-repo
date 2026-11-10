package br.com.zaya.mahospitalar.preco.repository;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.zaya.mahospitalar.preco.model.DocumentoMargemResultado;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Types;

public final class DocumentoMargemRepository {

    public boolean empresaHabilitada(JdbcWrapper jdbc, BigDecimal codEmp) throws Exception {
        NativeSql sql = new NativeSql(jdbc);
        ResultSet rs = null;
        try {
            sql.setNamedParameter("CODEMP", codEmp);
            rs = sql.executeQuery("SELECT 1 FROM AD_MAH_PD_CONFIG WHERE CODEMP = :CODEMP AND ATIVO = 'S'");
            return rs.next();
        } finally {
            if (rs != null) rs.close();
            NativeSql.releaseResources(sql);
        }
    }

    public DocumentoMargemResultado avaliar(JdbcWrapper jdbc, BigDecimal nuNota) throws Exception {
        CallableStatement call = null;
        try {
            call = jdbc.getConnection().prepareCall(
                    "{call PKG_MAH_PRECO_DIN.AVALIAR_DOCUMENTO(?,?,?,?,?,?,?)}"
            );
            call.setBigDecimal(1, nuNota);
            call.registerOutParameter(2, Types.NUMERIC);
            call.registerOutParameter(3, Types.NUMERIC);
            call.registerOutParameter(4, Types.NUMERIC);
            call.registerOutParameter(5, Types.NUMERIC);
            call.registerOutParameter(6, Types.VARCHAR);
            call.registerOutParameter(7, Types.NUMERIC);
            call.execute();

            return new DocumentoMargemResultado(
                    call.getBigDecimal(2),
                    call.getBigDecimal(3),
                    call.getBigDecimal(4),
                    call.getBigDecimal(5),
                    call.getString(6),
                    call.getInt(7)
            );
        } finally {
            if (call != null) call.close();
        }
    }
}
