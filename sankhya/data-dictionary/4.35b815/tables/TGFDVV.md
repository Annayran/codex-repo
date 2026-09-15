# TGFDVV — Destinação Verbas

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DestinacaoVerbas | Destinação Verbas | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NUDESTINACAO | Nro. Destinação | I |  |  | N |
| NUVERBA | Nro. Verba | I |  |  | N |
| VALOR | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DestinacaoVerbas / [TGFDVV](TGFDVV.md) | DestinacaoVerbaMestre / [TGFDVM](TGFDVM.md) | NUDESTINACAO->NUDESTINACAO | I |
| DestinacaoVerbas / [TGFDVV](TGFDVV.md) | Verbas / [TGFGEV](TGFGEV.md) | NUVERBA->NUVERBA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
