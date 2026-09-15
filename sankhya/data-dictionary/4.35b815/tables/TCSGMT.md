# TCSGMT — Grupos de Metodologias

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| GrupoMetodologia | Grupos de Metodologias | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODMETOD | Método | I |  |  | N |
| CODNAT | Cód. Natureza | I |  |  | N |
| DESCRGMETOD | Descrição do Método | S |  |  | N |
| NOMEGMETOD | Nome do Método | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| GrupoMetodologia / [TCSGMT](TCSGMT.md) | ModeloProjeto / [TCSMET](TCSMET.md) | CODMETOD->CODMETOD | I |
| GrupoMetodologia / [TCSGMT](TCSGMT.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
