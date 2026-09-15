# TGFFCT — Formatador Remessa por Conta

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FormatadorRemessaPorConta | Formatador Remessa Por Conta | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCTABCOINT | Conta Bancária | I |  |  | N |
| CODFORMREM | Formatador Remessa | I |  |  | N |
| MODULO | Módulo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FormatadorRemessa / [TSIREM](TSIREM.md) | FormatadorRemessaPorConta / [TGFFCT](TGFFCT.md) | CODIGO->CODFORMREM; MODULO->MODULO | I |
| FormatadorRemessaPorConta / [TGFFCT](TGFFCT.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| FormatadorRemessaPorConta / [TGFFCT](TGFFCT.md) | FormatadorRemessa / [TSIREM](TSIREM.md) | CODFORMREM->CODIGO; MODULO->MODULO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
