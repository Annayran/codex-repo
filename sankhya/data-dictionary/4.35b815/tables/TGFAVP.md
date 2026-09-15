# TGFAVP — Ajusta Valor Presente

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AjustaValorPresente | Ajusta Valor Presente | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODMOEDA | Cód. Moeda | I |  |  | N |
| INDICE | Índice | F |  |  | N |
| NUFIN | Nro Único Financeiro | I |  |  | N |
| RECDESP | Receita/Despesa | I |  |  | N |
| REFCTBZ | Ref. Contabilização | H |  |  | N |
| REFJURO | Ref. Juro | H |  |  | N |
| VLRJURO | Valor Juro | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AjustaValorPresente / [TGFAVP](TGFAVP.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
