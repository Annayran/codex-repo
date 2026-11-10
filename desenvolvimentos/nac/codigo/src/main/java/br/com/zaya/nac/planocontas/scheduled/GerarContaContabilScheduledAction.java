package br.com.zaya.nac.planocontas.scheduled;

import br.com.zaya.nac.planocontas.controller.GerarContaContabilAgendadaController;
import br.com.zaya.nac.planocontas.repository.sankhya.SankhyaParceiroPendenteRepository;
import br.com.zaya.nac.planocontas.repository.sankhya.SankhyaParceiroRepository;
import br.com.zaya.nac.planocontas.repository.sankhya.SankhyaPlanoContasRepository;
import br.com.zaya.nac.planocontas.service.ContaContabilService;
import br.com.zaya.nac.planocontas.service.EmpresaEventoResolver;
import br.com.zaya.nac.planocontas.service.ParceiroValidator;
import br.com.zaya.nac.planocontas.service.ProcessamentoLoteService;
import br.com.zaya.nac.planocontas.service.SequenciaContaService;
import br.com.zaya.nac.planocontas.util.SysOutLog;
import java.util.UUID;
import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;

public final class GerarContaContabilScheduledAction implements ScheduledAction {

    @Override
    public void onTime(ScheduledActionContext contexto) {
        String idExecucao = UUID.randomUUID().toString();
        SysOutLog.info(idExecucao, "Início da ação agendada de Plano de Contas.");
        try {
            SequenciaContaService sequenciaService = new SequenciaContaService();
            ContaContabilService contaService = new ContaContabilService(
                    new SankhyaParceiroRepository(),
                    new SankhyaPlanoContasRepository(sequenciaService),
                    new ParceiroValidator());
            GerarContaContabilAgendadaController controller =
                    new GerarContaContabilAgendadaController(
                            new SankhyaParceiroPendenteRepository(),
                            new ProcessamentoLoteService(contaService),
                            new EmpresaEventoResolver());
            controller.executar(contexto, idExecucao);
            SysOutLog.info(idExecucao, "Ação agendada finalizada sem falhas.");
        } catch (Exception e) {
            SysOutLog.erro(
                    idExecucao,
                    "A ação agendada terminou com falha. Os commits individuais já "
                            + "confirmados foram preservados; o parceiro com erro teve rollback.",
                    e);
            if (contexto != null) {
                contexto.log(
                        "Falha na execução " + idExecucao + ": " + mensagemRaiz(e));
            }
            throw new IllegalStateException(
                    "Falha na ação agendada de Plano de Contas. Motivo: "
                            + mensagemRaiz(e) + " Execução " + idExecucao + ".",
                    e);
        }
    }

    private String mensagemRaiz(Throwable erro) {
        Throwable atual = erro;
        while (atual.getCause() != null) {
            atual = atual.getCause();
        }
        return atual.getMessage() == null
                ? atual.getClass().getSimpleName()
                : atual.getMessage();
    }
}
