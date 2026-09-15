# TCISAL — Saldos

- Módulo técnico: [TCI](../modules/TCI.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| saldoImobilizado | Saldos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODBEM | Cód.Bem | S |  |  | N |
| CODPROD | Cód.Produto | I |  |  | N |
| REFERENCIA | Data referência | D |  |  | N |
| SALDO | Saldo | F |  |  | N |
| TOTALDEP | Total depreciado | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Imobilizado / [TCIBEM](TCIBEM.md) | saldoImobilizado / [TCISAL](TCISAL.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |
| saldoImobilizado / [TCISAL](TCISAL.md) | Imobilizado / [TCIBEM](TCIBEM.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |
| saldoImobilizado / [TCISAL](TCISAL.md) | MovimentacaoImobilizado / [TCIMOV](TCIMOV.md) | CODBEM->CODBEM; CODPROD->CODPROD; REFERENCIA->REFERENCIA | I |
| saldoImobilizado / [TCISAL](TCISAL.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
