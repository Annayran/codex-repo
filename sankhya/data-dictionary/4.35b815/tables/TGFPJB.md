# TGFPJB — Dados Boleto Rápido

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 20
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| BoletoRapido | Boleto Rápido | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCTABCOINT | Código da conta bancária | I |  |  | N |
| CODPARC | Codigo Parceiro | I |  |  | N |
| DTCRED | Data do Credito | D |  |  | N |
| DTPAG | Data Pagamento | D |  |  | N |
| DTVENC | Data Vencimento Boleto | D |  |  | N |
| IDUNICO | Identificador boleto rápido | I |  |  | N |
| IDUNIORIG | Id Unico Original | I |  |  | N |
| LINKBOLETO | Link para Impressao Boleto | S |  |  | N |
| LINKGRUPO | Link do boleto agrupado | S |  |  | N |
| LINKINFO | Link de informação detalhado | S |  |  | N |
| NMPEDIDO | Numero pedido único | I |  |  | N |
| NMTOKEN | Numero do token facilitador | S |  |  | N |
| NOSSONUMOR | Nosso Numero original | I |  |  | N |
| NUMBANCO | Numero do banco | S |  |  | N |
| SITREGBOL | Situação registro boleto | S |  |  | N |
| STATUS | Status Boleto Rápido | S |  |  | N |
| VRBOLETO | Valor Boleto | F |  |  | N |
| VRLIQUIDO | Valor liquido do boleto | F |  |  | N |
| VRPAGO | Valor Pago Boleto | F |  |  | N |
| VRTARIFA | Valor tarifa boleto rápido | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| BoletoRapido / [TGFPJB](TGFPJB.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| BoletoRapido / [TGFPJB](TGFPJB.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| BoletoRapidoHist / [TGFPJBH](TGFPJBH.md) | BoletoRapido / [TGFPJB](TGFPJB.md) | IDUNICO->IDUNICO | I |
| Financeiro / [TGFFIN](TGFFIN.md) | BoletoRapido / [TGFPJB](TGFPJB.md) | IDUNICO->IDUNICO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
