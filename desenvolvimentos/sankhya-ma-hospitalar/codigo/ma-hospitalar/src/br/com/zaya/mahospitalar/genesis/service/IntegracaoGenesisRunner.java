package br.com.zaya.mahospitalar.genesis.service;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.zaya.mahospitalar.genesis.client.*;
import br.com.zaya.mahospitalar.genesis.config.*;
import br.com.zaya.mahospitalar.genesis.domain.*;
import br.com.zaya.mahospitalar.genesis.repository.*;
import br.com.zaya.mahospitalar.genesis.util.*;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public final class IntegracaoGenesisRunner {
    public interface Log extends GenesisDiagnosticLog { }
    private static final AtomicBoolean EXECUTANDO = new AtomicBoolean();

    // The public signature is preserved. Every durable phase owns an autonomous Jape transaction.
    public void executar(JdbcWrapper ignored, Log destino) throws Exception {
        if (!EXECUTANDO.compareAndSet(false, true)) {
            destino.info("Integracao ja em execucao neste modulo; rodada ignorada.");
            return;
        }
        GenesisExecutionLog rastreio = new GenesisExecutionLog("CADASTROS_EQUIPAMENTOS", destino);
        Log log = rastreio::info;
        try {
            Settings settings = GenesisTransaction.execute(jdbc -> {
                if (new GenesisCooldownRepository().emEspera(jdbc)) return null;
                ConfigGenesisRepository configs = new ConfigGenesisRepository();
                ParametrosGenesis p = configs.carregar(jdbc);
                p.validarParaExecucao(); configs.validarTops(jdbc);
                return new Settings(p, configs.carregarTokenHomologacao(jdbc));
            });
            if (settings == null) { log.info("Cooldown persistente ativo; nenhuma chamada HTTP."); return; }
            final ParametrosGenesis parametros = settings.parametros;
            CandidatoGenesis candidato = GenesisTransaction.execute(jdbc -> {
                new CancelamentoGenesisRepository().bloquearIntegracao(jdbc);
                List<CandidatoGenesis> candidatos = new CandidatoGenesisRepository().listar(
                        jdbc, 1, parametros.getMaxTentativas(), !parametros.isSimulacao());
                log.info("Consulta concluida. candidatos=" + candidatos.size());
                if (candidatos.isEmpty()) return null;
                CandidatoGenesis c = candidatos.get(0);
                new MovimentoGenesisRepository().registrarPendente(jdbc, c);
                log.info("Controle PENDENTE preparado; movimento=" + c.chaveMovimento);
                return c;
            });
            if (candidato == null) return;
            rastreio.registro("movimento=" + candidato.chaveMovimento);
            try {
                new GenesisSyncService(parametros, new GenesisHttpClient(parametros, () -> settings.token, rastreio))
                        .validarCandidato(candidato);
                PayloadGenesisFactory.validarSituacaoItem(candidato);
                ContextoBaseInstaladaGenesis locais = GenesisTransaction.execute(
                        jdbc -> new CadastroGenesisRepository().carregar(jdbc, candidato));
                GenesisOperationRepository operacoes = new GenesisOperationRepository(candidato);
                GenesisCadastroSyncService sync = new GenesisCadastroSyncService(parametros,
                        new GenesisHttpClient(parametros, () -> settings.token, rastreio), operacoes);
                CadastroGenesisPreparationService preparacao = new CadastroGenesisPreparationService(
                        parametros, sync, operacoes, rastreio);
                if (!preparacao.preparar(candidato, locais)) {
                    log.info("Etapa de cadastro concluida; equipamento aguarda a proxima rodada.");
                    return;
                }
                PayloadGenesisFactory factory = new PayloadGenesisFactory(parametros.getUnidadeEpoch());
                Map<TipoEntidade, String> ids = new EnumMap<TipoEntidade, String>(TipoEntidade.class);
                ids.put(TipoEntidade.CLIENTE, locais.cliente.getIdGenesis().toString());
                ids.put(TipoEntidade.SETOR, locais.setor.getIdGenesis().toString());
                ids.put(TipoEntidade.MODELO, locais.modelo.getIdGenesis().toString());
                PayloadGenesis item = factory.preservarCodigoExterno(factory.item(candidato, ids),
                        locais.itemMaterial.getCodigoExterno());
                rastreio.controle("API|"+item.getTipo().name()+"|"+item.getChaveSankhya());
                GenesisTransaction.execute(jdbc -> { new MovimentoGenesisRepository().etapa(jdbc,candidato,"RESOLVER_ITEM","ITEM_MATERIAL"); return null; });
                GenesisSyncService.Resultado resultado = sync.resolver(item, locais.itemMaterial.getIdGenesis());
                if (resultado.simulado) {
                    GenesisTransaction.execute(jdbc -> {
                        new MovimentoGenesisRepository().marcarSimulado(jdbc, candidato, item.getHash());
                        return null;
                    });
                    return;
                }
                boolean criado = resultado.operacao == GenesisSyncService.Operacao.CRIADO || operacoes.criado(item);
                GenesisTransaction.execute(jdbc -> {
                    new CancelamentoGenesisRepository().bloquearIntegracao(jdbc);
                    CancelamentoGenesisRepository cancelamento = new CancelamentoGenesisRepository();
                    boolean cancelada = cancelamento.notaCancelada(jdbc, candidato.nuNota);
                    CandidatoGenesis gravacao = candidato;
                    if (cancelada) {
                        gravacao = new com.fasterxml.jackson.databind.ObjectMapper().convertValue(candidato, CandidatoGenesis.class);
                        gravacao.nuNota = null;
                    }
                    new BaseGenesisApiRepository().persistir(jdbc, gravacao, locais, resultado, parametros.getUnidadeEpoch());
                    MovimentoGenesisRepository movimentos = new MovimentoGenesisRepository();
                    movimentos.registrarPendente(jdbc, gravacao);
                    movimentos.registrarOrigemItem(jdbc, gravacao, criado ? "CRIADO" : "SEM_ALTERACAO");
                    if (cancelada) {
                        movimentos.registrarRetornoCancelado(jdbc, gravacao, resultado.api, item.getHash());
                        cancelamento.enfileirarRetornoTardio(jdbc, candidato.nuNota, candidato.chaveMovimento);
                    } else movimentos.marcarEnviado(jdbc, gravacao, resultado.api, item.getHash());
                    operacoes.sincronizado(jdbc, item);
                    movimentos.etapa(jdbc,gravacao,cancelada ? "CANCELADO" : "CONCLUIDO",null);
                    return null;
                });
                log.info("Equipamento conciliado na IDBASE=1; operacao=" + resultado.operacao +
                        "; http=" + resultado.api.getHttpStatus() + "; id=" + resultado.api.getIdGenesis());
            } catch (Exception e) {
                GenesisSyncService.FalhaIntegracao api = e instanceof GenesisSyncService.FalhaIntegracao ?
                        (GenesisSyncService.FalhaIntegracao) e : null;
                String status = api != null && api.resultado.getClassificacao() ==
                        GenesisApiResult.Classificacao.TRANSITORIO ? "REPROCESSAR" : "REVISAO";
                if (api != null && "POST".equals(api.etapa) && (api.resultado.getHttpStatus() >= 500 ||
                        api.resultado.getHttpStatus() == 408 || api.resultado.getHttpStatus() == 409)) {
                    status = "ENVIO_INCERTO";
                }
                if (api == null && "ENVIO_INCERTO".equals(GenesisTransaction.execute(
                        jdbc -> new MovimentoGenesisRepository().statusAtual(jdbc, candidato)))) status = "ENVIO_INCERTO";
                String mensagem = GenesisSanitizer.sanitize(e.getMessage());
                // The durable API control, not the movement retry, prevents repeating an uncertain POST.
                final String estado = status;
                GenesisTransaction.execute(jdbc -> {
                    new MovimentoGenesisRepository().marcarErro(jdbc, candidato, estado,
                            api == null ? null : api.resultado.getHttpStatus(), mensagem);
                    return null;
                });
                log.info("Movimento preservado para acompanhamento; status=" + status + "; motivo=" + mensagem);
            }
        } finally {
            EXECUTANDO.set(false);
            rastreio.finalizar();
        }
    }

    private static final class Settings {
        final ParametrosGenesis parametros;
        final String token;
        Settings(ParametrosGenesis parametros, String token) { this.parametros = parametros; this.token = token; }
    }
}
