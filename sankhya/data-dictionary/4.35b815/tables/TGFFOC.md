# TGFFOC — Fórmulas de Comissão

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 16
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FormulasComissao | Fórmulas de Comissão | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEVENTO | Cód. Evento | I |  |  | N |
| CODFORM | Código | I |  |  | N |
| DESCRFORM | Descrição | S |  |  | N |
| HISTREGPROP | Histórico região própria | I |  |  | N |
| HISTREGTERC | Histórico região terceiros | I |  |  | N |
| HISTSUPREGPROP | Hist Sup região própria | I |  |  | N |
| HISTSUPREGTERC | Hist Sup região terceiros | I |  |  | N |
| HISTSUPTERCREG | Hist Sup terceiros região | I |  |  | N |
| HISTTERCREG | Histórico terceiros região | I |  |  | N |
| SUPREGPROP | Supervisor região própria | S |  |  | N |
| SUPREGTERC | Supervisor região terceiros | S |  |  | N |
| SUPTERCREG | Supervisor terceiros região | S |  |  | N |
| TIPINTEGRA | Tipo de  Integração | S |  |  | N |
| VENDAREGPROP | Venda região própria | S |  |  | N |
| VENDAREGTERC | Venda região terceiros | S |  |  | N |
| VENDATERCREG | Venda terceiros região | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FormulaComissaoPremiacao / [TGFFCV](TGFFCV.md) | FormulasComissao / [TGFFOC](TGFFOC.md) | CODFORM->CODFORM | I |
| FormulasComissao / [TGFFOC](TGFFOC.md) | EventosPessoal /  | CODEVENTO->CODEVENTO | I |
| FormulasComissao / [TGFFOC](TGFFOC.md) | HistoricoComissoes / [TGFHCO](TGFHCO.md) | HISTREGPROP->CODHISTCOM | I |
| Vendedor / [TGFVEN](TGFVEN.md) | FormulasComissao / [TGFFOC](TGFFOC.md) | CODFORM->CODFORM | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
