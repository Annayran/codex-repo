package br.zaya.telecontrol.interfaces;

import java.util.Date;
import br.zaya.telecontrol.services.OsIntegracaoService;

/**
 * Interface delegator for OsIntegracaoService (non-destructive wrapper).
 * Keeps existing static implementation untouched and allows dependency inversion.
 */
public interface IOsIntegracaoService {
    OsIntegracaoService.SincronizacaoResultado sincronizarPorPeriodo(Date dataInicio, Date dataFim) throws Exception;
    OsIntegracaoService.SincronizacaoResultado sincronizacaoDia1() throws Exception;
    OsIntegracaoService.SincronizacaoResultado processarDocumentosOs(Boolean criarNotaCompraOverride) throws Exception;
}

