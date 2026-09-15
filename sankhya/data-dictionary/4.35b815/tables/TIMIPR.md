# TIMIPR — Imóveis com interesse

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 24
- Instâncias DWF: 1
- Relações DWF: 11
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimImovelFAC | Imóveis da FAC | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| IPRCHAVE | Chaves Entregues | S |  |  | N |
| IPRCHVENTREGUE | Chave | I |  |  | N |
| IPRCODUSUDEV | Usu. Resp. Devolução | I |  |  | N |
| IPRCODUSUENT | Usu. Resp. Entrega | I |  |  | N |
| IPRCODUSUPROP | Usu. Resp. Proposta | I |  |  | N |
| IPRDATA | Dh. Inclusão | H |  |  | N |
| IPRDHDEVCHAVES | Dh. Devolução | H |  |  | N |
| IPRDHENTCHAVES | Dh. Entrega | H |  |  | N |
| IPRDHPROP | Dh. Proposta | H |  |  | N |
| IPRESTAGIO | Estágio | S |  |  | S |
| IPRFAC | FAC | I |  |  | N |
| IPRIMOBDEV | Imobiliária de Devolução | I |  |  | N |
| IPRIMOVEL | Imóvel | I |  |  | N |
| IPRMOTIVO | Motivo Devolução | I |  |  | N |
| IPRMTVDESIST | Motivo de Desistência | I |  |  | N |
| IPROBSDEVOLUCAO | Observação | S |  |  | N |
| IPRPOSFILA | Posição na Fila | I |  |  | S |
| IPRPROPDTLIM | Dt. Limite | D |  |  | N |
| IPRPROPESTAGIO | Proposta | S |  |  | N |
| IPRPROPPARA | Proposta para | S |  |  | N |
| IPRPROPVLRMAX | Vlr. Máx | F |  |  | N |
| IPRPROPVLRMIN | Vlr. Mín. | F |  |  | N |
| IPRUSUARIO | Usuário Inclusão | I |  |  | N |
| IPRVLRALUGUEL | Valor Aluguel | F |  |  | S |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | TimImovelFAC / [TIMIPR](TIMIPR.md) | FACCODIGO->IPRFAC | I |
| TimImovelFAC / [TIMIPR](TIMIPR.md) | TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | IPRFAC->FACCODIGO | I |
| TimImovelFAC / [TIMIPR](TIMIPR.md) | TimImobiliaria / [TIMIMB](TIMIMB.md) | IPRIMOBDEV->IMBCODIGO | I |
| TimImovelFAC / [TIMIPR](TIMIPR.md) | TimImovel / [TIMIMV](TIMIMV.md) | IPRIMOVEL->IMVCODIGO | I |
| TimImovelFAC / [TIMIPR](TIMIPR.md) | TimImovelChaves / [TIMCHV](TIMCHV.md) | IPRCHVENTREGUE->CODCHV | I |
| TimImovelFAC / [TIMIPR](TIMIPR.md) | TimMotivoDesistencia / [TIMMDS](TIMMDS.md) | IPRMTVDESIST->MDSCODIGO | I |
| TimImovelFAC / [TIMIPR](TIMIPR.md) | TimMotivoDevolucao / [TIMMTD](TIMMTD.md) | IPRMOTIVO->MTDCODIGO | I |
| TimImovelFAC / [TIMIPR](TIMIPR.md) | Usuario / [TSIUSU](TSIUSU.md) | IPRUSUARIO->CODUSU | I |
| TimImovelFAC / [TIMIPR](TIMIPR.md) | Usuario2 / [TSIUSU](TSIUSU.md) | IPRCODUSUENT->CODUSU | I |
| TimImovelFAC / [TIMIPR](TIMIPR.md) | UsuarioBaixa / [TSIUSU](TSIUSU.md) | IPRCODUSUDEV->CODUSU | I |
| TimViewFichaAtendimentoCliente / [VIMFAC](VIMFAC.md) | TimImovelFAC / [TIMIPR](TIMIPR.md) | FACCODIGO->IPRFAC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
