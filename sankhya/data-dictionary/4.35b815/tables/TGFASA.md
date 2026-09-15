# TGFASA — Adiantamento Saldo Flex

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AdiantamentoSaldoFlex | Adiantamento Saldo Flex | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cód. Usuário | I |  |  | N |
| CODVEND | Cód. Vendedor | I |  |  | N |
| DTADIAN | Data Adiantamento | H |  |  | N |
| DTALTER | Data Alteração | H |  |  | N |
| NUADIANT | Adiantamento | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| PENDENTE | Pendente | S |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| SINAL | Sinal | I |  |  | N |
| VALOR | Valor Adiantamento | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AdiantamentoSaldoFlex / [TGFASA](TGFASA.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| AdiantamentoSaldoFlex / [TGFASA](TGFASA.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
