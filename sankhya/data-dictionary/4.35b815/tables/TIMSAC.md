# TIMSAC — Atendimento ao Cliente SAC

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 42
- Instâncias DWF: 1
- Relações DWF: 17
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimAtendimentoCliente | Atendimento ao Cliente SAC | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| SACATENDSTATUS | Status do Atendimento | S |  |  | S |
| SACCODIGO | Código | I |  |  | N |
| SACCODPARCPREST | Prestador Serviço | I |  |  | N |
| SACCOMOCONCLUIU | Estágio | S |  |  | N |
| SACCOMRECLAMACAO | Motivo Ressalvas | S |  |  | N |
| SACCOMSUCESSO | Com sucesso | S |  |  | N |
| SACCONTESTACAO | Contestação | S |  |  | N |
| SACCORRETOR | Corretor | I |  |  | N |
| SACDATA | Dt. Inclusão | H |  |  | N |
| SACDATARES | Dt. Resultado | H |  |  | N |
| SACDHCANCELOS | Dh. Cancelamento | H |  |  | N |
| SACDTCONCLUSAO | Dt. Conclusão | H |  |  | N |
| SACDTFINOS | Dh. Fim Execução | H |  |  | N |
| SACDTINIOS | Dh. Início Execução | H |  |  | N |
| SACEHOS | É solicitação de serviços | S |  |  | N |
| SACEMPREENDIMENTO | Empreendimento | I |  |  | N |
| SACESTAGIOFAC | Estágio da FAC | S |  |  | N |
| SACESTAGIOOS | Estágio Solicitação | S |  |  | N |
| SACFAC | FAC | I |  |  | N |
| SACIMOVEL | Imóvel | I |  |  | N |
| SACINTERNET | Internet | S |  |  | N |
| SACLOCACAO | Contrato Locação | I |  |  | N |
| SACLOTE | Lote | I |  |  | N |
| SACLOTEAMENTO | Loteamento | I |  |  | N |
| SACMTVCANCELADO | Motivo de Cancelamento | I |  |  | N |
| SACOCORRENCIA | Ocorrência | S |  |  | N |
| SACORIGCHAMADA | Origem Chamada | S |  |  | N |
| SACORIGEM | Produto | S |  |  | N |
| SACPARCEIRO | Parceiro | I |  |  | N |
| SACPROMETIDO | Dt. Prevista | D |  |  | N |
| SACQUEMPAGA | Responsável Pagamento | S |  |  | N |
| SACRESULTADO | Resultado | S |  |  | N |
| SACSEMSUCESSO | Sem sucesso por | S |  |  | N |
| SACSOLICITANTEOS | Solicitante | S |  |  | N |
| SACTIPO | Finalidade | S |  |  | N |
| SACTIPOCOMUNICACAO | Tipo Comunicação | S |  |  | N |
| SACTIPOHIST | Tipo SAC | I |  |  | N |
| SACTIPOOS | Tipo Serviço | S |  |  | N |
| SACUSUARIO | Usuário Alteração | I |  |  | N |
| SACVALORSERVICO | Vlr. Serviço | F |  |  | N |
| SACVENDAIMV | Contrato de Venda | I |  |  | N |
| SACVENDALOTE | Venda de Lote | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimAtendClienteAcompanhamento / [TIMSCA](TIMSCA.md) | TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | SCASAC->SACCODIGO | I |
| TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | Parceiro / [TGFPAR](TGFPAR.md) | SACPARCEIRO->CODPARC | I |
| TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | ParceiroF2 / [TGFPAR](TGFPAR.md) | SACCODPARCPREST->CODPARC | I |
| TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | TimAtendClienteAcompanhamento / [TIMSCA](TIMSCA.md) | SACCODIGO->SCASAC | I |
| TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | TimContratoVenda / [TIMCLA](TIMCLA.md) | SACVENDAIMV->CLACODIGO | I |
| TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | TimContratoVendaLote / [TIMLTV](TIMLTV.md) | SACVENDALOTE->LTVCODIGO | I |
| TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | TimCorretor / [TIMCOR](TIMCOR.md) | SACCORRETOR->CORCODIGO | I |
| TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | TimEmpreendimento / [TIMEPR](TIMEPR.md) | SACEMPREENDIMENTO->EPRCODIGO | I |
| TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | SACFAC->FACCODIGO | I |
| TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | TimImovel / [TIMIMV](TIMIMV.md) | SACIMOVEL->IMVCODIGO | I |
| TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | TimLocacao / [TIMLOC](TIMLOC.md) | SACLOCACAO->LOCCODIGO | I |
| TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | TimLote / [TIMLTE](TIMLTE.md) | SACLOTE->LTECODIGO | I |
| TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | TimLoteamento / [TIMLOT](TIMLOT.md) | SACLOTEAMENTO->LOTCODIGO | I |
| TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | TimMotivoCancelamento / [TIMMCA](TIMMCA.md) | SACMTVCANCELADO->MCACODIGO | I |
| TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | TimViewTipoSac / [VIMTHI](VIMTHI.md) | SACORIGEM->THIORIGEM; SACTIPOHIST->THICODIGO | I |
| TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | Usuario / [TSIUSU](TSIUSU.md) | SACUSUARIO->CODUSU | I |
| TimRenovacaoContrato / [TIMCTR](TIMCTR.md) | TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | CTRSACNEGOCIACAO->SACCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
