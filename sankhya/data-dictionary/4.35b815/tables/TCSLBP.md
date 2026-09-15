# TCSLBP — Linha de base do projeto

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LinhaBaseProjeto | Linha de base do projeto | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DHBASE | DHBASE | H |  |  | N |
| NUFAP | NUFAP | I |  |  | N |
| OBSERVACAO | OBSERVACAO | S |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| LinhaBaseEtapa / [TCSLBE](TCSLBE.md) | LinhaBaseProjeto / [TCSLBP](TCSLBP.md) | NUFAP->NUFAP | I |
| LinhaBaseProjeto / [TCSLBP](TCSLBP.md) | ProjetoServico / [TCSFAP](TCSFAP.md) | NUFAP->NUFAP | I |
| LinhaBaseRequisito / [TCSLBR](TCSLBR.md) | LinhaBaseProjeto / [TCSLBP](TCSLBP.md) | NUFAP->NUFAP; SEQUENCIA->SEQUENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
