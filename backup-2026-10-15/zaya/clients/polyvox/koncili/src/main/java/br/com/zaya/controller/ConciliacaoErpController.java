package br.com.zaya.controller;

import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.zaya.service.ConciliacaoErpService;
import java.math.BigDecimal;

public class ConciliacaoErpController {
    private final ConciliacaoErpService service;

    public ConciliacaoErpController() {
        this.service = new ConciliacaoErpService();
    }

    public void executar(ContextoAcao contexto) throws Exception {
        executarCompensacao(contexto);
    }

    public void executarCompensacao(ContextoAcao contexto) throws Exception {
        ConciliacaoErpService.CompensacaoResultado resultado = service.executarCompensacaoPadraoComResumo();
        if (contexto != null) {
            contexto.setMensagemRetorno(resultado.toMensagemRetorno());
        }
    }

    public void executarCompensacaoUnica(ContextoAcao contexto, BigDecimal idPendencia) throws Exception {
        ConciliacaoErpService.CompensacaoResultado resultado = service.executarCompensacaoUnica(idPendencia);
        if (contexto != null) {
            contexto.setMensagemRetorno(resultado.toMensagemRetorno());
        }
    }

    public ConciliacaoErpService.CompensacaoResultado executarCompensacaoUnica(BigDecimal idPendencia) throws Exception {
        return service.executarCompensacaoUnica(idPendencia);
    }
}
