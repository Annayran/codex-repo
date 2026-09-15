# TAPFAT — Resumo de Faturamento

- Módulo técnico: [TAP](../modules/TAP.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ResumoFaturamento | Resumo de Faturamento | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cód. Usuário | I |  |  | N |
| DHMOV | DHMOV | H |  |  | N |
| DTFINAL | DTFINAL | H |  |  | N |
| DTINICIAL | DTINICIAL | H |  |  | N |
| NUFAT | NUFAT | I |  |  | N |
| NUMCONTRATO | NUMCONTRATO | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FaturamentoRM / [TAPFRM](TAPFRM.md) | ResumoFaturamento / [TAPFAT](TAPFAT.md) | NUFAT->NUFAT | I |
| ItemResumoFaturamento / [TAPIFT](TAPIFT.md) | ResumoFaturamento / [TAPFAT](TAPFAT.md) | NUFAT->NUFAT | I |
| ResumoFaturamento / [TAPFAT](TAPFAT.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| ResumoFaturamento / [TAPFAT](TAPFAT.md) | FaturamentoRM / [TAPFRM](TAPFRM.md) | NUFAT->NUFAT | I |
| ResumoFaturamento / [TAPFAT](TAPFAT.md) | ItemResumoFaturamento / [TAPIFT](TAPIFT.md) | NUFAT->NUFAT | I |
| ResumoFaturamento / [TAPFAT](TAPFAT.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
