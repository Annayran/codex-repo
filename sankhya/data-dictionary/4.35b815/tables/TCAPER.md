# TCAPER — Período

- Módulo técnico: [TCA](../modules/TCA.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Periodo | Período | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODANO | Cód.Ano | I |  |  | N |
| CODEMP | Cód.Empresa | I |  |  | N |
| DIASLETIVOS | Quantidade de dias Letivos | I |  |  | N |
| DTFIM | Data do Fim | H |  |  | N |
| DTINICIO | Data de Início | H |  |  | N |
| MSGPERIODO | Mensagem | S |  |  | N |
| NOME | Nome | S |  |  | N |
| NOTAMINIMA | Nota Mínima | I |  |  | N |
| NUPERIODO | Número do Período | I |  |  | N |
| PERSUBFIM | PERSUBFIM | I |  |  | N |
| PERSUBINI | PERSUBINI | I |  |  | N |
| TIPSUB | TIPSUB | S |  |  | N |
| VLRPERIODO | Valor Período | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AnoLetivo / [TCAANO](TCAANO.md) | Periodo / [TCAPER](TCAPER.md) | CODANO->CODANO | I |
| MapaDiario / [TCAMAP](TCAMAP.md) | Periodo / [TCAPER](TCAPER.md) | CODANO->CODANO; NUPERIODO->NUPERIODO | I |
| Periodo / [TCAPER](TCAPER.md) | AnoLetivo / [TCAANO](TCAANO.md) | CODANO->CODANO | I |
| Periodo / [TCAPER](TCAPER.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Periodo / [TCAPER](TCAPER.md) | MapaDiario / [TCAMAP](TCAMAP.md) | CODANO->CODANO; NUPERIODO->NUPERIODO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
