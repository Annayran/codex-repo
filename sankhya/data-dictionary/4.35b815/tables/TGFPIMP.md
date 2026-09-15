# TGFPIMP — Pedido Importado

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PedidoImportado | Pedido Importado | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| MSG | Mensagem de erro | C |  |  | N |
| NROPED | Núm. Ped. Concessionário | I |  |  | N |
| NUIMP | Núm. Importação | I |  |  | N |
| NUNOTA | Número único | I |  |  | N |
| STATUS | Status | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ArquivoImportacaoPedido / [TGFAIMPPED](TGFAIMPPED.md) | PedidoImportado / [TGFPIMP](TGFPIMP.md) | NUIMP->NUIMP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
