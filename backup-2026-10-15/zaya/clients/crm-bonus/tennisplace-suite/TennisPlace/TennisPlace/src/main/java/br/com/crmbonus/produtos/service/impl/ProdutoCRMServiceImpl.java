package br.com.crmbonus.produtos.service.impl;


import br.com.crmbonus.produtos.dto.ProdutoCRMRequestDTO;
import br.com.crmbonus.produtos.integration.adapter.ProdutoCRMAdapter;
import br.com.crmbonus.produtos.service.ProdutoCRMService;

import java.util.List;
import java.math.BigDecimal;

public class ProdutoCRMServiceImpl implements ProdutoCRMService {

    private ProdutoCRMAdapter adapter = new ProdutoCRMAdapter();

    @Override
    public String consultar(String sku, BigDecimal codEmp) throws Exception {
        return adapter.consultar(sku, codEmp);
    }

    @Override
    public String cadastrar(List<ProdutoCRMRequestDTO> produtos, BigDecimal codEmp) throws Exception {
        return adapter.cadastrar(produtos, codEmp);
    }

    @Override
    public String atualizar(List<ProdutoCRMRequestDTO> produtos, BigDecimal codEmp) throws Exception {
        return adapter.atualizar(produtos, codEmp);
    }
}