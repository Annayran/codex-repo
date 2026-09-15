# TGFMDC — GF Movimentacao de devolucao de cheques

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 0
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DevolucaoCheque | DevolucaoCheque | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALINEACHEQDEV | Alínea | S |  |  | N |
| CODTIPTIT | Tipo de título | I |  |  | N |
| NUDEV | Número da devolução | I |  |  | N |
| NUFINDESP | Número no financeiro despesa | I |  |  | N |
| NUFINORIG | Número no financeiro de origem | I |  |  | N |
| NUFINREC | Número no financeiro de receita | I |  |  | N |

## Relações DWF

Nenhuma relação DWF encontrada nesta exportação.

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
