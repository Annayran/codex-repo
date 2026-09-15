# TIMFAC — Ficha de Atendimento ao Cliente

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 27
- Instâncias DWF: 1
- Relações DWF: 17
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimFichaAtendimentoCliente | Ficha de Atendimento ao Cliente | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| FACBAIRROIMOVEL | Bairro do imóvel | I |  |  | N |
| FACBUSCAPOR | Interesse em | S |  |  | N |
| FACCAPTACAO | Captação | S |  |  | N |
| FACCODIGO | Código | I |  |  | N |
| FACCODPARC | Parceiro | I |  |  | N |
| FACCORRETOR | Corretor | I |  |  | N |
| FACDHALTER | Dh. Alteração | H |  |  | N |
| FACDHDEVCHA | Dh. Dev. Chaves | H |  |  | S |
| FACDHENTCHAVE | Dh. Entrega Chaves | H |  |  | S |
| FACDOCCHAVE | Doc. Entrega Chaves | S |  |  | N |
| FACEDIFICIO | Edifício | I |  |  | N |
| FACEMPREENDIMENTO | Empreendimento | I |  |  | N |
| FACESTAGIO | Estágio | S |  |  | N |
| FACLANCAMENTO | Dh. Inclusão | H |  |  | N |
| FACMEDIAALUGFAC | Média Vlr. Aluguéis Imóveis Fac | F |  |  | S |
| FACNOMEPPROSPECT | Nome Prospect | S |  |  | S |
| FACOBSERVACAO | Observação | S |  |  | N |
| FACORIGEM | Origem | S |  |  | N |
| FACPROSPECT | Prospect | I |  |  | N |
| FACQUARTOS | Quartos | I |  |  | N |
| FACRETORNAR | Dt. Retorno | H |  |  | N |
| FACSITUACAOCHAVE | Situação Chaves | S |  |  | S |
| FACTIPOIMOVEL | Tipo de imóvel | I |  |  | N |
| FACUSUALTER | Usuário Alteração | I |  |  | N |
| FACUSUARIO | Usuário Inclusão | I |  |  | N |
| FACVALORPEDIDO | Valor pedido | F |  |  | N |
| FACVEICULO | Veículo de Comunicação | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | SACFAC->FACCODIGO | I |
| TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | Bairro / [TSIBAI](TSIBAI.md) | FACBAIRROIMOVEL->CODBAI | I |
| TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | Parceiro / [TGFPAR](TGFPAR.md) | FACCODPARC->CODPARC | I |
| TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | TimCorretor / [TIMCOR](TIMCOR.md) | FACCORRETOR->CORCODIGO | I |
| TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | TimEdificioGaleria / [TIMEDG](TIMEDG.md) | FACEDIFICIO->EDGCODIGO | I |
| TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | TimEmpreendimento / [TIMEPR](TIMEPR.md) | FACEMPREENDIMENTO->EPRCODIGO | I |
| TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | TimImovelFAC / [TIMIPR](TIMIPR.md) | FACCODIGO->IPRFAC | I |
| TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | TimImovelTipo / [TIMTPI](TIMTPI.md) | FACTIPOIMOVEL->TPICODIGO | I |
| TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | TimProspect / [TIMPPR](TIMPPR.md) | FACPROSPECT->PPRCODIGO | I |
| TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | TimTelefoneProspect / [TIMPTP](TIMPTP.md) | FACPROSPECT->PTPPROSPECT | I |
| TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | TimVeiculoComunicacao / [TIMVCO](TIMVCO.md) | FACVEICULO->VCOCODIGO | I |
| TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | Usuario / [TSIUSU](TSIUSU.md) | FACUSUARIO->CODUSU | I |
| TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | Usuario2 / [TSIUSU](TSIUSU.md) | FACUSUALTER->CODUSU | I |
| TimImovelChaves / [TIMCHV](TIMCHV.md) | TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | FACCODIGO->FACCODIGO | I |
| TimImovelFAC / [TIMIPR](TIMIPR.md) | TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | IPRFAC->FACCODIGO | I |
| TimImovelFilaChaves / [TIMFILCHV](TIMFILCHV.md) | TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | FILFAC->FACCODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | VDOFICHAATEND->FACCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
