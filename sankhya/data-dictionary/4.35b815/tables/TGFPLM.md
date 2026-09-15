# TGFPLM — Produtos por Modelo Veículos

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ProdutoModeloVeiculo | Produtos  por modelo de veículos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODMODVEI | Modelo Veículo | I |  |  | N |
| CODPROD | Produto | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Produto / [TGFPRO](TGFPRO.md) | ProdutoModeloVeiculo / [TGFPLM](TGFPLM.md) | CODPROD->CODPROD | I |
| ProdutoModeloVeiculo / [TGFPLM](TGFPLM.md) | ModeloVeiculo / [TGFMVE](TGFMVE.md) | CODMODVEI->CODMODVEI | I |
| ProdutoModeloVeiculo / [TGFPLM](TGFPLM.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
