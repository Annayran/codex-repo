# TGFMFO — Movimentos dos Financiamentos

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MovimentoFinanciamento | Movimentos dos Financiamentos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CARENCIA | Carência | I |  |  | N |
| CODTCF | Tabela Controle Financeiras | I |  |  | N |
| COEFICIENTE | Coeficiente | F |  |  | N |
| NUNOTA | Número único Nota | I |  |  | N |
| QTDPARCELAS | Quantidade Parcelas | I |  |  | N |
| TAXAJUR | Taxa Juro | F |  |  | N |
| VLRBOL | Valor boleto | F |  |  | N |
| VLRENTRADA | Valor Entrada | F |  |  | N |
| VLRFINANCIADO | Valor Financiado | F |  |  | N |
| VLRSEG | Valor Seguro | F |  |  | N |
| VLRTAC | Valor Tx. Abertura Crédito | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MovimentoFinanciamento / [TGFMFO](TGFMFO.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| MovimentoFinanciamento / [TGFMFO](TGFMFO.md) | ControleFinanceira / [TGFTCF](TGFTCF.md) | CODTCF->CODTCF | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
