# TCADIS — Disciplina

- Módulo técnico: [TCA](../modules/TCA.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Disciplina | Disciplina | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODDISC | Cód.Disciplina | S |  |  | N |
| NOME | Nome da Disciplina | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Disciplina / [TCADIS](TCADIS.md) | DisciplinaSemanal / [TCADSE](TCADSE.md) | CODDISC->CODDISC | I |
| DisciplinaSemanal / [TCADSE](TCADSE.md) | Disciplina / [TCADIS](TCADIS.md) | CODDISC->CODDISC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
