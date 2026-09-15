# TCISAL_ATUAL — Saldo Imobilizado Atual

- Módulo técnico: [TCI](../modules/TCI.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| SaldoImobilizadoAtual | Saldo Imobilizado Atual | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODBEM | Cód. Bem | S |  |  | N |
| CODPROD | Produto | I |  |  | N |
| REFERENCIA | Data referência | D |  |  | N |
| SALDO | Saldo | F |  |  | N |
| TOTALDEP | Total depreciado | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Imobilizado / [TCIBEM](TCIBEM.md) | SaldoImobilizadoAtual / [TCISAL_ATUAL](TCISAL_ATUAL.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
