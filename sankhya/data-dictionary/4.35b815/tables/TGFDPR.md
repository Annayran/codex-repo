# TGFDPR — TABLE TGFDPR

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DetalheReajusteContrato | Detalhe de Reajuste de Contratos | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARC | Parceiro | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| NUAGENDAMENTO | NUAGENDAMENTO | I |  |  | N |
| NUDETALHE | NUDETALHE | I |  |  | N |
| NUHISTORICO | NUHISTORICO | I |  |  | N |
| NUMCONTRATO | Núm. Contrato | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |
| STATUS | STATUS | S |  |  | N |
| TIPOAGE | TIPOAGE | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DetalheReajusteContrato / [TGFDPR](TGFDPR.md) | HistoricoReajusteContrato / [TGFHIS](TGFHIS.md) | NUAGENDAMENTO->NUAGENDAMENTO; NUHISTORICO->NUHISTORICO; SEQUENCIA->SEQUENCIA; TIPOAGE->TIPOAGE | I |
| DetalheReajusteContrato / [TGFDPR](TGFDPR.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| DetalheReajusteContrato / [TGFDPR](TGFDPR.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| HistoricoReajusteContrato / [TGFHIS](TGFHIS.md) | DetalheReajusteContrato / [TGFDPR](TGFDPR.md) | NUAGENDAMENTO->NUAGENDAMENTO; NUHISTORICO->NUHISTORICO; SEQUENCIA->SEQUENCIA; TIPOAGE->TIPOAGE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
