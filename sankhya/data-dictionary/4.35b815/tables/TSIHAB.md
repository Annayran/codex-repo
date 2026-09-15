# TSIHAB — TABLE TSIHAB

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| HistoricoAlteracaoRegras | Histórico de Alterações | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CAMPOALTER | Campo alterado | S |  |  | N |
| CODHAB | CodHab | I |  |  | N |
| CODRGB | CodRgb | I |  |  | N |
| CODUSU | Alterado por | I |  |  | N |
| DTALTER | Dt. alteração | H |  |  | N |
| MOTIVO | Motivo | S |  |  | N |
| NOMEREGRA | Nome Regra | S |  |  | N |
| TABELA | Tabela | S |  |  | N |
| VLRANT | Valor anterior | C |  |  | N |
| VLRPOS | Valor posterior | C |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| HistoricoAlteracaoRegras / [TSIHAB](TSIHAB.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| RegrasCustomizadas / [TSIRGB](TSIRGB.md) | HistoricoAlteracaoRegras / [TSIHAB](TSIHAB.md) | CODRGB->CODRGB | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
