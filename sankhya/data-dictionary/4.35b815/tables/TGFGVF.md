# TGFGVF — Faixas Pre Acordo Verba

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
| FaixasPreAcordoVerba | Faixas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NUCRITERIO | Nro Criterio | I |  |  | N |
| NUFAIXA | Nro Faixa | I |  |  | N |
| TIPO | Tipo de Recompensa | S |  |  | N |
| VLRMIN | Quantidade/Valor Mínimo | F |  |  | N |
| VLRRECOM | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CriterioPreAcordoVerba / [TGFGVC](TGFGVC.md) | FaixasPreAcordoVerba / [TGFGVF](TGFGVF.md) | NUCRITERIO->NUCRITERIO | I |
| FaixasPreAcordoVerba / [TGFGVF](TGFGVF.md) | CriterioPreAcordoVerba / [TGFGVC](TGFGVC.md) | NUCRITERIO->NUCRITERIO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
