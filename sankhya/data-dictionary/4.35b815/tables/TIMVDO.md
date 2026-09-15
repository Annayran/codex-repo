# TIMVDO — Vinculo de documentos

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 28
- Instâncias DWF: 1
- Relações DWF: 20
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimVinculoDocumento | Vincular Documentos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| VDOCODIGO | Código | I |  |  | N |
| VDOCONTRATOCLAAVALOC | Avaliação Locação | I |  |  | N |
| VDOCONTRATOCLAAVAVEN | Avaliação Venda | I |  |  | N |
| VDOCONTRATOCLACORBAN | Correspondente Bancário | I |  |  | N |
| VDOCONTRATOCLAIMVLAN | Imóvel Lançamento | I |  |  | N |
| VDOCONTRATOCLAIMVLOC | Imóvel Locação | I |  |  | N |
| VDOCONTRATOCLAIMVREV | Imóvel Revenda | I |  |  | N |
| VDOCONTRATOCLAVENLAN | Venda de Imóvel - Lançamento | I |  |  | N |
| VDOCONTRATOCLAVENREV | Venda de Imóvel - Revenda | I |  |  | N |
| VDOCONTRATOLOC | Contrato de Locação | I |  |  | N |
| VDOCONTRATOLTV | Contrato Venda de Lote | I |  |  | N |
| VDOCORRETOR | Corretor | I |  |  | N |
| VDODESCRICAO | Descrição | S |  |  | N |
| VDODETALHES | Detalhes p/ Conf. | S |  |  | S |
| VDODHALT | Dh. Alteração | H |  |  | N |
| VDODHINC | Dh. Inclusão | H |  |  | N |
| VDOEMPREENDIMENTO | Empreendimento | I |  |  | N |
| VDOFICHAATEND | Ficha de Atendimento | I |  |  | N |
| VDOIMOVEL | Imóvel | I |  |  | N |
| VDOLOTE | Lote | I |  |  | N |
| VDOLOTEAMENTO | Loteamento | I |  |  | N |
| VDOPARCEIRO | Pessoa | I |  |  | N |
| VDOPASTA | Pasta | I |  |  | N |
| VDOPATHSCANNER | Pasta Documentos | S |  |  | N |
| VDOPROCESSADO | Processado | S |  |  | N |
| VDOPRODUTO | Origem | S |  |  | N |
| VDOUSUALT | Usuário Alterou | I |  |  | N |
| VDOUSUINC | Usuário Incluíu | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | Parceiro / [TGFPAR](TGFPAR.md) | VDOPARCEIRO->CODPARC | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimContratoVendaAvaLocacao / [TIMCLA](TIMCLA.md) | VDOCONTRATOCLAAVALOC->CLACODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimContratoVendaAvaVenda / [TIMCLA](TIMCLA.md) | VDOCONTRATOCLAAVAVEN->CLACODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimContratoVendaCorrespBanc / [TIMCLA](TIMCLA.md) | VDOCONTRATOCLACORBAN->CLACODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimContratoVendaImoLancamento / [TIMCLA](TIMCLA.md) | VDOCONTRATOCLAIMVLAN->CLACODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimContratoVendaImoLocacao / [TIMCLA](TIMCLA.md) | VDOCONTRATOCLAIMVLOC->CLACODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimContratoVendaImoRevenda / [TIMCLA](TIMCLA.md) | VDOCONTRATOCLAIMVREV->CLACODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimContratoVendaLancamento / [TIMCLA](TIMCLA.md) | VDOCONTRATOCLAVENLAN->CLACODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimContratoVendaLote / [TIMLTV](TIMLTV.md) | VDOCONTRATOLTV->LTVCODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimContratoVendaRevenda / [TIMCLA](TIMCLA.md) | VDOCONTRATOCLAVENREV->CLACODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimCorretor / [TIMCOR](TIMCOR.md) | VDOCORRETOR->CORCODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimEmpreendimento / [TIMEPR](TIMEPR.md) | VDOEMPREENDIMENTO->EPRCODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | VDOFICHAATEND->FACCODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimImovel / [TIMIMV](TIMIMV.md) | VDOIMOVEL->IMVCODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimLocacao / [TIMLOC](TIMLOC.md) | VDOCONTRATOLOC->LOCCODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimLote / [TIMLTE](TIMLTE.md) | VDOLOTE->LTECODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimLoteamento / [TIMLOT](TIMLOT.md) | VDOLOTEAMENTO->LOTCODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimPastaDocumento / [TIMPVD](TIMPVD.md) | VDOPASTA->PVDCODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | Usuario / [TSIUSU](TSIUSU.md) | VDOUSUINC->CODUSU | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | Usuario2 / [TSIUSU](TSIUSU.md) | VDOUSUALT->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
