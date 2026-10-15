package br.com.crmbonus.produtos.dto;

import java.util.List;

public class ProdutoCRMRequestWrapperDTO {

    public List<ProdutoCRMRequestDTO> products;

    public ProdutoCRMRequestWrapperDTO(List<ProdutoCRMRequestDTO> products) {
        this.products = products;
    }
}
