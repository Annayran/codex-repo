# TWFIEXE — Apontameto Execução Tarefa

- Módulo técnico: [TWF](../modules/TWF.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ApontamentoExecucaoTarefa | Apontameto de Execução de Tarefa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cód. usuário | I |  |  | N |
| CODUSUALTER | Cod. usuário alteração | I |  |  | N |
| DHALTER | Dh. alteração | H |  |  | N |
| DHFINAL | Dh. final | H |  |  | N |
| DHINICIAL | Dh. inicial | H |  |  | N |
| IDINSTPRN | Id Instância do Processo | I |  |  | N |
| IDINSTTAR | Id Instância da Tarefa | I |  |  | N |
| INTERVALO | Intervalo | T |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TEMPO | Tempo | T |  |  | S |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ApontamentoExecucaoTarefa / [TWFIEXE](TWFIEXE.md) | InstanciaTarefa / [TWFITAR](TWFITAR.md) | IDINSTPRN->IDINSTPRN; IDINSTTAR->IDINSTTAR | I |
| ApontamentoExecucaoTarefa / [TWFIEXE](TWFIEXE.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ApontamentoExecucaoTarefa / [TWFIEXE](TWFIEXE.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUALTER->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
