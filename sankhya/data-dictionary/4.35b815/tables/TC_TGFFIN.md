# TC_TGFFIN — Eixos Estratégicos

- Módulo técnico: [TC_](../modules/TC_.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CarimboFinanceiro | Controle Financeiro | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| APPKEY | Appkey | S |  |  | N |
| DHALTER | Data de inclusão | H |  |  | N |
| HASHLINHA | Hash | S |  |  | N |
| IDPERSONALIZACAO | Id Personalização | S |  |  | N |
| INTEGRADO | Integrado | S |  |  | N |
| NUCHAVE | Nro Nota | I |  |  | N |
| ORIGEM | Origem | S |  |  | S |
| STATUS | Status | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CarimboFinanceiro / [TC_TGFFIN](TC_TGFFIN.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUCHAVE->NUFIN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
