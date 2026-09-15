# TGFAIMPPED — Arquivo Importação do Pedido

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ArquivoImportacaoPedido | Arquivo de Importação do Pedido | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARC | Parceiro | I |  |  | N |
| DHIMP | Data da Importação | H |  |  | N |
| NOMEARQ | Nome do Arquivo | S |  |  | N |
| NUIMP | Núm. Importação | I |  |  | N |
| QTDIMP | Qtd. Pedidos Importados | I |  |  | N |
| QTDNIMP | Qtd. Pedidos não Importados | I |  |  | N |
| QTDPEDIDOS | Qtd. Pedidos no Arquivo | I |  |  | N |
| QTDVEZES | Vezes Processado | I |  |  | N |
| REPROCESSAR | Reprocessar | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ArquivoImportacaoPedido / [TGFAIMPPED](TGFAIMPPED.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ArquivoImportacaoPedido / [TGFAIMPPED](TGFAIMPPED.md) | PedidoImportado / [TGFPIMP](TGFPIMP.md) | NUIMP->NUIMP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
