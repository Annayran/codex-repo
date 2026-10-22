package br.com.zaya.mahospitalar.genesis.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class EstoqueGenesisDto {
    public String codigo;
    public String codigoExterno;
    public String descServico;
    public String descTecnica;
    public Long idCategoria;
    public Long idFabricante;
    public Long idFornecedor;
    public String rgAnvisa;
    public String status;
    public Boolean controlarEstoque = false;
    public Boolean servico;
    public Boolean venda = true;
    public Boolean consumo = true;
    public BigDecimal qtd;
    public BigDecimal ipi;
    public BigDecimal vlrCusto;
    public BigDecimal vlrVenda;
}
