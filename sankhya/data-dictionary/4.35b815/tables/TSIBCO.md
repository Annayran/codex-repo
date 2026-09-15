# TSIBCO — Bancos

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 5
- Instâncias DWF: 4
- Relações DWF: 25
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Banco | Banco | S | N |
| BancoIpi | Banco | S | N |
| CorrespondenteBancario | Correspondente Bancário | S | N |
| TimBanco | Banco | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ABREVIATURA | Abreviatura | S |  |  | N |
| CODBCO | Cód. Banco | I |  |  | N |
| CTACMC7FIM | Pos. Final (Conta CMC7) | I |  |  | N |
| CTACMC7INI | Pos. Inicial (Conta CMC7) | I |  |  | N |
| NOMEBCO | Descrição | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AgenciaBancaria / [TSIAGE](TSIAGE.md) | Banco / [TSIBCO](TSIBCO.md) | CODBCO->CODBCO | I |
| Banco / [TSIBCO](TSIBCO.md) | PrefixoCheque / [TGFCMC7](TGFCMC7.md) | CODBCO->CODBCO | L |
| Cheque / [TGFCHQ](TGFCHQ.md) | Banco / [TSIBCO](TSIBCO.md) | CODBCO->CODBCO | I |
| ConfiguracaoLancamentoExtrato / [TGFCLX](TGFCLX.md) | Banco / [TSIBCO](TSIBCO.md) | CODBCO->CODBCO | I |
| ContaBancaria / [TSICTA](TSICTA.md) | Banco / [TSIBCO](TSIBCO.md) | CODBCO->CODBCO | I |
| ContaBancaria / [TSICTA](TSICTA.md) | CorrespondenteBancario / [TSIBCO](TSIBCO.md) | CODCORRBCO->CODBCO | I |
| ContaDestino / [TSICTA](TSICTA.md) | Banco / [TSIBCO](TSIBCO.md) | CODBCO->CODBCO | I |
| Contato / [TGFCTT](TGFCTT.md) | Banco / [TSIBCO](TSIBCO.md) | TIMBANCO->CODBCO | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | Banco / [TSIBCO](TSIBCO.md) | EFDCODBCO->CODBCO | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | BancoIpi / [TSIBCO](TSIBCO.md) | CODBCOIPI->CODBCO | I |
| Financeiro / [TGFFIN](TGFFIN.md) | Banco / [TSIBCO](TSIBCO.md) | CODBCO->CODBCO | I |
| FinanceiroVendaRapida / [TGFFVR](TGFFVR.md) | Banco / [TSIBCO](TSIBCO.md) | CODBCO->CODBCO | I |
| OcorrenciaBancariaRetorno / [TSIOBC](TSIOBC.md) | Banco / [TSIBCO](TSIBCO.md) | CODBCO->CODBCO | I |
| Parceiro / [TGFPAR](TGFPAR.md) | Banco / [TSIBCO](TSIBCO.md) | CODBCO->CODBCO | I |
| ParcelaPagamento / [TGFPPG](TGFPPG.md) | Banco / [TSIBCO](TSIBCO.md) | CODBCOPAD->CODBCO | I |
| PrefixoCheque / [TGFCMC7](TGFCMC7.md) | Banco / [TSIBCO](TSIBCO.md) | CODBCO->CODBCO | I |
| TimContaBancariaLocador / [TIMCBL](TIMCBL.md) | Banco / [TSIBCO](TSIBCO.md) | CBLBANCO->CODBCO | I |
| TimContratoAdm / [TIMADM](TIMADM.md) | Banco / [TSIBCO](TSIBCO.md) | ADMDFAGENTEFINANCEIRO->CODBCO | I |
| TimContratoAdm / [TIMADM](TIMADM.md) | TimBanco / [TSIBCO](TSIBCO.md) | ADMFINANCIAMENTOBCO->CODBCO | I |
| TimLocacaoGarantiaCaucao / [TIMLGP](TIMLGP.md) | Banco / [TSIBCO](TSIBCO.md) | LGPBANCO->CODBCO | I |
| TimLocacaoGarantiaTituloCap / [TIMLTC](TIMLTC.md) | Banco / [TSIBCO](TSIBCO.md) | LTCBANCO->CODBCO | I |
| TimLoteamentoProprietarios / [TIMLTP](TIMLTP.md) | Banco / [TSIBCO](TSIBCO.md) | LTPREPBANCO->CODBCO | I |
| ViewFinanceiro / [VGFFIN](VGFFIN.md) | Banco / [TSIBCO](TSIBCO.md) | CODBCO->CODBCO | I |
| ViewFinanceiroEDI / [VGFFREM](VGFFREM.md) | Banco / [TSIBCO](TSIBCO.md) | CODBCO->CODBCO | I |
| ViewPrefixoCheque / [VGFCMC7](VGFCMC7.md) | Banco / [TSIBCO](TSIBCO.md) | CODBCO->CODBCO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
