# TSIIMV — Tabela para registro das variáveis utilizadas em relatórios

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| VariavelUtilizadaRelatorio | Tabela para registro das variáveis utilizadas em relatórios | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODREL | Cód.Relatório | I |  |  | N |
| NOMEVAR | Nome da variável | S |  |  | N |
| TIPO | Tipo | S |  |  | N |
| VALORPADRAO | Valor Padrão | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| VariavelUtilizadaRelatorio / [TSIIMV](TSIIMV.md) | FormatadorRelatorio / [TSIIMP](TSIIMP.md) | CODREL->CODREL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
