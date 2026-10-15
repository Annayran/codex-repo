package br.com.crmbonus.produtos.service;

import br.com.crmbonus.produtos.dto.ProdutoCRMRequestDTO;

import java.util.List;
import java.math.BigDecimal;

public interface ProdutoCRMService {

    String consultar(String codProd, BigDecimal codEmp) throws Exception;

    String cadastrar(List<ProdutoCRMRequestDTO> produtos, BigDecimal codEmp) throws Exception;

    String atualizar(List<ProdutoCRMRequestDTO> produtos, BigDecimal codEmp) throws Exception;
}