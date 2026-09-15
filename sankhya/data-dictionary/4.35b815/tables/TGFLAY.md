# TGFLAY — Layout da Nota

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LayoutNota | Layout da Nota | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DESCRICAO | Descrição | S |  |  | N |
| LAYOUT | Layout | C |  |  | N |
| MODULO | MODULO | I |  |  | N |
| NULAYOUT | Nro. Layout | I |  |  | N |
| PADRAO | Padrão | S |  |  | N |
| TIPMOV | Tipo de movimento | S |  |  | N |
| TIPREC | Tipo de recebimento | S |  |  | N |
| USARCHECKOUTPROD | Usar checkout de produtos | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CheckoutCabecalho / [TGFCKC](TGFCKC.md) | LayoutNota / [TGFLAY](TGFLAY.md) | NULAYOUT->NULAYOUT | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | LayoutNota / [TGFLAY](TGFLAY.md) | NULAYOUT->NULAYOUT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
