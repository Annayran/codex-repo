# TCSEHI — Histórico de Etapas de Projeto (HistoricoEtapa)

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 19
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| HistoricoEtapa | HistoricoEtapa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Produto | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CONCLUIDA | Concluída | S |  |  | N |
| DTALTER | Data de alteração | H |  |  | N |
| DTCEDOFIMPREV | Início Limite | H |  |  | N |
| DTCEDOINIPREV | Início Previsto | H |  |  | N |
| DTTARDEFIMPREV | Término limite | H |  |  | N |
| DTTARDEINIPREV | Término previsto | H |  |  | N |
| NUFAP | Número da FAP | I |  |  | N |
| NUMETAPA | Número da Etapa | I |  |  | N |
| NUMSEQ | Número de sequência | I |  |  | N |
| SUPLEMENTA | Suplementa | S |  |  | N |
| SUPLEMENTADO | Suplementado | S |  |  | N |
| TIPVALOR | Tipo de Valor | S |  |  | N |
| VALOR | Valor | F |  |  | N |
| VLRSUPLEMENTA | Valor Suplementa | F |  |  | N |
| VLRSUPLEMENTADO | Valor Suplementado | F |  |  | N |
| VLRSUPLEMENTADOREA | Valor Suplementado Real | F |  |  | N |
| VLRSUPLEMENTAREA | Valor Suplementa Real | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EtapaProjeto / [TCSFET](TCSFET.md) | HistoricoEtapa / [TCSEHI](TCSEHI.md) | NUFAP->NUFAP; NUMETAPA->NUMETAPA | I |
| HistoricoEtapa / [TCSEHI](TCSEHI.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| HistoricoEtapa / [TCSEHI](TCSEHI.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
