# TCIDEM — Demonstrativos

- Módulo técnico: [TCI](../modules/TCI.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DemonstrativoTcidem | Demonstrativos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODBEM | Bem | S |  |  | N |
| CODPROD | Produto | I |  |  | N |
| REFERENCIA | Data referência | H |  |  | N |
| SALDOFINAL | Saldo final | F |  |  | N |
| SALDOINICIAL | Saldo inicial | F |  |  | N |
| TOTDEPFINAL | Total Depósito Final | F |  |  | N |
| TOTDEPINICIAL | Total Depósito Inicial | F |  |  | N |
| VLRBAIXA15 | Valor baixa 15 | F |  |  | N |
| VLRBAIXA16 | Valor baixa 16 | F |  |  | N |
| VLRDEP | Valor do Depósito | F |  |  | N |
| VLRINC15 | VLRINC15 | F |  |  | N |
| VLRINC16 | VLRINC16 | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DemonstrativoTcidem / [TCIDEM](TCIDEM.md) | Imobilizado / [TCIBEM](TCIBEM.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |
| DemonstrativoTcidem / [TCIDEM](TCIDEM.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
