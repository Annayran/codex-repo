# TCSLBR — Linha Base Requisito

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LinhaBaseRequisito | Linha de Base Requisito | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CASOUSO | CASOUSO | S |  |  | N |
| CODREQ | CODREQ | I |  |  | N |
| DESCRICAO | DESCRICAO | S |  |  | N |
| NOME | NOME | S |  |  | N |
| NUFAP | NUFAP | I |  |  | N |
| PESO | PESO | F |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |
| TODAETAPA | TODAETAPA | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| LinhaBaseRequisito / [TCSLBR](TCSLBR.md) | LinhaBaseProjeto / [TCSLBP](TCSLBP.md) | NUFAP->NUFAP; SEQUENCIA->SEQUENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
