# TCSPEP — Tabela de Processos e Procedimentos

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ProcessoProcedimento | Processos e Procedimentos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANALITICO | Analítico | S |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| CODPERG | Pergunta | I |  |  | N |
| CODPROC | Processo/Procedimento | I |  |  | N |
| CODPROCPAI | Processo Pai | I |  |  | N |
| CODQUEST | Questionário | I |  |  | N |
| DESCRPROC | Descrição | S |  |  | N |
| GRAU | Grau | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| OrdemServico / [TCSOSE](TCSOSE.md) | ProcessoProcedimento / [TCSPEP](TCSPEP.md) | CODPROCMAHA->CODPROC | I |
| ProcessoProcedimento / [TCSPEP](TCSPEP.md) | Pergunta / [TPQPER](TPQPER.md) | CODPERG->CODPERG | I |
| ProcessoProcedimento / [TCSPEP](TCSPEP.md) | Questionario / [TPQQUE](TPQQUE.md) | CODQUEST->CODQUEST | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
