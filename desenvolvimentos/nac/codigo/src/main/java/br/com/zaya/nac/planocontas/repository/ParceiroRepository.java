package br.com.zaya.nac.planocontas.repository;

import br.com.zaya.nac.planocontas.model.Parceiro;
import java.math.BigDecimal;

public interface ParceiroRepository {

    Parceiro buscar(BigDecimal codigoParceiro) throws Exception;

    void vincularConta(BigDecimal codigoParceiro, String campo, BigDecimal codigoConta)
            throws Exception;

    void vincularContaExclusiva(
            BigDecimal codigoParceiro,
            String campo,
            BigDecimal codigoConta) throws Exception;
}
