# TGFSEG — Seguros de Transporte

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| SeguroTransporte | Seguro de Transporte | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NOMESEG | Nome da Seguradora | S |  |  | N |
| NUMAPO | Nro. Apólice | S |  |  | N |
| NUMAVE | Nro. Averbação | S |  |  | N |
| NUNOTA | Nro. Único da Nota | I |  |  | N |
| RESPSEG | Responsável pelo Seguro | S |  |  | N |
| SEQSEG | Sequência do Responsável pelo Seguro | I |  |  | N |
| VLRCARGA | Valor da Carga | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | SeguroTransporte / [TGFSEG](TGFSEG.md) | NUNOTA->NUNOTA | I |
| SeguroTransporte / [TGFSEG](TGFSEG.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
