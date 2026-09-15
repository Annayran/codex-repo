# TSIFTD — SI Formatador de Detalhes

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FormatadorDetalhe | SI Formatador de Detalhes | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CAMPO | Campo | S |  |  | N |
| CODIGO | Código | I |  |  | N |
| MODULO | Módulo | S |  |  | N |
| QTDDEC | Quantidade Decimal | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TAMANHO | Tamanho | I |  |  | N |
| TIPO | Tipo | S |  |  | N |
| TIPREG | Tipo do Registro | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FormatadorDetalhe / [TSIFTD](TSIFTD.md) | FormatadorMaster / [TSIFTM](TSIFTM.md) | CODIGO->CODIGO; MODULO->MODULO | I |
| FormatadorMaster / [TSIFTM](TSIFTM.md) | FormatadorDetalhe / [TSIFTD](TSIFTD.md) | CODIGO->CODIGO; MODULO->MODULO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
