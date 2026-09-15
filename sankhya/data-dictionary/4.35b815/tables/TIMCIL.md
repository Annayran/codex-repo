# TIMCIL — Contas Compensáveis

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimContaControlada | Contas Compensáveis | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CILANO | Ano Referência | I |  |  | N |
| CILCODIGO | Código | I |  |  | N |
| CILCONTA | Tipo Conta | S |  |  | N |
| CILDATAINICIO | Dt. Inicio Referencia | D |  |  | N |
| CILFORMAPAGTO | Forma Pagamento | S |  |  | N |
| CILIMOVEL | Nro. Imóvel | I |  |  | N |
| CILORIGEM | Tipo Detalhamento | I |  |  | N |
| CILPARCELAS | Qtd. Parcelas | I |  |  | N |
| CILPARCRESPONSAVEL | Parceiro Responsável | I |  |  | N |
| CILRECEBEDE | Recebe de | S |  |  | N |
| CILREGISTRO | Registro | S |  |  | N |
| CILREPASSAPARA | Repassa para | S |  |  | N |
| CILVLORIGEM | Vlr. Total | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimContaControlada / [TIMCIL](TIMCIL.md) | Parceiro / [TGFPAR](TGFPAR.md) | CILPARCRESPONSAVEL->CODPARC | I |
| TimContaControlada / [TIMCIL](TIMCIL.md) | TimContaControladaParcela / [TIMCIP](TIMCIP.md) | CILCODIGO->CIPCODCIL; CILIMOVEL->CIPIMOVEL | I |
| TimContaControlada / [TIMCIL](TIMCIL.md) | TimImovel / [TIMIMV](TIMIMV.md) | CILIMOVEL->IMVCODIGO | I |
| TimContaControlada / [TIMCIL](TIMCIL.md) | TimTipoDetalhamento / [TIMHCB](TIMHCB.md) | CILORIGEM->HCBCODIGO | I |
| TimContaControladaParcela / [TIMCIP](TIMCIP.md) | TimContaControlada / [TIMCIL](TIMCIL.md) | CIPCODCIL->CILCODIGO; CIPIMOVEL->CILIMOVEL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
