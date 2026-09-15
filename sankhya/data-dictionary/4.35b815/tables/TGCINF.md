# TGCINF — Informações Necessárias para o Cálculo

- Módulo técnico: [TGC](../modules/TGC.md)
- Campos: 14
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| InformacaoNecessariaCalc | Informações Necessárias para o Cálculo | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CHAVE | Chave | S |  |  | N |
| CODCRED | Cód.Crédito | I |  |  | N |
| CODGRUPOCRED | Cód.Grupo Crédito | I |  |  | N |
| CODINFCRED | Cód.Informações de Crédito | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DECIMAIS | Decimais | I |  |  | N |
| DESCRINFCRED | Descrição das Informações de Crédito | S |  |  | N |
| DTALTER | Data Alteração | H |  |  | N |
| FORMULA | Fórmula | S |  |  | N |
| OBRIGATORIO | Obrigatório | S |  |  | N |
| OBS | Observação | S |  |  | N |
| ORDEM | Ordem | I |  |  | N |
| PERIODICIDADE | Periodicidade | I |  |  | N |
| TIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| GrupoInformacaoAnaliseCred / [TGCGRU](TGCGRU.md) | InformacaoNecessariaCalc / [TGCINF](TGCINF.md) | CODCRED->CODCRED; CODGRUPOCRED->CODGRUPOCRED | I |
| InformacaoNecessariaCalc / [TGCINF](TGCINF.md) | InformacaoCreditoParceiro / [TGCCAL](TGCCAL.md) | CHAVE->CHAVE | I |
| InformacaoNecessariaCalc / [TGCINF](TGCINF.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
