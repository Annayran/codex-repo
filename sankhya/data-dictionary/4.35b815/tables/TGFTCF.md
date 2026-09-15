# TGFTCF — Controle das Financeiras

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ControleFinanceira | Controle das Financeiras | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODTCF | Código | I |  |  | N |
| NOMETAB | Nome Tabela | S |  |  | N |
| TAXAJUR | Taxa Juro | F |  |  | N |
| VLRBOL | Valor boleto | F |  |  | N |
| VLRSEG | Valor Seguro | F |  |  | N |
| VLRTAC | Valor Taxa Abertura Crédito | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItemCoeficienteFinanciamento / [TGFICF](TGFICF.md) | ControleFinanceira / [TGFTCF](TGFTCF.md) | CODTCF->CODTCF | I |
| MovimentoFinanciamento / [TGFMFO](TGFMFO.md) | ControleFinanceira / [TGFTCF](TGFTCF.md) | CODTCF->CODTCF | I |
| TipoNegociacao / [TGFTPV](TGFTPV.md) | ControleFinanceira / [TGFTCF](TGFTCF.md) | CODTCF->CODTCF | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
