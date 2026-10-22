package br.com.zaya.mahospitalar.genesis.repository;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.zaya.mahospitalar.genesis.config.ParametrosGenesis;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public final class ConfigGenesisRepository {
    private static final int NUPAR_GENESIS = 1;

    public ParametrosGenesis carregar(JdbcWrapper jdbc) throws Exception {
        String baseUrl = carregarUrl(jdbc);
        return new ParametrosGenesis(
                NUPAR_GENESIS,
                "HML",
                baseUrl,
                true,
                false,
                15000,
                30000,
                5,
                1,
                "MS"
        );
    }

    public String carregarTokenHomologacao(JdbcWrapper jdbc) throws Exception {
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                "SELECT TOKENGENESIS FROM AD_CENTPARAM WHERE NUPAR=?")) {
            ps.setInt(1, NUPAR_GENESIS);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) throw new IllegalStateException("Central Genesis NUPAR=1 inexistente.");
                String token = rs.getString("TOKENGENESIS");
                if (token == null || token.trim().isEmpty()) {
                    throw new IllegalStateException("Token Genesis nao preenchido na Central NUPAR=1.");
                }
                return token.trim();
            }
        }
    }

    public void validarTops(JdbcWrapper jdbc) throws Exception {
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                "SELECT COUNT(*) FROM AD_CENTPARAMTOP WHERE NUPAR=?")) {
            ps.setInt(1, NUPAR_GENESIS);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                if (rs.getInt(1) == 0) {
                    throw new IllegalStateException("Nenhuma TOP cadastrada em AD_CENTPARAMTOP para NUPAR=1.");
                }
            }
        }
    }

    private String carregarUrl(JdbcWrapper jdbc) throws Exception {
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                "SELECT URLGENESIS FROM AD_CENTPARAM WHERE NUPAR=?")) {
            ps.setInt(1, NUPAR_GENESIS);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) throw new IllegalStateException("Central Genesis NUPAR=1 inexistente.");
                return normalizarUrl(rs.getString("URLGENESIS"));
            }
        }
    }

    static String normalizarUrl(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalStateException("URLGENESIS nao preenchida na Central NUPAR=1.");
        }
        String url = valor.trim();
        java.net.URI uri;
        try { uri = java.net.URI.create(url); }
        catch (IllegalArgumentException e) { throw new IllegalStateException("URLGENESIS invalida."); }
        if (!"https".equalsIgnoreCase(uri.getScheme()) || uri.getHost() == null
                || uri.getUserInfo() != null || uri.getQuery() != null || uri.getFragment() != null) {
            throw new IllegalStateException("URLGENESIS deve ser HTTPS, sem credenciais, query ou fragmento.");
        }
        while (url.endsWith("/")) url = url.substring(0, url.length() - 1);
        return url.endsWith("/api/v1") ? url + "/" : url + "/api/v1/";
    }

}
