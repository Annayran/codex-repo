# TGFMPE — Montagem de Pedidos Eletrônicos

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 33
- Instâncias DWF: 1
- Relações DWF: 0
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MontagemPedidoEletronico | Montagem de Pedidos Eletrônicos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCONTATO | Contato do parceiro do acordo | I |  |  | N |
| CODEMP | Empresa do acordo | I |  |  | N |
| CODLOCAL | Local do produto do acordo | I |  |  | N |
| CODPARC | Cód. Parceiro do acordo | I |  |  | N |
| CODPROD | Produto do acordo | I |  |  | N |
| CODVOL | Volume do produto do acordo | S |  |  | N |
| CONTROLE | Controle de Estoque | S |  |  | N |
| DECQTD | Decimal da quantidade do acordo | I |  |  | N |
| DESCRLOCAL | Descrição do local | S |  |  | N |
| DESCRPROD | Descrição do produto | S |  |  | N |
| DISTORCAOGIRO | Distorção do giro | S |  |  | N |
| DTULTCOMPRA | Data da última compra | H |  |  | N |
| ESTMAX | Quantidade de estoque Máximo | F |  |  | N |
| ESTMIN | Quantidade de estoque Mínimo | F |  |  | N |
| ESTOQUE | Quantidade de estoque | F |  |  | N |
| GIRO | Quantidade de giro | F |  |  | N |
| MARCA | Marca do produto | S |  |  | N |
| NUACORDO | Número único do acordo | I |  |  | N |
| PENDENT | Pendente Entrada | F |  |  | N |
| PENDSAI | Pendente Saída | F |  |  | N |
| PRAZOENTREGA | Prazo de entrega do acordo | I |  |  | N |
| PRAZOFAT | Prazo de faturamento do acordo | I |  |  | N |
| PRAZOPAG | Prazo de pagto do acordo | I |  |  | N |
| QTDULTCOMPRA | Quantidade da última compra | F |  |  | N |
| REFERENCIA | Referência do produto | S |  |  | N |
| RODADA | Campo auxiliar para controlar rodada de produtos com fórmulas aninhadas | S |  |  | N |
| SEQUENCIA | Número sequencial | I |  |  | N |
| SITUACAO | Situação do acordo | S |  |  | N |
| SUGESTAO | Quantidade sugerida | F |  |  | N |
| TIPEST | Tipo de estoque | S |  |  | N |
| TIPPER | Tipo de período | S |  |  | N |
| VLRPROD | Valor do acordo | F |  |  | N |
| VLRULTCOMPRA | Valor da última compra | F |  |  | N |

## Relações DWF

Nenhuma relação DWF encontrada nesta exportação.

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
