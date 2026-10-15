package br.com.zaya.controller;

import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.zaya.service.VinculacaoNotaService;

public class VinculacaoNotaController {
    private final VinculacaoNotaService service;

    public VinculacaoNotaController() {
        this.service = new VinculacaoNotaService();
    }

    public void executar(ContextoAcao contexto) throws Exception {
        int total = service.vincularPendentes();
        if (contexto != null) {
            contexto.setMensagemRetorno("Vinculacao concluida. notasVinculadas=" + total);
        }
    }
}

