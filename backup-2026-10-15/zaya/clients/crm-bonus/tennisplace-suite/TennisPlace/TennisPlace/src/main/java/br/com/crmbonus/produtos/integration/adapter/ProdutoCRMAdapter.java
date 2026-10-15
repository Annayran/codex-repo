package br.com.crmbonus.produtos.integration.adapter;


import br.com.crmbonus.produtos.dto.ProdutoCRMRequestDTO;
import br.com.crmbonus.produtos.dto.ProdutoCRMRequestWrapperDTO;
import br.com.crmbonus.produtos.integration.client.CRMClient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

public class ProdutoCRMAdapter {

    private CRMClient client = new CRMClient();

    public String consultar(String sku, BigDecimal codEmp) throws Exception {

        Map<String, Object> produto = new HashMap<>();
        produto.put("sku", sku);

        Map<String, Object> body = new HashMap<>();
        body.put("products", Arrays.asList(produto));

        return client.enviar("produto", "consultar", body, codEmp);
    }

    // Backwards-compatible overloads
    public String consultar(String sku) throws Exception {
        return consultar(sku, null);
    }

    public String cadastrar(List<ProdutoCRMRequestDTO> produtos, BigDecimal codEmp) throws Exception {

        ProdutoCRMRequestWrapperDTO wrapper =
                new ProdutoCRMRequestWrapperDTO(produtos);

        return client.enviar("produto", "cadastrar", wrapper, codEmp);
    }

    public String cadastrar(List<ProdutoCRMRequestDTO> produtos) throws Exception {
        return cadastrar(produtos, null);
    }

    public String atualizar(List<ProdutoCRMRequestDTO> produtos, BigDecimal codEmp) throws Exception {

        if (produtos == null || produtos.isEmpty()) {
            throw new IllegalArgumentException("Lista de produtos para atualização está vazia.");
        }

        System.out.println("[CRM-BONUS] Quantidade de produtos para atualizar: "
                + produtos.size());

        ProdutoCRMRequestWrapperDTO wrapper =
                new ProdutoCRMRequestWrapperDTO(produtos);

        return client.enviar("produto", "editar", wrapper, codEmp);
    }

    public String atualizar(List<ProdutoCRMRequestDTO> produtos) throws Exception {
        return atualizar(produtos, null);
    }
}