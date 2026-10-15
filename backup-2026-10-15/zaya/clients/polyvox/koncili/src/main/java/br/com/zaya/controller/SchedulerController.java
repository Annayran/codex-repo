package br.com.zaya.controller;

import br.com.zaya.models.ImportacaoResultado;
import br.com.zaya.service.ImportacaoConciliacaoService;

import java.util.logging.Logger;

public class SchedulerController {
    private static final Logger LOG = Logger.getLogger(SchedulerController.class.getName());

    private final ImportacaoConciliacaoService importacaoService;

    public SchedulerController() {
        this.importacaoService = new ImportacaoConciliacaoService();
    }

    public ImportacaoResultado executarImportacaoAgendada() {
        ImportacaoResultado resultado = importacaoService.importarMesAtualAgendado();
        LOG.info("[SCHEDULER_CONTROLLER_RESULTADO] " + resultado.toMensagemRetorno());
        return resultado;
    }
}

