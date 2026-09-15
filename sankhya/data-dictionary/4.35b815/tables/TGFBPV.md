# TGFBPV — Base para vencimento

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| BasesVencimento | Bases para vencimento | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| BASEMESVENCIMENTO | Mês de vencimento | I |  |  | N |
| CODTIPVENDA | Tipo de venda | I |  |  | N |
| DIABASEVENCIMENTO | Dia do vencimento | I |  |  | N |
| DIAFINALVENDA | Dia final de venda | I |  |  | N |
| DIAINICIALVENDA | Dia inicial de venda | I |  |  | N |
| SEQUENCIA | Sequencia | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TipoNegociacao / [TGFTPV](TGFTPV.md) | BasesVencimento / [TGFBPV](TGFBPV.md) | CODTIPVENDA->CODTIPVENDA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
