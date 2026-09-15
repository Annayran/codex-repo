# TCSITE_DAS — Ítens da Ordem de Serviço Doc Assinados

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemOSDocAssinados | Ítens da Ordem de Serviço Doc Assinados | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NUDOC | Núm. Documento | I |  |  | N |
| NUMITEM | Sub-OS | I |  |  | N |
| NUMOS | Núm. OS | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItemOrdemServico / [TCSITE](TCSITE.md) | ItemOSDocAssinados / [TCSITE_DAS](TCSITE_DAS.md) | NUMITEM->NUMITEM; NUMOS->NUMOS | I |
| ItemOSDocAssinados / [TCSITE_DAS](TCSITE_DAS.md) | DocumentoAssinado / [TSIDAS](TSIDAS.md) | NUDOC->NUDOC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
