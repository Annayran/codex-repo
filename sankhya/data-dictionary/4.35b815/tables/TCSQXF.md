# TCSQXF — Questionário por fluxo

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| QuestionarioFluxo | Questionário por fluxo | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODFLD | Cód. Fluxo de Diagnóstico | I |  |  | N |
| CODPERG | Pergunta abertura | I |  |  | N |
| CODQUEST | Cód. Questionário | I |  |  | N |
| CODRESP | Resposta abertura | I |  |  | N |
| PERCENTUALRESPONDIDO | Percentual respondido | F |  |  | S |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FluxoDiagnostico / [TCSFLD](TCSFLD.md) | QuestionarioFluxo / [TCSQXF](TCSQXF.md) | CODFLD->CODFLD | I |
| FluxoDiagnosticoOS / [TCSFXO](TCSFXO.md) | QuestionarioFluxo / [TCSQXF](TCSQXF.md) | CODFLD->CODFLD | I |
| QuestionarioFluxo / [TCSQXF](TCSQXF.md) | Pergunta / [TPQPER](TPQPER.md) | CODPERG->CODPERG | I |
| QuestionarioFluxo / [TCSQXF](TCSQXF.md) | Questionario / [TPQQUE](TPQQUE.md) | CODQUEST->CODQUEST | I |
| QuestionarioFluxo / [TCSQXF](TCSQXF.md) | RespostaPergunta / [TPQRES](TPQRES.md) | CODPERG->CODPERG; CODRESP->CODRESP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
