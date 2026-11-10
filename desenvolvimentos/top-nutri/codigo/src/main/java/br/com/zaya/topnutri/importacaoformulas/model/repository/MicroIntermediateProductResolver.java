package br.com.zaya.topnutri.importacaoformulas.model.repository;

import java.util.Optional;

public interface MicroIntermediateProductResolver {
    Optional<String> findExclusiveIntermediateProduct(String finishedProductCode);
}
