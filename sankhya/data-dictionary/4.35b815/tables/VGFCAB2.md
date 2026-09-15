# VGFCAB2 — View Cabeçalho da Nota

- Módulo técnico: [VGF](../modules/VGF.md)
- Campos: 33
- Instâncias DWF: 1
- Relações DWF: 12
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ViewCabecalhoNota | View do Cabeçalho da Nota | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| APELIDOVENDCR | Apelido Vend. CR | S |  |  | N |
| CODASSESSOR | Assessor Parc. | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODEXEC | Executante | I |  |  | N |
| CODGERASSESSOR | Ger. Assessor | I |  |  | N |
| CODGEREXEC | Ger. Executante | I |  |  | N |
| CODGERVEND | Ger. Vend/Comp | I |  |  | N |
| CODGERVENDCR | Ger. Vend/Comp CR | I |  |  | N |
| CODGERVENDITE | Ger. Vend/Comp Item | I |  |  | N |
| CODGERVENDLAN | Ger. Vend/Comp Lanç. | I |  |  | N |
| CODGERVENDPARC | Ger. Parc. | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODTIPOPER | Cód.Tipo Operação | I |  |  | N |
| CODTIPVENDA | Tipo Negoc. | I |  |  | N |
| CODVEND | Vend/Comp | I |  |  | N |
| CODVENDCR | Vend. CR | I |  |  | N |
| CODVENDITE | Vend/Comp Item | I |  |  | N |
| CODVENDLAN | Vend/Comp Lanç. | I |  |  | N |
| CODVENDPARC | Vend/Comp Parc. | I |  |  | N |
| COMGER | % Comissão Gerente | F |  |  | N |
| COMISSAO | % Comissão | F |  |  | N |
| DHTIPOPER | Dt./Hr. Operação | D |  |  | N |
| DHTIPVENDA | Dt./Hr. Tipo Negoc. | D |  |  | N |
| DTFATUR | Dt. Faturamento | D |  |  | N |
| DTMOV | Dt. Movimento | D |  |  | N |
| DTNEG | Dt. Negociação | D |  |  | N |
| NUMNOTA | Nro. Nota | I |  |  | N |
| NUNOTA | Nro. Único Nota | I |  |  | N |
| SERIENOTA | Série | S |  |  | N |
| TIPMOV | Tipo Mov. | S |  |  | N |
| VLRCOMGER | Vlr. Comissão Ger. | F |  |  | S |
| VLRCOMISSAO | Vlr. Comissão | F |  |  | S |
| VLRNOTA | Vlr. Nota | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ViewCabecalhoNota / [VGFCAB2](VGFCAB2.md) | Assessor / [TGFVEN](TGFVEN.md) | CODASSESSOR->CODVEND | L |
| ViewCabecalhoNota / [VGFCAB2](VGFCAB2.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ViewCabecalhoNota / [VGFCAB2](VGFCAB2.md) | ExecutanteItem / [TGFVEN](TGFVEN.md) | CODEXEC->CODVEND | L |
| ViewCabecalhoNota / [VGFCAB2](VGFCAB2.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ViewCabecalhoNota / [VGFCAB2](VGFCAB2.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODTIPVENDA->CODTIPVENDA; DHTIPVENDA->DHALTER | I |
| ViewCabecalhoNota / [VGFCAB2](VGFCAB2.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER; DHTIPOPER->DHALTER | I |
| ViewCabecalhoNota / [VGFCAB2](VGFCAB2.md) | ValorComissaoVendedor / [TGFCOM](TGFCOM.md) | NUNOTA->NUNOTAORIG | L |
| ViewCabecalhoNota / [VGFCAB2](VGFCAB2.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | L |
| ViewCabecalhoNota / [VGFCAB2](VGFCAB2.md) | VendedorCentroResultado / [TGFVEN](TGFVEN.md) | CODVENDCR->CODVEND | L |
| ViewCabecalhoNota / [VGFCAB2](VGFCAB2.md) | VendedorItem / [TGFVEN](TGFVEN.md) | CODVENDITE->CODVEND | L |
| ViewCabecalhoNota / [VGFCAB2](VGFCAB2.md) | VendedorLancamento / [TGFVEN](TGFVEN.md) | CODVENDLAN->CODVEND | L |
| ViewCabecalhoNota / [VGFCAB2](VGFCAB2.md) | VendParceiro / [TGFVEN](TGFVEN.md) | CODVENDPARC->CODVEND | L |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
