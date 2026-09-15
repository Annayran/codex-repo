# TSILIB — Liberacao de Limites

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 31
- Instâncias DWF: 1
- Relações DWF: 9
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LiberacaoLimite | Liberação de limites | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANTECIPACAO | Antecipação | S |  |  | N |
| CODCENCUS | Centro Resultado | I |  |  | N |
| CODMETA | Meta | I |  |  | N |
| CODTIPOPER | Tipo Operação | I |  |  | N |
| CODUSULIB | Usuário Liberador | I |  |  | N |
| CODUSUSOLICIT | Usuário Solicitante | I |  |  | N |
| DHLIB | Data e hora da liberação | H |  |  | N |
| DHSOLICIT | Data e hora da solicitação | H |  |  | N |
| EVENTO | Evento | I |  |  | N |
| NUCHAVE | Número chave | I |  |  | N |
| NUCLL | Número Laudo Classificação Produto | I |  |  | N |
| NULBO | Número do limite de bonificação | I |  |  | N |
| NURNG | Regra de negócio | I |  |  | N |
| OBSCOMPL | Observação Complementar | S |  |  | N |
| OBSERVACAO | Observação solicitante | S |  |  | N |
| OBSLIB | Observação liberador | S |  |  | N |
| ORDEM | Ordem | I |  |  | N |
| PERCANTERIOR | Percentual Anterior | F |  |  | N |
| PERCLIMITE | Percentual limite | F |  |  | N |
| REPROVADO | Reprovado | S |  |  | N |
| SEQCASCATA | Seq. Cascata | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| SUPLEMENTO | Suplemento | S |  |  | N |
| TABELA | Tabela | S |  |  | N |
| TRANSF | Transferência | S |  |  | N |
| VLRANTERIOR | Valor Anterior | F |  |  | N |
| VLRATUAL | Valor atual | F |  |  | N |
| VLRDESDOB | Valor Desdobramento | F |  |  | N |
| VLRLIBERADO | Valor liberado | F |  |  | N |
| VLRLIMITE | Valor limite | F |  |  | N |
| VLRTOTAL | Valor Total | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | LiberacaoLimite / [TSILIB](TSILIB.md) | CODMETA->CODMETA | I |
| Financeiro / [TGFFIN](TGFFIN.md) | LiberacaoLimite / [TSILIB](TSILIB.md) | NUFIN->NUCHAVE | I |
| LiberacaoLimite / [TSILIB](TSILIB.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| LiberacaoLimite / [TSILIB](TSILIB.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |
| LiberacaoLimite / [TSILIB](TSILIB.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSULIB->CODUSU | I |
| LiberacaoLimite / [TSILIB](TSILIB.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUSOLICIT->CODUSU | I |
| LiberacaoLimite / [TSILIB](TSILIB.md) | ViewEventoLiberacao / [VGFLIBEVE](VGFLIBEVE.md) | EVENTO->EVENTO | I |
| LimiteLiberacaoBonificacao / [TSILBO](TSILBO.md) | LiberacaoLimite / [TSILIB](TSILIB.md) | NULBO->NULBO | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | LiberacaoLimite / [TSILIB](TSILIB.md) | CODTIPOPER->CODTIPOPER | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
