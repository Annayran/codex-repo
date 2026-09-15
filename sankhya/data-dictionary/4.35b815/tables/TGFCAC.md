# TGFCAC — Tabela de Carrinho

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 16
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Carrinho | Carrinho | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODLOCAL | Código do Local | I |  |  | N |
| CODPROD | Código do Produto | I |  |  | N |
| CODVOL | Código do Volume | S |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| DESCTOTAL | Preço Total | F |  |  | N |
| NUNOTA | Nr Único da Nota | I |  |  | N |
| OUTRASCOLUNAS | Outras Colunas | C |  |  | N |
| PRECOBASE | Preço Base | F |  |  | N |
| PRECOBASEQTD | Preço base qtd. | F |  |  | N |
| PRECOTOTAL | Preço Total | F |  |  | N |
| PRECOUSADO | Preço Usado | F |  |  | N |
| QTDPRODUTO | Quantidade do Produto | F |  |  | N |
| SEQCAC | Sequência | I |  |  | N |
| VLRACRESCDESC | Vlr Acrescimo/Decrescimo | F |  |  | N |
| VLRCUS | Vlr Custo | F |  |  | N |
| VLRRETENCAO | Vlr Retenção | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Carrinho / [TGFCAC](TGFCAC.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Carrinho / [TGFCAC](TGFCAC.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
