package br.com.zaya.service;

import java.util.List;
import java.util.logging.Logger;
import br.com.zaya.models.ConciliacaoResponse;
import br.com.zaya.models.ImportacaoResultado;

public class ConciliacaoService {
    private static final Logger LOG = Logger.getLogger(ConciliacaoService.class.getName());
    private final ImportacaoConciliacaoService importacaoService;

    public ConciliacaoService() {
        this.importacaoService = new ImportacaoConciliacaoService();
    }

    public void persistirConciliacoes(List<ConciliacaoResponse> conciliacoes) {
        ImportacaoResultado resultado = importacaoService.persistirConciliacoes(
                conciliacoes,
                ImportacaoConciliacaoService.DuplicidadeStrategy.IDTRA_CODVEND
        );
        LOG.info("[KONCILI_PERSISTENCIA_RESULTADO] " + resultado.toMensagemRetorno());
    }
}

