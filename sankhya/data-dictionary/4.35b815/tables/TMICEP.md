# TMICEP — Causa Efeito Problema

- Módulo técnico: [TMI](../modules/TMI.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CausaEfeitoProblema | Causa Efeito/Problema | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCAUSA | Cód. Causa | I |  |  | N |
| CODPLA | Cód. Plano de Ação | I |  |  | N |
| NUEFEITO | Análise de Causa | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CausaEfeitoProblema / [TMICEP](TMICEP.md) | CausaEfeito / [TMICAU](TMICAU.md) | CODCAUSA->NUCAUSA; NUEFEITO->NUEFEITO | I |
| CausaEfeitoProblema / [TMICEP](TMICEP.md) | EfeitoProblema / [TMIEFE](TMIEFE.md) | NUEFEITO->NUEFEITO | I |
| PlanoAcao / [TMIPLA](TMIPLA.md) | CausaEfeitoProblema / [TMICEP](TMICEP.md) | CODPLA->CODPLA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
