# TGFESE — Entrada/Saída de Estoque

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EntradaSaidaEstoque | Entrada/Saída de Estoque | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATUALESTOQUE | Atualiza Estoque | I |  |  | N |
| ATUALESTTERC | Atualiza Estoque de Terceiros | S |  |  | N |
| CODEMP | Cód. Empresa | I |  |  | N |
| CODEMPCONTPART | Cód. Empresa Transferência | I |  |  | N |
| CODLOCALORIG | Cód. Local de Origem | I |  |  | N |
| CODPROD | Cód. Produto | I |  |  | N |
| CODTIPOPER | Cód. Tipo de Operação | I |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| DTREFERENCIA | Data de Referência | H |  |  | N |
| QTDENTRADAS | Qtd. de Entradas | F |  |  | N |
| QTDNEG | Qtd. Negociada | F |  |  | N |
| QTDSAIDAS | Qtd. Saídas | F |  |  | N |
| TIPMOV | Tipo de Movimento | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EntradaSaidaEstoque / [TGFESE](TGFESE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
