# TGFLVR — Layout Venda Rápida

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LayoutVendaRapida | Layout Venda Rápida | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CONFIG | Configurações | C |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| LAYOUT | Layout Consulta | C |  |  | N |
| LAYOUT2 | Layout Carrinho | C |  |  | N |
| NULAYOUT | Nro. Layout | I |  |  | N |
| PADRAO | Ativo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TipoOperacao / [TGFTOP](TGFTOP.md) | LayoutVendaRapida / [TGFLVR](TGFLVR.md) | NULAYOUTCVR->NULAYOUT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
