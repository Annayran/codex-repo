# TGFGNT — Grupo de Naturezas

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| GrupoNaturezas | Grupo de Naturezas | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANALITICO | Analítico | S |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| CODGRUPONAT | Grupo de natureza | I |  |  | N |
| CODGRUPONATPAI | Grupo de natureza pai | I |  |  | N |
| DESCRGRUPONAT | Descrição | S |  |  | N |
| GRAU | GRAU | I |  |  | N |
| TIPNAT | Tipo de natureza | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| GrupoNaturezas / [TGFGNT](TGFGNT.md) | Natureza / [TGFNAT](TGFNAT.md) | CODGRUPONAT->CODGRUPONAT | I |
| MetaAtual / [TGMMET](TGMMET.md) | GrupoNaturezas / [TGFGNT](TGFGNT.md) | CODGRUPONAT->CODGRUPONAT | I |
| Natureza / [TGFNAT](TGFNAT.md) | GrupoNaturezas / [TGFGNT](TGFGNT.md) | CODGRUPONAT->CODGRUPONAT | I |
| Transferencia / [TGMTRA](TGMTRA.md) | GrupoNaturezas / [TGFGNT](TGFGNT.md) | CODGRUPONAT->CODGRUPONAT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
