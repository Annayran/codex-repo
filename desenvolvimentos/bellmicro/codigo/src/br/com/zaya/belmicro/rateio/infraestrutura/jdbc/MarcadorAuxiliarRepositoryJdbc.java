package br.com.zaya.belmicro.rateio.infraestrutura.jdbc;

import java.sql.*;
import java.util.Objects;

public final class MarcadorAuxiliarRepositoryJdbc {
    private static final String SQL = ConsultasRateio.ler("marcador-auxiliar.sql");
    private final ConexaoRateio conexoes;
    public MarcadorAuxiliarRepositoryJdbc(ConexaoRateio conexoes) { this.conexoes=Objects.requireNonNull(conexoes,"conexoes"); }
    public boolean marcado(long nufin) {
        if (nufin <= 0) throw new IllegalArgumentException("NUFIN deve ser positivo.");
        try (PreparedStatement consulta=conexoes.obter().prepareStatement(SQL)) {
            consulta.setLong(1,nufin);
            try (ResultSet linhas=consulta.executeQuery()) { return linhas.next(); }
        } catch (SQLException e) { throw new FalhaConsultaRateio("MARCADOR_AUXILIAR"); }
    }
}
