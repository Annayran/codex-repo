# TSIDSG — Gadgets por Dashboard

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DashboardGadget | DashboardGadget | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NUDSB | NUDSB | I |  |  | N |
| NUGDG | NUGDG | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Dashboard / [TSIDSB](TSIDSB.md) | DashboardGadget / [TSIDSG](TSIDSG.md) | NUDSB->NUDSB | I |
| Gadget / [TSIGDG](TSIGDG.md) | DashboardGadget / [TSIDSG](TSIDSG.md) | NUGDG->NUGDG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
