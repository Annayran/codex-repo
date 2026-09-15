# TGFVOL — Volumes

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 8
- Instâncias DWF: 6
- Relações DWF: 36
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| UnidadeCompra | Unidade para compras | S | S |
| UnidadeSeparacaoPesoVariavel | Unidade de Separação para Peso Variavel | N | N |
| Volume | Unidade | S | S |
| VolumeFETHAB | Volume FETHAB | S | N |
| VolumeKanban | Unidade de movimentação padrão | S | N |
| VolumeResumoEntrega | Volume para Resumo de Entrega | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATUNUVERSAO | Atualizar versão | S |  |  | N |
| CODVOL | Sigla da Unidade | S |  |  | N |
| CODVOLFCI | Unidade para FCI | S |  |  | N |
| DECQTD | Decimais para quantidade | I |  |  | N |
| DESCRVOL | Descrição | S |  |  | N |
| NUVERSAO | Versão | I |  |  | N |
| UTILICONFPESO | Utiliza Conferência por peso | S |  |  | N |
| UTILIREGVOLWMS | Utiliza no Registro de Volumes WMS | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ApuracaoProPreAcordoVerba / [TGFGVA](TGFGVA.md) | Volume / [TGFVOL](TGFVOL.md) | UNIDADE->CODVOL | I |
| BaseStMediaVendaPro / [TGFSTMVPRO](TGFSTMVPRO.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| Carrinho / [TGFCAC](TGFCAC.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| CheckoutItem / [TGFCKI](TGFCKI.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| CodigoBarras / [TGFBAR](TGFBAR.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| ColetaItemCotacao / [TGFITC](TGFITC.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| CompraPendente / [TGFCVP](TGFCVP.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| ContagemEstoque / [TGFCTE](TGFCTE.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| Desconto / [TGFDES](TGFDES.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| DetalhesConferencia / [TGFCOI2](TGFCOI2.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| EstoqueMinMax / [TGFEMM](TGFEMM.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| HistoricoUnidadeAlternativa / [TGFVOAH](TGFVOAH.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| InsumoEtapa / [TCSFPE](TCSFPE.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| InsumoModeloEtapa / [TCSMPR](TCSMPR.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| ItemComposicaoProduto / [TGFICP](TGFICP.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| ItemCotacao / [TGFITC](TGFITC.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| ItemNota / [TGFITE](TGFITE.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| ItemNotaExcluida / [TGFITE_EXC](TGFITE_EXC.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| ItemPedidoVR / [TGFIVR](TGFIVR.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| MetaAtual / [TGMMET](TGMMET.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| PlanoManutencao / [TGFEPP](TGFEPP.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| Produto / [TGFPRO](TGFPRO.md) | UnidadeCompra / [TGFVOL](TGFVOL.md) | CODVOLCOMPRA->CODVOL | I |
| Produto / [TGFPRO](TGFPRO.md) | UnidadeSeparacaoPesoVariavel / [TGFVOL](TGFVOL.md) | CODVOLPESOVAR->CODVOL | I |
| Produto / [TGFPRO](TGFPRO.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| Produto / [TGFPRO](TGFPRO.md) | VolumeFETHAB / [TGFVOL](TGFVOL.md) | CODVOLFETHAB->CODVOL | I |
| Produto / [TGFPRO](TGFPRO.md) | VolumeKanban / [TGFVOL](TGFVOL.md) | CODVOLKANBAN->CODVOL | I |
| Produto / [TGFPRO](TGFPRO.md) | VolumeResumoEntrega / [TGFVOL](TGFVOL.md) | CODVOLRES->CODVOL | I |
| ProdutoDivergente / [TGFPDD](TGFPDD.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| ProdutosPreAcordoVerba / [TGFGVR](TGFGVR.md) | Volume / [TGFVOL](TGFVOL.md) | UNIDADE->CODVOL | I |
| RebaixaDeCusto / [TGFDRC](TGFDRC.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| RelacionamentoParceiroProduto / [TGFPAP](TGFPAP.md) | Volume / [TGFVOL](TGFVOL.md) | UNIDADEPARC->CODVOL | I |
| Servico / [TGFPRO](TGFPRO.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| UnidadeMovArmazenagemProduto / [TGFPUMA](TGFPUMA.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| VendaPendente / [TGFVCP](TGFVCP.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| Volume / [TGFVOL](TGFVOL.md) | UnidadeFiscalParceiro / [TGFUNP](TGFUNP.md) | CODVOL->CODVOL | I |
| VolumeAlternativo / [TGFVOA](TGFVOA.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
