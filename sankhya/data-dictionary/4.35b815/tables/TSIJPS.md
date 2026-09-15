# TSIJPS — Status de trabalho de impressão

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| StatusTrabalhoImpressao | Status de trabalho de impressão | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DESCRIPTION | Status | S |  |  | N |
| JOBSTATUS | Código | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TrabalhoImpressao / [TSIJPR](TSIJPR.md) | StatusTrabalhoImpressao / [TSIJPS](TSIJPS.md) | JOBSTATUS->JOBSTATUS | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
