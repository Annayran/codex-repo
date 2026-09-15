# TSIIRE — Formatador Bancos Ítens

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemRemessa | Itens da remessa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CAMPO | Conteúdo | S |  |  | N |
| CODIGO | Código | I |  |  | N |
| MODULO | Módulo | S |  |  | N |
| POSFIM | Posição Final | I |  |  | S |
| POSINI | Posição Inicial | I |  |  | S |
| QTDDEC | Quantidade Decimal | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TAMANHO | Tamanho | I |  |  | N |
| TIPO | Tipo do Campo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FormatadorRemessa / [TSIREM](TSIREM.md) | ItemRemessa / [TSIIRE](TSIIRE.md) | CODIGO->CODIGO; MODULO->MODULO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
