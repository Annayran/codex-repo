# TGFTRC — Transferência de Cheques

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 8
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TransferenciaCheque | Transferência de Cheques | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCTABCOINTDEST | Conta Destino | I |  |  | N |
| CODCTABCOINTORIG | Conta Origem | I |  |  | N |
| CODEMPBAIXAORIG | Empresa Baixa Origem | I |  |  | N |
| CODTIPTITORIG | Tipo Título Origem | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DTALTER | Data Alteração | H |  |  | N |
| NUBCODEST | Número Banco Destino | I |  |  | N |
| NUBCOORIG | Número Banco Origem | I |  |  | N |
| NUFIN | Número único Financeiro | I |  |  | N |
| NUMTRANSF | Número Tranferência | I |  |  | N |
| TIPOTRANSF | TIPOTRANSF | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TransferenciaCheque / [TGFTRC](TGFTRC.md) | ContaDestino / [TSICTA](TSICTA.md) | CODCTABCOINTDEST->CODCTABCOINT | I |
| TransferenciaCheque / [TGFTRC](TGFTRC.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMPBAIXAORIG->CODEMP | I |
| TransferenciaCheque / [TGFTRC](TGFTRC.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| TransferenciaCheque / [TGFTRC](TGFTRC.md) | MovimentacaoBancariaDestino / [TGFMBC](TGFMBC.md) | NUBCODEST->NUBCO | I |
| TransferenciaCheque / [TGFTRC](TGFTRC.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | CODTIPTITORIG->CODTIPTIT | I |
| TransferenciaCheque / [TGFTRC](TGFTRC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ViewFinanceiro / [VGFFIN](VGFFIN.md) | TransferenciaCheque / [TGFTRC](TGFTRC.md) | NUFIN->NUFIN | I |
| ViewFinanceiroEDI / [VGFFREM](VGFFREM.md) | TransferenciaCheque / [TGFTRC](TGFTRC.md) | NUFIN->NUFIN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
