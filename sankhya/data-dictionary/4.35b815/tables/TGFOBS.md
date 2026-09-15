# TGFOBS — Observações para Notas

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 11
- Instâncias DWF: 3
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ObservacaoNotasFiscais | Observação para Notas Fiscais | S | N |
| ObservacaoNotasFiscaisAju | Observação para Notas Fiscais | S | N |
| ObservacaoNotasFiscaisDoc | Observação para Notas Fiscais | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODOBSPADRAO | Código | I |  |  | N |
| CODREFINFCOMPL | Código de referência à informação complementar | S |  |  | N |
| COMPLEMENTOEFD | Carrega Complemento p/ EFD | S |  |  | N |
| DTALTER | Data | D |  |  | N |
| GERAREFD | Geração no EFD | S |  |  | N |
| NROPROCESSO | Num. Processo | S |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| OPERESSACOMP | Operação com Ressarcimento/Complemento de ST | S |  |  | N |
| ORIGPROCESSO | Origem Processo | I |  |  | N |
| QTDLINHAS | Qtde de Linhas | I |  |  | N |
| VINCDOCARREC | Vincular DAE/GNRE | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AliquotaICMS / [TGFICM](TGFICM.md) | ObservacaoNotasFiscais / [TGFOBS](TGFOBS.md) | CODOBSPADRAO->CODOBSPADRAO | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ObservacaoNotasFiscais / [TGFOBS](TGFOBS.md) | CODOBSPADRAO->CODOBSPADRAO | I |
| Contrato / [TCSCON](TCSCON.md) | ObservacaoNotasFiscais / [TGFOBS](TGFOBS.md) | CODOBS->CODOBSPADRAO | I |
| Financeiro / [TGFFIN](TGFFIN.md) | ObservacaoNotasFiscais / [TGFOBS](TGFOBS.md) | CODOBSPADRAO->CODOBSPADRAO | I |
| TipoNegociacao / [TGFTPV](TGFTPV.md) | ObservacaoNotasFiscais / [TGFOBS](TGFOBS.md) | CODOBSPADRAO->CODOBSPADRAO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
