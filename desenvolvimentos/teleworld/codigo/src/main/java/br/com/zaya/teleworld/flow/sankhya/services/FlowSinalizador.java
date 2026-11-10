package br.com.zaya.teleworld.flow.sankhya.services;

import br.com.sankhya.workflow.api.SankhyaFlow;
import br.com.zaya.teleworld.flow.model.service.LiberacaoProcessoService.Sinalizador;
import br.com.zaya.teleworld.flow.model.service.RotinaLog;

public class FlowSinalizador implements Sinalizador {
    @Override
    public void enviar(String sinal, String instancia) throws Exception {
        RotinaLog.info("Flow tentativa sinal=" + sinal + " instancia=" + instancia);
        try {
            SankhyaFlow.sendSignal(sinal, new String[] { instancia });
            RotinaLog.info("Flow chamada_concluida sinal=" + sinal + " instancia=" + instancia);
        } catch (Exception e) {
            RotinaLog.info("Flow chamada_falhou sinal=" + sinal + " instancia=" + instancia);
            throw e;
        }
    }
}
