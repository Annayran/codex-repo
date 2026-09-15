# TSIEVP — Evento Programável

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EventoProgramavel | Evento Programável | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CONFIG | Config | C |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| NOMEINSTANCIA | Instância | S |  |  | N |
| NUEVENTO | Num. Evento | I |  |  | N |
| ORDEM | Ordem | I |  |  | N |
| RESOURCEID | Filtro de telas | S |  |  | N |
| TIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FormularioFormatado / [TDDINS](TDDINS.md) | EventoProgramavel / [TSIEVP](TSIEVP.md) | NOMEINSTANCIA->NOMEINSTANCIA | I |
| Instancia / [TDDINS](TDDINS.md) | EventoProgramavel / [TSIEVP](TSIEVP.md) | NOMEINSTANCIA->NOMEINSTANCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
