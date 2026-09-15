# TGCPAR — GC Limites de Crédito

- Módulo técnico: [TGC](../modules/TGC.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LimiteCredito | GC Limites de Crédito | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCRED | Cód.Crédito | I |  |  | N |
| CODPARC | Cód.Parceiro | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CODUSUCALC | Cód. Usuário Calculo | I |  |  | N |
| DTCALC | Data Cálculo | H |  |  | N |
| DTEFETIVACAO | Data Efetivação | H |  |  | N |
| LIMCRED | Limite Crédito | F |  |  | N |
| LIMCREDCALC | Cálculo Limite Crédito | F |  |  | N |
| LIMCREDMENSAL | Limite Crédito Mensal | F |  |  | N |
| LIMCREDMENSALCALC | Cálculo Limite Crédito Mensal | F |  |  | N |
| OBS | Observação | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| LimiteCredito / [TGCPAR](TGCPAR.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| LimiteCredito / [TGCPAR](TGCPAR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Parceiro / [TGFPAR](TGFPAR.md) | LimiteCredito / [TGCPAR](TGCPAR.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
