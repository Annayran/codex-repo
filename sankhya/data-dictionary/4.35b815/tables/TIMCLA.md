# TIMCLA — Contratos de Venda

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 38
- Instâncias DWF: 9
- Relações DWF: 27
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimContratoVenda | Contratos de Venda | S | N |
| TimContratoVendaAvaLocacao | Avaliação - Locação | S | N |
| TimContratoVendaAvaVenda | Avaliação - Venda | S | N |
| TimContratoVendaCorrespBanc | Corresp. Bancário | S | N |
| TimContratoVendaImoLancamento | Imóvel - Lançamento | S | N |
| TimContratoVendaImoLocacao | Imóvel - Locação | S | N |
| TimContratoVendaImoRevenda | Imóvel - Revenda | S | N |
| TimContratoVendaLancamento | Venda de Imóvel - Lançamento | S | N |
| TimContratoVendaRevenda | Venda de Imóvel - Revenda | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CLAACC | Ação de Conciliação | S |  |  | N |
| CLACODIGO | Código | I |  |  | N |
| CLACOMISSAO | Comissão (%) | F |  |  | N |
| CLACOMISSAOVALOR | Vlr. Comissão Recebida | F |  |  | N |
| CLACONDICOESDAPOSSE | Condições Posse | S |  |  | N |
| CLACONDICOESDAVENDA | Condições Venda | S |  |  | N |
| CLACONTRATOADM | Contrato de Adm. | I |  |  | N |
| CLACORRBANCARIO | Vlr. Corresp. Bancário | F |  |  | N |
| CLACORRETOR | Corretor | I |  |  | N |
| CLADATAASSINATURA | Dt. Assinatura | D |  |  | N |
| CLADATACANCELAMENTO | Dt. Cancelamento | D |  |  | N |
| CLADATADAPROPOSTA | Dt. Proposta | D |  |  | N |
| CLADATADIGITACAO | Dh. Cadastro | H |  |  | N |
| CLADATAMOTIFICADAO | Dt. Notificação | D |  |  | N |
| CLADESCRICAO | Descrição | S |  |  | S |
| CLADHALTER | Dh. Alteração | H |  |  | N |
| CLAEMPREENDIMENTO | Empreendimento | I |  |  | N |
| CLAENTREGACHAVES | Dt. Entrega de Chaves | D |  |  | N |
| CLAESTAGIO | Estágio | S |  |  | N |
| CLAGERENTECOR | Supervisor | I |  |  | N |
| CLAGERGERCOR | Gerente | I |  |  | N |
| CLAIMOBILIARIA | Imobiliária | I |  |  | N |
| CLAIMOVEL | Imóvel | I |  |  | N |
| CLAINVESTIMENTO | Finalidade | S |  |  | N |
| CLAJUSTICACOMUN | Justiça Comum | S |  |  | N |
| CLANAOCOBRAR | Não Cobrar | S |  |  | N |
| CLANC | Notif. Cartorial | S |  |  | N |
| CLANUCHECKLIST | CheckList do contrato | I |  |  | N |
| CLAPE | Por Edital | S |  |  | N |
| CLAPERCENTUALPARCERIA | Parceria (%) | F |  |  | N |
| CLAPROTOCOLO | Protocolo | I |  |  | N |
| CLATIPO | Tipo do Contrato | S |  |  | N |
| CLAVALORDOSINAL | Vlr. Sinal | F |  |  | N |
| CLAVALORMETA | Vlr. Final | F |  |  | N |
| CLAVALORTOTAL | Vlr. Total | F |  |  | N |
| CLAVGV | Valor Geral de Venda | F |  |  | N |
| FILTROCOMPRADOR | Comprador | I |  |  | S |
| FILTROVENDEDOR | Vendedor | I |  |  | S |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | TimContratoVenda / [TIMCLA](TIMCLA.md) | TIMCONTRATOVENDA->CLACODIGO | I |
| TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | TimContratoVenda / [TIMCLA](TIMCLA.md) | SACVENDAIMV->CLACODIGO | I |
| TimCheckList / [TIMCHK](TIMCHK.md) | TimContratoVenda / [TIMCLA](TIMCLA.md) | CHKCONTRATOCLA->CLACODIGO | I |
| TimCompradorVenda / [TIMCLC](TIMCLC.md) | TimContratoVenda / [TIMCLA](TIMCLA.md) | CLCCONTRATO->CLACODIGO | I |
| TimContratoVenda / [TIMCLA](TIMCLA.md) | Parceiro / [TGFPAR](TGFPAR.md) | FILTROCOMPRADOR->CODPARC | I |
| TimContratoVenda / [TIMCLA](TIMCLA.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | FILTROVENDEDOR->CODPARC | I |
| TimContratoVenda / [TIMCLA](TIMCLA.md) | TimCheckList / [TIMCHK](TIMCHK.md) | CLANUCHECKLIST->CHKNUCHECK | I |
| TimContratoVenda / [TIMCLA](TIMCLA.md) | TimCompradorVenda / [TIMCLC](TIMCLC.md) | CLACODIGO->CLCCONTRATO | I |
| TimContratoVenda / [TIMCLA](TIMCLA.md) | TimContratoAdm / [TIMADM](TIMADM.md) | CLACONTRATOADM->ADMNUCONTRATO | I |
| TimContratoVenda / [TIMCLA](TIMCLA.md) | TimCorretor / [TIMCOR](TIMCOR.md) | CLACORRETOR->CORCODIGO | I |
| TimContratoVenda / [TIMCLA](TIMCLA.md) | TimCorretorGerente / [TIMCOR](TIMCOR.md) | CLAGERENTECOR->CORCODIGO | I |
| TimContratoVenda / [TIMCLA](TIMCLA.md) | TimCorretorGerente2 / [TIMCOR](TIMCOR.md) | CLAGERGERCOR->CORCODIGO | I |
| TimContratoVenda / [TIMCLA](TIMCLA.md) | TimEmpreendimento / [TIMEPR](TIMEPR.md) | CLAEMPREENDIMENTO->EPRCODIGO | I |
| TimContratoVenda / [TIMCLA](TIMCLA.md) | TimImobiliaria / [TIMIMB](TIMIMB.md) | CLAIMOBILIARIA->IMBCODIGO | I |
| TimContratoVenda / [TIMCLA](TIMCLA.md) | TimImovel / [TIMIMV](TIMIMV.md) | CLAIMOVEL->IMVCODIGO | I |
| TimContratoVenda / [TIMCLA](TIMCLA.md) | TimParcelamento / [TIMQPV](TIMQPV.md) | CLACODIGO->QPVCONTRATO | I |
| TimContratoVenda / [TIMCLA](TIMCLA.md) | TimRateioComisaoVenda / [TIMRCL](TIMRCL.md) | CLACODIGO->RCLCONTRATO | I |
| TimMFD / [TIMMFD](TIMMFD.md) | TimContratoVenda / [TIMCLA](TIMCLA.md) | MFDCONTRATOCLA->CLACODIGO | I |
| TimRateioComisaoVenda / [TIMRCL](TIMRCL.md) | TimContratoVenda / [TIMCLA](TIMCLA.md) | RCLCONTRATO->CLACODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimContratoVendaAvaLocacao / [TIMCLA](TIMCLA.md) | VDOCONTRATOCLAAVALOC->CLACODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimContratoVendaAvaVenda / [TIMCLA](TIMCLA.md) | VDOCONTRATOCLAAVAVEN->CLACODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimContratoVendaCorrespBanc / [TIMCLA](TIMCLA.md) | VDOCONTRATOCLACORBAN->CLACODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimContratoVendaImoLancamento / [TIMCLA](TIMCLA.md) | VDOCONTRATOCLAIMVLAN->CLACODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimContratoVendaImoLocacao / [TIMCLA](TIMCLA.md) | VDOCONTRATOCLAIMVLOC->CLACODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimContratoVendaImoRevenda / [TIMCLA](TIMCLA.md) | VDOCONTRATOCLAIMVREV->CLACODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimContratoVendaLancamento / [TIMCLA](TIMCLA.md) | VDOCONTRATOCLAVENLAN->CLACODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimContratoVendaRevenda / [TIMCLA](TIMCLA.md) | VDOCONTRATOCLAVENREV->CLACODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
