# TAPCAM — Coleta amostra

- Módulo técnico: [TAP](../modules/TAP.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ColetaAmostra | Coleta amostra | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODBARRA | CODBARRA | S |  |  | N |
| DHREMESSA | DHREMESSA | H |  |  | N |
| HRCOLETA | HRCOLETA | I |  |  | N |
| IDAMOSTRA | IDAMOSTRA | S |  |  | N |
| NUAMOSTRA | NUAMOSTRA | I |  |  | N |
| NUAPONTA | NUAPONTA | I |  |  | N |
| NUMCUSTODIA | NUMCUSTODIA | I |  |  | N |
| NUMITEM | NUMITEM | I |  |  | N |
| OBSREC | OBSREC | S |  |  | N |
| PROFFINAL | PROFFINAL | F |  |  | N |
| PROFINICIAL | PROFINICIAL | F |  |  | N |
| STATUSREC | STATUSREC | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Amostra / [TAPAMO](TAPAMO.md) | ColetaAmostra / [TAPCAM](TAPCAM.md) | NUAMOSTRA->NUAMOSTRA | I |
| ItemApontamento / [TAPIAP](TAPIAP.md) | ColetaAmostra / [TAPCAM](TAPCAM.md) | NUAPONTA->NUAPONTA; NUMITEM->NUMITEM | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
