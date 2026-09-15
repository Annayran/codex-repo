# TGFMON — GF Modelo para notas fiscais

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 12
- Relações DWF: 19
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ModeloDACTE | Modelo DACTE | S | N |
| ModeloDadosAdicionais | Modelo de dados adicionais para NFe | S | N |
| ModeloDanfe | Modelo Danfe | S | N |
| ModeloDanfeContingencia | Modelo Danfe Contingencia | S | N |
| ModeloDanfeNFCeCompleto | Modelo DANFE NFC-e Completo | S | N |
| ModeloDanfeNFCeSimplificado | Modelo DANFE NFC-e Simplificado | S | N |
| ModeloImpressaoCfeCanc | Modelo de Impressão CF-e Cancelamento | S | N |
| ModeloNFSe | Modelos para NFS-e | S | N |
| ModeloNotaFiscal | Modelo de Impressão | S | N |
| ModeloNotaFiscalEstoque | Modelo Nota Fiscal Estoque | S | N |
| ModeloRoman | Modelo para Romaneio | S | N |
| ModeloRPS | Modelo para RPS | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CAMINHO | Caminho | S |  |  | N |
| CAMINHOSW | Caminho Rep. Arquivos | S |  |  | N |
| CODMODNF | Modelo | I |  |  | N |
| NOME | Nome | S |  |  | N |
| NURFE | Número do relatório modelo | I |  |  | N |
| TIPOIMPRESSORA | Tipo de impressora | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ContaBancaria / [TSICTA](TSICTA.md) | ModeloNotaFiscal / [TGFMON](TGFMON.md) | MODBOLETA->CODMODNF | I |
| ControleNumeracao / [TGFNUM](TGFNUM.md) | ModeloNotaFiscal / [TGFMON](TGFMON.md) | MODNOTAFIS->CODMODNF | I |
| EmailTOP / [TGFTEM](TGFTEM.md) | ModeloNotaFiscal / [TGFMON](TGFMON.md) | MODELO->CODMODNF | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloDACTE / [TGFMON](TGFMON.md) | CODMODDACTE->CODMODNF | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloDanfe / [TGFMON](TGFMON.md) | CODMODDANFE->CODMODNF | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloDanfeContingencia / [TGFMON](TGFMON.md) | CODMODDANFECONTINGENCIA->CODMODNF | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloDanfeNFCeCompleto / [TGFMON](TGFMON.md) | CODMODNFCECOMPL->CODMODNF | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloDanfeNFCeSimplificado / [TGFMON](TGFMON.md) | CODMODNFCESIMPL->CODMODNF | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloNotaFiscal / [TGFMON](TGFMON.md) | MODDUPL->CODMODNF | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloNotaFiscalEstoque / [TGFMON](TGFMON.md) | MODEXPED->CODMODNF | I |
| ItensPacote / [TSSITP](TSSITP.md) | ModeloNotaFiscal / [TGFMON](TGFMON.md) | CODMODNF->CODMODNF | I |
| ModeloNotaFiscal / [TGFMON](TGFMON.md) | Relatorio / [TSIRFE](TSIRFE.md) | NURFE->NURFE | I |
| ModeloNotaFiscalEstoque / [TGFMON](TGFMON.md) | Relatorio / [TSIRFE](TSIRFE.md) | NURFE->NURFE | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | ModeloDadosAdicionais / [TGFMON](TGFMON.md) | CODMODDAD->CODMODNF | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | ModeloImpressaoCfeCanc / [TGFMON](TGFMON.md) | CODMODCFECANC->CODMODNF | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | ModeloNFSe / [TGFMON](TGFMON.md) | CODMODNFSE->CODMODNF | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | ModeloNotaFiscal / [TGFMON](TGFMON.md) | CODMODNF->CODMODNF | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | ModeloRoman / [TGFMON](TGFMON.md) | CODMODRO->CODMODNF | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | ModeloRPS / [TGFMON](TGFMON.md) | CODMODRPS->CODMODNF | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
