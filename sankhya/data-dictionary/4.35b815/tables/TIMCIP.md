# TIMCIP — Parcelas

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimContaControladaParcela | Parcelas da Conta | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CIPCODCIL | Cod. Conta | I |  |  | N |
| CIPCODIGO | Código | I |  |  | N |
| CIPDETALHAMENTO | Cód. Detalhamento | I |  |  | N |
| CIPIMOVEL | Cod. Imóvel | I |  |  | N |
| CIPNUFIN | Nu. Financeiro | I |  |  | N |
| CIPPAGAMENTO | Dt. Pagamento | D |  |  | N |
| CIPPARCELA | Parcela | I |  |  | N |
| CIPREFFINAL | Término Referência | D |  |  | N |
| CIPREFINICIO | Inicio Referência | D |  |  | N |
| CIPREGISTRO | Registro | S |  |  | N |
| CIPVENCIMENTO | Dt. Vcto. | D |  |  | N |
| CIPVLORIGEM | Valor | F |  |  | N |
| CIPVLPAGO | Vlr. Pago | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimContaControlada / [TIMCIL](TIMCIL.md) | TimContaControladaParcela / [TIMCIP](TIMCIP.md) | CILCODIGO->CIPCODCIL; CILIMOVEL->CIPIMOVEL | I |
| TimContaControladaParcela / [TIMCIP](TIMCIP.md) | TimContaControlada / [TIMCIL](TIMCIL.md) | CIPCODCIL->CILCODIGO; CIPIMOVEL->CILIMOVEL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
