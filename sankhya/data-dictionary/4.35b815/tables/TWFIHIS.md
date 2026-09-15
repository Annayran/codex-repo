# TWFIHIS — Histórico de Intância de Processo

- Módulo técnico: [TWF](../modules/TWF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| HistoricoInstanciaProcesso | Histórico de Instância de Processo | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DHEXECUCAO | Dh. Execução | H |  |  | N |
| IDELEMENTO | Id Elemento | S |  |  | N |
| IDINSTELE | Id Instância do Elemento | I |  |  | N |
| IDINSTPRN | Id Instância de Processo | I |  |  | N |
| RESUMO | Resumo | S |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| STATUS | Status | S |  |  | N |
| TEMPOEXECUCAO | Tempo de execução | I |  |  | N |
| TIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| HistoricoInstanciaProcesso / [TWFIHIS](TWFIHIS.md) | InstanciaProcesso / [TWFIPRN](TWFIPRN.md) | IDINSTPRN->IDINSTPRN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
