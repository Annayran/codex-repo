package br.com.zaya.nac.planocontas.service;

import br.com.zaya.nac.planocontas.model.FalhaProcessamento;
import br.com.zaya.nac.planocontas.model.ResultadoGeracao;
import br.com.zaya.nac.planocontas.model.ResultadoProcessamentoLote;
import br.com.zaya.nac.planocontas.util.SysOutLog;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class ProcessamentoLoteService {

    private final ProcessadorContaParceiro contaContabilService;

    public ProcessamentoLoteService(ProcessadorContaParceiro contaContabilService) {
        this.contaContabilService = contaContabilService;
    }

    public ResultadoProcessamentoLote processar(
            List<BigDecimal> codigosParceiros,
            BigDecimal codigoEmpresa,
            BigDecimal codigoUsuario,
            String idExecucao) {
        ResultadoProcessamentoLote lote = new ResultadoProcessamentoLote();
        Set<BigDecimal> codigosUnicos = new LinkedHashSet<BigDecimal>(codigosParceiros);

        for (BigDecimal codigoParceiro : codigosUnicos) {
            try {
                List<ResultadoGeracao> resultados = contaContabilService.processar(
                        codigoParceiro,
                        codigoEmpresa,
                        codigoUsuario,
                        idExecucao);
                if (resultados == null || resultados.isEmpty()) {
                    lote.registrarIgnorado();
                    SysOutLog.info(
                            idExecucao,
                            "Parceiro " + codigoParceiro
                                    + " ignorado: todas as vertentes aplicáveis já estão protegidas.");
                } else {
                    lote.registrarSucesso(resultados.size());
                    SysOutLog.info(
                            idExecucao,
                            "Parceiro " + codigoParceiro + " concluído com "
                                    + resultados.size() + " conta(s) criada(s). Commit confirmado.");
                }
            } catch (Exception e) {
                String motivo = mensagemRaiz(e);
                lote.registrarFalha(new FalhaProcessamento(codigoParceiro, motivo));
                SysOutLog.erro(
                        idExecucao,
                        "Falha no parceiro " + codigoParceiro
                                + ". A transação somente deste parceiro foi revertida; "
                                + "o lote continuará.",
                        e);
            }
        }
        return lote;
    }

    private String mensagemRaiz(Throwable erro) {
        Throwable atual = erro;
        while (atual.getCause() != null) {
            atual = atual.getCause();
        }
        return atual.getMessage() == null
                ? atual.getClass().getSimpleName()
                : atual.getMessage();
    }
}
