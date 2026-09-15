# TGFSBC — Saldos Bancários

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| SaldoBancario | Saldo Bancário | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCTABCOINT | Conta bancária | I |  |  | N |
| REFERENCIA | Referência | H |  |  | N |
| SALDOBCO | Saldo bancário | F |  |  | N |
| SALDOREAL | Saldo real | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MovimentoBancario / [TGFMBC](TGFMBC.md) | SaldoBancario / [TGFSBC](TGFSBC.md) | CODCTABCOINT->CODCTABCOINT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
