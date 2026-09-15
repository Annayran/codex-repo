# TGIRAT — TGIRAT

- Módulo técnico: [TGI](../modules/TGI.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RateioPropostaComercial | Rateio | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CHAVE | CHAVE | I |  |  | N |
| CODUSU | CODUSU | F |  |  | N |
| DTALTER | DTALTER | H |  |  | N |
| NUPROJETO | NUPROJETO | I |  |  | N |
| PERCRATEIO | PERCRATEIO | F |  |  | N |
| RATEADO | RATEADO | S |  |  | N |
| SEQITEM | SEQITEM | I |  |  | N |
| SEQKIT | SEQKIT | I |  |  | N |
| TIPO | TIPO | S |  |  | N |
| VERSAOPROJETO | VERSAOPROJETO | S |  |  | N |
| VLRRATEIO | VLRRATEIO | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoPropostaComercial / [TGICAB](TGICAB.md) | RateioPropostaComercial / [TGIRAT](TGIRAT.md) | NUPROJETO->NUPROJETO; VERSAOPROJETO->VERSAOPROJETO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
