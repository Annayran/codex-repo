package br.zaya.telecontrol.adapters;

import br.zaya.telecontrol.interfaces.IOsIntegracaoService;
import br.zaya.telecontrol.services.OsIntegracaoService;
import java.util.Date;

/**
 * Adapter implementing IOsIntegracaoService delegating to the existing static
 * OsIntegracaoService methods. Non-destructive: does not change legacy code.
 */
public class OsIntegracaoServiceAdapter implements IOsIntegracaoService {

    @Override
    public OsIntegracaoService.SincronizacaoResultado sincronizarPorPeriodo(Date dataInicio, Date dataFim) throws Exception {
        return OsIntegracaoService.sincronizarPorPeriodo(dataInicio, dataFim);
    }

    @Override
    public OsIntegracaoService.SincronizacaoResultado sincronizacaoDia1() throws Exception {
        return OsIntegracaoService.sincronizacaoDia1();
    }

    @Override
    public OsIntegracaoService.SincronizacaoResultado processarDocumentosOs(Boolean criarNotaCompraOverride) throws Exception {
        return OsIntegracaoService.processarDocumentosOs(criarNotaCompraOverride);
    }
}

