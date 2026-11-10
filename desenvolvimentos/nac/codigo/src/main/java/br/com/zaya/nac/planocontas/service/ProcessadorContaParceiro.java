package br.com.zaya.nac.planocontas.service;

import br.com.zaya.nac.planocontas.model.ResultadoGeracao;
import java.math.BigDecimal;
import java.util.List;

public interface ProcessadorContaParceiro {

    List<ResultadoGeracao> processar(
            BigDecimal codigoParceiro,
            BigDecimal codigoEmpresa,
            BigDecimal codigoUsuario,
            String idExecucao) throws Exception;
}
