# TGFCVP — TABLE TGFCVP

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 36
- Instâncias DWF: 1
- Relações DWF: 7
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CompraPendente | CompraPendente | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALIQICMS | Alíq. ICMS | F |  |  | N |
| ALIQIPI | Alíq. IPI | F |  |  | N |
| BASEICMS | Base ICMS | F |  |  | N |
| BASEIPI | Base do IPI | F |  |  | N |
| BASESUBSTIT | Base substituição | F |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODLOCALORIG | Local origem | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODREL | CODREL | I |  |  | N |
| CODTIPOPER | Tipo Operação | I |  |  | N |
| CODTIPVENDA | Tipo Negociação | I |  |  | N |
| CODVOL | Unidade | S |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| DHTIPOPER | Dt./Hr. Operação | H |  |  | N |
| DHTIPVENDA | Dt./Hr. Tipo de Neg. | H |  |  | N |
| DTENTSAI | Dt. Entrada/Saída | H |  |  | N |
| DTFATUR | Dt. do Faturamento | H |  |  | N |
| DTMOV | Dt. do Movimento | H |  |  | N |
| DTNEG | Dt. Neg. | H |  |  | N |
| NUMNOTA | Nro. Nota | I |  |  | N |
| NUNOTA | Nro. Único | I |  |  | N |
| PENDENTE | Pendente | S |  |  | N |
| QTDENTREGUE | Qtd. entregue | F |  |  | N |
| QTDNEG | Quantidade | F |  |  | N |
| QTDPEND | Quantidade Pendente | F |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |
| STATUSNOTA | Status da Nota | S |  |  | N |
| TIPMOV | Tipo de Movimento | S |  |  | N |
| VLRCUS | Vlr. custos | F |  |  | N |
| VLRDESC | Vlr. desconto | F |  |  | N |
| VLRICMS | Vlr. ICMS | F |  |  | N |
| VLRIPI | Vlr. IPI | F |  |  | N |
| VLRSUBST | Vlr. substituição | F |  |  | N |
| VLRTOT | Vlr. total | F |  |  | N |
| VLRUNIT | Vlr. unitário | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CompraPendente / [TGFCVP](TGFCVP.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| CompraPendente / [TGFCVP](TGFCVP.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| CompraPendente / [TGFCVP](TGFCVP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| CompraPendente / [TGFCVP](TGFCVP.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| CompraPendente / [TGFCVP](TGFCVP.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODTIPVENDA->CODTIPVENDA; DHTIPVENDA->DHALTER | I |
| CompraPendente / [TGFCVP](TGFCVP.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER; DHTIPOPER->DHALTER | I |
| CompraPendente / [TGFCVP](TGFCVP.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
