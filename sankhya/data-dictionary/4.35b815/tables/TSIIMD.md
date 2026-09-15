# TSIIMD — Tabela para registro dos campos usados em relatórios

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
| CampoUsadoRelatorio | Tabela para registro dos campos usados em relatórios | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODREL | Cód.Relatório | I |  |  | N |
| CODUNI | Cód.Unidade do Campo | I |  |  | N |
| COLUNA | Coluna | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| LINHA | Linha | I |  |  | N |
| NROQUERY | Número da Query | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| SUBLINHADO_ALTO | Sublinhado à Cima | S |  |  | N |
| SUBLINHADO_BAIXO | Sublinhado à Baixo | S |  |  | N |
| SUBLINHADO_DIREITA | Sublinhado à Direita | S |  |  | N |
| SUBLINHADO_ESQUERDA | Sublinhado à Esquerda | S |  |  | N |
| TAMANHO | Tamanho | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CampoUsadoRelatorio / [TSIIMD](TSIIMD.md) | FormatadorRelatorio / [TSIIMP](TSIIMP.md) | CODREL->CODREL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
