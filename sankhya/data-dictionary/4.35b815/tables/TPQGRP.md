# TPQGRP — Grupo Perguntas

- Módulo técnico: [TPQ](../modules/TPQ.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| GrupoPergunta | Grupo Perguntas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANALITICO | Analítico | S |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| CODGRUPOPERG | Cód Grupo pergunta | I |  |  | N |
| CODGRUPOPERGPAI | Cód. Grupo pergunta pai | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DESCRGRUPOPERG | Descrição | S |  |  | N |
| DHALTER | Data de alteração | H |  |  | N |
| GRAU | Grau | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Pergunta / [TPQPER](TPQPER.md) | GrupoPergunta / [TPQGRP](TPQGRP.md) | CODGRUPOPERG->CODGRUPOPERG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
