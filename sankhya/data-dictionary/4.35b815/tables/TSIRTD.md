# TSIRTD — Detalhe de Retorno

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
| DetalheRetorno | Detalhe de Retorno | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODIGO | Cód.Layout | I |  |  | N |
| NOME | Nome Coluna | S |  |  | N |
| POSFIM | Posição final da variável | I |  |  | N |
| POSINI | Posição inicial da variável | I |  |  | N |
| QTDDEC | Quantidade Decimais | I |  |  | N |
| SEQUENCIA | Sequência da Coluna | I |  |  | N |
| TIPO | Tipo Coluna | S |  |  | N |
| TIPREG | Tipo do Registro | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DetalheRetorno / [TSIRTD](TSIRTD.md) | MestreRetorno / [TSIRTM](TSIRTM.md) | CODIGO->CODIGO; TIPREG->TIPREG | I |
| MestreRetorno / [TSIRTM](TSIRTM.md) | DetalheRetorno / [TSIRTD](TSIRTD.md) | CODIGO->CODIGO; TIPREG->TIPREG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
