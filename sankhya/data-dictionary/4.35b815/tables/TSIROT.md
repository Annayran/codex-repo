# TSIROT — Tabela de Rotinas

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
| TabelaRotina | Tabela de Rotinas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODROT | Cód.Rotina | I |  |  | N |
| COMANDO | Camando a ser executado | S |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TabelaRotina / [TSIROT](TSIROT.md) | TarefaAgendada / [TSITAR](TSITAR.md) | CODROT->CODROT | I |
| TarefaAgendada / [TSITAR](TSITAR.md) | TabelaRotina / [TSIROT](TSIROT.md) | CODROT->CODROT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
