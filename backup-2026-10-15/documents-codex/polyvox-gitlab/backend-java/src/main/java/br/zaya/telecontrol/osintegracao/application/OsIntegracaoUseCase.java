package br.zaya.telecontrol.osintegracao.application;

import java.util.Date;

public interface OsIntegracaoUseCase {
    SincronizacaoResultado sincronizarPorPeriodo(Date dataInicio, Date dataFim) throws Exception;

    SincronizacaoResultado sincronizacaoDia1() throws Exception;

    SincronizacaoResultado processarDocumentosOs(Boolean criarNotaCompraOverride) throws Exception;
}

