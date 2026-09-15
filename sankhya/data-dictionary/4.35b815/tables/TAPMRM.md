# TAPMRM — Movimento de RM

- Módulo técnico: [TAP](../modules/TAP.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MovimentoRM | Movimento de RM | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROJ | CODPROJ | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DHMOV | DHMOV | H |  |  | N |
| NUMCONTRATO | NUMCONTRATO | I |  |  | N |
| NURM | NURM | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FaturamentoRM / [TAPFRM](TAPFRM.md) | MovimentoRM / [TAPMRM](TAPMRM.md) | NURM->NURM | I |
| ItemMovimentoRM / [TAPIRM](TAPIRM.md) | MovimentoRM / [TAPMRM](TAPMRM.md) | NURM->NURM | I |
| MovimentoRM / [TAPMRM](TAPMRM.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| MovimentoRM / [TAPMRM](TAPMRM.md) | ItemMovimentoRM / [TAPIRM](TAPIRM.md) | NURM->NURM | I |
| MovimentoRM / [TAPMRM](TAPMRM.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| MovimentoRM / [TAPMRM](TAPMRM.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
