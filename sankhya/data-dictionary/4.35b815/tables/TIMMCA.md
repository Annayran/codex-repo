# TIMMCA — TABLE TIMMCA

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimMotivoCancelamento | Motivos de Cancelamento | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| MCACMOTIVO | Motivo de Cancelamento | S |  |  | N |
| MCACODIGO | Código | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | TimMotivoCancelamento / [TIMMCA](TIMMCA.md) | SACMTVCANCELADO->MCACODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
