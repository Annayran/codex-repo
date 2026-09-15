# TIMFID — TIMFID

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimFiltroDados | Filtro de dados | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DESCRICAO | Descrição | S |  |  | N |
| NUFID | Nro. Único | I |  |  | N |
| SQLWHERE | WHERE | C |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimProcessoIntegracao / [TIMPIN](TIMPIN.md) | TimFiltroDados / [TIMFID](TIMFID.md) | NUFID->NUFID | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
