# TGFPCP — Projecao de Compra

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 19
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ProjecaoDeCompra | Projeção de Compra | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | CODPROD | I |  |  | N |
| DEMANDAAJU | DEMANDAAJU | F |  |  | N |
| DTFINAL | DTFINAL | H |  |  | N |
| ESTDISPON | ESTDISPON | F |  |  | N |
| ESTFINAL | ESTFINAL | F |  |  | N |
| ESTFISICO | ESTFISICO | F |  |  | N |
| ESTSEG | ESTSEG | F |  |  | N |
| ESTSEGFINAL | ESTSEGFINAL | F |  |  | N |
| ESTSEGFINALDIAS | ESTSEGFINALDIAS | F |  |  | N |
| ESTSEGFINALSEMA | ESTSEGFINALSEMA | F |  |  | N |
| GIROMEDEST | GIROMEDEST | F |  |  | N |
| NUPCA | NUPCA | I |  |  | N |
| PEDCOMPEN | PEDCOMPEN | F |  |  | N |
| PEDCOMPLE | PEDCOMPLE | F |  |  | N |
| PEDVENPEN | PEDVENPEN | F |  |  | N |
| SAZOMEDIA | SAZOMEDIA | F |  |  | N |
| SAZONALIDADE | SAZONALIDADE | F |  |  | N |
| SEQPERIODO | SEQPERIODO | I |  |  | N |
| TIPOPERIODO | TIPOPERIODO | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItensPlanejamentoCompra / [TGFIPC](TGFIPC.md) | ProjecaoDeCompra / [TGFPCP](TGFPCP.md) | CODPROD->CODPROD; NUPCA->NUPCA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
