# TGFVTP — Via de transporte

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ViaTransporte | Via de Transporte | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODMODPED | Modelo de Pedido | I |  |  | N |
| CODVTP | Cód. Meio de Transporte | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| LEADTIME | Lead Time Total | I |  |  | N |
| LEADTIMECONF | Lead Time Confirmado | I |  |  | N |
| LEADTIMEDESP | Lead Time após Despacho | I |  |  | N |
| LEADTIMEEMPC | Lead Time Empacotado | I |  |  | N |
| LEADTIMEPROC | Lead Time em Processamento | I |  |  | N |
| QTDITENS | Quantidade de Itens | I |  |  | N |
| TIPO | Via de Transporte | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ViaTransporte / [TGFVTP](TGFVTP.md) | CODVTP->CODVTP | I |
| PedidosComplementares / [TGFPCL](TGFPCL.md) | ViaTransporte / [TGFVTP](TGFVTP.md) | CODVTP->CODVTP | I |
| Produto / [TGFPRO](TGFPRO.md) | ViaTransporte / [TGFVTP](TGFVTP.md) | CODVTP->CODVTP | I |
| Servico / [TGFPRO](TGFPRO.md) | ViaTransporte / [TGFVTP](TGFVTP.md) | CODVTP->CODVTP | I |
| ViaTransporte / [TGFVTP](TGFVTP.md) | CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | CODMODPED->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
