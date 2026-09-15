# TSLIPX — Tabela de execução de previsão

- Módulo técnico: [TSL](../modules/TSL.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PrevisaoVendasExecucoes | Tabela de execução de previsão | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEXEC | Código da execução | I |  |  | N |
| CODGRUPO | Código do grupo de previsões. | I |  |  | N |
| CODPROD | Código do Produto | I |  |  | N |
| DIRETORIO | Diretório da previsão na AWS | S |  |  | N |
| TIPO | Tipo de Previsão | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PrevisaoVendasExecucoes / [TSLIPX](TSLIPX.md) | PrevisaoVariaveisAdicionais / [TSLIVA](TSLIVA.md) | CODEXEC->CODEXEC | I |
| PrevisaoVendasExecucoes / [TSLIPX](TSLIPX.md) | PrevisaoVendasAgendamento / [TSLISC](TSLISC.md) | CODEXEC->CODEXEC | I |
| PrevisaoVendasExecucoes / [TSLIPX](TSLIPX.md) | PrevisaoVendasDados / [TSLIPV](TSLIPV.md) | CODEXEC->CODEXEC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
