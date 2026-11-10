package br.com.zaya.mahospitalar.genesis.schedule;

import br.com.zaya.mahospitalar.genesis.client.*;
import br.com.zaya.mahospitalar.genesis.config.ParametrosGenesis;
import br.com.zaya.mahospitalar.genesis.domain.CandidatoGenesis;
import br.com.zaya.mahospitalar.genesis.repository.*;
import br.com.zaya.mahospitalar.genesis.util.*;
import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;
import java.util.concurrent.atomic.AtomicBoolean;

public final class BaseGenesisRefreshScheduleAction implements ScheduledAction {
    private static final AtomicBoolean EXECUTANDO = new AtomicBoolean();
    @Override public void onTime(ScheduledActionContext contexto) {
        if (!EXECUTANDO.compareAndSet(false, true)) return;
        GenesisExecutionLog log = new GenesisExecutionLog("RECONSULTA", contexto::info);
        try {
            Settings s = GenesisTransaction.execute(jdbc -> {
                if (new GenesisCooldownRepository().emEspera(jdbc)) return null;
                ConfigGenesisRepository configs = new ConfigGenesisRepository();
                ParametrosGenesis p = configs.carregar(jdbc); p.validarParaExecucao();
                return new Settings(p, configs.carregarTokenHomologacao(jdbc),
                        new BaseGenesisRefreshRepository().proximo(jdbc));
            });
            if (s == null || s.alvo == null || s.parametros.isSimulacao()) {
                log.info("Sem reconsulta elegivel, em cooldown ou simulacao; nenhuma chamada.");
                return;
            }
            CandidatoGenesis controle = new CandidatoGenesis();
            log.registro("entidade=" + s.alvo.tipo + "; id=" + s.alvo.id);
            log.info("Criterio: conciliacao vencida ha 24h ou ausente; alternancia pela entidade menos recentemente consultada; um registro por rodada.");
            controle.chaveMovimento = "API|REFRESH|" + s.alvo.tipo.name() + "|" + s.alvo.id;
            controle.tipoMovimento = "RECONSULTA_" + s.alvo.tipo.name();
            log.controle(controle.chaveMovimento);
            GenesisTransaction.execute(jdbc -> {
                new CancelamentoGenesisRepository().bloquearIntegracao(jdbc);
                new MovimentoGenesisRepository(true).registrarPendente(jdbc, controle); return null;
            });
            GenesisApiResult r = new GenesisHttpClient(s.parametros, () -> s.token, log)
                    .buscarPorId(s.alvo.tipo, String.valueOf(s.alvo.id));
            boolean valido = r.isSucesso() && String.valueOf(s.alvo.id).equals(r.getIdGenesis()) &&
                    r.registroParaPersistencia() != null &&
                    r.campoCorresponde("id", String.valueOf(s.alvo.id), false);
            if (valido) GenesisRequestRepository.identidade(r);
            GenesisTransaction.execute(jdbc -> {
                MovimentoGenesisRepository movimentos = new MovimentoGenesisRepository(true);
                if (valido) {
                    new BaseGenesisApiRepository().atualizarRetorno(jdbc, s.alvo.tipo, s.alvo.id, r,
                            s.parametros.getUnidadeEpoch());
                    movimentos.registrarRetornoPendente(jdbc, controle, r, null);
                    movimentos.marcarErro(jdbc, controle, "API_SINCRONIZADO", r.getHttpStatus(),
                            "Reconsulta por ID aplicada na carga 1.");
                } else {
                    movimentos.marcarErro(jdbc, controle, "REVISAO", r.getHttpStatus(),
                            "Reconsulta nao confirmada; registro local preservado. " + r.getCorpoSanitizado());
                }
                return null;
            });
            log.info("Reconsulta concluida; entidade=" + s.alvo.tipo + "; id=" + s.alvo.id +
                    "; http=" + r.getHttpStatus() + "; aplicado=" + valido);
        } catch (Exception e) {
            log.info("Reconsulta interrompida; nenhuma recriacao autorizada: " +
                    GenesisSanitizer.sanitize(e.getMessage()));
            throw new IllegalStateException("Reconsulta Genesis interrompida.", e);
        } finally { EXECUTANDO.set(false); log.finalizar(); }
    }

    private static final class Settings {
        final ParametrosGenesis parametros; final String token;
        final BaseGenesisRefreshRepository.Alvo alvo;
        Settings(ParametrosGenesis p, String token, BaseGenesisRefreshRepository.Alvo alvo) {
            this.parametros = p; this.token = token; this.alvo = alvo;
        }
    }
}
