# TGFDFT — TABLE TGFDFT

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DetalheFaturamentoContrato | Detalhe de Reajuste de Contratos | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARC | Parceiro | I |  |  | N |
| NUAGENDAMENTO | NUAGENDAMENTO | I |  |  | N |
| NUDETALHE | NUDETALHE | I |  |  | N |
| NUHISTORICO | NUHISTORICO | I |  |  | N |
| NUMCONTRATO | Núm. Contrato | I |  |  | N |
| NUNOTA | Núm. Contrato | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |
| STATUS | STATUS | S |  |  | N |
| TIPOAGE | TIPOAGE | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DetalheFaturamentoContrato / [TGFDFT](TGFDFT.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| DetalheFaturamentoContrato / [TGFDFT](TGFDFT.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| DetalheFaturamentoContrato / [TGFDFT](TGFDFT.md) | HistoricoFaturamentoContrato / [TGFHFT](TGFHFT.md) | NUAGENDAMENTO->NUAGENDAMENTO; NUHISTORICO->NUHISTORICO; SEQUENCIA->SEQUENCIA; TIPOAGE->TIPOAGE | I |
| DetalheFaturamentoContrato / [TGFDFT](TGFDFT.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| HistoricoFaturamentoContrato / [TGFHFT](TGFHFT.md) | DetalheFaturamentoContrato / [TGFDFT](TGFDFT.md) | NUAGENDAMENTO->NUAGENDAMENTO; NUHISTORICO->NUHISTORICO; SEQUENCIA->SEQUENCIA; TIPOAGE->TIPOAGE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
