# TCSFPR — Produto  por Projeto

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ProdutoProjeto | Produto por Projeto | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Produto | I |  |  | N |
| NUFAP | Número da FAP | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ProdutoProjeto / [TCSFPR](TCSFPR.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ProdutoProjeto / [TCSFPR](TCSFPR.md) | ProjetoServico / [TCSFAP](TCSFAP.md) | NUFAP->NUFAP | I |
| ProjetoServico / [TCSFAP](TCSFAP.md) | ProdutoProjeto / [TCSFPR](TCSFPR.md) | NUFAP->NUFAP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
