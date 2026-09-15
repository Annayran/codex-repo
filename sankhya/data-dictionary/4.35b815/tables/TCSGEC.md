# TCSGEC — Grupo Econômico

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| GrupoEconomicoContrato | Grupo Econômico | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARC | Parceiro | I |  |  | N |
| NUMCONTRATO | Número do contrato | I |  |  | N |
| PERCCUSTO | % de custo | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Contrato / [TCSCON](TCSCON.md) | GrupoEconomicoContrato / [TCSGEC](TCSGEC.md) | NUMCONTRATO->NUMCONTRATO | I |
| GrupoEconomicoContrato / [TCSGEC](TCSGEC.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| GrupoEconomicoContrato / [TCSGEC](TCSGEC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
