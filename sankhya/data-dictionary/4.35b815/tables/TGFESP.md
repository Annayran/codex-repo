# TGFESP — Tabela Estutura de Produção

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EstruturaProducao | Estrutura de Produção | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEST | Código | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| NUNOTA | Modelo do Pedido MP | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EstruturaProducao / [TGFESP](TGFESP.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| EstruturaProducao / [TGFESP](TGFESP.md) | EtapaEstruturaProducao / [TGFEEP](TGFEEP.md) | CODEST->CODEST | I |
| FormulaComposicaoProduto / [TGFFCP](TGFFCP.md) | EstruturaProducao / [TGFESP](TGFESP.md) | CODEST->CODEST | I |
| GrupoProducaoProduto / [TGAGRU](TGAGRU.md) | EstruturaProducao / [TGFESP](TGFESP.md) | CODEST->CODEST | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
