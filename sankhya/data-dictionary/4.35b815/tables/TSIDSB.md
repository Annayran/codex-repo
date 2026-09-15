# TSIDSB — Tabela de Dashboards

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Dashboard | Dashboard | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cód. Usuário | I |  |  | N |
| CODUSUINC | Cód. Usuário Inclusão | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DHALTER | Data/Hora Modificação | H |  |  | N |
| GRUPO | Grupo | S |  |  | N |
| LAYOUT | Layout | C |  |  | N |
| NUDSB | Dashboard | I |  |  | N |
| TITULO | Título | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Dashboard / [TSIDSB](TSIDSB.md) | DashboardGadget / [TSIDSG](TSIDSG.md) | NUDSB->NUDSB | I |
| GadgetBiaConhecimento / [TSIGBC](TSIGBC.md) | Dashboard / [TSIDSB](TSIDSB.md) | NUDSB->NUDSB | I |
| ItensPacote / [TSSITP](TSSITP.md) | Dashboard / [TSIDSB](TSIDSB.md) | NUDSB->NUDSB | I |
| MetaGestaoIndicadores / [TMIMET](TMIMET.md) | Dashboard / [TSIDSB](TSIDSB.md) | NUDSB->NUDSB | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
