# TCSLBE — Linha de base da etapa do projeto

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LinhaBaseEtapa | Linha de base da etapa do projeto | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | CODPROD | I |  |  | N |
| DTCEDOFIMPREV | DTCEDOFIMPREV | H |  |  | N |
| DTCEDOINIPREV | DTCEDOINIPREV | H |  |  | N |
| DTTARDEFIMPREV | DTTARDEFIMPREV | H |  |  | N |
| DTTARDEINIPREV | DTTARDEINIPREV | H |  |  | N |
| NUFAP | NUFAP | I |  |  | N |
| NUMETAPA | NUMETAPA | I |  |  | N |
| NUMETAPAPAI | NUMETAPAPAI | I |  |  | N |
| NUMSEQ | NUMSEQ | I |  |  | N |
| SEQLIBASE | SEQLIBASE | I |  |  | N |
| TIPVALOR | TIPVALOR | S |  |  | N |
| VALOR | VALOR | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| LinhaBaseEtapa / [TCSLBE](TCSLBE.md) | LinhaBaseProjeto / [TCSLBP](TCSLBP.md) | NUFAP->NUFAP | I |
| LinhaBaseEtapa / [TCSLBE](TCSLBE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
