# TCSEFP — Exceção Faturamento Pedido

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ExcecaoFatPedido | Exceção de Faturamento de Pedido | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ACEITACOMPL | Aceita Serviço Complementar | S |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DHALTER | Dt./Hota Alteração | H |  |  | N |
| NUNOTA | Nro. Único Pedido | I |  |  | N |
| OBS | Observação | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ExcecaoFatPedido / [TCSEFP](TCSEFP.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
