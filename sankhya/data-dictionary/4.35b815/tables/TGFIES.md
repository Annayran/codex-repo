# TGFIES — Executantes do Serviço

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemExecucaoServico | Ítens Execução do Serviço | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARC | Cód. Parceiro | I |  |  | N |
| DTFIM | Data Fim | H |  |  | N |
| DTINICIO | Data Início | H |  |  | N |
| NUNOTA | Número da Nota | I |  |  | N |
| NUNOTAPEDCOMPRA | Número da NF do Pedido de Compra | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| VALOR | Valor | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItemExecucaoServico / [TGFIES](TGFIES.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTAPEDCOMPRA->NUNOTA | I |
| ItemExecucaoServico / [TGFIES](TGFIES.md) | ItemNota / [TGFITE](TGFITE.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| ItemExecucaoServico / [TGFIES](TGFIES.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
