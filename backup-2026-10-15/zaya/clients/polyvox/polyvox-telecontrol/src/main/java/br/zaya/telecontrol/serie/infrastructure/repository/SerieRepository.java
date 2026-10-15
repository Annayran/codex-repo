package br.zaya.telecontrol.serie.infrastructure.repository;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.zaya.telecontrol.serie.domain.SeriePendente;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SerieRepository {
    public int sincronizarViewParaLog(JdbcWrapper jdbc) throws Exception {
        PreparedStatement ps = null;
        try {
            ps = jdbc.getConnection().prepareStatement(
                    "INSERT INTO AD_TCLLOGSER ("
                            + "ID, CODPROD, SERIE, REFERENCIA, DATAVENDA, DATAFABRICACAO, DTALTER, PROCESSADO, REVENDA"
                            + ") "
                            + "SELECT BASE.MAX_ID + ROW_NUMBER() OVER ("
                            + "ORDER BY V.CODPROD, V.SERIE, V.REVENDA, V.REFERENCIA"
                            + "), "
                            + "V.CODPROD, V.SERIE, V.REFERENCIA, V.DATAVENDA, V.DATAFABRICACAO, SYSDATE, 'N', V.REVENDA "
                            + "FROM ("
                            + "SELECT * FROM ("
                            + "SELECT S.CODPROD, S.SERIE, S.REFERENCIA, S.DATAVENDA, S.DATAFABRICACAO, S.REVENDA "
                            + "FROM AD_TCLSER S "
                            + "WHERE S.SERIE IS NOT NULL "
                            + "AND NOT EXISTS ("
                            + "SELECT 1 "
                            + "FROM AD_TCLLOGSER L "
                            + "WHERE L.CODPROD = S.CODPROD "
                            + "AND L.SERIE = S.SERIE "
                            + "AND NVL(L.REFERENCIA, '#NULL#') = NVL(S.REFERENCIA, '#NULL#') "
                            + "AND NVL(L.REVENDA, '#NULL#') = NVL(S.REVENDA, '#NULL#')"
                            + ") "
                            + "ORDER BY S.CODPROD, S.SERIE, S.REVENDA, S.REFERENCIA"
                            + ") WHERE ROWNUM < 501"
                            + ") V "
                            + "CROSS JOIN (SELECT NVL(MAX(ID), 0) MAX_ID FROM AD_TCLLOGSER) BASE"
            );
            return ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }

    public List<SeriePendente> buscarPendentes(JdbcWrapper jdbc) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = jdbc.getConnection().prepareStatement(
                    "SELECT * FROM ("
                            + "SELECT L.CODPROD, L.SERIE, L.REFERENCIA, "
                            + "NVL(P.REFERENCIA, L.REFERENCIA) AS REFERENCIA_ENVIO, "
                            + "L.DATAVENDA, L.DATAFABRICACAO, L.REVENDA, L.DTALTER, L.ID "
                            + "FROM AD_TCLLOGSER L "
                            + "LEFT JOIN TGFPRO P ON P.CODPROD = L.CODPROD "
                            + "WHERE NVL(L.PROCESSADO, 'N') = 'N' "
                            + "AND L.SERIE IS NOT NULL "
                            + "ORDER BY NVL(L.DTALTER, TO_DATE('1900-01-01', 'YYYY-MM-DD')), L.ID"
                            + ") WHERE ROWNUM < 501"
            );
            rs = ps.executeQuery();

            List<SeriePendente> pendentes = new ArrayList<SeriePendente>();
            while (rs.next()) {
                pendentes.add(new SeriePendente(
                        rs.getBigDecimal("CODPROD"),
                        rs.getString("SERIE"),
                        rs.getString("REFERENCIA"),
                        rs.getString("REFERENCIA_ENVIO"),
                        rs.getDate("DATAVENDA"),
                        rs.getDate("DATAFABRICACAO"),
                        rs.getString("REVENDA")
                ));
            }
            return pendentes;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    public void marcarProcessado(JdbcWrapper jdbc, SeriePendente serie) throws Exception {
        PreparedStatement ps = null;
        try {
            ps = jdbc.getConnection().prepareStatement(
                    "UPDATE AD_TCLLOGSER "
                            + "SET PROCESSADO = 'S', DTALTER = SYSDATE "
                            + "WHERE CODPROD = ? "
                            + "AND SERIE = ? "
                            + "AND NVL(REFERENCIA, '#NULL#') = NVL(?, '#NULL#') "
                            + "AND NVL(REVENDA, '#NULL#') = NVL(?, '#NULL#') "
                            + "AND NVL(PROCESSADO, 'N') <> 'S'"
            );
            ps.setBigDecimal(1, serie.getCodprod());
            ps.setString(2, serie.getSerie());
            ps.setString(3, serie.getReferenciaOriginal());
            ps.setString(4, serie.getRevenda());
            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }
}
