# TSIIMP — Formatador de Relatórios

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 33
- Instâncias DWF: 1
- Relações DWF: 7
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FormatadorRelatorio | Formatador de Relatórios | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ACESSOCUBO | Acessos do cubo | S |  |  | S |
| CODEMP | Cód.Empresa | I |  |  | N |
| CODGRUPOREL | Grupo Relatório | I |  |  | N |
| CODREL | Cód.Relatório | I |  |  | N |
| CONTASBCO | Contas Bancárias | S |  |  | N |
| DTFINAL | Data final | H |  |  | N |
| DTINICIAL | Data inicial | H |  |  | N |
| ESCOLHIDOS | Escolhidos | S |  |  | N |
| FASTSERVICE | Fast Service | S |  |  | N |
| FILTROS | Filtros | S |  |  | N |
| FILTROS2 | Filtros2 | S |  |  | N |
| GRAFIC | Gráfico | B |  |  | N |
| LAYOUT | Layout | B |  |  | N |
| LAYOUTSW | Arquivo IReport | B |  |  | N |
| LISTA1 | Lista1 | S |  |  | N |
| LISTA2 | Lista2 | S |  |  | N |
| LISTA3 | Lista3 | S |  |  | N |
| LISTA4 | Lista4 | S |  |  | N |
| NIVEL | Nivel | I |  |  | N |
| NOME | Nome | S |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| ORDEM | Ordem | S |  |  | N |
| ORIENTACAO | Orientação do relatório | S |  |  | N |
| ORIGEM | Origem | I |  |  | N |
| PARAMSGEN | ParamsGen | S |  |  | N |
| PERSONALIZADO | Personalizado | S |  |  | N |
| QTDVISUALIZACOES | Qtd. visualizações | I |  |  | S |
| RESUMO | Resumo | S |  |  | N |
| SANKHYA | Modelo | S |  |  | N |
| TAMFONTE | Tamanho da fonte | I |  |  | N |
| TEMLAYOUTSW | Usa modelo IReport | S |  |  | S |
| TIPO | Tipo | S |  |  | N |
| TIPOFONTE | Tipo da Fonte | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CampoQuebraRelatorio / [TSIIMQ](TSIIMQ.md) | FormatadorRelatorio / [TSIIMP](TSIIMP.md) | CODREL->CODREL | I |
| CampoUsadoRelatorio / [TSIIMD](TSIIMD.md) | FormatadorRelatorio / [TSIIMP](TSIIMP.md) | CODREL->CODREL | I |
| CampoUtilizadoComoFiltroRel / [TSIIMF](TSIIMF.md) | FormatadorRelatorio / [TSIIMP](TSIIMP.md) | CODREL->CODREL | I |
| ControleAcessoRelatorio / [TSIACI](TSIACI.md) | FormatadorRelatorio / [TSIIMP](TSIIMP.md) | CODREL->CODREL | I |
| FormatadorRelatorio / [TSIIMP](TSIIMP.md) | GrupoRelatorio / [TSIGRE](TSIGRE.md) | CODGRUPOREL->CODGRUPOREL | I |
| TarefaAgendada / [TSITAR](TSITAR.md) | FormatadorRelatorio / [TSIIMP](TSIIMP.md) | CODREL->CODREL | I |
| VariavelUtilizadaRelatorio / [TSIIMV](TSIIMV.md) | FormatadorRelatorio / [TSIIMP](TSIIMP.md) | CODREL->CODREL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
