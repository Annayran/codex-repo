package br.com.zaya.mahospitalar.genesis.schedule;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.zaya.mahospitalar.genesis.client.GenesisApiResult;
import br.com.zaya.mahospitalar.genesis.client.GenesisHttpClient;
import br.com.zaya.mahospitalar.genesis.config.ParametrosGenesis;
import br.com.zaya.mahospitalar.genesis.repository.CancelamentoGenesisRepository;
import br.com.zaya.mahospitalar.genesis.repository.ConfigGenesisRepository;
import br.com.zaya.mahospitalar.genesis.repository.GenesisRequestRepository;
import br.com.zaya.mahospitalar.genesis.util.GenesisSanitizer;
import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public final class InativacaoGenesisScheduleAction implements ScheduledAction {
    @Override public void onTime(final ScheduledActionContext context) {
        final br.com.zaya.mahospitalar.genesis.util.GenesisExecutionLog log =
                new br.com.zaya.mahospitalar.genesis.util.GenesisExecutionLog("INATIVACAO", context::info);
        JapeSession.SessionHandle session = null;
        try {
            session = JapeSession.open();
            log.info("Inicio da acao agendada.");
            session.execEnsuringTX(() -> processar(log));
        } catch (Exception e) {
            log.info("Falha geral: tipo="+e.getClass().getSimpleName()+", motivo="+e.getMessage());
            throw new IllegalStateException("Inativacao Genesis interrompida: " +
                    GenesisSanitizer.sanitize(e.getMessage()),e);
        } finally { JapeSession.close(session); log.finalizar(); }
    }

    private void processar(final br.com.zaya.mahospitalar.genesis.util.GenesisExecutionLog context) throws Exception {
        JdbcWrapper jdbc = null;
        try {
            jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
            jdbc.openSession();
            CancelamentoGenesisRepository fila = new CancelamentoGenesisRepository();
            fila.bloquearIntegracao(jdbc);
            if (new br.com.zaya.mahospitalar.genesis.repository.GenesisCooldownRepository().emEspera(jdbc)) {
                context.info("API Genesis em cooldown persistente; fila preservada.");
                return;
            }
            ConfigGenesisRepository configs = new ConfigGenesisRepository();
            ParametrosGenesis parametros = configs.carregar(jdbc);
            parametros.validarParaExecucao();
            if (parametros.isSimulacao()) {
                context.info("Simulacao: fila preservada; nenhum DELETE executado."); return;
            }
            final String token = configs.carregarTokenHomologacao(jdbc);
            context.info("Configuracao: ambiente="+parametros.getAmbiente()+", lote="+parametros.getTamanhoLote());
            GenesisHttpClient client = new GenesisHttpClient(parametros, () -> token, context);
            String sql = "SELECT * FROM (SELECT F.IDINTGENESIS,F.IDGENESIS,F.CHAVE_MOVIMENTO," +
                    "CASE WHEN (SELECT COUNT(*) FROM AD_GENITEMMAT I WHERE TO_CHAR(I.IDGENESIS)=F.IDGENESIS)=1 " +
                    "AND EXISTS (SELECT 1 FROM AD_GENITEMMAT I WHERE " +
                    "TO_CHAR(I.IDGENESIS)=F.IDGENESIS AND I.CHAVEMOVIMENTO=F.CHAVE_MOVIMENTO) " +
                    "THEN 0 ELSE 1 END AS ITEM_ALTERADO," +
                    "CASE WHEN REGEXP_LIKE(F.IDGENESIS,'^[1-9][0-9]*$') AND " +
                    "(SELECT COUNT(*) FROM AD_GENMOVEST M WHERE M.IDINTGENESIS=F.IDINTGENESIS " +
                    "AND M.CHAVE_MOVIMENTO=F.CHAVE_MOVIMENTO AND M.ORIGEM_ITEM='CRIADO' " +
                    "AND M.PROTOCOLO_GENESIS=F.IDGENESIS)=1 THEN 1 ELSE 0 END AS IDENTIDADE_SEGURA " +
                    "FROM AD_GENEXCFILA F WHERE F.STATUS IN ('PENDENTE','ERRO','REVISAO') " +
                    "AND (NVL(F.TENTATIVAS,0)=0 OR F.DH_ATUALIZACAO IS NULL OR F.DH_ATUALIZACAO<=SYSDATE-" +
                    "LEAST(60,POWER(2,LEAST(NVL(F.TENTATIVAS,0),6)))/1440) " +
                    "AND EXISTS (SELECT 1 FROM TGFCAN C WHERE C.NUNOTA=F.NUNOTA_ORIGEM AND C.DTCANC IS NOT NULL) " +
                    "AND NOT EXISTS (SELECT 1 FROM TGFCAB C WHERE C.NUNOTA=F.NUNOTA_ORIGEM) " +
                    "ORDER BY F.DH_ATUALIZACAO ASC NULLS FIRST,F.IDINTGENESIS) WHERE ROWNUM<=?";
            try (PreparedStatement ps = jdbc.getConnection().prepareStatement(sql)) {
                ps.setInt(1,parametros.getTamanhoLote());
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        long id = rs.getLong("IDINTGENESIS");
                        context.registro("controle="+id+", idGenesis="+rs.getString("IDGENESIS")+", movimento="+rs.getString("CHAVE_MOVIMENTO"));
                        context.info("Validacao: movimento="+rs.getString("CHAVE_MOVIMENTO")+", itemAlterado="+rs.getInt("ITEM_ALTERADO")+", identidadeSegura="+rs.getInt("IDENTIDADE_SEGURA"));
                        if (rs.getInt("ITEM_ALTERADO") != 0 || rs.getInt("IDENTIDADE_SEGURA") != 1) {
                            context.info("DELETE bloqueado por protecao de identidade/movimento; REVISAO preparada.");
                            fila.concluir(jdbc,id,new GenesisApiResult(409,GenesisApiResult.Classificacao.CONFLITO,
                                    null,"Identidade/origem nao confirmada ou movimento posterior; reavaliar inativacao."),parametros.getMaxTentativas());
                            continue;
                        }
                        GenesisApiResult resultado = client.inativarItem(rs.getString("IDGENESIS"));
                        fila.concluir(jdbc,id,resultado,parametros.getMaxTentativas());
                        if (resultado.getHttpStatus()==200 || resultado.getHttpStatus()==404) GenesisRequestRepository.persistido(jdbc.getConnection(),resultado,
                                "AD_GENEXCFILA; controle="+id+"; retorno de inativacao confirmado.");
                        context.info("Fila Genesis: controle=" + id + ", HTTP=" + resultado.getHttpStatus());
                        if (resultado.getHttpStatus()==429) {
                            context.info("Fila interrompida por limite da API (429); demais registros preservados.");
                            break;
                        }
                        if (resultado.getClassificacao()==GenesisApiResult.Classificacao.AUTENTICACAO) break;
                    }
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("Inativacao Genesis interrompida: " +
                    GenesisSanitizer.sanitize(e.getMessage()),e);
        } finally { JdbcWrapper.closeSession(jdbc); }
    }
}
