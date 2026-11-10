package br.com.zaya.belmicro.rateio.infraestrutura.jdbc;

import br.com.zaya.belmicro.rateio.aplicacao.dto.*;
import br.com.zaya.belmicro.rateio.aplicacao.porta.CriterioRepository;
import br.com.zaya.belmicro.rateio.dominio.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

/** Read-only adapter for the manual schema. Does not presume Jape entity names. */
public final class CriterioRepositoryJdbc implements CriterioRepository {
    private static final String CRITERIOS = ConsultasRateio.ler("criterios-aplicaveis.sql");
    private static final String DESTINOS = ConsultasRateio.ler("destinos-matriz.sql");
    private final ConexaoRateio conexoes;

    public CriterioRepositoryJdbc(ConexaoRateio conexoes) {
        this.conexoes = Objects.requireNonNull(conexoes,"conexoes");
    }

    public List<CriterioDisponivel> aplicaveis(DadosOrigem origem) {
        List<CriterioDisponivel> resultado = new ArrayList<CriterioDisponivel>();
        for (Versao versao : consultar(origem)) resultado.add(versao.criterio);
        return Collections.unmodifiableList(resultado);
    }

    public List<DestinoPeso> carregarPesos(String criterioId, int versao, DadosOrigem origem) {
        Versao selecionada = null;
        for (Versao candidata : consultar(origem)) {
            if (candidata.criterio.getId().equals(criterioId) && candidata.criterio.getVersao() == versao) selecionada = candidata;
        }
        if (selecionada == null) throw new ErroRateio("CRITERIO_NAO_SELECIONADO","Criterio ou versao indisponivel.");
        if (!"M".equals(selecionada.modalidade)) {
            if ("A".equals(selecionada.modalidade)) {
                throw new ErroRateio("BASE_INCOMPLETA","Fonte analitica da categoria ainda nao mapeada.");
            }
            throw new ErroRateio("CADASTRO_INVALIDO","Modalidade de criterio invalida.");
        }
        List<DestinoPeso> pesos = new ArrayList<DestinoPeso>();
        try (PreparedStatement consulta = conexoes.obter().prepareStatement(DESTINOS)) {
            consulta.setBigDecimal(1,new BigDecimal(selecionada.criterio.getId()));
            consulta.setInt(2,versao);
            try (ResultSet linhas = consulta.executeQuery()) {
                while (linhas.next()) {
                    String evidencia = "AD_RATDES:" + selecionada.criterio.getId() + ":" + versao + ":"
                            + inteiroPositivo(linhas.getBigDecimal("SEQDESTINO"));
                    BigDecimal percentual = linhas.getBigDecimal("PERCGLOBAL");
                    if (percentual == null || percentual.signum() <= 0 || percentual.stripTrailingZeros().scale() > 6) {
                        throw new ErroRateio("COMPOSICAO_INVALIDA","Matriz com percentual invalido.");
                    }
                    pesos.add(new DestinoPeso(inteiroPositivo(linhas.getBigDecimal("CODEMP")),
                            inteiroPositivo(linhas.getBigDecimal("CODCENCUS")),"MATRIZ",percentual,evidencia));
                }
            }
        } catch (SQLException e) { throw new FalhaConsultaRateio("DESTINOS_MATRIZ"); }
        return ComposicaoManual.validar(pesos);
    }

    private List<Versao> consultar(DadosOrigem origem) {
        if (origem == null || origem.getCompetencia() == null || origem.getCodEmp() <= 0 || origem.getCodNat() <= 0) {
            throw new ErroRateio("BASE_INCOMPLETA","Origem e competencia mapeadas sao obrigatorias para selecionar criterio.");
        }
        List<Versao> resultado = new ArrayList<Versao>();
        Set<String> encontrados = new HashSet<String>();
        try (PreparedStatement consulta = conexoes.obter().prepareStatement(CRITERIOS)) {
            consulta.setInt(1,origem.getCodEmp());
            consulta.setLong(2,origem.getCodNat());
            numeroOpcional(consulta,3,origem.getCodParc());
            numeroOpcional(consulta,4,origem.getCodTipOper());
            for (int indice = 5; indice <= 8; indice++) consulta.setDate(indice,java.sql.Date.valueOf(origem.getCompetencia()));
            try (ResultSet linhas = consulta.executeQuery()) {
                while (linhas.next()) {
                    BigDecimal codigo = linhas.getBigDecimal("CODCRITERIO");
                    if (codigo == null || codigo.signum() <= 0 || codigo.stripTrailingZeros().scale() > 0) {
                        throw new ErroRateio("CADASTRO_INVALIDO","Codigo de criterio invalido.");
                    }
                    String id = codigo.toBigIntegerExact().toString();
                    if (!encontrados.add(id)) throw new ErroRateio("REGRA_CONFLITANTE","Versoes aprovadas com vigencias sobrepostas.");
                    int sequencia = inteiroPositivo(linhas.getBigDecimal("SEQUENCIA"));
                    int numeroVisivel = inteiroPositivo(linhas.getBigDecimal("NROVERSAO"));
                    String descricao = linhas.getString("DESCRICAO");
                    if (descricao == null || descricao.trim().isEmpty()) throw new ErroRateio("CADASTRO_INVALIDO","Criterio sem descricao.");
                    resultado.add(new Versao(new CriterioDisponivel(id,sequencia,descricao + " (v" + numeroVisivel + ")"),
                            linhas.getString("MODALIDADE")));
                }
            }
        } catch (SQLException e) { throw new FalhaConsultaRateio("CRITERIOS_APLICAVEIS"); }
        return resultado;
    }

    private static void numeroOpcional(PreparedStatement consulta,int indice,Long valor) throws SQLException {
        if (valor == null) consulta.setNull(indice,Types.NUMERIC); else consulta.setLong(indice,valor);
    }
    private static int inteiroPositivo(BigDecimal valor) {
        try {
            if (valor == null || valor.signum() <= 0) throw new ArithmeticException();
            return valor.intValueExact();
        } catch (ArithmeticException e) { throw new ErroRateio("CADASTRO_INVALIDO","Identificador ausente ou fora do intervalo suportado."); }
    }
    private static final class Versao {
        final CriterioDisponivel criterio;
        final String modalidade;
        Versao(CriterioDisponivel criterio,String modalidade) { this.criterio=criterio; this.modalidade=modalidade; }
    }
}
