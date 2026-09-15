# TGFFRP — Repasse

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Repasse | Repasse | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NUCND | Nro. Condomínio | I |  |  | N |
| NUFINDESP | Nro. Despesa | I |  |  | N |
| NUFINORIG | Nro. Origem | I |  |  | N |
| NUFINREC | Nro. Receita | I |  |  | N |
| PERCRATEIO | Percentual Rateio | F |  |  | N |
| VALORRATEIO | Vlr. Rateio | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Repasse / [TGFFRP](TGFFRP.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFINORIG->NUFIN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
