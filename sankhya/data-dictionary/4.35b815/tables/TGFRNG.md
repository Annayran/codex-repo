# TGFRNG — RegraNegocio

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RegraNegocio | Regra de Negócio | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativa | S |  |  | N |
| CODUSULIB | Cód. Usuário Liberador | I |  |  | N |
| CONFIG | Expressão | C |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| EVENTO | Evento de liberação | I |  |  | N |
| EXPRESSAO | Expressão | S |  |  | N |
| INIBELIB | Inibe alteração do liberador | S |  |  | N |
| NURNG | Num. Regra | I |  |  | N |
| ONDE | Onde | S |  |  | N |
| QUANDO | Quando | S |  |  | N |
| TIPOEXPRESSAO | Tipo de expressão | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| RegraNegocio / [TGFRNG](TGFRNG.md) | TopRegraNegocio / [TGFTRN](TGFTRN.md) | NURNG->NURNG | I |
| RegraNegocio / [TGFRNG](TGFRNG.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSULIB->CODUSU | I |
| RegraNegocio / [TGFRNG](TGFRNG.md) | ViewEventoLiberacao / [VGFLIBEVE](VGFLIBEVE.md) | EVENTO->EVENTO | I |
| TopRegraNegocio / [TGFTRN](TGFTRN.md) | RegraNegocio / [TGFRNG](TGFRNG.md) | NURNG->NURNG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
