package br.com.zaya.nac.planocontas.repository;

import java.math.BigDecimal;
import java.util.List;

public interface ParceiroPendenteRepository {

    List<BigDecimal> buscarCodigosPendentes(int limite) throws Exception;
}
