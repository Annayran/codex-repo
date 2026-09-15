# VSILIB — View Liberacao Limites

- Módulo técnico: [VSI](../modules/VSI.md)
- Campos: 45
- Instâncias DWF: 1
- Relações DWF: 11
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ViewLiberacaoLimite | Liberação de limite | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCENCUS | Cód.Centro Resultado | I |  |  | N |
| CODEMP | Cód.Empresa | I |  |  | N |
| CODNAT | Cód.Natureza | I |  |  | N |
| CODPARC | Cód.Parceiro | I |  |  | N |
| CODPARCTRANSP | Cód. Parceiro Transportadora | I |  |  | N |
| CODPROJ | Cód.Projeto | I |  |  | N |
| CODTIPOPER | Cód.Tipo Operação | I |  |  | N |
| CODUSULIB | Cód. Usuário Liberador | I |  |  | N |
| CODUSUSOLICIT | Cód. Usuário Solicitante | I |  |  | N |
| CODVEND | Cód.Vendedor | I |  |  | N |
| DHLIB | Data Liberação | H |  |  | N |
| DHSOLICIT | Data Solicitação | H |  |  | N |
| DHTIPOPER | Data Operação | H |  |  | N |
| DTNEG | Dt. Negociação | D |  |  | N |
| DTPREVENT | Previsão de entrega | H |  |  | N |
| DTVENC | Prim.Dt.Venc | D |  |  | N |
| EVENTO | Evento | I |  |  | N |
| NUCHAVE | Nro Único | I |  |  | N |
| NUCLL | Número Controle Liberação | I |  |  | N |
| NULBO | Número do limite de bonificação | I |  |  | N |
| NUNOTA | Nro Único Nota | I |  |  | N |
| NURNG | Regra de negócio | I |  |  | N |
| OBSCOMPL | Observação Complementar | S |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| OBSLIB | Observação Liberador | S |  |  | N |
| ORDEM | Ordem | I |  |  | N |
| PERCANTERIOR | % Anterior | F |  |  | N |
| PERCLIMITE | % Limite | F |  |  | N |
| PERCSOLIC | % Solicitado | F |  |  | S |
| PRECOLIQUIDO | Preço Liquido | F |  |  | N |
| REPROVADO | Reprovado | S |  |  | N |
| SEQCASCATA | Sequência Cascata | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| STATUSNOTA | Status Nota | S |  |  | N |
| SUPLEMENTO | Suplemento | S |  |  | N |
| TABELA | Tabela | S |  |  | N |
| TOTALDESCONTONOTA | Valor Desconto Nota | I |  |  | N |
| TRANSF | Transferência | S |  |  | N |
| VLRANTERIOR | Vlr.Solicit.Anterior | F |  |  | N |
| VLRATUAL | Valor Solicitado | F |  |  | N |
| VLRDESDOB | Valor Desconto | F |  |  | N |
| VLRLIBERADO | Valor Liberado | F |  |  | N |
| VLRLIMITE | Valor Limite | F |  |  | N |
| VLRNOTA | Valor Nota/Financ. | F |  |  | N |
| VLRTOTAL | Valor Total | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ViewLiberacaoLimite / [VSILIB](VSILIB.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| ViewLiberacaoLimite / [VSILIB](VSILIB.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ViewLiberacaoLimite / [VSILIB](VSILIB.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| ViewLiberacaoLimite / [VSILIB](VSILIB.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ViewLiberacaoLimite / [VSILIB](VSILIB.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| ViewLiberacaoLimite / [VSILIB](VSILIB.md) | Solicitante / [TSIUSU](TSIUSU.md) | CODUSUSOLICIT->CODUSU | I |
| ViewLiberacaoLimite / [VSILIB](VSILIB.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER; DHTIPOPER->DHALTER | I |
| ViewLiberacaoLimite / [VSILIB](VSILIB.md) | Transportadora / [TGFPAR](TGFPAR.md) | CODPARCTRANSP->CODPARC | I |
| ViewLiberacaoLimite / [VSILIB](VSILIB.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSULIB->CODUSU | I |
| ViewLiberacaoLimite / [VSILIB](VSILIB.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| ViewLiberacaoLimite / [VSILIB](VSILIB.md) | ViewEventoLiberacao / [VGFLIBEVE](VGFLIBEVE.md) | EVENTO->EVENTO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
