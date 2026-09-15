# TCSSPNSEQ — Histórico simulação preço negociação

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| HistoricoSimulacaoPrecoNeg | Histórico da Simulação de Preço da Negociação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cód. Usuário | I |  |  | N |
| DTALTER | Dt. Ateração | H |  |  | N |
| NUMOS | Núm. da OS | I |  |  | N |
| SEQPROPOSTA | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PropostaNegociacaoPreVenda / [TCSPNP](TCSPNP.md) | HistoricoSimulacaoPrecoNeg / [TCSSPNSEQ](TCSSPNSEQ.md) | NUMOS->NUMOS; SEQPROPOSTA->SEQPROPOSTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
