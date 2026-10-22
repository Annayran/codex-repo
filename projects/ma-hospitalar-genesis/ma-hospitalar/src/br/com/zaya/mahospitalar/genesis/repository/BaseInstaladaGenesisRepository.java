package br.com.zaya.mahospitalar.genesis.repository;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.zaya.mahospitalar.genesis.domain.CandidatoGenesis;
import br.com.zaya.mahospitalar.genesis.domain.ReferenciasBaseInstaladaGenesis;
import br.com.zaya.mahospitalar.genesis.service.PayloadGenesisFactory;
import br.com.zaya.mahospitalar.genesis.util.GenesisDiagnosticLog;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public final class BaseInstaladaGenesisRepository {
    private static final int ID_BASE = 1;

    public ReferenciasBaseInstaladaGenesis resolver(JdbcWrapper jdbc, CandidatoGenesis candidato) throws Exception {
        return resolver(jdbc, candidato, GenesisDiagnosticLog.NONE);
    }

    public ReferenciasBaseInstaladaGenesis resolver(JdbcWrapper jdbc, CandidatoGenesis candidato,
                                                     GenesisDiagnosticLog log) throws Exception {
        GenesisDiagnosticLog diagnostico = log == null ? GenesisDiagnosticLog.NONE : log;
        diagnostico.info("Resolucao MODELO iniciada: IDBASE=" + ID_BASE +
                ", CODPROD=" + candidato.codProd +
                ", origem=TGFPRO.AD_IDEXTERNO, destino=AD_GENMODELO.IDGENESIS conciliado.");
        long idModelo = resolverModelo(jdbc, candidato);
        diagnostico.info("Resolucao MODELO concluida: IDGENESIS=" + idModelo + ".");
        diagnostico.info("Resolucao CLIENTE iniciada: IDBASE=" + ID_BASE +
                ", CODPARC=" + candidato.codParc +
                ", destino=AD_GENCLIENTE.IDGENESIS conciliado.");
        long idCliente = resolverCliente(jdbc, candidato);
        diagnostico.info("Resolucao CLIENTE concluida: IDGENESIS=" + idCliente + ".");
        diagnostico.info("Resolucao SETOR iniciada: IDBASE=" + ID_BASE +
                ", IDCLIENTEGEN=" + idCliente + ", CODPARC=" + candidato.codParc + ".");
        long idSetor = resolverSetor(jdbc, idCliente, candidato);
        diagnostico.info("Resolucao SETOR concluida: IDGENESIS=" + idSetor + ".");
        diagnostico.info("Pesquisa ITEM_MATERIAL iniciada: modelo=" + idModelo +
                ", cliente=" + idCliente + ", serie=" +
                PayloadGenesisFactory.normalizarSerie(candidato.serie) + ".");
        ItemExistente item = resolverItemExistente(jdbc, idModelo, idCliente, candidato);
        diagnostico.info(item == null
                ? "Pesquisa ITEM_MATERIAL concluida: nenhum ID Genesis local encontrado."
                : "Pesquisa ITEM_MATERIAL concluida: IDGENESIS=" + item.idGenesis +
                ", codigoExterno=" + item.codigoExterno + ".");
        return new ReferenciasBaseInstaladaGenesis(idCliente, idSetor, idModelo,
                item == null ? null : item.idGenesis,
                item == null ? null : item.codigoExterno);
    }

    public void registrarResultadoItem(JdbcWrapper jdbc, CandidatoGenesis candidato,
                                       ReferenciasBaseInstaladaGenesis referencias,
                                       String idGenesis, String codigoExterno) throws Exception {
        long idItem = idNumerico(idGenesis, "item-material retornado pela Genesis");
        int alterados = atualizarItem(jdbc, candidato, referencias, idItem, codigoExterno);
        validarItemUnico(alterados, candidato);
        if (alterados == 1) return;

        bloquearBase(jdbc);
        alterados = atualizarItem(jdbc, candidato, referencias, idItem, codigoExterno);
        validarItemUnico(alterados, candidato);
        if (alterados == 1) return;

        String sql = "INSERT INTO AD_GENITEMMAT (IDREGITEM,IDBASE,IDGENESIS,IDMODELOGEN," +
                "IDCLIENTEGEN,IDSETORGEN,CODIGOEXTERNO,NSERIE,STATUSGENESIS,VALORUNITARIO," +
                "DATAAQUISICAO,DATAINSTALACAO,ULTIMAINSTALACAO,MARCA,GARANTIA,DONO,SITUACAO," +
                "CODEMP,CODPARC,CODPROD,NUNOTA,SEQUENCIA,CHAVEMOVIMENTO,STATUSCONC," +
                "MENSAGEMCONC,DHCONCILIACAO) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                "?,?,?,'CONCILIADO',NULL,SYSDATE)";
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(sql)) {
            int i = 1;
            ps.setLong(i++, proximoId(jdbc));
            ps.setInt(i++, ID_BASE);
            ps.setLong(i++, idItem);
            ps.setLong(i++, referencias.getIdModelo());
            ps.setLong(i++, referencias.getIdCliente());
            ps.setLong(i++, referencias.getIdSetor());
            ps.setString(i++, limitar(codigoExterno, 200));
            ps.setString(i++, limitar(PayloadGenesisFactory.normalizarSerie(candidato.serie), 150));
            ps.setString(i++, limitar(candidato.statusEstoqueDescricao, 20));
            ps.setBigDecimal(i++, candidato.valorUnitario);
            Timestamp data = candidato.dataMovimento == null ? null : new Timestamp(candidato.dataMovimento.getTime());
            ps.setTimestamp(i++, data);
            ps.setTimestamp(i++, data);
            ps.setTimestamp(i++, data);
            ps.setString(i++, limitar(candidato.marca, 100));
            if (candidato.garantiaMeses == null) ps.setNull(i++, java.sql.Types.NUMERIC);
            else ps.setInt(i++, candidato.garantiaMeses);
            ps.setString(i++, limitar(candidato.poder, 50));
            ps.setString(i++, limitar(candidato.situacaoGenesis, 50));
            ps.setBigDecimal(i++, candidato.codEmp);
            ps.setBigDecimal(i++, candidato.codParc);
            ps.setBigDecimal(i++, candidato.codProd);
            ps.setBigDecimal(i++, candidato.nuNota);
            ps.setBigDecimal(i++, candidato.sequencia);
            ps.setString(i, limitar(candidato.chaveMovimento, 200));
            ps.executeUpdate();
        }
    }

    private long resolverModelo(JdbcWrapper jdbc, CandidatoGenesis candidato) throws Exception {
        String sql = "SELECT M.IDGENESIS FROM TGFPRO P JOIN AD_GENMODELO M " +
                "ON M.IDBASE=? AND M.CODPROD=P.CODPROD AND M.STATUSCONC='CONCILIADO' " +
                "AND UPPER(TRIM(M.CODIGOEXTERNO))=UPPER(TRIM(P.AD_IDEXTERNO)) " +
                "WHERE P.CODPROD=? AND P.AD_IDEXTERNO IS NOT NULL";
        List<Long> ids = consultarIds(jdbc, sql, new Parametros() {
            @Override public void aplicar(PreparedStatement ps) throws Exception {
                ps.setInt(1, ID_BASE); ps.setBigDecimal(2, candidato.codProd);
            }
        });
        long idModelo = unico(ids, "modelo Genesis para CODPROD=" + candidato.codProd +
                " via TGFPRO.AD_IDEXTERNO");
        validarIdModeloUnico(jdbc, idModelo, candidato.codProd);
        return idModelo;
    }

    private void validarIdModeloUnico(JdbcWrapper jdbc, long idModelo, BigDecimal codProd) throws Exception {
        String sql = "SELECT COUNT(*) FROM AD_GENMODELO WHERE IDBASE=? AND IDGENESIS=?";
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(sql)) {
            ps.setInt(1, ID_BASE);
            ps.setLong(2, idModelo);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                int quantidade = rs.getInt(1);
                if (quantidade > 1) {
                    throw new IllegalStateException("ID Genesis de modelo " + idModelo +
                            " possui " + quantidade + " vinculos locais; envio bloqueado para CODPROD=" +
                            codProd + ".");
                }
            }
        }
    }

    private long resolverCliente(JdbcWrapper jdbc, CandidatoGenesis candidato) throws Exception {
        String sql = "SELECT IDGENESIS FROM AD_GENCLIENTE WHERE IDBASE=? AND CODPARC=? " +
                "AND STATUSCONC='CONCILIADO'";
        List<Long> ids = consultarIds(jdbc, sql, new Parametros() {
            @Override public void aplicar(PreparedStatement ps) throws Exception {
                ps.setInt(1, ID_BASE); ps.setBigDecimal(2, candidato.codParc);
            }
        });
        return unico(ids, "cliente Genesis para CODPARC=" + candidato.codParc);
    }

    private long resolverSetor(JdbcWrapper jdbc, long idCliente, CandidatoGenesis candidato) throws Exception {
        String sql = "SELECT IDGENESIS,PADRAO FROM AD_GENSECTOR WHERE IDBASE=? AND IDCLIENTEGEN=? " +
                "AND CODPARC=? AND STATUSCONC='CONCILIADO' ORDER BY IDGENESIS";
        List<Setor> setores = new ArrayList<Setor>();
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(sql)) {
            ps.setInt(1, ID_BASE); ps.setLong(2, idCliente); ps.setBigDecimal(3, candidato.codParc);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) setores.add(new Setor(rs.getLong("IDGENESIS"), rs.getString("PADRAO")));
            }
        }
        if (setores.size() == 1) return setores.get(0).idGenesis;
        Long padrao = null;
        for (Setor setor : setores) {
            if (!"S".equalsIgnoreCase(setor.padrao)) continue;
            if (padrao != null) throw new IllegalStateException("Mais de um setor padrao para o cliente Genesis " + idCliente + ".");
            padrao = setor.idGenesis;
        }
        if (padrao != null) return padrao;
        if (setores.isEmpty()) throw new IllegalStateException("Nenhum setor Genesis conciliado para o cliente " + idCliente + ".");
        throw new IllegalStateException("Cliente Genesis " + idCliente + " possui varios setores e nenhum PADRAO='S'.");
    }

    private ItemExistente resolverItemExistente(JdbcWrapper jdbc, long idModelo, long idCliente,
                                                 CandidatoGenesis candidato) throws Exception {
        String serie = PayloadGenesisFactory.normalizarSerie(candidato.serie);
        String sql = "SELECT IDGENESIS,CODIGOEXTERNO FROM AD_GENITEMMAT WHERE IDBASE=? " +
                "AND IDMODELOGEN=? AND IDCLIENTEGEN=? AND UPPER(TRIM(NSERIE))=?";
        List<ItemExistente> itens = new ArrayList<ItemExistente>();
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(sql)) {
            ps.setInt(1, ID_BASE); ps.setLong(2, idModelo); ps.setLong(3, idCliente); ps.setString(4, serie);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) itens.add(new ItemExistente(rs.getLong("IDGENESIS"), rs.getString("CODIGOEXTERNO")));
            }
        }
        if (itens.size() > 1) {
            throw new IllegalStateException("Mais de um item-material Genesis para modelo=" + idModelo +
                    ", cliente=" + idCliente + " e serie=" + serie + ".");
        }
        return itens.isEmpty() ? null : itens.get(0);
    }

    private int atualizarItem(JdbcWrapper jdbc, CandidatoGenesis candidato,
                              ReferenciasBaseInstaladaGenesis referencias, long idGenesis,
                              String codigoExterno) throws Exception {
        String sql = "UPDATE AD_GENITEMMAT SET IDGENESIS=?,IDMODELOGEN=?,IDCLIENTEGEN=?,IDSETORGEN=?," +
                "CODIGOEXTERNO=COALESCE(?,CODIGOEXTERNO)," +
                "STATUSGENESIS=?,VALORUNITARIO=?,DATAINSTALACAO=?,ULTIMAINSTALACAO=?," +
                "MARCA=?,GARANTIA=?,DONO=?,SITUACAO=?,CODEMP=?,CODPARC=?,CODPROD=?,NUNOTA=?," +
                "SEQUENCIA=?,CHAVEMOVIMENTO=?,STATUSCONC='CONCILIADO',MENSAGEMCONC=NULL," +
                "DHCONCILIACAO=SYSDATE WHERE IDBASE=? AND (IDGENESIS=? OR " +
                "(CODPROD=? AND UPPER(TRIM(NSERIE))=?))";
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(sql)) {
            int i = 1;
            ps.setLong(i++, idGenesis);
            ps.setLong(i++, referencias.getIdModelo());
            ps.setLong(i++, referencias.getIdCliente());
            ps.setLong(i++, referencias.getIdSetor());
            ps.setString(i++, limitar(codigoExterno, 200));
            ps.setString(i++, limitar(candidato.statusEstoqueDescricao, 20));
            ps.setBigDecimal(i++, candidato.valorUnitario);
            Timestamp data = candidato.dataMovimento == null ? null : new Timestamp(candidato.dataMovimento.getTime());
            ps.setTimestamp(i++, data); ps.setTimestamp(i++, data);
            ps.setString(i++, limitar(candidato.marca, 100));
            if (candidato.garantiaMeses == null) ps.setNull(i++, java.sql.Types.NUMERIC);
            else ps.setInt(i++, candidato.garantiaMeses);
            ps.setString(i++, limitar(candidato.poder, 50));
            ps.setString(i++, limitar(candidato.situacaoGenesis, 50));
            ps.setBigDecimal(i++, candidato.codEmp); ps.setBigDecimal(i++, candidato.codParc);
            ps.setBigDecimal(i++, candidato.codProd); ps.setBigDecimal(i++, candidato.nuNota);
            ps.setBigDecimal(i++, candidato.sequencia); ps.setString(i++, limitar(candidato.chaveMovimento, 200));
            ps.setInt(i++, ID_BASE); ps.setLong(i++, idGenesis);
            ps.setBigDecimal(i++, candidato.codProd);
            ps.setString(i, PayloadGenesisFactory.normalizarSerie(candidato.serie));
            return ps.executeUpdate();
        }
    }

    private static void validarItemUnico(int quantidade, CandidatoGenesis candidato) {
        if (quantidade > 1) {
            throw new IllegalStateException("Mais de um item-material local encontrado para CODPROD=" +
                    candidato.codProd + " e SERIE=" +
                    PayloadGenesisFactory.normalizarSerie(candidato.serie) + ".");
        }
    }

    private void bloquearBase(JdbcWrapper jdbc) throws Exception {
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                "SELECT IDBASE FROM AD_GENBASE WHERE IDBASE=? FOR UPDATE")) {
            ps.setInt(1, ID_BASE);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) throw new IllegalStateException("Base Instalada Genesis IDBASE=1 inexistente.");
            }
        }
    }

    private long proximoId(JdbcWrapper jdbc) throws Exception {
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                "SELECT NVL(MAX(IDREGITEM),0)+1 FROM AD_GENITEMMAT");
             ResultSet rs = ps.executeQuery()) {
            rs.next(); return rs.getLong(1);
        }
    }

    private List<Long> consultarIds(JdbcWrapper jdbc, String sql, Parametros parametros) throws Exception {
        List<Long> ids = new ArrayList<Long>();
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(sql)) {
            parametros.aplicar(ps);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) ids.add(rs.getLong(1));
            }
        }
        return ids;
    }

    private static long unico(List<Long> ids, String descricao) {
        if (ids.isEmpty()) throw new IllegalStateException("Nenhuma correspondencia unica para " + descricao + ".");
        if (ids.size() > 1) throw new IllegalStateException("Mais de uma correspondencia para " + descricao + ".");
        return ids.get(0);
    }

    private static long idNumerico(String value, String descricao) {
        try { return Long.parseLong(value); }
        catch (Exception e) { throw new IllegalStateException("ID Genesis invalido para " + descricao + ".", e); }
    }

    private static String limitar(String value, int max) {
        if (value == null) return null;
        return value.length() <= max ? value : value.substring(0, max);
    }

    private interface Parametros { void aplicar(PreparedStatement ps) throws Exception; }
    private static final class Setor {
        final long idGenesis; final String padrao;
        Setor(long idGenesis, String padrao) { this.idGenesis = idGenesis; this.padrao = padrao; }
    }
    private static final class ItemExistente {
        final long idGenesis; final String codigoExterno;
        ItemExistente(long idGenesis, String codigoExterno) {
            this.idGenesis = idGenesis; this.codigoExterno = codigoExterno;
        }
    }
}
