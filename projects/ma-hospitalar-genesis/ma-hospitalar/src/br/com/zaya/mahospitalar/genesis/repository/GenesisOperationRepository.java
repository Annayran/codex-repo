package br.com.zaya.mahospitalar.genesis.repository;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.zaya.mahospitalar.genesis.client.GenesisApiResult;
import br.com.zaya.mahospitalar.genesis.domain.CandidatoGenesis;
import br.com.zaya.mahospitalar.genesis.domain.PayloadGenesis;
import br.com.zaya.mahospitalar.genesis.service.GenesisPostGuard;
import br.com.zaya.mahospitalar.genesis.util.GenesisTransaction;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public final class GenesisOperationRepository implements GenesisPostGuard {
    private final MovimentoGenesisRepository movimentos = new MovimentoGenesisRepository(true);
    private final CandidatoGenesis origem;
    public GenesisOperationRepository() { this(null); }
    public GenesisOperationRepository(CandidatoGenesis origem) { this.origem = origem; }

    @Override public void prepararConsulta(PayloadGenesis p) throws Exception {
        GenesisTransaction.execute(jdbc -> {
            new CancelamentoGenesisRepository().bloquearIntegracao(jdbc);
            CandidatoGenesis c=controle(p);
            try(PreparedStatement ps=jdbc.getConnection().prepareStatement("SELECT COUNT(*) FROM AD_GENAPICTRL WHERE CHAVE_MOVIMENTO=?")) {
                ps.setString(1,c.chaveMovimento);
                try(ResultSet rs=ps.executeQuery()) {
                    rs.next(); int n=rs.getInt(1);
                    if(n>1)throw new IllegalStateException("Controle API duplicado.");
                    if(n==0)movimentos.registrarPendente(jdbc,c);
                }
            }
            return null;
        });
    }

    @Override public void registrarFalhaConsulta(PayloadGenesis p,Exception erro) throws Exception {
        br.com.zaya.mahospitalar.genesis.service.GenesisSyncService.FalhaIntegracao f=
                erro instanceof br.com.zaya.mahospitalar.genesis.service.GenesisSyncService.FalhaIntegracao
                ? (br.com.zaya.mahospitalar.genesis.service.GenesisSyncService.FalhaIntegracao)erro : null;
        GenesisTransaction.execute(jdbc -> {
            try(PreparedStatement ps=jdbc.getConnection().prepareStatement(
                    "UPDATE AD_GENAPICTRL SET STATUS_INTEGRACAO=?,HTTP_STATUS=?,"+
                    "MENSAGEM_ERRO=CASE WHEN ORIGEM_ITEM='CRIADO' THEN MENSAGEM_ERRO ELSE TO_CLOB(?) END,"+
                    "TENTATIVAS=NVL(TENTATIVAS,0)+1,DH_ATUALIZACAO=SYSDATE WHERE CHAVE_MOVIMENTO=? " +
                    "AND STATUS_INTEGRACAO NOT IN ('ENVIO_INCERTO','API_CONFIRMADO')")) {
                ps.setString(1,f!=null&&f.resultado.getClassificacao()==GenesisApiResult.Classificacao.TRANSITORIO?"REPROCESSAR":"REVISAO");
                if(f==null)ps.setNull(2,java.sql.Types.NUMERIC);else ps.setInt(2,f.resultado.getHttpStatus());
                ps.setString(3,br.com.zaya.mahospitalar.genesis.util.GenesisSanitizer.sanitize(erro.getMessage()));
                ps.setString(4,controle(p).chaveMovimento);ps.executeUpdate();
            }
            return null;
        });
    }

    private CandidatoGenesis controle(PayloadGenesis p) {
        CandidatoGenesis c = new CandidatoGenesis();
        c.chaveMovimento = "API|" + p.getTipo().name() + "|" + p.getChaveSankhya();
        if (c.chaveMovimento.length() > 200) throw new IllegalStateException("Identidade API excede 200 caracteres.");
        c.tipoMovimento = "CADASTRO_" + p.getTipo().name();
        return c;
    }

    @Override public Long idConfirmado(PayloadGenesis p) throws Exception {
        return GenesisTransaction.execute(jdbc -> lerId(jdbc, controle(p)));
    }

    private Long lerId(JdbcWrapper jdbc, CandidatoGenesis c) throws Exception {
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                "SELECT PROTOCOLO_GENESIS,STATUS_INTEGRACAO FROM AD_GENAPICTRL WHERE CHAVE_MOVIMENTO=?")) {
            ps.setString(1, c.chaveMovimento);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                String id = rs.getString(1), status = rs.getString(2);
                if (rs.next()) throw new IllegalStateException("Controle API duplicado; revisar identidade.");
                if (id != null && id.trim().matches("[1-9][0-9]*")) return Long.valueOf(id.trim());
                if ("ENVIO_INCERTO".equals(status)) {
                    throw new GenesisPostGuard.EnvioIncerto("ENVIO_INCERTO: reconciliar controle API antes de repetir POST.");
                }
                return null;
            }
        }
    }

    @Override public Long reservar(PayloadGenesis p) throws Exception {
        return GenesisTransaction.execute(jdbc -> {
            new CancelamentoGenesisRepository().bloquearIntegracao(jdbc);
            if (origem != null && new CancelamentoGenesisRepository().notaCancelada(jdbc, origem.nuNota)) {
                throw new IllegalStateException("Nota cancelada; nenhum novo POST autorizado.");
            }
            CandidatoGenesis c = controle(p);
            Long id = lerId(jdbc, c);
            if (id != null) return id;
            movimentos.registrarPendente(jdbc, c);
            movimentos.marcarErro(jdbc, c, "ENVIO_INCERTO", null,
                    "Reserva duravel antes do POST; origem=" + (origem == null ? "" : origem.chaveMovimento));
            if (origem != null && p.getTipo() == br.com.zaya.mahospitalar.genesis.domain.TipoEntidade.ITEM_MATERIAL) {
                new MovimentoGenesisRepository().marcarErro(jdbc, origem, "ENVIO_INCERTO", null, "POST item-material reservado.");
            }
            return null;
        });
    }

    @Override public void confirmar(PayloadGenesis p, GenesisApiResult result, boolean criado) throws Exception {
        if (!criado) GenesisRequestRepository.identidade(result);
        GenesisTransaction.execute(jdbc -> {
            new CancelamentoGenesisRepository().bloquearIntegracao(jdbc);
            CandidatoGenesis c = controle(p);
            String mensagemOrigem = origem == null ? null : "Movimento de origem: " + origem.chaveMovimento;
            try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                    "SELECT DBMS_LOB.SUBSTR(MENSAGEM_ERRO,1000,1) FROM AD_GENAPICTRL " +
                            "WHERE CHAVE_MOVIMENTO=? AND ORIGEM_ITEM='CRIADO'")) {
                ps.setString(1, c.chaveMovimento);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next() && rs.getString(1) != null) mensagemOrigem = rs.getString(1);
                }
            }
            movimentos.registrarPendente(jdbc, c);
            movimentos.registrarRetornoPendente(jdbc, c, result, p.getHash());
            movimentos.registrarOrigemItem(jdbc, c, criado ? "CRIADO" : "SEM_ALTERACAO");
            movimentos.marcarErro(jdbc, c, "API_CONFIRMADO", result.getHttpStatus(), mensagemOrigem);
            return null;
        });
    }

    @Override public void falhar(PayloadGenesis p, GenesisApiResult result) throws Exception {
        // Only an explicit rejection authorizes a later POST. A timeout/5xx remains reserved.
        if (result.getHttpStatus() >= 500 || result.getHttpStatus() == 408 ||
                result.getHttpStatus() == 409 || result.getHttpStatus() < 400) return;
        GenesisTransaction.execute(jdbc -> {
            movimentos.marcarErro(jdbc, controle(p), "REPROCESSAR", result.getHttpStatus(),
                    result.getCorpoSanitizado());
            return null;
        });
    }

    public boolean sincronizado(JdbcWrapper jdbc, PayloadGenesis p, Long idLocal) throws Exception {
        if (idLocal == null) return false;
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                "SELECT COUNT(*) FROM AD_GENAPICTRL WHERE CHAVE_MOVIMENTO=? " +
                        "AND STATUS_INTEGRACAO='API_SINCRONIZADO' AND PROTOCOLO_GENESIS=?")) {
            ps.setString(1, controle(p).chaveMovimento); ps.setString(2, idLocal.toString());
            try (ResultSet rs = ps.executeQuery()) { rs.next(); return rs.getInt(1) == 1; }
        }
    }

    public void sincronizado(JdbcWrapper jdbc, PayloadGenesis p) throws Exception {
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                "UPDATE AD_GENAPICTRL SET STATUS_INTEGRACAO='API_SINCRONIZADO'," +
                        "DH_ATUALIZACAO=SYSDATE WHERE CHAVE_MOVIMENTO=?")) {
            ps.setString(1, controle(p).chaveMovimento);
            if (ps.executeUpdate() != 1) throw new IllegalStateException("Controle API ausente.");
        }
    }

    public boolean criado(PayloadGenesis p) throws Exception {
        return GenesisTransaction.execute(jdbc -> {
            try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                    "SELECT ORIGEM_ITEM,DBMS_LOB.SUBSTR(MENSAGEM_ERRO,1000,1) FROM AD_GENAPICTRL WHERE CHAVE_MOVIMENTO=?")) {
                ps.setString(1, controle(p).chaveMovimento);
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next() && "CRIADO".equals(rs.getString(1)) && origem != null &&
                            ("Movimento de origem: " + origem.chaveMovimento).equals(rs.getString(2));
                }
            }
        });
    }
}
