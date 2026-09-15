# TCATNO — Turnos

- Módulo técnico: [TCA](../modules/TCA.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Turno | Turnos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODTURNO | Cód.Turno | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Materiais / [TCAMAT](TCAMAT.md) | Turno / [TCATNO](TCATNO.md) | CODTURNO->CODTURNO | I |
| Turma / [TCATUR](TCATUR.md) | Turno / [TCATNO](TCATNO.md) | CODTURNO->CODTURNO | I |
| Turno / [TCATNO](TCATNO.md) | Materiais / [TCAMAT](TCAMAT.md) | CODTURNO->CODTURNO | I |
| Turno / [TCATNO](TCATNO.md) | Turma / [TCATUR](TCATUR.md) | CODTURNO->CODTURNO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
