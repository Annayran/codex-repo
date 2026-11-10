package br.com.zaya.skala;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.core.JapeSession;
import br.com.zaya.skala.particionamento.config.ParticionamentoConfig;
import br.com.zaya.skala.particionamento.sankhya.DesfazerParticionamentoRepository;
import br.com.zaya.skala.particionamento.util.NumericUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class DesfazerParticionamentoAction implements AcaoRotinaJava {
    private final DesfazerParticionamentoRepository repository = new DesfazerParticionamentoRepository();

    @Override
    public void doAction(ContextoAcao ctx) throws Exception {
        try {
            executeAction(ctx);
        } catch (Exception failure) {
            try {
                Package metadata = getClass().getPackage();
                String version = metadata == null ? null : metadata.getImplementationVersion();
                br.com.zaya.engineering.nexobutton.monitoring.FailureTelemetry.reportSafely(
                        failure, getClass().getName(), version == null ? "UNVERIFIED" : version,
                        java.util.UUID.randomUUID().toString());
            } catch (Throwable telemetryFailure) {
                // Preserve the original business failure even if capture initialization fails.
            }
            throw failure;
        }
    }

    private void executeAction(ContextoAcao ctx) throws Exception {
        final int nuNotaSelecionada = obterNuNotaSelecionada(ctx);
        final int codUsu = ctx.getUsuarioLogado().intValue();
        final AtomicReference<Integer> idPart = new AtomicReference<Integer>();
        final AtomicReference<List<Integer>> oesDesfeitas = new AtomicReference<List<Integer>>();

        JapeSession.execEnsuringTX(new JapeSession.TXBlock() {
            @Override
            public void doWithTx() throws Exception {
                Integer idPartLocal = repository.localizarParticionamentoAtivo(nuNotaSelecionada);
                if (idPartLocal == null) {
                    throw new IllegalStateException("Nao foi encontrado particionamento ativo para a nota selecionada.");
                }

                repository.bloquearParticionamento(idPartLocal);

                JapeSession.putProperty(ParticionamentoConfig.JAPE_PROPERTY_DESFAZENDO_PARTICIONAMENTO, "S");
                try {
                    List<Integer> notas = repository.excluirOEsGeradas(idPartLocal, nuNotaSelecionada);
                    repository.marcarRastroComoDesfeito(idPartLocal, codUsu);
                    repository.desmarcarPedidoOrigem(idPartLocal);
                    repository.marcarItensOrigemComoPendentes(idPartLocal);
                    idPart.set(idPartLocal);
                    oesDesfeitas.set(notas);
                } finally {
                    JapeSession.removeProperty(ParticionamentoConfig.JAPE_PROPERTY_DESFAZENDO_PARTICIONAMENTO);
                }
            }
        });

        ctx.setMensagemRetorno(
                "Particionamento desfeito com sucesso!\n"
                        + "Registro de rastreabilidade: " + idPart.get() + "\n"
                        + "OEs removidas: " + oesDesfeitas.get()
        );
    }

    private int obterNuNotaSelecionada(ContextoAcao ctx) throws Exception {
        Registro[] linhas = ctx.getLinhas();
        if (linhas == null || linhas.length == 0) {
            throw new IllegalStateException("Selecione uma nota para desfazer o particionamento.");
        }
        if (linhas.length > 1) {
            throw new IllegalStateException("Selecione apenas uma nota por vez.");
        }

        Object nuNota = linhas[0].getCampo("NUNOTA");
        int valor = (int) NumericUtils.toDouble(nuNota);
        if (valor <= 0) {
            throw new IllegalStateException("Nao foi possivel identificar o NUNOTA selecionado.");
        }
        return valor;
    }
}
