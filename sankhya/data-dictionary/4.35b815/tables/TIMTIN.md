# TIMTIN — TIMTIN

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimTipoIntermed | Tipo de Intermediária | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| TINCODIGO | Código | I |  |  | N |
| TINDESCRICAO | Descrição | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Financeiro / [TGFFIN](TGFFIN.md) | TimTipoIntermed / [TIMTIN](TIMTIN.md) | TIMTIPOINTERMED->TINCODIGO | I |
| TimIntermediaria / [TIMINT](TIMINT.md) | TimTipoIntermed / [TIMTIN](TIMTIN.md) | INTTIPOINTERMED->TINCODIGO | I |
| TimIntermedReneg / [TIMINR](TIMINR.md) | TimTipoIntermed / [TIMTIN](TIMTIN.md) | INRTIPOINTERMED->TINCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
