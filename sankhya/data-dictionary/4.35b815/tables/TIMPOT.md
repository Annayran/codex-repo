# TIMPOT — TimTelasPoi

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimTelasPoi | Telas do Relatório | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPOI | Cód. POI | I |  |  | N |
| CODPOI_BKP | Cód. POI | I |  |  | N |
| EXPRESSAO | Expressão | S |  |  | N |
| RESOURCEID | Resouce ID | S |  |  | N |
| SEQUENCIA | Sequencia | I |  |  | N |
| SEQUENCIA_BKP | Sequencia | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimRelatorioPoi / [TIMPOI](TIMPOI.md) | TimTelasPoi / [TIMPOT](TIMPOT.md) | CODPOI->CODPOI | I |
| TimTelasPoi / [TIMPOT](TIMPOT.md) | TimRelatorioPoi / [TIMPOI](TIMPOI.md) | CODPOI->CODPOI | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
