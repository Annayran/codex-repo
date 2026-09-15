# TMICAU — Causa efeito

- Módulo técnico: [TMI](../modules/TMI.md)
- Campos: 7
- Instâncias DWF: 2
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CausaEfeito | Causa efeito | S | N |
| CausaEfeitoPai | Causa Efeito Pai | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ACOES | Ações | I |  |  | S |
| DESCRICAO | Descrição | S |  |  | N |
| DETALHES | Detalhes | S |  |  | N |
| IMPORTANCIA | Importância | S |  |  | N |
| NUCAUSA | Causa | I |  |  | N |
| NUCAUSAORIG | Causa Origem | I |  |  | N |
| NUEFEITO | Núm. Efeito | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CausaEfeitoProblema / [TMICEP](TMICEP.md) | CausaEfeito / [TMICAU](TMICAU.md) | CODCAUSA->NUCAUSA; NUEFEITO->NUEFEITO | I |
| EfeitoProblema / [TMIEFE](TMIEFE.md) | CausaEfeito / [TMICAU](TMICAU.md) | NUEFEITO->NUEFEITO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
