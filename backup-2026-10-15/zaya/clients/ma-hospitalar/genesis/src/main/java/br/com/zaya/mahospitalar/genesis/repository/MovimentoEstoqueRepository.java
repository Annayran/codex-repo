package br.com.zaya.mahospitalar.genesis.repository;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.zaya.mahospitalar.genesis.client.GenesisResponse;
import br.com.zaya.mahospitalar.genesis.config.ParametrosGenesis;
import br.com.zaya.mahospitalar.genesis.domain.MovimentoEstoque;
import br.com.zaya.mahospitalar.genesis.dto.EstoqueGenesisDTO;
import br.com.zaya.mahospitalar.genesis.support.JsonUtil;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovimentoEstoqueRepository {
    private static final int NUPAR_GENESIS = 1;
    private static final String GENESIS_BASE_URL_FIXA = "https://sistema.genesis-s.com:7818/G4VAPI";
    private static final String GENESIS_TOKEN_FIXO = "WlBrg01R/Br1IN247xAnMizDz5Ioo0RFSId3e7o4mdz/7xVb4EodljSup67lQTYRL4PGM1RcKYHly8bjtL3ox7FMpKSAOQRJ+phs6zs5Hx0=";
    private static final String STATUS_PENDENTE = "PENDENTE";
    private static final String STATUS_ENVIADO = "ENVIADO";
    private static final String STATUS_ERRO = "ERRO";
    private static final String STATUS_REPROCESSAR = "REPROCESSAR";

    public ParametrosGenesis lerParametros() {
        JdbcWrapper jdbc = null;
        try {
            jdbc = abrirJdbc();
            validarParametroPrincipal(jdbc, NUPAR_GENESIS);
            List<Integer> topsElegiveis = buscarTopsElegiveis(jdbc, NUPAR_GENESIS);
            if (topsElegiveis.isEmpty()) {
                throw new IllegalStateException("Nenhuma TOP parametrizada em AD_CENTPARAMTOP para NUPAR = " + NUPAR_GENESIS + ".");
            }
            return new ParametrosGenesis(GENESIS_BASE_URL_FIXA, GENESIS_TOKEN_FIXO, topsElegiveis);
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao ler parametros Genesis: " + e.getMessage(), e);
        } finally {
            fecharJdbc(jdbc);
        }
    }

    public List<MovimentoEstoque> buscarPendentes(List<Integer> topsElegiveis) {
        JdbcWrapper jdbc = null;
        List<MovimentoEstoque> movimentos = new ArrayList<MovimentoEstoque>();
        try {
            jdbc = abrirJdbc();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT V.* ");
            sql.append("  FROM AD_VW_GENESIS_ESTOQUE V ");
            sql.append("  LEFT JOIN AD_GENMOVEST M ON M.CHAVE_MOVIMENTO = V.CHAVE_MOVIMENTO ");
            sql.append(" WHERE V.SERIE IS NOT NULL ");
            sql.append("   AND (M.CHAVE_MOVIMENTO IS NULL OR NVL(M.STATUS_INTEGRACAO, 'PENDENTE') IN ('PENDENTE', 'REPROCESSAR')) ");
            if (topsElegiveis != null && !topsElegiveis.isEmpty()) {
                sql.append("   AND V.CODTIPOPER IN (");
                for (int i = 0; i < topsElegiveis.size(); i++) {
                    if (i > 0) sql.append(", ");
                    sql.append("?");
                }
                sql.append(") ");
            }
            sql.append(" ORDER BY V.NUNOTA, V.SEQUENCIA");

            PreparedStatement ps = jdbc.getPreparedStatement(sql.toString());
            if (topsElegiveis != null && !topsElegiveis.isEmpty()) {
                for (int i = 0; i < topsElegiveis.size(); i++) {
                    ps.setInt(i + 1, topsElegiveis.get(i).intValue());
                }
            }
            ResultSet rs = null;
            try {
                rs = ps.executeQuery();
                while (rs.next()) {
                    movimentos.add(mapearMovimento(rs));
                }
            } finally {
                fechar(rs);
                fechar(ps);
            }
            return movimentos;
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao buscar pendentes na AD_VW_GENESIS_ESTOQUE: " + e.getMessage(), e);
        } finally {
            fecharJdbc(jdbc);
        }
    }

    public void registrarPendente(MovimentoEstoque movimento, EstoqueGenesisDTO dto) {
        String chave = chaveObrigatoria(movimento);
        JdbcWrapper jdbc = null;
        try {
            jdbc = abrirJdbc();
            if (existeMovimento(jdbc, chave)) {
                atualizarControlePendente(jdbc, movimento, dto);
            } else {
                inserirControlePendente(jdbc, movimento, dto);
            }
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao registrar pendencia na AD_GENMOVEST: " + e.getMessage(), e);
        } finally {
            fecharJdbc(jdbc);
        }
    }

    public void marcarEnviado(MovimentoEstoque movimento, GenesisResponse response) {
        JdbcWrapper jdbc = null;
        try {
            jdbc = abrirJdbc();
            PreparedStatement ps = jdbc.getPreparedStatement(
                    "UPDATE AD_GENMOVEST " +
                    "   SET STATUS_INTEGRACAO = ?, " +
                    "       HTTP_STATUS = ?, " +
                    "       MENSAGEM_ERRO = NULL, " +
                    "       PROTOCOLO_GENESIS = ?, " +
                    "       DH_ENVIO = SYSDATE, " +
                    "       DH_ATUALIZACAO = SYSDATE " +
                    " WHERE CHAVE_MOVIMENTO = ?");
            try {
                ps.setString(1, STATUS_ENVIADO);
                ps.setInt(2, response.getHttpStatus());
                ps.setString(3, truncar(extrairProtocolo(response), 100));
                ps.setString(4, chaveObrigatoria(movimento));
                ps.executeUpdate();
            } finally {
                fechar(ps);
            }
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao marcar AD_GENMOVEST como ENVIADO: " + e.getMessage(), e);
        } finally {
            fecharJdbc(jdbc);
        }
    }

    public void marcarErroFuncional(MovimentoEstoque movimento, GenesisResponse response) {
        marcarErro(movimento, STATUS_ERRO, response.getHttpStatus(), response.getMensagem());
    }

    public void marcarErroFuncional(MovimentoEstoque movimento, String mensagem) {
        marcarErro(movimento, STATUS_ERRO, null, mensagem);
    }

    public void incrementarTentativa(MovimentoEstoque movimento, GenesisResponse response) {
        marcarErro(movimento, STATUS_REPROCESSAR, response.getHttpStatus(), response.getMensagem());
    }

    public void incrementarTentativa(MovimentoEstoque movimento, String mensagem) {
        marcarErro(movimento, STATUS_REPROCESSAR, null, mensagem);
    }

    private void validarParametroPrincipal(JdbcWrapper jdbc, int nupar) throws Exception {
        PreparedStatement ps = jdbc.getPreparedStatement("SELECT 1 FROM AD_CENTPARAM WHERE NUPAR = ?");
        ResultSet rs = null;
        try {
            ps.setInt(1, nupar);
            rs = ps.executeQuery();
            if (!rs.next()) {
                throw new IllegalStateException("Parametro Genesis nao encontrado em AD_CENTPARAM para NUPAR = " + nupar + ".");
            }
        } finally {
            fechar(rs);
            fechar(ps);
        }
    }

    private List<Integer> buscarTopsElegiveis(JdbcWrapper jdbc, int nupar) throws Exception {
        List<Integer> tops = new ArrayList<Integer>();
        PreparedStatement ps = jdbc.getPreparedStatement(
                "SELECT CODTIPOPER FROM AD_CENTPARAMTOP WHERE NUPAR = ? ORDER BY CODTIPOPER");
        ResultSet rs = null;
        try {
            ps.setInt(1, nupar);
            rs = ps.executeQuery();
            while (rs.next()) {
                tops.add(Integer.valueOf(rs.getInt("CODTIPOPER")));
            }
            return tops;
        } finally {
            fechar(rs);
            fechar(ps);
        }
    }
    private MovimentoEstoque mapearMovimento(ResultSet rs) throws SQLException {
        MovimentoEstoque movimento = new MovimentoEstoque();
        movimento.setCodEmpresa(getInteger(rs, "CODEMP"));
        movimento.setNomeFantasia(getString(rs, "NOMEFANTASIA"));
        movimento.setRazaoEmpresa(getString(rs, "RAZAO_EMPRESA"));
        movimento.setCnpjEmpresa(getString(rs, "CNPJ_EMPRESA"));
        movimento.setNumUnico(getLong(rs, "NUNOTA"));
        movimento.setNumNota(getLong(rs, "NUMNOTA"));
        movimento.setDataNegociacao(rs.getTimestamp("DTNEG"));
        movimento.setDataInstalacao(rs.getTimestamp("DTINSTALACAO"));
        movimento.setCodTipoOperacao(getInteger(rs, "CODTIPOPER"));
        movimento.setCodParceiro(getInteger(rs, "CODPARC"));
        movimento.setNomeParceiro(getString(rs, "NOMEPARC"));
        movimento.setRazaoParceiro(getString(rs, "RAZAO_PARCEIRO"));
        movimento.setCnpjCpfParceiro(getString(rs, "CNPJ_CPF_PARCEIRO"));
        movimento.setParcAtivo(getString(rs, "PARC_STATUS"));
        movimento.setCodProduto(getString(rs, "CODPROD"));
        movimento.setProduto(getString(rs, "PRODUTO"));
        movimento.setGrupoProduto(getString(rs, "GRUPO_PRODUTO"));
        movimento.setMarca(getString(rs, "MARCA"));
        movimento.setRgAnvisa(getString(rs, "CODANVISA"));
        movimento.setSerie(getString(rs, "SERIE"));
        movimento.setProdAtivo(getString(rs, "PROD_STATUS"));
        movimento.setTipoMovimento(getString(rs, "TIPO"));
        movimento.setPoder(getString(rs, "PODER"));
        movimento.setGarantiaMeses(getInteger(rs, "GARANTIA_MESES"));
        movimento.setStatusEstoque(getString(rs, "STATUS_ESTOQUE"));
        movimento.setQuantidade(BigDecimal.ONE);
        movimento.setValorUnitario(getBigDecimal(rs, "VLRUNIT"));
        movimento.setLocalizacao(getString(rs, "DESCRLOCAL"));
        movimento.setChaveMovimento(getString(rs, "CHAVE_MOVIMENTO"));
        return movimento;
    }
    private void inserirControlePendente(JdbcWrapper jdbc, MovimentoEstoque movimento, EstoqueGenesisDTO dto) throws Exception {
        PreparedStatement ps = jdbc.getPreparedStatement(
                "INSERT INTO AD_GENMOVEST (" +
                "IDINTGENESIS, CHAVE_MOVIMENTO, TIPO_MOVIMENTO, STATUS_INTEGRACAO, TENTATIVAS, " +
                "HTTP_STATUS, MENSAGEM_ERRO, PAYLOAD_HASH, PROTOCOLO_GENESIS, DH_INCLUSAO, " +
                "DH_ENVIO, DH_ATUALIZACAO, USU_REPROCESSAMENTO, DH_REPROCESSAMENTO, NUNOTA" +
                ") VALUES (?, ?, ?, ?, 0, NULL, NULL, ?, NULL, SYSDATE, NULL, SYSDATE, NULL, NULL, ?)");
        try {
            ps.setLong(1, proximoId(jdbc));
            ps.setString(2, chaveObrigatoria(movimento));
            ps.setString(3, truncar(tipoMovimento(movimento), 100));
            ps.setString(4, STATUS_PENDENTE);
            ps.setString(5, hashPayload(dto));
            setLongOrNull(ps, 6, movimento.getNumUnico());
            ps.executeUpdate();
        } finally {
            fechar(ps);
        }
    }

    private void atualizarControlePendente(JdbcWrapper jdbc, MovimentoEstoque movimento, EstoqueGenesisDTO dto) throws Exception {
        PreparedStatement ps = jdbc.getPreparedStatement(
                "UPDATE AD_GENMOVEST " +
                "   SET TIPO_MOVIMENTO = ?, " +
                "       STATUS_INTEGRACAO = CASE WHEN STATUS_INTEGRACAO = 'ENVIADO' THEN STATUS_INTEGRACAO ELSE ? END, " +
                "       PAYLOAD_HASH = ?, " +
                "       NUNOTA = ?, " +
                "       DH_ATUALIZACAO = SYSDATE " +
                " WHERE CHAVE_MOVIMENTO = ?");
        try {
            ps.setString(1, truncar(tipoMovimento(movimento), 100));
            ps.setString(2, STATUS_PENDENTE);
            ps.setString(3, hashPayload(dto));
            setLongOrNull(ps, 4, movimento.getNumUnico());
            ps.setString(5, chaveObrigatoria(movimento));
            ps.executeUpdate();
        } finally {
            fechar(ps);
        }
    }

    private void marcarErro(MovimentoEstoque movimento, String status, Integer httpStatus, String mensagem) {
        JdbcWrapper jdbc = null;
        try {
            jdbc = abrirJdbc();
            PreparedStatement ps = jdbc.getPreparedStatement(
                    "UPDATE AD_GENMOVEST " +
                    "   SET STATUS_INTEGRACAO = ?, " +
                    "       TENTATIVAS = NVL(TENTATIVAS, 0) + 1, " +
                    "       HTTP_STATUS = ?, " +
                    "       MENSAGEM_ERRO = ?, " +
                    "       DH_ATUALIZACAO = SYSDATE " +
                    " WHERE CHAVE_MOVIMENTO = ?");
            try {
                ps.setString(1, status);
                setIntegerOrNull(ps, 2, httpStatus);
                ps.setString(3, truncar(mensagem, 4000));
                ps.setString(4, chaveObrigatoria(movimento));
                ps.executeUpdate();
            } finally {
                fechar(ps);
            }
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao atualizar erro na AD_GENMOVEST: " + e.getMessage(), e);
        } finally {
            fecharJdbc(jdbc);
        }
    }

    private boolean existeMovimento(JdbcWrapper jdbc, String chaveMovimento) throws Exception {
        PreparedStatement ps = jdbc.getPreparedStatement(
                "SELECT 1 FROM AD_GENMOVEST WHERE CHAVE_MOVIMENTO = ?");
        ResultSet rs = null;
        try {
            ps.setString(1, chaveMovimento);
            rs = ps.executeQuery();
            return rs.next();
        } finally {
            fechar(rs);
            fechar(ps);
        }
    }

    private long proximoId(JdbcWrapper jdbc) throws Exception {
        PreparedStatement ps = jdbc.getPreparedStatement(
                "SELECT NVL(MAX(IDINTGENESIS), 0) + 1 FROM AD_GENMOVEST");
        ResultSet rs = null;
        try {
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 1L;
        } finally {
            fechar(rs);
            fechar(ps);
        }
    }

    private String getString(ResultSet rs, String column) throws SQLException {
        String value = rs.getString(column);
        return isBlank(value) ? null : value.trim();
    }

    private Integer getInteger(ResultSet rs, String column) throws SQLException {
        BigDecimal value = rs.getBigDecimal(column);
        return value == null ? null : Integer.valueOf(value.intValue());
    }

    private Long getLong(ResultSet rs, String column) throws SQLException {
        BigDecimal value = rs.getBigDecimal(column);
        return value == null ? null : Long.valueOf(value.longValue());
    }

    private BigDecimal getBigDecimal(ResultSet rs, String column) throws SQLException {
        return rs.getBigDecimal(column);
    }
    private JdbcWrapper abrirJdbc() throws Exception {
        EntityFacade facade = EntityFacadeFactory.getDWFFacade();
        JdbcWrapper jdbc = facade.getJdbcWrapper();
        jdbc.openSession();
        return jdbc;
    }

    private void fecharJdbc(JdbcWrapper jdbc) {
        if (jdbc != null) {
            JdbcWrapper.closeSession(jdbc);
        }
    }

    private void fechar(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception ignored) {
                // Mantem a excecao original do fluxo.
            }
        }
    }

    private String chaveObrigatoria(MovimentoEstoque movimento) {
        if (movimento == null || isBlank(movimento.getChaveMovimento())) {
            throw new IllegalArgumentException("CHAVE_MOVIMENTO nao informada para AD_GENMOVEST.");
        }
        return movimento.getChaveMovimento().trim();
    }

    private String tipoMovimento(MovimentoEstoque movimento) {
        if (movimento != null && !isBlank(movimento.getTipoMovimento())) {
            return movimento.getTipoMovimento().trim();
        }
        return "A_CONFIRMAR";
    }

    private String hashPayload(EstoqueGenesisDTO dto) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(JsonUtil.toJson(dto).getBytes(StandardCharsets.UTF_8));
        StringBuilder hex = new StringBuilder();
        for (byte b : hash) {
            String value = Integer.toHexString(0xff & b);
            if (value.length() == 1) hex.append('0');
            hex.append(value);
        }
        return hex.toString();
    }

    private String extrairProtocolo(GenesisResponse response) {
        if (response == null || isBlank(response.getCorpo())) {
            return null;
        }
        String corpo = response.getCorpo().trim();
        String id = extrairValorJsonSimples(corpo, "id");
        return isBlank(id) ? corpo : id;
    }

    private String extrairValorJsonSimples(String json, String campo) {
        String chave = "\"" + campo + "\"";
        int idx = json.indexOf(chave);
        if (idx < 0) return null;
        int doisPontos = json.indexOf(':', idx + chave.length());
        if (doisPontos < 0) return null;
        int inicio = doisPontos + 1;
        while (inicio < json.length() && Character.isWhitespace(json.charAt(inicio))) inicio++;
        if (inicio >= json.length()) return null;
        if (json.charAt(inicio) == '"') {
            int fim = json.indexOf('"', inicio + 1);
            return fim > inicio ? json.substring(inicio + 1, fim) : null;
        }
        int fim = inicio;
        while (fim < json.length() && ",}".indexOf(json.charAt(fim)) < 0) fim++;
        return json.substring(inicio, fim).trim();
    }

    private void setLongOrNull(PreparedStatement ps, int index, Long value) throws SQLException {
        if (value == null) {
            ps.setNull(index, java.sql.Types.NUMERIC);
        } else {
            ps.setLong(index, value.longValue());
        }
    }

    private void setIntegerOrNull(PreparedStatement ps, int index, Integer value) throws SQLException {
        if (value == null) {
            ps.setNull(index, java.sql.Types.NUMERIC);
        } else {
            ps.setInt(index, value.intValue());
        }
    }

    private String truncar(String value, int maxLength) {
        if (value == null) return null;
        return value.length() <= maxLength ? value : value.substring(0, maxLength);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}