# TGFGVR — Produtos Pre Acordo Verba

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 7
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ProdutosPreAcordoVerba | Produtos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODGRUPOPROD | Cod Grupo | I |  |  | N |
| CODMARCA | Marca | I |  |  | N |
| CODPARCFORN | Fornecedor Preferencial | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| NUCRITERIO | Nro Criterio | I |  |  | N |
| NUPREPRO | Nro Produto Pre Acordo Verba | I |  |  | N |
| REFERENCIA | Referência | S |  |  | N |
| UNIDADE | Unidade | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CriterioPreAcordoVerba / [TGFGVC](TGFGVC.md) | ProdutosPreAcordoVerba / [TGFGVR](TGFGVR.md) | NUCRITERIO->NUCRITERIO | I |
| ProdutosPreAcordoVerba / [TGFGVR](TGFGVR.md) | CriterioPreAcordoVerba / [TGFGVC](TGFGVC.md) | NUCRITERIO->NUCRITERIO | I |
| ProdutosPreAcordoVerba / [TGFGVR](TGFGVR.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| ProdutosPreAcordoVerba / [TGFGVR](TGFGVR.md) | MarcaProduto / [TGFMAR](TGFMAR.md) | CODMARCA->CODIGO | I |
| ProdutosPreAcordoVerba / [TGFGVR](TGFGVR.md) | ParceiroFornecedor / [TGFPAR](TGFPAR.md) | CODPARCFORN->CODPARC | I |
| ProdutosPreAcordoVerba / [TGFGVR](TGFGVR.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ProdutosPreAcordoVerba / [TGFGVR](TGFGVR.md) | Volume / [TGFVOL](TGFVOL.md) | UNIDADE->CODVOL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
