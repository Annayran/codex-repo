# VIMFAC — Ficha de Atendimento ao Cliente

- Módulo técnico: [VIM](../modules/VIM.md)
- Campos: 25
- Instâncias DWF: 1
- Relações DWF: 10
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimViewFichaAtendimentoCliente | Ficha de Atendimento ao Cliente | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| FACBAIRROIMOVEL | Bairro do imóvel | I |  |  | N |
| FACBUSCAPOR | Interesse em | S |  |  | N |
| FACCAPTACAO | Captação | S |  |  | N |
| FACCODIGO | Código | I |  |  | N |
| FACCORRETOR | Corretor | I |  |  | N |
| FACDHALTER | Dh. Alteração | H |  |  | N |
| FACDHDEVCHAVE | Dh. Dev. Chaves | H |  |  | N |
| FACDHENTCHAVE | Dh. Entrega Chaves | H |  |  | N |
| FACDOCCHAVE | Doc. Entrega Chaves | S |  |  | N |
| FACEDIFICIO | Edifício | I |  |  | N |
| FACEMPREENDIMENTO | Empreendimento | I |  |  | N |
| FACESTAGIO | Estágio | S |  |  | N |
| FACLANCAMENTO | Dh. Inclusão | H |  |  | N |
| FACNOMEPPROSPECT | Nome Prospect | S |  |  | N |
| FACOBSERVACAO | Observação | S |  |  | N |
| FACORIGEM | Origem | S |  |  | N |
| FACPROSPECT | Prospect | I |  |  | N |
| FACQUARTOS | Quartos | I |  |  | N |
| FACRETORNAR | Dt. Retorno | H |  |  | N |
| FACSITUACAOCHAVE | Situação Chaves | S |  |  | N |
| FACTIPOIMOVEL | Tipo de imóvel | I |  |  | N |
| FACUSUALTER | Usuário Alteração | I |  |  | N |
| FACUSUARIO | Usuário Inclusão | I |  |  | N |
| FACVALORPEDIDO | Valor pedido | I |  |  | N |
| FACVEICULO | Veículo | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimViewFichaAtendimentoCliente / [VIMFAC](VIMFAC.md) | TimBairroImovel / [TSIBAI](TSIBAI.md) | FACBAIRROIMOVEL->CODBAI | I |
| TimViewFichaAtendimentoCliente / [VIMFAC](VIMFAC.md) | TimCorretor / [TIMCOR](TIMCOR.md) | FACCORRETOR->CORCODIGO | I |
| TimViewFichaAtendimentoCliente / [VIMFAC](VIMFAC.md) | TimEdificioGaleria / [TIMEDG](TIMEDG.md) | FACEDIFICIO->EDGCODIGO | I |
| TimViewFichaAtendimentoCliente / [VIMFAC](VIMFAC.md) | TimEmpreendimento / [TIMEPR](TIMEPR.md) | FACEMPREENDIMENTO->EPRCODIGO | I |
| TimViewFichaAtendimentoCliente / [VIMFAC](VIMFAC.md) | TimImovelFAC / [TIMIPR](TIMIPR.md) | FACCODIGO->IPRFAC | I |
| TimViewFichaAtendimentoCliente / [VIMFAC](VIMFAC.md) | TimImovelTipo / [TIMTPI](TIMTPI.md) | FACTIPOIMOVEL->TPICODIGO | I |
| TimViewFichaAtendimentoCliente / [VIMFAC](VIMFAC.md) | TimProspect / [TIMPPR](TIMPPR.md) | FACPROSPECT->PPRCODIGO | I |
| TimViewFichaAtendimentoCliente / [VIMFAC](VIMFAC.md) | TimTelefoneProspect / [TIMPTP](TIMPTP.md) | FACPROSPECT->PTPPROSPECT | I |
| TimViewFichaAtendimentoCliente / [VIMFAC](VIMFAC.md) | TimVeiculoComunicacao / [TIMVCO](TIMVCO.md) | FACVEICULO->VCOCODIGO | I |
| TimViewFichaAtendimentoCliente / [VIMFAC](VIMFAC.md) | Usuario / [TSIUSU](TSIUSU.md) | FACUSUARIO->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
