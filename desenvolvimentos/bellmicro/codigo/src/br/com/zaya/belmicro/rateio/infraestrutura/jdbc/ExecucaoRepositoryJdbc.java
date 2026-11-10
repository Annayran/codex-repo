package br.com.zaya.belmicro.rateio.infraestrutura.jdbc;

import br.com.zaya.belmicro.rateio.aplicacao.dto.OrigemCanonica;
import br.com.zaya.belmicro.rateio.aplicacao.porta.ExecucaoRepository;
import java.sql.*;
import java.util.Objects;

public final class ExecucaoRepositoryJdbc implements ExecucaoRepository {
    private static final String NOTA=ConsultasRateio.ler("execucao-nota.sql");
    private static final String TITULO=ConsultasRateio.ler("execucao-titulo.sql");
    private final ConexaoRateio conexoes;
    public ExecucaoRepositoryJdbc(ConexaoRateio conexoes) { this.conexoes=Objects.requireNonNull(conexoes,"conexoes"); }
    public boolean concluidaDe(OrigemCanonica origem) {
        Objects.requireNonNull(origem,"origem");
        String sql=origem.getTipo()==OrigemCanonica.Tipo.NOTA ? NOTA : TITULO;
        try (PreparedStatement consulta=conexoes.obter().prepareStatement(sql)) {
            consulta.setLong(1,origem.getId());
            try (ResultSet linhas=consulta.executeQuery()) { return linhas.next(); }
        } catch (SQLException e) { throw new FalhaConsultaRateio("EXECUCAO_CONCLUIDA"); }
    }
}
