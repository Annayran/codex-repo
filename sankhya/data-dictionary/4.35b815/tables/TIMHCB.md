# TIMHCB — Tipo de Detalhamento

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 7
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimTipoDetalhamento | Tipo de Detalhamento | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| HCBADM | Incide na tx. adm. | S |  |  | N |
| HCBCODIGO | Código | I |  |  | N |
| HCBGARANTIDO | Garantido | S |  |  | N |
| HCBGERADESP | Gera Despesa | S |  |  | N |
| HCBHISTORICO | Descrição | S |  |  | N |
| HCBINCIDEFPP | Incidde no FPP? | S |  |  | N |
| HCBINCIDEVLRPGO | Incide Valor Principal | S |  |  | N |
| HCBINCTXMINIMA | Incide tx mínima adm | S |  |  | N |
| HCBIRRF | Incide no IRRF | S |  |  | N |
| HCBJUROS | Incide em juros e multa | S |  |  | N |
| HCBNATUREZA | Natureza | I |  |  | N |
| HCBRECEBEDE | Recebe de | S |  |  | N |
| HCBREPASSAPARA | Repassa para | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimContaControlada / [TIMCIL](TIMCIL.md) | TimTipoDetalhamento / [TIMHCB](TIMHCB.md) | CILORIGEM->HCBCODIGO | I |
| TimDespesaSinteticaLoc / [VIMDER](VIMDER.md) | TimTipoDetalhamento / [TIMHCB](TIMHCB.md) | DTLHISTORICO->HCBCODIGO | I |
| TimDetalhamento / [TIMDTL](TIMDTL.md) | TimTipoDetalhamento / [TIMHCB](TIMHCB.md) | DTLHISTORICO->HCBCODIGO | I |
| TimDetalhamentoLoteamento / [TIMLDT](TIMLDT.md) | TimTipoDetalhamento / [TIMHCB](TIMHCB.md) | LDTHISTORICO->HCBCODIGO | I |
| TimImovelConta / [TIMCIM](TIMCIM.md) | TimTipoDetalhamento / [TIMHCB](TIMHCB.md) | CIMHISTORICO->HCBCODIGO | I |
| TimRescisaoLocDetalhamentos / [TIMRCM](TIMRCM.md) | TimTipoDetalhamento / [TIMHCB](TIMHCB.md) | RCMHISTORICO->HCBCODIGO | I |
| TimTipoDetalhamento / [TIMHCB](TIMHCB.md) | Natureza / [TGFNAT](TGFNAT.md) | HCBNATUREZA->CODNAT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
