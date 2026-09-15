# TPQINF — Informações Questionario

- Módulo técnico: [TPQ](../modules/TPQ.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| InformacaoQuestionario | Informações do Questionário | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODQUEST | Código do Questionário | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DHALTER | Data de alteração | H |  |  | N |
| SEQUENCIA | Sequencia | I |  |  | N |
| TEXTO | Texto | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| InformacaoQuestionario / [TPQINF](TPQINF.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Questionario / [TPQQUE](TPQQUE.md) | InformacaoQuestionario / [TPQINF](TPQINF.md) | CODQUEST->CODQUEST | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
