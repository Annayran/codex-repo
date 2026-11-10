package br.com.zaya.proauto.filareserva.sankhya.schedulers;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.zaya.proauto.filareserva.application.services.AtualizaProvisaoService;
import br.com.zaya.proauto.filareserva.infrastructure.database.OracleAtualizacaoProvisaoRepository;
import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;

public class AtualizaProvisaoScheduledAction implements ScheduledAction {
    private final AtualizaProvisaoService service;

    public AtualizaProvisaoScheduledAction() {
        this(new AtualizaProvisaoService(new OracleAtualizacaoProvisaoRepository()));
    }

    AtualizaProvisaoScheduledAction(AtualizaProvisaoService service) {
        if (service == null) {
            throw new IllegalArgumentException("O servico de atualizacao de provisao e obrigatorio.");
        }
        this.service = service;
    }

    @Override
    public void onTime(final ScheduledActionContext context) {
        SessionHandle session = null;
        registrarLog(context, "Inicio da atualizacao automatica de provisoes da fila de reserva.");

        try {
            session = JapeSession.open();
            session.execWithTX(new JapeSession.TXBlock() {
                @Override
                public void doWithTx() throws Exception {
                    service.executar();
                }
            });

            registrarInfo(context, "Atualizacao automatica de provisoes concluida com sucesso.");
        } catch (Exception exception) {
            registrarLog(context, "Falha na atualizacao automatica de provisoes: "
                    + mensagemSegura(exception));
            throw new IllegalStateException(
                    "Nao foi possivel atualizar as provisoes da fila de reserva.", exception);
        } finally {
            JapeSession.close(session);
        }
    }

    private void registrarInfo(ScheduledActionContext context, String mensagem) {
        if (context != null) {
            context.info(mensagem);
        }
    }

    private void registrarLog(ScheduledActionContext context, String mensagem) {
        if (context != null) {
            context.log(mensagem);
        }
    }

    private String mensagemSegura(Exception exception) {
        String mensagem = exception.getMessage();
        return mensagem == null || mensagem.trim().isEmpty()
                ? exception.getClass().getSimpleName()
                : mensagem;
    }
}
