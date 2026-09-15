# TGFIAC — Ítens do Acordo

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 24
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemAcordo | Ítens do Acordo | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AGRUPMIN | Agrupamento mínimo | F |  |  | N |
| CODLOCAL | Local | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODTIPVENDA | Tipo de Negociação | I |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| DISTORCAOGIRO | Distorção do Giro | I |  |  | N |
| GIROMEDDISTORCAO | Giro Médio para Cálculo da distorção | F |  |  | N |
| GIROZERO | Meses com giro 0 | S |  |  | N |
| NUACORDO | Número do Acordo | I |  |  | N |
| PERCACRESC | Percentual de Acréscimo | F |  |  | N |
| PERCDISTORC | Percentual de Distorção | F |  |  | N |
| PERCPRAZMAX | Percentual para prazo máximo | F |  |  | N |
| PERSEGESTMIN | Margem segurança estoque mínimo | F |  |  | N |
| PRAZOENTREGA | Prazo de Entrega | I |  |  | N |
| PRAZOFAT | Prazo de Faturamento | I |  |  | N |
| PRAZOPAG | Prazo de Pagamento | I |  |  | N |
| QTDMAXSUGCPA | Quantidade máxima para sugestão de cpa | F |  |  | N |
| QTDMIN | Quantidade mínima | F |  |  | N |
| QTDPER | Quantidade do período | I |  |  | N |
| SITUACAO | Situação | S |  |  | N |
| TAMPER | Tamanho do período | I |  |  | N |
| TIPCOMPRA | Tipo da Compra | S |  |  | N |
| TIPPER | Tipo de Período | S |  |  | N |
| VLRPROD | Valor do produto | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Acordo / [TGFACO](TGFACO.md) | ItemAcordo / [TGFIAC](TGFIAC.md) | NUACORDO->NUACORDO | I |
| ItemAcordo / [TGFIAC](TGFIAC.md) | Acordo / [TGFACO](TGFACO.md) | NUACORDO->NUACORDO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
