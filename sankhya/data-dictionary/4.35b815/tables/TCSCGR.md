# TCSCGR — Comercial Grupo

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 3
- Instâncias DWF: 2
- Relações DWF: 7
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ComercialGrupo | Comercial Grupo | S | N |
| GrupoInstalacao | Gerencia de Negócios | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| GRUPO | Grupo | I |  |  | N |
| NUMCONTRATO | Contrato | I |  |  | N |
| QTDPARC | Quantidade de parceiros | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ComercialGrupo / [TCSCGR](TCSCGR.md) | ComercialParceiro / [TCSCPA](TCSCPA.md) | GRUPO->GRUPO; NUMCONTRATO->NUMCONTRATO | I |
| ComercialGrupo / [TCSCGR](TCSCGR.md) | ComercialProduto / [TCSCPR](TCSCPR.md) | GRUPO->GRUPO; NUMCONTRATO->NUMCONTRATO | I |
| ComercialGrupo / [TCSCGR](TCSCGR.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| ComercialParceiro / [TCSCPA](TCSCPA.md) | ComercialGrupo / [TCSCGR](TCSCGR.md) | GRUPO->GRUPO; NUMCONTRATO->NUMCONTRATO | I |
| Contrato / [TCSCON](TCSCON.md) | GrupoInstalacao / [TCSCGR](TCSCGR.md) | NUMCONTRATO->NUMCONTRATO | I |
| GrupoInstalacao / [TCSCGR](TCSCGR.md) | ComercialProduto / [TCSCPR](TCSCPR.md) | GRUPO->GRUPO; NUMCONTRATO->NUMCONTRATO | I |
| GrupoInstalacao / [TCSCGR](TCSCGR.md) | ParceiroInstalacao / [TCSCPA](TCSCPA.md) | GRUPO->GRUPO; NUMCONTRATO->NUMCONTRATO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
