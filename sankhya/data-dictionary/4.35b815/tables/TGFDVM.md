# TGFDVM — Destinação Verba Mestre

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DestinacaoVerbaMestre | Destinação Verba Mestre | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cód. Usuário | I |  |  | N |
| DHALTER | Dt. Alteração | H |  |  | N |
| NUDESTINACAO | Nro. Destinação | I |  |  | N |
| STATUS | Status | S |  |  | N |
| TIPODEST | Tipo Destinação | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DestinacaoFinanceiros / [TGFDVF](TGFDVF.md) | DestinacaoVerbaMestre / [TGFDVM](TGFDVM.md) | NUDESTINACAO->NUDESTINACAO | I |
| DestinacaoVerbas / [TGFDVV](TGFDVV.md) | DestinacaoVerbaMestre / [TGFDVM](TGFDVM.md) | NUDESTINACAO->NUDESTINACAO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
