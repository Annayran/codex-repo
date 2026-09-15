# TGCGRU — Grupo para Informações da Análise de Crédito

- Módulo técnico: [TGC](../modules/TGC.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| GrupoInformacaoAnaliseCred | Grupo para Informações da Análise de Crédito | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCRED | Cód.Crédito | I |  |  | N |
| CODGRUPOCRED | Cód.Grupo Crédito | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DESCRGRUPOCRED | Descrição Grupo Crédito | S |  |  | N |
| DTALTER | Data Alteração | H |  |  | N |
| ORDEM | Ordem | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| GrupoAnaliseCredito / [TGCCRED](TGCCRED.md) | GrupoInformacaoAnaliseCred / [TGCGRU](TGCGRU.md) | CODCRED->CODCRED | I |
| GrupoInformacaoAnaliseCred / [TGCGRU](TGCGRU.md) | GrupoAnaliseCredito / [TGCCRED](TGCCRED.md) | CODCRED->CODCRED | I |
| GrupoInformacaoAnaliseCred / [TGCGRU](TGCGRU.md) | InformacaoNecessariaCalc / [TGCINF](TGCINF.md) | CODCRED->CODCRED; CODGRUPOCRED->CODGRUPOCRED | I |
| GrupoInformacaoAnaliseCred / [TGCGRU](TGCGRU.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
