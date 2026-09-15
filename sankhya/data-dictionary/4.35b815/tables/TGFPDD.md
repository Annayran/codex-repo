# TGFPDD — Tabela de produto divergente

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ProdutoDivergente | Produto Divergente | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Código do produto | I |  |  | N |
| CODVOL | Código do Volume | S |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| NUCONF | Código da conferência | I |  |  | N |
| SEQUENCIA | Sequencia | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ProdutoDivergente / [TGFPDD](TGFPDD.md) | CabecalhoConferencia / [TGFCON2](TGFCON2.md) | NUCONF->NUCONF | I |
| ProdutoDivergente / [TGFPDD](TGFPDD.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ProdutoDivergente / [TGFPDD](TGFPDD.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
