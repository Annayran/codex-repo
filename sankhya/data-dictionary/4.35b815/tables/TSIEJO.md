# TSIEJO — Estatisticas Job

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 16
- Instâncias DWF: 1
- Relações DWF: 0
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EstatisticasJob | Estatisticas Job | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DHEXEC | Dt. Última Execução | H |  |  | N |
| DHPROXEXEC | Dt. Próxima Execução | H |  |  | N |
| ERROTRACE | Detalhe do erro | S |  |  | N |
| ID | Cod. Job | S |  |  | N |
| MEDIAEXEC | Tempo Médio | I |  |  | N |
| MSGERRO | Mensagem de Erro | S |  |  | N |
| QTDEXECS | Qtd. Execuções | I |  |  | N |
| QTDFALHAS | Qtd. Falhas | I |  |  | N |
| STATUSEXEC | Status | S |  |  | N |
| TEMPOEXEC | Tempo Exec | I |  |  | N |
| TEMPOMAXEXEC | Maior Tempo de Execução | I |  |  | N |
| TEMPOMINEXEC | Menor Tempo de Execução | I |  |  | N |
| TEMPOULIMAEXEC | Tempo Última Execução | I |  |  | N |
| TIPJOB | Tipo de Job | S |  |  | N |

## Relações DWF

Nenhuma relação DWF encontrada nesta exportação.

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
