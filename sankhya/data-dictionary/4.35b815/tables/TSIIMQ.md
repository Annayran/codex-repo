# TSIIMQ — Tabela para registro dos campos das quebras em relatórios

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CampoQuebraRelatorio | Tabela para registro dos campos das quebras em relatórios | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODREL | Cód.Relatório | I |  |  | N |
| CODUNI | Cód.Unidade | I |  |  | N |
| COLUNA | Coluna | I |  |  | N |
| CONDICAO | Condição | S |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| LINHA | Linha | I |  |  | N |
| NROQUERY | Número da Query | I |  |  | N |
| ORDEM | Ordem | I |  |  | N |
| QUEBRA | Quebra | I |  |  | N |
| REIMPRIMIR | Reimprimir | S |  |  | N |
| SALTAPAGINA | Salta Pagina? | S |  |  | N |
| TAMANHO | Tamanho | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CampoQuebraRelatorio / [TSIIMQ](TSIIMQ.md) | FormatadorRelatorio / [TSIIMP](TSIIMP.md) | CODREL->CODREL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
