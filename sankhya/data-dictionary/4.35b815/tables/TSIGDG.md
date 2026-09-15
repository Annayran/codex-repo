# TSIGDG — Tabela de Gadgets

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 18
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Gadget | Gadget | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| APVNC | Atualiza Preço pela Nota de compra | S |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| CATEGORIA | Categoria | S |  |  | N |
| CODUSU | Cód. Usuário Modificação | I |  |  | N |
| CODUSUINC | Cód. Usuário Inclusão | I |  |  | N |
| CONFIG | Configuração (XML) | C |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DHALTER | Dt./Hora Modificação | H |  |  | N |
| EVOCARD | Cartão Inteligente | S |  |  | N |
| GDGASSINADO | Assinado? | S |  |  | N |
| HTML5 | HTML5 | B |  |  | N |
| LAYOUT | Layout | S |  |  | N |
| NUGDG | Código | I |  |  | N |
| QTDANALISESUTILIZADAS | Quantidade de Análises | I |  |  | S |
| TEMHTML5 | Possui HTML5 | S |  |  | S |
| THUMBNAIL | Thumbnail | B |  |  | N |
| TITULO | Título | S |  |  | N |
| URLCOMPONENTE | URL do Componente | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DashboardPlayer / [TSITVP](TSITVP.md) | Gadget / [TSIGDG](TSIGDG.md) | NUGDG->NUGDG | I |
| DataSourceUsuario / [TSIDSU](TSIDSU.md) | Gadget / [TSIGDG](TSIGDG.md) | NUGDG->NUGDG | I |
| Gadget / [TSIGDG](TSIGDG.md) | DashboardGadget / [TSIDSG](TSIDSG.md) | NUGDG->NUGDG | I |
| GadgetBiaInformacao / [TSIGBI](TSIGBI.md) | Gadget / [TSIGDG](TSIGDG.md) | NUGDG->NUGDG | I |
| GadgetEventoLiberacao / [TSIGEL](TSIGEL.md) | Gadget / [TSIGDG](TSIGDG.md) | NUGDG->NUGDG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
