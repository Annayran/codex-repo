# VGFCOM_FECH — Valores de Comissão p/ Fechamento

- Módulo técnico: [VGF](../modules/VGF.md)
- Campos: 45
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ViewComissaoFechamento | Valores de Comissão p/ Fechamento | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| APELIDO | Vendedor | S |  |  | N |
| CODCENCUSPAD | Cód.Centro Resultado Padrão | I |  |  | N |
| CODEMP | Cód. Empresa | I |  |  | N |
| CODEVENTO | Cód. Evento | I |  |  | N |
| CODFORM | Cód. Fórmula | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODPARCVEN | Cód. Parceiro Vendedor | I |  |  | N |
| CODTIPTIT | Cód. Tip. Título | I |  |  | N |
| CODTIPVENDA | Cód. Tip. Negoc. | I |  |  | N |
| CODVEND | Cód. Vendedor | I |  |  | N |
| COMISSAO | Comissão | F |  |  | N |
| COMLIQ | Comissão Parcela | F |  |  | N |
| COMLIQC | Comissão Parc. Líq. | F |  |  | S |
| DESCRFORM | Fórmula | S |  |  | N |
| DESCRNAT | Natureza | S |  |  | N |
| DESCROPER | Tipo Operação | S |  |  | N |
| DESDOBRAMENTO | Desdob. | S |  |  | N |
| DHBAIXA | Dt. Baixa | D |  |  | N |
| DHCONCILIACAO | Dt. Conciliação | D |  |  | N |
| DTFATUR | Dt. Fatur. | D |  |  | N |
| DTMOV | Dt. Movto. | D |  |  | N |
| DTNEG | Dt. Negoc. | D |  |  | N |
| DTVENC | Dt. Venc. | D |  |  | N |
| FINNUFIN | Nro. Único Fin. | I |  |  | N |
| INICEXEC | Dh. Execução | H |  |  | N |
| NOMEPARC | Parceiro | S |  |  | N |
| NOSSONUM | Nosso Número | S |  |  | N |
| NUFINORIG | Nro. Fin. Orig. | I |  |  | N |
| NUMITEMORIG | Nro. Item O.S. | I |  |  | N |
| NUMNOTA | Nro. Nota | I |  |  | N |
| NUMOSORIG | Nro. O.S. | I |  |  | N |
| NUNOTA | Nro. Único Nota | I |  |  | N |
| NUNOTAORIG | Nro Único Nota Orig. | I |  |  | N |
| PERCCOM | % Comissão | F |  |  | N |
| POSICAO_QUERY | POSICAO_QUERY | S |  |  | N |
| TAXA | Taxa Cartão | F |  |  | N |
| TIPCALC | Tip.Pgt.Comis. | S |  |  | N |
| TIPFATSERV | TIPFATSERV | S |  |  | N |
| TIPINTEGRA | Tipo de Integração | S |  |  | N |
| TIPMOV | Tip.Movto | S |  |  | N |
| TIPO | Tip. Comissão | S |  |  | N |
| VLRBAIXA | Vlr. Baixa | F |  |  | N |
| VLRCOM | Comissão Total | F |  |  | N |
| VLRDESC | Vlr Desc. | F |  |  | N |
| VLRDESDOB | Vlr. Desdob. | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ViewComissaoFechamento / [VGFCOM_FECH](VGFCOM_FECH.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ViewComissaoFechamento / [VGFCOM_FECH](VGFCOM_FECH.md) | Financeiro / [TGFFIN](TGFFIN.md) | FINNUFIN->NUFIN | L |
| ViewComissaoFechamento / [VGFCOM_FECH](VGFCOM_FECH.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ViewComissaoFechamento / [VGFCOM_FECH](VGFCOM_FECH.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
