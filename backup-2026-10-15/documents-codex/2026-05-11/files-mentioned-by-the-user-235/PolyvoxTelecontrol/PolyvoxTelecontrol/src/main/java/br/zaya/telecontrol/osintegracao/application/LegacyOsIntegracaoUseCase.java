package br.zaya.telecontrol.osintegracao.application;

import br.zaya.telecontrol.adapters.OsIntegracaoServiceAdapter;
import br.zaya.telecontrol.interfaces.IOsIntegracaoService;
import br.zaya.telecontrol.services.OsIntegracaoService;

import java.util.Date;

/**
 * Non-breaking application use case that delegates to the existing legacy static service.
 */
public class LegacyOsIntegracaoUseCase implements OsIntegracaoUseCase {
    private final IOsIntegracaoService osService;

    public LegacyOsIntegracaoUseCase() {
        this(new OsIntegracaoServiceAdapter());
    }

    public LegacyOsIntegracaoUseCase(IOsIntegracaoService osService) {
        this.osService = osService;
    }

    @Override
    public SincronizacaoResultado sincronizarPorPeriodo(Date dataInicio, Date dataFim) throws Exception {
        OsIntegracaoService.SincronizacaoResultado legacy = osService.sincronizarPorPeriodo(dataInicio, dataFim);
        return toAppResult(legacy);
    }

    @Override
    public SincronizacaoResultado sincronizacaoDia1() throws Exception {
        OsIntegracaoService.SincronizacaoResultado legacy = osService.sincronizacaoDia1();
        return toAppResult(legacy);
    }

    @Override
    public SincronizacaoResultado processarDocumentosOs(Boolean criarNotaCompraOverride) throws Exception {
        OsIntegracaoService.SincronizacaoResultado legacy = osService.processarDocumentosOs(criarNotaCompraOverride);
        return toAppResult(legacy);
    }

    private SincronizacaoResultado toAppResult(OsIntegracaoService.SincronizacaoResultado legacy) {
        if (legacy == null) {
            return new SincronizacaoResultado(0, 0, null, "Resultado de sincronizacao nulo");
        }
        return new SincronizacaoResultado(
                legacy.osProcessadas,
                legacy.osComErro,
                legacy.aviso,
                legacy.erro
        );
    }
}

