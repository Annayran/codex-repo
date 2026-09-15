# TGFPLAI — Itens do Planejamento de Entrega

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemPlanejamentoEntrega | Itens Planejamento de Entrega | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCONTATO | Contato | I |  |  | N |
| CODPARCFAT | Cód. Parceiro | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| NUNOTAORIG | Nro. Único Pedido Orig. | I |  |  | N |
| NUPLAN | Nro. Planejamento | I |  |  | N |
| QTDNEG | Quantidade | I |  |  | N |
| SEQUENCIAORIG | Sequência Pedido Orig. | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoPlanejamentoEntrega / [TGFPLAC](TGFPLAC.md) | ItemPlanejamentoEntrega / [TGFPLAI](TGFPLAI.md) | CODCONTATO->CODCONTATO; CODPARCFAT->CODPARCFAT; NUPLAN->NUPLAN | I |
| ItemPlanejamentoEntrega / [TGFPLAI](TGFPLAI.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
