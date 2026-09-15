# TGCCAL — Informações de Crédito do Parceiro

- Módulo técnico: [TGC](../modules/TGC.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| InformacaoCreditoParceiro | Informações de Crédito do Parceiro | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CHAVE | Chave | S |  |  | N |
| CODCRED | Cód.Crédito | I |  |  | N |
| CODPARC | Cód.Parceiro | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DIGITADO | Digitado | S |  |  | N |
| DTCALC | Data Cálculo | H |  |  | N |
| FORMULA | Fórmula | S |  |  | N |
| OBS | Observação | S |  |  | N |
| VALOR | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| InformacaoCreditoParceiro / [TGCCAL](TGCCAL.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| InformacaoCreditoParceiro / [TGCCAL](TGCCAL.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| InformacaoNecessariaCalc / [TGCINF](TGCINF.md) | InformacaoCreditoParceiro / [TGCCAL](TGCCAL.md) | CHAVE->CHAVE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
