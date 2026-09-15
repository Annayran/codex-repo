# TSIGRE — Grupo de Relatórios

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| GrupoRelatorio | Grupo de Relatórios | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANALITICO | Analítico | S |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| CODGRUPOREL | Cód.Grupo Relatório | I |  |  | N |
| CODGRUPORELPAI | Cód.Grupo Relatório Pai | I |  |  | N |
| DESCRGRUPOREL | Descrição | S |  |  | N |
| GRAU | Grau | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FormatadorRelatorio / [TSIIMP](TSIIMP.md) | GrupoRelatorio / [TSIGRE](TSIGRE.md) | CODGRUPOREL->CODGRUPOREL | I |
| GrupoRelatorio / [TSIGRE](TSIGRE.md) | GrupoRelatorio / [TSIGRE](TSIGRE.md) | CODGRUPORELPAI->CODGRUPOREL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
