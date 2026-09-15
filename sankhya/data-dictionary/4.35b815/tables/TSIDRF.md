# TSIDRF — Destinatários Relatórios Formatados

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DestinatarioRelatorioFormatado | Destinatários Relatórios Formatados | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCON | CODCON | I |  |  | N |
| NURFE | NURFE | I |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AgendamentoRelatorioFormatado / [TSIARF](TSIARF.md) | DestinatarioRelatorioFormatado / [TSIDRF](TSIDRF.md) | NURFE->NURFE; SEQUENCIA->SEQUENCIA | I |
| DestinatarioRelatorioFormatado / [TSIDRF](TSIDRF.md) | MSDDestinatario / [TMDCON](TMDCON.md) | CODCON->CODCON | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
