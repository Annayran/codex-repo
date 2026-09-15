# TAPIFT — Item de Faturamento

- Módulo técnico: [TAP](../modules/TAP.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemResumoFaturamento | Item de Resumo de Faturamento | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODSERV | CODSERV | I |  |  | N |
| CODVOL | CODVOL | S |  |  | N |
| NUFAT | NUFAT | I |  |  | N |
| QTDCOMDESC | QTDCOMDESC | F |  |  | N |
| QTDDESC | QTDDESC | F |  |  | N |
| QTDRET | QTDRET | F |  |  | N |
| QTDSEMDESC | QTDSEMDESC | F |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |
| VLRUNIT | VLRUNIT | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItemResumoFaturamento / [TAPIFT](TAPIFT.md) | ResumoFaturamento / [TAPFAT](TAPFAT.md) | NUFAT->NUFAT | I |
| ItemResumoFaturamento / [TAPIFT](TAPIFT.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERV->CODPROD | I |
| ResumoFaturamento / [TAPFAT](TAPFAT.md) | ItemResumoFaturamento / [TAPIFT](TAPIFT.md) | NUFAT->NUFAT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
