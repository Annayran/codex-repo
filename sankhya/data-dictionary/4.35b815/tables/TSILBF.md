# TSILBF — Liberação de Filtros

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 15
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LiberacaoFiltros | Liberação de Filtros | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| APROVADOPOR | Aprovado por | I |  |  | N |
| AUTOR | Autor | I |  |  | N |
| CHAVE | CHAVE | S |  |  | N |
| CODFILTRO | Cod. Filtro | I |  |  | N |
| CODSOL | Cód. Solicitação | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DTCRIACAO | Data de Criação | H |  |  | N |
| DTLIBERACAO | Data de Liberação | H |  |  | N |
| DTREPROVACAO | Data de Reprovação | H |  |  | N |
| EXPRESSAO | Expressão | S |  |  | N |
| MOTIVO | Motivo na Reprovação | S |  |  | N |
| NOMEFILTRO | Nome do Filtro | S |  |  | N |
| REPROVADOPOR | Reprovado por | I |  |  | N |
| STATUS | Status | S |  |  | N |
| TELA | Tela | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| LiberacaoFiltros / [TSILBF](TSILBF.md) | Usuario / [TSIUSU](TSIUSU.md) | AUTOR->CODUSU | I |
| LiberacaoFiltros / [TSILBF](TSILBF.md) | Usuario2 / [TSIUSU](TSIUSU.md) | APROVADOPOR->CODUSU | I |
| LiberacaoFiltros / [TSILBF](TSILBF.md) | Usuario3 / [TSIUSU](TSIUSU.md) | REPROVADOPOR->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
