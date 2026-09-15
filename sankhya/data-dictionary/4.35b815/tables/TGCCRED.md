# TGCCRED — Grupo para Análise de Crédito

- Módulo técnico: [TGC](../modules/TGC.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| GrupoAnaliseCredito | Grupo para Análise de Crédito | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCRED | Cód.Crédito | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DESCRCRED | Descrição Crédito | S |  |  | N |
| DTALTER | Data Alteração | H |  |  | N |
| FORMULALC | Fórmula Limite de Crédito | S |  |  | N |
| FORMULALCM | Fórmula Crédito Mensal | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ComplementoParc / [TGFCPL](TGFCPL.md) | GrupoAnaliseCredito / [TGCCRED](TGCCRED.md) | CODCRED->CODCRED | I |
| GrupoAnaliseCredito / [TGCCRED](TGCCRED.md) | ComplementoParc / [TGFCPL](TGFCPL.md) | CODCRED->CODCRED | I |
| GrupoAnaliseCredito / [TGCCRED](TGCCRED.md) | GrupoInformacaoAnaliseCred / [TGCGRU](TGCGRU.md) | CODCRED->CODCRED | I |
| GrupoAnaliseCredito / [TGCCRED](TGCCRED.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| GrupoInformacaoAnaliseCred / [TGCGRU](TGCGRU.md) | GrupoAnaliseCredito / [TGCCRED](TGCCRED.md) | CODCRED->CODCRED | I |
| Parceiro / [TGFPAR](TGFPAR.md) | GrupoAnaliseCredito / [TGCCRED](TGCCRED.md) | CODCRED->CODCRED | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
