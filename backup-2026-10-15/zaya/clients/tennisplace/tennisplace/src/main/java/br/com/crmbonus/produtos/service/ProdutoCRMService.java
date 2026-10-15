package br.com.crmbonus.produtos.service;

import br.com.crmbonus.produtos.dto.ProdutoCRMRequestDTO;

import java.util.List;

public interface ProdutoCRMService {

    String consultar(String codProd) throws Exception;

    String cadastrar(List<ProdutoCRMRequestDTO> produtos) throws Exception;

    String atualizar(List<ProdutoCRMRequestDTO> produtos) throws Exception;
}