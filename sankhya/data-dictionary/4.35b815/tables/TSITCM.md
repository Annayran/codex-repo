# TSITCM — Telemetria de Customizações

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 15
- Instâncias DWF: 1
- Relações DWF: 0
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TelemetriaCustomizacoes | Telemetria de Customizações | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DESCRICAO | Descrição | S |  |  | N |
| DETALHES | Detalhes | S |  |  | N |
| DHEXECUCAO | Dt. Execução | D |  |  | N |
| ENTIDADE | Entidade | S |  |  | N |
| IDTIPO | ID | S |  |  | N |
| IPSERVER | IP | S |  |  | S |
| PATHSERVER | Caminho Absoluto | S |  |  | S |
| PORTASERVER | Porta | S |  |  | S |
| PROCESSO | Processo | S |  |  | N |
| QTDCOLETA | Qtd. Execuções | I |  |  | N |
| QTDERROS | Qtd. Erros | I |  |  | N |
| SERVERID | Hash Info. Server | S |  |  | N |
| TEMPOMEDIO | Tempo Médio (ms) | I |  |  | N |
| TEMPOTOTAL | Total Exec. (ms) | I |  |  | N |
| TIPO | Tipo | S |  |  | N |

## Relações DWF

Nenhuma relação DWF encontrada nesta exportação.

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
