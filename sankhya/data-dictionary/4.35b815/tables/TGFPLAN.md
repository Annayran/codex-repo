# TGFPLAN — Planejamento de Entrega

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 17
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PlanejamentoEntrega | Planejamento de Entrega | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | S |  |  | S |
| CODPARC | Parceiro | S |  |  | S |
| CODTIPOPER | Tipo de Operação | S |  |  | S |
| CODUSU | Usuário Responsável | I |  |  | N |
| DHALTERTOP | Data de Alteração da TOP | H |  |  | S |
| DTALTER | Data de Alteração | D |  |  | N |
| DTNEG | Data de Negociação | D |  |  | S |
| GRUPO | Último Grupo | I |  |  | S |
| MODENTREGA | Modalidade de Entrega | S |  |  | S |
| NUMNOTADEST | Nro. Nota Destino | I |  |  | S |
| NUMNOTAORIG | Nro. Pedido Origem | I |  |  | S |
| NUNOTADEST | Nro. Único Nota Destino | I |  |  | S |
| NUNOTAORIG | Nro. Único Pedido Origem | I |  |  | N |
| NUPLAN | Nro. Planejamento | I |  |  | N |
| SITUACAO | Situação | S |  |  | N |
| TOTALCESTASCONTATO | Total de Cestas dos Contatos | I |  |  | S |
| TOTALITENS | Total de Itens do Planejamento | I |  |  | S |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PlanejamentoEntrega / [TGFPLAN](TGFPLAN.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTAORIG->NUNOTA | I |
| PlanejamentoEntrega / [TGFPLAN](TGFPLAN.md) | CabecalhoPlanejamentoEntrega / [TGFPLAC](TGFPLAC.md) | NUPLAN->NUPLAN | I |
| PlanejamentoEntrega / [TGFPLAN](TGFPLAN.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| PlanejamentoEntrega / [TGFPLAN](TGFPLAN.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| PlanejamentoEntrega / [TGFPLAN](TGFPLAN.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER; DHALTERTOP->DHALTER | I |
| PlanejamentoEntrega / [TGFPLAN](TGFPLAN.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
