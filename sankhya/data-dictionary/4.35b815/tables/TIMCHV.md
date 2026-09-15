# TIMCHV — TABLE TIMCHV

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 15
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimImovelChaves | Chaves do Imóvel | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CHVCODUSUALT | Usuário Alteração | I |  |  | N |
| CHVCODUSUINC | Usuário Inclusão | I |  |  | N |
| CHVDHALT | Dh. Alteração | H |  |  | N |
| CHVDHINC | Dh. Inclusão | H |  |  | N |
| CHVOBS | Observações | S |  |  | N |
| CODCHV | Código da Chave | I |  |  | N |
| CORCODIGO | Corretor | I |  |  | N |
| DESCRCHV | Descrição da Chave | S |  |  | N |
| DHULTSOL | Última Solicitação | H |  |  | N |
| FACCODIGO | FAC de solicitação | I |  |  | N |
| FILTROCHAVE | Código da Chave | I |  |  | S |
| IMBCODIGO | Imobiliária | I |  |  | N |
| IMVCODIGO | Imóvel | I |  |  | N |
| STATCOD | Local da chave | I |  |  | N |
| STATCODOLD | STATCODOLD | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimImovelChaves / [TIMCHV](TIMCHV.md) | TimCorretor / [TIMCOR](TIMCOR.md) | CORCODIGO->CORCODIGO | I |
| TimImovelChaves / [TIMCHV](TIMCHV.md) | TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | FACCODIGO->FACCODIGO | I |
| TimImovelChaves / [TIMCHV](TIMCHV.md) | TimImobiliaria / [TIMIMB](TIMIMB.md) | IMBCODIGO->IMBCODIGO | I |
| TimImovelChaves / [TIMCHV](TIMCHV.md) | TimImovel / [TIMIMV](TIMIMV.md) | IMVCODIGO->IMVCODIGO | I |
| TimImovelChaves / [TIMCHV](TIMCHV.md) | TimStatusChv / [TIMSTATCHV](TIMSTATCHV.md) | STATCOD->STATCOD | I |
| TimImovelFAC / [TIMIPR](TIMIPR.md) | TimImovelChaves / [TIMCHV](TIMCHV.md) | IPRCHVENTREGUE->CODCHV | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
