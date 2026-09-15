# TCSVPC — Vendedores do Produto do Contrato

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 14
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| VendedorProdutoContrato | Vendedor do Produto do Contrato | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Produto | I |  |  | N |
| CODUSU | Cód. Usuário Alteração | I |  |  | N |
| CODVEND | Vendedor | I |  |  | N |
| DHALTER | Dt./Hora Alteração | H |  |  | N |
| DTFIM | Data Final | D |  |  | N |
| DTINI | Data Inicial | D |  |  | N |
| NUMCONTRATO | Contrato | I |  |  | N |
| NUNOTA | Nro. Único Pedido | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| PERCCOM | Percentual de Comissão | F |  |  | N |
| QTDFAT | Qtd. Faturada | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TIPO | Tipo | S |  |  | N |
| VLRBASE | Valor Base p/ Comissão | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | VendedorProdutoContrato / [TCSVPC](TCSVPC.md) | CODPROD->CODPROD; NUMCONTRATO->NUMCONTRATO | I |
| VendedorProdutoContrato / [TCSVPC](TCSVPC.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| VendedorProdutoContrato / [TCSVPC](TCSVPC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| VendedorProdutoContrato / [TCSVPC](TCSVPC.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
