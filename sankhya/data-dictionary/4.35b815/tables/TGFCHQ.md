# TGFCHQ — TABLE TGFCHQ

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Cheque | Cheque | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AGENCIA | Agência | S |  |  | N |
| CGC_CPF | CNPJ/CPF do Emitente | S |  |  | N |
| CMC7 | CMC7 | S |  |  | N |
| CODBCO | Banco | I |  |  | N |
| CONTA | Conta | S |  |  | N |
| DATACHEQUE | Data do Cheque | D |  |  | N |
| NOMEEMITENTE | Emitente | S |  |  | N |
| NUCHQ | Nro. Único do Cheque | I |  |  | N |
| NUFIN | Nro. Financeiro | I |  |  | N |
| NUMCHEQUE | Nro. do Cheque | S |  |  | N |
| STATUS | Status | S |  |  | N |
| VLRCHEQUE | Vlr Cheque | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Cheque / [TGFCHQ](TGFCHQ.md) | Banco / [TSIBCO](TSIBCO.md) | CODBCO->CODBCO | I |
| Cheque / [TGFCHQ](TGFCHQ.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| EventosCheque / [TGFECQ](TGFECQ.md) | Cheque / [TGFCHQ](TGFCHQ.md) | NUCHQ->NUCHQ | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
