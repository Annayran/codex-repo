# TGFSLT — Tabela Sequência de lote

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| SequenciaLote | Sequência de Lote | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODBARRA | Cód. Barras | S |  |  | N |
| IDENTIFICADOR | Identificador | S |  |  | N |
| NUCONF | Nro. Conferência | I |  |  | N |
| SEQCONF | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| SequenciaLote / [TGFSLT](TGFSLT.md) | DetalhesConferencia / [TGFCOI2](TGFCOI2.md) | NUCONF->NUCONF; SEQCONF->SEQCONF | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
