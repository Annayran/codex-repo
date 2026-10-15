package br.com.zaya.service;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.zaya.repository.ConciliacaoRepository;

public class VinculacaoNotaService {
    private final ConciliacaoRepository repository;

    public VinculacaoNotaService() {
        this.repository = new ConciliacaoRepository();
    }

    public int vincularPendentes() throws Exception {
        JapeSession.SessionHandle hnd = null;
        JdbcWrapper jdbc = null;
        try {
            hnd = JapeSession.open();
            EntityFacade dwf = EntityFacadeFactory.getDWFFacade();
            jdbc = dwf.getJdbcWrapper();
            return repository.vincularNotasPendentes(jdbc);
        } finally {
            if (jdbc != null) {
                jdbc.closeSession();
            }
            JapeSession.close(hnd);
        }
    }
}

