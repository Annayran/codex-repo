# TGFTPD — Tipo de pedido

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TipoPedido | Tipo de Pedido | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODMODPED | Modelo de pedido | I |  |  | N |
| CODTPD | Cód. Tipo de Pedido | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| PRIORIDADE | Prioridade | I |  |  | N |
| SIGLA | Sigla | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | TipoPedido / [TGFTPD](TGFTPD.md) | CODTPD->CODTPD | I |
| TipoPedido / [TGFTPD](TGFTPD.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | CODMODPED->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
