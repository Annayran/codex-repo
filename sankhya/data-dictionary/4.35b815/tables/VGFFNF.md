# VGFFNF — Frete para Nota Financeiro

- Módulo técnico: [VGF](../modules/VGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ViewFinanceiroNotaFrete | Financeiro Nota Frete | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARC | Parceiro | I |  |  | N |
| DTNEG | Dt. Negociação | D |  |  | N |
| DTVENC | Dt. Vencimento | D |  |  | N |
| NUFIN | Número no Financeiro | I |  |  | N |
| NUNOTA | Número da Nota | I |  |  | N |
| TIPFRETE | Tipo do frete | S |  |  | N |
| VLRFRETE | Valor do frete | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ViewFinanceiroNotaFrete / [VGFFNF](VGFFNF.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
