# TCSFXO — Fluxo por Ordem Serviço

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FluxoDiagnosticoOS | Fluxo por Ordem de Serviço | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODFLD | Cód. Fluxo | I |  |  | N |
| NUMOS | Núm. O.S. | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FluxoDiagnosticoOS / [TCSFXO](TCSFXO.md) | FluxoDiagnostico / [TCSFLD](TCSFLD.md) | CODFLD->CODFLD | I |
| FluxoDiagnosticoOS / [TCSFXO](TCSFXO.md) | OrdemServico / [TCSOSE](TCSOSE.md) | NUMOS->NUMOS | I |
| FluxoDiagnosticoOS / [TCSFXO](TCSFXO.md) | QuestionarioFluxo / [TCSQXF](TCSQXF.md) | CODFLD->CODFLD | I |
| OrdemServico / [TCSOSE](TCSOSE.md) | FluxoDiagnosticoOS / [TCSFXO](TCSFXO.md) | NUMOS->NUMOS | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
