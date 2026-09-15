# TGFVALP — Vale Pedágio

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ValePedagio | Vale Pedágio | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARCFORN | Parceiro Fornecedor | I |  |  | N |
| CODPARCPAG | Parceiro Pagante | I |  |  | N |
| NUCOMPRA | Nro. Compra | S |  |  | N |
| NUNOTA | Nro. Único da Nota | I |  |  | N |
| SEQUENCIA | Sequência do Vale Pedágio | I |  |  | N |
| TIPPAG | Pagante do Pedágio | S |  |  | N |
| VLRPEDAGIO | Valor do Pedágio | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ValePedagio / [TGFVALP](TGFVALP.md) | NUNOTA->NUNOTA | I |
| ValePedagio / [TGFVALP](TGFVALP.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| ValePedagio / [TGFVALP](TGFVALP.md) | ParceiroFornecedor / [TGFPAR](TGFPAR.md) | CODPARCFORN->CODPARC | I |
| ValePedagio / [TGFVALP](TGFVALP.md) | ParceiroPagante / [TGFPAR](TGFPAR.md) | CODPARCPAG->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
