# TGFFAM — Família de Produtos

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FamiliaProduto | Família | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPRODFILHO | Produto filho | I |  |  | N |
| CODPRODPAI | Produto pai | I |  |  | N |
| COMPLDESC | Complemento | S |  |  | S |
| MARCA | Marca | S |  |  | S |
| REFERENCIA | Referência | S |  |  | S |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FamiliaProduto / [TGFFAM](TGFFAM.md) | Produto / [TGFPRO](TGFPRO.md) | CODPRODFILHO->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | FamiliaProduto / [TGFFAM](TGFFAM.md) | CODPROD->CODPRODPAI | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
