# TGFEXR — Execução Régua de cobrança

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ExecReguaCobranca | Execução Régua de cobrança | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ACONTECE | Acontece | S |  |  | S |
| CODEVENTO | Código do Evento | I |  |  | N |
| CODREGUA | Código da Régua | I |  |  | N |
| DESCRICAO | Evento | S |  |  | N |
| DHEXEC | Execução | H |  |  | N |
| QTDTITULOFALHA | Qtd. de Títulos Falha | I |  |  | N |
| QTDTITULOSUCESSO | Qtd. de Títulos Sucesso | I |  |  | N |
| RESULTADO | Resultado | S |  |  | N |
| RESUMO | Resumo | S |  |  | N |
| SEQEXEC | Sequência de Execução | I |  |  | N |
| TEMPOEXECUCAO | Tempo de Execução | F |  |  | N |
| TIPOEVENTO | Tipo | I |  |  | N |
| ULTIMAALTERACAOEVENTO | Última Alteração | H |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ExecReguaCobranca / [TGFEXR](TGFEXR.md) | EventosCobranca / [TGFECB](TGFECB.md) | CODEVENTO->CODEVENTO; CODREGUA->CODREGUA; TIPOEVENTO->TIPO | I |
| ExecReguaCobranca / [TGFEXR](TGFEXR.md) | ReguaCobranca / [TGFRCB](TGFRCB.md) | CODREGUA->CODREGUA | I |
| ExecReguaCobranca / [TGFEXR](TGFEXR.md) | TituloPorEventoRegua / [TGFTXE](TGFTXE.md) | CODREGUA->CODREGUA; SEQEXEC->SEQEXEC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
