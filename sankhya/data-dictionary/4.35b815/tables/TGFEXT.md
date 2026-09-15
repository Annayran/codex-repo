# TGFEXT — Extrato de Vendedores

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ExtratoVendedor | Extrato de Vendedores | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODHISTCOM | Histórico de comissão | I |  |  | N |
| CODVEND | Vendedor | I |  |  | N |
| DTLANC | Data do lançamento | H |  |  | N |
| DTPAG | Data pagamento | H |  |  | N |
| LIBERADO | Liberado | S |  |  | N |
| NUMRECIBO | Número do recibo | I |  |  | N |
| NUNOTA | Número único Nota | I |  |  | N |
| ORIGEM | Origem | S |  |  | N |
| TIPLANC | Tipo do lançamento | I |  |  | N |
| VLRDISP | Valor disponível | F |  |  | N |
| VLRLANC | Valor do lançamento | F |  |  | N |
| VLRNOTA | Valor da nota | F |  |  | N |
| VLRPAGO | Valor pago | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ExtratoVendedor / [TGFEXT](TGFEXT.md) | HistoricoComissoes / [TGFHCO](TGFHCO.md) | CODHISTCOM->CODHISTCOM | I |
| ExtratoVendedor / [TGFEXT](TGFEXT.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
