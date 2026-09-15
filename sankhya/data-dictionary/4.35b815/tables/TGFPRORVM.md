# TGFPRORVM — Prorrogação Venda Mais

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ProrrogacaoVendaMais | Prorrogação Venda Mais | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROR | Cód. Prorrogação VM | S |  |  | N |
| CODUSUPROR | Prorrogado por | I |  |  | N |
| DTPROR | Prorrogado em | H |  |  | N |
| NUFIN | Nro Único | I |  |  | N |
| NUFINDESP | Nro Único Despesa | I |  |  | N |
| NVDTVENC | Nova data de vencimento | H |  |  | N |
| PENDENTECRIARDESP | Pendente de Criar Despesa | S |  |  | N |
| PRORROGADO | Prorrogado VM | S |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ProrrogacaoVendaMais / [TGFPRORVM](TGFPRORVM.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| ProrrogacaoVendaMais / [TGFPRORVM](TGFPRORVM.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUPROR->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
