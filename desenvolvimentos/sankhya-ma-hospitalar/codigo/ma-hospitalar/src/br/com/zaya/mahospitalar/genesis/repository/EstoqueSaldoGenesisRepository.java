package br.com.zaya.mahospitalar.genesis.repository;

import br.com.sankhya.jape.dao.JdbcWrapper;
import java.math.BigDecimal;
import java.sql.*;

public final class EstoqueSaldoGenesisRepository {
    static final String SQL = "SELECT E.TIPO,SUM(NVL(E.ESTOQUE,0)-NVL(E.RESERVADO,0)) DISPONIVEL " +
            "FROM TGFEST E WHERE E.CODPROD=? AND E.TIPO IN ('P','T') " +
            "AND EXISTS (SELECT 1 FROM AD_CENTPARAMEMP C WHERE C.NUPAR=1 AND C.CODEMP=E.CODEMP) " +
            "AND EXISTS (SELECT 1 FROM AD_CENTPARAMLOC C WHERE C.NUPAR=1 AND C.CODLOCAL=E.CODLOCAL) " +
            "GROUP BY E.TIPO";

    public void validarEscopo(JdbcWrapper jdbc) throws Exception {
        try(PreparedStatement ps=jdbc.getConnection().prepareStatement(
                "SELECT (SELECT COUNT(*) FROM AD_CENTPARAMEMP WHERE NUPAR=1) EMPRESAS," +
                "(SELECT COUNT(*) FROM AD_CENTPARAMLOC WHERE NUPAR=1) LOCAIS FROM DUAL");ResultSet rs=ps.executeQuery()) {
            if(!rs.next() || rs.getInt(1)==0 || rs.getInt(2)==0)
                throw new IllegalStateException("Configurar empresas e locais de estoque no NUPAR=1 antes de enviar saldos.");
        }
    }

    public Saldo consultar(JdbcWrapper jdbc,BigDecimal produto) throws Exception {
        return consultar(jdbc.getConnection(),produto);
    }

    Saldo consultar(Connection connection,BigDecimal produto) throws Exception {
        Saldo saldo=new Saldo();
        try(PreparedStatement ps=connection.prepareStatement(SQL)) {
            ps.setBigDecimal(1,produto);
            try(ResultSet rs=ps.executeQuery()) {
                while(rs.next()) {
                    BigDecimal valor=rs.getBigDecimal("DISPONIVEL");
                    if(valor==null)valor=BigDecimal.ZERO;
                    if("P".equals(rs.getString("TIPO")))saldo.proprio=saldo.proprio.add(valor);
                    else if("T".equals(rs.getString("TIPO")))saldo.terceiro=saldo.terceiro.add(valor);
                    else throw new IllegalStateException("Tipo de estoque nao reconhecido.");
                }
            }
        }
        return saldo;
    }

    public static final class Saldo {
        public BigDecimal proprio=BigDecimal.ZERO,terceiro=BigDecimal.ZERO;
        public BigDecimal total(){return proprio.add(terceiro);}
        public String resumo(){return "NUPAR=1; disponivel P="+proprio.toPlainString()+
                "; disponivel T="+terceiro.toPlainString()+"; total="+total().toPlainString();}
    }
}
