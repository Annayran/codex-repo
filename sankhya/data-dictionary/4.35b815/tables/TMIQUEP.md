# TMIQUEP — TABLE TMIQUEP

- Módulo técnico: [TMI](../modules/TMI.md)
- Campos: 6
- Instâncias DWF: 2
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ParametrosDaQuebraDaMeta | Parâmetros da quebra da meta | S | S |
| ParametrosItemQuebraMeta | Parâmetros do item da quebra | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODQUEBRA | Código da quebra | I |  |  | N |
| ID | ID do parâmetro | I |  |  | N |
| NOMEPAR | Nome do Parâmetro | S |  |  | N |
| NUMETBASE | Número de Itens da Meta | I |  |  | N |
| TIPO | Tipo de parâmetro | S |  |  | N |
| VALOR | Valor Parâmetro | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MetaGestaoIndicadores / [TMIMET](TMIMET.md) | ParametrosDaQuebraDaMeta / [TMIQUEP](TMIQUEP.md) | NUMET->NUMETBASE | I |
| QuebraItensMeta / [TMIQUEI](TMIQUEI.md) | ParametrosItemQuebraMeta / [TMIQUEP](TMIQUEP.md) | CODQUEBRA->CODQUEBRA; NUMETBASE->NUMETBASE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
