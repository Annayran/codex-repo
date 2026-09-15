# TIMODA — TIMODA

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimFonteDados | Fonte de dados | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CAMPOIMOVEL | Coluna Id. Imóvel | S |  |  | N |
| DESCRODA | Descrição | S |  |  | N |
| NUODA | Nro. Único | I |  |  | N |
| SQLSELECT | SELECT | C |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimProcessoIntegracao / [TIMPIN](TIMPIN.md) | TimFonteDados / [TIMODA](TIMODA.md) | NUODA->NUODA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
