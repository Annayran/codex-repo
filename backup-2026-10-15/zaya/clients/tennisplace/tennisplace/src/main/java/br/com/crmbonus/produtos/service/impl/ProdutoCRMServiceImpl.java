package br.com.crmbonus.produtos.service.impl;


import br.com.crmbonus.produtos.dto.ProdutoCRMRequestDTO;
import br.com.crmbonus.produtos.integration.adapter.ProdutoCRMAdapter;
import br.com.crmbonus.produtos.service.ProdutoCRMService;

import java.util.List;

public class ProdutoCRMServiceImpl implements ProdutoCRMService {

    private ProdutoCRMAdapter adapter = new ProdutoCRMAdapter();

    @Override
    public String consultar(String sku) throws Exception {
        return adapter.consultar(sku);
    }

    @Override
    public String cadastrar(List<ProdutoCRMRequestDTO> produtos) throws Exception {
        return adapter.cadastrar(produtos);
    }

    @Override
    public String atualizar(List<ProdutoCRMRequestDTO> produtos) throws Exception {
        return adapter.atualizar(produtos);
    }
}