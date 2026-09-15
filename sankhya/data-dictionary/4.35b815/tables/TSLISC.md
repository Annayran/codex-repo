# TSLISC — Agendamento da Previsão de Vendas

- Módulo técnico: [TSL](../modules/TSL.md)
- Campos: 1
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PrevisaoVendasAgendamento | Agendamento da Previsão de Vendas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEXEC | Código do Produto | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PrevisaoVendasExecucoes / [TSLIPX](TSLIPX.md) | PrevisaoVendasAgendamento / [TSLISC](TSLISC.md) | CODEXEC->CODEXEC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
