# TCSFLD — Fluxo de Diagnóstico

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FluxoDiagnostico | Fluxo de Diagnóstico | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODFLD | Cód. Fluxo | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| FLUXO | FLUXO | C |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FluxoDiagnostico / [TCSFLD](TCSFLD.md) | QuestionarioFluxo / [TCSQXF](TCSQXF.md) | CODFLD->CODFLD | I |
| FluxoDiagnosticoOS / [TCSFXO](TCSFXO.md) | FluxoDiagnostico / [TCSFLD](TCSFLD.md) | CODFLD->CODFLD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
