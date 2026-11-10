package br.com.zaya.topnutri.importacaoformulas.model.repository;

import br.com.zaya.topnutri.importacaoformulas.model.entity.ProductCategory;

import java.util.Optional;

public interface ProductCategoryProvider {
    Optional<ProductCategory> findCategory(String productCode);
}
