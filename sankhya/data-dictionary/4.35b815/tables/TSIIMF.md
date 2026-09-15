# TSIIMF — Tabela para registro dos campos utilizados como filtro em relatórios

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CampoUtilizadoComoFiltroRel | Tabela para registro dos campos utilizados como filtro em relatórios | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODREL | Cód.Relatório | I |  |  | N |
| CODUNI | Cód.Unidade | I |  |  | N |
| NROQUERY | Número da Query | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CampoUtilizadoComoFiltroRel / [TSIIMF](TSIIMF.md) | FormatadorRelatorio / [TSIIMP](TSIIMP.md) | CODREL->CODREL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
